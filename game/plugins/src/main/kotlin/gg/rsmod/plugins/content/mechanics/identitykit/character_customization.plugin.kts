package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.game.Server.Companion.logger
import gg.rsmod.game.sync.block.UpdateBlockType

val components = setOf(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 68, 69, 70, 71, 72, 73, 74, 117)

/**
 * Handles Character Creation Buttons
 */
components.forEach { component ->
    on_button(interfaceId = 1028, component = component) {
        when(component) {
            117 -> {
                player.runClientScript(3943) //Send Character Name window
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
/**
fun handleCharacterCustomizingButtons(player: Player, buttonId: Int, slotId: Int) {
    when (buttonId) {
=
        in 103..105 -> {
            val index = player.attributes["ViewWearDesign"] as Int?
            if (index != null) {
                player.attributes["ViewWearDesignD"] = buttonId - 103
                setDesign(player, index, buttonId - 103)
            }
        }
        62, 63 -> setGender(player, buttonId == 62)
        65 -> setSkin(player, slotId)
        in 116..121 -> player.attributes["SelectWearDesignD"] = buttonId - 116
        128 -> {
            val index = player.attributes["SelectWearDesignD"] as Int?
            if (index != null) {
                // handle buttonId == 128 logic
            }
        }
        132 -> {
            val index = player.attributes["SelectWearDesignD"] as Int?
            if (index != null) {
                // handle buttonId == 132 logic
            }
        }
    }
}
fun setGender(player: Player, male: Boolean) {
    if (male == player.appearance.isMale) return
    if (!male) player.appearance.female() else player.appearance.male()
    val index1 = player.attributes["ViewWearDesign"] as? Int
    val index2 = player.attributes["ViewWearDesignD"] as? Int
    setDesign(player, index1 ?: 0, index2 ?: 0)
    player.addBlock(UpdateBlockType.APPEARANCE)
    player.varsManager.sendVarBit(8093, if (male) 0 else 1)
}

fun setSkin(player: Player, index: Int) {
    player.appearance.skinColor = ClientScriptMap.getMap(748).getIntValue(index)
}

fun setDesign(player: Player, index1: Int, index2: Int) {
    val map1 = ClientScriptMap.getMap(3278).getIntValue(index1)
    if (map1 == 0) return
    val male = player.appearance.isMale
    val map2Id = GeneralRequirementMap.getMap(map1).getIntValue(if (male) 1169 else 1175 + index2)
    if (map2Id == 0) return
    val map = GeneralRequirementMap.getMap(map2Id)
    for (i in 1182..1186) {
        val value = map.getIntValue(i)
        if (value == -1) continue
        player.appearance.setLook(i - 1180, value)
    }
    for (i in 1187..1190) {
        val value = map.getIntValue(i)
        if (value == -1) continue
        player.appearance.setColor(i - 1186, value)
    }
    if (!player.appearance.isMale) {
        player.appearance.beardStyle = player.appearance.hairStyle
    }
}
 **/