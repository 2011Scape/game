package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.model.TileAnimation

class LocAnimUpdate(
    override val type: EntityUpdateType,
    override val entity: TileAnimation,
) : EntityUpdate<TileAnimation>(type, entity) {
    override fun toMessage(): Message =
        LocAnimMessage(
            gameObject = entity.gameObject,
            animation = entity.animation,
        )
}
