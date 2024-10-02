import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_npc_option(npc = Npcs.FIDELIO, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

create_shop(
    "Fidelio's General Store",
    CoinCurrency(),
    containsSamples = true,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.NEEDLE, 10)
    items[1] = ShopItem(Items.THREAD, 1000)
    items[2] = ShopItem(Items.EMPTY_POT, 30)
    items[3] = ShopItem(Items.BUCKET, 30)
    items[4] = ShopItem(Items.JUG, 30)
    items[5] = ShopItem(Items.TINDERBOX_590, 10)
    items[6] = ShopItem(Items.CHISEL, 10)
    items[7] = ShopItem(Items.HAMMER, 10)
    items[8] = ShopItem(Items.SAMPLE_BOTTLE, 10)
    items[9] = ShopItem(Items.KNIFE, 10)
}

on_npc_option(npc = Npcs.FIDELIO, option = "trade") {
    player.openShop("Fidelio's General Store")
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "H-hello. You l-look like a s-stranger to these p-parts.",
        "Would you l-like to buy something? I h-have some",
        "s-special offers at the m-minute...some s-sample",
        "bottles for s-storing s-snail slime.",
    )
    when (
        it.options(
            "Yes, please.",
            "No thanks.",
            "Why are your prices so high?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Yes, please.")
            player.openShop("Fidelio's General Store")
        }
        SECOND_OPTION -> {
            it.chatPlayer("No thanks.")
            it.chatNpc(
                "(sigh) Th-that's okay. Nobody ever w-wants",
                "to buy my wares. Oh, s-sure, if it was food or",
                "c-clothes they would though!",
            )
        }
        THIRD_OPTION -> {
            it.chatPlayer("Why are your prices so high?")
            it.chatNpc(
                "As you p-probably know, the h-humans hate our kind",
                "and k-keep us trapped here. To get my s-stocks I have",
                "to sneak into the human lands in s-secret! All the",
                "s-secrecy and d-danger has played h-havoc with my nerves!",
            )
            it.chatPlayer("But all your stuff is overpriced junk!")
            it.chatNpc(
                "N-not at all! They are v-valuable human artefacts",
                "smuggled here at g-great personal risk!",
            )
        }
    }
}
