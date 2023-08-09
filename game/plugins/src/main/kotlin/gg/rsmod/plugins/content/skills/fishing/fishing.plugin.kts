package gg.rsmod.plugins.content.skills.fishing


FishingSpot.values().forEach { spot ->
    spot.objectIds.forEach { spotId ->
        spot.tools.forEach { tool ->
            on_npc_option(spotId, tool.option) {
                val fishingSpot = player.getInteractingNpc()
                player.queue {
                    var attempts = 0
                    while (player.tile.getDistance(fishingSpot.tile) > 1) {
                        if (attempts++ >= 10) {
                            player.message(Entity.YOU_CANT_REACH_THAT)
                            return@queue
                        }
                        wait(1) // This should be an appropriate delay function in your context
                    }
                    Fishing.fish(this, fishingSpot, tool)
                }
            }
        }
    }
}