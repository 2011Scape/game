package gg.rsmod.game.service.log

import gg.rsmod.game.Server
import gg.rsmod.game.event.Event
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.util.ServerProperties
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.ThreadContext

@Suppress("UNUSED")
class FileLoggerService : LoggerService {
    private val privateMessageLogger = LogManager.getLogger("PrivateMessageLogger")

    // Implement Service interface methods
    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        // No initialization needed for Log4j
    }

    override fun postLoad(server: Server, world: World) {
        // Perform any post-initialization logic here
    }

    override fun bindNet(server: Server, world: World) {
        // Perform any network-related setup here
    }

    override fun terminate(server: Server, world: World) {
        // Perform cleanup operations here
    }

    // Implement LoggerService methods
    override fun logPrivateChat(fromPlayer: Player, toPlayer: Player, message: String) {
        // Log outgoing message for the sender
        ThreadContext.put("player", fromPlayer.username) // Set the player context
        privateMessageLogger.info("[OUTGOING] -> ${toPlayer.username}: $message")
        ThreadContext.remove("player") // Clear the player context

        // Log incoming message for the receiver
        ThreadContext.put("player", toPlayer.username) // Set the player context
        privateMessageLogger.info("[INCOMING] <- ${fromPlayer.username}: $message")
        ThreadContext.remove("player") // Clear the player context
    }

    // Provide empty implementations for other LoggerService methods
    override fun logPacket(client: Client, message: String) {
        // Do nothing
    }

    override fun logLogin(player: Player) {
        // Do nothing
    }

    override fun logPublicChat(player: Player, message: String) {
        // Do nothing
    }

    override fun logClanChat(player: Player, clan: String, message: String) {
        // Do nothing
    }

    override fun logCommand(player: Player, command: String, vararg args: String) {
        // Do nothing
    }

    override fun logItemDrop(player: Player, item: Item, slot: Int) {
        // Do nothing
    }

    override fun logItemPickUp(player: Player, item: Item) {
        // Do nothing
    }

    override fun logNpcKill(player: Player, npc: Npc) {
        // Do nothing
    }

    override fun logPlayerKill(killer: Player, killed: Player) {
        // Do nothing
    }

    override fun logEvent(pawn: Pawn, event: Event) {
        // Do nothing
    }
}
