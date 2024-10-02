package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val ids = intArrayOf(Npcs.FLESH_CRAWLER_4391)

val table = DropTableFactory
val fleshCrawler =
    table.build {
        main {
            total(256)
            // Runes
            obj(Items.BODY_RUNE, quantityRange = 3..12, slots = 25)
            obj(Items.DUST_RUNE, quantityRange = 2..23, slots = 17)
            obj(Items.FIRE_RUNE, quantity = 42, slots = 8)
            obj(Items.NATURE_RUNE, quantityRange = 5..10, slots = 2)
            // Other Drops
            obj(Items.COINS_995, quantityRange = 1..101, slots = 25)
            obj(Items.IRON_ORE_NOTED, quantityRange = 1..10, slots = 25)
            obj(Items.ASHES, quantity = 1, slots = 25)
            obj(Items.BOTTOM_OF_SCEPTRE, quantity = 1, slots = 2)
            obj(Items.SILVER_BAR, quantity = 1, slots = 25)
            obj(Items.BLACK_FULL_HELM, quantity = 1, slots = 8)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)
            // Tables
            table(Herbs.minorHerbTable, slots = 43)
            table(Rare.rareTable, slots = 2)
            nothing(72)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 40)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 60)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 8)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 890)
        }
    }

table.register(fleshCrawler, *ids)

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
            attackSpeed = 3
            respawnDelay = 15
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 250
            attack = 100
            strength = 2
            defence = 10
        }
        bonuses {
            defenceStab = 15
            defenceSlash = 15
            defenceCrush = 15
            defenceMagic = 15
            defenceRanged = 15
        }
        anims {
            attack = 1184
            block = 1186
            death = 1187
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.FLESH_CRAWLER
            experience = 25.0
            level = 1
        }
    }
}
