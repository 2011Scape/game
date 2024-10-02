package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.action.NpcDeathAction
import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Seeds

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.ROCKSLUG, Npcs.ROCKSLUG_1632)

val table = DropTableFactory
val rockSlug =
    table.build {
        main {
            total(total = 512)

            obj(Items.MYSTIC_GLOVES_4115, slots = 1)

            obj(Items.EARTH_RUNE, quantity = 5, slots = 120)
            obj(Items.EARTH_RUNE, quantity = 42, slots = 16)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 16)

            obj(Items.COAL, slots = 52)
            obj(Items.IRON_ORE, slots = 88)
            obj(Items.BRONZE_BAR, slots = 8)
            obj(Items.IRON_BAR, slots = 12)
            obj(Items.COPPER_ORE, slots = 12)
            obj(Items.TIN_ORE, slots = 32)
            obj(Items.MITHRIL_ORE, slots = 4)

            table(Seeds.allotmentSeedTable, slots = 36)

            obj(Items.DWARVEN_STOUT, slots = 52)
            obj(Items.HAMMER, slots = 40)

            table(Gems.gemTable, slots = 23)
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

table.register(rockSlug, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ROCKSLUG_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            attackStyle = StyleType.CRUSH
        }
        stats {
            hitpoints = 270
            attack = 22
            strength = 27
            defence = 27
        }
        anims {
            attack = 9506
            block = 9508
            death = 9507
        }
        slayer {
            assignment = SlayerAssignment.ROCK_SLUG
            experience = 27.0
            level = 20
            deathBlowLifepoints = 50
        }
    }

    on_item_on_npc(item = Items.BAG_OF_SALT, npc = it) {
        val npc = player.getInteractingNpc()
        if (npc.getCombatTarget() == player) {
            player.inventory.remove(Items.BAG_OF_SALT, amount = 1)
            if (npc.getCurrentLifepoints() > npc.combatDef.deathBlowLifepoints) {
                player.filterableMessage("Your bag of salt is ineffective. The rockslug is not weak enough.")
                return@on_item_on_npc
            }
            npc.setCurrentLifepoints(0)
            npc.executePlugin(NpcDeathAction.deathPlugin)
            player.filterableMessage("The rockslug shrivels up and dies.")
        }
    }
}
