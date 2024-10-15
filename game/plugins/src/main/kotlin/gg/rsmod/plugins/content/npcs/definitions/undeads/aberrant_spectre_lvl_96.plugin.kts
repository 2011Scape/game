package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare
import gg.rsmod.plugins.content.drops.global.Seeds

val ids =
    intArrayOf(
        Npcs.ABERRANT_SPECTRE,
        Npcs.ABERRANT_SPECTRE_1605,
        Npcs.ABERRANT_SPECTRE_1607,
        Npcs.ABERRANT_SPECTRE_7801,
        Npcs.ABERRANT_SPECTRE_7802,
        Npcs.ABERRANT_SPECTRE_7803,
        Npcs.ABERRANT_SPECTRE_7804,
    )

val table = DropTableFactory

val aberrantSpectre =
    table.build {
        main {
            total(512)
            // Drop rate reference: 1/8 1/32 1/128 1/256 1/512 1/1024 1/5000 1/10000
            // WEAPONS AND ARMOUR
            obj(Items.STEEL_HATCHET, slots = 16)
            obj(Items.MITHRIL_KITESHIELD, slots = 16)
            obj(Items.LAVA_BATTLESTAFF, slots = 16)
            obj(Items.ADAMANT_PLATELEGS, slots = 16)
            obj(Items.RUNE_FULL_HELM, slots = 16)
            obj(Items.MYSTIC_ROBE_BOTTOM_4103, slots = 4)
            // COINS
            val coins = listOf(313, 350, 367, 380, 460)
            val coinsQuantity = coins.random()
            obj(Items.COINS_995, quantity = coinsQuantity, slots = 16)
            // OTHER
            obj(Items.STARVED_ANCIENT_EFFIGY, slots = 4)
            obj(Items.CLUE_SCROLL_HARD, slots = 4)
            obj(Items.COURT_SUMMONS, slots = 4)
            // SEEDS
            table(Seeds.uncommonSeedTable, slots = 76)
            // HERBS
            table(Herbs.minorMultipleHerbTable, slots = 232)
            // RARE
            table(Rare.rareTable, slots = 20)
            // NOTHING
            nothing(slots = 72)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 120)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 60)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 40)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 50)
            nothing(slots = 730)
        }
    }

table.register(aberrantSpectre, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SPECTRE_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    on_npc_spawn(it) {
        npc.animate(4127)
    }
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
        }
        stats {
            hitpoints = 900
            attack = 1
            strength = 1
            defence = 90
            magic = 105
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 20
            defenceCrush = 20
            defenceMagic = 0
            defenceRanged = 15
        }
        anims {
            attack = 9466
            death = 9467
            block = 9468
        }
        aggro {
            radius = 6
            aggroMinutes = 10
        }
        slayer {
            assignment = SlayerAssignment.ABERRANT_SPECTRE
            level = 60
            experience = 90.0
        }
    }
}
