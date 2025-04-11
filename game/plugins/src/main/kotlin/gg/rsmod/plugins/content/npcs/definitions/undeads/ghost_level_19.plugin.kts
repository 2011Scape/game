package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType

val ghosts = intArrayOf(Npcs.GHOST, Npcs.GHOST_104, Npcs.GHOST_5349, Npcs.GHOST_5350, Npcs.GHOST_5351, Npcs.GHOST_5352)

on_npc_pre_death(*ghosts) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GHOST_DEATH)
}

ghosts.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 40
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 250
            attack = 13
            strength = 13
            defence = 18
        }
        bonuses {
            defenceStab = 5
            defenceSlash = 5
            defenceCrush = 5
            defenceMagic = -5
            defenceRanged = 5
        }
        anims {
            if (it == Npcs.GHOST) {
                attack = Anims.GHOST_ATTACK_2
                death = Anims.GHOST_DEATH_2
                block = Anims.GHOST_BLOCK_2
            } else {
                attack = Anims.GHOST_ATTACK
                death = Anims.GHOST_DEATH
                block = Anims.GHOST_BLOCK
            }
        }
        slayer {
            assignment = SlayerAssignment.GHOST
            level = 1
            experience = 25.0
        }
    }
}
