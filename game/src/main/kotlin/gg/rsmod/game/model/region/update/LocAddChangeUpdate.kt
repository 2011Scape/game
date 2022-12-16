package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.LocAddChangeMessage
import gg.rsmod.game.model.entity.GameObject

/**
 * Represents an update where a [GameObject] is spawned.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class LocAddChangeUpdate(override val type: EntityUpdateType,
                         override val entity: GameObject) : EntityUpdate<GameObject>(type, entity) {

    override fun toMessage(): Message = LocAddChangeMessage(entity.id, entity.settings.toInt(),
        ((entity.tile.chunkOffsetX) shl 4) or (entity.tile.chunkOffsetZ))
}