package gg.rsmod.plugins.content.areas.wilderness

//Entrance Coperal Beast Lair
on_obj_option(obj = Objs.ENTRANCE_38815, option = 1) {//TO-DO: this wont work, i dont know why... this need be fixt for releasing corp.
    player.moveTo(x = 2885, z = 4372, height = 2)
}
//Exit Coperal Beast Lair
on_obj_option(obj = 37928, option = 1) {
    player.moveTo(x = 3214, z = 3782, height = 0)
}