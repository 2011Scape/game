package gg.rsmod.plugins.content.skills.firemaking

import gg.rsmod.plugins.api.cfg.Items

enum class FiremakingData(val raw: Int, val levelRequired: Int, val experience: Double) {

    NORMAL_LOGS(raw = Items.LOGS, levelRequired = 1, experience = 40.0),
    ACHEY_LOGS(raw = Items.ACHEY_TREE_LOGS, levelRequired = 1, experience = 40.0),
    OAK_LOGS(raw = Items.OAK_LOGS, levelRequired = 15, experience = 60.0),
    WILLOW_LOGS(raw = Items.WILLOW_LOGS, levelRequired = 30, experience = 90.0),
    TEAK_LOGS(raw = Items.TEAK_LOGS, levelRequired = 35, experience = 105.0),
    ARCTIC_PINE_LOGS(raw = Items.ARCTIC_PINE_LOGS, levelRequired = 42, experience = 125.0),
    MAPLE_LOGS(raw = Items.MAPLE_LOGS, levelRequired = 45, experience = 135.0),
    MAHOGANY_LOGS(raw = Items.MAHOGANY_LOGS, levelRequired = 50, experience = 157.5),
    YEW_LOGS(raw = Items.YEW_LOGS, levelRequired = 60, experience = 202.5),
    MAGIC_LOGS(raw = Items.MAGIC_LOGS, levelRequired = 75, experience = 303.8),


    EUCALYPTUS_LOGS(raw = Items.EUCALYPTUS_LOGS, levelRequired = 58, experience = 193.5),

    ;

    companion object {
        val values = enumValues<FiremakingData>()
        val firemakingDefinitions = values.associateBy { it.raw }
    }

}