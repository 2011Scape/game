package gg.rsmod.plugins.content.areas.gnomestronghold

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.GATE_190, option = "open") {
    player.queue {
        player.stopMovement()
        val closedDoor = DynamicObject(id = Objs.GATE_190, type = 10, rot = 2, tile = Tile(x = 2459, z = 3383))
        val secondDoor = DynamicObject(id = 191, type = 10, rot = 2, tile = Tile(x = 2459, z = 3383))
        val thirdDoor = DynamicObject(id = 192, type = 10, rot = 0, tile = Tile(x = 2462, z = 3383))

        world.remove(closedDoor)
        world.spawn(secondDoor)
        world.spawn(thirdDoor)
    }
}
