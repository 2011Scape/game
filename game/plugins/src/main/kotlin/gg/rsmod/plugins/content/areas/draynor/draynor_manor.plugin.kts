package gg.rsmod.plugins.content.areas.draynor

//Vampyre animations
val STUNNED: Int = 1568
val ASLEEP_IN_COFFIN: Int = 3111
val AWAKEN: Int = 3322
val SPAWN: Int = 3328
val DEATH: Int = 12604

//Player animations
val OPEN_COFFIN: Int = 2991
val MISSING_STAKE_IN_COFFIN: Int = 2992
val PUSHED_BACK: Int = 3064
val ON_FLOOR: Int = 16713
val KILL_W_STAKE: Int = 12606

//Coffin ID/animations
val COFFIN_ID: Int = 162
val COFFIN_OPEN: Int = 3112

//Count draynor boss music
val COUNTING_ON_YOU: Int = 717

//Coffin
on_obj_option(obj = COFFIN_ID, option = "Open") {
    player.queue {
        this.chatPlayer("Count Draynor isn't here. He'll probably be back soon...")
    }
}

on_obj_option(obj = Objs.COFFIN_158, option = "Open") {
    if (player.inventory.contains(Items.STAKE) && player.inventory.contains(Items.STAKE_HAMMER)) {
            val coffin = player.getInteractingGameObj()
            player.lockingQueue(lockState = LockState.FULL) {
                world.remove(obj = coffin)
                world.spawn(
                    DynamicObject(
                        id = COFFIN_ID,
                        type = 10,
                        rot = coffin.rot,
                        tile = Tile(x = coffin.tile.x, z = coffin.tile.z)
                    )
                )
                player.playSong(COUNTING_ON_YOU)
                world.spawn(Npc(id = Npcs.COUNT_DRAYNOR, tile = Tile(coffin.tile.x + 1, coffin.tile.z + 1, coffin.tile.height), world = world))

            }
        } else {
            player.queue {
                this.chatPlayer("I'll need both a stake and stake hammer or hammer, better go get those...")
            }
    }
}


//Stairs
on_obj_option(obj = Objs.STAIRS_47643, option = "Walk-down") {
val obj = player.getInteractingGameObj()

when(obj.tile.x) {
    3115 -> {
        player.handleStairs(x = 3081, z = 9776)
    } else ->
        player.message("Nothing interesting happens.")
    }
}

on_obj_option(obj = Objs.STAIRS_164, option = "Climb-up") {
    val obj = player.getInteractingGameObj()

    when(obj.tile.x) {
        3080 -> {
            player.handleStairs(x = 3116, z = 3355)
        } else ->
        player.message("Nothing interesting happens.")
    }
}
