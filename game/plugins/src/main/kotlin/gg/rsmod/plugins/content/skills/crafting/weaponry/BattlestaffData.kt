package gg.rsmod.plugins.content.skills.crafting.weaponry

import gg.rsmod.plugins.api.cfg.Items

enum class BattlestaffData(
    val orbId: Int,
    val resultItem: Int,
    val levelRequired: Int,
    val experience: Double,
) {
    WATER_BATTLESTAFF(
        orbId = Items.WATER_ORB,
        resultItem = Items.WATER_BATTLESTAFF,
        levelRequired = 54,
        experience = 100.0,
    ),
    EARTH_BATTLESTAFF(
        orbId = Items.EARTH_ORB,
        resultItem = Items.EARTH_BATTLESTAFF,
        levelRequired = 58,
        experience = 112.5,
    ),
    FIRE_BATTLESTAFF(
        orbId = Items.FIRE_ORB,
        resultItem = Items.FIRE_BATTLESTAFF,
        levelRequired = 62,
        experience = 125.0,
    ),
    AIR_BATTLESTAFF(orbId = Items.AIR_ORB, resultItem = Items.AIR_BATTLESTAFF, levelRequired = 66, experience = 137.5),
    ;

    companion object {
        val values = enumValues<BattlestaffData>()
        val battlestaffDefinitions = values.associateBy { it.orbId }
    }
}
