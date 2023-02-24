package gg.rsmod.plugins.content.areas.lumbridge

val RESPAWN_DELAY = 100

on_obj_option(obj = Objs.LOGS_36974, option = "take-hatchet") {
    val obj = player.getInteractingGameObj()
    val replacementObject = DynamicObject(obj, Objs.LOGS_36975)
    val defaultObject = DynamicObject(obj)
    val world = player.world
    if (obj.isSpawned(world)) {
        if (player.inventory.hasSpace) {
            player.playSound(2582)
            player.inventory.add(Item(Items.BRONZE_HATCHET, 1))
            world.remove(defaultObject)
            world.queue {
                world.spawn(replacementObject)
                wait(RESPAWN_DELAY)
                world.remove(replacementObject)
                world.spawn(defaultObject)
            }
        } else {
            player.filterableMessage("You don't have enough free inventory space to take that.")
        }
    }
}