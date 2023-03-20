package gg.rsmod.plugins.content.skills.agility

import gg.rsmod.game.model.attr.GNOME_AGILITY_STAGE

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
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.message("You walk carefully across the slippery log...", type = ChatMessageType.GAME_MESSAGE)
        //TODO ADD RENDER EMOTE
        player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
        wait(distance)
        player.message("... and make it safely to the other side.", type = ChatMessageType.GAME_MESSAGE)
        player.addXp(Skills.AGILITY, 7.5)
        increaseStage(player, 1)
    }
}

on_obj_option(obj = Objs.OBSTACLE_NET, option = "Climb-over") {
    val destination = Tile(player.tile.x, player.tile.z - 2, 1)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.message("You climb the netting...", type = ChatMessageType.GAME_MESSAGE)
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 7.5)
        increaseStage(player, 2)
    }
}

on_obj_option(obj = 35970, option = "Climb") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(obj.tile.x, player.tile.z - 3, 2)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.message("You climb the tree...", type = ChatMessageType.GAME_MESSAGE)
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 5.0)
        player.message("... to the platform above.", type = ChatMessageType.GAME_MESSAGE)
        increaseStage(player, 3)
    }
}
on_obj_option(obj = Objs.BALANCING_ROPE, option = "Walk-on") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(2483, obj.tile.z, 2)
    val distance = player.tile.getDistance(destination)
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.message("You carefully cross the tightrope.", type = ChatMessageType.GAME_MESSAGE)
        //TODO RENDER ANIM
        player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
        wait(distance)
        player.addXp(Skills.AGILITY, 7.5)
        player.message("... to the platform above.", type = ChatMessageType.GAME_MESSAGE)
        increaseStage(player, 4)
    }
}

on_obj_option(obj = Objs.TREE_BRANCH, option = "Climb-down") {
    val obj = player.getInteractingGameObj()
    val destination = Tile(obj.tile.x, player.tile.z, 0)
    val distance = player.tile.height - destination.height
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.message("You climb down the tree...", type = ChatMessageType.GAME_MESSAGE)
        player.animate(CLIMB_ANIMATION)
        wait(distance)
        player.moveTo(destination)
        player.addXp(Skills.AGILITY, 5.0)
        player.message("You land on the ground.", type = ChatMessageType.GAME_MESSAGE)
        increaseStage(player, 5)
    }
}
    on_obj_option(obj = Objs.OBSTACLE_NET_2286, option = 1) {
        val destination = Tile(player.tile.x, player.tile.z + 3, 0)
        val distance = player.tile.getDistance(destination)
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.message("You climb down the netting...", type = ChatMessageType.GAME_MESSAGE)
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
        val destination = Tile(obj.tile.x, 3437, 0)
        val distance = player.tile.getDistance(destination)
        if (player.tile.z > obj.tile.z)
            return@on_obj_option
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.message("You squeeze into the pipe...", type = ChatMessageType.GAME_MESSAGE)
            //TODO RENDER ANIM
            player.walkTo(destination, MovementQueue.StepType.FORCED_WALK, detectCollision = false)
            wait(distance)
            if (stage == 6) {
                player.addXp(Skills.AGILITY, 7.5 + COMPLETION_BONUS_EXPERIENCE)
                player.setGnomeAgilityStage(0)
            } else {
                player.addXp(Skills.AGILITY, 7.5)
            }
        }
    }
}