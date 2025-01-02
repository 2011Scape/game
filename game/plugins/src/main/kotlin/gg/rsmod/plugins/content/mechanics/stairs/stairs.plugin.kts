import gg.rsmod.plugins.content.mechanics.stairs.StairService

load_service(StairService())

on_world_init {
    world.getService(StairService::class.java)!!.stairs.forEach {
        val hasClimbUpOption =
            world.definitions.get(ObjectDef::class.java, it.objectId).options.any { option ->
                option?.lowercase() == it.climbUpOption.lowercase()
            }
        val hasClimbDownOption =
            world.definitions.get(ObjectDef::class.java, it.objectId).options.any { option ->
                option?.lowercase() == it.climbDownOption.lowercase()
            }

        if (hasClimbUpOption) {
            on_obj_option(it.objectId, it.climbUpOption) {
                val x = player.tile.x
                val z = player.tile.z
                val height = player.tile.height

                when (player.getInteractingGameObj().rot) {
                    it.northFacingRotation -> { // Moving north
                        player.handleStairs(x, z + it.horizontalMovementDistance, height + it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 1 % 4 -> { // Moving east
                        player.handleStairs(x + it.horizontalMovementDistance, z, height + it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 2 % 4 -> { // Moving south
                        player.handleStairs(x, z - it.horizontalMovementDistance, height + it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 3 % 4 -> { // Moving west
                        player.handleStairs(x - it.horizontalMovementDistance, z, height + it.verticalMovementDistance)
                    }
                }
            }
        }

        if (hasClimbDownOption) {
            on_obj_option(it.objectId, it.climbDownOption) {
                val x = player.tile.x
                val z = player.tile.z
                val height = player.tile.height

                when (player.getInteractingGameObj().rot) {
                    it.northFacingRotation -> { // Moving south
                        player.handleStairs(x, z - it.horizontalMovementDistance, height - it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 1 % 4 -> { // Moving west
                        player.handleStairs(x - it.horizontalMovementDistance, z, height - it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 2 % 4 -> { // Moving north
                        player.handleStairs(x, z + it.horizontalMovementDistance, height - it.verticalMovementDistance)
                    }

                    it.northFacingRotation + 3 % 4 -> { // Moving east
                        player.handleStairs(x + it.horizontalMovementDistance, z, height - it.verticalMovementDistance)
                    }
                }
            }
        }
    }
}
