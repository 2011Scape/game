package gg.rsmod.plugins.content.skills.agility

import gg.rsmod.game.model.attr.ADVANCED_BARBARIAN_AGILITY_STAGE
import gg.rsmod.game.model.attr.BARBARIAN_AGILITY_STAGE

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

/**
 * TO-DO:
 * Better animations!
 * Add Fail changes
 */

val CLIMB_ANIMATION = 828
val COMPLETION_BONUS_EXPERIENCE = 46.2
val ADVANCED_COMPLETION_BONUS_EXPERIENCE = 615

fun Player.getBarbarianAgilityStage(): Int {
    val lastStage = attr[BARBARIAN_AGILITY_STAGE]
    if (lastStage == null) {
        setBarbarianAgilityStage(0)
        return getBarbarianAgilityStage()
    }
    return lastStage
}

fun Player.getAdvancedBarbarianAgilityStage(): Int {
    val lastStage = attr[ADVANCED_BARBARIAN_AGILITY_STAGE]
    if (lastStage == null) {
        setAdvancedBarbarianAgilityStage(0)
        return getAdvancedBarbarianAgilityStage()
    }
    return lastStage
}

fun Player.setBarbarianAgilityStage(stage: Int) {
    attr[BARBARIAN_AGILITY_STAGE] = stage
}

fun Player.setAdvancedBarbarianAgilityStage(stage: Int) {
    attr[ADVANCED_BARBARIAN_AGILITY_STAGE] = stage
}

fun increaseStage(
    player: Player,
    newStage: Int,
) {
    val stage = player.getBarbarianAgilityStage()
    if (stage + 1 == newStage) {
        player.setBarbarianAgilityStage(newStage)
    }
}

fun increaseAdvancedStage(
    player: Player,
    newStage: Int,
) {
    val stage = player.getAdvancedBarbarianAgilityStage()
    if (stage + 1 == newStage) {
        player.setAdvancedBarbarianAgilityStage(newStage)
    }
}
on_obj_option(obj = Objs.ROPE_SWING_43526, option = "Swing-on") {
    val destination = Tile(player.tile.x, 3549, 0)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You swing across...")
        val movement =
            ForcedMovement.of(
                player.tile,
                destination,
                clientDuration1 = 60,
                clientDuration2 = 65,
                directionAngle = 3,
                lockState = LockState.FULL,
            )
        player.animate(751)
        player.faceTile(destination)
        player.swingRopeSwing(movement)
        wait(distance - 4)
        player.resetRenderAnimation()
        player.filterableMessage("... and make it safely to the other side.")
        player.addXp(Skills.AGILITY, 22.0)
        player.setBarbarianAgilityStage(1)
        player.setAdvancedBarbarianAgilityStage(1)
    }
}
on_obj_option(obj = Objs.LOG_BALANCE_43595, option = "Walk-across") {
    val destination = Tile(2541, 3546, 0)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You walk carefully across the slippery log...")
        player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
        player.setRenderAnimation(155)
        wait(distance + 2)
        player.resetRenderAnimation()
        player.filterableMessage("... and make it safely to the other side.")
        player.addXp(Skills.AGILITY, 13.7)
        increaseStage(player, 2)
        increaseAdvancedStage(player, 2)
    }
}
on_obj_option(obj = Objs.OBSTACLE_NET_20211, option = "Climb-over") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(obj.tile.x - 1, player.tile.z, 1)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You climb the netting...")
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 8.2)
        increaseStage(player, 3)
        player.setAdvancedBarbarianAgilityStage(0)
    }
}
on_obj_option(obj = Objs.BALANCING_LEDGE, option = "Walk-across") {
    val destination = Tile(2532, 3547, 1)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You walk carefully across the slippery log...")
        val movement =
            ForcedMovement.of(
                player.tile,
                destination,
                clientDuration1 = 60,
                clientDuration2 = 65,
                directionAngle = 3,
                lockState = LockState.FULL,
            )
        player.animate(753)
        player.faceTile(destination)
        player.walkacrossBalacingLedge(movement)
        wait(distance)
        player.resetRenderAnimation()
        player.filterableMessage("... and make it safely to the other side.")
        player.addXp(Skills.AGILITY, 22.0)
        increaseStage(player, 4)
    }
}
on_obj_option(obj = Objs.LADDER_3205, option = "Climb-down") {
    val destination = Tile(2532, 3546, 0)
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(828)
        wait(2)
        player.moveTo(destination)
        player.resetRenderAnimation()
    }
}

