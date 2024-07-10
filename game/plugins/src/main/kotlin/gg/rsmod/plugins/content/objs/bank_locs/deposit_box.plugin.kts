package gg.rsmod.plugins.content.objs.bank_locs

import gg.rsmod.plugins.content.inter.bank.openDepositBox

/**
 * @author Alycia <https://github.com/alycii>
 */

private val depositBoxes =
    listOf(
        Objs.DEPOSIT_BOX,
        Objs.DEPOSIT_BOX_2132,
        Objs.DEPOSIT_BOX_15985,
        Objs.DEPOSIT_BOX_24995,
        Objs.DEPOSIT_BOX_32924,
        Objs.BANK_DEPOSIT_BOX_6836,
        Objs.BANK_DEPOSIT_BOX_9398,
        Objs.BANK_DEPOSIT_BOX,
        Objs.BANK_DEPOSIT_BOX_20228,
        Objs.BANK_DEPOSIT_BOX_25937,
        Objs.BANK_DEPOSIT_BOX_26969,
        Objs.BANK_DEPOSIT_BOX_32930,
        Objs.BANK_DEPOSIT_BOX_32931,
        Objs.BANK_DEPOSIT_BOX_34755,
        Objs.BANK_DEPOSIT_BOX_36788,
        Objs.BANK_DEPOSIT_BOX_39830,
    )

depositBoxes.forEach {
    on_obj_option(obj = it, option = "deposit") {
        player.openDepositBox()
    }
}
