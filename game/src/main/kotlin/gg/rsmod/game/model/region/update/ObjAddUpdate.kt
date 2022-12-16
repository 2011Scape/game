package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.ObjAddMessage
import gg.rsmod.game.model.entity.GroundItem

/**
 * Represents an update where a [GroundItem] is spawned.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class ObjAddUpdate(override val type: EntityUpdateType,
                   override val entity: GroundItem) : EntityUpdate<GroundItem>(type, entity) {

    override fun toMessage(): Message = ObjAddMessage(entity.item, entity.amount, ((entity.tile.chunkOffsetX shl 4) or (entity.tile.chunkOffsetZ and 0x7)))
}