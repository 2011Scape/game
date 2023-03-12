package gg.rsmod.plugins.content.areas.falador.dwarven_mines

on_npc_option(npc = Npcs.DWARF_382, option = "talk-to") {
    player.queue {
        mainDialogue(this)
    }
}

suspend fun mainDialogue(it: QueueTask) {
    it.chatNpc("Welcome adventurer to the Mining Guild.", "Can I help you with anything?")
    when(it.options("What have you got in the Guild?", "What do dwarves do with the ore you mine?", "No, thanks. I'm fine.")) {
        1 -> {
            optionOne(it)
        }
        2 -> {
            optionTwo(it)
        }
        3 -> {
            optionThree(it)
        }
    }
}

suspend fun optionOne(it: QueueTask) {
    it.chatPlayer("What have you got in the Guild?")
    it.chatNpc("All sorts of things!", "There's plenty of coal rocks along with some iron,", "mithril and adamantite as well.")
    it.chatNpc("There's no better mining site anywhere!")
    when (it.options("What do dwarves do with the ore you mine?", "No, thanks. I'm fine.")) {
        1 -> {
            optionTwo(it)
        }
        2 -> {
            optionThree(it)
        }
    }
}

suspend fun optionTwo(it: QueueTask) {
    it.chatPlayer("What do you dwarves do with the ore you mine?")
    it.chatNpc("What do you think? We smelt it into bars, smith the", "metal to make armour and weapons, then we exchange", "them for goods and services.")
    it.chatPlayer("I don't see many dwarves", "selling armour or weapons here.")
    it.chatNpc("No, this is only a mining outpost. We dwarves don't", "much like to settle in human cities. Most of the ore is", "carted off to Keldagrim, the great dwarven city.", "They're got a special blast furnace up there - it makes")
    it.chatNpc("smelting the ore so much easier. There are plenty of", "dwarven traders working in Keldagrim. Anyway, can I", "help you with anything else?")
    when (it.options("What have you got in the guild?", "No, thanks. I'm fine.")) {
        1 -> {
            optionOne(it)
        }
        2 -> {
            optionThree(it)
        }
    }
}

suspend fun optionThree(it: QueueTask) {
    it.chatPlayer("No, thanks. I'm fine.")
}
