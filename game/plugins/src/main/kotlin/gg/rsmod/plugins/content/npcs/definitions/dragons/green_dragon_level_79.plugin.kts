package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids =
    intArrayOf(
        Npcs.GREEN_DRAGON,
        Npcs.GREEN_DRAGON_4677,
        Npcs.GREEN_DRAGON_4678,
        Npcs.GREEN_DRAGON_4679,
        Npcs.GREEN_DRAGON_4680,
    )

val table = DropTableFactory
val dragon =
    table.build {
        guaranteed {
            obj(Items.DRAGON_BONES)
            obj(Items.GREEN_DRAGONHIDE)
        }

        main {
            total(1024)

            obj(Items.STEEL_PLATELEGS, slots = 32)
            obj(Items.STEEL_BATTLEAXE, slots = 24)
            obj(Items.MITHRIL_HATCHET, slots = 24)
            obj(Items.MITHRIL_SPEAR, slots = 16)
            obj(Items.MITHRIL_KITESHIELD, slots = 8)
            obj(Items.ADAMANT_FULL_HELM, slots = 8)
            obj(Items.RUNE_DAGGER, slots = 8)

            obj(Items.WATER_RUNE, quantity = 75, slots = 64)
            obj(Items.NATURE_RUNE, quantity = 15, slots = 40)
            obj(Items.LAW_RUNE, quantity = 3, slots = 24)
            obj(Items.FIRE_RUNE, quantity = 37, slots = 8)

            table(Herbs.minorHerbTable, slots = 120)

            obj(Items.COINS_995, quantity = 44, slots = 232)
            obj(Items.COINS_995, quantity = 132, slots = 200)
            obj(Items.COINS_995, quantity = 200, slots = 80)
            obj(Items.COINS_995, quantity = 11, slots = 40)
            obj(Items.COINS_995, quantity = 440, slots = 8)

            obj(Items.BASS, slots = 24)
            obj(Items.ADAMANTITE_ORE, slots = 24)

            nothing(5)

            table(Gems.gemTable, slots = 40)
        }

        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_HARD, slots = 1)
            nothing(127)
        }
    }

table.register(dragon, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DRAGON_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        species {
            NpcSpecies.BASIC_DRAGON
            NpcSpecies.FIERY
        }
        stats {
            hitpoints = 750
            attack = 68
            strength = 68
            defence = 68
            magic = 68
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 40
            defenceCrush = 40
            defenceMagic = 30
            defenceRanged = 20
        }
        anims {
            attack = 12252
            block = 12251
            death = 12250
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.GREEN_DRAGON
            level = 1
            experience = 75.0
        }
    }
}
