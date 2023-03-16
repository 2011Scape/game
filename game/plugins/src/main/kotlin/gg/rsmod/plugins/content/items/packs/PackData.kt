package gg.rsmod.plugins.content.items.packs

import gg.rsmod.plugins.api.cfg.Items

enum class PackData(val pack: Int, val content: Int, val amount: Int) {
    Vial(Items.VIAL_PACK, Items.VIAL_NOTED, 50),
    EyeOfNewt(Items.EYE_OF_NEWT_PACK, Items.EYE_OF_NEWT_NOTED, 50),
}
