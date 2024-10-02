package gg.rsmod.plugins.content.mechanics.multi

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.constructor.SafeConstructor
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.plugins.api.ext.appendToString
import gg.rsmod.util.ServerProperties
import mu.KLogging
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class MultiService : Service {
    val multiRegions = mutableListOf<Int>()
    val multiChunks = mutableListOf<Int>()

    override fun init(
        server: Server,
        world: World,
        serviceProperties: ServerProperties,
    ) {
        try {
            // Retrieve the file path from a trusted source
            val filePathString = serviceProperties.get("areas") ?: "./data/cfg/areas/multi.yml"
            val file = Paths.get(filePathString)

            // Check if file exists and is accessible
            if (!Files.exists(file) || !Files.isReadable(file)) {
                throw IOException("File not found or not readable: $filePathString")
            }

            Files.newBufferedReader(file).use { reader ->
                val yaml =
                    com.fasterxml.jackson.dataformat.yaml.snakeyaml
                        .Yaml(SafeConstructor())
                val loadedData = yaml.load(reader)

                val multiAreaData =
                    safelyCastToMap(loadedData) ?: run {
                        logger.error("YAML data format is incorrect or not as expected.")
                        return
                    }
                @Suppress("UNCHECKED_CAST")
                (multiAreaData["regions"] as? List<Int>)?.forEach { region ->
                    setMultiCombatRegion(region)
                } ?: logger.warn("Regions data is not of expected type or is missing.")

                @Suppress("UNCHECKED_CAST")
                (multiAreaData["chunks"] as? List<Int>)?.forEach { chunkHash ->
                    setMultiCombatChunks(chunkHash)
                } ?: logger.warn("Chunks data is not of expected type or is missing.")
            }

            logger.info {
                "Loaded ${multiRegions.size.appendToString("multi combat region")} and " +
                    "${multiChunks.size.appendToString("chunk")}."
            }
        } catch (e: Exception) {
            // Log the exception and handle it appropriately
            logger.error("Error initializing with server properties: ${e.message}")
            // Depending on the application's requirements, you might want to rethrow the exception or handle it differently
        }
    }

    fun safelyCastToMap(data: Any?): Map<String, Any>? {
        return (data as? Map<*, *>)?.let {
            if (it.keys.all { key -> key is String }) {
                @Suppress("UNCHECKED_CAST")
                it as Map<String, Any>
            } else {
                null
            }
        }
    }

    fun setMultiCombatRegion(region: Int) {
        multiRegions.add(region)
    }

    fun setMultiCombatChunks(chunkHash: Int) {
        multiChunks.add(chunkHash)
    }

    fun appendMultiCombatChunks(newChunks: MutableList<Int>) {
        val filePath = "./data/cfg/areas/multi.yml"

        try {
            val file = Paths.get(filePath)
            val lines = Files.readAllLines(file).toMutableList()
            val chunkStartIndex = lines.indexOfFirst { it.trim().startsWith("chunks:") }

            if (chunkStartIndex == -1) {
                throw Exception("chunks: section not found in multi.yml")
            }

            // Extract existing chunk hashes
            val existingChunks =
                lines
                    .subList(chunkStartIndex + 1, lines.size)
                    .mapNotNull {
                        it
                            .trim()
                            .removePrefix("-")
                            .trim()
                            .toIntOrNull()
                    }.toSet()

            // Find the index after the last chunk or the 'chunks:' line
            var lastIndex = chunkStartIndex + 1
            while (lastIndex < lines.size && lines[lastIndex].trim().startsWith("-")) {
                lastIndex++
            }

            var addedCount = 0
            val existingChunkMessages = mutableListOf<String>()
            newChunks.forEach { chunk ->
                if (chunk !in existingChunks) {
                    lines.add(lastIndex, "- $chunk")
                    lastIndex++
                    addedCount++
                } else {
                    existingChunkMessages.add("Chunk with hash $chunk already exists.")
                }
            }

            // Write the updated lines back to the file
            BufferedWriter(FileWriter(file.toFile())).use { writer ->
                for (line in lines) {
                    writer.write(line)
                    writer.newLine()
                }
            }

            if (existingChunkMessages.isNotEmpty()) {
                println("The following chunks already exist and were not added again:")
                existingChunkMessages.forEach { println(it) }
            }

            println("Appended $addedCount new unique chunk(s) to multi.yml.")
        } catch (e: Exception) {
            println("Error while appending new chunks to multi.yml: ${e.message}")
        }
    }

    fun appendMultiCombatRegions(newRegions: List<Int>) {
        val filePath = "./data/cfg/areas/multi.yml"

        try {
            val file = Paths.get(filePath)
            val lines = Files.readAllLines(file).toMutableList()
            val regionStartIndex = lines.indexOfFirst { it.trim().startsWith("regions:") }

            if (regionStartIndex == -1) {
                throw Exception("regions: section not found in multi.yml")
            }

            // Extract existing region IDs
            val existingRegions =
                lines
                    .subList(regionStartIndex + 1, lines.size)
                    .mapNotNull {
                        it
                            .trim()
                            .removePrefix("-")
                            .trim()
                            .toIntOrNull()
                    }.toSet()

            // Find the index after the last region or the 'regions:' line
            var lastIndex = regionStartIndex + 1
            while (lastIndex < lines.size && lines[lastIndex].trim().startsWith("-")) {
                lastIndex++
            }

            var addedCount = 0
            val existingRegionMessages = mutableListOf<String>()
            newRegions.forEach { regionId ->
                if (regionId !in existingRegions) {
                    lines.add(lastIndex, "- $regionId")
                    lastIndex++
                    addedCount++
                } else {
                    existingRegionMessages.add("Region with ID $regionId already exists.")
                }
            }

            // Write the updated lines back to the file
            BufferedWriter(FileWriter(file.toFile())).use { writer ->
                for (line in lines) {
                    writer.write(line)
                    writer.newLine()
                }
            }

            if (existingRegionMessages.isNotEmpty()) {
                println("The following regions already exist and were not added again:")
                existingRegionMessages.forEach { println(it) }
            }

            println("Appended $addedCount new unique region(s) to multi.yml.")
        } catch (e: Exception) {
            println("Error while appending new regions to multi.yml: ${e.message}")
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
