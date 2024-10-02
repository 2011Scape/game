package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.CATABLEPON)
val table = DropTableFactory
val catablepon =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(256)
            obj(Items.BLACK_FULL_HELM, quantity = 1, slots = 9)
            obj(Items.ADAMANT_MED_HELM, quantityRange = 5..17, slots = 9)
            obj(Items.MITHRIL_ARROW, quantityRange = 5..14, slots = 9)
            obj(Items.ADAMANT_ARROW, quantity = 1, slots = 9)
            obj(Items.TROUT, quantity = 1, slots = 9)
            obj(Items.SALMON, quantity = 1, slots = 9)
            obj(Items.RUNE_ESSENCE_NOTED, quantity = 15, slots = 20)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 15, slots = 9)
            obj(Items.WATER_RUNE, quantity = 7, slots = 20)
            obj(Items.FIRE_RUNE, quantity = 15, slots = 9)
            obj(Items.COSMIC_RUNE, quantityRange = 2..4, slots = 9)
            obj(Items.CHAOS_RUNE, quantity = 7, slots = 9)
            obj(Items.LAW_RUNE, quantity = 2, slots = 9)
            obj(Items.COINS_995, quantity = 1, slots = 20)
            obj(Items.EYE_OF_NEWT, quantity = 1, slots = 20)
            obj(Items.UNLIT_TORCH, quantity = 1, slots = 20)
            obj(Items.CLUE_SCROLL_MEDIUM, quantity = 1, slots = 2)
            obj(Items.COAL_NOTED, quantityRange = 3..7, slots = 2)
            obj(Items.TOP_OF_SCEPTRE, quantity = 1, slots = 2)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)
            obj(Items.RUNE_ESSENCE_NOTED, quantity = 30, slots = 25)
            table(Seeds.allotmentSeedTable, slots = 10)
            table(Herbs.minorHerbTable, slots = 10)
            table(Gems.gemTable, slots = 5)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 200)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 20)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 745)
        }
    }

table.register(catablepon, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.CATABLEPON_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            deathDelay = 2
            respawnDelay = 20
            attackStyle = StyleType.STAB
            /**
             * Todo: Add weaken spell once it is implemented. Cast Anim 4272
             */
        }
        stats {
            hitpoints = 400
            attack = 45
            strength = 40
            defence = 40
            magic = 60
        }
        bonuses {
            defenceStab = 40
            defenceSlash = 30
            defenceCrush = 20
            defenceMagic = 20
            defenceRanged = 40
        }
        anims {
            attack = 4271
            block = 4273
            death = 4270
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.CATABLEPON
            level = 1
            experience = 40.0
        }
    }
}
