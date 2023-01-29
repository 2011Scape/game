package gg.rsmod.plugins.content.skills.fishing

FishingSpot.values().forEach { spot ->
    spot.objectIds.forEach { spotId ->
        spot.tools.forEach { tool ->
            on_npc_option(spotId, tool.option) {
                val fishingSpot = player.getInteractingNpc()
                player.queue {
                    Fishing.fish(this, fishingSpot, tool)
                }
            }
        }
    }
}
