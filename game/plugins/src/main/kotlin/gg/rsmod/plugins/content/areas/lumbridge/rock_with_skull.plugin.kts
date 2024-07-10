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
                    player.advanceToNextStage(theRestlessGhost)
                    player.message("You take the skull from the pile of rocks.")
                    // varbit = 0 transforms obj to 47714, 1 = obj 47715, 2 = invisible
                    player.setVarbit(2130, 1)
                    player.message("A skeleton warlock has appeared!")
                    val skeletonWarlock = Npc(Npcs.SKELETON_WARLOCK, location, world)
                    skeletonWarlock.animate(1993)
                    world.spawn(skeletonWarlock)
                    skeletonWarlock.facePawn(player)
                }
            }
        }
    }
}
