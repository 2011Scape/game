package gg.rsmod.plugins.content.skills.agility

import gg.rsmod.game.model.attr.ADVANCED_GNOME_AGILITY_STAGE
import gg.rsmod.game.model.attr.GNOME_AGILITY_STAGE

val CLIMB_ANIMATION = 828
val COMPLETION_BONUS_EXPERIENCE = 39.0
val ADVANCED_COMPLETION_BONUS_EXPERIENCE = 605.0

fun Player.getGnomeAgilityStage(): Int {
    val lastStage = attr[GNOME_AGILITY_STAGE]
    if (lastStage == null) {
        setGnomeAgilityStage(0)
        return getGnomeAgilityStage()
    }
    return lastStage
}

fun Player.getAdvancedGnomeAgilityStage(): Int {
    val lastStage = attr[ADVANCED_GNOME_AGILITY_STAGE]
    if (lastStage == null) {
        setAdvancedGnomeAgilityStage(0)
        return getAdvancedGnomeAgilityStage()
    }
    return lastStage
}

fun Player.setGnomeAgilityStage(stage: Int) {
    attr[GNOME_AGILITY_STAGE] = stage
}

fun Player.setAdvancedGnomeAgilityStage(stage: Int) {
    attr[ADVANCED_GNOME_AGILITY_STAGE] = stage
}

fun increaseStage(
    player: Player,
    newStage: Int,
) {
    val stage = player.getGnomeAgilityStage()
    if (stage + 1 == newStage) {
        player.setGnomeAgilityStage(newStage)
    }
}

fun increaseAdvancedStage(
    player: Player,
    newStage: Int,
) {
    val stage = player.getAdvancedGnomeAgilityStage()
    if (stage + 1 == newStage) {
        player.setAdvancedGnomeAgilityStage(newStage)
    }
}

on_obj_option(obj = Objs.LOG_BALANCE, option = "Walk-across") {
    val destination = Tile(2474, 3429, 0)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You walk carefully across the slippery log...")
        player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
        player.setRenderAnimation(155)
        wait(distance + 2)
        player.resetRenderAnimation()
        player.filterableMessage("... and make it safely to the other side.")
        player.addXp(Skills.AGILITY, 7.5)
        player.setGnomeAgilityStage(1)
        player.setAdvancedGnomeAgilityStage(1)
    }
}

on_obj_option(obj = Objs.OBSTACLE_NET, option = "Climb-over") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(obj.tile.x, obj.tile.z - 1, 1)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You climb the netting...")
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 7.5)
        increaseStage(player, 2)
        increaseAdvancedStage(player, 2)
    }
}

on_obj_option(obj = 35970, option = "Climb") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(obj.tile.x, obj.tile.z - 2, 2)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You climb the tree...")
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 5.0)
        player.filterableMessage("... to the platform above.")
        increaseStage(player, 3)
        increaseAdvancedStage(player, 3)
    }
}

