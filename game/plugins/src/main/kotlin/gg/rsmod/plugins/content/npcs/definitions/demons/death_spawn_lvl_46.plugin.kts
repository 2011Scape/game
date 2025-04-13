package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.DEATH_SPAWN)

val table = DropTableFactory
val deathSpawn =
    table.build {
        guaranteed {
            obj(Items.ACCURSED_ASHES)
        }
    }

table.register(deathSpawn, *ids)

on_npc_spawn(npc = Npcs.DEATH_SPAWN) {
    npc.graphic(Gfx.DEATH_SPAWN_SPAWN, 0)
}

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.IMP_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 0
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 600
            attack = 67
            strength = 7
            defence = 30
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 20
            defenceCrush = 20
            defenceMagic = 0
            defenceRanged = 20
        }
        anims {
            attack = Anims.DEATH_SPAWN_ATTACK
            death = Anims.DEATH_SPAWN_DEATH
            block = Anims.DEATH_SPAWN_BLOCK
        }
    }
}
