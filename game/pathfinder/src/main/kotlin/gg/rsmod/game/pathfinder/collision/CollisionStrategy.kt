package gg.rsmod.game.pathfinder.collision

interface CollisionStrategy {

    fun canMove(tileFlag: Int, blockFlag: Int): Boolean
}
