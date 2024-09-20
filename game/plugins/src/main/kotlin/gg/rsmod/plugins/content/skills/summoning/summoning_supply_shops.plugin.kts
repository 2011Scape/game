import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Pikkupstix's Summoning Shop", CoinCurrency()) {
    sampleItems[0] = ShopItem(Items.SPIRIT_SHARDS, 7)
    sampleItems[1] = ShopItem(Items.POUCH, 1)
    sampleItems[2] = ShopItem(Items.GOLD_CHARM, 1)
    sampleItems[3] = ShopItem(Items.WOLF_BONES, 1)

    items[0] = ShopItem(Items.ANTLERS, 10)
    items[1] = ShopItem(Items.LIZARD_SKULL, 10)
    items[2] = ShopItem(Items.FEATHER_HEADDRESS, 0)
    items[3] = ShopItem(Items.FEATHER_HEADDRESS_12213, 0)
    items[4] = ShopItem(Items.FEATHER_HEADDRESS_12216, 0)
    items[5] = ShopItem(Items.FEATHER_HEADDRESS_12219, 0)
    items[6] = ShopItem(Items.FEATHER_HEADDRESS_12222, 0)
    items[7] = ShopItem(Items.SPIRIT_SHARDS, 65000)
    items[8] = ShopItem(Items.POUCH, 5000)
    items[9] = ShopItem(Items.SPIRIT_SHARD_PACK, 65000)
    items[10] = ShopItem(Items.WOLF_BONES, 0)
}

create_shop("Summoning Supplies", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.ANTLERS, 10)
    items[1] = ShopItem(Items.LIZARD_SKULL, 10)
    items[2] = ShopItem(Items.FEATHER_HEADDRESS, 0)
    items[3] = ShopItem(Items.FEATHER_HEADDRESS_12213, 0)
    items[4] = ShopItem(Items.FEATHER_HEADDRESS_12216, 0)
    items[5] = ShopItem(Items.FEATHER_HEADDRESS_12219, 0)
    items[6] = ShopItem(Items.FEATHER_HEADDRESS_12222, 0)
    items[7] = ShopItem(Items.SPIRIT_SHARDS, 65000)
    items[8] = ShopItem(Items.POUCH, 5000)
    items[9] = ShopItem(Items.SPIRIT_SHARD_PACK, 65000)
}

on_npc_option(Npcs.PIKKUPSTIX, "trade") {
    player.openShop("Pikkupstix's Summoning Shop")
}

on_npc_option(Npcs.BOGROG, "trade") {
    player.openShop("Summoning Supplies")
}

on_obj_option(Objs.WISHING_WELL, "make-wish") {
    player.openShop("Summoning Supplies")
}
