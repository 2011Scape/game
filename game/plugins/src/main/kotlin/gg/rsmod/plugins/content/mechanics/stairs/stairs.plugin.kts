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
                var x = player.tile.x
                var z = player.tile.z
                val height = player.tile.height + it.verticalMovementDistance

                when (player.getInteractingGameObj().rot) {
                    it.northFacingRotation -> z += it.horizontalMovementDistance // Moving north
                    it.northFacingRotation + 1 % 4 -> x += it.horizontalMovementDistance // Moving east
                    it.northFacingRotation + 2 % 4 -> z -= it.horizontalMovementDistance // Moving south
                    it.northFacingRotation + 3 % 4 -> x -= it.horizontalMovementDistance // Moving west
                }

                player.handleStairs(x, z, height)
            }
        }

        if (hasClimbDownOption) {
            on_obj_option(it.objectId, it.climbDownOption) {
                var x = player.tile.x
                var z = player.tile.z
                val height = player.tile.height - it.verticalMovementDistance

                when (player.getInteractingGameObj().rot) {
                    it.northFacingRotation -> z -= it.horizontalMovementDistance // Moving south
                    it.northFacingRotation + 1 % 4 -> x -= it.horizontalMovementDistance // Moving west
                    it.northFacingRotation + 2 % 4 -> z += it.horizontalMovementDistance // Moving north
                    it.northFacingRotation + 3 % 4 -> x += it.horizontalMovementDistance // Moving east
                }

                player.handleStairs(x, z, height)
            }
        }
    }
}
