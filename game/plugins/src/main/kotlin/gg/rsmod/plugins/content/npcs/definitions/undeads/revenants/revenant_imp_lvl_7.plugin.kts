package gg.rsmod.plugins.content.npcs.definitions.undeads.revenants

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType

val imp = Npcs.REVENANT_IMP

set_combat_def(imp) {
    configs {
        attackSpeed = 5
        respawnDelay = 30
    }
    species {
        NpcSpecies.UNDEAD
    }
    stats {
        hitpoints = 100
        attack = 80
        strength = 80
        defence = 50
        magic = 80
        ranged = 80
    }
    bonuses {
        defenceStab = 50
        defenceSlash = 50
        defenceCrush = 50
        defenceMagic = 50
        defenceRanged = 50
    }
    anims {
        attack = Anims.REVENANT_IMP_MELEE_ATTACK
        block = Anims.REVENANT_IMP_BLOCK
        death = Anims.REVENANT_IMP_DEATH
    }
    aggro {
        radius = 5
    }
    slayer {
        level = 1
        experience = 10.0
        assignment = SlayerAssignment.REVENANT
    }
}
