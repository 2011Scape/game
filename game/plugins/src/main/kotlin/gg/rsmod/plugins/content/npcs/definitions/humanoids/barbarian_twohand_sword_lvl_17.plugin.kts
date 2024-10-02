package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.BARBARIAN_3249)

val table = DropTableFactory
val barbarian =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(1024)
            obj(Items.BRONZE_HATCHET, slots = 48)
            obj(Items.STAFF, slots = 32)
            obj(Items.IRON_MACE, slots = 8)

            obj(Items.CHAOS_RUNE, quantity = 2, slots = 32)
            obj(Items.BRONZE_ARROW, quantity = 15, slots = 24)
            obj(Items.EARTH_RUNE, quantity = 2, slots = 24)
            obj(Items.FIRE_RUNE, quantity = 5, slots = 16)
            obj(Items.MIND_RUNE, quantity = 5, slots = 16)
            obj(Items.LAW_RUNE, quantity = 2, slots = 8)

            obj(Items.COINS_995, quantity = 5, slots = 341)
            obj(Items.COINS_995, quantity = 8, slots = 76)
            obj(Items.COINS_995, quantity = 17, slots = 45)
            obj(Items.COINS_995, quantity = 27, slots = 26)

            nothing(288)

            obj(Items.TIN_ORE, slots = 8)
            obj(Items.BEAR_FUR, slots = 8)
            obj(Items.BEER, slots = 8)
            obj(Items.COOKED_MEAT, slots = 8)
            obj(Items.RING_MOULD, slots = 8)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 50)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 10)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 5)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 930)
        }
        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(barbarian, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BARBARIAN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 240
            attack = 15
            strength = 14
            defence = 10
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 9
            attackCrush = 15
            defenceStab = 0
            defenceSlash = 3
            defenceCrush = 2
            defenceMagic = -3
            defenceRanged = 2
        }
        anims {
            attack = 7048
            death = 836
            block = 3240
        }
    }
}
