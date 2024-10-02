package gg.rsmod.plugins.content.areas.zanaris

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.prepareForTeleport
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val fairyShopkeepers = arrayOf(Npcs.FAIRY_SHOPKEEPER, Npcs.FAIRY_SHOP_ASSISTANT)
val rawChicken = Items.RAW_CHICKEN

on_obj_option(Objs.FAIRY_RING_12094, "use") {
    player.lockingQueue(TaskPriority.STRONG) {
        wait(2)
        shedTeleport(player)
    }
}

on_obj_option(Objs.PORTAL_12260, "enter") {
    player.lockingQueue(TaskPriority.STRONG) {
        wait(2)
        zanarisTeleport(player)
    }
}

fun shedTeleport(player: Player) {
    val shedTile = Tile(3202, 3169)
    val type = TeleportType.FAIRY
    player.lockingQueue {
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

fun dragonLairTeleport(player: Player) {
    val lairTile = Tile(1565, 4356)
    val type = TeleportType.FAIRY
    player.lockingQueue {
        player.playSound(Sfx.FT_FAIRY_TELEPORT)
        player.prepareForTeleport()
        player.animate(type.animation)
        type.graphic?.let {
            player.graphic(it)
        }
        wait(type.teleportDelay)
        player.teleportTo(lairTile)
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

fun zanarisTeleport(player: Player) {
    val zanarisTile = Tile(2452, 4471)
    val type = TeleportType.FAIRY
    player.lockingQueue {
        player.playSound(Sfx.FT_FAIRY_TELEPORT)
        player.prepareForTeleport()
        player.animate(type.animation)
        type.graphic?.let {
            player.graphic(it)
        }
        wait(type.teleportDelay)
        player.teleportTo(zanarisTile)
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
    containsSamples = false,
) {
    items[0] = ShopItem(Items.DRAGON_DAGGER, 30)
    items[1] = ShopItem(Items.DRAGON_LONGSWORD, 10)
}

create_shop(
    "Irksol's Ruby Rings",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.RUBY_RING, 5)
}

create_shop("Zanaris General Store", CoinCurrency()) {
    sampleItems[0] = ShopItem(Items.TINDERBOX_590, 1, resupplyCycles = 1000)
    sampleItems[1] = ShopItem(Items.HAMMER, 1, resupplyCycles = 1000)
    sampleItems[2] = ShopItem(Items.BRONZE_DAGGER, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.EMPTY_POT, 5)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.SHEARS, 2)
    items[3] = ShopItem(Items.BUCKET, 3)
    items[4] = ShopItem(Items.BOWL, 2)
    items[5] = ShopItem(Items.CAKE_TIN, 2)
    items[6] = ShopItem(Items.TINDERBOX_590, 2)
    items[7] = ShopItem(Items.CHISEL, 2)
    items[8] = ShopItem(Items.HAMMER, 5)
    items[9] = ShopItem(Items.NEWCOMER_MAP, 5)
    items[10] = ShopItem(Items.SECURITY_BOOK, 5)
}

fairyShopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Zanaris General Store")
    }
}

on_npc_option(Npcs.JUKAT, "trade") {
    player.openShop("Jukat's Sword Shop")
}

on_npc_option(Npcs.IRKSOL, "trade") {
    player.openShop("Irksol's Ruby Rings")
}

on_item_on_obj(Objs.CHICKEN_SHRINE, item = rawChicken) {
    player.inventory.remove(rawChicken, 1)
    player.lockingQueue(TaskPriority.STRONG) {
        wait(2)
        dragonLairTeleport(player)
    }
}
