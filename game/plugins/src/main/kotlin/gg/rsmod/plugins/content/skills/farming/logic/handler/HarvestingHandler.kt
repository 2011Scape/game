package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState
import kotlin.math.ceil

class HarvestingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun harvest() {
        if (!canHarvest()) {
            return
        }

        player.queue {
            player.message("You begin to harvest the ${state.seed!!.seedName}.")
            while (state.livesLeft > 0 && canHarvest()) {
                player.animate(state.seed!!.seedType.harvestingTool.animation)
                player.playSound(state.seed!!.seedType.harvestingTool.harvestingSound)
                wait(3)
                if (canHarvest()) {
                    player.inventory.add(state.seed!!.produceId)
                    player.addXp(Skills.FARMING, state.seed!!.harvestXp)
                    if (state.seed!!.seedType.fixedLives || rollRemoveLive()) {
                        state.removeLive()
                        if (state.livesLeft == 0) {
                            state.clear()
                        }
                    }
                }
            }
            player.message("The ${patch.patchName} is now empty.")
        }
    }

    private fun canHarvest(): Boolean {
        if (!state.isPlantFullyGrown) {
            return false
        }

        if (player.inventory.requiresFreeSlotToAdd(state.seed!!.produceId) && player.inventory.isFull) {
            player.message("You don't have enough inventory space for that.")
            return false
        }

        if (!player.inventory.contains(state.seed!!.seedType.harvestingTool.id) && !player.hasEquipped(EquipmentType.WEAPON, state.seed!!.seedType.harvestingTool.id)) {
            player.message("You need a ${player.world.definitions.get(ItemDef::class.java, state.seed!!.seedType.harvestingTool.id).name.lowercase()} to do that.")
            return false
        }

        return true
    }

    private fun rollRemoveLive(): Boolean {
        val farmingLevel = player.getSkills().getCurrentLevel(Skills.FARMING)
        val baseSlots = (state.seed!!.minLiveSaveBaseSlots!! + farmingLevel).coerceAtMost(80)
        val slots = ceil(baseSlots * state.seed!!.seedType.harvestingTool.saveChanceMultiplier).toInt()
        return player.world.chance(slots, 256)
    }

    companion object {
    }
}
