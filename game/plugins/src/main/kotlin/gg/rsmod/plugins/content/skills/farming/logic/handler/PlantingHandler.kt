package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to planting a seed in an empty patch
 */
class PlantingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    fun plant(seed: Seed) {
        player.lockingQueue {
            if (!canPlant(seed)) {
                return@lockingQueue
            }

            if (player.inventory.remove(seed.seedId, amount = seed.amountToPlant()).hasSucceeded()) {
                seed.seedType.plant.plantingTool.replacementId?.let(player.inventory::add)
                player.animate(seed.seedType.plant.plantingTool.animation)
                player.playSound(seed.seedType.plant.plantingTool.plantingSound)
                farmingTimerDelayer.delayIfNeeded(plantingWaitTime)
                wait(plantingWaitTime)
                player.addXp(Skills.FARMING, seed.plant.plantXp)
                player.filterableMessage(seed.seedType.plant.plantingTool.plantedMessage(seed, patch))
                state.plantSeed(seed)
            }
        }
    }

    private fun canPlant(seed: Seed): Boolean {
        if (seed.seedType !in patch.seedTypes) {
            player.message("You can't plant a ${seed.seedName} in this patch.")
            return false
        }

        if (seed.plant.level > player.getSkills().getCurrentLevel(Skills.FARMING)) {
            player.message("You must be a Level ${seed.plant.level} Farmer to plant those.")
            return false
        }

        if (state.isWeedy) {
            player.message("This patch needs weeding first.")
            return false
        }

        if (!state.isEmpty) {
            player.message("You can only plant ${seed.seedName} in an empty patch.")
            return false
        }

        if (!player.inventory.contains(seed.seedType.plant.plantingTool.id)) {
            player.message(seed.seedType.plant.plantingTool.messageWhenMissing)
            return false
        }

        if (player.inventory.getItemCount(seed.seedId) < seed.amountToPlant()) {
            player.message("You need ${seed.amountToPlant()} ${seed.seedName.pluralSuffix(seed.amountToPlant())} to grow those.")
            return false
        }

        return true
    }

    companion object {
        private const val plantingWaitTime = 3
    }
}
