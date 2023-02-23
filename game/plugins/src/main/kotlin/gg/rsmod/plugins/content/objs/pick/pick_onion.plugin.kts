package gg.rsmod.plugins.content.objs.pick

val RESPAWN_DELAY = 100

val ids = arrayOf(Objs.ONION_3366)

ids.forEach {
    on_obj_option(obj = it, option = "pick", lineOfSightDistance = 0) {
        val obj = player.getInteractingGameObj()

        if (obj.isSpawned(world)) {
            player.queue {
                val route = player.walkTo(this, obj.tile)
                if (route.success) {
                    if (player.inventory.isFull) {
                        player.message("You don't have room for this onion.")
                        return@queue
                    }
                    player.animate(827)
                    val item = if (world.percentChance(5.0)) Items.ONION_SEED else Items.ONION
                    wait(player.world.definitions.get(AnimDef::class.java, 827).cycleLength)
                    player.inventory.add(item = item)
                    player.playSound(2582)
                    world.remove(obj)
                    world.queue {
                        wait(RESPAWN_DELAY)
                        world.spawn(DynamicObject(obj))
                    }
                } else {
                    player.message(Entity.YOU_CANT_REACH_THAT)
                }
            }
        }
    }
}