on_obj_option(obj = Objs.BALANCING_ROPE, option = "Walk-on") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(2483, obj.tile.z, 2)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You carefully cross the tightrope.")
        player.setRenderAnimation(155)
        player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
        wait(distance)
        player.resetRenderAnimation()
        player.addXp(Skills.AGILITY, 7.5)
        player.filterableMessage("... to the next platform.")
        increaseStage(player, 4)
    }
}
arrayOf(Objs.TREE_BRANCH, Objs.TREE_BRANCH_2315).forEach { branch ->
    on_obj_option(obj = branch, option = "Climb-down") {
        val obj = player.getInteractingGameObj()
        val destination = Tile(obj.tile.x, obj.tile.z, 0)
        val distance = player.tile.height - destination.height
        player.lockingQueue(lockState = LockState.FULL) {
            player.filterableMessage("You climb down the tree...")
            player.animate(CLIMB_ANIMATION)
            wait(distance)
            player.moveTo(destination)
            player.addXp(Skills.AGILITY, 5.0)
            player.filterableMessage("You land on the ground.")
            increaseStage(player, 5)
        }
    }
}
on_obj_option(obj = Objs.OBSTACLE_NET_2286, option = 1) {
    val obj = player.getInteractingGameObj()
    if (player.tile.z >= obj.tile.z) {
        player.message("You can't climb the netting from this side.")
        return@on_obj_option
    }
    val destination = Tile(obj.tile.x, obj.tile.z + 2, 0)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You climb the netting...")
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 7.5)
        increaseStage(player, 6)
    }
}
on_obj_option(obj = 43528, option = "Climb-up") {
    if (player.skills.getCurrentLevel(Skills.AGILITY) > 84) {
        val destination = Tile(2472, 3419, 3)
        val distance = player.tile.getDistance(destination)
        player.lockingQueue(lockState = LockState.FULL) {
            player.filterableMessage("You climb the tree...")
            player.animate(CLIMB_ANIMATION)
            wait(distance)
            player.moveTo(destination)
            player.addXp(Skills.AGILITY, 25.0)
            player.filterableMessage("... to the platform above.")
            increaseAdvancedStage(player, 4)
            player.setGnomeAgilityStage(0)
        }
    } else {
        player.message("You need a agility of level 85 to climb the tree!")
    }
}

