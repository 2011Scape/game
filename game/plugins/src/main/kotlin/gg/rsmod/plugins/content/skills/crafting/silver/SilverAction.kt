package gg.rsmod.plugins.content.skills.crafting.silver

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc

/**
 * Handles the crafting of silver items through the interface.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
object SilverAction {
    suspend fun craftSilver(
        task: QueueTask,
        player: Player,
        data: SilverData,
        amount: Int,
    ) {
        val inventory = player.inventory

        repeat(amount) {
            if (!canCraft(task, data)) {
                player.animate(-1)
                return
            }
            player.animate(id = 899)
            player.playSound(Sfx.FURNACE)
            task.wait(3)
            if (!inventory.remove(Items.SILVER_BAR, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            if (data.extraItems != null) {
                data.extraItems.forEach {
                    if (!inventory.remove(item = it, assureFullRemoval = true).hasSucceeded()) {
                        return
                    }
                }
            }
            inventory.add(data.resultItem, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.experience)
            task.wait(2)
        }
    }

    /**
     * TODO Add checking for quest-specific items for quest progress
     */
    private suspend fun canCraft(
        task: QueueTask,
        data: SilverData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.resultItem.id)
                .name
                .lowercase()

        if (!inventory.contains(Items.SILVER_BAR)) {
            return false
        }

        if (data.extraItems != null) {
            data.extraItems.forEach {
                if (!inventory.contains(it)) {
                    return false
                }
            }
        }

        if (!inventory.contains(data.mould.id)) {
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.resultItem.id,
                amountOrZoom = data.resultItem.amount,
            )
            return false
        }

        return true
    }
}
