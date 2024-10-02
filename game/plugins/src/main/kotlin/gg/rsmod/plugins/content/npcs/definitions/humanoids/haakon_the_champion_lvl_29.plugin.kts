package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.HAAKON_THE_CHAMPION)

val table = DropTableFactory
val barbarian =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(1024)
            obj(Items.IRON_HATCHET, slots = 48)
            obj(Items.BRONZE_BATTLEAXE, slots = 32)
            obj(Items.IRON_MACE, slots = 8)

            obj(Items.CHAOS_RUNE, quantity = 3, slots = 32)
            obj(Items.BRONZE_ARROW, quantity = 10, slots = 24)
            obj(Items.EARTH_RUNE, quantity = 5, slots = 24)
            obj(Items.IRON_ARROW, quantity = 8, slots = 24)
            obj(Items.FIRE_RUNE, quantity = 8, slots = 16)
            obj(Items.MIND_RUNE, quantity = 10, slots = 16)
            obj(Items.LAW_RUNE, quantity = 2, slots = 8)

            obj(Items.COINS_995, quantity = 8, slots = 317)
            obj(Items.COINS_995, quantity = 12, slots = 76)
            obj(Items.COINS_995, quantity = 25, slots = 45)
            obj(Items.COINS_995, quantity = 32, slots = 26)

            nothing(288)

            obj(Items.TIN_ORE, slots = 8)
            obj(Items.BEAR_FUR, slots = 8)
            obj(Items.BEER, slots = 8)
            obj(Items.COOKED_MEAT, slots = 8)
            obj(Items.RING_MOULD, slots = 8)
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
            attackSpeed = 6
            respawnDelay = 50
        }
        stats {
            hitpoints = 350
            attack = 22
            strength = 22
            defence = 25
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 8
            attackCrush = 9
            defenceStab = 12
            defenceSlash = 14
            defenceCrush = 10
            defenceMagic = -1
            defenceRanged = 11
        }
        anims {
            attack = 7048
            death = 836
            block = 3240
        }
    }
}
