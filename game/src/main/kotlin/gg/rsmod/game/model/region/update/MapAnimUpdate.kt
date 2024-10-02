package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.MapAnimMessage
import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.TileGraphic

/**
 * Represents an update where a [Graphic] is spawned on a tile.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class MapAnimUpdate(
    override val type: EntityUpdateType,
    override val entity: TileGraphic,
) : EntityUpdate<TileGraphic>(type, entity) {
    override fun toMessage(): Message =
        MapAnimMessage(
            id = entity.id,
            height = entity.height,
            delay = entity.delay,
            tile = ((entity.tile.height shl 28) or (entity.tile.x shl 14) or (entity.tile.z and 0x3fff) or (1 shl 30)),
            rotation = entity.rotation and 0x7,
        )
}
