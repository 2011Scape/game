package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.api.cfg.Npcs

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.POISON_SPIDER_1009)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BIG_SPIDER_DEATH)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
            attackStyle = StyleType.STAB
            poisonDamage = 3
        }
        stats {
            hitpoints = 250
            attack = 28
            strength = 28
            defence = 28
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 17
            defenceCrush = 10
            defenceMagic = 14
            defenceRanged = 14
        }
        anims {
            attack = 5327
            block = 5328
            death = 5329
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.SPIDER
            experience = 25.0
            level = 1
        }
    }
}