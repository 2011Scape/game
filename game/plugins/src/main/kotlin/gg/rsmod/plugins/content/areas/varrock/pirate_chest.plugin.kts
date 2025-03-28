package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PiratesTreasure

on_item_on_obj(Items.CHEST_KEY, Objs.CHEST_2079) {
    if (player.inventory.contains(Items.CHEST_KEY)) {
        openChest(player)
    }
}

on_obj_option(Objs.CHEST_2079, "Open") {
    if (player.inventory.contains(Items.CHEST_KEY)) {
        openChest(player)
    }
    else {
        player.message("The chest is locked.")
    }
}

fun openChest(player: Player) {
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.animate(535)
        val lockedChest = DynamicObject(player.getInteractingGameObj())
        val openChest = DynamicObject(lockedChest, 2080)
        player.message("All that's in the chest is a message...")
        player.inventory.remove(Items.CHEST_KEY)
        world.spawn(openChest)
        wait(4)
        player.inventory.add(Items.PIRATE_MESSAGE)
        player.message("You take the message from the chest")
        world.spawn(lockedChest)
    }
}

on_item_option(Items.PIRATE_MESSAGE, "Read") {
    player.setComponentText(222, 5, "Visit the city of the White Knights. In the park,")
    player.setComponentText(222, 6, "Saradomin points to the X which marks the spot.")
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 222)
    if (player.getCurrentStage(PiratesTreasure) == 2) {
        player.advanceToNextStage(PiratesTreasure)
    }
}
