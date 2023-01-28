package gg.rsmod.plugins.content.skills.firemaking

import gg.rsmod.plugins.api.cfg.Items

enum class FiremakingData(val raw: Int, val levelRequired: Int, val experience: Double) {

    NORMAL_LOGS(raw = Items.LOGS, levelRequired = 1, experience = 40.0);

    companion object {
        val values = enumValues<FiremakingData>()
        val firemakingDefinitions = values.associateBy { it.raw }
    }

}