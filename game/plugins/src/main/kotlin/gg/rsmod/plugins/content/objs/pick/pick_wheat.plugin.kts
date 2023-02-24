package gg.rsmod.plugins.content.objs.pick

val RESPAWN_DELAY = 20

val wheatObjects = arrayOf(Objs.WHEAT, Objs.WHEAT_15506, Objs.WHEAT_15507, Objs.WHEAT_15508, Objs.WHEAT_22300)

wheatObjects.forEach { wheat ->
    on_obj_option(obj = wheat, option = "pick") {
        val obj = player.getInteractingGameObj()

        if (obj.isSpawned(world)) {
            player.queue {
                if (player.inventory.isFull) {
                    player.message("You don't have room for this grain.")
                    return@queue
                }
                player.animate(827)
                val item = Items.GRAIN
                wait(player.world.definitions.get(AnimDef::class.java, 827).cycleLength)
                player.inventory.add(item = item)
                player.playSound(2582)
                world.remove(obj)
                world.queue {
                    wait(RESPAWN_DELAY)
                    world.spawn(DynamicObject(obj))
                }
            }
        }
    }
}