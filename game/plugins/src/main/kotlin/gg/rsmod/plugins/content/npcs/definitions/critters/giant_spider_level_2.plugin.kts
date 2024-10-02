package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.GIANT_SPIDER, Npcs.GIANT_SPIDER_60, Npcs.GIANT_SPIDER_12352)

val table = DropTableFactory
val spider =
    table.build {
        main {
            total(48)
            obj(Items.AIR_RUNE, quantityRange = 4..6, slots = 7)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 7)
            obj(Items.FIRE_RUNE, quantity = 6, slots = 7)
            obj(Items.BODY_RUNE, quantity = 7, slots = 3)
            nothing(24)
        }

        table("Secondary") {
            total(128)
            obj(Items.BRONZE_MED_HELM, slots = 48)
            obj(Items.BRONZE_BOLTS, slots = 12)
            obj(Items.BRONZE_ARROW, slots = 12)
            obj(Items.LONGBOW, slots = 4)
            obj(Items.BRONZE_KITESHIELD, slots = 1)
            nothing(51)
        }

        table("Tertiary") {
            total(128)
            obj(Items.STAFF_OF_AIR, slots = 1)
            obj(Items.SLING, slots = 1)
            nothing(126)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 90)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 11)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 9)
            nothing(slots = 860)
        }
    }

table.register(spider, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BIG_SPIDER_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        stats {
            hitpoints = 50
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -10
            attackCrush = -10
            defenceStab = -10
            defenceSlash = -10
            defenceCrush = -10
            defenceMagic = -10
            defenceRanged = -10
        }
        anims {
            attack = 5327
            block = 5328
            death = 5329
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.SPIDER
            level = 1
            experience = 5.0
        }
    }
}
