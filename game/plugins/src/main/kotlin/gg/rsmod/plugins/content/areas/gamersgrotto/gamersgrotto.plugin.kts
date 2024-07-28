package gg.rsmod.plugins.content.areas.gamersgrotto
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

//Cave Exit
on_obj_option(obj = Objs.CAVE_EXIT_20604, option = "Leave") {
            player.handleStairs(x = 3018, z = 3404, 0)
}
//Cave Entrance
on_obj_option(obj = Objs.CAVE_ENTRANCE_20602, option = "Enter") {
            player.handleStairs(x = 2954, z = 9675, 0)
}
//Portals
//First of guthix
on_obj_option(obj = Objs.PORTAL_20608, option = "Enter") {
    player.moveTo(1677, 5599, 0)
}
//FFA Safe
on_obj_option(obj = Objs.FREEFORALL_SAFE, option = "Enter") {
    player.moveTo(2815, 5511, 0)
}
//FFA Danger
on_obj_option(obj = Objs.FREEFORALL_DANGEROUS, option = "Enter") {
    player.moveTo(3007, 5511, 0)
}



