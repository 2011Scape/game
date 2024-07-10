package gg.rsmod.plugins.content.skills.herblore.mixing

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player

class CreateFinishedPotionAction {
    suspend fun mix(
        task: QueueTask,
        potion: PotionData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        if (!canMix(task, potion)) {
            return
        }
        player.animate(363)
        player.playSound(Sfx.GRIND)
        task.wait(1)
        repeat(amount) {
            if (!canMix(task, potion)) {
                return
            }
            val success =
                inventory.remove(potion.primary, assureFullRemoval = true).hasSucceeded() &&
                    inventory
                        .remove(
                            potion.secondary,
                            assureFullRemoval = true,
                        ).hasSucceeded()
            if (success) {
                player.addXp(Skills.HERBLORE, potion.experience)
                player.inventory.add(potion.product)
                val ingredientName =
                    player.world.definitions
                        .get(ItemDef::class.java, potion.secondary)
                        .name
                        .lowercase()
                player.filterableMessage("You mix the $ingredientName into your potion.")
            }
            player.animate(363)
            player.playSound(Sfx.GRIND)
            task.wait(2)
        }
    }

    private suspend fun canMix(
        task: QueueTask,
        potion: PotionData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        if (player.skills.getCurrentLevel(Skills.HERBLORE) < potion.levelRequirement) {
            val message = "You need a Herblore level of ${potion.levelRequirement} to make this potion."
            task.doubleItemMessageBox(message, item1 = potion.primary, item2 = potion.secondary)
            player.filterableMessage(message)
            return false
        }
        return (inventory.contains(potion.primary) && inventory.contains(potion.secondary))
    }
}
