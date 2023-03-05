package gg.rsmod.game.message.handler

import gg.rsmod.game.action.GroundItemPathAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpObjUMessage
import gg.rsmod.game.model.EntityType
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.priv.Privilege
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpObjUHandler : MessageHandler<OpObjUMessage> {

    override fun handle(client: Client, world: World, message: OpObjUMessage) {
        /*
         * If tile is too far away, don't process it.
         */
        val tile = Tile(message.x, message.z, client.tile.height)
        if (!tile.viewableFrom(client.tile, Player.TILE_VIEW_DISTANCE)) {
            return
        }

        if (!client.lock.canGroundItemInteract() || !client.lock.canItemInteract()) {
            return
        }


        val componentHash = message.componentHash
        val fromInterface = componentHash shr 16
        val fromComponent = componentHash and 0xFFFF

        val chunk = world.chunks.getOrCreate(tile)
        val groundItem = chunk.getEntities<GroundItem>(tile, EntityType.GROUND_ITEM).firstOrNull { it.item == message.groundItem && it.canBeViewedBy(client) } ?: return

        if (message.movementType == 1 && world.privileges.isEligible(client.privilege, Privilege.ADMIN_POWER)) {
            client.moveTo(groundItem.tile)
        }

        /**
         * Handles magic spells
         */
        if (message.inventoryItem == -1) {
            client.attr[INTERACTING_GROUNDITEM_ATTR] = WeakReference(groundItem)
            val handled = world.plugins.executeSpellOnGroundItem(client, componentHash)
            if (!handled && world.devContext.debugMagicSpells) {
                client.writeConsoleMessage("Unhandled spell on ground item: [item=[${groundItem}], slot=${message.slot}, from_component=[$fromInterface:$fromComponent]]")
            }
            return
        }

        val item = client.inventory[message.slot] ?: return

        client.closeInterfaceModal()
        client.interruptQueues()
        client.resetInteractions()

        client.attr[INTERACTING_ITEM] = WeakReference(item)
        client.attr[INTERACTING_ITEM_ID] = item.id
        client.attr[INTERACTING_ITEM_SLOT] = message.slot
        client.attr[INTERACTING_OPT_ATTR] = GroundItemPathAction.ITEM_ON_GROUND_ITEM_OPTION
        client.attr[INTERACTING_GROUNDITEM_ATTR] = WeakReference(groundItem)
        client.executePlugin(GroundItemPathAction.walkPlugin)
    }
}