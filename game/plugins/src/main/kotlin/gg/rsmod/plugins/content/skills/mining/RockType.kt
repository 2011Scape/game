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
        objectIds = arrayOf(Objs.ROCKS_15504, Objs.ROCKS_15503, Objs.ROCKS_15505)
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
            Objs.ROCKS_9710, Objs.ROCKS_9708, Objs.ROCKS_9709)
    ),
    TIN(
        level = 1,
        experience = 17.5,
        reward = Items.TIN_ORE,
        respawnDelay = 4,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,
        highChance = 379,
        objectIds = arrayOf(Objs.ROCKS_3038, Objs.ROCKS_3245, Objs.ROCKS_11933, Objs.ROCKS_11959, Objs.ROCKS_11957, Objs.ROCKS_11948, Objs.ROCKS_11949, Objs.ROCKS_11950,
            Objs.ROCKS_11958, Objs.ROCKS_9713, Objs.ROCKS_9711, Objs.ROCKS_9716, Objs.ROCKS_9714)
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
            Objs.ROCKS_9719, Objs.ROCKS_9718)
    ),
    SILVER(
        level = 20,
        experience = 40.0,
        reward = Items.SILVER_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 24,
        highChance = 200,
        objectIds = arrayOf(Objs.ROCKS_37306, Objs.ROCKS_37304, Objs.ROCKS_37305, Objs.ROCKS_2311)
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
            Objs.ROCKS_3233, Objs.ROCKS_3032)
    ),
    GOLD(
        level = 40,
        experience = 65.0,
        reward = Items.GOLD_ORE,
        respawnDelay = 100,
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 6,
        highChance = 75,
        objectIds = arrayOf(Objs.ROCKS_37310, Objs.ROCKS_37312, Objs.ROCKS_9720, Objs.ROCKS_9722, Objs.ROCKS_11951, Objs.ROCKS_11952, Objs.ROCKS_11953, Objs.ROCKS_2098, Objs.ROCKS_2099)
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
            Objs.ROCKS_3280, Objs.ROCKS_3041)
    ),
    ADAMANTITE(
        level = 75,
        experience = 95.0,
        reward = Items.ADAMANTITE_ORE,
        respawnDelay = 400,
        varrockArmourAffected = Items.VARROCK_ARMOUR_3,
        lowChance = 1,
        highChance = 25,
        objectIds = arrayOf(Objs.ROCKS_11939, Objs.ROCKS_11941, Objs.ROCKS_2104, Objs.ROCKS_2105, Objs.ROCKS_14862, Objs.ROCKS_14863, Objs.ROCKS_14864, Objs.ROCKS_3040, Objs.ROCKS_3273)
    ),
    RUNITE(
        level = 85,
        experience = 125.0,
        reward = Items.RUNITE_ORE,
        respawnDelay = 1200,
        varrockArmourAffected = Items.VARROCK_ARMOUR_4,
        lowChance = 1,
        highChance = 18,
        objectIds = arrayOf(Objs.ROCKS_14859, Objs.ROCKS_14860)
    );

    companion object {
        val values = enumValues<RockType>()
        val objects = RockType.values().flatMap { it.objectIds.toList() }
    }
}