package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.HILL_GIANT)
val table = DropTableFactory
val hillgiant =
    table.build {

        guaranteed {
            obj(Items.BIG_BONES)
        }
        main {
            total(total = 128)

            obj(Items.IRON_FULL_HELM, quantity = 1, slots = 4)
            obj(Items.IRON_DAGGER, quantity = 1, slots = 5)
            obj(Items.IRON_KITESHIELD, quantity = 1, slots = 3)
            obj(Items.STEEL_LONGSWORD, quantity = 1, slots = 2)

            obj(Items.IRON_ARROW, quantity = 3, slots = 6)
            obj(Items.FIRE_RUNE, quantity = 15, slots = 3)
            obj(Items.WATER_RUNE, quantity = 7, slots = 3)
            obj(Items.LAW_RUNE, quantity = 2, slots = 3)
            obj(Items.STEEL_ARROW, quantity = 10, slots = 2)
            obj(Items.MIND_RUNE, quantity = 3, slots = 2)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 2)
            obj(Items.NATURE_RUNE, quantity = 6, slots = 2)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 1)
            obj(Items.DEATH_RUNE, quantity = 2, slots = 1)

            table(Herbs.minorHerbTable, slots = 7)
            table(Seeds.allotmentSeedTable, slots = 18)

            obj(Items.COINS_995, quantity = 38, slots = 15)
            obj(Items.COINS_995, quantity = 52, slots = 10)
            obj(Items.COINS_995, quantity = 15, slots = 8)
            obj(Items.COINS_995, quantity = 7, slots = 6)
            obj(Items.COINS_995, quantity = 88, slots = 2)

            nothing(slots = 1)
            obj(Items.LIMPWURT_ROOT, quantity = 1, slots = 11)
            obj(Items.BEER, quantity = 1, slots = 6)
            obj(Items.BODY_TALISMAN, quantity = 1, slots = 2)

            table(Gems.gemTable, slots = 3)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 150)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 10)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 828)
        }
    }

table.register(hillgiant, *ids)

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
            attackSpeed = 6
            respawnDelay = 30
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 350
            attack = 18
            strength = 22
            defence = 26
        }
        bonuses {
            attackStab = 18
            attackCrush = 16
        }
        anims {
            attack = 4652
            block = 4651
            death = 4653
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.HILL_GIANT
            level = 1
            experience = 35.0
        }
    }
}
