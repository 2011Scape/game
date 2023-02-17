package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs

enum class RockType(
    val level: Int, val experience: Double, val reward: Int, val respawnDelay: Int, val varrockArmourAffected: Int,
    val lowChance: Int, val highChance: Int, val objectIds: Array<Int>
) {
    CLAY(
        level = 5,
        experience = 5.0,
        reward = Items.CLAY,
        respawnDelay = 2,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,
        highChance = 392,
        objectIds = emptyArray()
    ),
    COPPER(
        level = 1,
        experience = 17.5,
        reward = Items.COPPER_ORE,
        respawnDelay = 4,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,
        highChance = 379,
        objectIds = arrayOf(Objs.ROCKS_3229, Objs.ROCKS_3027)
    ),
    TIN(
        level = 1,
        experience = 17.5,
        reward = Items.TIN_ORE,
        respawnDelay = 4,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,
        highChance = 379,
        objectIds = arrayOf(Objs.ROCKS_3038, Objs.ROCKS_3245)
    ),
    IRON(
        level = 15,
        experience = 35.0,
        reward = Items.IRON_ORE,
        respawnDelay = 9,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 109,
        highChance = 346,
        objectIds = emptyArray()
    ),
    SILVER(
        level = 20,
        experience = 40.0,
        reward = Items.SILVER_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 24,
        highChance = 200,
        objectIds = emptyArray()
    ),
    COAL(
        level = 30,
        experience = 50.0,
        reward = Items.COAL,
        respawnDelay = 50,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 15,
        highChance = 100,
        objectIds = emptyArray()
    ),
    GOLD(
        level = 40,
        experience = 65.0,
        reward = Items.GOLD_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 6,
        highChance = 75,
        objectIds = emptyArray()
    ),
    MITHRIL(
        level = 55,
        experience = 80.0,
        reward = Items.MITHRIL_ORE,
        respawnDelay = 200,
        varrockArmourAffected = Items.VARROCK_ARMOUR_2,
        lowChance = 2,
        highChance = 50,
        objectIds = emptyArray()
    ),
    ADAMANTITE(
        level = 75,
        experience = 95.0,
        reward = Items.ADAMANTITE_ORE,
        respawnDelay = 400,
        varrockArmourAffected = Items.VARROCK_ARMOUR_3,
        lowChance = 1,
        highChance = 25,
        objectIds = emptyArray()
    ),
    RUNITE(
        level = 85,
        experience = 125.0,
        reward = Items.RUNITE_ORE,
        respawnDelay = 1200,
        varrockArmourAffected = Items.VARROCK_ARMOUR_4,
        lowChance = 1,
        highChance = 18,
        objectIds = emptyArray()
    );

    companion object {
        val values = enumValues<RockType>()
        val objects = RockType.values().flatMap { it.objectIds.toList() }
    }
}