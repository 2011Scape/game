package gg.rsmod.plugins.content.skills.prayer.burying

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.crafting.spinning.SpinningData

enum class BoneData(val bone: Int, val experience: Double) {

    BONES(bone = Items.BONES, experience = 4.5),
    WOLF_BONES(bone = Items.WOLF_BONES, experience = 4.5),
    BURNST_BONES(bone = Items.BURNT_BONES, experience = 4.5),
    MONKEY_BONES(bone = Items.MONKEY_BONES, experience = 5.0),
    MONKEY_BONES_2(bone = Items.MONKEY_BONES_3180, experience = 5.0),
    MONKEY_BONES_3(bone = Items.MONKEY_BONES_3181, experience = 5.0),
    MONKEY_BONES_4(bone = Items.MONKEY_BONES_3182, experience = 5.0),
    MONKEY_BONES_5(bone = Items.MONKEY_BONES_3183, experience = 5.0),
    MONKEY_BONES_6(bone = Items.MONKEY_BONES_3185, experience = 5.0),
    MONKEY_BONES_7(bone = Items.MONKEY_BONES_3186, experience = 5.0),
    BAT_BONES(bone = Items.BAT_BONES, experience = 5.3),
    BIG_BONES(bone = Items.BIG_BONES, experience = 15.0),
    JOGRE_BONES(bone = Items.JOGRE_BONES, experience = 15.0),
    ZOGRE_BONES(bone = Items.ZOGRE_BONES, experience = 12.5),
    SHAIKAHAN_BONES(bone = Items.SHAIKAHAN_BONES, experience = 25.0),
    BABY_DRAGON_BONES(bone = Items.BABYDRAGON_BONES, experience = 30.0),
    WYVERN_BONES(bone = Items.WYVERN_BONES, experience = 50.0),
    DRAGON_BONES(bone = Items.DRAGON_BONES, experience = 72.0),
    FAYRG(bone = Items.FAYRG_BONES, experience = 84.0),
    RAURG_BONES(bone = Items.RAURG_BONES, experience = 96.0),
    DAGANNOTH(bone = Items.DAGANNOTH_BONES, experience = 125.0),
    OURG_BONES(bone = Items.OURG_BONES, experience = 140.0),
    FROST_DRAGON_BONES(bone = Items.FROST_DRAGON_BONES, experience = 180.0);

    companion object {
        val values = enumValues<BoneData>()
        val boneDefinitions = values.associateBy { it.bone }
    }
}