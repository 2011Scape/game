package gg.rsmod.plugins.content.skills.farming.data.blocks

import gg.rsmod.game.model.item.Item

data class SeedGrowth(
    val growthStages: Int,
    val canDisease: Boolean,
    val diseaseSlots: Int,
    val protectionPayment: Item?,
    val waterVarbit: Int?,
    val diseaseVarbit: Int,
    val diedVarbit: Int,
)
