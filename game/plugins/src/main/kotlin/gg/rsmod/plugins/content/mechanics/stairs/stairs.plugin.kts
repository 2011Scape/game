import gg.rsmod.plugins.content.mechanics.stairs.StairService

load_service(StairService())

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

        if (hasClimbUpOption) {
            on_obj_option(stair.objectId, stair.climbUpOption) {
                var x = player.tile.x
                var z = player.tile.z
                val height = player.tile.height + stair.verticalMovementDistance

                when (player.getInteractingGameObj().rot) {
                    stair.northFacingRotation -> z += stair.horizontalMovementDistance // Moving north
                    stair.northFacingRotation + 1 % 4 -> x += stair.horizontalMovementDistance // Moving east
                    stair.northFacingRotation + 2 % 4 -> z -= stair.horizontalMovementDistance // Moving south
                    stair.northFacingRotation + 3 % 4 -> x -= stair.horizontalMovementDistance // Moving west
                }

                player.handleStairs(x, z, height)
            }
        }

        if (hasClimbDownOption) {
            on_obj_option(stair.objectId, stair.climbDownOption) {
                var x = player.tile.x
                var z = player.tile.z
                val height = player.tile.height - stair.verticalMovementDistance

                when (player.getInteractingGameObj().rot) {
                    stair.northFacingRotation -> z -= stair.horizontalMovementDistance // Moving south
                    stair.northFacingRotation + 1 % 4 -> x -= stair.horizontalMovementDistance // Moving west
                    stair.northFacingRotation + 2 % 4 -> z += stair.horizontalMovementDistance // Moving north
                    stair.northFacingRotation + 3 % 4 -> x += stair.horizontalMovementDistance // Moving east
                }

                player.handleStairs(x, z, height)
            }
        }
    }
}
