package gg.rsmod.plugins.content.skills.cooking

import gg.rsmod.plugins.api.cfg.Items

enum class CookingData(val raw: Int, val cooked: Int, val burnt: Int, val levelRequirement: Int, val experience: Double, val lowChance: Int, val highChance: Int) {

    SHRIMPS(raw = Items.RAW_SHRIMPS, cooked = Items.SHRIMPS, burnt = Items.BURNT_SHRIMP, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512)

    ;


    companion object {
        val values = enumValues<CookingData>()
        val cookingDefinitions = values.associateBy { it.raw }
    }

}