package gg.rsmod.plugins.content.npcs.sheep

val SHEEP = arrayOf(Npcs.SHEEP_1763, Npcs.SHEEP_1765, Npcs.SHEEP_5156, Npcs.SHEEP_5157)

val SHEAR_ANIMATION = 893

SHEEP.forEach { sheep ->
    on_npc_option(npc = sheep, option = "shear") {
        val sheep = player.getInteractingNpc()

        if(!player.hasItem(Items.SHEARS)) {
            player.message("You need shears to shear a sheep.")
            return@on_npc_option
        }

        if(world.definitions.get(NpcDef::class.java, sheep.id).options.contains("attack")) {
            player.message("That one looks a little too violent to shear...")
            return@on_npc_option
        }

        if(player.inventory.isFull) {
            player.message("You don't have enough space in your inventory to carry any wool you would shear.")
            return@on_npc_option
        }

        player.queue {
            sheep.stopMovement()
            player.animate(SHEAR_ANIMATION)
            wait(world.getAnimationDelay(SHEAR_ANIMATION))
            if(world.random(1..10) <= 9) {
                player.inventory.add(Item(Items.WOOL))
                player.filterableMessage("You get some wool.")
                sheep.forceChat("Baa!")
                world.queue {
                    sheep.setTransmogId(Npcs.SHEEP_5153)
                    wait(80)
                    sheep.setTransmogId(sheep.id)
                }
            } else {
                player.filterableMessage("The sheep manages to get away from you!")
                sheep.resetFacePawn()
                val rx = world.random(-sheep.walkRadius..sheep.walkRadius)
                val rz = world.random(-sheep.walkRadius..sheep.walkRadius)
                val start = sheep.spawnTile
                val dest = start.transform(rx, rz)
                if (world.collision.chunks.get(dest, createIfNeeded = false) != null) {
                    sheep.walkTo(dest, detectCollision = true)
                }
            }
        }
    }
}
