package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.MOSS_GIANT, Npcs.MOSS_GIANT_1587, Npcs.MOSS_GIANT_1588, Npcs.MOSS_GIANT_4688)

val table = DropTableFactory
val mossGiant =
    table.build {
        guaranteed {
            obj(Items.BIG_BONES)
        }
        main {
            total(128)

            obj(Items.BLACK_SQ_SHIELD, slots = 5)
            obj(Items.MAGIC_STAFF, slots = 2)
            obj(Items.STEEL_MED_HELM, slots = 2)
            obj(Items.MITHRIL_SWORD, slots = 2)
            obj(Items.MITHRIL_SPEAR, slots = 2)
            obj(Items.STEEL_KITESHIELD, slots = 1)
            obj(Items.LAW_RUNE, quantity = 3, slots = 4)
            obj(Items.AIR_RUNE, quantity = 18, slots = 3)
            obj(Items.EARTH_RUNE, quantity = 27, slots = 3)
            obj(Items.CHAOS_RUNE, quantity = 7, slots = 3)
            obj(Items.NATURE_RUNE, quantity = 6, slots = 3)
            obj(Items.COSMIC_RUNE, quantity = 3, slots = 2)
            obj(Items.IRON_ARROW, quantity = 15, slots = 2)
            obj(Items.STEEL_ARROW, quantity = 30, slots = 1)
            obj(Items.DEATH_RUNE, quantity = 3, slots = 1)
            obj(Items.BLOOD_RUNE, quantity = 1, slots = 1)
            obj(Items.COINS_995, quantity = 37, slots = 19)
            obj(Items.COINS_995, quantity = 2, slots = 8)
            obj(Items.COINS_995, quantity = 119, slots = 10)
            obj(Items.COINS_995, quantity = 300, slots = 2)
            obj(Items.STEEL_BAR, slots = 6)
            obj(Items.COAL, slots = 1)
            obj(Items.SPINACH_ROLL, slots = 1)

            table(Herbs.minorHerbTable, slots = 5)
            table(Seeds.uncommonSeedTable, slots = 35)
            table(Gems.gemTable, slots = 4)
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

table.register(mossGiant, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GIANT_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 6
            respawnDelay = 30
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 600
            attack = 30
            strength = 30
            defence = 30
        }
        bonuses {
            attackStab = 33
            attackCrush = 31
        }
        anims {
            attack = 4652
            death = 4653
            block = 4651
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.MOSS_GIANT
            level = 1
            experience = 60.0
        }
    }
}
