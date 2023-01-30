package gg.rsmod.game.fs.def

import gg.rsmod.game.fs.Definition
import gg.rsmod.util.io.BufferUtils.readString
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.bytes.Byte2ByteOpenHashMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

/**
 * @author Tom <rspsmods@gmail.com>
 */
class ItemDef(override val id: Int) : Definition(id) {

    var name = ""
    var stacks = false
    var cost = 0
    var members = false
    var maleWornModel = -1
    var maleWornModel2 = -1
    val groundMenu = Array<String?>(5) { null }
    val inventoryMenu = Array<String?>(5) { null }
    val equipmentMenu = Array<String?>(8) { null }
    /**
     * The item can be traded through the grand exchange.
     */
    var grandExchange = false
    var teamCape = 0
    /**
     * When an item is noted or unnoted (and has a noted variant), this will
     * represent the other item id. For example, item definition [4151] will
     * have a [noteLinkId] of [4152], while item definition [4152] will have
     * a [noteLinkId] of 4151.
     */
    var noteLinkId = 0
    /**
     * When an item is noted, it will set this value.
     */
    var noteTemplateId = 0
    var placeholderLink = 0
    var placeholderTemplate = 0

    var lendId = -1
    var lendTemplateId = -1

    var recolourId = -1
    var recolourTemplateId = -1

    val params = Int2ObjectOpenHashMap<Any>()

    /**
     * Custom metadata.
     */
    var examine: String? = null
    var tradeable = false
    var weight = 0.0
    var attackSpeed = -1
    var equipSlot = -1
    var equipType = 0
    var appearanceId = 0
    var weaponType = -1
    var renderAnimations: IntArray? = null
    var skillReqs: Byte2ByteOpenHashMap? = null
    var bonuses = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    val stackable: Boolean
        get() = stacks || noteTemplateId > 0

    val noted: Boolean
        get() = noteTemplateId > 0

    /**
     * Whether the object is a placeholder.
     */
    val isPlaceholder
        get() = placeholderTemplate > 0 && placeholderLink > 0


    override fun decode(buf: ByteBuf, opcode: Int) {
        when (opcode) {
            1 -> buf.readUnsignedShort()
            2 -> name = buf.readString()
            4 -> buf.readUnsignedShort()
            5 -> buf.readUnsignedShort()
            6 -> buf.readUnsignedShort()
            7 -> buf.readUnsignedShort()
            8 -> buf.readUnsignedShort()
            11 -> stacks = true
            12 -> cost = buf.readInt()
            16 -> members = true
            18 -> buf.readShort()
            23 -> maleWornModel = buf.readUnsignedShort()
            24 -> buf.readUnsignedShort()
            25 -> maleWornModel2 = buf.readUnsignedShort()
            26 -> buf.readUnsignedShort()
            in 30 until 35 -> {
                groundMenu[opcode - 30] = buf.readString()
                if (groundMenu[opcode - 30]!!.lowercase() == "null") {
                    groundMenu[opcode - 30] = null
                }
            }
            in 35 until 40 -> inventoryMenu[opcode - 35] = buf.readString()
            40 -> {
                val count = buf.readUnsignedByte()

                for (i in 0 until count) {
                    buf.readShort()
                    buf.readShort()
                }
            }
            41 -> {
                val count = buf.readUnsignedByte()

                for (i in 0 until count) {
                    buf.readShort()
                    buf.readShort()
                }
            }
            42 -> {
                val count = buf.readUnsignedByte()

                for (i in 0 until count) {
                    buf.readByte()
                }
            }
            65 -> grandExchange = true
            78 -> buf.readUnsignedShort()
            79 -> buf.readUnsignedShort()
            90 -> buf.readShort()
            91 -> buf.readShort()
            92 -> buf.readShort()
            93 -> buf.readShort()
            95 -> buf.readShort()
            96 -> buf.readByte()
            97 -> noteLinkId = buf.readUnsignedShort()
            98 -> noteTemplateId = buf.readUnsignedShort()
            in 100 until 110 -> {
                buf.readUnsignedShort()
                buf.readUnsignedShort()
            }
            110 -> buf.readShort()
            111 -> buf.readShort()
            112 -> buf.readShort()
            113 -> buf.readByte()
            114 -> buf.readByte()
            115 -> teamCape = buf.readUnsignedByte().toInt()
            121 -> lendId = buf.readUnsignedShort() // lendId
            122 -> lendTemplateId = buf.readUnsignedShort() // lend template
            124 -> {
                for(i in 0 until 6) {
                    buf.readShort()
                }
            }
            125 -> {
                for(i in 0 until 3) {
                    buf.readByte()
                }
            }
            126 -> {
                for(i in 0 until 3) {
                    buf.readByte()
                }
            }
            127 -> {
                buf.readByte()
                buf.readShort()
            }
            128 -> {
                buf.readByte()
                buf.readShort()
            }
            129 -> {
                buf.readByte()
                buf.readShort()
            }
            130 -> {
                buf.readByte()
                buf.readShort()
            }
            132 -> {
                val count = buf.readUnsignedByte()
                for(i in 0 until count) {
                    buf.readUnsignedShort()
                }
            }
            134 -> buf.readByte()
            139 -> recolourId = buf.readUnsignedShort()
            140 -> recolourTemplateId = buf.readUnsignedShort()
            249 -> {
                params.putAll(readParams(buf))

                for (i in 0 until 8) {
                    val paramId = 451 + i
                    val option = params.get(paramId) as? String ?: continue
                    equipmentMenu[i] = option
                }
            }
        }
    }

    companion object {
        fun getSlotText(slot: Int): String {
            return when (slot) {
                0 -> "on head"
                1 -> "on your back"
                2 -> "on neck"
                3 -> "as a weapon"
                4 -> "on body"
                5 -> "as a shield"
                7 -> "on legs"
                9 -> "on hands"
                10 -> "on feet"
                12 -> "on ring finger"
                13 -> "in the quiver"
                else -> "null"
            }
        }
    }
}