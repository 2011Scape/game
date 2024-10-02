package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PriestInPeril

/**
 * @author Harley <https://github.com/HarleyGilpin>
 * Start Date: 5/19/2023
 */

val table = DropTableFactory
val templeGuardian =
    table.build {
        guaranteed {
            obj(Items.BONES, quantity = 1)
        }
    }

table.register(templeGuardian, Npcs.TEMPLE_GUARDIAN)

on_npc_pre_death(Npcs.TEMPLE_GUARDIAN) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DOG_DEATH)
    if (p.getCurrentStage(PriestInPeril) == 2) {
        p.advanceToNextStage(PriestInPeril)
    }
}

on_npc_death(Npcs.TEMPLE_GUARDIAN) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(Npcs.TEMPLE_GUARDIAN) {
    configs {
        attackSpeed = 4
        respawnDelay = 15
    }
    stats {
        hitpoints = 450
        attack = 20
        strength = 20
        defence = 20
        magic = 1
        ranged = 1
    }
    bonuses {
        defenceMagic = 999999
    }
    anims {
        attack = 6559
        death = 6558
        block = 6557
    }
}
