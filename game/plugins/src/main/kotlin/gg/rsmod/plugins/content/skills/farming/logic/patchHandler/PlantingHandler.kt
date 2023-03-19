package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.pluralSuffix
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed

class PlantingHandler(patch: Patch, player: Player) : PatchVarbitUpdater(patch, player) {
    suspend fun plant(task: QueueTask, seed: Seed) {
        if (!canPlant(seed)) {
            return
        }

        if (player.inventory.remove(seed.seedId).hasSucceeded()) {
            seed.seedType.plantingTool.replacementId?.let(player.inventory::add)
            player.animate(seed.seedType.plantingTool.animation)
            player.playSound(seed.seedType.plantingTool.plantingSound)
            task.wait(plantingWaitTime)
            player.addXp(Skills.FARMING, seed.plantXp)
            player.filterableMessage(seed.seedType.plantingTool.plantedMessage(seed, patch))
            setVarbit(seed.startingVarbitValue)
        }
    }

    private val patchIsEmpty get() = varbitValue == emptyVarbit

    private val patchHasWeeds get() = varbitValue in weedVarbits

    private fun canPlant(seed: Seed): Boolean {
        if (seed.seedType !in patch.seedTypes) {
            player.message("You can't plant a ${seed.seedName} in this patch.")
            return false
        }

        if (seed.level > player.getSkills().getCurrentLevel(Skills.FARMING)) {
            player.message("You must be a Level ${seed.level} Farmer to plant those.")
            return false
        }

        if (patchHasWeeds) {
            player.message("This patch needs weeding first.")
            return false
        }

        if (!patchIsEmpty) {
            player.message("You can only plant ${seed.seedName} in an empty patch.")
            return false
        }

        if (!player.inventory.contains(seed.seedType.plantingTool.id)) {
            player.message(seed.seedType.plantingTool.messageWhenMissing)
            return false
        }

        if (player.inventory.getItemCount(seed.seedId) < seed.amountToPlant) {
            player.message("You need ${seed.amountToPlant} ${seed.seedName.pluralSuffix(seed.amountToPlant)} to grow those.")
            return false
        }

        return true
    }

    companion object {
        private const val emptyVarbit = 3
        private const val plantingWaitTime = 3

        private val weedVarbits = 0..2
    }
}
