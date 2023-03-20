package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class WaterHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun water(inventorySlot: Int) {
        val wateringCan = player.inventory[inventorySlot]

        when {
            wateringCan == null -> Unit
            wateringCan.id !in wateringCans -> Unit
            wateringCan.id !in wateringCans.drop(1) -> player.message("This watering can contains no water.")
            state.seed == null -> player.message("You should grow something first.")
            !state.seed!!.seedType.canBeWatered -> player.message("This patch doesn't need watering.")
            state.isPlantFullyGrown -> player.message("This patch doesn't need watering.")
            state.isDead -> player.message("Water isn't going to cure that!")
        }
    }

    companion object {
        private val wateringCans = listOf(
                Items.WATERING_CAN,
                Items.WATERING_CAN_1,
                Items.WATERING_CAN_2,
                Items.WATERING_CAN_3,
                Items.WATERING_CAN_4,
                Items.WATERING_CAN_5,
                Items.WATERING_CAN_6,
                Items.WATERING_CAN_7,
                Items.WATERING_CAN_8,
        )
    }
}
