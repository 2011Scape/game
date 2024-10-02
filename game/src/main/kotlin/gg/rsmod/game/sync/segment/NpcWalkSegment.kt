package gg.rsmod.game.sync.segment

import gg.rsmod.game.sync.SynchronizationSegment
import gg.rsmod.net.packet.GamePacketBuilder

/**
 * @author Tom <rspsmods@gmail.com>
 */
class NpcWalkSegment(private val walkDirection: Int, private val runDirection: Int,
                     private val decodeUpdateBlocks: Boolean) : SynchronizationSegment {

    override fun encode(buf: GamePacketBuilder) {
        // TODO: add support for running
        buf.putBits(2, 1)
        buf.putBits(3, walkDirection)
        buf.putBits(1, if (decodeUpdateBlocks) 1 else 0)
    }
}