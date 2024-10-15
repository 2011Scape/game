package gg.rsmod.plugins.content.areas.lumbridge

/**
 * @author Alycia <https://github.com/alycii>
 */

val TRAPDOOR_VARBIT = 235

on_obj_option(obj = Objs.TRAPDOOR_5490, option = "open") {
    player.setVarbit(TRAPDOOR_VARBIT, 1)
}

on_obj_option(obj = Objs.TRAPDOOR_5491, option = "climb-down") {
    player.queue {
        player.animate(827)
        wait(world.getAnimationDelay(827))
        player.moveTo(Tile(3149, 9652, 0))
    }
}

on_obj_option(obj = Objs.LADDER_5493, option = "climb-up") {
    player.queue {
        player.animate(828)
        wait(world.getAnimationDelay(828))
        player.moveTo(Tile(3165, 3251, 0))
    }
}

on_obj_option(obj = Objs.TRAPDOOR_5491, option = "close") {
    player.setVarbit(TRAPDOOR_VARBIT, 0)
}
