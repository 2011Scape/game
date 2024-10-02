package gg.rsmod.plugins.content.inter.skilling

import gg.rsmod.plugins.content.skills.crafting.silver.SilverAction
import gg.rsmod.plugins.content.skills.crafting.silver.SilverData
import kotlin.math.max
import kotlin.math.min

/**
 *  Set on_button plugins for all the buttons on the silver crafting interface
 *  @author Kevin Senez <ksenez94@gmail.com>
 */
SilverData.values.forEach { data ->
    on_button(interfaceId = 438, component = data.componentArray[1]) {
        val silverData = SilverData.getDataByComponent(player.getInteractingButton())!!
        val inventory = player.inventory
        when (player.getInteractingOpcode()) {
            // Make 1
            61 -> {
                player.queue(TaskPriority.STRONG) {
                    SilverAction.craftSilver(this, player, silverData, amount = 1)
                }
            }
            // Make 5
            64 -> {
                player.queue(TaskPriority.STRONG) {
                    SilverAction.craftSilver(this, player, silverData, amount = 5)
                }
            }
            // Make All
            4 -> {
                var count = inventory.getItemCount(Items.SILVER_BAR)
                var countExtra = 0
                if (data.extraItems != null) {
                    data.extraItems.forEach {
                        countExtra = max(countExtra, inventory.getItemCount(it))
                    }
                }
                if (countExtra != 0) {
                    count = min(count, countExtra)
                }

                player.queue(TaskPriority.STRONG) { SilverAction.craftSilver(this, player, silverData, count) }
            }
            // Make X
            52 -> {
                player.queue(TaskPriority.WEAK) {
                    val amount = this.inputInt("How many would you like to make?")
                    player.queue(TaskPriority.STRONG) {
                        SilverAction.craftSilver(this, player, silverData, amount)
                    }
                }
            } else -> {
                player.message("Unhandled Option Opcode: ${player.getInteractingOpcode()}", ChatMessageType.CONSOLE)
            }
        }
    }
}
