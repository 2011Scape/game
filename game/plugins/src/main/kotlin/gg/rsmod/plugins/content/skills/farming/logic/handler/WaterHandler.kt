package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class WaterHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun water(wateringCan: Int) {
        if (canWater(wateringCan)) {
            player.lockingQueue {
                player.animate(animation)
                player.playSound(sound)
                wait(4)
                if (canWater(wateringCan)) {
                    state.water()
                    val slot = player.inventory.getItemIndex(wateringCan, false)
                    if (player.inventory.remove(wateringCan, beginSlot = slot).hasSucceeded()) {
                        player.inventory.add(usedWateringCan(wateringCan), beginSlot = slot)
                    }
                }
            }
        }
    }

    private fun canWater(wateringCan: Int): Boolean {
        if (!player.inventory.contains(wateringCan)) {
            return false
        }

        if (wateringCan !in wateringCans) {
            return false
        }

        if (wateringCan == emptyWateringCan) {
            player.message("This watering can contains no water.")
            return false
        }

        if (state.seed == null) {
            player.message("You should grow something first.")
            return false
        }

        if (!state.seed!!.seedType.canBeWatered) {
            player.message("This patch doesn't need watering.")
            return false
        }

        if (state.isPlantFullyGrown) {
            player.message("This patch doesn't need watering.")
            return false
        }

        if (state.isDead) {
            player.message("Water isn't going to cure that!")
            return false
        }

        return true
    }

    companion object {
        private const val animation = 2293
        private const val sound = 2446

        private const val emptyWateringCan = Items.WATERING_CAN
        val wateringCans = listOf(
                emptyWateringCan,
                Items.WATERING_CAN_1,
                Items.WATERING_CAN_2,
                Items.WATERING_CAN_3,
                Items.WATERING_CAN_4,
                Items.WATERING_CAN_5,
                Items.WATERING_CAN_6,
                Items.WATERING_CAN_7,
                Items.WATERING_CAN_8,
        )

        private fun usedWateringCan(current: Int): Int {
            val index = (wateringCans.indexOf(current) - 1).coerceAtLeast(0)
            return wateringCans[index]
        }
    }
}
