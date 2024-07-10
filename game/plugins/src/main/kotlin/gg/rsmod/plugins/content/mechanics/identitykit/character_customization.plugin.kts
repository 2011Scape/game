package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.game.Server.Companion.logger

val components = setOf(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 68, 69, 70, 71, 72, 73, 74, 117)

/**
 * Handles Character Creation Buttons
 */
components.forEach { component ->
    on_button(interfaceId = 1028, component = component) {
        when (component) {
            117 -> {
                player.runClientScript(3943) // Send Character Name window
            }
            166 -> {
                // player.closeFullscreenInterface()
                // player.addBlock(UpdateBlockType.APPEARANCE)
                // TODO: Add personal groomer achievement
            }
            in 48..67 -> {
                // TODO: setDesign(player, buttonId - 68, 0)
                player.setComponentText(interfaceId = 1028, component = 115, text = "Modify Further")
                logger.info("Player selected their outfit.")
            }
            in 68..74 -> {
                // TODO: setDesign(player, buttonId - 68, 0)
            }
            else -> {
                // Log an error when an unexpected component value is encountered.
                logger.info("Unexpected component value in on_button call: $component")
            }
        }
    }
}
