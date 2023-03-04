package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Gender
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.sync.block.UpdateBlockType

/**
 * @author Alycia <https://github.com/alycii>
 */

val MAKEOVER_VARC_START = 1008
val MAKEOVER_VARC_COUNT = 6

val makeoverStyleVars = IntArray(MAKEOVER_VARC_COUNT) { i ->
    MAKEOVER_VARC_START + i
}

val MAKEOVER_HAIR_VARC = makeoverStyleVars[0]
val MAKEOVER_BEARD_VARC = makeoverStyleVars[1]
val MAKEOVER_BODY_VARC = makeoverStyleVars[2]
val MAKEOVER_ARMS_VARC = makeoverStyleVars[3]
val MAKEOVER_WRISTS_VARC = makeoverStyleVars[4]
val MAKEOVER_LEGS_VARC = makeoverStyleVars[5]

val MAKEOVER_COLOR_VARC_START = 1014
val MAKEOVER_COLOR_VARC_COUNT = 5

val makeoverColorVars = IntArray(MAKEOVER_COLOR_VARC_COUNT) { i ->
    MAKEOVER_COLOR_VARC_START + i
}

val MAKEOVER_HAIR_COLOR_VARC = makeoverColorVars[1]
val MAKEOVER_TOP_COLOR_VARC = makeoverColorVars[2]
val MAKEOVER_LEGS_COLOR_VARC = makeoverColorVars[3]
val MAKEOVER_SHOES_COLOR_VARC = makeoverColorVars[4]

val struct = 1048
val legsParam = 1185
val topParam = 1182
val armParam = 1183
val wristParam = 1184
val shoesParam = 1186

val LOOK_ARMS_MALE = 711
val LOOK_ARMS_FEMALE = 693

val LOOK_WRISTS_MALE = 749
val LOOK_WRISTS_FEMALE = 751

val LOOK_LEGS_MALE = 1586
val LOOK_LEGS_FEMALE = 1607

val LOOK_TOP_MALE = 690
val LOOK_TOP_FEMALE = 1591


/**
 * Sets the appearance VARCs for the given player based on their current appearance styles and colors.
 * Uses the `makeoverStyleVars` and `makeoverColorVars` constants to determine the VARCs to set.
 * @param player The player whose appearance VARCs should be set.
 */
fun setAppearanceVarcs(player: Player) {
    // Loop through the style VARCs and set the corresponding VARC for each appearance style.
    makeoverStyleVars.forEachIndexed { index, i ->
        player.setVarc(i, player.appearance.looks[index])
    }

    // Loop through the color VARCs and set the corresponding VARC for each appearance color.
    makeoverColorVars.forEachIndexed { index, i ->
        player.setVarc(i, player.appearance.colors[index])
    }
}

/**
 * Sets the appearance styles and colors for the given player based on their current VARC values.
 * Uses the `makeoverStyleVars` and `makeoverColorVars` constants to determine the VARCs to retrieve.
 * @param player The player whose appearance styles and colors should be set.
 */
fun setAppearance(player: Player) {
    // Loop through the color VARCs and set the corresponding color for each appearance color.
    makeoverColorVars.forEachIndexed { index, i ->
        if(player.getVarc(i) > -1) {
            player.appearance.colors[index] = player.getVarc(i)
        }
    }

    // Loop through the style VARCs and set the corresponding style for each appearance style.
    makeoverStyleVars.forEachIndexed { index, i ->
        if(player.getVarc(i) > -1) {
            player.appearance.looks[index] = player.getVarc(i)
        }
    }

    // Add an appearance update block to update the player's appearance.
    player.addBlock(UpdateBlockType.APPEARANCE)
}

/**
 * Returns the appearance style VARC value for the given gender and body part.
 *
 * @param gender The gender to retrieve the appearance style VARC for.
 * @param part The body part to retrieve the appearance style VARC for.
 * @return The appearance style VARC value for the given gender and body part.
 * @throws IllegalArgumentException If the given part number is not valid for the given gender.
 */
fun getBodyStyleEnum(gender: Gender, part: Int) : Int {
    return when(gender) {
        Gender.MALE -> when(part) {
            0 -> LOOK_TOP_MALE
            1 -> LOOK_ARMS_MALE
            2 -> LOOK_WRISTS_MALE
            3 -> LOOK_LEGS_MALE
            else -> throw IllegalArgumentException("Invalid part number $part for Male")
        }
        Gender.FEMALE -> when(part) {
            0 -> LOOK_TOP_FEMALE
            1 -> LOOK_ARMS_FEMALE
            2 -> LOOK_WRISTS_FEMALE
            3 -> LOOK_LEGS_FEMALE
            else -> throw IllegalArgumentException("Invalid part number $part for Female")
        }
    }
}