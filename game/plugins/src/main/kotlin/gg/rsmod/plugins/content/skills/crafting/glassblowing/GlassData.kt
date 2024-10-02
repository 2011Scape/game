package gg.rsmod.plugins.content.skills.crafting.glassblowing

import gg.rsmod.plugins.api.cfg.Items

enum class GlassData(
    val id: Int,
    val levelRequired: Int,
    val experience: Double,
) {
    BEER_GLASS(id = Items.BEER_GLASS, levelRequired = 1, experience = 17.5),
    EMPTY_CANDLE_LANTERN(id = Items.CANDLE_LANTERN, levelRequired = 4, experience = 19.0),
    EMPTY_OIL_LAMP(id = Items.OIL_LAMP_4525, levelRequired = 12, experience = 25.0),
    EMPTY_VIAL(id = Items.VIAL, levelRequired = 33, experience = 35.0),
    FISHBOWL(id = Items.FISHBOWL, levelRequired = 42, experience = 42.5),
    UNPOWERED_ORB(id = Items.UNPOWERED_ORB, levelRequired = 46, experience = 52.5),
    LANTERN_LENS(id = Items.LANTERN_LENS, levelRequired = 49, experience = 55.0),
    EMPTY_LIGHT_ORB(id = Items.EMPTY_LIGHT_ORB, levelRequired = 87, experience = 70.0),
    ;

    companion object {
        val values = enumValues<GlassData>()
        val glassDefinitions = values.associateBy { it.id }
    }
}
