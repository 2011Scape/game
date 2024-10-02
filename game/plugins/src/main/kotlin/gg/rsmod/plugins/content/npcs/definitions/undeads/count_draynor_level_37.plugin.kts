package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.attr.killedCountDraynor
import gg.rsmod.plugins.content.drops.DropTableFactory

val count = Npcs.COUNT_DRAYNOR

val table = DropTableFactory
val countTable =
    table.build {

        guaranteed {
            nothing(128)
        }
    }

table.register(countTable)

on_npc_pre_death(count) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.VAMPIRE_DEATH)
    npc.animate(1568)
    npc.queue {
        wait(2)
        val distance = npc.tile.getDistance(p.tile)
        if (distance > 1) {
            p.walkTo(
                npc.tile.transform(p.faceDirection.getDeltaX(), p.faceDirection.getDeltaZ()),
                MovementQueue.StepType.FORCED_RUN,
                detectCollision = true,
            )
        }
        npc.facePawn(p)
        npc.animate(id = 12604)
        p.facePawn(npc)
        p.animate(id = 12606, idleOnly = true)
    }
}

on_npc_death(count) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.attr.put(killedCountDraynor, true)
    p.lockingQueue(priority = TaskPriority.STRONG) {
        this.chatPlayer("I should tell Morgan that I've killed the vampyre!", facialExpression = FacialExpression.HAPPY)
    }
}

set_combat_def(count) {
    configs {
        attackSpeed = 4
        respawnDelay = 0
    }

    stats {
        hitpoints = 350
        attack = 30
        strength = 25
        defence = 30
        magic = 1
        ranged = 1
    }

    bonuses {
        defenceStab = 2
        defenceSlash = 1
        defenceCrush = 3
        defenceMagic = 0
        defenceRanged = 0
    }

    anims {
        attack = 3331
        block = 3332
        death = 12604
    }
}
