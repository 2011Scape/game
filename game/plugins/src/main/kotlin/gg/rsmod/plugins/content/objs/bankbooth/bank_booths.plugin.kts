package gg.rsmod.plugins.content.objs.bankbooth

import gg.rsmod.plugins.content.inter.bank.openBank

private val BOOTHS = setOf(Objs.BANK_BOOTH, Objs.BANK_BOOTH_10517, Objs.BANK_BOOTH_11338, Objs.BANK_BOOTH_25808, Objs.BANK_BOOTH_36786, Objs.COUNTER_2019)

BOOTHS.forEach { booth ->

    on_obj_option(obj = booth, option = "use") {
        player.openBank()
    }

    on_obj_option(obj = booth, option = "use-quickly") {
        player.openBank()
    }
}
