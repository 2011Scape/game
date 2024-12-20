package gg.rsmod.plugins.content.mechanics.music

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR

/**
 * @author Alycia <https://github.com/alycii>
 * @author vl1 <https://github.com/vl1>
 * @author Ilwyd <https://github.com/ilwyd>
 */

load_service(RegionMusicService())

val PLAY_SONG = 3
val ADD_TO_PLAYLIST = 4
val REMOVE_FROM_PLAYLIST = 5

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

on_button(187, 1) {
    val trackIndex = player.getInteractingSlot() / 2
    val option = player.getInteractingOption()
    val trackId = world.definitions.get(EnumDef::class.java, 1351).getInt(trackIndex)
    val trackName = world.definitions.get(EnumDef::class.java, 1345).getString(trackIndex)

    if (option == PLAY_SONG) player.playSong(trackId, trackName)
}

on_button(187, 9) {
    val trackSlot = player.getInteractingSlot()
    val option = player.getInteractingOption()
    val trackIndex = player.getVarbit(7081 + trackSlot)
    val trackId = world.definitions.get(EnumDef::class.java, 1351).getInt(trackIndex)
    val trackName = world.definitions.get(EnumDef::class.java, 1345).getString(trackIndex)

    if (option == PLAY_SONG) player.playSong(trackId, trackName)
}

on_component_to_component_item_swap(187, 9, 187, 9) {
    val fromSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    var toSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    // Converting the toSlot number to align with the button slots
    if (toSlot <= -16) {
        toSlot += 16
    }
    toSlot += 12

    player.message("Swapping slot $fromSlot and $toSlot")

    val trackFrom = player.getVarbit(7081 + fromSlot)
    val trackTo = player.getVarbit(7081 + toSlot)

    player.setVarbit(7081 + toSlot, trackFrom)
    player.setVarbit(7081 + fromSlot, trackTo)
}

on_login {
    // Enabling clicking music in main tab
    player.setEvents(interfaceId = 187, component = 1, to = 1968, setting = 30)

    // Enabling clicking music in the playlist tab
    player.setEvents(interfaceId = 187, component = 9, to = 23, setting = 0x24001E)

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
