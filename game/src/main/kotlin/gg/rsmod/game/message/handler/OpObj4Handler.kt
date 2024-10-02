package gg.rsmod.game.message.handler

import gg.rsmod.game.action.GroundItemPathAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpObj4Message
import gg.rsmod.game.model.EntityType
import gg.rsmod.game.model.ExamineEntityType
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.INTERACTING_GROUNDITEM_ATTR
import gg.rsmod.game.model.attr.INTERACTING_OPT_ATTR
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.priv.Privilege
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpObj4Handler : MessageHandler<OpObj4Message> {

    override fun handle(client: Client, world: World, message: OpObj4Message) {
        /*
         * If tile is too far away, don't process it.
         */
        val tile = Tile(message.x, message.z, client.tile.height)
        if (!tile.viewableFrom(client.tile, Player.TILE_VIEW_DISTANCE)) {
            return
        }

        if (!client.lock.canGroundItemInteract()) {
            return
        }

        log(client, "Ground Item action 4: item=%d, x=%d, z=%d, movement=%d", message.item, message.x, message.z, message.movementType)
        world.sendExamine(client, message.item, ExamineEntityType.ITEM)
    }
}