package gg.rsmod.plugins.content.objs.mill

/**
 * @author Alycia <https://github.com/alycii>
 */

val FULL_BIN_VARP = 695
val HOPPER_VARP = 694

on_obj_option(obj = Objs.HOPPER_CONTROLS, option = "Operate") {
    if (player.getVarp(HOPPER_VARP) == 0) {
        player.queue {
            player.animate(2572)
            wait(world.getAnimationDelay(2572))
            player.message("You operate the empty hopper. Nothing interesting happens.")
        }
        return@on_obj_option
    }
    player.queue {
        player.animate(2572)
        wait(world.getAnimationDelay(2572))
        player.message("You operate the hopper. The grain slides down the chute.")
        player.setVarp(FULL_BIN_VARP, player.getVarp(HOPPER_VARP))
        player.setVarp(HOPPER_VARP, 0)
    }
}
