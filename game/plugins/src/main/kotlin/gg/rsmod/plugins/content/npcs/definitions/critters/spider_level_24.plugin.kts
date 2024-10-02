package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.SPIDER_4401)

val table = DropTableFactory
val spider =
    table.build {
        main {
            total(200)
            obj(Items.GOLD_CHARM, slots = 5)
            obj(Items.GREEN_CHARM, slots = 7)
            obj(Items.CRIMSON_CHARM, slots = 3)
            obj(Items.BLUE_CHARM, slots = 2)
            nothing(slots = 183)
        }
    }

table.register(spider, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SMALL_SPIDER_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 27
            attackStyle = StyleType.STAB
        }
        stats {
            hitpoints = 220
            attack = 21
            strength = 21
            defence = 21
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 53
            attackCrush = 53
            defenceStab = 53
            defenceSlash = 53
            defenceCrush = 53
            defenceMagic = 53
            defenceRanged = 53
        }
        anims {
            attack = 6249
            death = 6251
            block = 6250
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.SPIDER
            level = 1
            experience = 22.0
        }
    }
}
