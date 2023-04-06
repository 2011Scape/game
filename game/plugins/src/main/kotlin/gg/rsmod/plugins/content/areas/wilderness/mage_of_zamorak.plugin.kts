package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import java.lang.ref.WeakReference
import kotlin.random.Random

val MAGE_OF_ZAMORAK = Npcs.MAGE_OF_ZAMORAK_2259

val ABYSS_TELEPORT_OUTER = arrayOf(
    intArrayOf(3059, 4817), intArrayOf(3062, 4812), intArrayOf(3052, 4810), intArrayOf(3041, 4807), intArrayOf(3035, 4811),
    intArrayOf(3030, 4808), intArrayOf(3026, 4810), intArrayOf(3021, 4811), intArrayOf(3015, 4810), intArrayOf(3020, 4818),
    intArrayOf(3018, 4819), intArrayOf(3016, 4824), intArrayOf(3013, 4827), intArrayOf(3017, 4828), intArrayOf(3015, 4837),
    intArrayOf(3017, 4843), intArrayOf(3014, 4849), intArrayOf(3021, 4847), intArrayOf(3022, 4852), intArrayOf(3027, 4849),
    intArrayOf(3031, 4856), intArrayOf(3035, 4854), intArrayOf(3043, 4855), intArrayOf(3045, 4852), intArrayOf(3050, 4857),
    intArrayOf(3054, 4855), intArrayOf(3055, 4848), intArrayOf(3060, 4848), intArrayOf(3059, 4844), intArrayOf(3065, 4841),
    intArrayOf(3061, 4836), intArrayOf(3063, 4832), intArrayOf(3064, 4828), intArrayOf(3060, 4824), intArrayOf(3063, 4821),
    intArrayOf(3041, 4808), intArrayOf(3030, 4810), intArrayOf(3018, 4816), intArrayOf(3015, 4829), intArrayOf(3017, 4840),
    intArrayOf(3020, 4849), intArrayOf(3031, 4855), intArrayOf(3020, 4854), intArrayOf(3035, 4855), intArrayOf(3047, 4854),
    intArrayOf(3060, 4846), intArrayOf(3062, 4836), intArrayOf(3060, 4828), intArrayOf(3063, 4820), intArrayOf(3028, 4806)
)

fun teleportToAbyss(player: Player, dialogue: String = "Veniens! Sallakar! Rinnesset!!") {
    val targetTileIndex = Random.nextInt(ABYSS_TELEPORT_OUTER.size)
    val targetTile = Tile(ABYSS_TELEPORT_OUTER[targetTileIndex][0], ABYSS_TELEPORT_OUTER[targetTileIndex][1])
    val npc = player.getInteractingNpc()
    npc.attr[INTERACTING_PLAYER_ATTR] = WeakReference(player)
    val p = npc.getInteractingPlayer()
    npc.queue {
        npc.facePawn(npc.getInteractingPlayer())
        npc.forceChat(dialogue)
        npc.graphic(343)
        p.graphic(343)
        p.playSound(127)
        wait(MagicCombatStrategy.getHitDelay(npc.tile, p.tile) + 1)
        p.moveTo(targetTile)
        p.getSkills().setCurrentLevel(Skills.PRAYER, 0)
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
    teleportToAbyss(player)
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
                2 -> teleportToAbyss(player)
            }
        }
    }
}