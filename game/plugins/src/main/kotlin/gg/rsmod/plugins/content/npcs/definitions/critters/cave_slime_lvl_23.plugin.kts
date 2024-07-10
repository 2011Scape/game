package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.CAVE_SLIME)

val table = DropTableFactory
val caveSlime =
    table.build {
        guaranteed {
            obj(Items.SWAMP_TAR, quantity = world.random(1..6))
        }
        main {
            total(total = 128)

            obj(Items.BRONZE_HATCHET, slots = 3)
            obj(Items.IRON_SWORD, slots = 7)
            obj(Items.BRONZE_FULL_HELM, slots = 1)
            obj(Items.IRON_KITESHIELD, slots = 2)
            obj(Items.IRON_BOOTS, slots = 1)

            obj(Items.WATER_RUNE, quantity = 15, slots = 5)
            obj(Items.EARTH_RUNE, quantity = 5, slots = 3)

            obj(Items.COINS_995, quantity = 1, slots = 7)
            obj(Items.COINS_995, quantity = 4, slots = 30)
            obj(Items.COINS_995, quantity = 10, slots = 39)
            obj(Items.COINS_995, quantity = 22, slots = 10)
            obj(Items.COINS_995, quantity = 46, slots = 2)

            obj(Items.UNLIT_TORCH, slots = 11)
            obj(Items.OIL_LANTERN_FRAME, slots = 1)
            obj(Items.GOLD_BAR, slots = 2)

            table(Gems.gemTable, slots = 4)
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

table.register(caveSlime, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SLIME_DEATH)
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
            poisonDamage = 3
        }
        stats {
            hitpoints = 250
            attack = 13
            strength = 13
            defence = 35
            magic = 13
        }
        anims {
            attack = 1789
            death = 1792
        }
        slayer {
            assignment = SlayerAssignment.CAVE_SLIME
            experience = 25.0
            level = 17
        }
    }
}
