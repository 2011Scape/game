import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.plugins.content.inter.bank.openDepositBox
import kotlin.random.Random

/**
 * @author Harley <https://github.com/HarleyGilpin>
 * Description: This plugin is for handling the living rock caverns.
 * Date: 4/15/2023
 */

// Enum class to represent each mineral deposit with its tile, spawn interval range, and timer key
enum class MineralDeposit(
    val tile: Tile,
    val spawnIntervalRange: IntRange,
    val timerKey: TimerKey,
) {
    // Define all 12 mineral deposit locations, their spawn intervals, and unique timer keys
    MINERAL_DEPOSIT_1(
        Tile(3664, 5090, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_2(
        Tile(3667, 5075, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_3(
        Tile(3674, 5098, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_4(
        Tile(3687, 5107, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_5(
        Tile(3690, 5125, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_6(
        Tile(3690, 5146, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_7(
        Tile(3677, 5160, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_8(
        Tile(3647, 5142, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_9(
        Tile(3629, 5148, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_10(
        Tile(3625, 5107, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_11(
        Tile(3615, 5090, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    MINERAL_DEPOSIT_12(
        Tile(3637, 5094, 0),
        180..400,
        TimerKey(tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true),
    ),
    ;

    // Companion object to provide a helper function to generate random spawn intervals
    companion object {
        fun randomSpawnInterval(range: IntRange): Int = Random.nextInt(range.first, range.last + 1)
    }
}

// On world initialization, set each deposit's timer with a random spawn interval
on_world_init {
    MineralDeposit.values().forEach { deposit ->
        world.timers[deposit.timerKey] = MineralDeposit.randomSpawnInterval(deposit.spawnIntervalRange)
    }
}

// Set up an event for each mineral deposit's timer, triggering the spawn function when the timer expires
MineralDeposit.values().forEach { deposit ->
    on_timer(deposit.timerKey) {
        // Spawn the mineral deposit at the specified tile
        val tile = deposit.tile
        spawnMineralDeposit(world, tile)
        // Reset the timer with a new random spawn interval
        world.timers[deposit.timerKey] = MineralDeposit.randomSpawnInterval(deposit.spawnIntervalRange)
    }
}

// Function to spawn mineral deposits, taking the world and tile as arguments
fun spawnMineralDeposit(
    world: World,
    tile: Tile,
) {
    // Find players within a specific radius of the tile
    // Check if there's an interactable object at the specified tile
    // If the object is a collapsed mineral deposit, trigger the spawn animation and spawn the mineral deposit
    try {
        val filteredPlayers = mutableListOf<Player>()
        for (player in world.players.entries) {
            if (player != null && player.tile.isWithinRadius(tile, 50)) {
                filteredPlayers.add(player)
            }
        }
        val playersWithinRange = filteredPlayers

        if (playersWithinRange.isEmpty()) {
            // GameService.logger.debug("No players found within 10 tiles.")
            return
        }

        val objectSelect = world.getObject(tile, ObjectType.INTERACTABLE)
        if (objectSelect != null) {
            val GOLD_MINERAL_DEPOSIT =
                DynamicObject(Objs.MINERAL_DEPOSIT_45076, objectSelect.type, objectSelect.rot, objectSelect.tile)
            val COLLAPSED_GOLD_MINERAL_DEPOSIT =
                DynamicObject(
                    Objs.COLLAPSED_MINERAL_DEPOSIT_45075,
                    objectSelect.type,
                    objectSelect.rot,
                    objectSelect.tile,
                )
            val COAL_MINERAL_DEPOSIT =
                DynamicObject(Objs.MINERAL_DEPOSIT, objectSelect.type, objectSelect.rot, objectSelect.tile)
            val COLLAPSED_COAL_MINERAL_DEPOSIT =
                DynamicObject(Objs.COLLAPSED_MINERAL_DEPOSIT, objectSelect.type, objectSelect.rot, objectSelect.tile)
            val spawnTime = Random.nextInt(350, 501)

            if (objectSelect.isSpawned(world) && objectSelect.id == Objs.COLLAPSED_MINERAL_DEPOSIT_45075) {
                world.queue {
                    playersWithinRange.forEach { currentPlayer ->
                        currentPlayer.write(LocAnimMessage(gameObject = objectSelect, animation = 12219))
                    }
                    wait(4)
                    world.spawnTemporaryObject(GOLD_MINERAL_DEPOSIT, spawnTime, COLLAPSED_GOLD_MINERAL_DEPOSIT)
                }
            } else if (objectSelect.isSpawned(world) && objectSelect.id == Objs.COLLAPSED_MINERAL_DEPOSIT) {
                world.queue {
                    playersWithinRange.forEach { currentPlayer ->
                        currentPlayer.write(LocAnimMessage(gameObject = objectSelect, animation = 12219))
                    }
                    wait(4)
                    world.spawnTemporaryObject(COAL_MINERAL_DEPOSIT, spawnTime, COLLAPSED_COAL_MINERAL_DEPOSIT)
                }
            } else {
                // GameService.logger.debug("Error: No object found at this location.")
            }
        } else {
            // GameService.logger.debug("Error: No interactable object found at this location.")
        }
    } catch (e: Exception) {
        // GameService.logger.debug("Error: Failed to spawn gold node. Please try again later.")
    }
}

// Functions to handle climbing up and down ropes, as well as prospecting and depositing minerals

fun climbDownRope(
    p: Player,
    obj: GameObject,
) {
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
            // player.write(LocAnimMessage(gameObject = nonNullRopeObject, animation = 12225))
            p.write(LocAnimMessage(gameObject = nonNullRopeObject, animation = 12224))
        }
        wait(1)
    }
}

fun climbUpRope(
    p: Player,
    obj: GameObject,
) {
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

on_obj_option(obj = Objs.PULLEY_LIFT, option = "deposit", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    // Check if the rope object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            player.openDepositBox()
        }
    }
}
