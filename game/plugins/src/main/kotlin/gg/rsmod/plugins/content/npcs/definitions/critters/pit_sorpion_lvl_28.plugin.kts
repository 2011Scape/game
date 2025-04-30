package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment

on_npc_pre_death(Npcs.PIT_SCORPION) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SCORPION_DEATH)
}

set_combat_def(npc = Npcs.PIT_SCORPION) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 320
        attack = 20
        strength = 25
        defence = 28
    }
    bonuses {
        defenceStab = 5
        defenceSlash = 13
        defenceCrush = 13
        defenceRanged = 5
    }
    anims {
        attack = Anims.SCORPION_ATTACK
        block = Anims.SCORPION_BLOCK
        death = Anims.SCORPION_DEATH
    }
    aggro {
        radius = 4
    }
    slayer {
        assignment = SlayerAssignment.SCORPION
        level = 1
        experience = 32.0
    }
}
