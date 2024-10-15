package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids = intArrayOf(Npcs.SKELETON, Npcs.SKELETON_5332, Npcs.SKELETON_5333, Npcs.SKELETON_5334)
val table = DropTableFactory
val skeleton =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 1024)

            obj(Items.BRONZE_ARROW, quantity = 2, slots = 56)
            obj(Items.BRONZE_ARROW, quantity = 5, slots = 32)
            obj(Items.IRON_ARROW, quantity = 1, slots = 32)
            obj(Items.STEEL_ARROW, quantity = 1, slots = 8)

            obj(Items.AIR_RUNE, quantity = 15, slots = 16)
            obj(Items.EARTH_RUNE, quantity = 3, slots = 16)
            obj(Items.FIRE_RUNE, quantity = 2, slots = 16)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 16)
            obj(Items.NATURE_RUNE, quantity = 3, slots = 16)

            table(Herbs.minorHerbTable, slots = 49)

            obj(Items.COINS_995, quantity = 2, slots = 175)
            obj(Items.COINS_995, quantity = 12, slots = 200)
            obj(Items.COINS_995, quantity = 4, slots = 56)
            obj(Items.COINS_995, quantity = 16, slots = 32)
            obj(Items.COINS_995, quantity = 25, slots = 32)
            obj(Items.COINS_995, quantity = 33, slots = 32)
            obj(Items.COINS_995, quantity = 48, slots = 8)

            nothing(slots = 144)
            obj(Items.IRON_DAGGER, quantity = 1, slots = 48)
            obj(Items.FIRE_TALISMAN, quantity = 1, slots = 16)
            obj(Items.IRON_ORE, quantity = 1, slots = 8)
            obj(Items.GRAIN, quantity = 1, slots = 8)

            table(Gems.gemTable, slots = 8)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 50)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 12)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 14)
            nothing(slots = 894)
        }
    }

table.register(skeleton, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SKELETON_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(npc = it) {
        configs {
            attackSpeed = 4
            respawnDelay = 70
        }
        stats {
            hitpoints = 290
            attack = 15
            strength = 18
            defence = 17
        }
        bonuses {
            defenceStab = 5
            defenceSlash = 5
            defenceCrush = -5
            defenceRanged = 5
        }
        anims {
            attack = 5485
            block = 5489
            death = 5491
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.SKELETON
            level = 1
            experience = 29.0
        }
    }
}
