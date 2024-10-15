package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.ABYSSAL_GUARDIAN)

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
                player.hasItem(
                    Items.LARGE_POUCH,
                )
            ) {
                obj(Items.GIANT_POUCH, quantity = 1, slots = 6)
            } else {
                nothing(6)
            }

            // Others
            obj(Items.CLUE_SCROLL_MEDIUM, quantity = 1, slots = 6)
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
    p.playSound(Sfx.ABYSSAL_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 25
        }
        stats {
            hitpoints = 550
            attack = 30
            strength = 90
            defence = 30
        }
        bonuses {
            attackBonus = 15
            strengthBonus = 5
            defenceStab = 70
            defenceSlash = 70
            defenceCrush = 70
            defenceMagic = 150
            defenceRanged = 70
        }
        anims {
            attack = 2186
            block = 2188
            death = 2189
        }
        aggro {
            radius = 3
        }
    }
}
