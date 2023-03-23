package gg.rsmod.plugins.content.skills.farming.data.blocks

import gg.rsmod.plugins.content.skills.farming.data.CureType

data class Growth(
        val growthFrequency: Int,
        val canDiseaseOnFirstStage: Boolean,
        val wateredOffset: Int? = null,
        val diseasedOffset: Int,
        val diedOffset: Int,
        val cureType: CureType,
) {
    val canBeWatered = wateredOffset != null
    val canBeCured = cureType != CureType.None
}
