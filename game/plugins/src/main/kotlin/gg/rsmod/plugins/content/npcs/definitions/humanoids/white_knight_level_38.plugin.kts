package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems.gemTable
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable
import gg.rsmod.plugins.content.drops.global.Seeds.allotmentSeedTable

val npcId = Npcs.WHITE_KNIGHT_3348

val table = DropTableFactory
val white_knight =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(128)
            obj(Items.IRON_LONGSWORD, slots = 2)
            obj(Items.STEEL_SWORD, slots = 1)
            obj(Items.STEEL_MED_HELM, slots = 2)
            obj(Items.MIND_RUNE, quantity = 5, slots = 8)
            obj(Items.NATURE_RUNE, quantity = 4, slots = 4)
            obj(Items.BODY_RUNE, quantityRange = 10..14, slots = 3)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 3)
            obj(Items.WATER_RUNE, quantityRange = 30..35, slots = 3)
            obj(Items.MITHRIL_ARROW, quantity = 5, slots = 2)
            obj(Items.ADAMANT_ARROW, quantity = 2, slots = 3)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 1)
            obj(Items.LAW_RUNE, quantity = 3, slots = 1)
            obj(Items.COINS_995, quantity = 15, slots = 13)
            obj(Items.COINS_995, quantity = 10, slots = 15)
            obj(Items.COINS_995, quantity = 54, slots = 10)
            obj(Items.COINS_995, quantityRange = 50..58, slots = 10)
            obj(Items.COINS_995, quantity = 3, slots = 6)
            obj(Items.COINS_995, quantity = 1, slots = 1)
            obj(Items.COINS_995, quantity = 120, slots = 1)
            obj(Items.IRON_BAR, quantity = 2, slots = 8)
            obj(Items.IRON_BAR, quantity = 1, slots = 2)
            obj(Items.HALF_AN_APPLE_PIE, slots = 1)
            obj(Items.IRON_ORE, slots = 1)
            obj(Items.POT_OF_FLOUR, slots = 1)

            nothing(slots = 1)

            table(minorHerbTable, slots = 7)
            table(allotmentSeedTable, slots = 18)
            table(gemTable, slots = 2)
        }
        table("Tertiary") {
            total(1024)
            obj(Items.CLUE_SCROLL_EASY, slots = 8)
            nothing(1016)
        }
    }

table.register(white_knight, npcId)

on_npc_pre_death(npcId) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(npcId) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = npcId) {
    configs {
        attackSpeed = 7
        respawnDelay = 50
    }
    stats {
        hitpoints = 520
        attack = 30
        strength = 29
        defence = 25
    }
    bonuses {
        attackBonus = 30
        strengthBonus = 31
        defenceStab = 83
        defenceSlash = 76
        defenceCrush = 70
        defenceMagic = -11
        defenceRanged = 74
    }
    anims {
        attack = 406
        block = 7050
        death = 836
    }
}
