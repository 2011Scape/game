package gg.rsmod.plugins.content.areas.karamja

import gg.rsmod.game.Server
import gg.rsmod.game.model.World

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.cfg.*
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.areas.portsarim.Wydin
import gg.rsmod.plugins.content.quests.impl.PiratesTreasure
import gg.rsmod.plugins.content.quests.startedQuest

class Luthas(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    companion object {
        /**
         * Whether player is employed or not
         */
        val EMPLOYED_ATTR = AttributeKey<Boolean>(persistenceKey = "employed_by_luthas")
        /**
         *  How many stored bananas we have in crate
         */
        val CRATED_BANANAS_ATTR = AttributeKey<Int>(persistenceKey = "crated_bananas")
        /**
         * Whether rum is stashed or not
         */
        val RUM_STASHED_ATTR = AttributeKey<Boolean>(persistenceKey = "stashed_rum")
    }

    init {
        on_npc_option(npc = Npcs.LUTHAS, option = "talk-to") {
            player.queue {
                val employed: Boolean = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
                if (employed) {
                    val stored = player.attr.getOrDefault(CRATED_BANANAS_ATTR, 0)
                    if (stored >= 10) {
                        filledCrateDialogue(this)
                    }
                    else {
                        unfilledCrateDialogue(this)
                    }
                }
                else {
                    chatNpc("Hello I'm Luthas, I run the banana plantation here.")
                    when (options(
                        "Could you offer me employment on your plantation?",
                        "That customs officer is annoying isn't she?"
                    )) {
                        FIRST_OPTION -> askForEmploymentDialogue(this)
                        SECOND_OPTION -> annoyingCustomsDialogue(this)
                    }
                }
            }
        }

        on_obj_option(obj = Objs.CRATE_2072, "Search") {
            val stored = player.attr.getOrDefault(CRATED_BANANAS_ATTR, 0)
            val nanas = if (stored == 1) "banana" else "bananas"
            val message = when {
                stored == 10 -> "The crate is full of bananas."
                stored > 0 -> "The crate has $stored $nanas."
                else -> "The crate is completely empty."
            }

            val rumStored = player.attr.has(RUM_STASHED_ATTR) && player.attr[RUM_STASHED_ATTR]!!
            if (rumStored) {
                if (stored == 0) {
                    player.message("There is some rum in here, although with no bananas to cover it. It is a little obvious.")
                }
                else {
                    player.message(message)
                    player.message("There is also some rum stashed in here too.")
                }
            }
            else {
                player.message(message)
            }
        }

        on_obj_option(obj = Objs.CRATE_2072, "Fill") {
            val employed = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
            if (!employed) {
                player.message("I don't know what goes in there.")
                return@on_obj_option
            }

            if (player.inventory.getItemCount(Items.BANANA) > 0) {
                val stored = player.attr.getOrDefault(CRATED_BANANAS_ATTR, 0)
                if (stored == 10) {
                    player.message("The crate is already full.")
                    return@on_obj_option
                }
                val needed = 10 - stored
                val removeNeeded = player.inventory.remove(Items.BANANA, needed)
                if (removeNeeded.hasFailed()) {
                    val taken = needed - removeNeeded.getLeftOver()
                    player.attr[CRATED_BANANAS_ATTR] = stored + taken
                    if (taken > 0) {
                        player.queue {
                            messageBox("You fill the crate with bananas.")
                        }
                    }
                }
                else {
                    player.attr[CRATED_BANANAS_ATTR] = 10
                    player.queue {
                        messageBox("You fill the crate with bananas.")
                    }
                }
            }
        }

        on_item_on_obj(obj = Objs.CRATE_2072, Items.BANANA) {
            val employed = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
            if (!employed) {
                player.message("Why would I want to do that?")
                return@on_item_on_obj
            }

            val stored = player.attr.getOrDefault(CRATED_BANANAS_ATTR, 0)
            if (stored < 10) {
                if (player.inventory.contains(Items.BANANA)) {
                    player.queue {
                        player.inventory.remove(Items.BANANA, 1)
                        player.attr[CRATED_BANANAS_ATTR] = stored + 1
                        messageBox("You pack a banana into the crate")
                    }
                }
            }
            else {
                player.message("The crate is already full.")
            }

        }

        on_item_on_obj(obj = Objs.CRATE_2072, Items.KARAMJAN_RUM) {
            val employed = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
            val rumStored = player.attr.has(RUM_STASHED_ATTR) && player.attr[RUM_STASHED_ATTR]!!
            if (!player.startedQuest(PiratesTreasure)) {
                player.message("I see no reason to do that.")
            }
            else if (!employed) {
                player.message("Why would I want to do that?")
            }
            else {
                val hasWithWydin = player.attr.has(Wydin.RUM_STASHED_ATTR) && player.attr[Wydin.RUM_STASHED_ATTR]!!
                if (hasWithWydin) {
                    player.message("There is already some rum in Wydin's store, I should go and get that first.")
                }
                else if (rumStored) {
                    player.message("There's already some rum in here...")
                }
                else {
                    val removeRum = player.inventory.remove(Items.KARAMJAN_RUM, 1)
                    if (removeRum.hasSucceeded()) {
                        player.attr[RUM_STASHED_ATTR] = true
                        player.queue {
                            messageBox("You stash the rum in the crate.")
                        }
                    }
                }
            }
        }

    }

    private suspend fun filledCrateDialogue(it: QueueTask) {
        it.chatPlayer("I've filled the crate with bananas.")
        it.chatNpc("Well done, here's your payment.")
        val payment = 30
        val added = it.player.inventory.add(Item(Items.COINS_995, payment))
        if (added.hasFailed()) {
            world.spawn(GroundItem(Items.COINS_995, added.getLeftOver(), it.player.tile, it.player))
        }
        val stashed = it.player.attr.has(RUM_STASHED_ATTR) && it.player.attr[RUM_STASHED_ATTR]!!
        if (stashed) {
            it.player.attr[Wydin.RUM_STASHED_ATTR] = true
        }
        it.player.attr[EMPLOYED_ATTR] = false
        it.player.attr[CRATED_BANANAS_ATTR] = 0
        it.player.attr[RUM_STASHED_ATTR] = false
        when (it.options(
            "Will you pay me for another crate full?",
            "Thank you, I'll be on my way.",
            "So where are these bananas going to be delivered to?",
            "That customs officer is annoying isn't she?"
        )) {
            FIRST_OPTION -> {
                it.chatNpc("Yes certainly.")
                it.chatNpc(*("If you go outside you should see the old crate has been loaded on to the ship, and there is " +
                    "another empty crate in its place.").splitForDialogue())
                it.player.attr[EMPLOYED_ATTR] = true
            }
            SECOND_OPTION -> it.chatPlayer("Thank you, I'll be on my way.")
            THIRD_OPTION -> {
                it.chatPlayer(*"So where are these bananas going to be delivered to?".splitForDialogue())
                it.chatNpc(*"I sell them to Wydin who runs a grocery store in Port Sarim.".splitForDialogue())
            }
            FOURTH_OPTION -> {
                annoyingCustomsDialogue(it)
            }
        }
    }

    private suspend fun unfilledCrateDialogue(it: QueueTask) {
        it.chatNpc("Have you completed your task yet?")
        when (it.options(
            "What did I have to do again?",
            "No, the crate isn't full yet...",
            "So where are these bananas going to be delivered to?",
            "That customs officer is annoying isn't she?"
        )) {
            FIRST_OPTION -> {
                it.chatPlayer("What did I have to do again?")
                it.chatNpc(*("There's a crate ready to be loaded onto the ship. If you could fill it up with bananas, I'll" +
                    " pay you 30 gold.").splitForDialogue())
            }
            SECOND_OPTION -> {
                it.chatPlayer("No, the crate isn't full yet...")
                it.chatNpc("Well come back when it is.")
            }
            THIRD_OPTION -> {
                it.chatPlayer(*"So where are these bananas going to be delivered to?".splitForDialogue())
                it.chatNpc(*"I sell them to Wydin who runs a grocery store in Port Sarim.".splitForDialogue())
            }
            FOURTH_OPTION -> {
                annoyingCustomsDialogue(it)
            }

        }
    }

    private suspend fun askForEmploymentDialogue(it: QueueTask) {
        it.chatPlayer("Could you offer me employment on your plantation?")
        it.chatNpc(*"Yes, I can sort something out. There's a crate ready to be loaded onto the ship.".splitForDialogue())
        it.chatNpc(*("You wouldn't believe the demand for bananas from Wydin's shop over in Port Sarim. I think this is " +
            "the third crate I've shipped him this month..").splitForDialogue())
        it.chatNpc(*"If you could fill it up with bananas, I'll pay you 30 gold.".splitForDialogue())
        it.player.attr[EMPLOYED_ATTR] = true
    }

    private suspend fun annoyingCustomsDialogue(it: QueueTask) {
        it.chatPlayer("That customs officer is annoying isn't she?")
        it.chatNpc(*"Well I know her pretty well. She doesn't cause me any trouble any more.".splitForDialogue())
        it.chatNpc(*"She doesn't even search my export crates any more. She knows they only contain bananas.".splitForDialogue())
        it.chatPlayer(*"Really? How interesting. Whereabouts do you send those to?".splitForDialogue())
        it.chatNpc(*"There is a little shop over in Port Sarim that buys them up by the crate. I believe it is run by a man called Wydin.".splitForDialogue())
    }
}
