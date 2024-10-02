package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.ABYSSAL_LEECH)

val table = DropTableFactory
val guard =
    table.build {
        guaranteed {
            obj(Items.ASHES)
        }

        main {
            total(256)
            // Talismans
            obj(Items.AIR_TALISMAN, slots = 5)
            obj(Items.WATER_TALISMAN, slots = 5)
            obj(Items.EARTH_TALISMAN, slots = 5)
            obj(Items.FIRE_TALISMAN, slots = 5)
            obj(Items.MIND_TALISMAN, slots = 5)
            obj(Items.COSMIC_TALISMAN, slots = 5)
            obj(Items.CHAOS_TALISMAN, slots = 5)
            obj(Items.BODY_TALISMAN, slots = 5)
            obj(Items.NATURE_TALISMAN, slots = 2)
            obj(Items.LAW_TALISMAN, slots = 2)
            obj(Items.ELEMENTAL_TALISMAN, slots = 1)
            // Runecrafting Items
            obj(Items.PURE_ESSENCE, slots = 36)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 1, slots = 36)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 5, slots = 4)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 10, slots = 4)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 25, slots = 4)
            obj(Items.BINDING_NECKLACE, quantity = 1, slots = 3)
            // Pouches
            if (!player.hasItem(Items.SMALL_POUCH)) {
                obj(Items.SMALL_POUCH, quantity = 1, slots = 6)
            } else {
                nothing(6)
            }
            if (!player.hasItem(Items.MEDIUM_POUCH) && player.hasItem(Items.SMALL_POUCH)) {
                obj(Items.MEDIUM_POUCH, quantity = 1, slots = 6)
            } else {
                nothing(6)
            }
            if (!player.hasItem(Items.LARGE_POUCH) &&
                player.hasItem(Items.SMALL_POUCH) &&
                player.hasItem(Items.MEDIUM_POUCH)
            ) {
                obj(Items.LARGE_POUCH, quantity = 1, slots = 6)
            } else {
                nothing(6)
            }
            if (!player.hasItem(Items.GIANT_POUCH) &&
                player.hasItem(Items.SMALL_POUCH) &&
                player.hasItem(Items.MEDIUM_POUCH) &&
                player.hasItem(Items.LARGE_POUCH)
            ) {
                obj(Items.GIANT_POUCH, quantity = 1, slots = 6)
            } else {
                nothing(6)
            }
            // Others
            obj(Items.CLUE_SCROLL_MEDIUM, quantity = 1, slots = 6)
            obj(Items.ABYSSAL_CHARM, quantity = 2, slots = 64)
            nothing(2)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 50)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 180)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 1)
            obj(Items.ABYSSAL_CHARM, quantity = 2, slots = 64)
            nothing(slots = 880)
        }
    }

table.register(guard, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ABYSSAL_LEECH_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 2
            respawnDelay = 25
        }
        stats {
            hitpoints = 100
            attack = 95
            strength = 5
            defence = 25
        }
        bonuses {
            attackBonus = 100
            strengthBonus = 10
            defenceStab = 50
            defenceSlash = 50
            defenceCrush = 100
            defenceMagic = 50
            defenceRanged = 10
        }
        anims {
            attack = 2181
            block = 2182
            death = 2183
        }
        aggro {
            radius = 3
        }
    }
}
