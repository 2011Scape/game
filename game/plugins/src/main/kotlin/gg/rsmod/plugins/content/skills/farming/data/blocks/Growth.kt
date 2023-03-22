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

    fun wateredVarbits(plantedVarbit: Int, growthStages: Int): IntRange {
        if (wateredOffset == null) {
            return 0..-1
        }

        return findVarbitRange(plantedVarbit, growthStages, wateredOffset, false)
    }

    fun diseasedVarbits(plantedVarbit: Int, growthStages: Int) =
            findVarbitRange(plantedVarbit, growthStages, diseasedOffset, true)

    fun diedVarbits(plantedVarbit: Int, growthStages: Int) =
            findVarbitRange(plantedVarbit, growthStages, diedOffset, true)

    private fun findVarbitRange(plantedVarbit: Int, growthStages: Int, offset: Int, useFirstStageAdjustment: Boolean): IntRange {
        val firstStageAdjustment = if (useFirstStageAdjustment && !canDiseaseOnFirstStage) 1 else 0

        val start = plantedVarbit + offset + firstStageAdjustment
        val end = start + growthStages - firstStageAdjustment - 1
        return start..end
    }
}
