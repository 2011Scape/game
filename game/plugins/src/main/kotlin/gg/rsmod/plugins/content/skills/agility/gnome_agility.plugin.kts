package gg.rsmod.plugins.content.skills.agility

import gg.rsmod.game.model.attr.GNOME_AGILITY_STAGE
import gg.rsmod.game.sync.block.UpdateBlockType

val CLIMB_ANIMATION = 828
val COMPLETION_BONUS_EXPERIENCE = 39.0

fun Player.getGnomeAgilityStage(): Int {
    val lastStage = attr[GNOME_AGILITY_STAGE]
    if (lastStage == null) {
        setGnomeAgilityStage(0)
        return getGnomeAgilityStage()
    }
    return lastStage
}

fun Player.setGnomeAgilityStage(stage: Int) {
    attr[GNOME_AGILITY_STAGE] = stage
}

fun increaseStage(player: Player, newStage: Int) {
    val stage = player.getGnomeAgilityStage()
    if (stage + 1 == newStage)
        player.setGnomeAgilityStage(newStage)
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
        player.filterableMessage("... to the platform above.")
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

val pipes = intArrayOf(43543, 43544)
pipes.forEach { pipe ->
    on_obj_option(obj = pipe, option = 1) {
        val obj = player.getInteractingGameObj()
        val stage = player.getGnomeAgilityStage()
        if (player.tile.z > obj.tile.z)
            return@on_obj_option
        player.lockingQueue() {
            val pipeStartTile = Tile(obj.tile.x, obj.tile.z - 1)
            if (player.tile != pipeStartTile) {
                val distance = player.tile.getDistance(pipeStartTile)
                player.walkTo(pipeStartTile)
                wait(distance + 2)
                player.faceTile(obj.tile)
            }
            player.filterableMessage("You squeeze into the pipe...")
            player.animate(12457)
            val move = ForcedMovement.of(
                player.tile,
                Tile(obj.tile.x, obj.tile.z + 2),
                clientDuration1 = 10,
                clientDuration2 = 70,
                directionAngle = Direction.NORTH.ordinal,
                lockState = LockState.NONE
            )
            wait(2)
            player.forceMove(this, move)
            wait(2)
            val move2 = ForcedMovement.of(
                player.tile,
                Tile(obj.tile.x, obj.tile.z + 4),
                clientDuration1 = 10,
                clientDuration2 = 70,
                directionAngle = Direction.NORTH.ordinal,
                lockState = LockState.NONE
            )
            player.forceMove(this, move2)
            wait(2)
            player.animate(12458)
            val move3 = ForcedMovement.of(
                player.tile,
                Tile(obj.tile.x, obj.tile.z + 6),
                clientDuration1 = 20,
                clientDuration2 = 70,
                directionAngle = Direction.NORTH.ordinal,
                lockState = LockState.NONE
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