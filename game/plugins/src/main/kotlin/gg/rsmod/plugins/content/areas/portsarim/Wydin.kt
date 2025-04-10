package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.game.Server
import gg.rsmod.game.model.LockState
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.attr.INTERACTING_NPC_ATTR
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.shop.PurchasePolicy
import gg.rsmod.game.model.shop.ShopItem
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.cfg.*
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import java.lang.ref.WeakReference

class Wydin(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    companion object {
        /**
         * Whether player is employed or not
         */
        val EMPLOYED_ATTR = AttributeKey<Boolean>(persistenceKey = "employed_by_wydin")
        /**
         * Whether rum is stashed or not
         */
        val RUM_STASHED_ATTR = AttributeKey<Boolean>(persistenceKey = "stashed_rum_wydin")
    }

    private val crateIds = listOf(Objs.CRATE_2071, Objs.CRATE_15030, Objs.CRATE_15031, Objs.CRATES_15032)

    init {
        create_shop(
            "Food Store.",
            currency = CoinCurrency(),
            purchasePolicy = PurchasePolicy.BUY_STOCK,
            containsSamples = false,
        ) {
            items[0] = ShopItem(Items.POT_OF_FLOUR, amount = 500)
            items[1] = ShopItem(Items.RAW_BEEF, amount = 10)
            items[2] = ShopItem(Items.RAW_CHICKEN, amount = 10)
            items[3] = ShopItem(Items.CABBAGE, amount = 10)
            items[4] = ShopItem(Items.BANANA, amount = 0)
            items[5] = ShopItem(Items.REDBERRIES, amount = 0)
            items[6] = ShopItem(Items.BREAD, amount = 10)
            items[7] = ShopItem(Items.CHOCOLATE_BAR, amount = 10)
            items[8] = ShopItem(Items.CHEESE, amount = 10)
            items[9] = ShopItem(Items.TOMATO, amount = 10)
            items[10] = ShopItem(Items.POTATO, amount = 10)
            items[11] = ShopItem(Items.GARLIC, amount = 10)
        }

        on_npc_option(Npcs.WYDIN, option = "trade") {
            sendShop(player)
        }

        on_npc_option(Npcs.WYDIN, option = "talk-to") {
            player.queue { chat(this) }
        }

        on_obj_option(Objs.DOOR_2069, "open") {
            handleDoor(player)
        }

        crateIds.forEach {
            on_obj_option(it, "search") {
                takeFromCrate(player, it)
            }
        }
    }

    private fun handleDoor(player: Player) {
        val employed = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
        if ((employed && player.equipment.contains(Items.WHITE_APRON)) || player.tile.x < 3012) {
            player.lockingQueue (lockState = LockState.DELAY_ACTIONS) {
                val obj = player.getInteractingGameObj()
                val blockingObject = DynamicObject(id = 0, type = 0, rot = 0, tile = obj.tile)
                val openDoor =
                    world.openDoor(
                        obj,
                        opened = 40109,
                        invertTransform = obj.type == ObjectType.DIAGONAL_WALL.value,
                    )
                world.spawn(blockingObject)
                wait(2)
                player.moveTo(Tile(x = if (player.tile.x >= 3012) 3011 else 3012, player.tile.z, player.tile.height))
                wait(2)
                world.closeDoor(
                        openDoor,
                        closed = Objs.DOOR_2069,
                        invertTransform = obj.type == ObjectType.DIAGONAL_WALL.value,
                    )
            }
        }
        else {
            player.queue {
                player.attr[INTERACTING_NPC_ATTR] = WeakReference(world.npcs.firstOrNull { npc -> npc.id == Npcs
                    .WYDIN })
                if (!employed) {
                    chatNpc(*"Hey, you can't go in there. Only employees of the grocery store can go in."
                        .splitForDialogue())
                    val rumSentOver = player.attr.has(RUM_STASHED_ATTR) && player.attr[RUM_STASHED_ATTR]!!
                    if (rumSentOver) {
                        when (options(
                            "Well, can I get a job here?",
                            "Sorry, I didn't realise."
                        )) {
                            FIRST_OPTION -> jobDialogue(this)
                            SECOND_OPTION -> didntRealiseDialogue(this)
                        }
                    }
                    else {
                        didntRealiseDialogue(this)
                    }
                }
                else {
                    chatNpc(*"Can you put your white apron on before going in there, please?"
                        .splitForDialogue())
                }
            }
        }
    }

    private fun takeFromCrate(player: Player, crateId: Int) {
        val itemId = when (crateId) {
            Objs.CRATE_2071 -> Items.BANANA
            Objs.CRATE_15030 -> Items.POTATO
            Objs.CRATE_15031 -> Items.CABBAGE
            Objs.CRATES_15032 -> Items.RAW_CHICKEN
            else -> Items.BANANA
        }
        val itemName = when (crateId) {
            Objs.CRATE_2071 -> "banana"
            Objs.CRATE_15030 -> "potato"
            Objs.CRATE_15031 -> "cabbage"
            Objs.CRATES_15032 -> "raw chicken"
            else -> "banana"
        }
        val itemNamePlural = when (crateId) {
            Objs.CRATE_2071 -> "bananas"
            Objs.CRATE_15030 -> "potatoes"
            Objs.CRATE_15031 -> "cabbages"
            Objs.CRATES_15032 -> "raw chickens"
            else -> "bananas"
        }

        val stashed = player.attr.has(RUM_STASHED_ATTR) && player.attr[RUM_STASHED_ATTR]!!
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            wait(1)
            player.message("There are a lot of $itemNamePlural in the crate.")
            wait(2)
            if (crateId == Objs.CRATE_2071 && stashed) {
                player.message("You find your bottle of rum in amongst the $itemNamePlural.")
                if (player.inventory.hasFreeSpace()) {
                    player.animate(Anims.REACH_FORWARD_2)
                    player.inventory.add(Item(Items.KARAMJAN_RUM, 1))
                    player.attr[RUM_STASHED_ATTR] = false
                }
                else {
                    player.message("You do not have enough free space to take the rum.")
                }
            }
            wait(2)
            player.unlock()
            when (options(
                "Yes.",
                "No.",
                title = "Do you want to take a ${itemName}?"
            )) {
                FIRST_OPTION -> {
                    if (player.inventory.hasFreeSpace()) {
                        player.animate(Anims.REACH_FORWARD_2)
                        player.inventory.add(Item(itemId))
                    }
                    else {
                        player.message("You do not have enough free space to take a $itemName.")
                    }
                }
            }
        }
    }

    fun sendShop(player: Player) {
        player.openShop("Food Store.")
    }

    suspend fun chat(it: QueueTask) {
        val employed = it.player.attr.has(EMPLOYED_ATTR) && it.player.attr[EMPLOYED_ATTR]!!
        if (employed) {
            tidyDialogue(it)
        }
        else {
            it.chatNpc("Welcome to my food store! Would you like to buy anything?")
            val rumSentOver = it.player.attr.has(RUM_STASHED_ATTR) && it.player.attr[RUM_STASHED_ATTR]!!
            if (rumSentOver) {
                when (it.options(
                    "Yes please",
                    "No, thank you.",
                    "What can you recommend?",
                    "Can I get a job here?"
                )) {
                    FIRST_OPTION -> yesPleaseDialogue(it)
                    SECOND_OPTION -> noThankYouDialogue(it)
                    THIRD_OPTION -> recommendDialogue(it)
                    FOURTH_OPTION -> jobDialogue(it)
                }
            }
            else {
                when (it.options(
                    "Yes please",
                    "No, thank you.",
                    "What can you recommend?"
                )) {
                    FIRST_OPTION -> yesPleaseDialogue(it)
                    SECOND_OPTION -> noThankYouDialogue(it)
                    THIRD_OPTION -> recommendDialogue(it)
                }
            }
        }
    }

    private suspend fun yesPleaseDialogue(it: QueueTask) {
        it.chatPlayer("Yes please.")
        sendShop(it.player)
    }

    private suspend fun noThankYouDialogue(it: QueueTask) {
        it.chatPlayer("No, thank you.")
    }

    private suspend fun recommendDialogue(it: QueueTask) {
        it.chatPlayer("What can you recommend?")
        it.chatNpc(*("We have this really exotic fruit all the way from Karamja. It's called a " +
            "banana.").splitForDialogue())
        when (it.options(
            "Hmm, I think I'll try one.",
            "I don't like the sound of that."
        )) {
            FIRST_OPTION -> {
                it.chatPlayer("Hmm, I think I'll try one.")
                it.chatNpc(*("Great, you might as well take a look at the rest of my wares as well" +
                    ".").splitForDialogue())
                sendShop(it.player)
            }
            SECOND_OPTION -> {
                it.chatPlayer("I don't like the sound of that.")
                it.chatNpc("Well, it's your choice, but I do recommend them.")
            }
        }
    }

    private suspend fun tidyDialogue(it: QueueTask) {
        it.chatNpc("Is it nice and tidy round the back now?", facialExpression = FacialExpression.HAPPY_TALKING)
        when (
            it.options(
                "Yes, can I work out front now?",
                "Yes, are you going to pay me yet?",
                "No, it's a complete mess.",
                "Can I buy something please?",
            )
        ) {
            1 -> {
                it.chatPlayer(
                    "Yes, can I work out front now?",
                    facialExpression = FacialExpression.TALKING,
                )
                it.chatNpc("No, I'm the one who works here.", facialExpression = FacialExpression.TALKING)
            }

            2 -> {
                it.chatPlayer(
                    "Yes, are you going to pay me yet?",
                    facialExpression = FacialExpression.TALKING,
                )
                it.chatNpc("Umm... No, not yet.", facialExpression = FacialExpression.TALKING)
            }

            3 -> {
                it.chatPlayer(
                    "No, it's a complete mess.",
                    facialExpression = FacialExpression.TALKING,
                )
                it.chatNpc("Ah well it`ll give you something to do, won't it.", facialExpression = FacialExpression.TALKING)
            }

            4 -> {
                it.chatPlayer(
                    "Can I buy something please?",
                    facialExpression = FacialExpression.TALKING,
                )
                it.chatNpc("Yes, of course..", facialExpression = FacialExpression.TALKING)
                sendShop(it.player)
            }
        }
    }

    private suspend fun jobDialogue(it: QueueTask) {
        it.chatPlayer("Can I get a job here?")
        it.chatNpc(*("Well, you're keen, I'll give you that. Okay, I'll give you a go. Have you got your own white " +
            "apron?").splitForDialogue())
        if (it.player.inventory.contains(Items.WHITE_APRON) || it.player.equipment.contains(Items.WHITE_APRON)) {
            it.chatPlayer("Yes, I have one right here.")
            it.chatNpc(*("Wow - you are well prepared! You're hired. Go through to the back and tidy up for me, please" +
                ".").splitForDialogue())
            it.player.attr[EMPLOYED_ATTR] = true
        }
        else {
            it.chatPlayer("No, I haven't.")
            it.chatNpc(*("Well, you can't work here unless you have a white apron. Health and safety regulations, you " +
                "understand.").splitForDialogue())
            it.chatPlayer("Where can I get one of those?")
            it.chatNpc(*("Well, I get all of mine over at the clothing shop in Varrock. " +
                "They sell them cheap there.").splitForDialogue())
            it.chatNpc(*("Oh, and I'm sure that I've seen a spare one over in Gerrant's fish store somewhere. It's " +
                "the little place just north of here.").splitForDialogue())
        }
    }

    private suspend fun didntRealiseDialogue(it: QueueTask) {
        it.chatPlayer("Sorry, I didn't realise.")
    }
}
