package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.ANKOU_4382)
val table = DropTableFactory
val ankou =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(256)
            obj(Items.BLACK_ROBE, quantity = 1, slots = 16)
            obj(Items.BLACK_KNIFE, quantity = 1, slots = 9)
            obj(Items.BATTLESTAFF, quantity = 1, slots = 9)
            obj(Items.ADAMANT_ARROW, quantityRange = 5..14, slots = 9)
            obj(Items.ADAMANT_ARROW, quantity = 25, slots = 9)
            obj(Items.WIZARD_HAT_1017, quantity = 1, slots = 2)
            obj(Items.BLOOD_RUNE, quantityRange = 5..6, slots = 16)
            obj(Items.BLOOD_RUNE, quantityRange = 10..12, slots = 16)
            obj(Items.BLOOD_RUNE, quantity = 16, slots = 16)
            obj(Items.LAW_RUNE, quantity = 2, slots = 9)
            obj(Items.FIRE_RUNE, quantity = 1, slots = 9)
            obj(Items.DEATH_RUNE, quantityRange = 10..20, slots = 9)
            obj(Items.BASS, quantity = 1, slots = 16)
            obj(Items.FRIED_MUSHROOMS, quantity = 1, slots = 9)
            obj(Items.COINS_995, quantityRange = 1..500, slots = 16)
            obj(Items.LEFT_SKULL_HALF, quantity = 1, slots = 9)
            obj(Items.RUNE_ESSENCE_NOTED, quantity = 15, slots = 9)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 15, slots = 9)
            obj(Items.WEAPON_POISON, quantity = 1, slots = 9)
            obj(Items.MITHRIL_ORE_NOTED, quantityRange = 3..7, slots = 9)
            obj(Items.FIRE_TALISMAN, quantity = 1, slots = 2)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 2)
            obj(Items.CLUE_SCROLL_HARD, quantity = 1, slots = 1)
            table(Seeds.uncommonSeedTable, slots = 3)
            table(Herbs.minorHerbTable, slots = 8)
            table(Gems.gemTable, slots = 5)
            nothing(20)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 240)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 20)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 20)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 715)
        }
    }

table.register(ankou, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SKELETON_DEATH)
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
            hitpoints = 650
            attack = 80
            strength = 70
            defence = 70
        }
        anims {
            attack = 422
            block = 424
            death = 836
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.ANKOU
            level = 1
            experience = 65.0
        }
    }
}
