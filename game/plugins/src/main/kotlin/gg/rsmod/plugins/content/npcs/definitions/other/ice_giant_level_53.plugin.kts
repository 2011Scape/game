package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.ICE_GIANT, Npcs.ICE_GIANT_3072, Npcs.ICE_GIANT_4685, Npcs.ICE_GIANT_4686, Npcs.ICE_GIANT_4687)

val table = DropTableFactory
val iceGiant =
    table.build {
        guaranteed {
            obj(Items.BIG_BONES)
        }

        main {
            total(1024)
            obj(Items.IRON_2H_SWORD, slots = 40)
            obj(Items.BLACK_KITESHIELD, slots = 32)
            obj(Items.STEEL_HATCHET, slots = 32)
            obj(Items.STEEL_SWORD, slots = 32)
            obj(Items.IRON_PLATELEGS, slots = 8)
            obj(Items.MITHRIL_MACE, slots = 8)
            obj(Items.MITHRIL_SQ_SHIELD, slots = 8)

            obj(Items.ADAMANT_ARROW, quantity = 5, slots = 48)
            obj(Items.NATURE_RUNE, quantity = 6, slots = 32)
            obj(Items.MIND_RUNE, quantity = 24, slots = 24)
            obj(Items.BODY_RUNE, quantity = 37, slots = 24)
            obj(Items.LAW_RUNE, quantity = 3, slots = 16)
            obj(Items.WATER_RUNE, quantity = 12, slots = 8)
            obj(Items.COSMIC_RUNE, quantity = 4, slots = 8)
            obj(Items.DEATH_RUNE, quantity = 3, slots = 8)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 8)

            table(Seeds.allotmentSeedTable, slots = 64)

            obj(Items.COINS_995, quantity = 117, slots = 224)
            obj(Items.COINS_995, quantity = 53, slots = 76)
            obj(Items.COINS_995, quantity = 196, slots = 72)
            obj(Items.COINS_995, quantity = 5, slots = 64)
            obj(Items.COINS_995, quantity = 8, slots = 54)
            obj(Items.COINS_995, quantity = 2, slots = 48)
            obj(Items.COINS_995, quantity = 400, slots = 16)

            obj(Items.JUG_OF_WINE, slots = 24)
            obj(Items.MITHRIL_ORE, slots = 8)
            obj(Items.BANANA, slots = 8)

            table(Gems.gemTable, slots = 32)
        }

        table("Tertiary") {
            total(100_000)
            obj(Items.LONG_BONE, slots = 250)
            obj(Items.CURVED_BONE, slots = 20)
            obj(Items.CHAMPIONS_SCROLL_6800, slots = 20)
            nothing(99710)
        }
    }

table.register(iceGiant, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GIANT_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 30
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 700
            attack = 40
            strength = 40
            defence = 40
        }
        bonuses {
            attackBonus = 29
            strengthBonus = 31
            defenceSlash = 3
            defenceCrush = 2
        }
        anims {

            attack = 4672
            block = 4671
            death = 4673
        }
        aggro {
            radius = 5
        }
        slayer {
            assignment = SlayerAssignment.ICE_GIANT
            level = 1
            experience = 70.0
        }
    }
}
