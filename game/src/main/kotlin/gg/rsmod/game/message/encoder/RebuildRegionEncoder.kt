package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.RebuildRegionMessage
import gg.rsmod.net.packet.GamePacketBuilder
import io.netty.buffer.Unpooled

/**
 * @author Tom <rspsmods@gmail.com>
 */
class RebuildRegionEncoder : MessageEncoder<RebuildRegionMessage>() {
    override fun extract(
        message: RebuildRegionMessage,
        key: String,
    ): Number =
        when (key) {
            "zoneX" -> message.x
            "zoneZ" -> message.z
            "force_load" -> message.forceLoad
            "build_area" -> message.buildArea
            "area_mode" -> message.areaMode
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: RebuildRegionMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "data" -> {
                val xteaBuffer = Unpooled.buffer(message.coordinates.size * (Int.SIZE_BYTES * 4))

                val bitBuf = GamePacketBuilder()
                bitBuf.switchToBitAccess()

                message.coordinates.forEach { copiedCoords ->
                    bitBuf.putBit(copiedCoords != -1)
                    if (copiedCoords != -1) {
                        bitBuf.putBits(26, copiedCoords)
                    }
                }
                bitBuf.switchToByteAccess()

                val regions = hashSetOf<Int>()
                var xteaCount = 0
                message.coordinates.forEach { copiedCoords ->
                    if (copiedCoords == -1) {
                        return@forEach
                    }
                    val rx = copiedCoords shr 14 and 0x3FF
                    val rz = copiedCoords shr 3 and 0x7FF
                    val region = rz / 8 + (rx / 8 shl 8)
                    if (regions.add(region)) {
                        val keys = message.xteaKeyService!!.get(region)
                        for (xteaKey in keys) {
                            xteaBuffer.writeInt(xteaKey) // Client always reads as int
                        }
                        xteaCount++
                    }
                }

                val buf = Unpooled.buffer(bitBuf.readableBytes + xteaBuffer.readableBytes())

            /*
             * Write the bit data.
             */
                buf.writeBytes(bitBuf.byteBuf)
            /*
             * Write the XTEA keys.
             */
                buf.writeBytes(xteaBuffer)

                val data = ByteArray(buf.readableBytes())
                buf.readBytes(data)
                data
            }
            else -> throw Exception("Unhandled value key.")
        }
}
