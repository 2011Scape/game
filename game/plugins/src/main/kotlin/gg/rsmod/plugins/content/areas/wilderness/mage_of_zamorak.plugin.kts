package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import java.lang.ref.WeakReference

val MAGE_OF_ZAMORAK = Npcs.MAGE_OF_ZAMORAK_2259

fun teleportToAbyss(player: Player, dialogue: String = "Veniens! Sallakar! Rinnesset!!", targetTile: Tile) {
    val npc = player.getInteractingNpc()
    npc.attr[INTERACTING_PLAYER_ATTR] = WeakReference(player)
    val p = npc.getInteractingPlayer()
    npc.queue {
        npc.facePawn(npc.getInteractingPlayer())
        npc.forceChat(dialogue)
        npc.graphic(343)
        val projectile = npc.createProjectile(p, 109, ProjectileType.MAGIC)
        p.world.spawn(projectile)
        p.playSound(127)
        wait(MagicCombatStrategy.getHitDelay(npc.tile, p.tile) + 1)
        p.moveTo(targetTile)
        wait(1)
        p.graphic(343)
        p.playSound(126)
    }
}

create_shop("Battle Runes", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.FIRE_RUNE, amount = 100, sellPrice = 5, buyPrice = 17)
    items[1] = ShopItem(Items.WATER_RUNE, amount = 100, sellPrice = 5, buyPrice = 17)
    items[2] = ShopItem(Items.AIR_RUNE, amount = 100, sellPrice = 5, buyPrice = 17)
    items[3] = ShopItem(Items.EARTH_RUNE, amount = 100, sellPrice = 5, buyPrice = 17)
    items[4] = ShopItem(Items.MIND_RUNE, amount = 100, sellPrice = 5, buyPrice = 17)
    items[5] = ShopItem(Items.BODY_RUNE, amount = 100, sellPrice = 5, buyPrice = 16)
    items[6] = ShopItem(Items.CHAOS_RUNE, amount = 30, sellPrice = 140, buyPrice = 42)
    items[7] = ShopItem(Items.DEATH_RUNE, amount = 30, sellPrice = 310, buyPrice = 93)
}

on_npc_option(npc = MAGE_OF_ZAMORAK, option = "teleport") {
    teleportToAbyss(player, targetTile = Tile(3048, 4809))
}

on_npc_option(npc = MAGE_OF_ZAMORAK, option = "trade") {
    player.openShop("Battle Runes")
}

on_npc_option(npc = MAGE_OF_ZAMORAK, option = "talk-to") {
    val mageNPC = player.getInteractingNpc()
    val otherGodNames = arrayOf("SARADOMIN", "GUTHIX")
    if (!player.hasEquippedWithName(otherGodNames)) {
        player.queue {
            chatNpc("This isn't the place to talk. Visit me in Varrock's", "Chaos Temple if you have something to discuss.", "Unless you're here to teleport or buy something?")
            when (options("Let's see what you're selling.", "Could you teleport me to the Abyss?")) {
                1 -> player.openShop("Battle Runes")
                2 -> teleportToAbyss(player, targetTile = Tile(3048, 4809))
            }
        }
    }
}