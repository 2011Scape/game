package gg.rsmod.plugins.content.skills.crafting.jewellery

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.LockState
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.queue.TaskPriority
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

/**
 * Handles the crafting of gold jewellery items through the crafting interface
 * @author Kevin Senez <ksenez94@gmail.com>
 */
object JewelleryAction {

    fun craftJewellery(player: Player, data: JewelleryItem, amount: Int) {
        val inventory = player.inventory
        val jewelData = JewelleryData.getJewelleryDataFromItem(data)!!

        val maxAmount = min(amount, if (jewelData == JewelleryData.GOLD) inventory.getItemCount(Items.GOLD_BAR) else min(inventory.getItemCount(Items.GOLD_BAR), inventory.getItemCount(jewelData.gemRequired)))

        player.queue {
            repeat(maxAmount) {
                if (!canCraft(this, data)) {
                    player.animate(-1)
                    return@queue
                }
                player.animate(id = 899)
                player.playSound(id = 2725)
                wait(3)
                if (!inventory.remove(Items.GOLD_BAR, assureFullRemoval = true).hasSucceeded())
                    return@queue
                if (jewelData != JewelleryData.GOLD && !inventory.remove(jewelData.gemRequired, assureFullRemoval = true).hasSucceeded())
                    return@queue
                inventory.add(data.resultItem, assureFullInsertion = true)
                player.addXp(Skills.CRAFTING, data.experience)
                wait(2)
            }
        }
    }

    private suspend fun canCraft(task: QueueTask, data: JewelleryItem): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName = player.world.definitions.get(ItemDef::class.java, data.resultItem).name.lowercase()
        val jewelData = JewelleryData.getJewelleryDataFromItem(data)!!

        if (!inventory.contains(Items.GOLD_BAR))
            return false

        if (jewelData != JewelleryData.GOLD && !inventory.contains(jewelData.gemRequired))
            return false

        if (!inventory.contains(data.mould.id))
            return false

        if (player.getSkills().getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox("You need a Crafting level of ${data.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(resultName)}.", item = data.resultItem)
            return false
        }

        return true
    }

}