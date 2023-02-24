package gg.rsmod.plugins.content.magic

import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class MagicStaves(val runeId: Int, val staves: Array<Int>) {

    /**
     * Represents the fire rune staves.
     */
    FIRE_RUNE(Items.FIRE_RUNE, arrayOf(Items.STAFF_OF_FIRE)),

    /**
     * Represents the water rune staves.
     */
    WATER_RUNE(Items.WATER_RUNE, arrayOf(Items.STAFF_OF_WATER)),

    /**
     * Represents the air rune staves.
     */
    AIR_RUNE(Items.AIR_RUNE, arrayOf(Items.STAFF_OF_AIR)),

    /**
     * Represents the earth rune staves.
     */
    EARTH_RUNE(Items.EARTH_RUNE, arrayOf(Items.STAFF_OF_EARTH));
}