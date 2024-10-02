package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs

val ids =
    intArrayOf(
        Npcs.OUTLAW,
        Npcs.OUTLAW_5843,
        Npcs.OUTLAW_5844,
        Npcs.OUTLAW_5845,
        Npcs.OUTLAW_5846,
        Npcs.OUTLAW_5847,
        Npcs.OUTLAW_5848,
        Npcs.OUTLAW_5849,
        Npcs.OUTLAW_5850,
        Npcs.OUTLAW_5851,
    )

val table = DropTableFactory
val outlaw =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(128)

            obj(Items.BRONZE_MED_HELM, slots = 2)
            obj(Items.MIND_RUNE, quantity = 9, slots = 2)
            obj(Items.WATER_RUNE, quantity = 6, slots = 2)
            obj(Items.EARTH_RUNE, quantity = 5, slots = 2)
            obj(Items.COINS_995, quantity = 5, slots = 12)
            obj(Items.COINS_995, quantity = 15, slots = 4)
            obj(Items.COINS_995, quantity = 25, slots = 1)
            obj(Items.ROPE, slots = 46)
            obj(Items.FISHING_BAIT, slots = 15)
            obj(Items.CABBAGE, slots = 6)
            obj(Items.COPPER_ORE, slots = 2)
            obj(Items.KNIFE, slots = 1)

            table(Herbs.minorHerbTable, slots = 32)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 360)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 30)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 7)
            nothing(slots = 573)
        }
    }

table.register(outlaw, *ids)

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
            respawnDelay = 2
            xpMultiplier = 0.025
        }
        stats {
            hitpoints = 200
            attack = 35
            strength = 30
            defence = 25
        }
        bonuses {
            attackCrush = -21
        }
        anims {
            attack = 390
            block = 424
            death = 836
        }
        aggro {
            radius = 4
        }
    }
}
