package gg.rsmod.plugins.content.objs.watersource

import gg.rsmod.plugins.api.cfg.Items

enum class WaterContainerData(
    val startItem: Int,
    val resultItem: Int,
) {
    VIAL_OF_WATER(startItem = Items.VIAL, resultItem = Items.VIAL_OF_WATER),
    BUCKET_OF_WATER(startItem = Items.BUCKET, resultItem = Items.BUCKET_OF_WATER),
    JUG_OF_WATER(startItem = Items.EMPTY_JUG, resultItem = Items.JUG_OF_WATER),
    JUG_OF_WATER2(startItem = Items.JUG, resultItem = Items.JUG_OF_WATER),
    BOWL_OF_WATER(startItem = Items.BOWL, resultItem = Items.BOWL_OF_WATER),
    CUP_OF_WATER(startItem = Items.EMPTY_CUP, resultItem = Items.CUP_OF_WATER),
    JUJU_VIAL_OF_WATER(startItem = Items.JUJU_VIAL, resultItem = Items.JUJU_VIAL_OF_WATER),
    SOFT_CLAY(startItem = Items.CLAY, resultItem = Items.SOFT_CLAY),
    WATERING_CAN(startItem = Items.WATERING_CAN, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_1(startItem = Items.WATERING_CAN_1, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_2(startItem = Items.WATERING_CAN_2, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_3(startItem = Items.WATERING_CAN_3, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_4(startItem = Items.WATERING_CAN_4, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_5(startItem = Items.WATERING_CAN_5, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_6(startItem = Items.WATERING_CAN_6, resultItem = Items.WATERING_CAN_8),
    WATERING_CAN_7(startItem = Items.WATERING_CAN_7, resultItem = Items.WATERING_CAN_8),
    ;

    companion object {
        val values = enumValues<WaterContainerData>()
    }
}
