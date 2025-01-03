package gg.rsmod.game.uds

import gg.rsmod.game.message.impl.LogoutFullMessage
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerDomainSocketChannel
import io.netty.channel.unix.DomainSocketAddress
import io.netty.channel.unix.DomainSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import java.io.File

/**
 * A Unix Domain Socket (UDS) server that listens for incoming commands and processes them.
 * @author Harley "Pixel" <hg@harleygilpin.com>
 * @param socketPath The filesystem path for the Unix Domain Socket (e.g., "/tmp/game_server.sock").
 */

class UnixDomainSocketServer(private val socketPath: String, private val world: World) {

    /**
     * Starts the UDS server.
     */
    fun start() {
        // Delete the socket file if it already exists
        File(socketPath).delete()

        // Create event loop groups for Netty
        val bossGroup = EpollEventLoopGroup(1) // Handles incoming connections
        val workerGroup = EpollEventLoopGroup() // Handles I/O operations

        try {
            // Configure the server
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(EpollServerDomainSocketChannel::class.java) // Use Epoll for UDS
                .childHandler(object : ChannelInitializer<DomainSocketChannel>() {
                    override fun initChannel(ch: DomainSocketChannel) {
                        // Set up the pipeline for each connection
                        val pipeline = ch.pipeline()
                        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8)) // Decode incoming bytes to strings
                        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8)) // Encode outgoing strings to bytes
                        pipeline.addLast(CommandHandler(world)) // Handle commands
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // Set the maximum number of pending connections
                .childOption(ChannelOption.SO_KEEPALIVE, true) // Enable keep-alive

            // Bind the server to the socket path and start listening
            val channelFuture = bootstrap.bind(DomainSocketAddress(socketPath)).sync()
            println("UDS server started and listening on $socketPath")

            // Wait until the server socket is closed
            channelFuture.channel().closeFuture().sync()
        } finally {
            // Shut down the event loop groups gracefully
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}

/**
 * Handles incoming commands from the UDS server.
 * @param world The game world instance.
 */
class CommandHandler(private val world: World) : SimpleChannelInboundHandler<String>() {

    /**
     * Processes incoming commands and sends responses back to the Discord bot.
     *
     * @param ctx The channel handler context.
     * @param command The received command as a string.
     */
    override fun channelRead0(ctx: ChannelHandlerContext, command: String) {
        println("Received command: $command")

        // Process the command and send a response back to the Discord bot
        val response = when {
            command.startsWith("ban") -> handleBan(command)
            command.startsWith("kick") -> handleKick(command)
            command.startsWith("mute") -> handleMute(command)
            command.startsWith("teleport") -> handleTeleport(command)
            else -> "Unknown command: $command"
        }

        // Send the response back to the Discord bot
        ctx.writeAndFlush("$response\n")
    }

    /**
     * Handles the "kick" command.
     *
     * @param command The full command string (e.g., "kick alycia").
     * @return A response message to send back to the Discord bot.
     */
    private fun handleKick(command: String): String {
        val args = command.split(" ")
        if (args.size < 2) {
            return "Usage: kick <username>"
        }

        val username = args[1].replace("_", " ") // Replace underscores with spaces
        val player = world.getPlayerForName(username)
            ?: return "Player not found: $username" // Find the player in the game world

        // Kick the player
        player.requestLogout()
        player.write(LogoutFullMessage())
        player.channelClose()

        return "Kicked player: $username"
    }

    /**
     * Handles the "ban" command.
     *
     * @param command The full command string (e.g., "ban player1").
     * @return A response message to send back to the Discord bot.
     */
    private fun handleBan(command: String): String {
        val args = command.split(" ")
        if (args.size < 2) {
            return "Usage: ban <username>"
        }
        val playerName = args[1]
        // TODO: Add your ban logic here.
        return "Banning player: $playerName"
    }

    /**
     * Handles the "mute" command.
     *
     * @param command The full command string (e.g., "mute player1 10").
     * @return A response message to send back to the Discord bot.
     */
    private fun handleMute(command: String): String {
        val args = command.split(" ")
        if (args.size < 3) {
            return "Usage: mute <username> <duration>"
        }
        val playerName = args[1]
        val duration = args[2].toLong()
        // TODO: Add muting logic here.
        return "Muting player: $playerName for $duration minutes"
    }

    /**
     * Handles the "teleport" command.
     *
     * @param command The full command string (e.g., "teleport player1 3222 3222 1").
     * @return A response message to send back to the Discord bot.
     */
    private fun handleTeleport(command: String): String {
        val args = command.split(" ")
        if (args.size < 4) {
            return "Usage: teleport <username> <x> <z> [height]"
        }

        val playerName = args[1]
        val x = args[2].toInt()
        val z = args[3].toInt()
        val height = if (args.size > 4) args[4].toInt() else 0 // Default height is 0

        // Find the player
        val player = world.getPlayerForName(playerName) ?: return "Player not found: $playerName"

        // Create a Tile object and move the player
        val tile = Tile(x, z, height)
        player.moveTo(tile)

        return "Teleported player: $playerName to coordinates ($x, $z, $height)"
    }

    /**
     * Handles exceptions during command processing.
     *
     * @param ctx The channel handler context.
     * @param cause The exception that occurred.
     */
    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
