package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.BLACK_DEMON, Npcs.BLACK_DEMON_4702, Npcs.BLACK_DEMON_4703, Npcs.BLACK_DEMON_4705)
val table = DropTableFactory
val blackDemon =
    table.build {

        guaranteed {
            obj(Items.INFERNAL_ASHES)
        }

        main {
            total(total = 128)

            obj(Items.BLACK_SWORD, quantity = 1, slots = 4)
            obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 3)
            obj(Items.BLACK_HATCHET, quantity = 1, slots = 2)
            obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 1)
            obj(Items.RUNE_MED_HELM, quantity = 1, slots = 1)
            obj(Items.RUNE_CHAINBODY, quantity = 1, slots = 1)

            obj(Items.AIR_RUNE, quantity = 50, slots = 8)
            obj(Items.CHAOS_RUNE, quantity = 10, slots = 7)
            obj(Items.BLOOD_RUNE, quantity = 7, slots = 4)
            obj(Items.FIRE_RUNE, quantity = 37, slots = 1)
            obj(Items.LAW_RUNE, quantity = 3, slots = 1)

            table(Herbs.minorHerbTable, slots = 23)

            obj(Items.COINS_995, quantity = 132, slots = 40)
            obj(Items.COINS_995, quantity = 30, slots = 7)
            obj(Items.COINS_995, quantity = 44, slots = 6)
            obj(Items.COINS_995, quantity = 220, slots = 6)
            obj(Items.COINS_995, quantity = 460, slots = 1)

            obj(Items.LOBSTER, quantity = 1, slots = 3)
            obj(Items.ADAMANT_BAR, quantity = 1, slots = 2)
            obj(Items.DEFENCE_POTION_3, quantity = 1, slots = 1)

            table(Gems.gemTable, slots = 5)
            table(Rare.rareTable, slots = 1)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 21)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 11)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 44)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 922)
        }
    }

table.register(blackDemon, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BLACK_DEMON_DEATH)
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
            hitpoints = 1570
            attack = 145
            strength = 148
            defence = 152
        }
        bonuses {
            defenceMagic = -10
        }
        anims {
            attack = 64
            block = 65
            death = 67
        }
        aggro {
            radius = 4
        }
    }
}
