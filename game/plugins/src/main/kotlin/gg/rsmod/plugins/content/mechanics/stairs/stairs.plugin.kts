import gg.rsmod.plugins.content.mechanics.stairs.StairService
import gg.rsmod.plugins.content.mechanics.stairs.Stairs

load_service(StairService())

/**
 * Implements climbing for all stairs listed in the ./data/cfg/stairs/stairs.json file.
 *
 * Stairs in the above file currently have the following options, with their respective default values:
 * objectId = -1,
 * northFacingRotation = 0,
 * horizontalMovementDistance = 0,
 * verticalMovementDistance = 1,
 * climbDownOption = "climb-down",
 * climbUpOption = "climb-up",
 * climbDialogueOption = "climb",
 */
on_world_init {
    world.getService(StairService::class.java)!!.stairs.forEach { stair ->
        val hasClimbUpOption =
            world.definitions.get(ObjectDef::class.java, stair.objectId).options.any { option ->
                option?.lowercase() == stair.climbUpOption.lowercase()
            }
        val hasClimbDownOption =
            world.definitions.get(ObjectDef::class.java, stair.objectId).options.any { option ->
                option?.lowercase() == stair.climbDownOption.lowercase()
            }
        val hasClimbDialogueOption =
            world.definitions.get(ObjectDef::class.java, stair.objectId).options.any { option ->
                option?.lowercase() == stair.climbDialogueOption.lowercase()
            }

        if (hasClimbDialogueOption) {
            on_obj_option(stair.objectId, stair.climbDialogueOption) {
                player.queue {
                    when (options("Climb up the staircase.", "Climb down the staircase.")) {
                        1 -> climbUp(player, stair)
                        2 -> climbDown(player, stair)
                    }
                }
            }
        }

        if (hasClimbUpOption) {
            on_obj_option(stair.objectId, stair.climbUpOption) {
                climbUp(player, stair)
            }
        }

        if (hasClimbDownOption) {
            on_obj_option(stair.objectId, stair.climbDownOption) {
                climbDown(player, stair)
            }
        }
    }
}

fun climbUp(
    player: Player,
    stair: Stairs,
) {
    var x = player.tile.x
    var z = player.tile.z
    val height = player.tile.height + stair.verticalMovementDistance

    when (player.getInteractingGameObj().rot) {
        stair.northFacingRotation -> z += stair.horizontalMovementDistance // Moving north
        (stair.northFacingRotation + 1) % 4 -> x += stair.horizontalMovementDistance // Moving east
        (stair.northFacingRotation + 2) % 4 -> z -= stair.horizontalMovementDistance // Moving south
        (stair.northFacingRotation + 3) % 4 -> x -= stair.horizontalMovementDistance // Moving west
    }

    player.handleStairs(x, z, height)
}

fun climbDown(
    player: Player,
    stair: Stairs,
) {
    var x = player.tile.x
    var z = player.tile.z
    val height = player.tile.height - stair.verticalMovementDistance

    when (player.getInteractingGameObj().rot) {
        stair.northFacingRotation -> z -= stair.horizontalMovementDistance // Moving south
        (stair.northFacingRotation + 1) % 4 -> x -= stair.horizontalMovementDistance // Moving west
        (stair.northFacingRotation + 2) % 4 -> z += stair.horizontalMovementDistance // Moving north
        (stair.northFacingRotation + 3) % 4 -> x += stair.horizontalMovementDistance // Moving east
    }

    player.handleStairs(x, z, height)
}
