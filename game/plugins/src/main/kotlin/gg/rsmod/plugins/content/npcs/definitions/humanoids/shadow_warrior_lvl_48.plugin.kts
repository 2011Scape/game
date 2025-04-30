package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val table = DropTableFactory
val warrior = table.build {
    main {
        total(256)
        obj(Items.BLACK_FULL_HELM, slots = 4)
        obj(Items.BLACK_DAGGER_P, slots = 2)
        obj(Items.BLACK_ROBE, slots = 2)
        obj(Items.BLACK_KNIFE, slots = 2)
        obj(Items.BLACK_LONGSWORD, slots = 1)
        obj(Items.ADAMANT_SPEAR, slots = 1)
        obj(Items.MITHRIL_MACE, slots = 1)

        table(Herbs.minorHerbTable, slots = 36)

        obj(Items.COSMIC_RUNE, quantity = 3, slots = 16)
        obj(Items.BLOOD_RUNE, quantity = 2, slots = 12)
        obj(Items.DEATH_RUNE, quantity = 2, slots = 12)

        table(Rare.rareTable, slots = 1)
        nothing(166)
    }
}

table.register(warrior, Npcs.SHADOW_WARRIOR)

on_npc_pre_death(Npcs.SHADOW_WARRIOR) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(Npcs.SHADOW_WARRIOR) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(Npcs.SHADOW_WARRIOR) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 670
        attack = 60
        strength = 55
        defence = 47
    }
    anims {
        attack = Anims.ATTACK_HACK
        block = Anims.BLOCK_UNARMED
        death = Anims.HUMAN_DEATH
    }
    aggro {
        radius = 5
    }
    slayer {
        assignment = SlayerAssignment.GHOST
        level = 1
        experience = 67.0
    }
}
