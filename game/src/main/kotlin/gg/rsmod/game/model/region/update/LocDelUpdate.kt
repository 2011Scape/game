package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.LocDelMessage
import gg.rsmod.game.model.entity.GameObject

/**
 * Represents an update where a [GameObject] is removed.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class LocDelUpdate(override val type: EntityUpdateType,
                   override val entity: GameObject) : EntityUpdate<GameObject>(type, entity) {

    override fun toMessage(): Message = LocDelMessage(entity.settings.toInt(),
            ((entity.tile.chunkOffsetX) shl 4) or (entity.tile.chunkOffsetZ and 0x7))
}