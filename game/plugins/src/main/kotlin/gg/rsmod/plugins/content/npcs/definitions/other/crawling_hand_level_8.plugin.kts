package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare

val ids =
    intArrayOf(
        Npcs.CRAWLING_HAND,
        Npcs.CRAWLING_HAND_1649,
        Npcs.CRAWLING_HAND_1650,
        Npcs.CRAWLING_HAND_1651,
        Npcs.CRAWLING_HAND_1652,
        Npcs.CRAWLING_HAND_1653,
        Npcs.CRAWLING_HAND_1654,
        Npcs.CRAWLING_HAND_1655,
        Npcs.CRAWLING_HAND_1656,
        Npcs.CRAWLING_HAND_1657,
    )
val table = DropTableFactory
val crawlinghand =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 512)

            // WEAPONS AND ARMOUR
            obj(Items.LEATHER_GLOVES, quantity = 1, slots = 64)
            obj(Items.GLOVES, quantity = 1, slots = 16)
            obj(Items.GLOVES_2912, quantity = 1, slots = 16)
            obj(Items.GLOVES_2922, quantity = 1, slots = 16)
            obj(Items.GLOVES_2932, quantity = 1, slots = 16)
            obj(Items.GLOVES_2942, quantity = 1, slots = 16)
            obj(Items.MYSTIC_GLOVES_4115, quantity = 1, slots = 1)

            // JEWELLERY
            obj(Items.GOLD_RING, quantity = 1, slots = 16)
            obj(Items.SAPPHIRE_RING, quantity = 1, slots = 16)
            obj(Items.EMERALD_RING, quantity = 1, slots = 16)
            obj(Items.RUBY_RING, quantity = 1, slots = 16)

            // OTHER
            obj(Items.COINS_995, quantity = 5, slots = 16)
            obj(Items.COINS_995, quantity = 8, slots = 16)
            obj(Items.COINS_995, quantity = 312, slots = 16)
            obj(Items.ASHES, quantity = 1, slots = 16)
            obj(Items.HAND_BONE, quantity = 1, slots = 16)
            obj(Items.CRAWLING_HAND_7982, quantity = 1, slots = 1)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)

            nothing(slots = 218)

            // RARE TABLE
            table(Rare.rareTable, slots = 3)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 96)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 6)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 6)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 890)
        }
    }

table.register(crawlinghand, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HAND_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 160
            attack = 8
            strength = 4
            defence = 4
            magic = 1
            ranged = 1
        }
        anims {
            attack = 9125
            block = 9127
            death = 9126
        }
        slayer {
            assignment = SlayerAssignment.CRAWLING_HAND
            level = 5
            experience = 12.0
        }
    }
}
