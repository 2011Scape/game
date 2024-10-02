package gg.rsmod.plugins.content.npcs.definitions.other.minotaur

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val npc = Npcs.MINOTAUR_4406

val table = DropTableFactory
val minotaurTable =
    table.build {
        guaranteed {
            obj(Items.BONES, quantity = 1)
        }
        main {
            total(459)
            // Weapons, armour, and ammunition.
            obj(Items.BRONZE_SPEAR, quantity = 1, slots = 51)
            obj(Items.BRONZE_FULL_HELM, quantity = 1, slots = 51)
            obj(Items.IRON_ARROW, quantityRange = 5..20, slots = 51)
            obj(Items.BRONZE_DAGGER, quantity = 1, slots = 20)
            obj(Items.BRONZE_ARROW, quantity = 3, slots = 15)
            obj(Items.SLING, quantity = 1, slots = 16)
            obj(Items.STAFF_OF_AIR, quantity = 1, slots = 16)
            obj(Items.RUNE_JAVELIN, quantity = 5, slots = 4)
            // Gems, ores and bars
            obj(Items.TIN_ORE_NOTED, quantity = 1, slots = 32)
            obj(Items.COPPER_ORE_NOTED, quantity = 1, slots = 32)
            obj(Items.PURE_ESSENCE_NOTED, quantityRange = 5..15, slots = 25)
            obj(Items.RUNE_ESSENCE_NOTED, quantityRange = 5..30, slots = 25)
            obj(Items.GOLD_BAR, quantity = 1, slots = 4)
            // Other
            obj(Items.COINS_995, quantityRange = 1..229, slots = 25)
            obj(Items.WATER_RUNE, quantity = 5, slots = 16)
            obj(Items.EARTH_RUNE, quantity = 5, slots = 16)
            obj(Items.COOKED_MEAT_NOTED, quantity = 1, slots = 4)
            obj(Items.MIND_RUNE, quantity = 1, slots = 25)
            obj(Items.FIRE_TALISMAN, quantity = 1, slots = 4)
            obj(Items.NATURE_TALISMAN, quantity = 1, slots = 4)
            obj(Items.RIGHT_SKULL_HALF, quantity = 1, slots = 15)
            obj(Items.CLUE_SCROLL_EASY, quantity = 1, slots = 4)
            table(Rare.rareTable, slots = 4)
        }
    }

table.register(minotaurTable, npc)

on_npc_pre_death(npc) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.MINOTAUR_DEATH)
}

on_npc_death(npc) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = npc) {
    configs {
        attackSpeed = 4
        respawnDelay = 15
        attackStyle = StyleType.CRUSH
    }
    stats {
        hitpoints = 220
        attack = 23
        strength = 25
        defence = 25
    }
    bonuses {
        defenceStab = -10
        defenceSlash = -10
        defenceCrush = -10
        defenceMagic = -21
        defenceRanged = -10
    }
    anims {
        attack = 4266
        block = 4267
        death = 4265
    }
    slayer {
        assignment = SlayerAssignment.MINOTAUR
        level = 1
        experience = 22.0
    }
}
