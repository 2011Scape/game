package gg.rsmod.plugins.content.skills.farming.data.blocks

data class SeedHarvest(
        val harvestXp: Double,
        val minLiveSaveBaseSlots: Int,
        val maxLiveSaveBaseSlots: Int,
        val healthCheckXp: Double? = null,
        val healthCheckVarbit: Int? = null,
        val choppedDownVarbit: Int? = null,
        val harvestOption: String?,
)
