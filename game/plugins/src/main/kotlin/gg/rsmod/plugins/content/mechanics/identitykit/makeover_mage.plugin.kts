package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.game.sync.block.UpdateBlockType

/**
 * @author Alycia <https://github.com/alycii>
 */

val SKIN_VARBIT = 6099
val GENDER_VARBIT = 6098

val BEARD_ENUM = 703
val SKIN_COLOR_ENUM = 748

/**
 * Handles skin color selection
 */
for(button in 20..31) {
    on_button(interfaceId = 900, button) {
        player.setVarbit(SKIN_VARBIT, world.definitions.get(EnumDef::class.java, SKIN_COLOR_ENUM).getInt(button - 20))
    }
}

/**
 * Handles gender selection
 */

// male
on_button(interfaceId = 900, component = 16) {
    player.setVarbit(GENDER_VARBIT, 0)
}

// female
on_button(interfaceId = 900, component = 17) {
    player.setVarbit(GENDER_VARBIT, 1)
}

/**
 * Handles confirmation
 */
on_button(interfaceId = 900, component = 33) {
    player.closeInterface(interfaceId = 900)
    player.appearance.colors[5] = player.getVarbit(SKIN_VARBIT)
    if(player.appearance.gender.id != player.getVarbit(GENDER_VARBIT)) {
        swapSex(player, player.getVarbit(GENDER_VARBIT) == 0)
    }
    player.addBlock(UpdateBlockType.APPEARANCE)
}

on_interface_open(interfaceId = 900) {
    player.setComponentText(interfaceId = 900, component = 33, "CONFIRM")
    player.setVarbit(SKIN_VARBIT, world.definitions.get(EnumDef::class.java, 748).getKeyForValue(player.appearance.colors[5]))
    player.setVarbit(GENDER_VARBIT, if(player.appearance.gender.isMale()) 0 else 1)
}

/**
 * Swaps the gender and look of the player,
 * ensuring that the clothing stays as close as possible
 * and randomizes hair.
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun swapSex(player: Player, male: Boolean) {
    val enumValue = world.definitions.get(EnumDef::class.java, if(male) EnumDef.MALE_HAIR_STRUCT else EnumDef.FEMALE_HAIR_STRUCT).getRandomInt()
    val structValue = world.definitions.get(StructDef::class.java, enumValue).getInt(788)
    player.appearance.looks[0] = structValue
    player.appearance.looks[1] = if(male) world.definitions.get(EnumDef::class.java, BEARD_ENUM).getRandomInt() else 1000
    for(part in 0..3) {
        swapLook(player, male, part + 2)
    }
    player.appearance.gender = if(male) Gender.MALE else Gender.FEMALE
    player.addBlock(UpdateBlockType.APPEARANCE)
}

/**
 * Helper function for looking up
 * clothing parts that match the gender swap
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun swapLook(player: Player, male: Boolean, bodyPart: Int) {
    val old = world.definitions.get(EnumDef::class.java, getBodyStyleEnum(if(male) Gender.FEMALE else Gender.MALE, bodyPart - 2))
    val new = world.definitions.get(EnumDef::class.java, getBodyStyleEnum(if(male) Gender.MALE else Gender.FEMALE, bodyPart - 2))
    val key = old.getKeyForValue(player.appearance.looks[bodyPart])
    player.appearance.looks[bodyPart] = new.getInt(key)
}