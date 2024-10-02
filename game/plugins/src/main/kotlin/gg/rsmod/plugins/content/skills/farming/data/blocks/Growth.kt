package gg.rsmod.plugins.content.skills.farming.data.blocks

import gg.rsmod.plugins.content.skills.farming.data.CureType

data class Growth(
    val growthFrequency: Int,
    val canDiseaseOnFirstStage: Boolean,
    val cureType: CureType,
    val canBeWatered: Boolean,
)
