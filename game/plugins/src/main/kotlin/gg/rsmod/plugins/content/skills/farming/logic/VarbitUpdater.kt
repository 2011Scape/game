package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit

/**
 * Provides some helper functions to manipulate a varbit
 */
class VarbitUpdater(private val varbit: Int, private val player: Player) {

    fun set(newValue: Int) {
        player.setVarbit(varbit, newValue)
    }

    fun update(delta: Int) {
        player.setVarbit(varbit, player.getVarbit(varbit) + delta)
    }

    fun increaseByOne() {
        update(1)
    }

    fun decreaseByOne() {
        update(-1)
    }

    val value get() = player.getVarbit(varbit)
}
