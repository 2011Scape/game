package gg.rsmod.plugins.content.skills.herblore.mixing

import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.skills.herblore.HerbData

class CreateUnfinishedPotionAction {
    suspend fun mix(
        task: QueueTask,
        potion: HerbData,
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
                inventory.remove(potion.clean, assureFullRemoval = true).hasSucceeded() &&
                    inventory
                        .remove(
                            Items.VIAL_OF_WATER,
                            assureFullRemoval = true,
                        ).hasSucceeded()
            if (success) {
                player.inventory.add(potion.unf)
                player.filterableMessage("You put the ${potion.name.lowercase()} into the vial of water.")
            }
            player.animate(363)
            player.playSound(Sfx.GRIND)
            task.wait(1)
        }
    }

    private suspend fun canMix(
        task: QueueTask,
        potion: HerbData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        if (player.skills.getCurrentLevel(Skills.HERBLORE) < potion.levelRequirement) {
            val message = "You need a Herblore level of ${potion.levelRequirement} to make this potion."
            task.doubleItemMessageBox(message, item1 = Items.VIAL_OF_WATER, item2 = potion.clean)
            player.filterableMessage(message)
            return false
        }
        return (inventory.contains(potion.clean) && inventory.contains(Items.VIAL_OF_WATER))
    }
}
