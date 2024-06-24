package gg.rsmod.game.action

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.message.impl.SetMapFlagMessage
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.MovementQueue
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.attr.INTERACTING_ITEM
import gg.rsmod.game.model.attr.INTERACTING_OBJ_ATTR
import gg.rsmod.game.model.attr.INTERACTING_OPT_ATTR
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.entity.Entity
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.queue.TaskPriority
import gg.rsmod.game.model.timer.FROZEN_TIMER
import gg.rsmod.game.model.timer.STUN_TIMER
import gg.rsmod.game.pathfinder.Route
import gg.rsmod.game.pathfinder.collision.CollisionStrategies
import gg.rsmod.game.plugin.Plugin
import gg.rsmod.util.AabbUtil
import gg.rsmod.util.DataConstants
import java.util.*

/**
 * This class is responsible for calculating distances and valid interaction
 * tiles for [GameObject] path-finding.
 *
 * @author Tom <rspsmods@gmail.com>
 */
object ObjectPathAction {

    fun walk(player: Player, obj: GameObject, lineOfSightRange: Int?, logic: Plugin.() -> Unit) {
        player.queue(TaskPriority.STANDARD) {
            terminateAction = {
                player.stopMovement()
                player.write(SetMapFlagMessage(255, 255))
            }
            val route = walkTo(obj, lineOfSightRange)
            if (route.success) {
                if (lineOfSightRange == null || lineOfSightRange > 0) {
                    faceObj(player, obj)
                }
                player.executePlugin(logic)
            } else {
                player.faceTile(obj.tile)
                when {
                    player.timers.has(FROZEN_TIMER) -> player.writeMessage(Entity.MAGIC_STOPS_YOU_FROM_MOVING)
                    player.timers.has(STUN_TIMER) -> player.writeMessage(Entity.YOURE_STUNNED)
                    else -> player.writeMessage(Entity.YOU_CANT_REACH_THAT)
                }
                player.write(SetMapFlagMessage(255, 255))
            }
        }
    }

    val itemOnObjectPlugin: Plugin.() -> Unit = {
        val player = ctx as Player

        val item = player.attr[INTERACTING_ITEM]!!.get()!!
        val obj = player.attr[INTERACTING_OBJ_ATTR]!!.get()!!
        val lineOfSightRange = player.world.plugins.getObjInteractionDistance(obj.id)

        walk(player, obj, lineOfSightRange) {
            if (!player.world.plugins.executeItemOnObject(player, obj.getTransform(player), item.id)) {
                player.writeMessage(Entity.NOTHING_INTERESTING_HAPPENS)
                if (player.world.devContext.debugObjects) {
                    player.writeConsoleMessage("Unhandled item on object: [item=$item, id=${obj.id}, type=${obj.type}, rot=${obj.rot}, x=${obj.tile.x}, z=${obj.tile.z}]")
                }
            }
        }
    }

    val objectInteractPlugin: Plugin.() -> Unit = {
        val player = ctx as Player

        val obj = player.attr[INTERACTING_OBJ_ATTR]!!.get()!!
        val opt = player.attr[INTERACTING_OPT_ATTR]
        val lineOfSightRange = player.world.plugins.getObjInteractionDistance(obj.id)

        walk(player, obj, lineOfSightRange) {
            if (!player.world.plugins.executeObject(player, obj.getTransform(player), opt!!)) {
                player.writeMessage(Entity.NOTHING_INTERESTING_HAPPENS)
            }
        }
    }

