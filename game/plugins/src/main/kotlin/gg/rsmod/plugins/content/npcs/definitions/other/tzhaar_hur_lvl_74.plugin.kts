package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.TZHAARHUR)

val table = DropTableFactory
val tzhaarHur =
    table.build {
        main {
            total(128)

            obj(Items.TOKKUL, quantity = world.random(150..800), slots = 32)

            obj(Items.GOLD_ORE, slots = 12)
            obj(Items.SILVER_ORE_NOTED, quantity = world.random(1..2), slots = 11)
            obj(Items.SOFT_CLAY_NOTED, quantity = 3, slots = 8)
            obj(Items.STEEL_BAR, slots = 8)

            obj(Items.ONYX_BOLT_TIPS, slots = 2)
            obj(Items.MOLTEN_GLASS, quantity = 2, slots = 6)
            obj(Items.BATTLESTAFF, slots = 6)
            obj(Items.HARD_LEATHER_NOTED, slots = 6)
            obj(Items.LEATHER_NOTED, quantity = 3, slots = 6)
            obj(Items.WOOL_NOTED, quantity = 8, slots = 6)
            obj(Items.FLAX_NOTED, quantity = world.random(5..10), slots = 6)
            obj(Items.PURE_ESSENCE_NOTED, quantity = world.random(4..6), slots = 6)
            obj(Items.LOBSTER, slots = 6)
            obj(Items.SUPER_ATTACK_1, slots = 3)
            obj(Items.SUPER_STRENGTH_1, slots = 3)

            table(Rare.rareTable, slots = 1)
        }
        table("Charms") {
            total(1000)
            obj(Items.OBSIDIAN_CHARM, quantity = 1, slots = 360)
            obj(Items.OBSIDIAN_CHARM, quantity = 2, slots = 360)
            nothing(slots = 280)
        }
    }

table.register(tzhaarHur, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.TZHAAR_HUR_DEATH)
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
            hitpoints = 800
            attack = 60
            strength = 60
            defence = 60
            magic = 80
        }
        anims {
            attack = Anims.TZHAAR_HUR_ATTACK
            death = Anims.TZHAAR_HUR_DEATH
            block = Anims.TZHAAR_HUR_BLOCK
        }
    }
}
