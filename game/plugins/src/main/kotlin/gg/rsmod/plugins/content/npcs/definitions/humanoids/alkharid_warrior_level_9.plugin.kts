package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs

/**
 * Handles the Al-Kharid warrior definitions
 * TODO Add a way for all surrounding warriors to attack player if one becomes under attack
 * @author Kevin Senez <ksenez94@gmail.com>
 */

val NPC_ID = Npcs.ALKHARID_WARRIOR

val table = DropTableFactory
val alkharid_warrior =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(1024)

            obj(Items.BRONZE_MED_HELM, slots = 16)
            obj(Items.IRON_DAGGER, slots = 8)

            obj(Items.BRONZE_BOLTS, quantityRange = 2..12, slots = 146)
            obj(Items.BRONZE_ARROW, quantity = 7, slots = 24)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 16)
            obj(Items.FIRE_RUNE, quantity = 6, slots = 16)
            obj(Items.MIND_RUNE, quantity = 9, slots = 16)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 8)

            table(Herbs.minorHerbTable, slots = 84)

            obj(Items.COINS_995, quantity = 3, slots = 204)
            obj(Items.COINS_995, quantity = 10, slots = 84)
            obj(Items.COINS_995, quantity = 5, slots = 72)
            obj(Items.COINS_995, quantity = 15, slots = 32)
            obj(Items.COINS_995, quantity = 25, slots = 8)

            nothing(slots = 146)
            nothing(slots = 64)
            obj(Items.FISHING_BAIT, slots = 40)
            obj(Items.COPPER_ORE, slots = 16)
            obj(Items.EARTH_TALISMAN, slots = 16)
            obj(Items.CABBAGE, slots = 8)
        }
        table("Tertiary") {
            total(1024)
            obj(Items.CLUE_SCROLL_EASY, slots = 8)
            nothing(1016)
        }
    }

table.register(alkharid_warrior, NPC_ID)

on_npc_pre_death(NPC_ID) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(NPC_ID) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = NPC_ID) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 190
        attack = 7
        strength = 5
        defence = 4
    }
    bonuses {
        attackBonus = 10
        strengthBonus = 9
        defenceStab = 12
        defenceSlash = 15
        defenceCrush = 10
        defenceMagic = -1
        defenceRanged = 12
    }
    anims {
        attack = 390
        block = 388
        death = 836
    }
}
