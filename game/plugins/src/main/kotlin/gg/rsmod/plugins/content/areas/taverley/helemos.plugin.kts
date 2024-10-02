package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.game.model.shop.PurchasePolicy
import gg.rsmod.game.model.shop.ShopItem
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.openShop
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

create_shop(
    "Happy Heroes' H'emporium",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.DRAGON_BATTLEAXE, 10)
    items[1] = ShopItem(Items.DRAGON_MACE, 10)
}

on_npc_option(npc = Npcs.HELEMOS, option = "trade") {
    player.openShop("Happy Heroes' H'emporium")
}

on_npc_option(npc = Npcs.HELEMOS, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Welcome to the Heroes' Guild!",
    )
    when (
        it.options(
            "So do you sell anything here?",
            "So what can I do here?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("So do you sell anything good here?")
            it.chatNpc("Why yes! We DO run an exclusive shop for our members!", wrap = true)
            player.openShop("Happy Heroes' H'emporium")
        }
        SECOND_OPTION -> {
            it.chatPlayer("So what can I do here?")
            it.chatNpc(
                "Look around... there are all sorts of things to keep our guild members entertained!",
                wrap = true,
            )
        }
    }
}
