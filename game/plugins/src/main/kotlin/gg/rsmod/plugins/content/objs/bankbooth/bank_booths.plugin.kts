package gg.rsmod.plugins.content.objs.bankbooth

import gg.rsmod.plugins.content.inter.bank.openBank

private val BOOTHS = setOf(Objs.BANK_BOOTH, Objs.BANK_BOOTH_10517, Objs.BANK_BOOTH_11338,
    Objs.BANK_BOOTH_25808, Objs.BANK_BOOTH_36786, Objs.COUNTER_2019, Objs.COUNTER_2015, Objs.COUNTER_2012,
    Objs.BANK_BOOTH_35647, Objs.BANK_BOOTH_11758)

private val BANK_CHESTS_USE = setOf(Objs.BANK_CHEST, Objs.BANK_CHEST_8981, Objs.BANK_CHEST_20607, Objs.BANK_CHEST_21301, Objs.BANK_CHEST_42192, Objs.BANK_CHEST_57437)

private val BANK_CHESTS_BANK = setOf(Objs.BANK_CHEST_27663)

BOOTHS.forEach { booth ->

    on_obj_option(obj = booth, option = "use") {
        player.openBank()
    }

    on_obj_option(obj = booth, option = "use-quickly") {
        player.openBank()
    }
}

BANK_CHESTS_USE.forEach { chest ->
    on_obj_option(obj = chest, "use") {
        player.openBank()
    }
}

BANK_CHESTS_BANK.forEach { chest ->
    on_obj_option(obj = chest, "bank") {
        player.openBank()
    }
}