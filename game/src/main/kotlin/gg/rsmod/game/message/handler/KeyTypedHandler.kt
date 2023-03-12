package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.KeyTypedMessage
import gg.rsmod.game.message.impl.ResumePauseButtonMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.system.ServerSystem

/**
 * @author Alycia <https://github.com/alycii>
 */
class KeyTypedHandler : MessageHandler<KeyTypedMessage> {

    override fun handle(client: Client, world: World, message: KeyTypedMessage) {
        val keycode = message.keycode
        val player = client as Player

        when(keycode) {
            13 -> { // ESC
                if(player.interfaces.getModal() != -1 && player.lock.canInterfaceInteract()) {
                    player.closeInterfaceModal()
                }
            }
        }

        client.queues.submitReturnValue(message)
    }
}