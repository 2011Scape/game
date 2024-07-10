package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.COCKATRICE, Npcs.COCKATRICE_4227)

val table = DropTableFactory
val cockatrice =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 512)

            obj(Items.IRON_SWORD, slots = 12)
            obj(Items.STEEL_DAGGER, slots = 12)
            obj(Items.IRON_BOOTS, slots = 4)
            obj(Items.IRON_JAVELIN, slots = 4)
            obj(Items.STEEL_LONGSWORD, slots = 4)
            obj(Items.MYSTIC_BOOTS_4117, slots = 1)

            obj(Items.NATURE_RUNE, quantity = 2, slots = 24)
            obj(Items.NATURE_RUNE, quantity = 4, slots = 16)
            obj(Items.NATURE_RUNE, quantity = 6, slots = 8)
            obj(Items.LAW_RUNE, quantity = 2, slots = 12)
            obj(Items.WATER_RUNE, quantity = 2, slots = 8)
            obj(Items.FIRE_RUNE, quantity = 7, slots = 8)

            table(Seeds.allotmentSeedTable, slots = 72)
            table(Herbs.minorHerbTable, slots = 40)

            obj(Items.COINS_995, quantity = 15, slots = 64)
            obj(Items.COINS_995, quantity = 5, slots = 48)
            obj(Items.COINS_995, quantity = 28, slots = 48)
            obj(Items.COINS_995, quantity = 62, slots = 16)
            obj(Items.COINS_995, quantity = 42, slots = 12)
            obj(Items.COINS_995, quantity = 1, slots = 4)

            nothing(slots = 3)
            obj(Items.LIMPWURT_ROOT, slots = 84)

            table(Gems.gemTable, slots = 8)
        }
        table("Tertiary") {
            total(1000)
            obj(Items.COCKATRICE_EGG, slots = 256)
            obj(Items.COCKATRICE_HEAD, slots = 1)
            nothing(slots = 743)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 90)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 20)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 875)
        }
    }

table.register(cockatrice, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.COCKATRICE_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            attackStyle = StyleType.STAB
        }
        stats {
            hitpoints = 370
            attack = 22
            strength = 37
            defence = 37
        }
        bonuses {
            defenceStab = 10
            defenceSlash = 10
            defenceMagic = 10
        }
        anims {
            attack = 7762
            block = 7761
            death = 7763
        }
        slayer {
            assignment = SlayerAssignment.COCKATRICE
            experience = 37.0
            level = 25
        }
    }
}
