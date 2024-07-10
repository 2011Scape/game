package gg.rsmod.plugins.content.skills.urns

import gg.rsmod.game.model.Animation
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.runecrafting.Rune
import java.util.HashMap

/**
 * @author Harley <github.com/HarleyGilpin>
 * Created: 2/20/2024 8:08 AM
 *
 * Description:
 * This file defines the 'Urn' enum class which represents various types of urns used in the game.
 * Each urn is characterized by its unique ID, animations for ready and teleport actions, associated skill ID,
 * required level, and experience points needed for filling. The urns are categorized based on different skills
 * such as Smithing, Woodcutting, Fishing, Cooking, and Mining, with varying levels and experience points.
 *
 * The enum class provides methods to get ready and teleport animations, as well as IDs for different stages
 * of the urn (unfired, nr, r, fill, and full). Additionally, it includes a property to determine the type of rune
 * associated with each urn based on its skill, and a property to calculate teleport experience points.
 *
 * The companion object contains static maps to associate urn IDs with their respective urn types, facilitating
 * easy retrieval of urn details based on ID. This setup is essential for game mechanics where urns play a role
 * in skill enhancement and gameplay dynamics.
 */

enum class Urn(
    private val unfId: Int,
    private val readyAnim: Animation,
    private val teleAnim: Animation,
    private val skillId: Int,
    val level: Int,
    private val fillXp: Double,
) {
    CRACKED_SMELTING(Items.CRACKED_SMELTING_URN_UNF, Animation(6384), Animation(4580), Skills.SMITHING, 4, 200.0),
    FRAGILE_SMELTING(Items.FRAGILE_SMELTING_URN_UNF, Animation(6385), Animation(6380), Skills.SMITHING, 17, 312.5),
    SMELTING(Items.SMELTING_URN_UNF, Animation(6386), Animation(6381), Skills.SMITHING, 35, 750.0),
    STRONG_SMELTING(Items.STRONG_SMELTING_URN_UNF, Animation(6387), Animation(6382), Skills.SMITHING, 49, 1250.0),
    CRACKED_WOODCUTTING(
        Items.CRACKED_WOODCUTTING_URN_UNF,
        Animation(10279),
        Animation(8713),
        Skills.WOODCUTTING,
        4,
        800.0,
    ),
    FRAGILE_WOODCUTTING(
        Items.FRAGILE_WOODCUTTING_URN_UNF,
        Animation(10280),
        Animation(8727),
        Skills.WOODCUTTING,
        15,
        2125.0,
    ),
    WOODCUTTING(Items.WOODCUTTING_URN_UNF, Animation(10281), Animation(8729), Skills.WOODCUTTING, 44, 4125.0),
    STRONG_WOODCUTTING(
        Items.STRONG_WOODCUTTING_URN_UNF,
        Animation(10828),
        Animation(8730),
        Skills.WOODCUTTING,
        61,
        8312.5,
    ),
    CRACKED_FISHING(Items.CRACKED_FISHING_URN_UNF, Animation(6474), Animation(6394), Skills.FISHING, 2, 750.0),
    FRAGILE_FISHING(Items.FRAGILE_FISHING_URN_UNF, Animation(6475), Animation(6463), Skills.FISHING, 15, 1750.0),
    FISHING(Items.FISHING_URN_UNF, Animation(6769), Animation(6471), Skills.FISHING, 41, 2500.0),
    STRONG_FISHING(Items.STRONG_FISHING_URN_UNF, Animation(6770), Animation(6472), Skills.FISHING, 53, 3000.0),
    DECORATED_FISHING(Items.DECORATED_FISHING_URN_UNF, Animation(6789), Animation(6473), Skills.FISHING, 76, 9500.0),
    CRACKED_COOKING(Items.CRACKED_COOKING_URN_UNF, Animation(8649), Animation(6794), Skills.COOKING, 2, 2000.0),
    FRAGILE_COOKING(Items.FRAGILE_COOKING_URN_UNF, Animation(8651), Animation(6795), Skills.COOKING, 12, 2750.0),
    COOKING(Items.COOKING_URN_UNF, Animation(8652), Animation(7126), Skills.COOKING, 36, 4750.0),
    STRONG_COOKING(Items.STRONG_COOKING_URN_UNF, Animation(8654), Animation(7133), Skills.COOKING, 51, 5250.0),
    DECORATED_COOKING(Items.DECORATED_COOKING_URN_UNF, Animation(8691), Animation(8629), Skills.COOKING, 81, 7737.5),
    CRACKED_MINING(Items.CRACKED_MINING_URN_UNF, Animation(11420), Animation(10829), Skills.MINING, 1, 437.5),
    FRAGILE_MINING(Items.FRAGILE_MINING_URN_UNF, Animation(11421), Animation(10830), Skills.MINING, 17, 1000.0),
    MINING(Items.MINING_URN_UNF, Animation(11425), Animation(10831), Skills.MINING, 32, 1625.0),
    STRONG_MINING(Items.STRONG_MINING_URN_UNF, Animation(11447), Animation(10947), Skills.MINING, 48, 2000.0),
    DECORATED_MINING(Items.DECORATED_MINING_URN_UNF, Animation(11448), Animation(11419), Skills.MINING, 59, 3125.0),
    IMPIOUS(Items.IMPIOUS_URN_UNF, Animation(4567), Animation(4292), Skills.PRAYER, 2, 100.0),
    ACCURSED(Items.ACCURSED_URN_UNF, Animation(4569), Animation(4541), Skills.PRAYER, 26, 312.5),
    INFERNAL(Items.INFERNAL_URN_UNF, Animation(4578), Animation(4542), Skills.PRAYER, 62, 1562.5),
    ;

    fun getReadyAnim(): Animation = readyAnim

    fun getTeleAnim(): Animation = teleAnim

    fun unfId(): Int = unfId

    fun nrId(): Int = unfId + 1

    fun rId(): Int = unfId + 3

    fun fillId(): Int = unfId + 4

    fun fullId(): Int = unfId + 5

    val rune: Rune
        get() =
            when (skillId) {
                Skills.COOKING, Skills.SMITHING -> Rune.FIRE
                Skills.FISHING -> Rune.WATER
                Skills.MINING, Skills.WOODCUTTING -> Rune.EARTH
                else -> Rune.AIR
            }

    val teleXp: Double
        get() =
            when (this) {
                IMPIOUS, ACCURSED, INFERNAL -> fillXp * 1.20
                else -> fillXp * 0.20
            }

    companion object {
        val UNF_IDS: MutableMap<Int, Urn> = HashMap()
        val NR_IDS: MutableMap<Int, Urn> = HashMap()
        val FILL_IDS: MutableMap<Int, Urn> = HashMap()
        val FULL_IDS: MutableMap<Int, Urn> = HashMap()

        init {
            for (urn in values()) {
                UNF_IDS[urn.unfId()] = urn
                NR_IDS[urn.nrId()] = urn
                FILL_IDS[urn.fillId()] = urn
                FULL_IDS[urn.fullId()] = urn
            }
        }

        fun forNRId(id: Int): Urn? = NR_IDS[id]

        fun forFillId(id: Int): Urn? = FILL_IDS[id]

        fun forFullId(id: Int): Urn? = FULL_IDS[id]
    }
}
