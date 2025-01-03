package gg.rsmod.plugins.content.areas.draynor

on_obj_option(obj = Objs.DRAWERS_46250, option = "open") {
    player.queue {
        val obj = player.getInteractingGameObj()
        player.animate(9429)
        world.remove(obj)
        world.spawn(DynamicObject(id = Objs.DRAWERS_12961, type = 10, rot = 3, tile = Tile(3096, 3270, 1)))
    }
}

on_obj_option(obj = Objs.DRAWERS_12961, option = "search") {
    player.queue {
        player.message("You find a bulb of garlic.")
        player.inventory.add(Items.GARLIC, 1)
    }
}

on_obj_option(obj = Objs.DRAWERS_12961, option = "close") {
    player.queue {
        val obj = player.getInteractingGameObj()
        player.animate(9429)
        world.remove(obj)
        world.spawn(DynamicObject(id = Objs.DRAWERS_46250, type = 10, rot = 3, tile = Tile(3096, 3270, 1)))
    }
}
