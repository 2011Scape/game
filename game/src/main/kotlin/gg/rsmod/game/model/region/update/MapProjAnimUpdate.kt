package gg.rsmod.game.model.region.update

import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.MapProjAnimMessage
import gg.rsmod.game.model.entity.Projectile

/**
 * Represents an update where a [Projectile] is spawned.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class MapProjAnimUpdate(override val type: EntityUpdateType,
                        override val entity: Projectile) : EntityUpdate<Projectile>(type, entity) {

    override fun toMessage(): Message = if (entity.targetPawn != null) {
        val targetIndex = if (entity.targetPawn.entityType.isNpc) entity.targetPawn.index + 1 else -(entity.targetPawn.index + 1)
        MapProjAnimMessage(
                start = ((entity.tile.chunkOffsetX shl 3) or entity.tile.chunkOffsetZ),
                pawnTargetIndex = targetIndex, offsetX = entity.targetTile.x - entity.tile.x, offsetZ = entity.targetTile.z - entity.tile.z,
                gfx = entity.gfx, startHeight = entity.startHeight, endHeight = entity.endHeight,
                delay = entity.delay, lifespan = entity.lifespan, angle = entity.angle, steepness = entity.steepness)
    } else {
        MapProjAnimMessage(
                start = ((entity.tile.chunkOffsetX shl 3) or entity.tile.chunkOffsetZ),
                pawnTargetIndex = 0, offsetX = entity.targetTile.x - entity.tile.x, offsetZ = entity.targetTile.z - entity.tile.z,
                gfx = entity.gfx, startHeight = entity.startHeight, endHeight = entity.endHeight, delay = entity.delay,
                lifespan = entity.lifespan, angle = entity.angle, steepness = entity.steepness)
    }
}