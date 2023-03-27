package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.skills.farming.data.Patch

/**
 * Provides some helper functions to manipulate a varbit
 */
abstract class VarbitUpdater(protected val varbit: Int, protected val player: Player) {

    protected fun setVarbit(newValue: Int) {
        player.setVarbit(varbit, newValue)
    }

    protected fun updateVarbit(delta: Int) {
        player.setVarbit(varbit, player.getVarbit(varbit) + delta)
    }

    protected fun increaseVarbitByOne() {
        updateVarbit(1)
    }

    protected fun decreaseVarbitByOne() {
        updateVarbit(-1)
    }

    protected val varbitValue get() = player.getVarbit(varbit)
}
