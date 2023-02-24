package gg.rsmod.plugins.content.skills.cooking

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.util.Misc

object CookingAction {

    suspend fun cook(task: QueueTask, data: CookingData, amount: Int) {

        val player = task.player
        val inventory = player.inventory
        val usingFire = player.world.definitions.get(ObjectDef::class.java, player.getInteractingGameObj().id).name.contains("Fire")
        val maxCount = minOf(amount, inventory.getItemCount(data.raw))

        repeat(maxCount) {
            if (!canCook(task, data)) {
                player.animate(-1)
                return@repeat
            }

            player.animate(if (usingFire) 897 else 883)
            task.wait(1)
            val removeResult = inventory.remove(data.raw, assureFullRemoval = true)
            if (removeResult.hasFailed()) return@repeat

            val success = interpolate(data.lowChance, data.highChance, player.getSkills().getCurrentLevel(Skills.COOKING)) > RANDOM.nextInt(255)

            if (success) {
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

        if (!player.world.isSpawned(player.getInteractingGameObj())) {
            return false
        }

        if(!inventory.contains(data.raw)) {
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.COOKING) < data.levelRequirement) {
            player.queue {
                val name = player.world.definitions.get(ItemDef::class.java, data.cooked).name.lowercase()
                messageBox("You need a Cooking level of ${data.levelRequirement} to cook ${Misc.formatForVowel(name)} $name.")
            }
            return false
        }

        return true
    }

}