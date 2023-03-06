package gg.rsmod.plugins.content.mechanics.music

/**
 * @author Alycia <https://github.com/alycii>
 * @author vl1 <https://github.com/vl1>
 */

load_service(RegionMusicService())

on_world_init {
    world.getService(RegionMusicService::class.java)!!.let { service ->
        service.musicTrackList.forEach { music ->
            music.areas.forEach { area ->
                on_enter_region(regionId = area.region) {
                    val id = world.definitions.get(EnumDef::class.java, 1351).getInt(music.index)
                    val name = world.definitions.get(EnumDef::class.java, 1345).getString(music.index)
                    player.playSong(id, name)
                }
            }
        }
    }
}