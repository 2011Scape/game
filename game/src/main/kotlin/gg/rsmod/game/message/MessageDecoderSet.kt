package gg.rsmod.game.message

import gg.rsmod.game.message.decoder.*
import gg.rsmod.game.message.handler.*
import gg.rsmod.game.message.impl.*

/**
 * Stores all the [MessageDecoder]s that are used on the
 * [gg.rsmod.game.service.GameService].
 *
 * @author Tom <rspsmods@gmail.com>
 */
class MessageDecoderSet {

    /**
     * The [MessageDecoder]s stored in respect to their opcode.
     */
    private val decoders = arrayOfNulls<MessageDecoder<*>>(256)

    /**
     * The [MessageHandler]s stored in respect to their opcode.
     */
    private val handlers = arrayOfNulls<MessageHandler<out Message>>(256)

    /**
     * Links [Message]s to their respective [MessageDecoder]s and [MessageHandler].
     */
    fun init(structures: MessageStructureSet) {
        put(MoveGameClickMessage::class.java, MoveGameClickDecoder(), ClickMapHandler(), structures)
        put(MoveMinimapClickMessage::class.java, MoveMinimapClickDecoder(), ClickMinimapHandler(), structures)
        put(IfButtonMessage::class.java, IfButton1Decoder(), IfButton1Handler(), structures)
        put(IfButtonDMessage::class.java, IfButtonDDecoder(), IfButtonDHandler(), structures)
        put(ClientCheatMessage::class.java, ClientCheatDecoder(), ClientCheatHandler(), structures)
        put(CloseModalMessage::class.java, CloseModalDecoder(), CloseMainComponentHandler(), structures)
        put(MessagePublicMessage::class.java, MessagePublicDecoder(), MessagePublicHandler(), structures)
        put(ResumePauseButtonMessage::class.java, ResumePauseButtonDecoder(), ResumePauseButtonHandler(), structures)
        put(ResumePCountDialogMessage::class.java, ResumePCountDialogDecoder(), ResumePCountDialogHandler(), structures)
        put(WindowStatusMessage::class.java, WindowStatusDecoder(), WindowStatusHandler(), structures)

        put(OpLoc1Message::class.java, OpLoc1Decoder(), OpLoc1Handler(), structures)
        put(OpLoc2Message::class.java, OpLoc2Decoder(), OpLoc2Handler(), structures)
        put(OpLoc3Message::class.java, OpLoc3Decoder(), OpLoc3Handler(), structures)
        put(OpLoc6Message::class.java, OpLoc6Decoder(), OpLoc6Handler(), structures)

        put(OpObj1Message::class.java, OpObj1Decoder(), OpObj1Handler(), structures)
        put(OpObj2Message::class.java, OpObj2Decoder(), OpObj2Handler(), structures)

        put(OpNpc1Message::class.java, OpNpc1Decoder(), OpNpc1Handler(), structures)
        put(OpNpc2Message::class.java, OpNpc2Decoder(), OpNpc2Handler(), structures)
        put(OpNpc3Message::class.java, OpNpc3Decoder(), OpNpc3Handler(), structures)
        put(OpNpc4Message::class.java, OpNpc4Decoder(), OpNpc4Handler(), structures)

        put(OpHeldUMessage::class.java, OpHeldUDecoder(), OpHeldUHandler(), structures)
        put(OpLocUMessage::class.java, OpLocUDecoder(), OpLocUHandler(), structures)
        put(OpNpcTMessage::class.java, OpNpcTDecoder(), OpNpcTHandler(), structures)

        put(OpPlayer3Message::class.java, OpPlayer3Decoder(), OpPlayer3Handler(), structures)
        put(OpPlayer4Message::class.java, OpPlayer4Decoder(), OpPlayer4Handler(), structures)


    }

    private fun <T : Message> put(messageType: Class<T>, decoderType: MessageDecoder<T>, handlerType: MessageHandler<T>, structures: MessageStructureSet) {
        val structure = structures.get(messageType) ?: throw RuntimeException("Message structure has not been set in packets file. [message=$messageType]")
        structure.opcodes.forEach { opcode ->
            decoders[opcode] = decoderType
            handlers[opcode] = handlerType
        }
    }

    fun get(opcode: Int): MessageDecoder<*>? {
        return decoders[opcode]
    }

    @Suppress("UNCHECKED_CAST")
    fun getHandler(opcode: Int): MessageHandler<Message>? {
        return handlers[opcode] as MessageHandler<Message>?
    }
}