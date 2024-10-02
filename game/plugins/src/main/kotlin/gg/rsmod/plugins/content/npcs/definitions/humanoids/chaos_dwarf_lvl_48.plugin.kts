package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.CHAOS_DWARF)

val table = DropTableFactory
val chaosDwarf =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 128)
            obj(Items.STEEL_FULL_HELM, slots = 2)
            obj(Items.MITHRIL_LONGSWORD, slots = 1)
            obj(Items.MITHRIL_SQ_SHIELD, slots = 1)

            obj(Items.LAW_RUNE, quantity = 3, slots = 4)
            obj(Items.AIR_RUNE, quantity = 24, slots = 3)
            obj(Items.CHAOS_RUNE, quantity = 10, slots = 3)
            obj(Items.MIND_RUNE, quantity = 37, slots = 3)
            obj(Items.NATURE_RUNE, quantity = 9, slots = 3)
            obj(Items.COSMIC_RUNE, quantity = 3, slots = 2)
            obj(Items.DEATH_RUNE, quantity = 3, slots = 1)
            obj(Items.WATER_RUNE, quantity = 10, slots = 1)

            obj(Items.COINS_995, quantity = 92, slots = 40)
            obj(Items.COINS_995, quantity = 47, slots = 18)
            obj(Items.COINS_995, quantity = 25, slots = 11)
            obj(Items.COINS_995, quantity = 150, slots = 10)
            obj(Items.COINS_995, quantity = 350, slots = 2)
            obj(Items.COINS_995, quantity = 15, slots = 2)

            nothing(slots = 5)

            obj(Items.COAL, slots = 1)
            obj(Items.MUDDY_KEY, slots = 7)
            obj(Items.CHEESE, slots = 1)
            obj(Items.MITHRIL_BAR, slots = 6)
            obj(Items.TOMATO, slots = 1)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 100)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 50)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 20)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 20)
            nothing(slots = 810)
        }
    }

table.register(chaosDwarf, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DWARF_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 610
            attack = 38
            strength = 42
            defence = 28
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 13
            attackCrush = 9
            defenceStab = 40
            defenceSlash = 34
            defenceCrush = 25
            defenceMagic = 10
            defenceRanged = 35
        }
        anims {
            attack = 99
            death = 102
            block = 100
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.DWARF
            level = 1
            experience = 61.0
        }
    }
}
