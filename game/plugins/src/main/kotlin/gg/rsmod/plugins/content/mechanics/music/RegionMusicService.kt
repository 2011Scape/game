package gg.rsmod.plugins.content.mechanics.music

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import gg.rsmod.game.Server
import gg.rsmod.game.fs.def.EnumDef
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import mu.KLogging
import java.io.File
import java.io.FileNotFoundException

class RegionMusicService : Service {
    val musicTrackList = ObjectArrayList<MusicTrack>()
    val musicTrackVarps =
        arrayOf(
            20,
            21,
            22,
            23,
            24,
            25,
            298,
            311,
            346,
            414,
            464,
            598,
            662,
            721,
            906,
            1009,
            1104,
            1136,
            1180,
            1202,
            1381,
            1394,
            1434,
            1596,
            1618,
            1619,
            1620,
            -1, // Adding -1 here because songs 864 - 895 are unused
            1864,
            1865,
            2019,
            2246,
        )

    data class MusicTrack(
        val name: String,
        val index: Int,
        val areas: List<MusicTrackArea>,
        val varp: Int,
        val bitNum: Int,
    )

    data class MusicTrackArea(
        val region: Int = -1,
        val x: List<Int> = emptyList(),
        val y: List<Int> = emptyList(),
        val plane: Int = 0,
    )

    override fun init(
        server: Server,
        world: World,
        serviceProperties: ServerProperties,
    ) {
        val files = File("./data/cfg/music/music_by_region.yaml")
        if (!files.exists()) {
            throw FileNotFoundException("Path does not exist $files.")
        }
        files.bufferedReader().use { reader ->
            val rawMusic = ObjectMapper(YAMLFactory()).readValue(reader, Map::class.java)
            rawMusic.forEach { (key, value) ->
                val index = (value as Map<*, *>).getOrDefault("index", -1) as Int
                val varp = musicTrackVarps[index.floorDiv(32)]
                val bitNum = index - index.floorDiv(32) * 32
                val areas =
                    when (val areasValue = value["areas"]) {
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
                                            plane = plane,
                                        )
                                    }
                                    else -> null
                                }
                            }
                        }
                        else -> emptyList()
                    }
                val track = MusicTrack(name = key as String, index = index, areas = areas, varp = varp, bitNum = bitNum)
                musicTrackList.add(track)
            }
        }
        // Adding tracks that aren't present in the music file but are in the cache
        world.definitions
            .get(
                EnumDef::class.java,
                1345,
            ).values
            .filter { it.key !in musicTrackList.map { it.index } }
            .forEach {
                val varp = musicTrackVarps[it.key.floorDiv(32)]
                val bitNum = it.key - it.key.floorDiv(32) * 32
                val track =
                    MusicTrack(
                        name = it.value as String,
                        index = it.key,
                        areas = listOf(),
                        varp = varp,
                        bitNum = bitNum,
                    )
                musicTrackList.add(track)
            }
    }

    override fun postLoad(
        server: Server,
        world: World,
    ) {
    }

    override fun bindNet(
        server: Server,
        world: World,
    ) {
    }

    override fun terminate(
        server: Server,
        world: World,
    ) {
    }

    companion object : KLogging()
}
