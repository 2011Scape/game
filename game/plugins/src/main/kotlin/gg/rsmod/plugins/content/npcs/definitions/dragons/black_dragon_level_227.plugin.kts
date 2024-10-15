package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Rare

val ids =
    intArrayOf(
        Npcs.BLACK_DRAGON,
        Npcs.BLACK_DRAGON_4673,
        Npcs.BLACK_DRAGON_4674,
        Npcs.BLACK_DRAGON_4675,
        Npcs.BLACK_DRAGON_4676,
    )

val table = DropTableFactory
val dragon =
    table.build {
        guaranteed {
            obj(Items.DRAGON_BONES)
            obj(Items.BLACK_DRAGONHIDE)
        }

        main {
            total(1024)

            obj(Items.MITHRIL_2H_SWORD, slots = 32)
            obj(Items.MITHRIL_HATCHET, slots = 24)
            obj(Items.MITHRIL_BATTLEAXE, slots = 24)
            obj(Items.RUNE_KNIFE, quantity = 2, slots = 24)
            obj(Items.MITHRIL_KITESHIELD, slots = 8)
            obj(Items.ADAMANT_PLATEBODY, slots = 8)
            obj(Items.RUNE_LONGSWORD, slots = 8)

            obj(Items.ADAMANT_JAVELIN, quantity = 30, slots = 160)
            obj(Items.FIRE_RUNE, quantity = 50, slots = 64)
            obj(Items.ADAMANT_DART_P, quantity = 16, slots = 56)
            obj(Items.LAW_RUNE, quantity = 10, slots = 40)
            obj(Items.BLOOD_RUNE, quantity = 15, slots = 24)
            obj(Items.AIR_RUNE, quantity = 75, slots = 8)

            obj(Items.COINS_995, quantity = 196, slots = 320)
            obj(Items.COINS_995, quantity = 330, slots = 80)
            obj(Items.COINS_995, quantity = 690, slots = 8)

            obj(Items.ADAMANT_BAR, slots = 24)
            obj(Items.CHOCOLATE_CAKE, slots = 24)

            table(Gems.gemTable, slots = 24)
            table(Rare.rareTable, slots = 16)

            nothing(48)
        }

        table("Tertiary") {
            total(10_000)
            obj(Items.CLUE_SCROLL_HARD, slots = 78)
            obj(Items.CLUE_SCROLL_HARD, slots = 156)
            obj(Items.CLUE_SCROLL_ELITE, slots = 20)
            obj(Items.CLUE_SCROLL_ELITE, slots = 40)
            obj(Items.DRACONIC_VISAGE, slots = 1)
            nothing(9_705)
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
            hitpoints = 1900
            attack = 200
            strength = 200
            defence = 200
            magic = 100
            ranged = 1
        }
        bonuses {
            defenceStab = 50
            defenceSlash = 70
            defenceCrush = 70
            defenceMagic = 60
            defenceRanged = 50
        }
        anims {
            attack = 12252
            block = 12251
            death = 12250
        }
        aggro {
            radius = 4
        }
    }
}
