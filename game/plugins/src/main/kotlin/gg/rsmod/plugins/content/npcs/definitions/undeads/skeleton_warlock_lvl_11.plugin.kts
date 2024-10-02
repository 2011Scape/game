package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.SKELETON_WARLOCK
val table = DropTableFactory
val skeleton =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(skeleton, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SKELETON_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        respawnDelay = 0
        attackStyle = StyleType.MAGIC
        spell = 996
    }
    stats {
        hitpoints = 180
        attack = 1
        strength = 1
        defence = 1
        magic = 8
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 0
        defenceSlash = 0
        defenceCrush = 0
        defenceMagic = 5
        defenceRanged = 0
    }
    anims {
        attack = 724
        block = 424
        death = 836
    }
    slayer {
        assignment = SlayerAssignment.SKELETON
        level = 1
        experience = 13.8
    }
}
