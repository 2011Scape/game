package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

fun createWildernessCapeShop(
    npcId: Int,
    npcName: String,
    shopItems: List<Pair<Int, Int>>,
) {
    create_shop(
        "$npcName's Wilderness Cape Shop",
        currency = CoinCurrency(),
        purchasePolicy = PurchasePolicy.BUY_STOCK,
        containsSamples = false,
    ) {
        shopItems.forEachIndexed { index, item ->
            items[index] = ShopItem(item.first, amount = item.second)
        }
    }

    on_npc_option(npc = npcId, option = "trade") {
        player.openShop("$npcName's Wilderness Cape Shop")
    }

    on_npc_option(npc = npcId, option = "talk-to") {
        player.queue {
            chatNpc("Hello there!", "Are you interested in buying one of my special team capes?")
            when (options("What do team capes do?", "Yes please!", "No thanks.")) {
                1 -> {
                    chatPlayer("What do team capes do?")
                    chatNpc(
                        "If you and your friends all wear the same team cape,",
                        "you'll be less likely to attack your friends by accident,",
                        "and you'll find it easier to attack everyone else.",
                    )
                    chatNpc(
                        "They're very useful in Clan Wars and other",
                        "player-vs-player combat areas where you might",
                        "come across friends you don't want to harm.",
                    )
                    chatNpc("So would you like to buy one?")
                    when (options("Yes please!", "No thanks.")) {
                        1 -> player.openShop("$npcName's Wilderness Cape Shop")
                        2 -> chatPlayer("No thanks.")
                    }
                }
                2 -> player.openShop("$npcName's Wilderness Cape Shop")
                3 -> chatPlayer("No thanks.")
            }
        }
    }
}

val williamItems =
    listOf(
        Items.TEAM1_CAPE to 10,
        Items.TEAM11_CAPE to 10,
        Items.TEAM21_CAPE to 10,
        Items.TEAM31_CAPE to 10,
        Items.TEAM41_CAPE to 10,
    )

val darrenItems =
    listOf(
        Items.TEAM4_CAPE to 10,
        Items.TEAM14_CAPE to 10,
        Items.TEAM24_CAPE to 10,
        Items.TEAM34_CAPE to 10,
        Items.TEAM44_CAPE to 10,
    )

val edmondItems =
    listOf(
        Items.TEAM8_CAPE to 10,
        Items.TEAM18_CAPE to 10,
        Items.TEAM28_CAPE to 10,
        Items.TEAM38_CAPE to 10,
        Items.TEAM48_CAPE to 10,
    )

val edwardItems =
    listOf(
        Items.TEAM5_CAPE to 10,
        Items.TEAM15_CAPE to 10,
        Items.TEAM25_CAPE to 10,
        Items.TEAM35_CAPE to 10,
        Items.TEAM45_CAPE to 10,
    )
val ianItems =
    listOf(
        Items.TEAM2_CAPE to 10,
        Items.TEAM12_CAPE to 10,
        Items.TEAM22_CAPE to 10,
        Items.TEAM32_CAPE to 10,
        Items.TEAM42_CAPE to 10,
    )

val larryItems =
    listOf(
        Items.TEAM3_CAPE to 10,
        Items.TEAM13_CAPE to 10,
        Items.TEAM23_CAPE to 10,
        Items.TEAM33_CAPE to 10,
        Items.TEAM43_CAPE to 10,
    )

val neilItems =
    listOf(
        Items.TEAM7_CAPE to 10,
        Items.TEAM17_CAPE to 10,
        Items.TEAM27_CAPE to 10,
        Items.TEAM37_CAPE to 10,
        Items.TEAM47_CAPE to 10,
    )

val simonItems =
    listOf(
        Items.TEAM6_CAPE to 10,
        Items.TEAM16_CAPE to 10,
        Items.TEAM26_CAPE to 10,
        Items.TEAM36_CAPE to 10,
        Items.TEAM46_CAPE to 10,
    )

val richardItems =
    listOf(
        Items.TEAM6_CAPE to 10,
        Items.TEAM16_CAPE to 10,
        Items.TEAM26_CAPE to 10,
        Items.TEAM36_CAPE to 10,
        Items.TEAM46_CAPE to 10,
    )

val samItems =
    listOf(
        Items.TEAM10_CAPE to 10,
        Items.TEAM20_CAPE to 10,
        Items.TEAM30_CAPE to 10,
        Items.TEAM40_CAPE to 10,
        Items.TEAM50_CAPE to 10,
    )

createWildernessCapeShop(Npcs.WILLIAM, "William", williamItems)
createWildernessCapeShop(Npcs.IAN, "Ian", ianItems)
createWildernessCapeShop(Npcs.LARRY, "Larry", larryItems)
createWildernessCapeShop(Npcs.DARREN, "Darren", darrenItems)
createWildernessCapeShop(Npcs.EDMOND_1785, "Edmond", edmondItems)
createWildernessCapeShop(Npcs.RICHARD, "Richard", richardItems)
createWildernessCapeShop(Npcs.NEIL, "Neil", neilItems)
createWildernessCapeShop(Npcs.EDWARD, "Edward", edwardItems)
createWildernessCapeShop(Npcs.SIMON, "Simon", simonItems)
createWildernessCapeShop(Npcs.SAM_1787, "Sam", samItems)
