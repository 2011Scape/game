package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.ICE_WARRIOR, Npcs.ICE_WARRIOR_145, Npcs.ICE_WARRIOR_3073)

val table = DropTableFactory
val iceWarrior =
    table.build {
        main {
            total(1024)
            obj(Items.IRON_BATTLEAXE, slots = 24)
            obj(Items.MITHRIL_MACE, slots = 8)

            obj(Items.NATURE_RUNE, quantity = 4, slots = 80)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 64)
            obj(Items.LAW_RUNE, quantity = 2, slots = 56)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 40)
            obj(Items.MITHRIL_ARROW, quantity = 3, slots = 40)
            obj(Items.ADAMANT_ARROW, quantity = 2, slots = 16)
            obj(Items.DEATH_RUNE, quantity = 2, slots = 24)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 8)

            table(Herbs.minorHerbTable, slots = 75)

            table(Seeds.allotmentSeedTable, slots = 125)

            obj(Items.COINS_995, quantity = 15, slots = 224)
            obj(Items.COINS_995, quantity = 5, slots = 104)
            obj(Items.COINS_995, quantity = 10, slots = 62)

            nothing(50)

            table(Gems.gemTable, slots = 24)
        }

        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_MEDIUM, slots = 1)
            nothing(127)
        }
    }

table.register(iceWarrior, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ICE_WARRIOR_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 590
            attack = 47
            strength = 47
            defence = 47
        }
        bonuses {
            defenceStab = 30
            defenceSlash = 40
            defenceCrush = 20
            defenceMagic = 10
            defenceRanged = 30
        }
        anims {
            attack = 391
            block = 399
            death = 843
        }
        aggro {
            radius = 5
        }
        slayer {
            assignment = SlayerAssignment.ICE_WARRIOR
            level = 1
            experience = 59.0
        }
    }
}
