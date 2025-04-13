package gg.rsmod.game.fs.def

import gg.rsmod.game.fs.Definition
import io.netty.buffer.ByteBuf

class SpotAnimDef(
    id: Int,
) :Definition(id) {

    private var rotation = 0
    private lateinit var textureToReplace: ShortArray
    private lateinit var textureToFind: ShortArray
    private var scaleXZ = 128
    private var scaleY = 128
    private var animationId = -1
    private lateinit var recolorToFind: ShortArray
    private lateinit var recolorToReplace: ShortArray
    private var modelId = 0
    private var ambient = 0
    private var contrast = 0
    private var hillType = 0
    private var hillValue = -1
    private var loop = false

    override fun decode(buf: ByteBuf, opcode: Int) {
        when (opcode) {
            1 -> modelId = buf.readShort().toInt()
            2 -> animationId = buf.readShort().toInt()
            4 -> scaleXZ = buf.readShort().toInt()
            5 -> scaleY = buf.readShort().toInt()
            6 -> rotation = buf.readShort().toInt()
            7 -> ambient = buf.readByte().toInt()
            8 -> contrast = buf.readByte().toInt()
            9 -> {
                hillType = 3
                hillValue = 8224
            }
            10 -> loop = true
            11 -> hillType = 1
            12 -> hillType = 4
            13 -> hillType = 5
            14 -> {
                hillType = 2
                hillValue = buf.readByte().toInt() * 256
            }
            15 -> {
                hillType = 3
                hillValue = buf.readShort().toInt()
            }
            16 -> {
                hillType = 3
                hillValue = buf.readInt()
            }
            40 -> {
                var count = buf.readByte().toInt()
                recolorToFind = ShortArray(count)
                recolorToReplace = ShortArray(count)
                for (i in 0 until count) {
                    recolorToFind[i] = buf.readShort()
                    recolorToReplace[i] = buf.readShort()
                }
            }
            41 -> {
                var count = buf.readByte().toInt()
                textureToFind = ShortArray(count)
                textureToReplace = ShortArray(count)
                for (i in 0 until count) {
                    textureToFind[i] = buf.readShort()
                    textureToReplace[i] = buf.readShort()
                }
            }
        }
    }
}
