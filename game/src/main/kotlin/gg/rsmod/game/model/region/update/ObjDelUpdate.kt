package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.ObjDelMessage
import gg.rsmod.game.model.entity.GroundItem

/**
 * Represents an update where a [GroundItem] is removed.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class ObjDelUpdate(override val type: EntityUpdateType,
                   override val entity: GroundItem) : EntityUpdate<GroundItem>(type, entity) {

    override fun toMessage(): Message = ObjDelMessage(entity.item, ((entity.tile.chunkOffsetX shl 4) or (entity.tile.chunkOffsetZ and 0x7)))
}