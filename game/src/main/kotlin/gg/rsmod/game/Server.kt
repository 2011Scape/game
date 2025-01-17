package gg.rsmod.game

import com.displee.cache.CacheLibrary
import com.google.common.base.Stopwatch
import gg.rsmod.game.message.impl.LogoutFullMessage
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.game.protocol.ClientChannelInitializer
import gg.rsmod.game.service.GameService
import gg.rsmod.game.service.rsa.RsaService
import gg.rsmod.game.service.xtea.XteaKeyService
import gg.rsmod.util.ServerProperties
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerDomainSocketChannel
import io.netty.channel.unix.DomainSocketAddress
import java.io.File
import kotlin.concurrent.thread
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import mu.KLogging
import java.net.InetSocketAddress
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import java.util.*

/**
 * The [Server] is responsible for starting any and all games.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class Server {
    /**
     * The properties specific to our API.
     */
    private val apiProperties = ServerProperties()

    private val acceptGroup = NioEventLoopGroup(2)

    private val ioGroup = NioEventLoopGroup(1)

    val bootstrap = ServerBootstrap()

    /**
     * Prepares and handles any API related logic that must be handled
     * before the game can be launched properly.
     */
    fun startServer(apiProps: Path) {
        Thread.setDefaultUncaughtExceptionHandler { t, e -> logger.error("Uncaught server exception in thread $t!", e) }
        val stopwatch = Stopwatch.createStarted()

        /*
         * Load the API property file.
         */
        apiProperties.loadYaml(apiProps.toFile())
        logger.info("Preparing ${getApiName()}...")

        /*
         * Inform the time it took to load the API related logic.
         */
        logger.info("${getApiName()} loaded up in ${stopwatch.elapsed(TimeUnit.MILLISECONDS)}ms.")
        logger.info("Visit our site ${getApiSite()} to purchase & sell plugins.")
    }

    private fun isUnixSocketSupported(): Boolean {
        val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
        return osName.contains("linux") // UDS is typically supported only on Linux
    }

    fun startCommandServer(world: World,
                           socketPath: String = "/tmp/game_server.sock",
                           tcpPort: Int = 53678) {
        val isUnixSupported = isUnixSocketSupported()

        // Start Unix Domain Sockets if supported
        if (isUnixSupported) {
            startUnixSocketListener(world, socketPath)
        } else {
            logger.info("Unix domain sockets are not supported on this OS. Falling back to TCP.")
            startTcpSocketListener(world, tcpPort)
        }
    }

    /**
     * Starts a Unix Domain Socket Listener specifically for Linux systems to handle communication with clients.
     * A socket file will be created at a pre-defined path, and a Netty-based server will be initialized to listen for incoming connections.
     * The method includes resource management, error handling, and cleanup mechanisms to ensure reliability.
     *
     * @param world The game world instance, which provides context and facilitates handling requests from connected clients through the Unix socket.
     */
    private fun startUnixSocketListener(world: World, socketPath: String) {
        if (socketPath.length > 108) { // UDS path length limit
            throw IllegalArgumentException("Socket path cannot exceed 108 characters.")
        }

        val socketFile = File(socketPath)
        if (socketFile.exists()) {
            try {
                logger.info("Deleting existing Unix socket file: $socketPath")
                socketFile.delete()
            } catch (e: Exception) {
                logger.error("Unable to delete socket file: ${e.message}", e)
                return
            }
        }

        thread(start = true, name = "UnixSocketListener") {
            val bossGroup = EpollEventLoopGroup(1)
            val workerGroup = EpollEventLoopGroup(Runtime.getRuntime().availableProcessors())

            try {
                val serverBootstrap = ServerBootstrap()
                serverBootstrap.group(bossGroup, workerGroup)
                    .channel(EpollServerDomainSocketChannel::class.java)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(object : ChannelInitializer<Channel>() {
                        override fun initChannel(ch: Channel) {
                            ch.pipeline().addLast(NettyUnixSocketHandler(world, ::handleCommand))
                        }
                    })

                logger.info("Starting Unix Domain Socket Server...")
                val serverChannelFuture = serverBootstrap.bind(DomainSocketAddress(socketPath)).sync()
                logger.info("Unix Domain Socket Server started at $socketPath")
                serverChannelFuture.channel().closeFuture().sync()
            } catch (e: Exception) {
                logger.error("Error starting Unix Domain Socket Server: ${e.message}", e)
            } finally {
                cleanupResources(bossGroup, workerGroup, socketFile)
            }
        }
    }

    private fun startTcpSocketListener(world: World, port: Int) {
        thread(start = true, name = "TcpSocketListener") {
            val bossGroup = NioEventLoopGroup(1)
            val workerGroup = NioEventLoopGroup(Runtime.getRuntime().availableProcessors())

            try {
                val serverBootstrap = ServerBootstrap()
                serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(object : ChannelInitializer<Channel>() {
                        override fun initChannel(ch: Channel) {
                            ch.pipeline().addLast(NettyTcpSocketHandler(world, ::handleCommand))
                        }
                    })

                logger.info("Starting TCP Socket Command Server...")
                val serverChannelFuture = serverBootstrap.bind(InetSocketAddress("127.0.0.1", port)).sync()
                logger.info("TCP command server listening on 127.0.0.1:$port")
                serverChannelFuture.channel().closeFuture().sync()
            } catch (e: Exception) {
                logger.error("Error starting TCP Socket Server: ${e.message}", e)
            } finally {
                cleanupResources(bossGroup, workerGroup, null)
            }
        }
    }

    private fun cleanupResources(
        bossGroup: EventLoopGroup?,
        workerGroup: EventLoopGroup?,
        socketFile: File?
    ) {
        try {
            bossGroup?.shutdownGracefully()?.sync()
            workerGroup?.shutdownGracefully()?.sync()
            if (socketFile?.exists() == true) {
                socketFile.delete()
                logger.info("Unix socket file ${socketFile.path} deleted.")
            }
        } catch (e: Exception) {
            logger.error("Error during resource cleanup: ${e.message}", e)
        }
    }

    /**
     * A handler class for managing communication over a Unix Domain Socket in a Netty-based server.
     * This handler processes incoming messages, executes commands, and sends appropriate responses.
     *
     * @constructor
     * @param world The game world instance, which provides context for processing incoming commands.
     * @param commandHandler A function reference for handling commands. Takes a command as a string
     * and the `world` instance, returning the response as a string.
     *
     * This class is typically utilized by the Netty server to handle incoming connections and data from clients.
     */
    private class NettyUnixSocketHandler(
        private val world: World,
        private val commandHandler: (String, World) -> String
    ) : io.netty.channel.ChannelInboundHandlerAdapter() {

        override fun channelRead(ctx: io.netty.channel.ChannelHandlerContext, msg: Any) {
            if (msg is io.netty.buffer.ByteBuf) {
                try {
                    val command = msg.toString(io.netty.util.CharsetUtil.UTF_8).trim()
                    logger.info("Received command via UDS: $command")

                    // Command validation and handling logic inlined
                    val response = when {
                        command.isBlank() -> "Error: Command cannot be empty."
                        command.length > 255 -> "Error: Command exceeds maximum length of 255 characters."
                        else -> try {
                            commandHandler(command, world)
                        } catch (e: Exception) {
                            logger.error("Error executing command via UDS: '${command}': ${e.message}", e)
                            "Error: An exception occurred while executing the command."
                        }
                    }

                    // Write response back to client
                    val responseBuf = ctx.alloc().buffer()
                    responseBuf.writeBytes("$response\n".toByteArray(io.netty.util.CharsetUtil.UTF_8))
                    ctx.writeAndFlush(responseBuf)
                } finally {
                    msg.release()
                }
            }
        }

        override fun exceptionCaught(ctx: io.netty.channel.ChannelHandlerContext, cause: Throwable) {
            // Log and close on exception
            logger.error("UDS Handler: Error occurred: ${cause.message}", cause)
            ctx.close()
        }
    }

    private class NettyTcpSocketHandler(
        private val world: World,
        private val commandHandler: (String, World) -> String
    ) : io.netty.channel.ChannelInboundHandlerAdapter() {

        override fun channelRead(ctx: io.netty.channel.ChannelHandlerContext, msg: Any) {
            if (msg is io.netty.buffer.ByteBuf) {
                try {
                    val command = msg.toString(io.netty.util.CharsetUtil.UTF_8).trim()
                    logger.info("Received command via TCP: $command")

                    // Command validation and handling logic inlined
                    val response = when {
                        command.isBlank() -> "Error: Command cannot be empty."
                        command.length > 255 -> "Error: Command exceeds maximum length of 255 characters."
                        else -> try {
                            commandHandler(command, world)
                        } catch (e: Exception) {
                            logger.error("Error executing command via TCP: '${command}': ${e.message}", e)
                            "Error: An exception occurred while executing the command."
                        }
                    }

                    // Write response back to client
                    val responseBuf = ctx.alloc().buffer()
                    responseBuf.writeBytes("$response\n".toByteArray(io.netty.util.CharsetUtil.UTF_8))
                    ctx.writeAndFlush(responseBuf)
                } finally {
                    msg.release()
                }
            }
        }

        override fun exceptionCaught(ctx: io.netty.channel.ChannelHandlerContext, cause: Throwable) {
            // Log and close on exception
            logger.error("TCP Handler: Error occurred: ${cause.message}", cause)
            ctx.close()
        }
    }

    /**
     * Processes a given command and performs the specified action within the game's world.
     *
     * The method supports commands such as "kick" (to remove a player from the game)
     * and "teleport" (to move a player to a specific location in the game world).
     *
     * @param command The command string containing the action to be executed along with its arguments.
     * @param world The game world instance, used to interact and manipulate the state of the players.
     * @return A response string indicating the result of the command execution or an error message if applicable.
     */
    private fun handleCommand(command: String, world: World): String {
        return try {
            when {
                command.startsWith("kick") -> {
                    val username = command.substringAfter(" ").trim()
                    if (username.isEmpty()) {
                        return "Invalid usage! Expected: kick <username>"
                    }
                    val player = world.getPlayerForName(username.replace("_", " "))
                    return if (player != null) {
                        val response = "Player $username has been kicked."
                        Thread {
                            try {
                                player.apply {
                                    requestLogout()
                                    write(LogoutFullMessage())
                                    channelClose()
                                }
                            } catch (e: Exception) {
                                logger.error("Error during player logout: ${e.message}", e)
                            }
                        }.start()
                        response
                    } else {
                        "Player $username not found."
                    }
                }
                command.startsWith("teleport") -> {
                    val args = command.substringAfter(" ").split(" ")
                    if (args.size < 3 || args.size > 4) {
                        return "Invalid usage! Expected: teleport <username> <x> <z> [height]"
                    }
                    val username = args[0]
                    val x = args[1].toIntOrNull()
                    val z = args[2].toIntOrNull()
                    val height = if (args.size == 4) args[3].toIntOrNull() ?: 0 else 0

                    if (x == null || z == null) {
                        return "Invalid coordinates! <x> and <z> must be integers."
                    }

                    val player = world.getPlayerForName(username.replace("_", " "))
                    return if (player != null) {
                        player.moveTo(Tile(x, z, height))
                        "Player $username has been teleported to [$x, $z, $height]."
                    } else {
                        "Player $username not found."
                    }
                }
                //TODO: Add moderation commands once report abuse interface is finished.
                else -> "Unknown command: $command"
            }
        } catch (e: Exception) {
            logger.error("Error while handling command: '$command'. Exception: ${e.message}", e)
            "An error occurred while processing your command: ${e.message}"
        }
    }

    /**
     * Prepares and handles any game related logic that was specified by the
     * user.
     *
     * Due to being decoupled from the API logic that will always be used, you
     * can start multiple servers with different game property files.
     */
    fun startGame(
        filestore: Path,
        gameProps: Path,
        packets: Path,
        blocks: Path,
        devProps: Path?,
        @Suppress("UNUSED_PARAMETER") args: Array<String>,
    ): World {
        val stopwatch = Stopwatch.createStarted()
        val individualStopwatch = Stopwatch.createUnstarted()

        /*
         * Load the game property file.
         */
        val initialLaunch = Files.deleteIfExists(Paths.get("./first-launch"))
        val gameProperties = ServerProperties()
        val devProperties = ServerProperties()

        gameProperties.loadYaml(gameProps.toFile())
        if (devProps != null && Files.exists(devProps)) {
            devProperties.loadYaml(devProps.toFile())
        }

        logger.info("Loaded properties for ${gameProperties.get<String>("name")!!}.")

        /*
         * Extract the `commandServer` configuration as a map
         */
        val commandServerConfig = gameProperties.get<Map<String, Any>>("commandServer")
        val socketPath = commandServerConfig?.get("socketPath") as? String ?: "/tmp/game_server.sock"
        val tcpPort = commandServerConfig?.get("tcpPort") as? Int ?: 50017

        /*
         * Create a game context for our configurations and services to run.
         */
        val gameContext =
            GameContext(
                initialLaunch = initialLaunch,
                name = gameProperties.get<String>("name")!!,
                revision = gameProperties.get<Int>("revision")!!,
                cycleTime = gameProperties.getOrDefault("cycle-time", 600),
                playerLimit = gameProperties.getOrDefault("max-players", 2048),
                home =
                    Tile(
                        gameProperties.get<Int>("home-x")!!,
                        gameProperties.get<Int>("home-z")!!,
                        gameProperties.getOrDefault("home-height", 0),
                    ),
                skillCount = gameProperties.getOrDefault("skill-count", SkillSet.DEFAULT_SKILL_COUNT),
                npcStatCount = gameProperties.getOrDefault("npc-stat-count", Npc.Stats.DEFAULT_NPC_STAT_COUNT),
                runEnergy = gameProperties.getOrDefault("run-energy", true),
                gItemPublicDelay =
                    gameProperties.getOrDefault(
                        "gitem-public-spawn-delay",
                        GroundItem.DEFAULT_PUBLIC_SPAWN_CYCLES,
                    ),
                gItemDespawnDelay =
                    gameProperties.getOrDefault(
                        "gitem-despawn-delay",
                        GroundItem.DEFAULT_DESPAWN_CYCLES,
                    ),
                preloadMaps = gameProperties.getOrDefault("preload-maps", false),
                bonusExperience = gameProperties.getOrDefault("bonus-experience", false),

                // New properties for Command Server
                socketPath = socketPath,
                tcpPort = tcpPort
            )

        val devContext =
            DevContext(
                debugExamines = devProperties.getOrDefault("debug-examines", false),
                debugObjects = devProperties.getOrDefault("debug-objects", false),
                debugButtons = devProperties.getOrDefault("debug-buttons", false),
                debugItemActions = devProperties.getOrDefault("debug-items", false),
                debugMagicSpells = devProperties.getOrDefault("debug-spells", false),
            )

        val world = World(gameContext, devContext)

        /*
         * Load the file store.
         */
        individualStopwatch.reset().start()
        world.filestore = CacheLibrary(filestore.toFile().toString())
        logger.info(
            "Loaded filestore from path {} in {}ms.",
            filestore,
            individualStopwatch.elapsed(TimeUnit.MILLISECONDS),
        )

        /*
         * Load the definitions.
         */
        world.definitions.loadAll(world.filestore)

        /*
         * Load the services required to run the server.
         */
        world.loadServices(this, gameProperties)
        world.init()

        if (gameContext.preloadMaps) {
            /*
             * Preload region definitions.
             */
            world.getService(XteaKeyService::class.java)?.let { service ->
                world.definitions.loadRegions(world, world.chunks, service.validRegions)
            }
        }

        /*
         * Load the packets for the game.
         */
        world.getService(type = GameService::class.java)?.let { gameService ->
            individualStopwatch.reset().start()
            gameService.messageStructures.load(packets.toFile())
            gameService.messageEncoders.init()
            gameService.messageDecoders.init(gameService.messageStructures)
            logger.info(
                "Loaded message codec and handlers in {}ms.",
                individualStopwatch.elapsed(TimeUnit.MILLISECONDS),
            )
        }

        /*
         * Load the update blocks for the game.
         */
        individualStopwatch.reset().start()
        world.loadUpdateBlocks(blocks.toFile())
        logger.info("Loaded update blocks in {}ms.", individualStopwatch.elapsed(TimeUnit.MILLISECONDS))

        /*
         * Load the privileges for the game.
         */
        individualStopwatch.reset().start()
        world.privileges.load(gameProperties)
        logger.info(
            "Loaded {} privilege levels in {}ms.",
            world.privileges.size(),
            individualStopwatch.elapsed(TimeUnit.MILLISECONDS),
        )

        /*
         * Load the plugins for game content.
         */
        individualStopwatch.reset().start()
        world.plugins.init(
            server = this,
            world = world,
            jarPluginsDirectory = gameProperties.getOrDefault("plugin-packed-path", "./plugins"),
        )
        logger.info(
            "Loaded {} plugins in {}ms.",
            DecimalFormat().format(world.plugins.getPluginCount()),
            individualStopwatch.elapsed(TimeUnit.MILLISECONDS),
        )

        /*
         * Post load world.
         */
        world.postLoad()

        /*
         * Inform the time it took to load up all non-network logic.
         */
        logger.info(
            "${gameProperties.get<String>("name")!!} loaded up in ${stopwatch.elapsed(TimeUnit.MILLISECONDS)}ms.",
        )

        /*
         * Set our bootstrap's groups and parameters.
         */
        val rsaService = world.getService(RsaService::class.java)
        val clientChannelInitializer =
            ClientChannelInitializer(
                revision = gameContext.revision,
                rsaExponent = rsaService?.getExponent(),
                rsaModulus = rsaService?.getModulus(),
                filestore = world.filestore,
                world = world,
            )

        bootstrap.group(acceptGroup, ioGroup)
        bootstrap.channel(NioServerSocketChannel::class.java)
        bootstrap.childHandler(clientChannelInitializer)

        /*
         * Bind all service networks, if applicable.
         */
        world.bindServices(this)

        /*
         * Bind the game port.
         */
        val port = gameProperties.getOrDefault("game-port", 43594)
        bootstrap.bind(InetSocketAddress(port)).sync().awaitUninterruptibly()
        logger.info("Now listening for incoming connections on port $port...")

        System.gc()

        return world
    }

    /**
     * Gets the API-specific org name.
     */
    fun getApiName(): String = apiProperties.getOrDefault("org", "RS Mod")

    fun getApiSite(): String = apiProperties.getOrDefault("org-site", "rspsmods.com")

    companion object : KLogging() {}
}
