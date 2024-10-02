package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems

val ids = intArrayOf(Npcs.PYREFIEND, Npcs.PYREFIEND_1634, Npcs.PYREFIEND_1635, Npcs.PYREFIEND_1636)

val table = DropTableFactory
val pyrefiend =
    table.build {
        guaranteed {
            obj(Items.IMPIOUS_ASHES)
        }

        main {
            total(128)

            obj(Items.STEEL_HATCHET, slots = 4)
            obj(Items.STEEL_FULL_HELM, slots = 4)
            obj(Items.MITHRIL_CHAINBODY, slots = 2)
            obj(Items.STAFF_OF_FIRE, slots = 3)
            obj(Items.STEEL_BOOTS, slots = 1)
            obj(Items.ADAMANT_MED_HELM, slots = 1)

            obj(Items.FIRE_RUNE, quantity = 30, slots = 21)
            obj(Items.FIRE_RUNE, quantity = 60, slots = 8)
            obj(Items.CHAOS_RUNE, quantity = 12, slots = 5)
            obj(Items.DEATH_RUNE, quantity = 3, slots = 3)

            obj(Items.COINS_995, quantity = 10, slots = 7)
            obj(Items.COINS_995, quantity = 40, slots = 24)
            obj(Items.COINS_995, quantity = 120, slots = 20)
            obj(Items.COINS_995, quantity = 200, slots = 10)
            obj(Items.COINS_995, quantity = 450, slots = 2)

            obj(Items.GOLD_ORE, slots = 8)
            obj(Items.JUG_OF_WINE, slots = 2)

            table(Gems.gemTable, slots = 3)
        }
    }

table.register(pyrefiend, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.PYREFIEND_DEATH)
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
            hitpoints = 450
            attack = 52
            strength = 30
            defence = 22
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 10
            defenceSlash = 10
            defenceCrush = 10
            defenceMagic = 0
            defenceRanged = 10
        }
        anims {
            attack = 8080
            death = 11211
        }
        slayer {
            assignment = SlayerAssignment.PYREFIEND
            experience = 45.0
            level = 30
        }
    }
}
