package gg.rsmod.game.fs.def

import gg.rsmod.game.fs.Definition
import gg.rsmod.util.io.BufferUtils.readString
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

class StructDef(override val id: Int) : Definition(id) {

    val values = Int2ObjectOpenHashMap<Any>()

    override fun decode(buf: ByteBuf, opcode: Int) {
        when(opcode) {
            249 -> {
                val length = buf.readUnsignedByte().toInt()
                for(i in 0 until length) {
                    val stringInstance = buf.readBoolean()
                    val key = buf.readMedium()
                    if(stringInstance) {
                        values[key] = buf.readString()
                    } else {
                        values[key] = buf.readInt()
                    }

                }
            }
        }
    }

    fun getInt(key: Int): Int = values[key] as? Int ?: 0

    fun getString(key: Int): String = values[key] as? String ?: ""

    companion object {
        const val HAIR_WITHOUT_FACEMASK = 790
        const val HAIR_WITH_FACEMASK = 791
    }
}