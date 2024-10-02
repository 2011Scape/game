package gg.rsmod.plugins.content.items.combine

import gg.rsmod.plugins.api.cfg.Items

enum class CombinationTool(
    val item: Int = -1,
) {
    NONE,
    CHISEL(Items.CHISEL),
    NEEDLE(Items.NEEDLE),
    HAMMER(Items.HAMMER),
}
