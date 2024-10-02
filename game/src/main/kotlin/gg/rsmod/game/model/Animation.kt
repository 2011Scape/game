package gg.rsmod.game.model

import gg.rsmod.game.model.entity.Entity
import gg.rsmod.game.model.entity.GameObject

data class Animation(
    val id: Int,
    val delay: Int = 0,
)

class TileAnimation private constructor(
    val gameObject: GameObject,
    val animation: Int,
) : Entity() {
    override val entityType: EntityType
        get() = EntityType.LOC_ANIM
}
