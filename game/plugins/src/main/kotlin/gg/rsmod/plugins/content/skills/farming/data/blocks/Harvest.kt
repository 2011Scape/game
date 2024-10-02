package gg.rsmod.plugins.content.skills.farming.data.blocks

data class Harvest(
    val harvestingTool: Int?,
    val fixedLives: Boolean,
    val harvestAnimation: Int,
    val livesReplenish: Boolean,
    val liveReplenishFrequency: Int?,
)
