package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.NECHRYAEL)

val table = DropTableFactory
val nechryael =
    table.build {
        guaranteed {
            obj(Items.INFERNAL_ASHES)
        }

        main {
            total(624)
            // ARMOUR
            obj(Items.RUNE_FULL_HELM, slots = 16)
            obj(Items.ADAMANT_PLATELEGS, slots = 16)
            obj(Items.MITHRIL_KITESHIELD, slots = 16)
            obj(Items.RUNE_BOOTS, slots = 4)
            obj(Items.DRAGON_MED_HELM, slots = 1)
            obj(Items.RUNE_SQ_SHIELD, slots = 1)
            // WEAPONS
            obj(Items.STEEL_2H_SWORD, slots = 64)
            obj(Items.STEEL_BATTLEAXE, slots = 64)
            obj(Items.STEEL_HATCHET, slots = 64)
            obj(Items.RUNE_2H_SWORD, slots = 4)
            obj(Items.STEEL_ARROW, slots = 4)
            obj(Items.RUNE_ARROW, slots = 1)
            obj(Items.RUNE_LONGSWORD, slots = 2)
            obj(Items.RUNE_BATTLEAXE, slots = 2)
            // RUNES
            obj(Items.DEATH_RUNE, quantity = listOf(5, 10, 45).random(), slots = 64)
            obj(Items.CHAOS_RUNE, quantity = listOf(3, 37).random(), slots = 16)
            obj(Items.LAW_RUNE, quantity = 45, slots = 4)
            obj(Items.NATURE_RUNE, quantity = 68, slots = 4)
            // SEEDS
            table(Seeds.uncommonSeedTable, slots = 80)
            // OTHER
            obj(Items.COINS_995, quantity = listOf(11, 44, 132, 460, 3000, 8992).random(), slots = 64)
            obj(Items.TUNA, quantity = 1, slots = 64)
            obj(Items.THREAD, quantity = 10, slots = 16)
            obj(Items.GOLD_BAR, quantity = 1, slots = 16)
            obj(Items.RUNE_BAR, quantity = 1, slots = 4)
            obj(Items.COURT_SUMMONS, quantity = 1, slots = 4)
            obj(Items.CLUE_SCROLL_HARD, quantity = 1, slots = 4)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 4)
            obj(Items.SILVER_ORE_NOTED, quantity = 100, slots = 1)
            // RARE
            table(Rare.rareTable, slots = 4)
            // NOTHING
            nothing(slots = 16)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 90)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 40)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 310)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 11)
            nothing(slots = 549)
        }
    }

table.register(nechryael, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.NECHRAYEL_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 59
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 1050
            attack = 97
            strength = 97
            defence = 105
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 20
            defenceCrush = 20
            defenceMagic = 0
            defenceRanged = 20
        }
        anims {
            attack = 9487
            death = 9488
            block = 9489
        }
        slayer {
            assignment = SlayerAssignment.NECHRYAEL
            level = 80
            experience = 105.0
        }
    }
}
