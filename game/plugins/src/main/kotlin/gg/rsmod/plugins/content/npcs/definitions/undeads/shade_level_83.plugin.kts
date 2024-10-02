package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.SHADE

val table = DropTableFactory
val shade =
    table.build {

        main {
            total(total = 4)
            obj(Items.SHADE_ROBE, quantity = 1, slots = 1)
            obj(Items.SHADE_ROBE_548, quantity = 1, slots = 1)
            nothing(2)
        }
    }

table.register(shade, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SHADE_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        deathDelay = 2
        respawnDelay = 50
        attackStyle = StyleType.CRUSH
    }
    stats {
        hitpoints = 700
        attack = 74
        strength = 74
        defence = 70
        magic = 1
        ranged = 1
    }
    anims {
        attack = 394
        death = 843
        block = 399
    }
    slayer {
        assignment = SlayerAssignment.SHADE
        level = 1
        experience = 4.2
    }
}
