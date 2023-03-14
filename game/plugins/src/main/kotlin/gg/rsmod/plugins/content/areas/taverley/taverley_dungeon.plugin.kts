package gg.rsmod.plugins.content.areas.taverley

on_obj_option(obj = Objs.LADDER_55404, option = "climb-down") {
    player.queue {
        player.animate(827)
        wait(world.getAnimationDelay(827))
        player.moveTo(Tile(2884, 9798, 0))
    }
}