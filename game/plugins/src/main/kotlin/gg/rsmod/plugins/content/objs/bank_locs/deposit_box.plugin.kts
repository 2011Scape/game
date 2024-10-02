package gg.rsmod.plugins.content.objs.bank_locs

import gg.rsmod.plugins.content.inter.bank.openDepositBox

/**
 * @author Alycia <https://github.com/alycii>
 */

private val depositBoxes = listOf(Objs.DEPOSIT_BOX, Objs.DEPOSIT_BOX_2132)

depositBoxes.forEach {
    on_obj_option(obj = it, option = "deposit") {
        player.openDepositBox()
    }
}
