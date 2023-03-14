package gg.rsmod.plugins.content.objs.watersource

import gg.rsmod.plugins.api.cfg.Items

enum class WaterContainerData(val startItem: Int, val resultItem: Int) {
    VIAL_OF_WATER(startItem = Items.EMPTY_VIAL, resultItem = Items.VIAL_OF_WATER),
    BUCKET_OF_WATER(startItem = Items.BUCKET, resultItem = Items.BUCKET_OF_WATER),
    JUG_OF_WATER(startItem = Items.EMPTY_JUG, resultItem = Items.JUG_OF_WATER),
    BOWL_OF_WATER(startItem = Items.BOWL, resultItem = Items.BOWL_OF_WATER),
    CUP_OF_WATER(startItem = Items.EMPTY_CUP, resultItem = Items.CUP_OF_WATER),
    JUJU_VIAL_OF_WATER(startItem = Items.JUJU_VIAL, resultItem = Items.JUJU_VIAL_OF_WATER);

    companion object {
        val values = enumValues<WaterContainerData>()
    }
}