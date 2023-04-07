package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.PatchState
import mu.KLogging
import kotlin.math.ceil

/**
 * Logic related to harvesting a patch that is fully grown
 */
class HarvestingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    fun harvest() {
        if (!canHarvest()) {
            return
        }

        player.queue {
            player.filterableMessage("You begin to harvest the ${state.seed!!.seedName.replace("sapling", "tree")}.")
            while (state.livesLeft > 0 && canHarvest()) {
                player.animate(state.seed!!.seedType.harvest.harvestAnimation)
                farmingTimerDelayer.delayIfNeeded(harvestWaitTime)
                wait(harvestWaitTime)
                if (canHarvest()) {
                    player.addXp(Skills.FARMING, state.seed!!.harvest.harvestXp)
                    if (state.seed!! == Seed.Limpwurt) {
                        val extra = (player.world.random(player.getSkills().getCurrentLevel(Skills.FARMING) - 1) - 1) / 10
                        player.inventory.add(state.seed!!.produce.id, amount = 3 + extra)
                        state.clear()
                    } else {
                        player.inventory.add(state.seed!!.produce)
                        if (rollRemoveLive()) {
                            state.removeLive()
                            if (state.livesLeft == 0 && !state.seed!!.seedType.harvest.livesReplenish) {
                                state.clear()
                            }
                        }
                    }
                }
            }
            if (state.livesLeft < 1 && state.seed?.seedType?.harvest?.livesReplenish == false) {
                player.filterableMessage("The ${patch.patchName} is now empty.")
            }

        }
    }

    private fun canHarvest(): Boolean {
        if (!state.isPlantFullyGrown) {
            return false
        }

        if (player.inventory.freeSlotCount < state.seed!!.produce.amount) {
            player.message("You don't have enough inventory space for that.")
            return false
        }

        val tool = state.seed!!.seedType.harvest.harvestingTool
        if (tool != null && !player.inventory.contains(tool)) {
            player.message("You need a ${player.world.definitions.get(ItemDef::class.java, tool).name.lowercase()} to do that.")
            return false
        }

        return true
    }

    private fun rollRemoveLive(): Boolean {
        if (state.seed!!.seedType.harvest.fixedLives) {
            return true
        }

        val farmingLevelDifference = player.getSkills().getCurrentLevel(Skills.FARMING) - state.seed!!.plant.level
        val baseSlots = (state.seed!!.harvest.minLiveSaveBaseSlots + farmingLevelDifference).coerceAtMost(state.seed!!.harvest.maxLiveSaveBaseSlots)
        val magicSecateursFactor = if (player.hasEquipped(EquipmentType.WEAPON, Items.MAGIC_SECATEURS)) 1.1 else 1.0
        val slots = ceil(baseSlots * magicSecateursFactor).toInt()
        return !player.world.chance(slots, 256)
    }

    companion object : KLogging() {
        private const val harvestWaitTime = 2
    }
}
