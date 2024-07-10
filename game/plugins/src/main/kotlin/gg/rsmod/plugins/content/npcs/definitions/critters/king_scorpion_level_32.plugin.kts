package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment

on_npc_pre_death(Npcs.KING_SCORPION) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SCORPION_DEATH)
}

set_combat_def(npc = Npcs.KING_SCORPION) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 300
        attack = 30
        strength = 29
        defence = 23
    }
    bonuses {
        defenceStab = 5
        defenceSlash = 15
        defenceCrush = 15
        defenceRanged = 5
    }
    anims {
        attack = 6254
        block = 6255
        death = 6256
    }
    aggro {
        radius = 4
    }
    slayer {
        assignment = SlayerAssignment.SCORPION
        level = 1
        experience = 37.0
    }
}
