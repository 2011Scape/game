package gg.rsmod.game.message.handler

import gg.rsmod.game.Server.Companion.logger
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.MessagePrivateMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.service.log.LoggerService
import gg.rsmod.util.Misc

class MessagePrivateHandler : MessageHandler<MessagePrivateMessage> {
    override fun handle(client: Client, world: World, message: MessagePrivateMessage) {
        val decompressed = ByteArray(256)
        val huffman = world.huffman
        huffman.decompress(message.message, decompressed, message.length)

        val unpacked = String(decompressed, 0, message.length)
        val fromPlayer = client as Player
        val toPlayer = world.getPlayerForName(message.username)!!

        val formattedMessage = Misc.formatSentence(unpacked)

        fromPlayer.sendPrivateMessage(formattedMessage, Misc.formatForDisplay(toPlayer.username))
        toPlayer.receivePrivateMessage(
            formattedMessage,
            fromPlayer.privilege,
            Misc.formatForDisplay(fromPlayer.username),
        )

        // Retrieve the LoggerService and log the private message
        val loggerService = world.getService(LoggerService::class.java, searchSubclasses = true)
        if (loggerService != null) {
            loggerService.logPrivateChat(fromPlayer, toPlayer, unpacked)
        } else {
            logger.info("LoggerService not found. Private message will not be logged.")
        }
    }
}
