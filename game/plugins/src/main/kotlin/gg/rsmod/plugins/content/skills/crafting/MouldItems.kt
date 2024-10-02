package gg.rsmod.plugins.content.skills.crafting

import gg.rsmod.plugins.api.cfg.Items

enum class MouldItems(
    val id: Int,
) {
    RING(id = Items.RING_MOULD),
    NECKLACE(id = Items.NECKLACE_MOULD),
    BRACELET(id = Items.BRACELET_MOULD),
    AMULET(id = Items.AMULET_MOULD),
    TIARA(id = Items.TIARA_MOULD),
    UNHOLY(id = Items.UNHOLY_MOULD),
    HOLY(id = Items.HOLY_MOULD),
    SICKLE(id = Items.SICKLE_MOULD),
    DEMONIC_SIGIL(id = Items.DEMONIC_SIGIL_MOULD),
    SILVTHRIL_CHAIN(id = Items.CHAIN_LINK_MOULD),
    LIGHTNING_ROD(id = Items.CONDUCTOR_MOULD),
    SILVTHRIL_ROD(id = Items.ROD_CLAY_MOULD),
    BOLT(id = Items.BOLT_MOULD),
}
