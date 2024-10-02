package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids =
    intArrayOf(
        Npcs.INFERNAL_MAGE,
        Npcs.INFERNAL_MAGE_1644,
        Npcs.INFERNAL_MAGE_1645,
        Npcs.INFERNAL_MAGE_1646,
        Npcs.INFERNAL_MAGE_1647,
    )

val table = DropTableFactory

val infernalMage =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(1024)
            // ARMOUR
            obj(Items.MYSTIC_BOOTS_4107, slots = 2) // chance = 1/512
            obj(Items.MYSTIC_HAT_4099, slots = 2) // chance = 1/512
            // STAVES
            obj(Items.STAFF, quantity = 1, slots = 64) // chance = 1/16
            obj(Items.STAFF_OF_FIRE, quantity = 1, slots = 8) // chance = 1/128
            obj(Items.LAVA_BATTLESTAFF, quantity = 1, slots = 1) // chance = 1/1024
            // ELEMENTAL RUNES
            obj(Items.EARTH_RUNE, quantity = 36, slots = 16) // chance = 1/64
            obj(Items.FIRE_RUNE, quantity = 18, slots = 16) // chance = 1/64
            obj(Items.AIR_RUNE, quantity = 10, slots = 24) // chance = 1/42
            obj(Items.AIR_RUNE, quantity = 18, slots = 16) // chance = 1/64
            obj(Items.WATER_RUNE, quantity = 18, slots = 16) // chance = 1/64
            obj(Items.WATER_RUNE, quantity = 10, slots = 24) // chance = 1/42
            obj(Items.EARTH_RUNE, quantity = 10, slots = 48) // chance = 1/21
            obj(Items.FIRE_RUNE, quantity = 10, slots = 48) // chance = 1/21
            // CATALYTIC RUNES
            obj(Items.DEATH_RUNE, quantity = 7, slots = 146)
            obj(Items.MIND_RUNE, quantity = 18, slots = 16)
            obj(Items.BODY_RUNE, quantity = 18, slots = 16)
            obj(Items.BLOOD_RUNE, quantity = 4, slots = 16)
            // COINS
            obj(Items.COINS_995, quantity = 1, slots = 152)
            obj(Items.COINS_995, quantity = 2, slots = 113)
            obj(Items.COINS_995, quantity = 4, slots = 64)
            obj(Items.COINS_995, quantity = 29, slots = 24)
            // OTHER
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 2)
            // NOTHING
            nothing(slots = 190)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 333)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 30)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 20)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 7)
            nothing(slots = 610)
        }
    }

table.register(infernalMage, *ids)

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
            attackSpeed = 6
            respawnDelay = 15
            spell = 995
        }
        stats {
            hitpoints = 600
            attack = 1
            strength = 1
            defence = 60
            magic = 75
            ranged = 1
        }
        bonuses {
            attackStab = 0
            attackCrush = 0
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 40
            defenceRanged = 0
        }
        anims {
            attack = 422
            death = 836
            block = 404
        }
        slayer {
            assignment = SlayerAssignment.INFERNAL_MAGE
            level = 45
            experience = 60.0
        }
    }
}
