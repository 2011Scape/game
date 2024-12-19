package gg.rsmod.plugins.content.mechanics.music

/**
 * @author Alycia <https://github.com/alycii>
 * @author vl1 <https://github.com/vl1>
 * @author Ilwyd <https://github.com/ilwyd>
 */

load_service(RegionMusicService())

on_world_init {
    world.getService(RegionMusicService::class.java)!!.let { service ->
        service.musicTrackList.forEach { music ->
            music.areas.forEach { area ->
                val id = world.definitions.get(EnumDef::class.java, 1351).getInt(music.index)
                val name = world.definitions.get(EnumDef::class.java, 1345).getString(music.index)
                val polygonVertices = mutableListOf<Tile>()
                for (i in area.x.indices) polygonVertices.add(Tile(area.x[i], area.y[i]))
                on_enter_region(regionId = area.region) {
                    player.playSong(id, name)
                    player.unlockSong(player, music.index)
                }
                on_enter_simple_polygon_area(SimplePolygonArea(polygonVertices.toTypedArray())) {
                    player.playSong(id, name)
                    player.unlockSong(player, music.index)
                }
            }
        }
    }
}
