package gg.rsmod.plugins.content.skills.crafting.spinning

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

object SpinningAction {

    suspend fun spin(task: QueueTask, data: SpinningData, amount: Int) {

        val player = task.player
        val inventory = player.inventory

        val inventoryItems = inventory.rawItems.filterNotNull().map { it.id }.toIntArray()
        val rawItem = inventoryItems.find { data.raw.contains(it) }

        if(rawItem == null) {
            player.message("You don't have any materials to make ${player.world.definitions.get(ItemDef::class.java, data.product).name.toLowerCase()}.")
            return
        }

        if(!canSpin(task, data)) {
            player.animate(-1)
            return
        }

        val maxCount = min(amount, inventory.getItemCount(rawItem))

        repeat(maxCount) {
            player.animate(896)
            task.wait(3)
            inventory.remove(rawItem, assureFullRemoval = true)
            inventory.add(data.product, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.experience)
        }

    }

    private fun canSpin(task: QueueTask, data: SpinningData) : Boolean {
        val player = task.player
        val inventory = player.inventory

        val inventoryItems = inventory.rawItems.filterNotNull().map { it.id }.toIntArray()
        val rawItem = inventoryItems.find { data.raw.contains(it) }

        if(rawItem == null) {
            player.message("You don't have any materials to make ${player.world.definitions.get(ItemDef::class.java, data.product).name.toLowerCase()}.")
            return false
        }

        if(!inventory.contains(rawItem)) {
            player.message("You don't have any ${player.world.definitions.get(ItemDef::class.java, rawItem).name.toLowerCase()}.")
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.CRAFTING) < data.levelRequirement) {
            player.message("You need a crafting level of ${data.levelRequirement} to make this.")
            return false
        }

        return true
    }

}