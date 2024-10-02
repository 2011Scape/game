package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.TZHAARXIL, Npcs.TZHAARXIL_2609)

val table = DropTableFactory
val tzhaarXil = table.build {
    main {
        total(128)

        obj(Items.TOKKUL, quantity = world.random(100..1500), slots = 46)

        obj(Items.SUPER_STRENGTH_1, slots = 4)
        obj(Items.SUPER_ATTACK_1, slots = 4)
        obj(Items.UNCUT_SAPPHIRE, slots = 22)
        obj(Items.UNCUT_EMERALD, slots = 22)
        obj(Items.UNCUT_RUBY, slots = 6)
        obj(Items.UNCUT_DIAMOND, slots = 6)
        obj(Items.LOBSTER, slots = 6)
        obj(Items.ONYX_BOLT_TIPS, slots = 12)
    }
    table("Tokkul") {
        total(total = 440)
        obj(Items.TOKKUL, quantity = 15_000, slots = 1)
        nothing(slots = 439)
    }
    table("Obsidian") {
        total(total = 512)
        obj(Items.TOKTZXILUL, quantity = world.random(9..29), slots = 1)
        obj(Items.TOKTZXILAK, slots = 1)
        obj(Items.TOKTZXILEK, slots = 1)
        obj(Items.OBSIDIAN_CAPE, slots = 1)
        nothing(slots = 508)
    }
    table("Charms") {
        total(1000)
        obj(Items.OBSIDIAN_CHARM, quantity = 2, slots = 720)
        nothing(slots = 280)
    }
}

table.register(tzhaarXil, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.TZHAAR_XIL_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 50
        }
        stats {
            hitpoints = 1200
            attack = 140
            strength = 100
            defence = 100
            magic = 40
            ranged = 120
        }
        anims {
            attack = 9345
            death = 9291
            block = 9287
        }
    }
}