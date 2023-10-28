package gg.rsmod.plugins.content.mechanics.multi;

import gg.rsmod.game.Server;
import gg.rsmod.game.model.World;
import gg.rsmod.game.model.region.ChunkCoords;
import gg.rsmod.game.service.Service;
import gg.rsmod.plugins.api.ext.appendToString;
import gg.rsmod.util.ServerProperties;
import mu.KLogging;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class MultiService : Service {

    val multiRegions = mutableListOf<Int>()
    val multiChunks = mutableListOf<ChunkCoords>()

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val file = Paths.get(serviceProperties.get("areas") ?: "./data/cfg/areas/multi.yml")
        Files.newBufferedReader(file).use { reader ->
            val yaml = Yaml(Constructor(Map::class.java))
            val multiAreaData = yaml.load(reader) as Map<String, Any>

            @Suppress("UNCHECKED_CAST")
            (multiAreaData["regions"] as List<Int>).forEach { region ->
                setMultiCombatRegion(region)
            }

            @Suppress("UNCHECKED_CAST")
            (multiAreaData["chunks"] as List<Map<String, Int>>).forEach { chunkMap ->
                setMultiCombatChunks(ChunkCoords(chunkMap["x"]!!, chunkMap["z"]!!))
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
