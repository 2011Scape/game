package gg.rsmod.plugins.content.mechanics.music

/**
 * @author Alycia <https://github.com/alycii>
 */


load_service(RegionMusicService())

on_world_init {
    world.getService(RegionMusicService::class.java)!!.let { service ->
        service.music.forEach { music ->
            on_enter_region(regionId = music.regionID) {

                player.playSong(music.musicID)
            }
        }
    }
}