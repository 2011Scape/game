package gg.rsmod.plugins.content.mechanics.music

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import mu.KLogging
import java.io.File
import java.io.FileNotFoundException

class RegionMusicService : Service {

    val musicTrackList = ObjectArrayList<MusicTrack>()

    data class MusicTrack(
        val name: String, val index: Int, val areas: List<MusicTrackArea>
    )

    data class MusicTrackArea(
        val region: Int = -1, val x: List<Int> = emptyList(), val y: List<Int> = emptyList(), val plane: Int = 0
    )

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val files = File("./data/cfg/music/music_by_region.yaml")
        if (!files.exists()) {
            throw FileNotFoundException("Path does not exist $files.")
        }
        files.bufferedReader().use { reader ->
            val rawMusic = ObjectMapper(YAMLFactory()).readValue(reader, Map::class.java)
            rawMusic.forEach { (key, value) ->
                val index = (value as Map<*, *>).getOrDefault("index", -1) as Int
                val areas = when (val areasValue = value["areas"]) {
                    is List<*> -> {
                        areasValue.mapNotNull { area ->
                            when (area) {
                                is Int -> MusicTrackArea(region = area.toInt())
                                is Map<*, *> -> {
                                    val region = area["region"] as? Int ?: -1
                                    val x = area["x"] as? List<*> ?: emptyList<Int>()
                                    val y = area["y"] as? List<*> ?: emptyList<Int>()
                                    val plane = area["plane"] as? Int ?: 0
                                    MusicTrackArea(
                                        region = region,
                                        x = x.filterIsInstance<Int>(),
                                        y = y.filterIsInstance<Int>(),
                                        plane = plane
                                    )
                                }
                                else -> null
                            }
                        }
                    }
                    else -> emptyList()
                }
                val track = MusicTrack(name = key as String, index = index, areas = areas)
                musicTrackList.add(track)
            }
        }
    }

    override fun postLoad(server: Server, world: World) {
    }

    override fun bindNet(server: Server, world: World) {
    }

    override fun terminate(server: Server, world: World) {
    }

    companion object : KLogging()

}