on_obj_option(obj = Objs.CRUMBLING_WALL, option = "Climb-over") {
    when (player.tile.x) {
        2536 -> {
            val destination = Tile(2538, 3553, 0)
            player.lockingQueue(lockState = LockState.FULL) {
                val movement =
                    ForcedMovement.of(
                        player.tile,
                        destination,
                        clientDuration1 = 33,
                        clientDuration2 = 60,
                        directionAngle = 3,
                        lockState = LockState.FULL,
                    )
                player.faceTile(destination)
                player.filterableMessage("You climb the low wall...")
                player.crumblingWall(movement)
                player.moveTo(destination)
                player.resetRenderAnimation()
                player.addXp(Skills.AGILITY, 13.7)
                increaseStage(player, 5)
            }
        }
        2541 -> {
            val stage = player.getBarbarianAgilityStage()
            val destination = Tile(2543, 3553, 0)
            player.lockingQueue(lockState = LockState.FULL) {
                val movement =
                    ForcedMovement.of(
                        player.tile,
                        destination,
                        clientDuration1 = 33,
                        clientDuration2 = 60,
                        directionAngle = 3,
                        lockState = LockState.FULL,
                    )
                player.faceTile(destination)
                player.filterableMessage("You climb the low wall...")
                player.crumblingWall(movement)
                player.moveTo(destination)
                player.resetRenderAnimation()
                player.addXp(Skills.AGILITY, 13.7)
                if (stage == 5) {
                    player.addXp(Skills.AGILITY, 13.7 + COMPLETION_BONUS_EXPERIENCE)
                    player.setBarbarianAgilityStage(0)
                } else {
                    player.addXp(Skills.AGILITY, 13.7)
                }
            }
        }
        else -> {
            player.filterableMessage("You cannot climb that from this side.")
        }
    }
}
on_obj_option(obj = Objs.WALL_43533, option = "Run-up") {
    if (player.skills.getCurrentLevel(Skills.AGILITY) > 89) {
        val destination = Tile(2537, 3545, 2)
        val distance = player.tile.getDistance(destination)
        player.lockingQueue(lockState = LockState.FULL) {
            player.filterableMessage("You run up the wall...")
            player.animate(10493)
            wait(distance)
            player.moveTo(destination)
            player.addXp(Skills.AGILITY, 15.0)
            player.filterableMessage("... to the platform above.")
            increaseAdvancedStage(player, 3)
            player.setBarbarianAgilityStage(0)
        }
    } else {
        player.message("You need a agility of level 90 to run up this wall!")
    }
}
on_obj_option(obj = Objs.WALL_43597, option = "Climb-up") {
    val destination = Tile(2536, 3546, 3)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You climb the wall...")
        player.animate(10023)
        wait(distance)
        player.moveTo(destination)
        player.animate(11794)
        player.addXp(Skills.AGILITY, 15.0)
        increaseAdvancedStage(player, 4)
    }
}
on_obj_option(obj = Objs.SPRING_DEVICE, option = "Fire") {
    val destination = Tile(2532, 3553, 3)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.filterableMessage("You are fired in to the air...")
        player.animate(4189)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 15.0)
        increaseAdvancedStage(player, 5)
    }
}
on_obj_option(obj = Objs.BALANCE_BEAM_43527, option = "Cross") {
    val destination = Tile(2536, 3553, 3)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 15.0)
        increaseAdvancedStage(player, 6)
    }
}
on_obj_option(obj = Objs.GAP_43531, option = "Jump-over") {
    val destination = Tile(2538, 3553, 2)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(2588)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 15.0)
        increaseAdvancedStage(player, 7)
    }
}
on_obj_option(obj = Objs.ROOF_43532, option = "Slide-down") {
    val stage = player.getAdvancedBarbarianAgilityStage()
    val destination = Tile(2544, player.tile.z, 0)
    val destination1 = Tile(2544, player.tile.z, 0)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(11790)
        wait(distance)
        player.moveTo(destination1)
        player.animate(11791)
        wait(2)
        player.animate(2558)
        player.moveTo(destination)
        if (stage == 7) {
            player.addXp(Skills.AGILITY, 15.0 + ADVANCED_COMPLETION_BONUS_EXPERIENCE)
            player.setAdvancedBarbarianAgilityStage(0)
        } else {
            player.addXp(Skills.AGILITY, 15.0)
        }
    }
}

fun Player.swingRopeSwing(movement: ForcedMovement) {
    queue {
        // player.stopMovement()
        playSound(Sfx.SWING_ACROSS)
        animate(751)
        forceMove(this, movement)
    }
}

fun Player.walkacrossBalacingLedge(movement: ForcedMovement) {
    queue {
        player.animate(759)
        forceMove(this, movement)
        wait(1)
    }
}

fun Player.crumblingWall(movement: ForcedMovement) {
    queue {
        player.stopMovement()
        wait(2)
        playSound(Sfx.CLIMB_UNDER)
        animate(6132)
        forceMove(this, movement)
    }
}
