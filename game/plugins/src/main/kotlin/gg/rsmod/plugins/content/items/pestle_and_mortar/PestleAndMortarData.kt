package gg.rsmod.plugins.content.items.pestle_and_mortar

import gg.rsmod.plugins.api.cfg.Items

enum class PestleAndMortarData(val source: Int, val result: Int) {
    UnicornHorn(Items.UNICORN_HORN, Items.UNICORN_HORN_DUST),
    ChocolateBar(Items.CHOCOLATE_BAR, Items.CHOCOLATE_DUST),
    KebbitTeeth(Items.KEBBIT_TEETH, Items.KEBBIT_TEETH_DUST),
    GorakClaw(Items.GORAK_CLAWS, Items.GORAK_CLAW_POWDER),
    BirdNest(Items.BIRDS_NEST_5075, Items.CRUSHED_NEST),
    DesertGoatHorn(Items.DESERT_GOAT_HORN, Items.GOAT_HORN_DUST),
    BlueDragonScales(Items.BLUE_DRAGON_SCALE, Items.DRAGON_SCALE_DUST),
    MudRune(Items.MUD_RUNE, Items.GROUND_MUD_RUNES),
    Ashes(Items.ASHES, Items.GROUND_ASHES),
    PoisonKarambwan(Items.POISON_KARAMBWAN, Items.KARAMBWAN_PASTE),
    FishingBait(Items.FISHING_BAIT, Items.GROUND_FISHING_BAIT),
    SeaWeed(Items.SEAWEED, Items.GROUND_SEAWEED);

    companion object {
        val values = enumValues<PestleAndMortarData>()
        val definitions = values.associateBy { it.source }
    }
}
