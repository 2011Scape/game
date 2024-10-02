package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import java.lang.ref.WeakReference

val mageOfZamorak = Npcs.MAGE_OF_ZAMORAK_2259

val abyssCoordinatesOuterRing =
    listOf(
        Coordinate(3059, 4817),
        Coordinate(3062, 4812),
        Coordinate(3052, 4810),
        Coordinate(3041, 4807),
        Coordinate(3035, 4811),
        Coordinate(3030, 4808),
        Coordinate(3026, 4810),
        Coordinate(3021, 4811),
        Coordinate(3015, 4810),
        Coordinate(3020, 4818),
        Coordinate(3018, 4819),
        Coordinate(3016, 4824),
        Coordinate(3013, 4827),
        Coordinate(3017, 4828),
        Coordinate(3015, 4837),
        Coordinate(3017, 4843),
        Coordinate(3014, 4849),
        Coordinate(3021, 4847),
        Coordinate(3022, 4852),
        Coordinate(3027, 4849),
        Coordinate(3031, 4856),
        Coordinate(3035, 4854),
        Coordinate(3043, 4855),
        Coordinate(3045, 4852),
        Coordinate(3050, 4857),
        Coordinate(3054, 4855),
        Coordinate(3055, 4848),
        Coordinate(3060, 4848),
        Coordinate(3059, 4844),
        Coordinate(3065, 4841),
        Coordinate(3061, 4836),
        Coordinate(3063, 4832),
        Coordinate(3064, 4828),
        Coordinate(3060, 4824),
        Coordinate(3063, 4821),
        Coordinate(3041, 4808),
        Coordinate(3030, 4810),
        Coordinate(3018, 4816),
        Coordinate(3015, 4829),
        Coordinate(3017, 4840),
        Coordinate(3020, 4849),
        Coordinate(3031, 4855),
        Coordinate(3020, 4854),
        Coordinate(3035, 4855),
        Coordinate(3047, 4854),
        Coordinate(3060, 4846),
        Coordinate(3062, 4836),
        Coordinate(3060, 4828),
        Coordinate(3063, 4820),
        Coordinate(3028, 4806),
    )

// TODO: Need to code the enter the Abyss miniquest and add the second battle runes shop for when players complete the quest.

fun teleportToAbyss(
    player: Player,
    dialogue: String = "Veniens! Sallakar! Rinnesset!!",
) {
    val targetTileIndex = abyssCoordinatesOuterRing.random()
    val targetTile = Tile(targetTileIndex.x, targetTileIndex.z)
    val npc = player.getInteractingNpc()
    npc.attr[INTERACTING_PLAYER_ATTR] = WeakReference(player)
    val p = npc.getInteractingPlayer()
    npc.queue {
        npc.facePawn(npc.getInteractingPlayer())
        npc.forceChat(dialogue)
        npc.graphic(343)
        p.graphic(343)
        p.playSound(Sfx.CURSE_CAST_AND_FIRE)
        wait(MagicCombatStrategy.getHitDelay(npc.tile, p.tile) + 1)
        p.playSound(Sfx.CURSE_HIT)
        p.moveTo(targetTile)
        p.setCurrentPrayerPoints(0)
    }
}

create_shop(
    "Battle Runes",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.FIRE_RUNE, amount = 100, sellPrice = 17, buyPrice = 5)
    items[1] = ShopItem(Items.WATER_RUNE, amount = 100, sellPrice = 17, buyPrice = 5)
    items[2] = ShopItem(Items.AIR_RUNE, amount = 100, sellPrice = 17, buyPrice = 5)
    items[3] = ShopItem(Items.EARTH_RUNE, amount = 100, sellPrice = 17, buyPrice = 5)
    items[4] = ShopItem(Items.MIND_RUNE, amount = 100, sellPrice = 17, buyPrice = 5)
    items[5] = ShopItem(Items.BODY_RUNE, amount = 100, sellPrice = 16, buyPrice = 5)
    items[6] = ShopItem(Items.CHAOS_RUNE, amount = 30, sellPrice = 140, buyPrice = 42)
    items[7] = ShopItem(Items.DEATH_RUNE, amount = 30, sellPrice = 310, buyPrice = 93)
}

on_npc_option(npc = mageOfZamorak, option = "teleport") {
    teleportToAbyss(player)
}

on_npc_option(npc = mageOfZamorak, option = "trade") {
    player.openShop("Battle Runes")
}

on_npc_option(npc = mageOfZamorak, option = "talk-to") {
    val otherGodNames = listOf("SARADOMIN", "GUTHIX")
    if (!player.hasEquippedWithName(otherGodNames.toTypedArray())) {
        player.queue {
            chatNpc(
                "This isn't the place to talk. Visit me in Varrock's",
                "Chaos Temple if you have something to discuss.",
                "Unless you're here to teleport or buy something?",
            )
            when (options("Let's see what you're selling.", "Could you teleport me to the Abyss?")) {
                1 -> player.openShop("Battle Runes")
                2 -> teleportToAbyss(player)
            }
        }
    }
}
