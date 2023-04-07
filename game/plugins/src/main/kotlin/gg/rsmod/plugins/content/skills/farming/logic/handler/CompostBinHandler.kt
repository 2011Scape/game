package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.options
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.CompostBinState
import gg.rsmod.plugins.content.skills.farming.data.CompostBinState.*
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.VarbitUpdater

class CompostBinHandler(private val bin: CompostBin, private val player: Player) {

    private val varbit = VarbitUpdater(bin.varbit, player)

    private val state get() = CompostBinState.forVarbit(varbit.value)

    fun addCompostable(itemId: Int) {
        // Check whether the item can be composted
        if (itemId !in compostable && itemId !in superCompostable) {
            player.message("You cannot compost this item.")
            return
        }

        player.queue {
            // Finds the amount of items currently in the bin, and returns if the bin is closed or filled with compost
            var currentCount = when (state) {
                Empty -> 0
                FillingCompost,
                FillingSuperCompost -> 15 - (state.varbits.last - varbit.value)
                EmptyingCompost,
                EmptyingSuperCompost -> {
                    player.message("The compost bin must be emptied before you can put new items in it.")
                    return@queue
                }
                ClosedReadyCompost,
                ClosedReadySupercompost,
                ClosedFermentingCompost,
                ClosedFermentingSupercompost -> {
                    player.message("The compost bin is closed.")
                    return@queue
                }
            }

            // Check whether there is room in the bin for more items, and returns if there is none
            if (currentCount == 15) {
                player.message("The compost bin is too full to put anything else in it.")
                return@queue
            }

            // Determines the next state. Adding a compostable to super compost, or the other way around, should ask for confirmation
            val nextState = when {
                state == FillingCompost && itemId in superCompostable -> {
                    if (options("Yes, I want to use it to make normal compost.", "No, I don't want to waste it making normal compost.", title = "This is a supercompostable item - are you sure?") == 2) {
                        return@queue
                    }
                    FillingCompost
                }
                state == FillingSuperCompost && itemId in compostable -> {
                    if (options("I just want to make normal compost now.", "I want to stick to making supercompost.", title = "The compost bin contains supercompostable items") == 2) {
                        return@queue
                    }
                    FillingCompost
                }
                state == Empty && itemId in compostable -> FillingCompost
                state == Empty && itemId in superCompostable -> FillingSuperCompost
                else -> state
            }

            // Keep adding the item to the bin until the bin is full or the player runs out of items
            while (player.inventory.contains(itemId) && currentCount < 15) {
                player.animate(fillingAnimation)
                player.playSound(fillingSound)
                wait(3)
                if (player.inventory.remove(itemId).hasSucceeded()) {
                    currentCount++
                    varbit.set(nextState.varbits.first + currentCount - 1)
                }
            }
        }
    }

    fun open() {
        // Determines the new state based on what type of compost is in the bin.
        // If the fermentation is not finished, return
        val newState = when (state) {
            ClosedReadyCompost -> EmptyingCompost
            ClosedReadySupercompost -> EmptyingSuperCompost
            else -> {
                player.message("The vegetation hasn't finished rotting yet.")
                return
            }
        }

        // Open the bin
        player.queue {
            player.animate(openingAnimation)
            player.playSound(openingSound)
            varbit.set(newState.varbits.last)
            player.filterableMessage("You open the compost bin.")
        }
    }

    fun close() {
        // Determines the new state based on what type of compostable is in the bin.
        // If the bin is not completely filled with items, return
        val newState = when {
            state == FillingCompost && varbit.value == state.varbits.last -> ClosedFermentingCompost
            state == FillingSuperCompost && varbit.value == state.varbits.last -> ClosedFermentingSupercompost
            else -> return
        }

        // Close the bin
        player.queue {
            player.animate(closingAnimation)
            player.playSound(closingSound)
            varbit.set(newState.varbits.first)
            player.filterableMessage("You close the compost bin.")
            player.filterableMessage("The contents have begun to rot.")
        }
    }

    fun empty() {
        // If there's no compost to be removed, return
        if (state != EmptyingCompost && state != EmptyingSuperCompost) {
            player.message("There's no compost to take at the moment.")
            return
        }

        // Determine whether we return compost or super compost
        val result = if (state == EmptyingCompost) Items.COMPOST else Items.SUPERCOMPOST
        val xp = if (state == EmptyingCompost) 4.5 else 8.5

        // Keep removing compost as long as there is any left in the bin and the player has empty buckets
        player.queue {
            var currentCount = when (state) {
                EmptyingCompost,
                EmptyingSuperCompost -> 15 - (state.varbits.last - varbit.value)
                else -> throw IllegalStateException()
            }

            while (currentCount > 0) {
                if (!player.inventory.contains(Items.BUCKET)) {
                    player.message("You need a suitable bucket to do that.")
                    return@queue
                }

                player.animate(emptyingAnimation)
                player.playSound(emptyingSound)
                wait(3)
                val slot = player.inventory.getItemIndex(Items.BUCKET, false)
                if (player.inventory.remove(Items.BUCKET, beginSlot = slot).hasSucceeded()) {
                    player.inventory.add(result, beginSlot = slot)
                    player.addXp(Skills.FARMING, xp)
                    currentCount--
                    if (currentCount == 0) {
                        varbit.set(0)
                        player.filterableMessage("The compost bin is now empty.")
                    } else {
                        varbit.decreaseByOne()
                    }
                } else {
                    player.message("You need a suitable bucket to do that.")
                    return@queue
                }
            }
        }
    }

    // Move to the next step in the fermentation process
    fun tick() {
        // If we're not fermenting, return
        if (state != ClosedFermentingCompost && state != ClosedFermentingSupercompost) {
            return
        }

        // Move to the next step if we're not yet done fermenting
        if (varbit.value != state.varbits.last) {
            varbit.increaseByOne()
        } else { // If we're done fermenting, update the state to an openable bin
            val newState = if (state == ClosedFermentingCompost) {
                ClosedReadyCompost
            } else {
                ClosedReadySupercompost
            }
            varbit.set(newState.varbits.first)
        }
    }

    companion object {
        private val superCompostable = setOf(
            Items.PINEAPPLE,
            Items.CALQUAT_FRUIT,
            Items.PUNGENT_VINE,
            Items.WATERMELON,
            Items.BITTERCAP_MUSHROOM,
            Items.OAK_ROOTS,
            Items.WILLOW_ROOTS,
            Items.MAPLE_ROOTS,
            Items.YEW_ROOTS,
            Items.MAGIC_ROOTS,
            Items.COCONUT_SHELL,
            Items.PAPAYA_FRUIT,
            Items.JANGERBERRIES,
            Items.WHITE_BERRIES,
            Items.POISON_IVY_BERRIES,
            Items.CLEAN_TOADFLAX,
            Items.CLEAN_AVANTOE,
            Items.CLEAN_KWUARM,
            Items.CLEAN_SNAPDRAGON,
            Items.CLEAN_CADANTINE,
            Items.CLEAN_LANTADYME,
            Items.CLEAN_DWARF_WEED,
            Items.CLEAN_TORSTOL,
            Items.WHITE_TREE_FRUIT,
        )

        // Any farming produce that is not super compostable, is compostable
        private val compostable = (Seed.values().map { it.produce.id } - superCompostable + Items.WEEDS).toSet()

        private const val fillingAnimation = 832
        private const val closingAnimation = 835
        private const val openingAnimation = 834
        private const val emptyingAnimation = 832

        private const val fillingSound = 2441
        private const val closingSound = 2428
        private const val openingSound = 2429
        private const val emptyingSound = 2443
    }
}
