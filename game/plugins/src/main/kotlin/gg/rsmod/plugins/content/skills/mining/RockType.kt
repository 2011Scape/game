package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs

enum class RockType(
    val level: Int, val experience: Double, val reward: Int, val respawnDelay: Int, val varrockArmourAffected: Int,
    val lowChance: Int, val highChance: Int, val objectIds: Array<Int>
) {
    ESSENCE(
        level = 1,
        experience = 5.0,
        reward = Items.RUNE_ESSENCE,
        respawnDelay = -1,
        varrockArmourAffected = -1,
        lowChance = 255,
        highChance = 392,
        objectIds = arrayOf(Objs.RUNE_ESSENCE_2491)
    ),
    CLAY(
        level = 1,
        experience = 5.0,
        reward = Items.CLAY,
        respawnDelay = 2,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,
        highChance = 392,
        objectIds = arrayOf(Objs.ROCKS_15504, Objs.ROCKS_15503, Objs.ROCKS_15505, Objs.ROCKS_5766, Objs.ROCKS_5767,
            Objs.ROCKS_11190, Objs.ROCKS_11189, Objs.ROCKS_11191)
    ),
    COPPER(
        level = 1,
        experience = 17.5,
        reward = Items.COPPER_ORE,
        respawnDelay = 4,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,
        highChance = 379,
        objectIds = arrayOf(Objs.ROCKS_3229, Objs.ROCKS_3027, Objs.ROCKS_11936, Objs.ROCKS_11937, Objs.ROCKS_11938, Objs.ROCKS_11960, Objs.ROCKS_11962, Objs.ROCKS_11961,
            Objs.ROCKS_9710, Objs.ROCKS_9708, Objs.ROCKS_9709, Objs.ROCKS_5779, Objs.ROCKS_5780, Objs.ROCKS_5781)
    ),
    TIN(
        level = 1,
        experience = 17.5,
        reward = Items.TIN_ORE,
        respawnDelay = 4,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,
        highChance = 379,
        objectIds = arrayOf(Objs.ROCKS_3038, Objs.ROCKS_3245, Objs.ROCKS_11933, Objs.ROCKS_11959, Objs.ROCKS_11957,
            Objs.ROCKS_11958, Objs.ROCKS_9713, Objs.ROCKS_9711, Objs.ROCKS_9716, Objs.ROCKS_9714, Objs.ROCKS_5776,
            Objs.ROCKS_5777, Objs.ROCKS_5778)
    ),
    BLURITE(
        level = 10,
        experience = 17.5,
        reward = Items.BLURITE_ORE,
        respawnDelay = 25,
        varrockArmourAffected = Items.VARROCK_ARMOUR_3,
        lowChance = 106,
        highChance = 365,
        objectIds = arrayOf(Objs.ROCKS_2561, Objs.ROCKS_33221)
    ),
    IRON(
        level = 15,
        experience = 35.0,
        reward = Items.IRON_ORE,
        respawnDelay = 9,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 109,
        highChance = 346,
        objectIds = arrayOf(Objs.ROCKS_37309, Objs.ROCKS_37307, Objs.ROCKS_37308, Objs.ROCKS_11954, Objs.ROCKS_11955, Objs.ROCKS_11956, Objs.ROCKS_9717,
            Objs.ROCKS_9719, Objs.ROCKS_9718, Objs.ROCKS_5773, Objs.ROCKS_5774, Objs.ROCKS_5775, Objs.ROCKS_2092, Objs.ROCKS_2093)
    ),
    SILVER(
        level = 20,
        experience = 40.0,
        reward = Items.SILVER_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 24,
        highChance = 200,
        objectIds = arrayOf(Objs.ROCKS_37306, Objs.ROCKS_37304, Objs.ROCKS_37305, Objs.ROCKS_2311, Objs.ROCKS_37670, Objs.ROCKS_11950, Objs.ROCKS_11948, Objs.ROCKS_11949,
            Objs.ROCKS_15580, Objs.ROCKS_15579, Objs.ROCKS_15581, Objs.ROCKS_11187, Objs.ROCKS_11186)
    ),
    COAL(
        level = 30,
        experience = 50.0,
        reward = Items.COAL,
        respawnDelay = 50,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 15,
        highChance = 100,
        objectIds = arrayOf(Objs.ROCKS_11932, Objs.ROCKS_11930, Objs.ROCKS_11963, Objs.ROCKS_11964, Objs.ROCKS_11931, Objs.ROCKS_2096, Objs.ROCKS_2097, Objs.ROCKS_14850, Objs.ROCKS_14851, Objs.ROCKS_14852,
            Objs.ROCKS_3233, Objs.ROCKS_3032, Objs.ROCKS_5770, Objs.ROCKS_5771, Objs.ROCKS_5772)
    ),
    GOLD(
        level = 40,
        experience = 65.0,
        reward = Items.GOLD_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 6,
        highChance = 75,
        objectIds = arrayOf(Objs.ROCKS_37310, Objs.ROCKS_37312, Objs.ROCKS_9720, Objs.ROCKS_9722, Objs.ROCKS_11951,
            Objs.ROCKS_11952, Objs.ROCKS_11953, Objs.ROCKS_2098, Objs.ROCKS_2099, Objs.ROCKS_5768, Objs.ROCKS_5769,
            Objs.ROCKS_32433, Objs.ROCKS_32432, Objs.ROCKS_32434, Objs.ROCKS_15576, Objs.ROCKS_15577, Objs.ROCKS_15578,
            Objs.ROCKS_11183, Objs.ROCKS_11185, Objs.ROCKS_11184)
    ),
    MITHRIL(
        level = 55,
        experience = 80.0,
        reward = Items.MITHRIL_ORE,
        respawnDelay = 200,
        varrockArmourAffected = Items.VARROCK_ARMOUR_2,
        lowChance = 2,
        highChance = 50,
        objectIds = arrayOf(Objs.ROCKS_11942, Objs.ROCKS_11943, Objs.ROCKS_11944, Objs.ROCKS_11946, Objs.ROCKS_11947, Objs.ROCKS_2102, Objs.ROCKS_2103, Objs.ROCKS_14853, Objs.ROCKS_14854, Objs.ROCKS_14855,
            Objs.ROCKS_3280, Objs.ROCKS_3041, Objs.ROCKS_5784, Objs.ROCKS_5785, Objs.ROCKS_5786)
    ),
    ADAMANTITE(
        level = 70,
        experience = 95.0,
        reward = Items.ADAMANTITE_ORE,
        respawnDelay = 400,
        varrockArmourAffected = Items.VARROCK_ARMOUR_3,
        lowChance = 1,
        highChance = 25,
        objectIds = arrayOf(Objs.ROCKS_11939, Objs.ROCKS_11941, Objs.ROCKS_2104, Objs.ROCKS_2105, Objs.ROCKS_14862, Objs.ROCKS_14863, Objs.ROCKS_14864, Objs.ROCKS_3040, Objs.ROCKS_3273,
            Objs.ROCKS_5782, Objs.ROCKS_5783)
    ),
    CONCENTRATED_COAL(
        level = 77,
        experience = 50.0,
        reward = Items.COAL,
        respawnDelay = 300,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 15,
        highChance = 100,
        objectIds = arrayOf(Objs.MINERAL_DEPOSIT)
    ),
    CONCENTRATED_GOLD(
        level = 80,
        experience = 65.0,
        reward = Items.GOLD_ORE,
        respawnDelay = 300,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 6,
        highChance = 75,
        objectIds = arrayOf(Objs.MINERAL_DEPOSIT_45076)
    ),
    RUNITE(
        level = 85,
        experience = 125.0,
        reward = Items.RUNITE_ORE,
        respawnDelay = 1200,
        varrockArmourAffected = Items.VARROCK_ARMOUR_4,
        lowChance = 1,
        highChance = 18,
        objectIds = arrayOf(Objs.ROCKS_14859, Objs.ROCKS_14860, Objs.ROCKS_33078, Objs.ROCKS_33079)
    );

    companion object {
        val values = enumValues<RockType>()
        val objects = RockType.values().flatMap { it.objectIds.toList() }
    }
}