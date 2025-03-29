package gg.rsmod.plugins.content.areas.lumbridge

val closedDrawersIds = listOf(Objs.DRAWERS_37011, Objs.DRAWERS_45243)
val openDrawerIds = listOf(Objs.DRAWERS_37012, Objs.DRAWERS_45244)
val lockedDrawers = listOf(Tile(3227, 3201), Tile(3227, 3204), Tile(3232, 3209))

closedDrawersIds.forEach { id ->
    on_obj_option(id, "Open") {
        val interacting = player.getInteractingGameObj()
        if (lockedDrawers.any { tile -> tile.x == interacting.tile.x && tile.z == interacting.tile.z }) {
            // These are some drawers which are locked
            player.queue {
                chatPlayer(*"Locked! What could be so important that it needs to be locked away?".splitForDialogue(),
                    facialExpression = FacialExpression.CONFUSED)
            }
        }
        else {
            player.lockingQueue(lockState = LockState.FULL) {
                player.animate(535)
                val interacting = player.getInteractingGameObj()
                world.spawn(DynamicObject(
                    id + 1,
                    interacting.type,
                    interacting.rot,
                    interacting.tile
                ))
                wait(2)
            }
        }
    }
}

openDrawerIds.forEach { id ->
    on_obj_option(id, "Close") {
        player.lockingQueue(lockState = LockState.FULL) {
            player.animate(535)
            val interacting = player.getInteractingGameObj()
            world.spawn(DynamicObject(
                id - 1,
                interacting.type,
                interacting.rot,
                interacting.tile
            ))
            wait(2)
        }
    }

    on_obj_option(id, "Search") {
        player.message("Nothing terribly interesting in here.")
    }
}
