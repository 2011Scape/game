package gg.rsmod.plugins.content.skills.crafting.pottery

data class PotteryItem(
    val unfired: Int,
    val fired: Int,
    val levelRequired: Int = 1,
    val formingExperience: Double = 0.0,
    val firingExperience: Double = 0.0,
    val addRuneAnimation: Int = -1,
)
