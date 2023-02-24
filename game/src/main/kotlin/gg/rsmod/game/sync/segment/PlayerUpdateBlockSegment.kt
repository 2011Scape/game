package gg.rsmod.game.sync.segment

import gg.rsmod.game.fs.def.NpcDef
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.sync.SynchronizationSegment
import gg.rsmod.game.sync.block.UpdateBlockType
import gg.rsmod.net.packet.DataType
import gg.rsmod.net.packet.GamePacketBuilder
import gg.rsmod.util.Misc
import kotlin.math.max

/**
 * @author Tom <rspsmods@gmail.com>
 */
class PlayerUpdateBlockSegment(val other: Player, private val newPlayer: Boolean) : SynchronizationSegment {

    override fun encode(buf: GamePacketBuilder) {
        var mask = other.blockBuffer.blockValue()
        val blocks = other.world.playerUpdateBlocks

        var forceFacePawn = false
        var forceFaceTile = false

        var forceFace: Tile? = null
        if (newPlayer) {
            mask = mask or blocks.updateBlocks[UpdateBlockType.APPEARANCE]!!.bit

            when {
                other.blockBuffer.faceDegrees != 0 -> {
                    mask = mask or blocks.updateBlocks[UpdateBlockType.FACE_TILE]!!.bit
                    forceFaceTile = true
                }
                other.blockBuffer.facePawnIndex != -1 -> {
                    mask = mask or blocks.updateBlocks[UpdateBlockType.FACE_PAWN]!!.bit
                    forceFacePawn = true
                }
                else -> {
                    mask = mask or blocks.updateBlocks[UpdateBlockType.FACE_TILE]!!.bit
                    forceFace = other.tile.step(other.lastFacingDirection)
                }
            }
        }

        if (mask >= 0x100) {
            mask = mask or blocks.updateBlockExcessMask
        }
        if (mask >= 0x10000) {
            mask = mask or 0x800
        }

        buf.put(DataType.BYTE, mask and 0xFF)

        if (mask >= 0x100) {
            buf.put(DataType.BYTE, mask shr 8)
        }
        if (mask >= 0x10000) {
            buf.put(DataType.BYTE, mask shr 16)
        }

        blocks.updateBlockOrder.forEach { blockType ->
            val force = when (blockType) {
                UpdateBlockType.FACE_TILE -> forceFaceTile || forceFace != null
                UpdateBlockType.FACE_PAWN -> forceFacePawn
                UpdateBlockType.APPEARANCE -> newPlayer
                else -> false
            }
            if (other.hasBlock(blockType) || force) {
                write(buf, blockType, forceFace)
            }
        }
    }

    private fun write(buf: GamePacketBuilder, blockType: UpdateBlockType, forceFace: Tile?) {
        val blocks = other.world.playerUpdateBlocks

        when (blockType) {

            UpdateBlockType.FORCE_CHAT -> {
                // NOTE(Tom): do not need the structure since this value is always
                // written as a string.
                buf.putString(other.blockBuffer.forceChat)
            }

            UpdateBlockType.MOVEMENT -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation,
                        if (other.blockBuffer.teleport) 127 else if (other.steps?.runDirection != null) 2 else 1)
            }

