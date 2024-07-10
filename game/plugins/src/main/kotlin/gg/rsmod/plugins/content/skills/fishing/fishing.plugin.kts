package gg.rsmod.plugins.content.skills.fishing


FishingSpot.values().forEach { spot ->
    spot.objectIds.forEach { spotId ->
        spot.tools.forEach { tool ->
            on_npc_option(spotId, tool.option) {
                val fishingSpot = player.getInteractingNpc()
                player.queue {
                    var attempts = 0
                    // If the spot is a Rocktail shoal, check all four tiles
                    val isRocktailShoal = fishingSpot.id == Npcs.ROCKTAIL_SHOAL
                    val tilesToCheck =
                        if (isRocktailShoal) {
                            listOf(
                                fishingSpot.tile, // Assuming this is the bottom-left tile
                                fishingSpot.tile.transform(1, 0), // Bottom-right tile
                                fishingSpot.tile.transform(0, 1), // Top-left tile
                                fishingSpot.tile.transform(1, 1), // Top-right tile
                            )
                        } else {
                            listOf(fishingSpot.tile) // For other spots, just check the NPC's tile
                        }

                    while (tilesToCheck.none { player.tile.getDistance(it) <= 1 }) {
                        if (attempts++ >= 10) {
                            player.message(Entity.YOU_CANT_REACH_THAT)
                            return@queue
                        }
                        wait(1)
                    }
                    Fishing.fish(this, fishingSpot, tool)
                }
            }
        }
    }
}
