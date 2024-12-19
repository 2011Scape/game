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
                    player.unlockSong(music.index)
                }

                if (polygonVertices.size != 0) {
                    on_enter_simple_polygon_area(SimplePolygonArea(polygonVertices.toTypedArray())) {
                        player.playSong(id, name)
                        player.unlockSong(music.index)
                    }
                }
            }
        }
    }
}

on_login {
    val defaultTracks = // Taken from https://runescape.wiki/w/List_of_music_tracks
        arrayOf(
            0,
            5,
            17,
            23,
            35,
            47,
            48,
            52,
            63,
            84,
            89,
            90,
            93,
            103,
            105,
            106,
            110,
            114,
            131,
            143,
            145,
            146,
            150,
            151,
            152,
            153,
            159,
            160,
            161,
            163,
            165,
            166,
            170,
            175,
            185,
            196,
            200,
            257,
            316,
            318,
            321,
            323,
            336,
            340,
            341,
            350,
            360,
            361,
            377,
            411,
            412,
            479,
            482,
            514,
            517,
            518,
            519,
            520,
            555,
            602,
            611,
            650,
            717,
            931,
        )

    defaultTracks.forEach {
        player.unlockSong(it, false)
    }
}
