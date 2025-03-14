package gg.rsmod.game

import java.nio.file.Paths

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server()
        server.startServer(apiProps = Paths.get("./data/api.yml"))

        // Start the game world
        val world = server.startGame(
            filestore = Paths.get("./data", "cache"),
            gameProps = Paths.get("./game.yml"),    // Already pointing to game.yml
            packets = Paths.get("./data", "packets.yml"),
            blocks = Paths.get("./data", "blocks.yml"),
            devProps = Paths.get("./dev-settings.yml"),
            args = args,
        )

        // Start the command server
        server.startCommandServer(world, tcpPort = world.gameContext.tcpPort)
    }
}
