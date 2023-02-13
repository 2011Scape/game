package gg.rsmod.plugins.content.mechanics.multi

val MULTIWAY_VARC = 616

on_world_init {
    world.getMultiCombatRegions().forEach { region ->
        on_enter_region(region) {
            player.setVarc(MULTIWAY_VARC, 1)
        }

        on_exit_region(region) {
            player.setVarc(MULTIWAY_VARC, 0)
        }
    }

    world.getMultiCombatChunks().forEach { chunk ->
        on_enter_chunk(chunk) {
            player.setVarc(MULTIWAY_VARC, 1)
        }

        on_exit_chunk(chunk) {
            player.setVarc(MULTIWAY_VARC, 0)
        }
    }
}