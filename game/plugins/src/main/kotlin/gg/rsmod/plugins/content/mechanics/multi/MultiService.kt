package gg.rsmod.plugins.content.mechanics.multi

import com.google.gson.Gson
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.model.region.ChunkCoords
import gg.rsmod.game.service.Service
import gg.rsmod.plugins.api.ext.appendToString
import gg.rsmod.util.ServerProperties
import mu.KLogging
import java.nio.file.Files
import java.nio.file.Paths
class MultiService : Service {

    val multiRegions = mutableListOf<Int>()

    val multiChunks = mutableListOf<ChunkCoords>()

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val file = Paths.get(serviceProperties.get("areas") ?: "./data/cfg/areas/multi.json")
        Files.newBufferedReader(file).use { reader ->
            val multiAreaData = Gson().fromJson(reader, MultiAreaData::class.java)

            multiAreaData.regions.forEach { region ->
                setMultiCombatRegion(region)
            }

            multiAreaData.chunks.forEach { (x, z) ->
                setMultiCombatChunks(ChunkCoords(x, z))
            }
        }

        logger.info {
            "Loaded ${multiRegions.size.appendToString("multi combat region")} and " +
                    "${multiChunks.size.appendToString("chunk")}."
        }
    }

    fun setMultiCombatRegion(region: Int) {
        multiRegions.add(region)
    }

    fun setMultiCombatChunks(chunk: ChunkCoords) {
        multiChunks.add(chunk)
    }

    override fun postLoad(server: Server, world: World) {
    }

    override fun bindNet(server: Server, world: World) {
    }

    override fun terminate(server: Server, world: World) {
    }

    companion object : KLogging()
}
