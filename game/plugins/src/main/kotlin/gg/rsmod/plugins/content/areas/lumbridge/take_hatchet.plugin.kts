package gg.rsmod.plugins.content.areas.lumbridge

val RESPAWN_DELAY = 100

on_obj_option(obj = Objs.LOGS_36974, option = "take-hatchet") {
    val obj = player.getInteractingGameObj()
    val replacementObject = DynamicObject(obj, Objs.LOGS_36975)
    val world = player.world
    if (obj.isSpawned(world)) {
        if (player.inventory.hasSpace) {
            world.remove(obj)
            world.queue {
                world.spawn(replacementObject)
                wait(RESPAWN_DELAY)
                world.remove(replacementObject)
                world.spawn(DynamicObject(obj))
            }
            player.playSound(Sfx.PICK2)
            player.inventory.add(Item(Items.BRONZE_HATCHET, 1))
        } else {
            player.filterableMessage("You don't have enough free inventory space to take that.")
        }
    }
}
