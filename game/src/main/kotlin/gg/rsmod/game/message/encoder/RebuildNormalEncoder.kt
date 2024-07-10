package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.RebuildNormalMessage
import gg.rsmod.game.model.region.Chunk
import gg.rsmod.game.service.xtea.XteaKeyService
import io.netty.buffer.Unpooled

/**
 * @author Tom <rspsmods@gmail.com>
 */
class RebuildNormalEncoder : MessageEncoder<RebuildNormalMessage>() {
    override fun extract(
        message: RebuildNormalMessage,
        key: String,
    ): Number =
        when (key) {
            "map_size" -> message.mapSize
            "force_load" -> message.forceLoad
            "x" -> message.x
            "z" -> message.z
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: RebuildNormalMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "xteas" -> {
                val chunkX = message.x
                val chunkZ = message.z

                val lx = (chunkX - (Chunk.MAX_VIEWPORT shr 4)) shr 3
                val rx = (chunkX + (Chunk.MAX_VIEWPORT shr 4)) shr 3
                val lz = (chunkZ - (Chunk.MAX_VIEWPORT shr 4)) shr 3
                val rz = (chunkZ + (Chunk.MAX_VIEWPORT shr 4)) shr 3

                val buf = Unpooled.buffer(Short.SIZE_BYTES + (Int.SIZE_BYTES * 10))

                for (x in lx..rx) {
                    for (z in lz..rz) {
                        val region = z + (x shl 8)
                        val keys = message.xteaKeyService?.get(region) ?: XteaKeyService.EMPTY_KEYS
                        for (xteaKey in keys) {
                            buf.writeInt(xteaKey) // Client always reads as int
                        }
                    }
                }

                val xteas = ByteArray(buf.readableBytes())
                buf.readBytes(xteas)
                xteas
            }
            else -> throw Exception("Unhandled value key.")
        }
}
