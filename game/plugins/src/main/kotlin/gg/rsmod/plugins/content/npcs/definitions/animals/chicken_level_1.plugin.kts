package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.CHICKEN, Npcs.CHICKEN_1017, Npcs.CHICKEN_2313, Npcs.CHICKEN_2314, Npcs.CHICKEN_2315)

val table = DropTableFactory
val chicken =
    table.build {
        guaranteed {
            obj(Items.BONES)
            obj(Items.RAW_CHICKEN)
        }
        main {
            total(128)
            obj(Items.FEATHER, quantity = 5, slots = 64)
            obj(Items.FEATHER, quantity = 15, slots = 32)
            nothing(slots = 32)
        }
    }

table.register(chicken, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.CHICKEN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 25
        }
        stats {
            hitpoints = 30
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -47
            attackCrush = -42
            defenceStab = -42
            defenceSlash = -42
            defenceCrush = -42
            defenceMagic = -42
            defenceRanged = -42
        }
        anims {
            attack = Anims.CHICKEN_ATTACK
            death = Anims.CHICKEN_DEATH
            block = Anims.CHICKEN_BLOCK
        }
        slayer {
            level = 1
            experience = 3.0
            assignment = SlayerAssignment.BIRD
        }
    }
}
