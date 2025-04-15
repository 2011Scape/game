package gg.rsmod.plugins.content.npcs.definitions.undeads.revenants

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.REVENANT_GOBLIN_13468
val table = DropTableFactory

val goblin =
    table.build {
        main {
            total(225_000)

            // Least rare, but still rare 1/5k
            obj(Items.BROKEN_STATUE_HEADDRESS, 1, 45)
            obj(Items.THIRD_AGE_CARAFE, 1, 45)
            obj(Items.BRONZED_DRAGON_CLAW, 1, 45)
            obj(Items.ANCIENT_PSALTERY_BRIDGE, 1, 45)
            obj(Items.SARADOMIN_AMPHORA, 1, 45)

            // Rare ~1/7.5k
            obj(Items.BANDOS_SCRIMSHAW, 1, 30)
            obj(Items.SARADOMIN_CARVING, 1, 30)
            obj(Items.ZAMORAK_MEDALLION, 1, 30)
            obj(Items.ARMADYL_TOTEM, 1, 30)
            obj(Items.GUTHIXIAN_BRAZIER, 1, 30)
            obj(Items.RUBY_CHALICE, 1, 30)

            obj(Items.CORRUPT_DRAGON_BATTLEAXE, 1, 30)
            obj(Items.CORRUPT_DRAGON_DAGGER, 1, 30)
            obj(Items.CORRUPT_DRAGON_LONGSWORD, 1, 30)
            obj(Items.CORRUPT_DRAGON_MACE, 1, 30)
            obj(Items.CORRUPT_DRAGON_SCIMITAR, 1, 30)
            obj(Items.CORRUPT_DRAGON_SPEAR, 1, 30)
            obj(Items.CORRUPT_DRAGON_CHAINBODY, 1, 30)
            obj(Items.CORRUPT_DRAGON_MED_HELM, 1, 30)
            obj(Items.CORRUPT_DRAGON_PLATELEGS, 1, 30)
            obj(Items.CORRUPT_DRAGON_PLATESKIRT, 1, 30)
            obj(Items.CORRUPT_DRAGON_SQ_SHIELD, 1, 30)

            // Rare AF ~1/15k
            obj(Items.BANDOS_STATUETTE, 1, 15)
            obj(Items.SARADOMIN_STATUETTE, 1, 15)
            obj(Items.ZAMORAK_STATUETTE, 1, 15)
            obj(Items.ARMADYL_STATUETTE, 1, 15)
            obj(Items.SEREN_STATUETTE, 1, 15)
            obj(Items.ANCIENT_STATUETTE, 1, 15)

            // Ancient Warrior Equip ~1/25k
            obj(Items.MORRIGANS_JAVELIN, quantityRange = 15..50, 9)
            obj(Items.MORRIGANS_THROWING_AXE, quantityRange = 15..50, 9)
            obj(Items.STATIUSS_WARHAMMER, 1, 9)
            obj(Items.VESTAS_LONGSWORD, 1, 9)
            obj(Items.VESTAS_SPEAR, 1, 9)
            obj(Items.ZURIELS_STAFF, 1, 9)
            obj(Items.MORRIGANS_COIF, 1, 9)
            obj(Items.MORRIGANS_LEATHER_BODY, 1, 9)
            obj(Items.MORRIGANS_LEATHER_CHAPS, 1, 9)
            obj(Items.STATIUSS_FULL_HELM, 1, 9)
            obj(Items.STATIUSS_PLATEBODY, 1, 9)
            obj(Items.STATIUSS_PLATELEGS, 1, 9)
            obj(Items.VESTAS_CHAINBODY, 1, 9)
            obj(Items.VESTAS_PLATESKIRT, 1, 9)
            obj(Items.ZURIELS_HOOD, 1, 9)
            obj(Items.ZURIELS_ROBE_TOP, 1, 9)
            obj(Items.ZURIELS_ROBE_BOTTOM, 1, 9)

            obj(Items.CORRUPT_MORRIGANS_JAVELIN, quantityRange = 15..50, 9)
            obj(Items.C_MORRIGANS_THROWING_AXE, quantityRange = 15..50, 9)
            obj(Items.CORRUPT_STATIUSS_WARHAMMER, 1, 9)
            obj(Items.CORRUPT_VESTAS_LONGSWORD, 1, 9)
            obj(Items.CORRUPT_VESTAS_SPEAR, 1, 9)
            obj(Items.CORRUPT_ZURIELS_STAFF, 1, 9)
            obj(Items.CORRUPT_MORRIGANS_COIF, 1, 9)
            obj(Items.CORRUPT_MORRIGANS_LEATHER_BODY, 1, 9)
            obj(Items.CORRUPT_STATIUSS_FULL_HELM, 1, 9)
            obj(Items.CORRUPT_STATIUSS_PLATEBODY, 1, 9)
            obj(Items.CORRUPT_STATIUSS_PLATELEGS, 1, 9)
            obj(Items.CORRUPT_VESTAS_CHAINBODY, 1, 9)
            obj(Items.CORRUPT_VESTAS_PLATESKIRT, 1, 9)
            obj(Items.CORRUPT_ZURIELS_HOOD, 1, 9)
            obj(Items.CORRUPT_ZURIELS_ROBE_TOP, 1, 9)
            obj(Items.CORRUPT_ZURIELS_ROBE_BOTTOM, 1, 9)

            // Rarest 1 or 2 in 225k
            obj(Items.BRAWLING_GLOVES_AGILITY, slots = 1)
            obj(Items.BRAWLING_GLOVES_COOKING, slots = 1)
            obj(Items.BRAWLING_GLOVES_FISHING, slots = 1)
            obj(Items.BRAWLING_GLOVES_FM, slots = 1)
            obj(Items.BRAWLING_GLOVES_HUNTER, slots = 2)
            obj(Items.BRAWLING_GLOVES_MAGIC, slots = 1)
            obj(Items.BRAWLING_GLOVES_MELEE, slots = 1)
            obj(Items.BRAWLING_GLOVES_MINING, slots = 1)
            obj(Items.BRAWLING_GLOVES_PRAYER, slots = 1)
            obj(Items.BRAWLING_GLOVES_RANGED, slots = 1)
            obj(Items.BRAWLING_GLOVES_SMITHING, slots = 2)
            obj(Items.BRAWLING_GLOVES_THIEVING, slots = 1)
            obj(Items.BRAWLING_GLOVES_WC, slots = 1)

            val takenSlots = 1137
            val remaining = 225_000 - takenSlots
            obj(Items.COINS_995, quantityRange = 1..200, remaining)
        }
    }

table.register(goblin, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GHOST_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

on_npc_spawn(id) {
    npc.animate(Anims.REVENANT_GOBLIN_SPAWN)
}

set_combat_def(id) {
    configs {
        attackSpeed = 5
        respawnDelay = 30
    }
    species {
        NpcSpecies.UNDEAD
    }
    stats {
        hitpoints = 320
        attack = 95
        strength = 95
        defence = 70
        magic = 95
        ranged = 95
    }
    bonuses {
        defenceStab = 70
        defenceSlash = 70
        defenceCrush = 70
        defenceMagic = 70
        defenceRanged = 70
    }
    anims {
        attack = Anims.REVENANT_GOBLIN_MELEE_ATTACK
        block = Anims.REVENANT_GOBLIN_BLOCK
        death = Anims.REVENANT_GOBLIN_DEATH
    }
    aggro {
        radius = 5
    }
    slayer {
        level = 1
        experience = 35.0
        assignment = SlayerAssignment.GHOST
    }
}
