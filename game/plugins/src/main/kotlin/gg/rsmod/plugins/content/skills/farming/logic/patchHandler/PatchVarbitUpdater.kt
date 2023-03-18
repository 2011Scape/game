package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.skills.farming.data.Patch

abstract class PatchVarbitUpdater(protected val patch: Patch, protected val player: Player) {

    protected fun setVarbit(newValue: Int) {
        player.setVarbit(patch.varbit, newValue)
    }

    protected fun updateVarbit(delta: Int) {
        player.setVarbit(patch.varbit, player.getVarbit(patch.varbit) + delta)
    }

    protected fun increaseVarbitByOne() {
        updateVarbit(1)
    }

    protected fun decreaseVarbitByOne() {
        updateVarbit(-1)
    }

    protected val varbitValue get() = player.getVarbit(patch.varbit)
}
