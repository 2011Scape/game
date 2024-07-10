package gg.rsmod.plugins.content.items.packs

import gg.rsmod.plugins.api.cfg.Items

enum class PackData(
    val pack: Int,
    val content: Int,
    val amount: Int,
) {
    Vial(Items.VIAL_PACK, Items.VIAL_NOTED, 50),
    VialWater(Items.VIAL_OF_WATER_PACK, Items.VIAL_OF_WATER_NOTED, 50),
    EyeOfNewt(Items.EYE_OF_NEWT_PACK, Items.EYE_OF_NEWT_NOTED, 50),
    EmptySack(Items.EMPTY_SACK_PACK, Items.EMPTY_SACK_NOTED, 50),
    Basket(Items.BASKET_PACK, Items.BASKET_NOTED, 50),
    Juju(Items.JUJU_VIAL_PACK, Items.JUJU_VIAL_NOTED, 50),
    RawBirdMeat(Items.RAW_BIRD_MEAT_PACK, Items.RAW_BIRD_MEAT_NOTED, 50),
    SpiritShard(Items.SPIRIT_SHARD_PACK, Items.SPIRIT_SHARDS, 5000),
}
