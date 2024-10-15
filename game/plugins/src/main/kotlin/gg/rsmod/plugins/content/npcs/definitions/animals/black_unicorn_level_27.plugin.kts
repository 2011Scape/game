package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.BLACK_UNICORN)

val table = DropTableFactory
val black_unicorn =
    table.build {
        guaranteed {
            obj(Items.BONES)
            obj(Items.UNICORN_HORN)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 130)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 60)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 40)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 765)
        }
    }

table.register(black_unicorn, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ANGER_UNICORN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 90
        }
        stats {
            hitpoints = 290
            attack = 21
            strength = 21
            defence = 23
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 0
            attackCrush = 0
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 0
            defenceRanged = 0
        }
        anims {
            attack = 6376
            death = 6377
            block = 6375
        }
    }
}
