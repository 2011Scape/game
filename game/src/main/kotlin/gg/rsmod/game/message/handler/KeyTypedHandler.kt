package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.KeyTypedMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Player

/**
 * @author Alycia <https://github.com/alycii>
 */
class KeyTypedHandler : MessageHandler<KeyTypedMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: KeyTypedMessage,
    ) {
        val keycode = message.keycode
        val player = client as Player

        when (keycode) {
            13 -> { // ESC
                if (player.interfaces.getModal() != -1 && player.lock.canInterfaceInteract()) {
                    player.closeInterfaceModal()
                }
            }
            83 -> { // SPACE
                if (!player.interfaces.optionsOpen &&
                    (player.interfaces.isOccupied(752, 12) || player.interfaces.isOccupied(752, 13))
                ) {
                    client.queues.submitReturnValue(message)
                }
            }
            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 -> { // Keys 1..10
                if (player.interfaces.optionsOpen || player.interfaces.isVisible(905)) {
                    client.queues.submitReturnValue(message)
                }
            }
        }
    }
}
