package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.attr.HAS_SPAWNED_TREE_SPIRIT
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val treeSpirit = Npcs.TREE_SPIRIT

val table = DropTableFactory
val treeSpiritTable =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(treeSpiritTable, treeSpirit)

on_npc_pre_death(treeSpirit) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BANSHEE_DEATH)
    when (p.getCurrentStage(LostCity)) {
        LostCity.ENTRANA_DUNGEON -> {
            p.queue {
                p.attr.remove(HAS_SPAWNED_TREE_SPIRIT)
                p.advanceToNextStage(LostCity)
                messageBox("With the tree spirit defeated, you can now chop the tree.")
            }
        }
    }
}

on_npc_death(treeSpirit) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

on_npc_spawn(treeSpirit) {
    npc.forceChat("You must defeat me before touching the tree!")
}

set_combat_def(treeSpirit) {
    configs {
        attackSpeed = 4
        respawnDelay = 0
        attackStyle = StyleType.CRUSH
    }
    stats {
        hitpoints = 220
        attack = 90
        strength = 95
        defence = 80
    }
    bonuses {
        defenceStab = 5
        defenceSlash = 5
        defenceCrush = 5
        defenceMagic = 0
        defenceRanged = 5
    }
    aggro {
        radius = 4
    }
    anims {
        attack = 5532
        death = 5534
        block = 5533
    }
}
