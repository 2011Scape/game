package gg.rsmod.plugins.content.skills.cooking

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.RANDOM
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

object CookingAction {

    suspend fun cook(task: QueueTask, data: CookingData, amount: Int) {

        val player = task.player
        val inventory = player.inventory

        if(!canCook(task, data)) {
            player.animate(-1)
            return
        }

        val maxCount = min(amount, inventory.getItemCount(data.raw))

        repeat(maxCount) {
            // TODO: fire/range animations
            player.animate(883)
            task.wait(1)
            if (!inventory.remove(data.raw, assureFullRemoval = true).hasSucceeded()) {
                return@repeat
            }

            val success = interpolate(data.lowChance, data.highChance, player.getSkills().getCurrentLevel(Skills.COOKING)) > RANDOM.nextInt(255)

            if(success) {
                inventory.add(data.cooked)
                player.addXp(Skills.COOKING, data.experience)
            } else {
                inventory.add(data.burnt)
            }
            task.wait(3)
        }

    }

    private fun canCook(task: QueueTask, data: CookingData) : Boolean {
        val player = task.player
        val inventory = player.inventory

        if(!inventory.contains(data.raw)) {
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.COOKING) < data.levelRequirement) {
            player.message("You need a cooking level of ${data.levelRequirement} to cook ${player.world.definitions.get(ItemDef::class.java, data.raw).name.toLowerCase()}.")
            return false
        }

        return true
    }

}