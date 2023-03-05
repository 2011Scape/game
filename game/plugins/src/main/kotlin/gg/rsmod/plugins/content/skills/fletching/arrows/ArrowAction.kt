package gg.rsmod.plugins.content.skills.fletching.arrows

import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player

class ArrowAction {

    suspend fun createArrow(task: QueueTask, arrow: ArrowData, setsToMake: Int) {
        val player = task.player
        val inventory = player.inventory
        var setsLeft = setsToMake
        while (canFletch(task, arrow) && setsLeft > 0) {
            task.wait(2)
            val amountToMake =
                minOf(inventory.getItemCount(Items.HEADLESS_ARROW), inventory.getItemCount(arrow.tips), arrow.amount)
            val success =
                inventory.remove(arrow.tips, amountToMake, assureFullRemoval = true).hasSucceeded() && inventory.remove(
                    Items.HEADLESS_ARROW, amountToMake, assureFullRemoval = true
                ).hasSucceeded()
            if (success) {
                player.inventory.add(arrow.product, amountToMake)
                player.addXp(Skills.FLETCHING, arrow.experience)
                val message =
                    if (amountToMake == 1) "You attach the arrow head to the arrow shaft." else "You attach arrow heads to $amountToMake arrow shafts."
                player.filterableMessage(message)
                setsLeft--
            }
        }
    }

    private suspend fun canFletch(task: QueueTask, arrow: ArrowData): Boolean {
        val player = task.player
        if (player.getSkills().getCurrentLevel(Skills.FLETCHING) < arrow.levelRequirement) {
            val message =
                "You need a Fletching level of ${arrow.levelRequirement} to make ${arrow.name.lowercase()} arrows."
            task.doubleItemMessageBox(
                message, item1 = arrow.tips, amount1 = arrow.amount, item2 = Items.HEADLESS_ARROW
            )
            player.filterableMessage(message)
            return false
        }
        if (!hasRoom(player.inventory, arrow)) {
            player.message("You don't have enough inventory space to do that.")
            return false
        }
        return true
    }

    private fun hasRoom(inventory: ItemContainer, arrow: ArrowData): Boolean {
        if (inventory.isFull && !inventory.requiresFreeSlotToAdd(arrow.product)) {
            return true
        }
        if (inventory.hasSpace) {
            return true
        }
        return inventory.getItemCount(arrow.tips) <= arrow.amount && inventory.getItemCount(Items.HEADLESS_ARROW) <= arrow.amount
    }

}