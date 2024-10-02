package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.GIANT_BAT)

val table = DropTableFactory
val giantBat =
    table.build {
        guaranteed {
            obj(Items.BAT_BONES)
        }
    }

table.register(giantBat, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BAT_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 35
        }
        stats {
            hitpoints = 320
            attack = 22
            strength = 22
            defence = 22
        }
        bonuses {
            defenceStab = 10
            defenceSlash = 10
            defenceCrush = 12
            defenceMagic = 10
            defenceRanged = 8
        }
        anims {
            attack = 4915
            death = 4917
            block = 4916
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.BAT
            level = 1
            experience = 32.0
        }
    }
}
