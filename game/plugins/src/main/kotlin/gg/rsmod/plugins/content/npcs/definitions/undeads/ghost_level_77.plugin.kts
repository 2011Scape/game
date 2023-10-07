package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType

val ids = intArrayOf(Npcs.GHOST_4387, Npcs.GHOST_5369, Npcs.GHOST_5370, Npcs.GHOST_5371)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GHOST_DEATH)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 40
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 800
            attack = 63
            strength = 63
            defence = 68
        }
        bonuses {
            defenceStab = 55
            defenceSlash = 55
            defenceCrush = 5
            defenceMagic = 55
            defenceRanged = 55
        }
        anims {
            attack = 5532
            death = 5534
            block = 5533
        }
        slayer {
            assignment = SlayerAssignment.GHOST
            level = 1
            experience = 80.0
        }
    }
}