on_obj_option(obj = Objs.SIGNPOST_43581, option = "Run-across") {
    val destination = Tile(2484, 3418, 3)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You skilfully run across the board...")
        val runAcrossSign =
            ForcedMovement.of(
                player.tile,
                destination,
                clientDuration1 = 30,
                clientDuration2 = 90,
                directionAngle = 3,
                lockState = LockState.FULL,
            )
        player.faceTile(destination)
        player.crossSignpost(runAcrossSign)
        player.addXp(Skills.AGILITY, 25.0)
        player.filterableMessage("... to the next platform.")
        increaseAdvancedStage(player, 5)
    }
}
on_obj_option(obj = Objs.POLE_43529, option = "Swing-to", lineOfSightDistance = 6) {
    val startDestination = Tile(2486, 3421, 3)
    val destination1 = Tile(2486, 3425, 3)
    val destination2 = Tile(2486, 3429, 3)
    val destination = Tile(2486, 3432, 3)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You swing....")
        val firstMovement =
            ForcedMovement.of(
                player.tile,
                startDestination,
                clientDuration1 = 10,
                clientDuration2 = 43,
                directionAngle = 0,
                lockState = LockState.FULL,
            )
        val leapMovement =
            ForcedMovement.of(
                startDestination,
                destination1,
                clientDuration1 = 1,
                clientDuration2 = 30,
                directionAngle = 0,
                lockState = LockState.FULL,
            )
        val secondMovement =
            ForcedMovement.of(
                destination1,
                destination2,
                clientDuration1 = 125,
                clientDuration2 = 150,
                directionAngle = 0,
                lockState = LockState.FULL,
            )
        val lastMovement =
            ForcedMovement.of(
                destination2,
                destination,
                clientDuration1 = 50,
                clientDuration2 = 75,
                directionAngle = 0,
                lockState = LockState.FULL,
            )
        player.faceTile(destination1)
        player.runTowardsPole(firstMovement)
        wait(2)
        player.leapToPole(leapMovement)
        wait(1)
        player.secondSwingPole(secondMovement)
        wait(3)
        player.lastSwingPole(lastMovement)
        player.resetRenderAnimation()
        wait(3)
        player.addXp(Skills.AGILITY, 25.0)
        player.filterableMessage("... to the next platform.")
        increaseAdvancedStage(player, 6)
    }
}
on_obj_option(obj = Objs.BARRIER_43539, option = "Jump-over", lineOfSightDistance = 3) {
    val obj = player.getInteractingGameObj()
    val stage = player.getAdvancedGnomeAgilityStage()
    val exitPipe = Tile(2485, 3436, 0)
    val enterPipe = Tile(2485, 3434, 3)
    player.lockingQueue(lockState = LockState.FULL) {
        player.walkTo(obj.tile)
        wait(1)
        player.faceTile(enterPipe, 1, 1)
        wait(1)
        player.filterableMessage("You jump over in to the pipe...")
        player.animate(2923)
        player.forceMove(
            this,
            ForcedMovement.of(
                player.tile,
                enterPipe,
                clientDuration1 = 20,
                clientDuration2 = 60,
                directionAngle = 0,
                lockState = LockState.FULL,
            ),
        )
        wait(1)
        player.moveTo(exitPipe)
        player.animate(2924)
        wait(1)
        player.resetRenderAnimation()
        player.addXp(Skills.AGILITY, 25.0)
        player.filterableMessage("... and land on the ground.")
        if (stage == 6) {
            player.addXp(Skills.AGILITY, 25.0 + ADVANCED_COMPLETION_BONUS_EXPERIENCE)
            player.setGnomeAgilityStage(0)
        } else {
            player.addXp(Skills.AGILITY, 25.0)
        }
    }
}
val pipes = intArrayOf(43543, 43544)
pipes.forEach { pipe ->
    on_obj_option(obj = pipe, option = 1) {
        val obj = player.getInteractingGameObj()
        val stage = player.getGnomeAgilityStage()
        if (player.tile.z > obj.tile.z) {
            return@on_obj_option
        }
        player.lockingQueue {
            val pipeStartTile = Tile(obj.tile.x, obj.tile.z - 1)
            if (player.tile != pipeStartTile) {
                val distance = player.tile.getDistance(pipeStartTile)
                player.walkTo(pipeStartTile)
                wait(distance + 2)
                player.faceTile(obj.tile)
            }
            player.filterableMessage("You squeeze into the pipe...")
            player.animate(12457)
            val move =
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x, obj.tile.z + 2),
                    clientDuration1 = 10,
                    clientDuration2 = 70,
                    directionAngle = Direction.NORTH.ordinal,
                    lockState = LockState.NONE,
                )
            wait(2)
            player.forceMove(this, move)
            wait(2)
            val move2 =
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x, obj.tile.z + 4),
                    clientDuration1 = 10,
                    clientDuration2 = 70,
                    directionAngle = Direction.NORTH.ordinal,
                    lockState = LockState.NONE,
                )
            player.forceMove(this, move2)
            wait(2)
            player.animate(12458)
            val move3 =
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x, obj.tile.z + 6),
                    clientDuration1 = 20,
                    clientDuration2 = 70,
                    directionAngle = Direction.NORTH.ordinal,
                    lockState = LockState.NONE,
                )
            player.forceMove(this, move3)
            if (stage == 6) {
                player.addXp(Skills.AGILITY, 7.5 + COMPLETION_BONUS_EXPERIENCE)
                player.setGnomeAgilityStage(0)
            } else {
                player.addXp(Skills.AGILITY, 7.5)
            }
        }
    }
}

fun Player.crossSignpost(runAcrossSign: ForcedMovement) {
    queue {
        player.stopMovement()
        wait(2)
        playSound(Sfx.CLIMB_UNDER)
        animate(2922)
        forceMove(this, runAcrossSign)
    }
}

fun Player.runTowardsPole(firstMovement: ForcedMovement) {
    queue {
        player.stopMovement()
        animate(11784)
        forceMove(this, firstMovement)
    }
}

fun Player.leapToPole(leapMovement: ForcedMovement) {
    queue {
        player.stopMovement()
        animate(11785)
        forceMove(this, leapMovement)
    }
}

fun Player.secondSwingPole(secondMovement: ForcedMovement) {
    queue {
        player.resetRenderAnimation()
        playSound(Sfx.SWING_ACROSS)
        animate(11789)
        forceMove(this, secondMovement)
    }
}

fun Player.lastSwingPole(lastMovement: ForcedMovement) {
    queue {
        player.resetRenderAnimation()
        playSound(Sfx.SWING_ACROSS)
        forceMove(this, lastMovement)
    }
}