            UpdateBlockType.MOVEMENT_TYPE -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation, if (other.blockBuffer.teleport) 127 else if (other.steps?.runDirection != null) 2 else 1)
            }

            UpdateBlockType.FACE_TILE -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                if (forceFace != null) {
                    val srcX = other.tile.x * 64
                    val srcZ = other.tile.z * 64
                    val dstX = forceFace.x * 64
                    val dstZ = forceFace.z * 64
                    val degreesX = (srcX - dstX).toDouble()
                    val degreesZ = (srcZ - dstZ).toDouble()
                    buf.put(structure[0].type, structure[0].order, structure[0].transformation, (Math.atan2(degreesX, degreesZ) * 325.949).toInt() and 0x7ff)
                } else {
                    buf.put(structure[0].type, structure[0].order, structure[0].transformation, other.blockBuffer.faceDegrees)
                }
            }

            UpdateBlockType.APPEARANCE -> {
                val appBuf = GamePacketBuilder()

                val settings = 0
                appBuf.put(DataType.BYTE,  settings or other.appearance.gender.id) // flag
                appBuf.put(DataType.BYTE, 0) // title
                appBuf.put(DataType.BYTE, other.skullIcon)
                appBuf.put(DataType.BYTE, other.prayerIcon)
                appBuf.put(DataType.BYTE, if(other.invisible) 1 else 0) // hidden

                val transmog = other.getTransmogId() >= 0

                if (!transmog) {

                    // displays weapon, amulet, cape, and head item
                    for(i in 0 until 4) {
                        val item = other.equipment[i]
                        if (item != null) {
                            appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                        } else {
                            appBuf.put(DataType.BYTE, 0)
                        }
                    }

                    // TODO: implement full helmet definitions
                    // hair
                    var item = other.equipment[0]
                    if(item != null && item.getDef(other.world.definitions).equipType == 8) {
                        appBuf.put(DataType.BYTE, 0)
                    } else if(item != null) {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.lookupHairStyle(other.world, other.appearance.looks[0]))
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[0])
                    }

                    // shield
                    item = other.equipment[5]
                    if (item != null) {
                        appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                    } else {
                        appBuf.put(DataType.BYTE, 0)
                    }

                    // chest
                    item = other.equipment[4]
                    if(item != null) {
                        appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[2])
                    }

                    // TODO: implement full platebody/body definitions
                    // arms
                    if(item != null && item.getDef(other.world.definitions).equipType == 6) {
                        appBuf.put(DataType.BYTE, 0)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[3])
                    }

                    // wrists
                    item = other.equipment[9]
                    if (item != null) {
                        appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[4])
                    }

                    // legs
                    item = other.equipment[7]
                    if (item != null) {
                        appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[5])
                    }

                    // feet
                    item = other.equipment[10]
                    if (item != null) {
                        appBuf.put(DataType.SHORT, 0x8000 + item.getDef(other.world.definitions).appearanceId)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[6])
                    }

                    // TODO: impelement full helmet/mask definitions
                    // beard
                    item = other.equipment[0]
                    if(item != null && item.getDef(other.world.definitions).equipType == 8) {
                        appBuf.put(DataType.BYTE, 0)
                    } else {
                        appBuf.put(DataType.SHORT, 0x100 + other.appearance.looks[1])
                    }


                } else {
                    appBuf.put(DataType.SHORT, 0xFFFF)
                    appBuf.put(DataType.SHORT, other.getTransmogId())
                    appBuf.put(DataType.BYTE, 0)
                }

                // required for a bit hash that'll determine auras, skillcape colors etc
                appBuf.put(DataType.SHORT, 0)

                for (i in 0..10) {
                    val color = max(0, other.appearance.colors[i])
                    appBuf.put(DataType.BYTE, color)
                }

                if(!transmog) {
                    val animations = arrayOf(1426, 823, 819, 820, 821, 822, 824)

                    val weapon = other.equipment[3] // Assume slot 3 is the weapon.
                    if (weapon != null) {
                        val def: Any = weapon.getDef(other.world.definitions).params.get(644) ?: 1426
                        appBuf.put(DataType.SHORT, def as Int)
                    } else {
                        appBuf.put(DataType.SHORT, 1426)
                    }
                }
                appBuf.putString(Misc.formatForDisplay(other.username))
                appBuf.put(DataType.BYTE, other.combatLevel)
                appBuf.put(DataType.BYTE, other.combatLevel)
                appBuf.put(DataType.BYTE, -1)
                appBuf.put(DataType.BYTE, 0)

                if(transmog) {
                    val def = other.world.definitions.get(NpcDef::class.java, other.getTransmogId())
                    val animations = arrayOf(def.standAnim, def.walkAnim, def.walkAnim, def.render3,
                        def.render4, def.render5, def.walkAnim)

                    animations.forEach { anim ->
                        appBuf.put(DataType.SHORT, anim)
                    }
                }

                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation, appBuf.byteBuf.readableBytes())
                buf.putBytes(structure[1].transformation, appBuf.byteBuf)
            }

            UpdateBlockType.HITMARK -> {
                val structure = blocks.updateBlocks[blockType]!!.values

                val hitmarkCountStructure = structure[0]
                val hitbarPercentageStructure = structure[1]

                val hits = other.blockBuffer.hits

                buf.put(hitmarkCountStructure.type, hitmarkCountStructure.order, hitmarkCountStructure.transformation, hits.size)
                hits.forEach { hit ->
                    val hitmarks = Math.min(2, hit.hitmarks.size)

                    /*
                     * Inform the client of how many hitmarkers to decode.
                     */
                    if (hitmarks == 0) {
                        buf.putSmart(32766)
                    }

                    for (i in 0 until hitmarks) {
                        val hitmark = hit.hitmarks[i]
                        buf.putSmart(hitmark.type)
                        buf.putSmart(hitmark.damage)
                    }

                    buf.putSmart(hit.clientDelay)
                    val max: Int = other.getMaxHp()
                    var percentage = 0
                    if (max > 0) {
                        percentage = if (max < other.getCurrentHp()) {
                            255
                        } else {
                            other.getCurrentHp() * 255 / max
                        }
                    }
                    buf.put(hitbarPercentageStructure.type, hitbarPercentageStructure.order, hitbarPercentageStructure.transformation, percentage)
                }

            }

            UpdateBlockType.FACE_PAWN -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation,
                        other.blockBuffer.facePawnIndex)
            }

            UpdateBlockType.ANIMATION -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation, other.blockBuffer.animation)
                buf.put(structure[1].type, structure[1].order, structure[1].transformation, -1)
                buf.put(structure[2].type, structure[2].order, structure[2].transformation, -1)
                buf.put(structure[3].type, structure[3].order, structure[3].transformation, -1)
                buf.put(structure[4].type, structure[4].order, structure[4].transformation, other.blockBuffer.animationDelay)
            }

            UpdateBlockType.GFX -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation, other.blockBuffer.graphicId)
                buf.put(structure[1].type, structure[1].order, structure[1].transformation, (other.blockBuffer.graphicDelay and 0xffff) or (other.blockBuffer.graphicHeight shl 16))
                buf.put(structure[2].type, structure[2].order, structure[2].transformation, other.blockBuffer.graphicRotation and 0x7)
            }

            UpdateBlockType.FORCE_MOVEMENT -> {
                val structure = blocks.updateBlocks[blockType]!!.values
                buf.put(structure[0].type, structure[0].order, structure[0].transformation, other.blockBuffer.forceMovement.diffX1)
                buf.put(structure[1].type, structure[1].order, structure[1].transformation, other.blockBuffer.forceMovement.diffZ1)
                buf.put(structure[2].type, structure[2].order, structure[2].transformation, other.blockBuffer.forceMovement.diffX2)
                buf.put(structure[3].type, structure[3].order, structure[3].transformation, other.blockBuffer.forceMovement.diffZ2)
                buf.put(structure[4].type, structure[4].order, structure[4].transformation, other.blockBuffer.forceMovement.clientDuration1)
                buf.put(structure[5].type, structure[5].order, structure[5].transformation, other.blockBuffer.forceMovement.clientDuration2)
                buf.put(structure[6].type, structure[6].order, structure[6].transformation, other.blockBuffer.forceMovement.directionAngle)
            }

            else -> throw RuntimeException("Unhandled update block type: $blockType")
        }
    }
}