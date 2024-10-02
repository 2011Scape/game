package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.DARK_WIZARD_174)

val table = DropTableFactory
val wizard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(1024)

            obj(Items.STAFF, slots = 64)
            obj(Items.BLACK_ROBE, slots = 24)
            obj(Items.WIZARD_HAT_1017, slots = 32)

            obj(Items.CHAOS_RUNE, quantity = 2, slots = 64)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 64)

            obj(Items.EARTH_RUNE, quantity = 36, slots = 32)
            obj(Items.AIR_RUNE, quantity = 10, slots = 24)
            obj(Items.WATER_RUNE, quantity = 10, slots = 24)
            obj(Items.EARTH_RUNE, quantity = 10, slots = 24)
            obj(Items.FIRE_RUNE, quantity = 10, slots = 24)
            obj(Items.AIR_RUNE, quantity = 18, slots = 16)
            obj(Items.WATER_RUNE, quantity = 18, slots = 16)
            obj(Items.EARTH_RUNE, quantity = 18, slots = 16)
            obj(Items.FIRE_RUNE, quantity = 18, slots = 16)

            obj(Items.NATURE_RUNE, quantity = 4, slots = 56)
            obj(Items.CHAOS_RUNE, quantity = 5, slots = 48)
            obj(Items.MIND_RUNE, quantity = 10, slots = 24)
            obj(Items.BODY_RUNE, quantity = 10, slots = 24)
            obj(Items.MIND_RUNE, quantity = 18, slots = 16)
            obj(Items.BODY_RUNE, quantity = 18, slots = 16)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 16)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 8)
            obj(Items.LAW_RUNE, quantity = 3, slots = 8)

            obj(Items.WATER_TALISMAN, slots = 8)
            obj(Items.FIRE_TALISMAN, slots = 8)

            obj(Items.COINS_995, quantity = 1, slots = 136)
            obj(Items.COINS_995, quantity = 4, slots = 56)
            obj(Items.COINS_995, quantity = 29, slots = 24)
            obj(Items.COINS_995, quantity = 30, slots = 8)

            nothing(slots = 128)
        }
    }

table.register(wizard, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
            spell = 17
        }
        stats {
            hitpoints = 120
            attack = 5
            strength = 2
            defence = 5
            magic = 6
            ranged = 1
        }
        bonuses {
            attackStab = 0
            attackCrush = 0
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 3
            defenceRanged = 0
        }
        anims {
            attack = 422
            death = 836
            block = 404
        }
        aggro {
            radius = 4
        }
    }
}
