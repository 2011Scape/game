package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val banshee = Npcs.BANSHEE

val table = DropTableFactory
val bansheeTable =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 1148)
            // WEAPONS AND ARMOUR
            obj(Items.IRON_DAGGER, quantity = 1, slots = 128)
            obj(Items.IRON_MACE, quantity = 1, slots = 128)
            obj(Items.IRON_KITESHIELD, quantity = 1, slots = 128)
            obj(Items.ADAMANT_KITESHIELD, quantity = 1, slots = 4)
            obj(Items.MYSTIC_GLOVES, quantity = 1, slots = 8)
            obj(Items.MYSTIC_GLOVES_4105, quantity = 1, slots = 2)
            // RUNES
            obj(Items.AIR_RUNE, quantity = 3, slots = 24)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 24)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 13, slots = 128)
            obj(Items.CHAOS_RUNE, quantity = 7, slots = 8)
            obj(Items.FIRE_RUNE, quantity = 7, slots = 8)
            // OTHER
            obj(Items.COINS_995, quantity = 13, slots = 84)
            obj(Items.FISHING_BAIT, quantity = 15, slots = 128)
            obj(Items.EYE_OF_NEWT, quantity = 1, slots = 8)
            obj(Items.IRON_ORE, quantity = 1, slots = 8)
            obj(Items.CLUE_SCROLL_EASY, quantity = 1, slots = 32)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 2)
            // HERB
            table(Herbs.minorHerbTable, slots = 272)
            // RARE
            table(Rare.rareTable, slots = 16)
            // NOTHING
            nothing(slots = 8)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 3)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 7)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 1)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 1)
            nothing(slots = 898)
        }
    }

table.register(bansheeTable, banshee)

on_npc_pre_death(banshee) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BANSHEE_DEATH)
}

on_npc_death(banshee) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(banshee) {
    configs {
        attackSpeed = 6
        respawnDelay = 15
        attackStyle = StyleType.CRUSH
    }
    stats {
        hitpoints = 220
        attack = 22
        strength = 15
        defence = 22
    }
    bonuses {
        defenceStab = 5
        defenceSlash = 5
        defenceCrush = 5
        defenceMagic = 0
        defenceRanged = 5
    }
    anims {
        attack = 5498
        death = 9378
        block = 9451
    }
    slayer {
        assignment = SlayerAssignment.BANSHEE
        level = 15
        experience = 22.0
    }
}
