package gg.rsmod.plugins.content.areas.falador.dwarven_mines

val MINING_LEVEL_REQ = 60

on_obj_option(obj = Objs.DOOR_2112, option = "open") {
    val obj = player.getInteractingGameObj()
    if (player.tile.z > obj.tile.z && player.skills.getMaxLevel(Skills.MINING) < MINING_LEVEL_REQ) {
        player.queue {
            chatNpc("Sorry, but you're not experienced enough to go in there.", npc = Npcs.DWARF_382)
            messageBox("You need a Mining level of $MINING_LEVEL_REQ to access the Mining Guild.")
        }
        return@on_obj_option
    }
    val dynamicObj = DynamicObject(obj)
    val moveX = obj.tile.x
    val moveZ = if (player.tile.z == 9756) 9757 else 9756
    val changedDoorId = -1
    val doorX = obj.tile.x
    val doorZ = obj.tile.z + 1
    val rotation = 2
    val wait = 3
    player.handleTemporaryDoor(
        obj = dynamicObj,
        movePlayerX = moveX,
        movePlayerZ = moveZ,
        newDoorId = changedDoorId,
        moveObjX = doorX,
        moveObjZ = doorZ,
        newRotation = rotation,
        waitTime = wait,
    )
}

on_obj_option(obj = Objs.LADDER_30942, option = "climb-down") {
    player.handleLadder(underground = true)
}

on_obj_option(obj = Objs.LADDER_2113, option = "climb-down") {
    handleMiningGuild(player, climbUp = false)
}

on_obj_option(obj = Objs.LADDER_6226, option = "climb-up") {
    handleMiningGuild(player, climbUp = true)
}

fun handleMiningGuild(
    player: Player,
    climbUp: Boolean,
) {
    if (player.skills.getMaxLevel(Skills.MINING) < MINING_LEVEL_REQ && !climbUp) {
        player.queue {
            chatNpc("Sorry, but you're not experienced enough to go in there.", npc = Npcs.DWARF_3295)
            messageBox("You need a Mining level of $MINING_LEVEL_REQ to access the Mining Guild.")
        }
        return
    }
    player.queue {
        player.animate(828)
        wait(2)
        val zOffset =
            when (climbUp) {
                true -> -6400
                false -> 6400
            }
        player.moveTo(player.tile.x, player.tile.z + zOffset)
    }
}

on_npc_option(npc = Npcs.DWARF_382, option = "talk-to") {
    player.queue {
        mainDialogue(this)
    }
}

suspend fun mainDialogue(it: QueueTask) {
    it.chatNpc("Welcome adventurer to the Mining Guild.", "Can I help you with anything?")
    when (
        it.options(
            "What have you got in the Guild?",
            "What do dwarves do with the ore you mine?",
            "No, thanks. I'm fine.",
        )
    ) {
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
    it.chatNpc(
        *"All sorts of things! There's plenty of coal rocks along with some iron, mithril and adamantite as well."
            .splitForDialogue(),
    )
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
    it.chatNpc(
        *"What do you think? We smelt it into bars, smith the metal to make armour and weapons, then we exchange them for goods and services."
            .splitForDialogue(),
    )
    it.chatPlayer(*"I don't see many dwarves selling armour or weapons here.".splitForDialogue())
    it.chatNpc(
        *"No, this is only a mining outpost. We dwarves don't much like to settle in human cities. Most of the ore is carted off to Keldagrim, the great dwarven city. They're got a special blast furnace up there - it makes"
            .splitForDialogue(),
    )
    it.chatNpc(
        *"smelting the ore so much easier. There are plenty of dwarven traders working in Keldagrim. Anyway, can I help you with anything else?"
            .splitForDialogue(),
    )
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
