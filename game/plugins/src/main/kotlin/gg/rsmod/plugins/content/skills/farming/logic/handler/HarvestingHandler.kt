package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ChatMessageType
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState
import mu.KLogging
import kotlin.math.ceil

class HarvestingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun harvest() {
        if (!canHarvest()) {
            return
        }

        player.queue {
            player.message("You begin to harvest the ${patch.patchName}.")
            while (state.livesLeft > 0 && canHarvest()) {
                player.animate(state.seed!!.seedType.harvestAnimation)
//                player.playSound(???) TODO
                wait(3)
                if (canHarvest()) {
                    player.inventory.add(state.seed!!.produceId)
                    player.addXp(Skills.FARMING, state.seed!!.harvestXp)
                    if (state.seed!!.seedType.fixedLives || rollRemoveLive()) {
                        logger.warn("After removing live: $state")
                        state.removeLive()
                        if (state.livesLeft == 0) {
                            state.clear()
                        }
                    }
                }
            }
            if (state.livesLeft < 1) {
                player.message("The ${patch.patchName} is now empty.")
            }
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

        val tool = state.seed!!.seedType.harvestingTool
        if (tool != null && !player.inventory.contains(tool)) {
            player.message("You need a ${player.world.definitions.get(ItemDef::class.java, tool).name.lowercase()} to do that.")
            return false
        }

        return true
    }

    private fun rollRemoveLive(): Boolean {
        val farmingLevel = player.getSkills().getCurrentLevel(Skills.FARMING)
        val baseSlots = (state.seed!!.minLiveSaveBaseSlots!! + farmingLevel).coerceAtMost(80)
        val magicSecateursFactor = if (state.seed!!.seedType.fixedLives && player.hasEquipped(EquipmentType.WEAPON, Items.MAGIC_SECATEURS)) 1.1 else 1.0
        val slots = ceil(baseSlots * magicSecateursFactor).toInt()
        return player.world.chance(slots, 256)
    }

    companion object : KLogging()
}
