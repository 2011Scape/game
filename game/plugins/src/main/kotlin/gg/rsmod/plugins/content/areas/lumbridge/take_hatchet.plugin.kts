package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(obj = Objs.LOGS_36974, option = "take-hatchet") {
    val obj = player.getInteractingGameObj()
    val replacementObject = DynamicObject(obj, Objs.LOGS_36975)
    val world = player.world
    if (obj.isSpawned(world)) {
        if (player.inventory.hasSpace) {
            player.inventory.add(Item(Items.BRONZE_HATCHET, 1))
            world.queue {
                world.remove(obj)
                world.spawn(replacementObject)
                wait(100)
                world.remove(replacementObject)
                world.spawn(obj)
            }
        } else {
            player.filterableMessage("You don't have enough free inventory space to take that.")
        }
    }
}