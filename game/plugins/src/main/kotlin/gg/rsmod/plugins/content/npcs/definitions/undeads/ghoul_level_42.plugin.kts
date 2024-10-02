package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.GHOUL

val table = DropTableFactory
val ghoul =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            // DROP RATE REFERENCE
            // 8, 32, 128, 256, 512
            total(total = 512)
            // TODO: ADD THIS LINE FOR RAG BONE MAN QUEST
            // obj(Items.GHOUL_BONE, quantity = 1, slots = 128)
            obj(Items.LAW_TALISMAN, quantity = 1, slots = 16)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)
            obj(Items.DEATH_TALISMAN, quantity = 1, slots = 16)
            obj(Items.BLOOD_TIARA, quantity = 1, slots = 2)
            nothing(477)
        }
    }

table.register(ghoul, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GHOUL_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        respawnDelay = 60
        deathDelay = 1
    }
    stats {
        hitpoints = 500
        attack = 30
        strength = 40
        defence = 30
        magic = 1
        ranged = 1
    }
    anims {
        attack = 7183
        block = 7186
        death = 836
    }
    aggro {
        radius = 4
    }
    slayer {
        assignment = SlayerAssignment.GHOUL
        level = 1
        experience = 50.0
    }
}
