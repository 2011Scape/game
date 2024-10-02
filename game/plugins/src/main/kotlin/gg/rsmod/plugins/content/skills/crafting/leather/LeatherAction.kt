package gg.rsmod.plugins.content.skills.crafting.leather

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

/**
 * Handles the leather crafting action.
 * TODO -> For [LeatherData.YAK_HIDE] crafting, add requirement to have progressed The Fremennik Isles up to the yak armour crafting point.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
object LeatherAction {
    suspend fun craft(
        task: QueueTask,
        item: Int,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        val leatherData = LeatherData.getLeatherDataForId(item)!!
        val leatherItem = LeatherData.getLeatherItemForId(item)!!
        val rawItem = leatherData.raw

        val maxCount = min(amount, inventory.getItemCount(rawItem) / leatherItem.amountRequired)

        /**
         * Handles the pre-checking before looping crafting action.
         */
        if (!canCraft(task, item, crafting = false)) {
            return
        }

        repeat(maxCount + 1) {
            if (!canCraft(task, item)) {
                player.animate(id = -1)
                return
            }
            player.animate(id = 1249)
            if (!inventory.remove(rawItem, leatherItem.amountRequired, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            if (player.world.random(1..5) == 1) {
                inventory.remove(Items.THREAD, assureFullRemoval = true)
                player.filterableMessage("You use up one of your reels of thread.")
            }
            inventory.add(item, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, leatherItem.experience)
            player.filterableMessage(
                "You make ${Misc.formatWithIndefiniteArticle(
                    player.world.definitions
                        .get(ItemDef::class.java, item)
                        .name
                        .lowercase(),
                )}.",
            )
            task.wait(3)
        }
    }

    private suspend fun canCraft(
        task: QueueTask,
        item: Int,
        crafting: Boolean = true,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val leatherItem = LeatherData.getLeatherItemForId(item)!!
        val leatherData = LeatherData.getLeatherDataForId(item)!!
        val rawName =
            player.world.definitions
                .get(ItemDef::class.java, leatherData.raw)
                .name
                .lowercase()
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, item)
                .name
                .lowercase()

        if (!inventory.contains(Items.NEEDLE)) {
            player.message("You don't have a needle to craft with.")
            return false
        }
        if (!inventory.contains(Items.THREAD)) {
            player.message("You don't have any thread to craft with.")
            return false
        }
        if (inventory.getItemCount(leatherData.raw) < leatherItem.amountRequired) {
            if (crafting) {
                player.message(
                    "You don't have enough $rawName to make ${Misc.formatWithIndefiniteArticle(resultName)}.",
                )
            } else {
                task.itemMessageBox(
                    "You don't have enough $rawName to<br>make ${Misc.formatWithIndefiniteArticle(
                        resultName,
                    )}.",
                    item = leatherData.raw,
                )
            }
            return false
        }
        if (player.skills.getCurrentLevel(Skills.CRAFTING) < leatherItem.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${leatherItem.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = leatherItem.resultItem,
            )
            return false
        }
        return true
    }
}
