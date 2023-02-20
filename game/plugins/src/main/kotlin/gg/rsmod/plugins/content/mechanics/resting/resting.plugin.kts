package gg.rsmod.plugins.content.mechanics.resting

/**
 * Button by minimap.
 */
on_button(interfaceId = 750, component = 1) {
    when(player.getInteractingOpcode()) {
        64 -> Resting.rest(player, musician = false)
    }
}