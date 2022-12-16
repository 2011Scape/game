package gg.rsmod.game.model

import gg.rsmod.game.fs.def.EnumDef
import gg.rsmod.game.fs.def.EnumDef.Companion.FEMALE_HAIR_SLOT
import gg.rsmod.game.fs.def.EnumDef.Companion.FEMALE_HAIR_STRUCT
import gg.rsmod.game.fs.def.EnumDef.Companion.MALE_HAIR_SLOT
import gg.rsmod.game.fs.def.EnumDef.Companion.MALE_HAIR_STRUCT
import gg.rsmod.game.fs.def.StructDef
import gg.rsmod.game.fs.def.StructDef.Companion.HAIR_WITHOUT_FACEMASK
import gg.rsmod.game.fs.def.StructDef.Companion.HAIR_WITH_FACEMASK

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class Appearance(val looks: IntArray, val colors: IntArray, val gender: Gender) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Appearance

        if (!looks.contentEquals(other.looks)) return false
        if (!colors.contentEquals(other.colors)) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = looks.contentHashCode()
        result = 31 * result + colors.contentHashCode()
        result = 31 * result + gender.hashCode()
        return result
    }

    /**
     * Calculates the struct for the appearance to
     * know whether the player is wearing a hat or not.
     *
     * If so, return with the hairstyle for the appearance block
     */
    internal fun lookupHairStyle(world: World, baseStyle: Int, faceMask: Boolean = false): Int {
        val lookup: EnumDef = when(gender) {
            Gender.MALE -> world.definitions.get(EnumDef::class.java, MALE_HAIR_SLOT)
            Gender.FEMALE -> world.definitions.get(EnumDef::class.java, FEMALE_HAIR_SLOT)
        }

        val slot: Int = lookup.getInt(baseStyle)
        val structLookup: EnumDef = when(gender) {
            Gender.MALE -> world.definitions.get(EnumDef::class.java, MALE_HAIR_STRUCT)
            Gender.FEMALE -> world.definitions.get(EnumDef::class.java, FEMALE_HAIR_STRUCT)
        }

        val structID: Int = structLookup.getInt(slot)
        return when(faceMask) {
            true -> world.definitions.get(StructDef::class.java, structID).getInt(HAIR_WITH_FACEMASK)
            false -> world.definitions.get(StructDef::class.java, structID).getInt(HAIR_WITHOUT_FACEMASK)
        }
    }

    companion object {

        private val DEFAULT_LOOKS_MALE = intArrayOf(310, 14, 473, 26, 34, 626, 42)

        private val DEFAULT_COLORS = intArrayOf(0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0)

        private val DEFAULT_LOOKS_FEMALE = intArrayOf(48, 1000, 56, 61, 68, 70, 79)

        val DEFAULT = Appearance(DEFAULT_LOOKS_MALE, DEFAULT_COLORS, Gender.MALE)

        val DEFAULT_FEMALE = Appearance(DEFAULT_LOOKS_FEMALE, DEFAULT_COLORS, Gender.FEMALE)
    }
}