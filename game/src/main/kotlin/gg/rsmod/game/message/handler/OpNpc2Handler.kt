package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpNpc2Message
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.LAST_HIT_ATTR
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.priv.Privilege
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpNpc2Handler : MessageHandler<OpNpc2Message> {

    override fun handle(client: Client, world: World, message: OpNpc2Message) {
        val npc = world.npcs[message.index] ?: return

        if (!client.lock.canAttack()) {
            return
        }

        log(client, "Npc option 2: index=%d, movement=%d, npc=%s", message.index, message.movementType, npc)

        if (message.movementType == 1 && world.privileges.isEligible(client.privilege, Privilege.ADMIN_POWER)) {
            client.moveTo(world.findRandomTileAround(npc.tile, 1) ?: npc.tile)
        }

        client.closeInterfaceModal()
        client.interruptQueues()
        client.resetInteractions()

        client.attack(npc)
        npc.attr[LAST_HIT_ATTR] = WeakReference(client as Pawn)
    }
}