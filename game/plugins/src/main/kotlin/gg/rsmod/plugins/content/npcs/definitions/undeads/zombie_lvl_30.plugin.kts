package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs

val ids = intArrayOf(Npcs.ZOMBIE_5375, Npcs.ZOMBIE_4392, Npcs.ZOMBIE_5376)
val table = DropTableFactory
val zombie =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 128)

            obj(Items.IRON_HATCHET, quantity = 1, slots = 3)
            obj(Items.BRONZE_LONGSWORD, quantity = 1, slots = 1)
            obj(Items.BRONZE_MED_HELM, quantity = 1, slots = 4)
            obj(Items.BRONZE_KITESHIELD, quantity = 1, slots = 3)

            obj(Items.IRON_ARROW, quantity = 5, slots = 7)
            obj(Items.BODY_RUNE, quantity = 6, slots = 5)
            obj(Items.MIND_RUNE, quantity = 5, slots = 5)
            obj(Items.AIR_RUNE, quantity = 13, slots = 4)
            obj(Items.IRON_ARROW, quantity = 8, slots = 4)
            obj(Items.STEEL_ARROW, quantity = 6, slots = 2)
            obj(Items.NATURE_RUNE, quantity = 6, slots = 1)
            obj(Items.COSMIC_RUNE, quantity = 4, slots = 1)
            obj(Items.FIRE_RUNE, quantity = 7, slots = 1)

            table(Herbs.minorHerbTable, slots = 25)

            obj(Items.COINS_995, quantity = 10, slots = 36)
            obj(Items.COINS_995, quantity = 4, slots = 4)
            obj(Items.COINS_995, quantity = 18, slots = 3)
            obj(Items.COINS_995, quantity = 13, slots = 2)
            obj(Items.COINS_995, quantity = 28, slots = 2)

            nothing(slots = 8)
            obj(Items.FISHING_BAIT, quantity = 5, slots = 5)
            obj(Items.COPPER_ORE, quantity = 1, slots = 2)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 30)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 17)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 14)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 937)
        }
    }

table.register(zombie, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ZOMBIE_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 35
        }
        stats {
            hitpoints = 300
            attack = 25
            strength = 29
            defence = 20
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
            attack = 5578
            block = 5579
            death = 5580
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.ZOMBIE
            level = 1
            experience = 30.0
        }
    }
}
