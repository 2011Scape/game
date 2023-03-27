package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.options
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.CompostBinState
import gg.rsmod.plugins.content.skills.farming.data.CompostBinState.*
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.VarbitUpdater

class CompostBinHandler(bin: CompostBin, player: Player) : VarbitUpdater(bin.varbit, player) {

    private var state = CompostBinState.forVarbit(varbitValue)

    fun addCompostable(itemId: Int) {
        if (itemId !in compostable && itemId !in superCompostable) {
            player.message("You cannot compost this item.")
            return
        }
        if (state != Empty && varbitValue == state.varbits.last) {
            player.message("The compost bin is too full to put anything else in it.")
            return
        }
        player.queue {
            var currentCount = when (state) {
                Empty -> 0
                FillingCompost,
                FillingSuperCompost -> 15 - (state.varbits.last - varbitValue)
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
            while (player.inventory.contains(itemId) && currentCount < 15) {
                player.animate(fillingAnimation)
                player.playSound(fillingSound)
                wait(3)
                if (player.inventory.remove(itemId).hasSucceeded()) {
                    currentCount++
                    state = nextState
                    setVarbit(state.varbits.first + currentCount - 1)
                }
            }
        }
    }

    fun open() {
        val newState = when (state) {
            ClosedReadyCompost -> EmptyingCompost
            ClosedReadySupercompost -> EmptyingSuperCompost
            else -> {
                player.message("The vegetation hasn't finished rotting yet.")
                return
            }
        }

        player.queue {
            player.animate(openingAnimation)
            player.playSound(openingSound)
            setVarbit(newState.varbits.last)
            state = newState
            player.message("You open the compost bin.")
        }
    }

    fun close() {
        val newState = when {
            state == FillingCompost && varbitValue == state.varbits.last -> ClosedFermentingCompost
            state == FillingSuperCompost && varbitValue == state.varbits.last -> ClosedFermentingSupercompost
            else -> return
        }

        player.queue {
            player.animate(closingAnimation)
            player.playSound(closingSound)
            setVarbit(newState.varbits.first)
            state = newState
            player.message("You close the compost bin.")
            player.message("The contents have begun to rot.")
        }
    }

    fun empty() {
        if (state != EmptyingCompost && state != EmptyingSuperCompost) {
            player.message("There's no compost to take at the moment.")
            return
        }

        val result = if (state == EmptyingCompost) Items.COMPOST else Items.SUPERCOMPOST
        val xp = if (state == EmptyingCompost) 4.5 else 8.5

        player.queue {
            var currentCount = when (state) {
                EmptyingCompost,
                EmptyingSuperCompost -> 15 - (state.varbits.last - varbitValue)
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
                        setVarbit(0)
                        state = Empty
                        player.message("The compost bin is now empty.")
                    } else {
                        decreaseVarbitByOne()
                    }
                } else {
                    player.message("You need a suitable bucket to do that.")
                    return@queue
                }
            }
        }
    }

    fun tick() {
        if (state != ClosedFermentingCompost && state != ClosedFermentingSupercompost) {
            return
        }
        if (varbitValue != state.varbits.last) {
            increaseVarbitByOne()
        } else {
            state = if (state == ClosedFermentingCompost) {
                ClosedReadyCompost
            } else {
                ClosedReadySupercompost
            }
            setVarbit(state.varbits.first)
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
