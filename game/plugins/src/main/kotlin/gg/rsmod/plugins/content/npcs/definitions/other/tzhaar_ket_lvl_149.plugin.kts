package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.TZHAARKET_2616)

val table = DropTableFactory
val tzhaarKet = table.build {
    main {
        total(128)

        obj(Items.TOKKUL, quantity = world.random(100..2000), slots = 69)

        obj(Items.FIRE_BATTLESTAFF, quantity = 1, slots = 1)

        obj(Items.SUPER_STRENGTH_1, quantity = 1, slots = 4)
        obj(Items.SUPER_DEFENCE_1, quantity = 1, slots = 4)
        obj(Items.UNCUT_SAPPHIRE, quantity = 1, slots = 12)
        obj(Items.UNCUT_EMERALD, quantity = 1, slots = 12)
        obj(Items.UNCUT_RUBY, quantity = 1, slots = 6)
        obj(Items.UNCUT_DIAMOND, quantity = 1, slots = 6)
        obj(Items.LOBSTER, quantity = 1, slots = 6)
        obj(Items.CHILLI_POTATO, quantity = 1, slots = 6)
        obj(Items.PURE_ESSENCE_NOTED, quantity = world.random(5..10), slots = 1)
        obj(Items.ONYX_BOLT_TIPS, quantity = world.random(1..4), slots = 1)
    }
    table("Obsidian") {
        total(total = 512)
        obj(Items.TZHAARKETOM, quantity = 1, slots = 1)
        obj(Items.TOKTZKETXIL, quantity = 1, slots = 1)
        obj(Items.OBSIDIAN_CAPE, quantity = 1, slots = 1)
        nothing(slots = 509)
    }
    table("Charms") {
        total(1000)
        obj(Items.OBSIDIAN_CHARM, quantity = 2, slots = 720)
        nothing(slots = 280)
    }
}

table.register(tzhaarKet, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.TZHAAR_KET_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 1400
            attack = 120
            strength = 140
            defence = 120
            magic = 40
            ranged = 1
        }
        anims {
            attack = 9345
            death = 9288
            block = 9287
        }
    }
}