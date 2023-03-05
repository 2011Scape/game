package gg.rsmod.plugins.content.skills.fletching.arrows

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.player

class ArrowAction {

    suspend fun createArrow(task: QueueTask, arrow: ArrowData, setsToMake: Int) {
        val player = task.player
        val inventory = player.inventory
        var setsLeft = setsToMake
        while (canFletch(player, arrow) && setsLeft > 0) {
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

    private fun canFletch(player: Player, data: ArrowData): Boolean {
        if (player.getSkills().getCurrentLevel(Skills.FLETCHING) < data.levelRequirement) {
            player.filterableMessage("You need a Fletching level of ${data.levelRequirement} to make this.")
            return false
        }
        if (player.inventory.requiresFreeSlotToAdd(data.product) && !player.inventory.hasSpace) {
            player.filterableMessage("You don't have enough inventory space to do that.")
            return false
        }
        return player.inventory.contains(data.tips) && player.inventory.contains(Items.HEADLESS_ARROW)
    }

}