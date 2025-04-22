package gg.rsmod.plugins.content.skills.prayer.scattering

import gg.rsmod.plugins.api.cfg.Gfx
import gg.rsmod.plugins.api.cfg.Items

enum class AshData(
    val ash: Int,
    val gfx: Int,
    val experience: Double,
) {
    IMPIOUS_ASHES(ash = Items.IMPIOUS_ASHES, gfx = Gfx.SCATTER_IMPIOUS_ASHES, experience = 4.0),
    ACCURSED_ASHES(ash = Items.ACCURSED_ASHES, gfx = Gfx.SCATTER_ACCURSED_ASHES, experience = 12.5),
    INFERNAL_ASHES(ash = Items.INFERNAL_ASHES, gfx = Gfx.SCATTER_INFERNAL_ASHES, experience = 62.5),
    ;

    companion object {
        val values = enumValues<AshData>()
        val ashDefinitions = values.associateBy { it.ash }
    }
}
