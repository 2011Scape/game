package gg.rsmod.plugins.content.inter.skilling

import gg.rsmod.plugins.content.skills.crafting.jewellery.JewelleryAction
import gg.rsmod.plugins.content.skills.crafting.jewellery.JewelleryData
import kotlin.math.min

/**
 * Set on_button plugins for all buttons on jewellery crafting interface
 * @author Kevin Senez <ksenez94@gmail.com>
 */
JewelleryData.values.forEach { data ->
    data.products.forEach { product ->
        on_button(interfaceId = 446, component = product.optionComponent) {
            val jewelleryItem = JewelleryData.getJewelleryItemFromOption(player.getInteractingButton())!!
            val inventory = player.inventory
            when (player.getInteractingOpcode()) {
                // Make 1
                61 -> {
                    player.queue(
                        TaskPriority.STRONG,
                    ) { JewelleryAction.craftJewellery(this, player, jewelleryItem, amount = 1) }
                }
                // Make 5
                64 -> {
                    player.queue(TaskPriority.STRONG) {
                        JewelleryAction.craftJewellery(this, player, jewelleryItem, amount = 5)
                    }
                }
                // Make All
                4 -> {
                    val jewelData = JewelleryData.getJewelleryDataFromItem(jewelleryItem)!!
                    val count =
                        if (jewelData ==
                            JewelleryData.GOLD
                        ) {
                            inventory.getItemCount(Items.GOLD_BAR)
                        } else {
                            min(inventory.getItemCount(Items.GOLD_BAR), inventory.getItemCount(jewelData.gemRequired))
                        }
                    player.queue(TaskPriority.STRONG) {
                        JewelleryAction.craftJewellery(this, player, jewelleryItem, amount = count)
                    }
                }
                // Make X
                52 -> {
                    player.queue(TaskPriority.WEAK) {
                        val amount = this.inputInt("How many would you like to make?")
                        player.queue(TaskPriority.STRONG) {
                            JewelleryAction.craftJewellery(this, player, jewelleryItem, amount)
                        }
                    }
                } else -> {
                    player.message("Unhandled Option Opcode: ${player.getInteractingOpcode()}", ChatMessageType.CONSOLE)
                }
            }
        }
    }
}
