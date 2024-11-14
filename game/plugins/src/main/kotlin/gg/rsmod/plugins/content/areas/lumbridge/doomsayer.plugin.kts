package gg.rsmod.plugins.content.areas.lumbridge

val DOOMSAYER_INTERFACE_ID = 583

on_npc_option(Npcs.DOOMSAYER, option = "talk-to") {
    player.queue { chat(this) }
}

on_npc_option(Npcs.DOOMSAYER, option = "Toggle-warnings") {
    player.openInterface(DOOMSAYER_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Dooooom!")
    it.chatPlayer("Where?", facialExpression = FacialExpression.WORRIED)
    it.chatNpc("All around us! I can feel it in the air, header it on the", "wind, smell it..also in the air!")
    it.chatPlayer("Is there anything we can do about the doom?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc(
        "There is nothing you need to do my friend! I am the",
        "Doomsayer, although my real title should be something",
        "like the Danger Tutor.",
    )
    it.chatPlayer("Danger Tutor?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("Yes! I roam the world sensing danger.")
    it.chatNpc(
        "If I find a dangerous area, then I put up warning",
        "signs that will tell you what is so dangerous about that",
        "area.",
    )
    it.chatNpc(
        "If you see the signs often enough, then you can turn",
        "them off, by that time you likely know what the area",
        "has in store for you.",
    )
    it.chatPlayer("But what if I want to see the warnings again?")
    it.chatNpc("That's why I'm waiting here!")
    it.chatNpc("If you want to see the warning messages again, I can", "turn them back on for you.")
    it.chatNpc("Do you need to turn on any warnings right now?")
    when (
        it.options(
            "Yes, I do.",
            "Not right now.",
        )
    ) {
        1 -> {
            it.chatPlayer("Yes, I do.")
            it.player.openInterface(DOOMSAYER_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
        }
        2 -> {
            it.chatNpc("Ok, keep an eye out for the messages though!")
            it.chatPlayer("I will.")
        }
    }
}
