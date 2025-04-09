package gg.rsmod.plugins.content.objs

val closedDrawersIds = listOf(
    Objs.DRAWERS_37011, Objs.DRAWERS_45243, // Lumbridge
    Objs.DRAWERS_2631, Objs.DRAWERS_46250   // Draynor
)
val openDrawerIds = listOf(
    Objs.DRAWERS_37012, Objs.DRAWERS_45244, // Lumbridge
    Objs.DRAWERS_2651, Objs.DRAWERS_12961   // Draynor
)
val lockedDrawers = listOf(
    Tile(3227, 3201), Tile(3227, 3204), Tile(3232, 3209), // Lumbridge
    Tile(3097, 3260),                                                 // Draynor
)
val openAndSearch = listOf(
    Tile(3096, 3267, 1), Tile(3096, 3268), Tile(3097, 3277), Tile(3099, 3277, 1),
    Tile(3103, 3281, 1), Tile(3094, 3285, 1)// Draynor
)

closedDrawersIds.forEachIndexed { index, id ->
    on_obj_option(id, "Open") {
        val interacting = player.getInteractingGameObj()
        if (lockedDrawers.any { tile -> tile.x == interacting.tile.x && tile.z == interacting.tile.z }) {
            player.queue {
                chatPlayer(*"Locked! What could be so important that it needs to be locked away?".splitForDialogue(),
                    facialExpression = FacialExpression.CONFUSED)
            }
        }
        else {
            player.lockingQueue(lockState = LockState.FULL) {
                player.animate(Anims.REACH_FORWARD)
                wait(2)
                world.spawn(DynamicObject(openDrawerIds[index], interacting.type, interacting.rot, interacting.tile))
                when  {
                    interacting.tile in openAndSearch -> player.message("You search the drawers but find nothing.")
                }
            }
        }
    }
}

openDrawerIds.forEachIndexed { index, id ->
    on_obj_option(id, "Close") {
        val interacting = player.getInteractingGameObj()
        player.lockingQueue(lockState = LockState.FULL) {
            player.animate(Anims.REACH_FORWARD)
            wait(2)
            world.spawn(DynamicObject(closedDrawersIds[index], interacting.type, interacting.rot, interacting.tile))
        }
    }

    on_obj_option(id, "Search") {
        val interacting = player.getInteractingGameObj()
        when {
            interacting.tile in openAndSearch -> player.message("The drawers are empty.")
            interacting.id == Objs.DRAWERS_12961 -> {
                player.queue {
                    player.message("You find a bulb of garlic.")
                    val gave = player.inventory.add(Items.GARLIC, 1)
                    if (!gave.hasSucceeded()) {
                        world.spawn(GroundItem(Items.GARLIC, 1, player.tile, player))
                    }
                }
            }
            else -> player.message("Nothing terribly interesting in here.")
        }
    }
}
