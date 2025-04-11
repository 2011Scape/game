package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.action.NpcDeathAction
import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val ids = intArrayOf(Npcs.GARGOYLE, Npcs.GARGOYLE_9087)

val table = DropTableFactory
val gargoyle =
    table.build {
        main {
            total(total = 546)
            // WEAPONS
            obj(Items.STEEL_2H_SWORD, quantity = 1, slots = 64)
            obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 64)
            obj(Items.STEEL_HATCHET, quantity = 1, slots = 64)
            obj(Items.GRANITE_MAUL, quantity = 1, slots = 2)
            // ARMOUR
            obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 12)
            obj(Items.ADAMANT_BOOTS, quantity = 1, slots = 12)
            obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 4)
            obj(Items.MYSTIC_ROBE_TOP_4101, quantity = 1, slots = 1)
            // RUNES
            obj(Items.FIRE_RUNE, quantity = listOf(37, 75).random(), slots = 64)
            obj(Items.CHAOS_RUNE, quantity = 15, slots = 64)
            obj(Items.DEATH_RUNE, quantity = 5, slots = 16)
            // OTHER
            obj(Items.COINS_995, quantity = listOf(11, 44, 132, 220, 460).random(), slots = 64)
            obj(Items.PURE_ESSENCE_NOTED, quantity = 35, slots = 24)
            obj(Items.STEEL_BAR, quantity = 3, slots = 24)
            obj(Items.GOLD_BAR, quantity = 1, slots = 12)
            obj(Items.THREAD, quantity = 10, slots = 16)
            obj(Items.FEROCIOUS_RING_5, quantity = 1, slots = 16)
            obj(Items.CLUE_SCROLL_HARD, quantity = 1, slots = 2)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)
            // RARE
            table(Rare.rareTable, slots = 20)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 100)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 50)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 50)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 70)
            nothing(slots = 730)
        }
    }

table.register(gargoyle, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GARGOYLE_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            deathDelay = 2
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 1050
            attack = 75
            strength = 105
            defence = 107
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 20
            defenceMagic = 20
            defenceRanged = 20
        }
        anims {
            attack = Anims.GARGOYLE_ATTACK
            block = Anims.GARGOYLE_BLOCK
            death = Anims.GARGOYLE_DEATH
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.GARGOYLE
            experience = 105.0
            level = 75
            deathBlowLifepoints = 100
        }
    }

    // TODO: Fix error Npc is already bound to a plugin: 1610 [opt=3]
    /**on_npc_option(npc = Npcs.GARGOYLE, "smash") {
     val npc = player.getInteractingNpc()
     if (npc.getCombatTarget() == player) {
     if (npc.getCurrentLifepoints() > npc.combatDef.deathBlowLifepoints) {
     player.filterableMessage("The gargoyle is not weak enough to smash yet.")
     } else if (player.inventory.contains(Items.ROCK_HAMMER)) {
     player.animate(1755)
     npc.setTransmogId(Npcs.GARGOYLE_1827)
     npc.setCurrentLifepoints(0)
     npc.executePlugin(NpcDeathAction.deathPlugin)
     player.filterableMessage("You smash the gargoyle with the rock hammer and it shatters into pieces.")
     } else {
     player.filterableMessage("You need a rock hammer to smash the gargoyle.")
     }
     }
     }**/

    on_item_on_npc(item = Items.ROCK_HAMMER, npc = it) {
        val npc = player.getInteractingNpc()
        if (npc.getCombatTarget() == player) {
            if (npc.getCurrentLifepoints() > npc.combatDef.deathBlowLifepoints) {
                player.filterableMessage("The gargoyle is not weak enough to smash yet.")
                return@on_item_on_npc
            }
            player.animate(Anims.HAMMER_GARGOYLE)
            npc.setTransmogId(Npcs.GARGOYLE_1827)
            npc.setCurrentLifepoints(0)
            npc.executePlugin(NpcDeathAction.deathPlugin)
            player.filterableMessage("You smash the gargoyle with the rock hammer and it shatters into pieces.")
        }
    }
}
