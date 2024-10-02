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

val ids = intArrayOf(Npcs.CAVE_CRAWLER, Npcs.CAVE_CRAWLER_1601, Npcs.CAVE_CRAWLER_1602, Npcs.CAVE_CRAWLER_1603)

val table = DropTableFactory
val caveCrawler =
    table.build {
        main {
            total(total = 128)

            obj(Items.BRONZE_BOOTS, slots = 1)

            obj(Items.NATURE_RUNE, quantity = world.random(3..4), slots = 6)
            obj(Items.FIRE_RUNE, quantity = 12, slots = 5)
            obj(Items.EARTH_RUNE, quantity = 12, slots = 2)

            table(Seeds.allotmentSeedTable, slots = 26)
            table(Herbs.minorHerbTable, slots = 22)

            obj(Items.VIAL_OF_WATER, slots = 13)
            obj(Items.WHITE_BERRIES, slots = 5)
            obj(Items.UNICORN_HORN_DUST, slots = 2)
            obj(Items.EYE_OF_NEWT, slots = 1)
            obj(Items.RED_SPIDERS_EGGS, slots = 1)
            obj(Items.LIMPWURT_ROOT, slots = 1)
            obj(Items.SNAPE_GRASS, slots = 1)

            obj(Items.COINS_995, quantity = 3, slots = 5)
            obj(Items.COINS_995, quantity = 8, slots = 3)
            obj(Items.COINS_995, quantity = 29, slots = 3)
            obj(Items.COINS_995, quantity = 10, slots = 1)

            nothing(slots = 29)

            table(Gems.gemTable, slots = 1)
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

table.register(caveCrawler, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.CAVE_CRAWLER_DEATH)
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
            poisonDamage = 8
        }
        stats {
            hitpoints = 220
            attack = 22
            strength = 18
            defence = 18
        }
        bonuses {
            defenceStab = 10
            defenceSlash = 10
            defenceCrush = 5
            defenceMagic = 5
            defenceRanged = 10
        }
        anims {
            attack = 266
            block = 267
            death = 265
        }
        slayer {
            assignment = SlayerAssignment.CAVE_CRAWLER
            experience = 22.0
            level = 10
        }
    }
}
