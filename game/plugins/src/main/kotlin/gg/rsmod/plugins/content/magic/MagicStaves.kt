package gg.rsmod.plugins.content.magic

import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class MagicStaves(val runeId: Int, val staves: Array<Int>) {

    /**
     * Represents the fire rune staves.
     */
    FIRE_RUNE(Items.FIRE_RUNE, arrayOf(Items.STAFF_OF_FIRE, Items.FIRE_BATTLESTAFF, Items.MYSTIC_FIRE_STAFF, Items.LAVA_BATTLESTAFF)),

    /**
     * Represents the water rune staves.
     */
    WATER_RUNE(Items.WATER_RUNE, arrayOf(Items.STAFF_OF_WATER, Items.WATER_BATTLESTAFF, Items.MYSTIC_WATER_STAFF)),

    /**
     * Represents the air rune staves.
     */
    AIR_RUNE(Items.AIR_RUNE, arrayOf(Items.STAFF_OF_AIR, Items.AIR_BATTLESTAFF, Items.MYSTIC_AIR_STAFF, Items.ARMADYL_BATTLESTAFF)),

    /**
     * Represents the earth rune staves.
     */
    EARTH_RUNE(Items.EARTH_RUNE, arrayOf(Items.STAFF_OF_EARTH, Items.EARTH_BATTLESTAFF, Items.MYSTIC_EARTH_STAFF, Items.LAVA_BATTLESTAFF));
}