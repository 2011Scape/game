package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids = intArrayOf(Npcs.ZOMBIE_75)
val table = DropTableFactory
val zombie = table.build {

    guaranteed {
        obj(Items.BONES)
    }
    main {
        total(total = 128)

        obj(Items.IRON_MACE, quantity = 1, slots = 3)
        obj(Items.IRON_DAGGER, quantity = 1, slots = 2)
        obj(Items.BRONZE_KITESHIELD, quantity = 1, slots = 1)

        obj(Items.MITHRIL_ARROW, quantity = 1, slots = 3)
        obj(Items.AIR_RUNE, quantity = 3, slots = 2)
        obj(Items.BODY_RUNE, quantity = 3, slots = 3)
        obj(Items.CHAOS_RUNE, quantity = 4, slots = 1)
        obj(Items.COSMIC_RUNE, quantity = 2, slots = 1)
        obj(Items.FIRE_RUNE, quantity = 7, slots = 1)

        table(Herbs.minorHerbTable, slots = 30)

        obj(Items.COINS_995, quantity = 10, slots = 21)
        obj(Items.COINS_995, quantity = 18, slots = 11)
        obj(Items.COINS_995, quantity = 26, slots = 7)
        obj(Items.COINS_995, quantity = 35, slots = 7)
        obj(Items.COINS_995, quantity = 1, slots = 2)


        nothing(slots = 3)
        obj(Items.FISHING_BAIT, quantity = 7, slots = 26)
        obj(Items.TINDERBOX_590, quantity = 1, slots = 2)
        obj(Items.EYE_OF_NEWT, quantity = 1, slots = 1)
        obj(Items.TIN_ORE, quantity = 1, slots = 1)

        table(Gems.gemTable, slots = 1)

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
            attack = 19
            strength = 21
            defence = 16
        }
        bonuses {
            attackStab = 5
            attackCrush = 7
            defenceStab = 9
            defenceSlash = 8
            defenceCrush = 12
            defenceMagic = 10
            defenceRanged = 11
        }
        anims {
            attack = 5578
            block = 5579
            death = 5580
        }
        aggro {
            radius = 4
        }
        slayerData {
            slayerAssignment = SlayerAssignment.ZOMBIE
            levelRequirement = 1
            xp = 30.0
        }
    }
}