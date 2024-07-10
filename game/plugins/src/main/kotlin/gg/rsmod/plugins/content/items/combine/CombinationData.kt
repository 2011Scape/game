package gg.rsmod.plugins.content.items.combine

import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items

/**
 * Handles all the data relation to combining items and giving experience in the relative skill.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
enum class CombinationData(
    val items: IntArray,
    val tool: CombinationTool = CombinationTool.NONE,
    val resultItem: Int,
    val skill: Int = Skills.CRAFTING,
    val levelRequired: Int = 1,
    val experience: Double,
    val message: String? = null,
) {
    BRONZE_CBOW_U(
        items = intArrayOf(Items.BRONZE_LIMBS, Items.WOODEN_STOCK),
        resultItem = Items.BRONZE_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 9,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    BRONZE_CROSSBOW(
        items = intArrayOf(Items.BRONZE_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.BRONZE_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 9,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    BLURITE_CBOW_U(
        items = intArrayOf(Items.BLURITE_LIMBS, Items.OAK_STOCK),
        resultItem = Items.BLURITE_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 24,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    BLURITE_CROSSBOW(
        items = intArrayOf(Items.BLURITE_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.BLURITE_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 24,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    IRON_CBOW_U(
        items = intArrayOf(Items.IRON_LIMBS, Items.WILLOW_STOCK),
        resultItem = Items.IRON_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 39,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    IRON_CROSSBOW(
        items = intArrayOf(Items.IRON_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.IRON_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 39,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    STEEL_CBOW_U(
        items = intArrayOf(Items.STEEL_LIMBS, Items.TEAK_STOCK),
        resultItem = Items.STEEL_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 46,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    STEEL_CROSSBOW(
        items = intArrayOf(Items.STEEL_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.STEEL_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 46,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    MITHRIL_CBOW_U(
        items = intArrayOf(Items.MITHRIL_LIMBS, Items.MAPLE_STOCK),
        resultItem = Items.MITHRIL_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 54,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    MITHRIL_CROSSBOW(
        items = intArrayOf(Items.MITHRIL_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.MITH_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 54,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    ADAMANT_CBOW_U(
        items = intArrayOf(Items.ADAMANTITE_LIMBS, Items.MAHOGANY_STOCK),
        resultItem = Items.ADAMANT_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 61,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    ADAMANT_CROSSBOW(
        items = intArrayOf(Items.ADAMANT_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.ADAMANT_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 61,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    RUNE_CBOW_U(
        items = intArrayOf(Items.RUNITE_LIMBS, Items.YEW_STOCK),
        resultItem = Items.RUNITE_CBOW_U,
        skill = Skills.FLETCHING,
        levelRequired = 69,
        experience = 0.0,
        message = "You attach the stock to the limbs and create an unstrung crossbow.",
    ),
    RUNE_CROSSBOW(
        items = intArrayOf(Items.RUNITE_CBOW_U, Items.CROSSBOW_STRING),
        resultItem = Items.RUNE_CROSSBOW,
        skill = Skills.FLETCHING,
        levelRequired = 69,
        experience = 0.0,
        message = "You add a string to the crossbow.",
    ),
    STRANGE_SKULL(
        items = intArrayOf(Items.LEFT_SKULL_HALF, Items.RIGHT_SKULL_HALF),
        resultItem = Items.STRANGE_SKULL,
        levelRequired = 1,
        experience = 0.0,
    ),
    RUNED_SCEPTRE(
        items = intArrayOf(Items.BOTTOM_OF_SCEPTRE, Items.TOP_OF_SCEPTRE),
        resultItem = Items.RUNED_SCEPTRE,
        levelRequired = 1,
        experience = 0.0,
    ),
    SKULL_SCEPTRE(
        items = intArrayOf(Items.STRANGE_SKULL, Items.RUNED_SCEPTRE),
        resultItem = Items.SKULL_SCEPTRE,
        levelRequired = 1,
        experience = 0.0,
    ),
    BULLYEYE_LANTERN(
        items = intArrayOf(Items.BULLSEYE_LANTERN, Items.LANTERN_LENS),
        resultItem = Items.BULLSEYE_LANTERN_4546,
        levelRequired = 49,
        experience = 0.0,
    ),
    BULLYEYE_EMERALD_LANTERN(
        items = intArrayOf(Items.BULLSEYE_LANTERN_4546, Items.EMERALD_LENS),
        resultItem = Items.EMERALD_LANTERN,
        levelRequired = 49,
        experience = 0.0,
    ),

    OIL_LANTERN(
        items = intArrayOf(Items.OIL_LAMP, Items.OIL_LANTERN_FRAME),
        resultItem = Items.OIL_LANTERN_4537,
        levelRequired = 50,
        experience = 50.0,
    ),

    STUDDED_BODY(
        items = intArrayOf(Items.LEATHER_BODY, Items.STEEL_STUDS),
        resultItem = Items.STUDDED_BODY,
        levelRequired = 41,
        experience = 40.0,
    ),
    STUDDED_CHAPS(
        items = intArrayOf(Items.LEATHER_CHAPS, Items.STEEL_STUDS),
        resultItem = Items.STUDDED_CHAPS,
        levelRequired = 44,
        experience = 42.0,
    ),
    LEATHER_SPIKY_VAMBRACES(
        items = intArrayOf(Items.LEATHER_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0,
    ),
    GREEN_SPIKY_VAMBRACES(
        items = intArrayOf(Items.GREEN_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.GREEN_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0,
    ),
    BLUE_SPIKY_VAMBRACES(
        items = intArrayOf(Items.BLUE_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.BLUE_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0,
    ),
    RED_SPIKY_VAMBRACES(
        items = intArrayOf(Items.RED_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.RED_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0,
    ),
    BLACK_SPIKY_VAMBRACES(
        items = intArrayOf(Items.BLACK_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.BLACK_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0,
    ),
    CRAB_HELMET(
        items = intArrayOf(Items.FRESH_CRAB_SHELL),
        tool = CombinationTool.CHISEL,
        resultItem = Items.CRAB_HELMET,
        levelRequired = 15,
        experience = 32.5,
    ),
    CRAB_CLAW(
        items = intArrayOf(Items.FRESH_CRAB_CLAW),
        tool = CombinationTool.CHISEL,
        resultItem = Items.CRAB_CLAW,
        levelRequired = 15,
        experience = 32.5,
    ),
    SEAWEED_NET(
        items = intArrayOf(Items.UNFINISHED_NET, Items.BRONZE_WIRE),
        resultItem = Items.EMPTY_SEAWEED_NET,
        levelRequired = 52,
        experience = 83.0,
    ),
    ARMADYL_BATTLESTAFF(
        items = intArrayOf(Items.BATTLESTAFF, Items.ORB_OF_ARMADYL),
        resultItem = Items.ARMADYL_BATTLESTAFF,
        levelRequired = 77,
        experience = 150.0,
    ),
    GOLD_AMULET(
        items = intArrayOf(Items.GOLD_AMULET, Items.BALL_OF_WOOL),
        resultItem = Items.GOLD_AMULET_1692,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    SAPPHIRE_AMULET(
        items = intArrayOf(Items.SAPPHIRE_AMULET, Items.BALL_OF_WOOL),
        resultItem = Items.SAPPHIRE_AMULET_1694,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    EMERALD_AMULET(
        items = intArrayOf(Items.EMERALD_AMULET, Items.BALL_OF_WOOL),
        resultItem = Items.EMERALD_AMULET_1696,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    RUBY_AMULET(
        items = intArrayOf(Items.RUBY_AMULET, Items.BALL_OF_WOOL),
        resultItem = Items.RUBY_AMULET_1698,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    DIAMOND_AMULET(
        items = intArrayOf(Items.DIAMOND_AMULET, Items.BALL_OF_WOOL),
        resultItem = Items.DIAMOND_AMULET_1700,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    DRAGONSTONE_AMULET(
        items = intArrayOf(Items.DRAGONSTONE_AMMY, Items.BALL_OF_WOOL),
        resultItem = Items.DRAGONSTONE_AMMY_1702,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    ONYX_AMULET(
        items = intArrayOf(Items.ONYX_AMULET_6579, Items.BALL_OF_WOOL),
        resultItem = Items.ONYX_AMULET_6581,
        experience = 4.0,
        message = "You put some string on your amulet.",
    ),
    UNBLESSED_SYMBOL(
        items = intArrayOf(Items.UNSTRUNG_SYMBOL, Items.BALL_OF_WOOL),
        resultItem = Items.UNBLESSED_SYMBOL,
        experience = 4.0,
        message = "You put some string on your symbol.",
    ),
    UNPOWERED_SYMBOL(
        items = intArrayOf(Items.UNSTRUNG_EMBLEM, Items.BALL_OF_WOOL),
        resultItem = Items.UNPOWERED_SYMBOL,
        experience = 4.0,
        message = "You put some string on your emblem.",
    ),
    ;

    companion object {
        val values = enumValues<CombinationData>()
        val combinationDefinitions = values.associateBy { it.items[0] }
    }
}
