package gg.rsmod.game.message.handler

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpPlayerTMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Entity
import gg.rsmod.game.model.priv.Privilege
import java.lang.ref.WeakReference

/**
 * @author Alycia <https://github.com/alycii>
 */
class OpPlayerTHandler : MessageHandler<OpPlayerTMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: OpPlayerTMessage,
    ) {
        val player = world.players[message.playerIndex] ?: return
        val parent = message.componentHash shr 16
        val child = message.componentHash and 0xFFFF

        if (!client.lock.canNpcInteract()) {
            return
        }

        client.writeConsoleMessage("Interface on Player: [$message], parent=$parent, child=$child")

        if (message.movementType == 1 && world.privileges.isEligible(client.privilege, Privilege.ADMIN_POWER)) {
            client.moveTo(world.findRandomTileAround(player.tile, 1) ?: player.tile)
        }

        client.closeInterfaceModal()
        client.fullInterruption(movement = true, interactions = true, queue = true)

        client.attr[INTERACTING_PLAYER_ATTR] = WeakReference(player)
        client.attr[INTERACTING_COMPONENT_PARENT] = parent
        client.attr[INTERACTING_COMPONENT_CHILD] = child

        if (parent == 679) {
            val item = client.inventory[message.componentSlot] ?: return
            client.attr[INTERACTING_ITEM] = WeakReference(item)
            client.executePlugin(PawnPathAction.itemUsePlugin)
            return
        }

        if (!world.plugins.executeSpellOnPlayer(client, parent, child)) {
            client.writeMessage(Entity.NOTHING_INTERESTING_HAPPENS)
            if (world.devContext.debugMagicSpells) {
                client.writeConsoleMessage("Unhandled magic spell: [$parent, $child]")
            }
        }
    }
}
