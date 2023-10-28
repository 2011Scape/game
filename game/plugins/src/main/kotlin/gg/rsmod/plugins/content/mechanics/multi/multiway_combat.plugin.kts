package gg.rsmod.plugins.content.mechanics.multi

val MULTIWAY_VARC = 616

load_service(MultiService())

on_world_init {
    world.getService(MultiService::class.java)!!.let { service ->
        println("Multi Combat Regions: ${service.multiRegions}")
        println("Multi Combat Chunks: ${service.multiChunks}")

        // Handling Regions
        service.multiRegions.forEach { region ->
            set_multi_combat_region(region)
            on_enter_exit_region(region)
        }

        // Handling Chunks
        service.multiChunks.forEach { chunk ->
            print("Chunk: ${chunk}, hash:${chunk.hashCode()} \n")
            set_multi_combat_chunk(chunk.hashCode())
            on_enter_exit_chunk(chunk.hashCode())
        }
    }
}

fun on_enter_exit_region(region: Int) {
    on_enter_region(region) {
        player.setVarc(MULTIWAY_VARC, 1)
        println("${player.username} entered multi area (Region).")
    }

    on_exit_region(region) {
        player.setVarc(MULTIWAY_VARC, 0)
        println("${player.username} exited multi area (Region).")
    }
}

fun on_enter_exit_chunk(chunk: Int) {
    // Define behavior for entering and exiting chunks if different from regions
    // This is just a placeholder and should be replaced with actual chunk handling code
    on_enter_chunk(chunk) {
        player.setVarc(MULTIWAY_VARC, 1) // Or some other relevant code
        println("${player.username} entered multi area (Chunk).")
    }

    on_exit_chunk(chunk) {
        player.setVarc(MULTIWAY_VARC, 0) // Or some other relevant code
        println("${player.username} exited multi area (Chunk).")
    }
}

