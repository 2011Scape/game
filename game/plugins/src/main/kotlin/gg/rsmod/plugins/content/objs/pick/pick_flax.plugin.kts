package gg.rsmod.plugins.content.objs.pick

val RESPAWN_DELAY = 100

on_obj_option(obj = Objs.FLAX_2646, option = "pick", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()

    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        if (player.inventory.isFull) {
            player.message("You don't have room for this flax.")
            return@on_obj_option
        }
        player.animate(827)
        val item = Items.FLAX
        player.inventory.add(item = item)
        player.playSound(2582)
        if (world.percentChance(25.0)) {
            world.remove(obj)
            world.queue {
                wait(RESPAWN_DELAY)
                world.spawn(DynamicObject(obj))
            }
        }
    }
}