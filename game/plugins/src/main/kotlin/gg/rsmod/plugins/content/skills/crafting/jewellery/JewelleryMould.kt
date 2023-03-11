package gg.rsmod.plugins.content.skills.crafting.jewellery

import gg.rsmod.plugins.api.cfg.Items

enum class JewelleryMould(val id: Int) {
    RING(id = Items.RING_MOULD),
    NECKLACE(id = Items.NECKLACE_MOULD),
    BRACELET(id = Items.BRACELET_MOULD),
    AMULET(id = Items.AMULET_MOULD),
    TIARA(id = Items.TIARA_MOULD);
}