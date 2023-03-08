package gg.rsmod.plugins.content.skills.crafting.other

on_item_option(Items.BUTTONS, "polish") {
    player.queue(TaskPriority.WEAK) {
        if (player.getSkills().getCurrentLevel(Skills.CRAFTING) < 3) {
            player.message("You rub the buttons on your clothes but they aren't improved by the process.")
        } else {
            wait(1)
            val slot = player.getInteractingItemSlot()
            if (player.inventory[slot]?.id == Items.BUTTONS) {
                if (player.inventory.remove(Items.BUTTONS, beginSlot = slot).hasSucceeded()) {
                    player.inventory.add(Items.POLISHED_BUTTONS, beginSlot = slot)
                    player.message("You rub the buttons on your clothes and they become more shiny.")
                    player.addXp(Skills.CRAFTING, 5.0)
                }
            }
        }
    }
}
