package gg.rsmod.plugins.content.mechanics.stairs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.plugins.api.ext.appendToString
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import mu.KLogging
import java.nio.file.Files
import java.nio.file.Paths

class StairService : Service {
    val stairs = ObjectArrayList<Stairs>()

    override fun init(
        server: Server,
        world: World,
        serviceProperties: ServerProperties,
    ) {
        val file = Paths.get(serviceProperties.get("stairs") ?: "./data/cfg/stairs/stairs.json")
        Files.newBufferedReader(file).use { reader ->
            val stairs =
                Gson().fromJson<ObjectArrayList<Stairs>>(
                    reader,
                    object : TypeToken<ObjectArrayList<Stairs>>() {}.type,
                )
            this.stairs.addAll(stairs)
        }

        logger.info { "Loaded ${stairs.size.appendToString("stair")}." }
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
