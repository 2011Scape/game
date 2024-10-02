package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.TheRestlessGhost

/**
 * @author Tank <Tank#4733>
 */

val theRestLessGhost = TheRestlessGhost
val ghost = Npc(Npcs.RESTLESS_GHOST, Tile(3250, 3195), world) // Declaring Restless Ghost
// Location for Ghost to appear to
// You search the coffin and find some human remains. There's no point in disturbing them

val closedCoffin = DynamicObject(id = Objs.COFFIN_2145, type = 10, rot = 0, tile = Tile(x = 3249, z = 3192))
val openedCoffin = DynamicObject(id = Objs.COFFIN_5728, type = 10, rot = 0, tile = Tile(x = 3249, z = 3192))
val skullCoffin = DynamicObject(id = Objs.COFFIN_5729, type = 10, rot = 0, tile = Tile(x = 3249, z = 3192))

on_obj_option(obj = Objs.COFFIN_2145, option = "open") {

    openCoffin(player) // Calling the function to open the coffin
}

on_obj_option(obj = Objs.COFFIN_2145, option = "search") {
    player.message("Maybe you should open the coffin first.")
}

on_obj_option(obj = Objs.COFFIN_5728, option = "search") {
    player.message("You search the coffin and find some human remains.")
}

on_obj_option(obj = Objs.COFFIN_5729, option = "search") {
    player.message("There's a complete body in there!")
}

on_obj_option(obj = Objs.COFFIN_5729, option = "close") {

    closeCoffin(player)
}

on_obj_option(obj = Objs.COFFIN_5728, option = "close") {
    closeCoffin(player)
}

on_item_on_obj(obj = Objs.COFFIN_5728, item = Items.MUDDY_SKULL) {
    player.inventory.remove(Items.MUDDY_SKULL)
    theRestLessGhost.finishQuest(player)
}

fun openCoffin(player: Player) {
    player.lock = LockState.DELAY_ACTIONS
    player.animate(536)
    player.message("You open the coffin.")
    if (!player.finishedQuest(theRestLessGhost)) {
        world.remove(closedCoffin)
        world.spawn(ghost)
        world.spawn(openedCoffin)
    } else if (player.finishedQuest(theRestLessGhost)) {
        world.remove(closedCoffin)
        world.spawn(skullCoffin)
    }
    player.unlock()
}

fun closeCoffin(player: Player) {
    player.lock = LockState.DELAY_ACTIONS
    player.animate(535)
    player.message("You close the coffin.")
    if (!player.finishedQuest(theRestLessGhost)) {
        world.remove(openedCoffin)
        world.spawn(closedCoffin)
        world.remove(ghost)
    } else if (player.finishedQuest(theRestLessGhost)) {
        world.remove(skullCoffin)
        world.spawn(closedCoffin)
    }

    player.unlock()
}
