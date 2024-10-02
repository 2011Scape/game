package gg.rsmod.plugins.content.items.pestle_and_mortar

import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

object GrindAction {
    // Function to grind a certain number of items using a pestle and mortar
    suspend fun grind(
        task: QueueTask,
        data: PestleAndMortarData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory

        // Calculate the maximum number of items that can be ground based on the players inventory and the given amount
        val maxCount = min(amount, inventory.getItemCount(data.source))

        // Repeat the grinding process for each item up to the maximum count
        repeat(maxCount) {
            // Check if the player can still grind
            if (!canGrind(task, data)) {
                player.animate(-1)
                return
            }
            // Animate the player grinding with a pestle and mortar
            player.animate(364)
            // Remove the source item from the player's inventory and add the resulting item
            if (!inventory.remove(data.source, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            inventory.add(data.result)
            // Wait for a short amount of time before grinding the next item
            task.wait(2)
        }
    }

    // Private function to check if the player can still grind
    private fun canGrind(
        task: QueueTask,
        data: PestleAndMortarData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        // Check if the player has a pestle and mortar and the source item in their inventory
        if (!inventory.contains(Items.PESTLE_AND_MORTAR)) {
            return false
        }
        if (!inventory.contains(data.source)) {
            return false
        }
        return true
    }
}
