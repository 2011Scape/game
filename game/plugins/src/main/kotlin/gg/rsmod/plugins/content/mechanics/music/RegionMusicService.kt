package gg.rsmod.plugins.content.mechanics.music

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.game.service.game.ItemMetadataService
import gg.rsmod.plugins.content.mechanics.doors.Door
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import mu.KLogging
import java.io.File
import java.io.FileNotFoundException

class RegionMusicService : Service {

    val music = ObjectArrayList<MusicType>()
    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val files = File("./data/cfg/music/music_by_region.yaml")
        if (!files.exists()) {
            throw FileNotFoundException("Path does not exist $files.")
        }
        var mapper = ObjectMapper(YAMLFactory())
        files.bufferedReader().use {reader ->
            val musicType = mapper.readValue(reader, Array<MusicType>::class.java)
            this.music.addAll(musicType)
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
data class MusicType (
    val regionID: Int = -1,
    val musicID: Int = -1
)
