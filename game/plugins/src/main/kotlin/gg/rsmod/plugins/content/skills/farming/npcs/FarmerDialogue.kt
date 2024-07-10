package gg.rsmod.plugins.content.skills.farming.npcs

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.npcs.Farmer
import gg.rsmod.plugins.content.skills.farming.logic.PatchManager

class FarmerDialogue(
    private val farmer: Farmer,
) {
    fun quickProtect(
        player: Player,
        patch: Patch,
    ) {
        player.queue {
            val patchManager = player.farmingManager().getPatchManager(patch)
            if (!patchManager.canProtect()) {
                chatNpc("Please plant a healthy seed first.")
            } else {
                val cost =
                    patchManager.state.seed!!
                        .growth.protectionPayment!!
                val name =
                    player.world.definitions
                        .get(ItemDef::class.java, cost.id)
                        .name
                        .lowercase()
                when (
                    this.options(
                        "Ok.",
                        "No thank you.",
                        title = "Pay the farmer ${cost.amount} ${name.pluralSuffix(cost.amount)}?",
                    )
                ) {
                    1 -> {
                        if (!patchManager.hasEnoughItemsToProtect()) {
                            chatNpc("You don't have enough $name!")
                        } else {
                            if (patchManager.protect()) {
                                chatNpc(
                                    *"Thank you, that will do. I will look after your crops for you.".splitForDialogue(),
                                )
                            } else {
                                player.message("Something went wrong here. Please contact the discord.")
                            }
                        }
                    }
                    2 -> Unit
                }
            }
        }
    }

    suspend fun mainChat(it: QueueTask) {
        when (
            it.options(
                "Would you look after my crops for me?",
                "Can you give me any Farming advice?",
                "Can you sell me something?",
                "That's all, thanks.",
            )
        ) {
            1 -> lookAfterCropsChat(it)
            2 -> farmingAdviceChat(it)
            3 -> {
                it.chatPlayer("Can you sell me something?")
                it.chatNpc(
                    *"That depends on whether I have it to sell. What is it that you're looking for?".splitForDialogue(),
                )
                shopChat(it, true)
            }
            4 -> it.chatPlayer("That's all, thanks.")
        }
    }

    suspend fun lookAfterCropsChat(it: QueueTask) {
        it.chatPlayer("Would you look after my crops for me?")
        if (farmer.protectionOptions.size == 1) {
            lookAfterSpecificCropChat(
                it,
                it.player.farmingManager().getPatchManager(farmer.protectionOptions.single().patch),
            )
        } else {
            it.chatNpc("I might. Which one were you thinking of?")
            when (it.options(*farmer.protectionOptions.map { it.description }.toTypedArray())) {
                1 ->
                    lookAfterSpecificCropChat(
                        it,
                        it.player.farmingManager().getPatchManager(farmer.protectionOptions[0].patch),
                    )
                2 ->
                    lookAfterSpecificCropChat(
                        it,
                        it.player.farmingManager().getPatchManager(farmer.protectionOptions[1].patch),
                    )
            }
        }
    }

    suspend fun lookAfterSpecificCropChat(
        it: QueueTask,
        patchManager: PatchManager,
    ) {
        when {
            patchManager.state.seed == null ->
                it.chatNpc(
                    *"You don't have anything planted in that patch. Plant something and I might agree to look after it for you."
                        .splitForDialogue(),
                )
            patchManager.state.isPlantFullyGrown ->
                it.chatNpc(
                    *"That patch is already fully grown! I don't know what you want me to do with it!"
                        .splitForDialogue(),
                )
            patchManager.state.isProtectedThroughPayment -> it.chatNpc("I'm already looking after this patch for you.")
            patchManager.state.isDead -> it.chatNpc("That crop is dead!")
            patchManager.state.isDiseased -> it.chatNpc("Cure the crop of disease first.")
            !patchManager.state.seed!!
                .growth.canDisease -> it.chatNpc("That patch does not need protecting.")
            else -> {
                val cost =
                    patchManager.state.seed!!
                        .growth.protectionPayment ?: return
                val name =
                    it.player.world.definitions
                        .get(ItemDef::class.java, cost.id)
                        .name
                        .lowercase()
                it.chatNpc("Certainly, that will cost you ${cost.amount} ${name.pluralSuffix(cost.amount)}.")
                when (it.options("Ok.", "No thank you.")) {
                    1 -> {
                        if (!patchManager.hasEnoughItemsToProtect()) {
                            it.chatNpc("You don't have enough $name!")
                        } else {
                            if (patchManager.protect()) {
                                it.chatNpc(
                                    *"Thank you, that will do. I will look after your crops for you.".splitForDialogue(),
                                )
                            } else {
                                it.player.message("Something went wrong here. Please contact the discord.")
                            }
                        }
                    }

                    2 -> it.chatPlayer("No thank you.")
                }
            }
        }
    }

    suspend fun farmingAdviceChat(it: QueueTask) {
        it.chatPlayer("Can you give me any Farming advice?")
        it.chatNpc(*farmingAdvicePossibilities.random())
        mainChat(it)
    }

    suspend fun shopChat(
        it: QueueTask,
        firstPage: Boolean,
    ) {
        val options = if (firstPage) sellingOptions.take(3) else sellingOptions.drop(3)
        val lastOption = if (firstPage) "(See more items)" else "(See previous items)"
        when (
            val chosen =
                it.options(
                    *options
                        .map {
                            it.playerMessage.replace("{name}", it.name)
                        }.toTypedArray(),
                    lastOption,
                )
        ) {
            in (1..3) -> {
                val item = options[chosen - 1]
                it.chatPlayer(item.playerMessage.replace("{name}", item.name))
                it.chatNpc(
                    *(
                        item.dantaeraMessage.replace("{name}", item.name) +
                            dantaeraMessage3.replace("{price}", item.cost.toString())
                    ).splitForDialogue(),
                )
                when (
                    it.options(
                        "Yes, that sounds like a fair price.",
                        "No thanks, I can get that much cheaper elsewhere.",
                    )
                ) {
                    1 -> {
                        it.chatPlayer("Yes, that sounds like a fair price.")
                        if (!item.handle(it.player)) {
                            it.chatNpc("Come back when you have enough coins.")
                        }
                    }
                    2 -> it.chatPlayer("No thanks, I can get that much cheaper elsewhere.")
                }
            }
            4 -> shopChat(it, !firstPage)
        }
    }

    val northPatch = Patch.CatherbyAllotmentNorth
    val southPatch = Patch.CatherbyAllotmentSouth
    val farmingAdvicePossibilities =
        listOf(
            "Hops are good for brewing ales. I believe there's a brewery up in Keldagrim somewhere, and I've heard rumours that a place called Phasmatys used to be good for that type of thng. 'Fore they all died, of course."
                .splitForDialogue(),
            "Applying compost to a patch will not only reduce the chance that your crops will get diseased, but you will also grow more crops to harvest."
                .splitForDialogue(),
            "Vegetables, hops, and flowers are far more likely to grow healthily if you water them periodically."
                .splitForDialogue(),
            "You don't have to buy all your plantpots you know, you can make them yourself on a pottery wheel. If you're a good enough [craftsman/craftswoman], that is."
                .splitForDialogue(),
            "You can fill plantpots with soil from Farming patches, if you have a gardening trowel.".splitForDialogue(),
            "Tree seeds must be grown in a plantpot of soil into a sapling, and then transferred to a tree patch to continue growing to adulthood."
                .splitForDialogue(),
            "You can put up to ten potatoes, cabbages or onions in vegetable sacks, although you can't have a mix in the same sack."
                .splitForDialogue(),
            "The only way to cure a bush or tree of disease is to prune away the diseased leaves with a pair of secateurs. For all other crops I would just apply some plant-cure."
                .splitForDialogue(),
            "There are four main Farming areas - Elstan looks after an area south of Falador, Dantaera has one to the north of Catherby, Kragen has one near Ardougne, and Lyra looks after a place in north Morytania."
                .splitForDialogue(),
            "Don't just throw away your weeds after you've raked a patch - put them in a compost bin and make some compost."
                .splitForDialogue(),
            "You can put up to five tomatoes, strawberries, apples, bananas, or oranges into a fruit basket, although you can't have a mix in the same basket."
                .splitForDialogue(),
            "Supercompost is far better than normal compost, but more expensive to make. You need to rot the right type of item, show me an item, and I'll tell you if it's supercompostable or not."
                .splitForDialogue(),
            "You can buy all the farming tools from farming shops, which can be found close to the allotments."
                .splitForDialogue(),
            "There is a special patch for growing Belladonna - I believe it's somewhere near Draynor Manor, where the ground is a tad 'unblessed.'"
                .splitForDialogue(),
        )
    val playerMessage1 = "Some {name}."
    val playerMessage2 = "A {name}."
    val dantaeraMessage1 = "Plant cure, eh? I might have some put aside for myself. T"
    val dantaeraMessage2 = "A {name}, eh? I might have one spare... t"
    val dantaeraMessage3 = "ell you what, I'll sell it to you for {price} gp if you like."
    val sellingOptions =
        listOf(
            SellOption(Items.PLANT_CURE, "plant cure", playerMessage1, dantaeraMessage1, 25),
            SellOption(Items.COMPOST, "bucket of compost", playerMessage2, dantaeraMessage2, 35),
            SellOption(Items.RAKE, "rake", playerMessage2, dantaeraMessage2, 15),
            SellOption(Items.WATERING_CAN_8, "watering can", playerMessage2, dantaeraMessage2, 25),
            SellOption(Items.GARDENING_TROWEL, "gardening trowel", playerMessage2, dantaeraMessage2, 15),
            SellOption(Items.SEED_DIBBER, "seed dibber", playerMessage2, dantaeraMessage2, 15),
        )

    data class SellOption(
        val item: Int,
        val name: String,
        val playerMessage: String,
        val dantaeraMessage: String,
        val cost: Int,
    ) {
        fun handle(player: Player): Boolean {
            return if (player.inventory.remove(Items.COINS_995, amount = cost).hasSucceeded()) {
                player.inventory.add(item)
                true
            } else {
                false
            }
        }
    }
}
