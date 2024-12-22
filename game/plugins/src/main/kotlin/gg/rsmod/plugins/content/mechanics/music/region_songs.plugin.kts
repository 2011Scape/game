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

on_world_init {
    world.getService(RegionMusicService::class.java)!!.let { service ->
        service.musicTrackList.forEach { music ->
            music.areas.forEach { area ->
                val id = world.definitions.get(EnumDef::class.java, 1351).getInt(music.index)
                val name = world.definitions.get(EnumDef::class.java, 1345).getString(music.index)
                val polygonVertices = mutableListOf<Tile>()
                for (i in area.x.indices) polygonVertices.add(Tile(area.x[i], area.y[i]))
                on_enter_region(regionId = area.region) {
                    player.unlockSong(music.index)
                    val playlistEnabled = player.getVarbit(7078) == 1
                    if (playlistEnabled) return@on_enter_region

                    player.playSong(id, name)
                }

                if (polygonVertices.size != 0) {
                    on_enter_simple_polygon_area(SimplePolygonArea(polygonVertices.toTypedArray())) {
                        player.unlockSong(music.index)
                        val playlistEnabled = player.getVarbit(7078) == 1
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
    val trackId = world.definitions.get(EnumDef::class.java, 1351).getInt(trackIndex)
    val trackName = world.definitions.get(EnumDef::class.java, 1345).getString(trackIndex)

    if (option == PLAY_SONG) {
        player.playSong(trackId, trackName)
    } else if (option == ADD_TO_PLAYLIST) {
        player.addSongToPlaylist(slot)
    } else if (option == REMOVE_FROM_PLAYLIST_5) {
        player.removeSongFromPlaylist(slot, true)
    }
}

on_button(187, 9) {
    val trackSlot = player.getInteractingSlot()
    val option = player.getInteractingOption()
    val trackIndex = player.getVarbit(7081 + trackSlot)
    val trackId = world.definitions.get(EnumDef::class.java, 1351).getInt(trackIndex)
    val trackName = world.definitions.get(EnumDef::class.java, 1345).getString(trackIndex)

    if (option == PLAY_SONG) {
        player.playSong(trackId, trackName)
    } else if (option == REMOVE_FROM_PLAYLIST_2) {
        player.removeSongFromPlaylist(trackSlot)
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

    val trackFrom = player.getVarbit(7081 + fromSlot)
    val trackTo = player.getVarbit(7081 + toSlot)

    player.setVarbit(7081 + toSlot, trackFrom)
    player.setVarbit(7081 + fromSlot, trackTo)
}

on_song_end {
    val finishedSongId = player.attr[LAST_SONG_END]!!
    val finishedSongIndex = world.definitions.get(EnumDef::class.java, 1351).getKeyForValue(finishedSongId)
    val playlistEnabled = player.getVarbit(7078) == 1
    val shuffleEnabled = player.getVarbit(7079) == 1

    var songIndex: Int
    val playlistSongs = (7081..7092).filter { player.getVarbit(it) != 32767 }.map { player.getVarbit(it) }

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
        songIndex = songsToChoose[world.random(songsToChoose.size - 1)].index
    }

    val songId = world.definitions.get(EnumDef::class.java, 1351).getInt(songIndex)
    val songName = world.definitions.get(EnumDef::class.java, 1345).getString(songIndex)
    println("Song ended, now playing: $songName ($songId, $songIndex)")
    player.playSong(songId, songName)
}

on_login {
    // Enabling clicking music in main tab
    player.setEvents(interfaceId = 187, component = 1, to = 2030, setting = 30)

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
