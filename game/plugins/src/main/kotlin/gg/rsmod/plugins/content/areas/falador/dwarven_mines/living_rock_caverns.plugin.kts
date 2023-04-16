import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.model.attr.CORRECT_EXERCISE
import gg.rsmod.game.model.attr.EXERCISE_SCORE
import gg.rsmod.game.model.collision.ObjectType
import kotlin.random.Random

/**
 * @author Harley <https://github.com/HarleyGilpin>
 * Description: This plugin is for handling the living rock caverns.
 * Date: 4/15/2023
 */

/**
 * obj id = 45077
 * moveTo(3012, 9831)
 *
 */

fun climbDownRope(p: Player, obj: GameObject) {
    val faceWest = Tile(x = obj.tile.x - 1, z = obj.tile.z)
    val faceSouth = Tile(3651, 5122, 0)
    val tile = Tile(3651, 5123, 0)
    val ropeObject = p.world.getObject(tile, ObjectType.LENGTHWISE_WALL)
    // Lock the player's actions during this interaction sequence
    p.lockingQueue {
        p.moveTo(obj.tile.x, obj.tile.z)
        wait(2)
        p.faceTile(faceWest)
        wait(2)
        p.animate(12216)
        wait(2)
        p.faceTile(faceSouth)
        p.moveTo(3651, 5122, 0)
        p.animate(12217)
        wait(1)
        ropeObject?.let { nonNullRopeObject ->
            //player.write(LocAnimMessage(gameObject = nonNullRopeObject, animation = 12225))
            p.write(LocAnimMessage(gameObject = nonNullRopeObject, animation = 12224))
            p.message("${p.world.getObject(tile, ObjectType.LENGTHWISE_WALL)}")
        }
        wait(1)
    }
}

fun climbUpRope(p: Player, obj: GameObject) {
    val faceEast = Tile(3014, 9830, 0)
    // Lock the player's actions during this interaction sequence
    p.lockingQueue {
        p.faceTile(obj.tile)
        wait(2)
        p.animate(9136)
        wait(3)
        p.moveTo(3013, 9830, 0)
        p.faceTile(faceEast)
        p.animate(-1)
        wait(1)
    }
}

// Set up an event handler for the "climb" option on the rope
on_obj_option(obj = Objs.ROPE_45077, option = "climb", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    // Check if the rope object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            climbDownRope(player, obj)
        }
    }
}

// Set up an event handler for the "climb" option on the rope
on_obj_option(obj = Objs.ROPE_45078, option = "climb", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    // Check if the rope object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            climbUpRope(player, obj)
        }
    }
}

/**
 * Prospecting
 */
on_obj_option(obj = Objs.MINERAL_DEPOSIT_45076, option = "prospect", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    // Check if the rope object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            player.message("This rock contains gold.")
        }
    }
}

on_obj_option(obj = Objs.MINERAL_DEPOSIT, option = "prospect", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    // Check if the rope object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            player.message("This rock contains coal.")
        }
    }
}


/**
 * Living Rock Caverns Configuration:
 * Mineral Deposit Animations
 * Collapse Mineral Deposit Animation: 12218
 * Open Mineral Deposit Animation: 12219
 * Normal Rock: 12223
 *
 * Objects
 * Mineral Deposit (Gold) Object ID = 45076
 * Depleted (Gold) Object ID = 45075
 * Mineral Deposit (Coal) Object ID = 5999
 * Depleted (Coal) Object ID = 5990
 */