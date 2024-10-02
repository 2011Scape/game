package gg.rsmod.plugins.content.skills.crafting.leather

data class LeatherItem(
    val resultItem: Int,
    val amountRequired: Int = 1,
    val levelRequired: Int,
    val experience: Double,
)
