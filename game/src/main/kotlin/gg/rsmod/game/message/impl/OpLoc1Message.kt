package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class OpLoc1Message(val movementType: Int, val x: Int, val id: Int, val z: Int) : Message