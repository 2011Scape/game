package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheRestlessGhost
import gg.rsmod.plugins.content.quests.startedQuest

/**
 * @author Tank <Tank#4733>
 */

val theRestlessGhost = TheRestlessGhost
val muddySkull = Items.MUDDY_SKULL

val location = Tile(x = 3236, z = 3147, height = 0)
// val rockSkull = DynamicObject(id = Objs.ROCKS_47714, type = 10, rot = 0, tile = Tile(x = 3234, z = 3145))
// val rock = DynamicObject(id = Objs.ROCKS_47713, type= 10, rot =0, tile = Tile(x = 3234, z = 3145))

on_obj_option(Objs.ROCKS_47714, "search") {
    player.queue {
        if (!player.startedQuest(theRestlessGhost) || player.finishedQuest(theRestlessGhost)) {
            player.message("You search the rock and found nothing!")
        } else if (!player.finishedQuest(theRestlessGhost) &&
            (
                player.getCurrentStage(theRestlessGhost) == 3 ||
                    player.getCurrentStage(
                        theRestlessGhost,
                    ) == 4
            )
        ) {
            if (player.inventory.contains(muddySkull)) {
                player.message("You already have a skull!")
            } else {
                if (player.inventory.capacity < 1) {
                    player.filterableMessage("You don't have enough inventory space.")
                } else if (player.inventory.add(Items.MUDDY_SKULL).hasSucceeded()) {
                    if (player.getCurrentStage(theRestlessGhost) == 3) {
                        player.advanceToNextStage(theRestlessGhost)
                    }
                    player.message("You take the skull from the pile of rocks.")
                    // varbit = 0 transforms obj to 47714, 1 = obj 47715, 2 = invisible
                    player.setVarbit(Varbits.MUDDY_SKULL_TAKEN, 1)
                    if (!world.npcs.any { npc -> npc.id == Npcs.SKELETON_WARLOCK && npc.owner == player }) {
                        player.message("A skeleton warlock has appeared!")
                        val skeletonWarlock = Npc(player, Npcs.SKELETON_WARLOCK, location, world)
                        player.lock = LockState.FULL
                        skeletonWarlock.facePawn(player)
                        world.spawn(skeletonWarlock)
                        skeletonWarlock.animate(Anims.CLIMB_FROM_GROUND)
                        wait(3)
                        player.lock = LockState.NONE
                        skeletonWarlock.attack(player)
                    }
                }
            }
        }
    }
}

on_obj_option(Objs.ROCKS_47715, "search") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("There's nothing there of any interest.")
        wait(1)
    }
}
