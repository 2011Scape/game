package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.skills.woodcutting.AxeType

val npcName = "Guard in Tree"
val npcId = Npcs.BANK_GUARD
val messages = listOf("Hey - gerroff me!", "Ooooch!", "Oi!", "Will you stop that?", "Don't draw attention to me!",
    "You'll blow my cover! I'm meant to be hidden!", "Ow! That really hurt!", "Watch what you're doing with that " +
        "hatchet, you nit!")

on_obj_option(Objs.TREE_39328, "Chop down") {
    val axe = AxeType.values.reversed().firstOrNull {
        player.skills.getMaxLevel(Skills.WOODCUTTING) >= it.level &&
            (player.equipment.contains(it.item) || player.inventory.contains(it.item))
    }
    if (axe != null) {
        player.lockingQueue(lockState = LockState.FULL) {
            player.animate(axe.animation, idleOnly = true)
            wait(5)
            player.resetAnimation()
            player.unlock()
            chatNpc(message = arrayOf(messages.random()), npcId, FacialExpression.SHOCK, npcName, true)
        }
    }
    else {
        player.message("You do not have an axe which you have the woodcutting level to use.")
    }
}

on_obj_option(Objs.TREE_39328, "Talk to") {
    player.queue {
        chatPlayer("Hello?", facialExpression = FacialExpression.CONFUSED)
        chatNpc(message = arrayOf("Ssshhh! What do you want?"), npcId, FacialExpression.MAD, npcName, true)
        chatPlayer("Well, it's not every day you see a man up a tree.", facialExpression = FacialExpression.CONFUSED)
        chatNpc(message = arrayOf("I'm trying to observe a suspect. Leave me alone!"), npcId, FacialExpression.MAD,
        npcName, true)
        when (options(
            "This is about the bank robbery, right?",
            "You're not being very subtle up there.",
            "Can I do anything to help?"
        )) {
            FIRST_OPTION -> {
                chatPlayer("This is about the bank robbery, right?", facialExpression = FacialExpression.CALM_TALK)
                chatNpc(message = arrayOf("Yes, that's right. We're keeping the suspect under tight observation for the " +
                    "moment."),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                chatPlayer("Can't you just... I dunno... arrest him?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(message = arrayOf("I'm not meant to discuss the case. You know what",
                    "confidentiality rules are like."),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.CALM_TALK)
                chatPlayer("Fair enough.", facialExpression = FacialExpression.CALM_TALK)
            }
            SECOND_OPTION -> {
                chatPlayer("You're not being very subtle up there.", facialExpression = FacialExpression.LAUGHING)
                chatNpc(message = arrayOf("I'd be doing a lot better if nits like you didn't come crowding around me " +
                    "all day."),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.MAD,
                    wrap = true)
                chatPlayer("But your legs are hanging down!", facialExpression = FacialExpression.LAUGHING, wrap = true)
                chatNpc(message = arrayOf("Go away!"),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.MAD)
                chatPlayer("Please yourself!", facialExpression = FacialExpression.SAD)
            }
            THIRD_OPTION -> {
                chatPlayer("Can I do anything to help?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(message = arrayOf("That's very kind of you. I'd rather like a nice bowl of stew, if you could " +
                    "fetch me one. I don't get many meal breaks."),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.THINK,
                    wrap = true)
                chatPlayer("You want a bowl of stew?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(message = arrayOf("If you wouldn't mind..."),
                    npc = npcId,
                    title = npcName,
                    facialExpression = FacialExpression.CALM_TALK)
                chatPlayer("I'll think about it", facialExpression = FacialExpression.SUSPICIOUS)
            }
        }
    }
}
