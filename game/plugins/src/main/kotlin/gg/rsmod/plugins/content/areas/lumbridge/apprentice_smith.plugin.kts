package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.APPRENTICE_SMITH, option = "talk-to") {
    player.queue {
        this.chatPlayer("Can you teach me the basics of smithing please?")
        if (!player.hasItem(Items.COPPER_ORE) || !player.hasItem(Items.TIN_ORE)) {
            noItemsDialogue(this)
        } else {
            dialogue(this)
        }
    }
}

suspend fun noItemsDialogue(task: QueueTask) {
    task.itemMessageBox("Look for this icon on your minimap to find a furnance to smelt ores into metal.", 2349)
    task.chatNpc(
        "You'll need to have mined some ore to smelt first. Go",
        "see the mining tutor to the south if you're not",
        "sure how to do this.",
    )
    task.itemMessageBox("Look for this icon on your minimap to find a furnance to smelt ores into metal.", 1265)
}

suspend fun dialogue(task: QueueTask) {
    task.itemMessageBox("Look for this icon on your minimap to find a furnace to smelt ores into metal.", 2349)
    task.chatNpc("I see you have some ore with you to smelt so let's get", "started.")
    task.chatNpc("Click on the furnace to bring up a menu of metal bars", "you can try to make from your ore.")
    task.chatNpc(
        "When you have a full inventory, take it to the bank,",
        "you can find it on the roof of the castle in Lumbridge.",
    )
    task.itemMessageBox(
        "To find a bank, look for this symbol on your minimap after climbing the stairs of the Lumbridge Castle to the top. There are banks all over the world with this symbol.",
        Items.BANKERS_NOTE,
    )
    task.chatNpc("If you have a hammer with you, you can smith the", "bronze bars into equipment on the anvil outside")
    task.chatNpc("I'm afraid the weather over the years has rusted it", "down so it can only be used to work bronze.")
    task.chatNpc(
        "Alternatively you can run up to Varrock. Look for my",
        "Master, the Smithing Tutor, in the west of the city,",
        "he can help you smith better gear.",
    )
}
