package gg.rsmod.plugins.content.skills.smithing.data

import gg.rsmod.game.model.entity.Player

enum class SmithingType(
    val barRequirement: Int,
    val componentId: Int,
    val buttonIds: Array<Int>,
    val producedAmount: Int
) {

    /**
     * Dagger
     */
    TYPE_DAGGER(
        barRequirement = 1,
        componentId = 19,
        buttonIds = arrayOf(24, 23, 22, 21),
        producedAmount = 1
    ),

    /**
     * Hatchet
     */
    TYPE_HATCHET(
        barRequirement = 1,
        componentId = 27,
        buttonIds = arrayOf(32, 31, 30, 29),
        producedAmount = 1
    ),

    /**
     * Mace
     */
    TYPE_MACE(
        barRequirement = 1,
        componentId = 35,
        buttonIds = arrayOf(40, 39, 38, 37),
        producedAmount = 1
    ),

    /**
     * Med helm
     */
    TYPE_MEDIUM_HELM(
        barRequirement = 1,
        componentId = 43,
        buttonIds = arrayOf(48, 47, 46, 45),
        producedAmount = 1
    ),

    /**
     * Crossbow bolt
     */
    TYPE_CROSSBOW_BOLTS(
        barRequirement = 1,
        componentId = 51,
        buttonIds = arrayOf(56, 55, 54, 53),
        producedAmount = 10
    ),

    /**
     * Sword
     */
    TYPE_SWORD(
        barRequirement = 1,
        componentId = 59,
        buttonIds = arrayOf(64, 63, 62, 61),
        producedAmount = 1
    ),

    /**
     * Dart tips
     */
    TYPE_DART_TIPS(
        barRequirement = 1,
        componentId = 67,
        buttonIds = arrayOf(72, 71, 70, 69),
        producedAmount = 10
    ),

    /**
     * Nails
     */
    TYPE_NAILS(
        barRequirement = 1,
        componentId = 75,
        buttonIds = arrayOf(80, 79, 78, 77),
        producedAmount = 15
    ),

    /**
     * Bullseye
     */
    TYPE_BULLSEYE(
        barRequirement = 1,
        componentId = 163,
        buttonIds = arrayOf(168, 167, 166, 165),
        producedAmount = 1
    ),

    /**
     *
     */
    TYPE_WIRE(
        barRequirement = 1,
        componentId = 83,
        buttonIds = arrayOf(96, 95, 94, 93),
        producedAmount = 1
    ),

    /**
     * Arrow Tips
     */
    TYPE_ARROW_TIPS(
        barRequirement = 1,
        componentId = 107,
        buttonIds = arrayOf(112, 111, 110, 109),
        producedAmount = 15
    ),

    /**
     * Scimitar
     */
    TYPE_SCIMITAR(
        barRequirement = 2,
        componentId = 115,
        buttonIds = arrayOf(120, 119, 118, 117),
        producedAmount = 1
    ),

    /**
     * Crossbow Limbs
     */
    TYPE_CROSSBOW_LIMBS(
        barRequirement = 1,
        componentId = 123,
        buttonIds = arrayOf(128, 127, 126, 125),
        producedAmount = 1
    ),

    /**
     * LongSword
     */
    TYPE_LONGSWORD(
        barRequirement = 2,
        componentId = 131,
        buttonIds = arrayOf(136, 135, 134, 133),
        producedAmount = 1
    ),

    /**
     * Throwing Knife
     */
    TYPE_THROWING_KNIFE(
        barRequirement = 1,
        componentId = 139,
        buttonIds = arrayOf(144, 143, 142, 141),
        producedAmount = 5
    ),

    /**
     * Iron Spit
     */
    TYPE_IRON_SPIT(
        barRequirement = 1,
        componentId = 91,
        buttonIds = arrayOf(96, 95, 94, 93),
        producedAmount = 1
    ),


    /**
     * Lantern
     */
    TYPE_OIL_LANTERN(
        barRequirement = 1,
        componentId = 163,
        buttonIds = arrayOf(168, 167, 166, 165),
        producedAmount = 1
    ),


    /**
     * Full helm
     */
    TYPE_FULL_HELM(
        barRequirement = 2,
        componentId = 147,
        buttonIds = arrayOf(152, 151, 150, 149),
        producedAmount = 1
    ),

    /**
     * Square Shield
     */
    TYPE_SQUARE_SHIELD(
        barRequirement = 2,
        componentId = 155,
        buttonIds = arrayOf(160, 159, 158, 157),
        producedAmount = 1
    ),

    /**
     * Grapple Tips
     */
    TYPE_GRAPPLE_TIP(
        barRequirement = 1,
        componentId = 171,
        buttonIds = arrayOf(175, 176, 174, 173, 172),
        producedAmount = 1
    ),

    /**
     * The studs type.
     */
    TYPE_STUDS(
        barRequirement = 1,
        componentId = 99,
        buttonIds = arrayOf(96, 95, 94, 93),
        producedAmount = 1
    ),

    /**
     * Warhammer
     */
    TYPE_WARHAMMER(
        barRequirement = 3,
        componentId = 179,
        buttonIds = arrayOf(184, 183, 182, 181),
        producedAmount = 1
    ),

    /**
     * Battle axe
     */
    TYPE_BATTLE_AXE(
        barRequirement = 3,
        componentId = 187,
        buttonIds = arrayOf(192, 191, 190, 189),
        producedAmount = 1
    ),

    /**
     * Chainbody
     */
    TYPE_CHAINBODY(
        barRequirement = 3,
        componentId = 195,
        buttonIds = arrayOf(200, 199, 198, 197),
        producedAmount = 1
    ),

    /**
     * Kite shield
     */
    TYPE_KITE_SHIELD(
        barRequirement = 3,
        componentId = 203,
        buttonIds = arrayOf(208, 207, 206, 205),
        producedAmount = 1
    ),

    /**
     * Claws
     */
    TYPE_CLAWS(
        barRequirement = 2,
        componentId = 211,
        buttonIds = arrayOf(216, 215, 214, 213),
        producedAmount = 1
    ),

    /**
     * 2H
     */
    TYPE_TWO_HAND_SWORD(
        barRequirement = 3,
        componentId = 219,
        buttonIds = arrayOf(224, 223, 222, 221),
        producedAmount = 1
    ),

    /**
     * Plate Skirt
     */
    TYPE_PLATE_SKIRT(
        barRequirement = 3,
        componentId = 227,
        buttonIds = arrayOf(232, 231, 230, 229),
        producedAmount = 1
    ),

    /**
     * Platelegs
     */
    TYPE_PLATELEGS(
        barRequirement = 3,
        componentId = 235,
        buttonIds = arrayOf(240, 239, 238, 237),
        producedAmount = 1
    ),

    /**
     * Platebody
     */
    TYPE_PLATEBODY(
        barRequirement = 5,
        componentId = 243,
        buttonIds = arrayOf(248, 247, 246, 245),
        producedAmount = 1
    ),

    /**
     * Pickaxe
     */
    TYPE_PICKAXE(
        barRequirement = 2,
        componentId = 268,
        buttonIds = arrayOf(273, 272, 271, 270),
        producedAmount = 1
    );

    companion object {

       fun retrieveAllButtons() : Array<Int> {
           val buttons = ArrayList<Int>()
           for(type in SmithingType.values()) {
                buttons.addAll(type.buttonIds)
           }
           return buttons.distinct().toTypedArray()
       }

        fun forButton(player: Player, bar: BarProducts?, button: Int, item: Int): Int {
            val buttonIds = bar?.smithingType?.buttonIds ?: return -1
            return when (buttonIds.indexOf(button)) {
                0 -> 1
                1 -> 5
                2 -> -1
                3 -> player.inventory.getItemCount(item)
                else -> -1
            }
        }
    }

}