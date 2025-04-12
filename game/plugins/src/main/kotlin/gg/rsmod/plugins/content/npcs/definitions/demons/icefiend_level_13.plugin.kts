package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.ICEFIEND_7715)

val table = DropTableFactory
val icefiendDrops =
    table.build {
        guaranteed {
            obj(Items.IMPIOUS_ASHES, quantity = 1)
        }

        main {
            // Source: https://runescape.wiki/w/Icefiend?oldid=4567016
            // https://www.youtube.com/watch?v=35dF-TbIXMY
            total(128)

            // Very common
            obj(Items.WATER_TALISMAN, 1, 8)

            // Common
            obj(Items.BRONZE_SWORD, 1, 4)
            obj(Items.BRONZE_HATCHET, 1, 4)
            obj(Items.IRON_HATCHET, 1, 4)
            obj(Items.BRONZE_PICKAXE, 1, 4)
            obj(Items.IRON_PICKAXE, 1, 4)

            obj(Items.BRONZE_ARROW, quantityRange = 2..9, 4)
            obj(Items.IRON_ARROW, quantityRange = 1..5, 4)
            obj(Items.STEEL_ARROW, 1, 4)
            obj(Items.BRONZE_BOLTS, quantityRange =  1..10, 4)

            obj(Items.BRONZE_MED_HELM, 1, 4)
            obj(Items.BRONZE_FULL_HELM, 1, 4)
            obj(Items.IRON_CHAINBODY, 1, 4)

            obj(Items.SILVER_ORE, 1, 4)
            obj(Items.SILVER_BAR, 1, 4)

            // Uncommon
            obj(Items.STEEL_MED_HELM, 1, 2)
            obj(Items.JUG_OF_WINE, 1, 2)

            // Rare
            obj(Items.STEEL_HATCHET, 1, 1)
            obj(Items.STEEL_PICKAXE, 1, 1)
            obj(Items.STAFF_OF_AIR, 1, 1)
            obj(Items.STAFF_OF_WATER, 1, 1)

            obj(Items.DWARVEN_STOUT, 1, 1)
            obj(Items.CAKE, 1, 1)
            obj(Items.BEER, 1, 1)

            obj(Items.CHAOS_RUNE, quantityRange = 1..6, 1)
            obj(Items.DEATH_RUNE, quantityRange = 1..2, 1)

            nothing(51)
        }
    }

table.register(icefiendDrops, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ICEFIEND_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            attackStyle = StyleType.MAGIC_MELEE
        }
        stats {
            hitpoints = 150
            attack = 10
            strength = 10
            defence = 12
        }
        bonuses {
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 0
            defenceRanged = 0
        }
        anims {
            attack = Anims.FIEND_ATTACK
            block = Anims.ICEFIEND_BLOCK
            death = Anims.ICEFIEND_DEATH
        }
        slayer {
            assignment = SlayerAssignment.ICEFIEND
            level = 1
            experience = 7.0
        }
    }
}