    private suspend fun QueueTask.walkTo(obj: GameObject, lineOfSightRange: Int?): Route {
        val pawn = ctx as Pawn
        val def = obj.getDef(pawn.world.definitions)
        var tile = obj.tile
        val type = obj.type
        val rot = obj.rot
        var width = def.width
        var length = def.length
        val blockApproach = def.blockApproach
        val wall = type == ObjectType.LENGTHWISE_WALL.value || type == ObjectType.DIAGONAL_WALL.value
        val diagonal = type == ObjectType.DIAGONAL_WALL.value || type == ObjectType.DIAGONAL_INTERACTABLE.value
        val wallDeco = type == ObjectType.INTERACTABLE_WALL_DECORATION.value || type == ObjectType.INTERACTABLE_WALL.value
        val blockDirections = EnumSet.noneOf(Direction::class.java)

        if (wallDeco) {
            width = 0
            length = 0
        } else if (!wall && (rot == 1 || rot == 3)) {
            width = def.length
            length = def.width
        }

        /*
         * Objects have a clip mask in their [ObjectDef] which can be used
         * to specify any directions that the object can't be 'interacted'
         * from.
         */
        val blockBits = 4
        val blockFlag = (DataConstants.BIT_MASK[blockBits] and (blockApproach shl rot)) or (blockApproach shr (blockBits - rot))

        if ((0x1 and blockFlag) != 0) {
            blockDirections.add(Direction.NORTH)
        }

        if ((0x2 and blockFlag) != 0) {
            blockDirections.add(Direction.EAST)
        }

        if ((0x4 and blockFlag) != 0) {
            blockDirections.add(Direction.SOUTH)
        }

        if ((blockFlag and 0x8) != 0) {
            blockDirections.add(Direction.WEST)
        }

        /*
         * Wall objects can't be interacted from certain directions due to
         * how they are visually placed in a tile.
         */
        val blockedWallDirections = when (rot) {
            0 -> EnumSet.of(Direction.NORTH)
            1 -> EnumSet.of(Direction.EAST)
            2 -> EnumSet.of(Direction.SOUTH)
            3 -> EnumSet.of(Direction.WEST)
            else -> throw IllegalStateException("Invalid object rotation: $rot")
        }

        /*
         * Diagonal walls have an extra direction set as 'blocked', this is to
         * avoid the player interacting with the door and having its opened
         * door object be spawned on top of them, which leads to them being
         * stuck.
         */
        if (wall && diagonal) {
            when (rot) {
                0 -> blockedWallDirections.add(Direction.NORTH)
                1 -> blockedWallDirections.add(Direction.EAST)
                2 -> blockedWallDirections.add(Direction.SOUTH)
                3 -> blockedWallDirections.add(Direction.WEST)
            }
        }
        if (wall) {
            /*
             * Check if the pawn is within interaction distance of the wall.
             */
            if (pawn.tile.isWithinRadius(tile, 1)) {
                val dir = Direction.between(tile, pawn.tile)
                if (dir !in blockedWallDirections && (diagonal || !AabbUtil.areDiagonal(pawn.tile.x, pawn.tile.z, pawn.getSize(), pawn.getSize(), tile.x, tile.z, width, length))) {
                    return Route(Pawn.EMPTY_TILE_DEQUE, alternative = false, success = true)
                }
            }

            blockDirections.addAll(blockedWallDirections)
        }

        val route = pawn.world.pathFinder.findPath(
            level = pawn.tile.height,
            srcX = pawn.tile.x,
            srcZ = pawn.tile.z,
            destX = tile.x,
            destZ = tile.z,
            destWidth = def.width,
            destHeight = def.length,
            srcSize = pawn.getSize(),
            collision = CollisionStrategies.Normal,
            objRot = obj.rot,
            objShape = obj.type,
            blockAccessFlags = def.blockApproach
        )

        val tileQueue: Queue<Tile> = ArrayDeque(route.waypoints.map { Tile(it.x, it.z, it.level) })
        pawn.walkPath(tileQueue, MovementQueue.StepType.NORMAL, detectCollision = true)

        val last = pawn.movementQueue.peekLast()

        while (last != null && !pawn.tile.sameAs(last) && !pawn.timers.has(FROZEN_TIMER) && !pawn.timers.has(STUN_TIMER) && pawn.lock.canMove()) {
            wait(1)
        }

        if (pawn.timers.has(STUN_TIMER)) {
            pawn.stopMovement()
            return Route.FAILED
        }

        if (pawn.timers.has(FROZEN_TIMER)) {
            pawn.stopMovement()
            return Route.FAILED
        }

        if (wall && !route.success && Direction.between(tile, pawn.tile) !in blockedWallDirections) {
            // Here we assume that route.waypoints is already of type List<RouteCoordinates>
            return Route(route.waypoints, alternative = false, success = true)
        }
        // Find the nearest tile within the object's dimensions to the player
        val nearestTile = findNearestTile(pawn.tile, tile, width, length, rot)

        val isPathBlocked = pawn.isPathBlocked(nearestTile)
        val radius = lineOfSightRange ?: 1

        val isWithinRadius = pawn.tile.isWithinRadius(nearestTile, radius)
        // Ensure the route is successful only if the player is within interaction range to the nearest object tile
        if (route.success && (isWithinRadius) && (!isPathBlocked || wall || wallDeco)) {
            //println("isBlocked: $isPathBlocked, nearestTile: $nearestTile, isWithinRadius: $isWithinRadius, radius: $radius")

            return route
        }
        //println("isBlocked: $isPathBlocked, nearestTile: $nearestTile, isWithinRadius: $isWithinRadius, radius: $radius")
        return Route.FAILED
    }

    private fun findNearestTile(playerTile: Tile, objectTile: Tile, width: Int, length: Int, rotation: Int): Tile {
        val adjustedWidth = if (rotation == 1 || rotation == 3) length else width
        val adjustedLength = if (rotation == 1 || rotation == 3) width else length

        val nearestX = playerTile.x.coerceIn(objectTile.x..objectTile.x + adjustedWidth)
        val nearestZ = playerTile.z.coerceIn(objectTile.z..objectTile.z + adjustedLength)

        return Tile(nearestX, nearestZ, objectTile.height)
    }

    private fun faceObj(pawn: Pawn, obj: GameObject) {
        val def = pawn.world.definitions.get(ObjectDef::class.java, obj.id)
        val rot = obj.rot
        val type = obj.type

        when (type) {
            ObjectType.LENGTHWISE_WALL.value -> {
                if (!pawn.tile.sameAs(obj.tile)) {
                    pawn.faceTile(obj.tile)
                }
            }
            ObjectType.INTERACTABLE_WALL_DECORATION.value, ObjectType.INTERACTABLE_WALL.value -> {
                val dir = when (rot) {
                    0 -> Direction.WEST
                    1 -> Direction.NORTH
                    2 -> Direction.EAST
                    3 -> Direction.SOUTH
                    else -> throw IllegalStateException("Invalid object rotation: $obj")
                }
                pawn.faceTile(pawn.tile.step(dir))
            }
            else -> {
                var width = def.width
                var length = def.length
                if (rot == 1 || rot == 3) {
                    width = def.length
                    length = def.width
                }
                var tile = obj.tile
                if(width > 1 || length > 1) {
                    tile = tile.transform(width shr 1, length shr 1)
                }
                pawn.faceTile(tile, width, length)
            }
        }
    }
}