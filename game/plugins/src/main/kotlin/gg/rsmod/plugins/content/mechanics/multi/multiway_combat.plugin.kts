package gg.rsmod.plugins.content.mechanics.multi

val MULTIWAY_VARC = 616

load_service(MultiService())

on_world_init {
    world.getService(MultiService::class.java)!!.let { service ->
        // Handling Regions
        service.multiRegions.forEach { region ->
            set_multi_combat_region(region)
            on_enter_exit_region(region)
        }
        // Handling Chunks
        service.multiChunks.forEach { chunk ->
            set_multi_combat_chunk(chunk)
            on_enter_exit_chunk(chunk)
        }
    }
}

fun on_enter_exit_region(region: Int) {
    on_enter_region(region) {
        player.setVarc(MULTIWAY_VARC, 1)
    }

    on_exit_region(region) {
        player.setVarc(MULTIWAY_VARC, 0)
    }
}

fun on_enter_exit_chunk(chunk: Int) {
    on_enter_chunk(chunk) {
        player.setVarc(MULTIWAY_VARC, 1)
    }

    on_exit_chunk(chunk) {
        player.setVarc(MULTIWAY_VARC, 0)
    }
}
