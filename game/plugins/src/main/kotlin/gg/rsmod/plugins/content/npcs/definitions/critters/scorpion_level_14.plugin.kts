package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val table = DropTableFactory
val scorpion =
    table.build {
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 30)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 70)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 885)
        }
    }

table.register(scorpion, Npcs.SCORPION)

on_npc_pre_death(Npcs.SCORPION) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SCORPION_DEATH)
}

on_npc_death(Npcs.SCORPION) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = Npcs.SCORPION) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 170
        attack = 11
        strength = 12
        defence = 11
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
        experience = 17.0
    }
}
