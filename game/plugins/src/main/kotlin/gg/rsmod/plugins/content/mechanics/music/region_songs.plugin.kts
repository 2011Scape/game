package gg.rsmod.plugins.content.mechanics.music

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.LAST_SONG_END
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR

/**
 * @author Alycia <https://github.com/alycii>
 * @author vl1 <https://github.com/vl1>
 * @author Ilwyd <https://github.com/ilwyd>
 */

load_service(RegionMusicService())

val REMOVE_FROM_PLAYLIST_2 = 2
val PLAY_SONG = 3
val ADD_TO_PLAYLIST = 4
val REMOVE_FROM_PLAYLIST_5 = 5

val PLAYLIST_VARBITS = 7081..7092
val PLAYLIST_ENABLED = 7078
val PLAYLIST_SHUFFLE_ENABLED = 7079

on_world_init {
    world.getService(RegionMusicService::class.java)!!.let { service ->
        service.musicTrackList.forEach { music ->
            music.areas.forEach { area ->
                val id = getTrackIdFromIndex(music.index)
                val name = getTrackNameFromIndex(music.index)
                val polygonVertices = mutableListOf<Tile>()
                for (i in area.x.indices) polygonVertices.add(Tile(area.x[i], area.y[i]))
                on_enter_region(regionId = area.region) {
                    player.unlockSong(music.index)
                    val playlistEnabled = player.getVarbit(PLAYLIST_ENABLED) == 1
                    if (playlistEnabled) return@on_enter_region

                    player.playSong(id, name)
                }

                if (polygonVertices.size != 0) {
                    on_enter_simple_polygon_area(SimplePolygonArea(polygonVertices.toTypedArray())) {
                        player.unlockSong(music.index)
                        val playlistEnabled = player.getVarbit(PLAYLIST_ENABLED) == 1
                        if (playlistEnabled) return@on_enter_simple_polygon_area

                        player.playSong(id, name)
                    }
                }
            }
        }
    }
}

on_button(187, 1) {
    val slot = player.getInteractingSlot()
    val trackIndex = slot / 2
    val option = player.getInteractingOption()
    val trackId = getTrackIdFromIndex(trackIndex)
    val trackName = getTrackNameFromIndex(trackIndex)

    when (option) {
        PLAY_SONG -> player.playSong(trackId, trackName)
        ADD_TO_PLAYLIST -> player.addSongToPlaylist(slot)
        REMOVE_FROM_PLAYLIST_5 -> player.removeSongFromPlaylist(slot, true)
    }
}

on_button(187, 9) {
    val trackSlot = player.getInteractingSlot()
    val option = player.getInteractingOption()
    val trackIndex = player.getVarbit(PLAYLIST_VARBITS.first + trackSlot)
    val trackId = getTrackIdFromIndex(trackIndex)
    val trackName = getTrackNameFromIndex(trackIndex)

    when (option) {
        PLAY_SONG -> player.playSong(trackId, trackName)
        REMOVE_FROM_PLAYLIST_2 -> player.removeSongFromPlaylist(trackSlot)
    }
}

on_button(187, 11) {
    player.clearPlaylist()
}

on_button(187, 10) {
    player.togglePlaylist()
}

on_button(187, 13) {
    player.togglePlaylistShuffle()
}

on_component_to_component_item_swap(187, 9, 187, 9) {
    val fromSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    var toSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    // Converting the toSlot number to align with the button slots
    if (toSlot <= -16) {
        toSlot += 16
    }
    toSlot += 12

    val trackFrom = player.getVarbit(PLAYLIST_VARBITS.first + fromSlot)
    val trackTo = player.getVarbit(PLAYLIST_VARBITS.first + toSlot)

    player.setVarbit(PLAYLIST_VARBITS.first + toSlot, trackFrom)
    player.setVarbit(PLAYLIST_VARBITS.first + fromSlot, trackTo)
}

on_song_end {
    val finishedSongId = player.attr[LAST_SONG_END]!!
    val finishedSongIndex = getTrackIndexFromId(finishedSongId)
    val playlistEnabled = player.getVarbit(PLAYLIST_ENABLED) == 1
    val shuffleEnabled = player.getVarbit(PLAYLIST_SHUFFLE_ENABLED) == 1

    val songIndex: Int
    val playlistSongs = PLAYLIST_VARBITS.filter { player.getVarbit(it) != 32767 }.map { player.getVarbit(it) }

    if (playlistEnabled && shuffleEnabled) {
        // If the playlist is enabled and shuffle is enabled, play a random song from the playlist
        songIndex = playlistSongs[world.random(playlistSongs.size - 1)]
    } else if (playlistEnabled) {
        // If the playlist is enabled, but shuffle is not, play the next song in the list
        songIndex = playlistSongs[(playlistSongs.indexOf(finishedSongIndex) + 1) % 12]
    } else {
        // If the playlist is off, find out if we're in any region / area that has any associated songs and play a
        // random one
        val musicTracks = world.getService(RegionMusicService::class.java)?.musicTrackList
        val songsToChoose = mutableListOf<RegionMusicService.MusicTrack>()
        musicTracks?.forEach { musicTrack ->
            musicTrack.areas.forEach {
                val vertices = (it.x zip it.y).map { pos -> Tile(pos.first, pos.second) }
                val playerInRegion = it.region == player.tile.regionId
                val playerInPolygonArea =
                    vertices.isNotEmpty() && SimplePolygonArea(vertices.toTypedArray()).containsTile(player.tile)
                if (playerInRegion || playerInPolygonArea) {
                    songsToChoose.add(musicTrack)
                }
            }
        }

        if (songsToChoose.size == 0) return@on_song_end

        songIndex = songsToChoose[world.random(songsToChoose.size - 1)].index
    }

    val songId = getTrackIdFromIndex(songIndex)
    val songName = getTrackNameFromIndex(songIndex)
    player.playSong(songId, songName)
}

/**
 * When the player logs in we need to set the interface events for the main music tab and the playlist tab.
 * Setting is larger for the playlist tab because we need to enable dragging the playlist items.
 */
on_login {
    // Enabling clicking music in main tab
    player.setEvents(interfaceId = 187, component = 1, to = 2030, setting = 30)

    // Enabling clicking music in the playlist tab
    player.setEvents(interfaceId = 187, component = 9, to = 23, setting = 0x24001E)
}

/**
 * When the play logs in we need to unlock all of the default songs if they haven't already been
 */
on_login {
    val defaultTracks = // Taken from https://runescape.wiki/w/List_of_music_tracks
        arrayOf(
            0,
            5,
            9,
            17,
            23,
            35,
            47,
            48,
            52,
            60,
            63,
            84,
            89,
            90,
            93,
            103,
            105,
            106,
            110,
            113,
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
            188,
            190,
            191,
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

fun getTrackIdFromIndex(songIndex: Int): Int {
    return world.definitions.get(EnumDef::class.java, 1351).getInt(songIndex)
}

fun getTrackNameFromIndex(songIndex: Int): String {
    return world.definitions.get(EnumDef::class.java, 1345).getString(songIndex)
}

fun getTrackIndexFromId(songId: Int): Int {
    return world.definitions.get(EnumDef::class.java, 1351).getKeyForValue(songId)
}
