package gg.rsmod.plugins.content.skills.crafting.gems

import gg.rsmod.plugins.api.cfg.Items

enum class Gems(val uncut: Int, val cut: Int, val experience: Double, val levelRequirement: Int, val animation: Int, val lowChance: Int = -1, val highChance: Int = -1) {
    OPAL(uncut = Items.UNCUT_OPAL, cut = Items.OPAL, experience = 15.0, levelRequirement = 1, animation = 886, lowChance = 128, highChance = 250),
    JADE(uncut = Items.UNCUT_JADE, cut = Items.JADE, experience = 20.0, levelRequirement = 13, animation = 886, lowChance = 100, highChance = 245),
    TOPAZ(uncut = Items.UNCUT_RED_TOPAZ, cut = Items.RED_TOPAZ, experience = 25.0, levelRequirement = 16, animation = 887, lowChance = 90, highChance = 240),
    SAPPHIRE(uncut = Items.UNCUT_SAPPHIRE, cut = Items.SAPPHIRE, experience = 50.0, levelRequirement = 20, animation = 888),
    EMERALD(uncut = Items.UNCUT_EMERALD, cut = Items.EMERALD, experience = 67.0, levelRequirement = 27, animation = 889),
    RUBY(uncut = Items.UNCUT_RUBY, cut = Items.RUBY, experience = 85.0, levelRequirement = 34, animation = 887),
    DIAMOND(uncut = Items.UNCUT_DIAMOND, cut = Items.DIAMOND, experience = 107.5, levelRequirement = 43, animation = 890),
    DRAGONSTONE(uncut = Items.UNCUT_DRAGONSTONE, cut = Items.DRAGONSTONE, experience = 137.5, levelRequirement = 55, animation = 885),
    ONYX(uncut = Items.UNCUT_ONYX, cut = Items.ONYX, experience = 167.5, levelRequirement = 67, animation = 2717)
    ;

    companion object {
        val values = enumValues<Gems>()
        val gemDefinitions = values.associateBy { it.uncut }
    }
}