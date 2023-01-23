package gg.rsmod.plugins.content.mechanics.following

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.model.timer.FROZEN_TIMER
import gg.rsmod.game.model.timer.STUN_TIMER

on_player_option("Follow") {
    val target = player.getInteractingPlayer()
    pawn.queue {
        while (true) {
            if (!cycle(this, target)) {
                break
            }
            wait(1)
        }
    }
}

suspend fun cycle(it: QueueTask, target: Pawn): Boolean {
    val pawn = it.pawn

    if (!pawn.lock.canMove()) {
        return false
    }

    pawn.facePawn(target)
    if(!pawn.hasMoveDestination()) {
        pawn.walkTo(target.tile.transform(-target.faceDirection.getDeltaX(), -target.faceDirection.getDeltaZ()))
    }
    return true
}