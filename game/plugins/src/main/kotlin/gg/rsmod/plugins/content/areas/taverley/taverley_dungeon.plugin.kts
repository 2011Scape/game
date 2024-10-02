package gg.rsmod.plugins.content.areas.taverley

on_obj_option(obj = Objs.LADDER_55404, option = "climb-down") { // handles the ladder south of taverley leading to the dungeon
    player.queue {
        player.animate(827) // animation id for climbing down ladder
        wait(world.getAnimationDelay(827))
        player.moveTo(Tile(2884, 9798, 0)) // sets the x, z, height for player destination
    }
}
