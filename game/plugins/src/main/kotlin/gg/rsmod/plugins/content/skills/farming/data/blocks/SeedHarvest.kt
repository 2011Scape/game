package gg.rsmod.plugins.content.skills.farming.data.blocks

data class SeedHarvest(
    val harvestXp: Double,
    val minLiveSaveBaseSlots: Int,
    val maxLiveSaveBaseSlots: Int,
    val healthCheckXp: Double?,
    val healthCheckVarbit: Int?,
    val choppableVarbit: Int?,
    val clearableVarbit: Int?,
    val fullLivesHarvestableVarbit: Int,
    val choppedDownVarbit: Int?,
    val harvestOption: String?,
)
