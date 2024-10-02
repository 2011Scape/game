package gg.rsmod.plugins.content.areas.karamja

/**
 * @author Alycia <https://github.com/alycii>
 */

// Rocks on Karamja Volcano
on_obj_option(obj = Objs.ROCKS_492, option = "climb-down") {
    player.handleLadder(x = 2856, z = 9568)
}

// Climbing rope in Karamja Dungeon
on_obj_option(obj = Objs.CLIMBING_ROPE_1764, option = "climb") {
    player.handleLadder(x = 2856, z = 3167)
}

// TzHaar entrance from Karamja side
on_obj_option(obj = Objs.CAVE_ENTRANCE_31284, option = "enter") {
    player.moveTo(x = 2480, z = 5175)
}

// TzHaar exit to Karamja side
on_obj_option(obj = Objs.CAVE_EXIT_9359, option = "enter") {
    player.moveTo(x = 2866, z = 9571)
}
