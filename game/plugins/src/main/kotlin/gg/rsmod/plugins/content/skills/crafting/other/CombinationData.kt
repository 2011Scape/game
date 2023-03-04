package gg.rsmod.plugins.content.skills.crafting.other

import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items

/**
 * Handles all the data relation to combining items and giving crafting experience.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
enum class CombinationData(
    val items: IntArray,
    val tool: CombinationTool = CombinationTool.NONE,
    val resultItem: Int,
    val skill: Int = Skills.CRAFTING,
    val levelRequired: Int = 1,
    val experience: Double
) {
    STUDDED_BODY(
        items = intArrayOf(Items.LEATHER_BODY, Items.STEEL_STUDS),
        resultItem = Items.STUDDED_BODY,
        levelRequired = 41,
        experience = 40.0
    ),
    STUDDED_CHAPS(
        items = intArrayOf(Items.LEATHER_CHAPS, Items.STEEL_STUDS),
        resultItem = Items.STUDDED_CHAPS,
        levelRequired = 44,
        experience = 42.0
    ),
    LEATHER_SPIKY_VAMBRACES(
        items = intArrayOf(Items.LEATHER_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0
    ),
    GREEN_SPIKY_VAMBRACES(
        items = intArrayOf(Items.GREEN_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.GREEN_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0
    ),
    BLUE_SPIKY_VAMBRACES(
        items = intArrayOf(Items.BLUE_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.BLUE_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0
    ),
    RED_SPIKY_VAMBRACES(
        items = intArrayOf(Items.RED_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.RED_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0
    ),
    BLACK_SPIKY_VAMBRACES(
        items = intArrayOf(Items.BLACK_DHIDE_VAMBRACES, Items.KEBBIT_CLAWS),
        resultItem = Items.BLACK_SPIKY_VAMBRACES,
        levelRequired = 32,
        experience = 6.0
    ),
    CRAB_HELMET(
        items = intArrayOf(Items.FRESH_CRAB_SHELL),
        tool = CombinationTool.CHISEL,
        resultItem = Items.CRAB_HELMET,
        levelRequired = 15,
        experience = 32.5
    ),
    CRAB_CLAW(
        items = intArrayOf(Items.FRESH_CRAB_CLAW),
        tool = CombinationTool.CHISEL,
        resultItem = Items.CRAB_CLAW,
        levelRequired = 15,
        experience = 32.5
    ),
    ARMADYL_BATTLESTAFF(
        items = intArrayOf(Items.BATTLESTAFF, Items.ORB_OF_ARMADYL),
        resultItem = Items.ARMADYL_BATTLESTAFF,
        levelRequired = 77,
        experience = 150.0
    );

    companion object {
        val values = enumValues<CombinationData>()
        val combinationDefinitions = values.associateBy { it.items[0] }
    }
}

enum class CombinationTool(val item: Int = -1) {
    NONE,
    CHISEL(Items.CHISEL),
    NEEDLE(Items.NEEDLE),
    HAMMER(Items.HAMMER)
}