package gg.rsmod.plugins.content.areas.lumbridge

val TAKEN_VARBIT = 9010

on_obj_option(obj = 15463, option = "take") {
    if(player.getVarbit(TAKEN_VARBIT) == 0) {
        player.inventory.add(Item(Items.COINS_995, 25))
        player.setVarbit(TAKEN_VARBIT, 1)
    }
}
