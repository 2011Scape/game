package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.CAVE_BUG)

val table = DropTableFactory
val caveBug =
    table.build {
        main {
            total(total = 128)

            obj(Items.WATER_RUNE, quantity = 8, slots = 5)
            obj(Items.NATURE_RUNE, quantity = 1, slots = 5)
            obj(Items.EARTH_RUNE, quantity = 6, slots = 2)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 1)

            table(Herbs.minorHerbTable, slots = 23)

            obj(Items.UNICORN_HORN_DUST, slots = 2)
            obj(Items.EYE_OF_NEWT, slots = 2)
            obj(Items.RED_SPIDERS_EGGS, slots = 2)
            obj(Items.LIMPWURT_ROOT, slots = 1)
            obj(Items.SNAPE_GRASS, slots = 1)

            obj(Items.COINS_995, quantity = 3, slots = 8)
            obj(Items.COINS_995, quantity = 8, slots = 3)

            nothing(slots = 64)
            obj(Items.CANDLE, slots = 5)
            obj(Items.TINDERBOX_590, slots = 3)
            obj(Items.CANDLE_LANTERN, slots = 1)
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

table.register(caveBug, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.CAVE_BUG_DEATH)
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
            hitpoints = 50
            attack = 6
            strength = 5
            defence = 6
        }
        bonuses {
            defenceStab = 10
            defenceSlash = 10
            defenceCrush = 5
            defenceMagic = 5
            defenceRanged = 10
        }
        anims {
            attack = 6079
            death = 6082
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.CAVE_BUG
            experience = 5.0
            level = 6
        }
    }
}
