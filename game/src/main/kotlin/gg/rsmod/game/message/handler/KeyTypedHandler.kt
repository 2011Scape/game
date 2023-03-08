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

    override fun handle(client: Client, world: World, message: KeyTypedMessage) {
        val keycode = message.keycode
        val player = client as Player

        // escape key
        if(keycode == 13) {
            if(player.interfaces.getModal() != -1 && player.lock.canInterfaceInteract()) {
                player.closeInterfaceModal()
            }
        }

    }
}