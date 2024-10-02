package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items

enum class CureType(
    val itemId: Int,
    val toUse: String,
    val animation: Int,
    val sound: Int,
) {
    None(-1, "", -1, -1),
    Potion(Items.PLANT_CURE, "plant cure", 2288, 2438),
    Secateurs(Items.SECATEURS, "secateurs", 2275, -1),
}
