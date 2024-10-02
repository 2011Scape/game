package gg.rsmod.plugins.content.skills.crafting.jewellery

import gg.rsmod.plugins.content.skills.crafting.MouldItems

data class JewelleryItem(
    val mould: MouldItems,
    val resultItem: Int,
    val levelRequired: Int,
    val experience: Double,
    val modelComponent: Int,
    val optionComponent: Int,
)
