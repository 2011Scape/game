package gg.rsmod.plugins.content.areas.zanaris

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.prepareForTeleport
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_obj_option(Objs.FAIRY_RING_12094, "use") {
    player.lockingQueue(TaskPriority.STRONG) {
        wait(2)
        shedTeleport(player)
    }
}

fun shedTeleport (player: Player) {
    val shedTile = Tile(3202, 3169)
    val type = TeleportType.FAIRY
    player.lockingQueue{
        player.message("The world starts to shimmer...")
        player.playSound(Sfx.FT_FAIRY_TELEPORT)
        player.prepareForTeleport()
        player.animate(type.animation)
        type.graphic?.let {
            player.graphic(it)
        }
        wait(type.teleportDelay)
        player.teleportTo(shedTile)
        type.endAnimation?.let {
            player.animate(it)
        }
        type.endGraphic?.let {
            player.graphic(it)
        }
        type.endAnimation?.let {
            val def = world.definitions.get(AnimDef::class.java, it)
            wait(def.cycleLength)
        }
        player.animate(-1)
        player.unlock()
        wait(2)
    }
}

create_shop(
        "Jukat's Sword Shop",
        currency = CoinCurrency(),
        purchasePolicy = PurchasePolicy.BUY_STOCK,
        containsSamples = false
)
{
    items[0] = ShopItem(Items.DRAGON_DAGGER, 30)
    items[1] = ShopItem(Items.DRAGON_LONGSWORD, 10)
}

on_npc_option(Npcs.JUKAT, "trade") {
    player.openShop("Jukat's Sword Shop")
}
