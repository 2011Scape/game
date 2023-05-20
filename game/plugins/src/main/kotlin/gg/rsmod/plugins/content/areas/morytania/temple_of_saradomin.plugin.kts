package gg.rsmod.plugins.content.areas.morytania

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PriestInPeril
import gg.rsmod.plugins.content.quests.startedQuest

fun red(text: String) = "<col=8A0808>$text</col>"
fun blue(text: String) = "<col=08088A>$text</col>"

suspend fun KnockAtDoorQuestDialogue(it: QueueTask) {
    it.doubleMessageBox("You knock at the door... You hear a voice from inside. <col=0000EE>Who are you</col>",
        "<col=0000EE>and what do you want?</col>")
    it.chatPlayer("Ummmmm.....")
    when (it.options("Roald sent me to check on Drezel.", "Hi, I just moved in next door...", "I hear this place is of historic interest", "The council sent me to check your pipes.")) {
        1 -> {
            it.chatPlayer("Roald sent me to check on Drezel.")
            it.messageBox5("${blue("(Psst... Hey... Who's Roald? Who's Drezel?)")}${red("(Uh... isn't Drezel that")}",
                            "${red("dude upstairs? Oh, wait, Roald's the King of Varrock right?)")}${blue("He is???")}",
                            "${blue("Aw man... Hey, you deal with this okay?) He's just coming! Wait a")}",
                            "${blue("second!")}${red("Hello, my name is Drevil.")}${blue("(Drezeil!)")}${red("I mean Drezel. How can")}",
                            "${red("I help?")}")
            it.chatPlayer("Well, as I say, the King sent me to make sure",
                                    "everything's okay with you.")
            it.messageBox("${red("And, uh, what would you do if everything wasn't okay with me?")}")
            it.chatPlayer("I'm not sure. Ask you what help you need I suppose.")
            it.doubleMessageBox("${red("Ah, good, well, I don't think...")} ${blue("(Psst... hey... the dog!)")} ${red("OH! Yes, of")}",
                                "${red("course! Will you do me a favour adventurer?")}")
            when (it.options("Sure.", "Nope.")) {
                1 -> {
                    it.chatPlayer("Sure. I'm a helpful person!")
                    it.messageBox4("${red("HAHAHAHA! Really? Thanks buddy! You see that mausoleum out")}",
                                    "${red("there? There's a horrible big dog underneath it that i'd like you to")}",
                                    "${red("kill for me! It's been really bugging me! Barking all the time and")}",
                                    "${red("stuff! Please kill it for me buddy!")}")
                    it.chatPlayer("Okey-dokey, one dead dog coming up.")
                    it.player.advanceToNextStage(PriestInPeril)
                }
                2 -> {
                    it.chatPlayer("No, something about all this is very suspicious.")
                    it.doubleMessageBox("${red("Get lost, then!")}",
                                        "${red("I have important things to do, as sure as my name is Dibzil.")}")
                    it.messageBox("${blue("(Drezel!)")}")
                    it.messageBox("${red("Drezel. Go away!")}")
                }
            }
        }
        2 -> {
            it.chatPlayer("Hello. I just moved in next door...",
                                    "Can I borrow a cup of coffee?")
            it.messageBox("${red("What next door? What's 'coffee'? Who ARE you?")}")
        }
        3 -> {
            it.chatPlayer("I hear this place is of historic interest.",
                                    "Can I come in and have a wander around?",
                                    "Possibly look at some antiques or buy something from your",
                                    "Gift shop?")
            it.messageBox("${red("(Psst... Hey... Is this place of historic interest?")}")

        }
        4 -> {
            it.chatPlayer("The council sent me to check your pipes")
            it.doubleMessageBox("${red("They did? Ummm... (Psst... Hey... ")}",
                        "${red("Are there any pipes in here, you reckon?)")}")
            it.messageBox("${blue("(I don't know. Don't think so.)")}")
            it.messageBox("${red("We don't have any! Thanks. Goodbye!")}")
        }
    }
}

suspend fun KnockAtDoorAfterAgreeingToKillDog(it: QueueTask) {
    it.doubleMessageBox("You knock at the door... You hear a voice from inside. <col=0000EE>Hello?</col>",
        "<col=0000EE>and what do you want?</col>")
    it.chatPlayer("What am I suppose to be doing again?")
    it.messageBox("${red("Who are you?")}")
    it.messageBox4("${blue("Shhh! It's the adventurer!) ")}${red("I want you to kill the")}",
                        "${red("horrible dog in the basement for me. You can use")}",
                        "${red("the entrance in the mausoleum out there. You don't")}",
                        "${red("need to come inside to do it.")}")
    it.messageBox("${red("You'll do this for good old Delzig, won't you, buddy?")}")
    it.messageBox("${blue("(Drezel!)")}")
    it.messageBox("${red("*cough* For good old Drezel. Right, buddy?")}")
}

suspend fun KnockAtDoorAfterKillingDog(it: QueueTask) {
    it.doubleMessageBox("You knock at the door... You hear a voice from inside.",
        "<col=0000EE>You again? What do you want now?</col>")
    it.chatPlayer("I killed that dog for you.")
    it.messageBox("${blue("Really? Hey, that's great!")}")
    it.messageBox("${red("Yeah, thanks a lot, buddy! Hahaha")}")
    it.chatPlayer("What's so funny?")
    it.messageBox("${red("Nothing, buddy. We're just so grateful to you.")}")
    it.doubleMessageBox("${red("Yeah, maybe you should tell the King what a great")}",
        "${red("job you did... 'Buddy'!")}")
}

suspend fun KnockAtDoorDialogue(it: QueueTask) {
    it.doubleMessageBox("You knock at the door... You hear a voice from inside. <col=0000EE>Who are you</col>",
        "<col=0000EE>and what do you want?</col>")
    it.chatPlayer("Ummmmm.....")
    when (it.options("Hi, I just moved in next door...", "I hear this place is of historic interest", "The council sent me to check your pipes.")) {
        1 -> {
            it.chatPlayer("Hello. I just moved in next door...",
                "Can I borrow a cup of coffee?")
            it.messageBox("${red("What next door? What's 'coffee'? Who ARE you?")}")
        }
        2 -> {
            it.chatPlayer("I hear this place is of historic interest.",
                "Can I come in and have a wander around?",
                "Possibly look at some antiques or buy something from your",
                "Gift shop?")
            it.messageBox("${red("(Psst... Hey... Is this place of historic interest?")}")

        }
        3 -> {
            it.chatPlayer("The council sent me to check your pipes")
            it.doubleMessageBox("${red("They did? Ummm... (Psst... Hey... ")}",
                "${red("Are there any pipes in here, you reckon?)")}")
            it.messageBox("${blue("(I don't know. Don't think so.)")}")
            it.messageBox("${red("We don't have any! Thanks. Goodbye!")}")
        }
    }
}

on_obj_option(obj = Objs.TRAPDOOR_30571, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30572, option = "close") {
    close(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30573, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30572, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        3405 -> player.handleLadder(height = 0, underground = true)
        3422 -> player.handleLadder(x = 3440, z = 9887, height = 0, underground = true)
    }
}

on_obj_option(obj = Objs.LADDER_30575, option = "climb-up") {
    player.handleLadder(height = 0, underground = true)
}

on_obj_option(obj = Objs.HOLY_BARRIER, option = "pass-through") {
    player.moveTo(Tile(x = 3423, z = 3484, height = 0))
}

on_obj_option(obj = Objs.GATE_3444, option = "open") {
    handleDoor(player)
}

on_obj_option(obj = Objs.LARGE_DOOR_30707, option = "open") {
    player.queue {
        if (player.startedQuest(PriestInPeril)) {
            when (player.getCurrentStage(PriestInPeril)) {
                1 -> messageBox("The door is securely locked from inside.")
                2 -> messageBox("The door is securely locked from inside.")
                3 -> messageBox("The door is securely locked from inside.")
                //else -> handleDoor(player)
            }
        } else {
            messageBox("The door is securely locked from inside.")
        }
    }
}

on_obj_option(obj = Objs.LARGE_DOOR_30707, option = "knock-at") {
    player.queue {
        when (player.getCurrentStage(PriestInPeril)) {
            1 -> KnockAtDoorQuestDialogue(this)
            2 -> KnockAtDoorAfterAgreeingToKillDog(this)
            3 -> KnockAtDoorAfterKillingDog(this)
            else -> {
                KnockAtDoorDialogue(this)
            }
        }
    }
}

on_obj_option(obj = Objs.LARGE_DOOR_30708, option = "open") {
    player.queue {
        messageBox("The door is securely locked from inside.")
    }
}

on_obj_option(obj = Objs.LARGE_DOOR_30708, option = "knock-at") {
    player.queue {
        when (player.getCurrentStage(PriestInPeril)) {
            1 -> KnockAtDoorQuestDialogue(this)
            2 -> KnockAtDoorAfterAgreeingToKillDog(this)
            3 -> KnockAtDoorAfterKillingDog(this)
            else -> {
                KnockAtDoorDialogue(this)
            }
        }
    }
}

fun handleDoor(player: Player) {
    val closedDoor = DynamicObject(id = Objs.GATE_3444, type = 0, rot = 3, tile = Tile(x = 3405, z = 9895))
    val door = DynamicObject(id = Objs.GATE_3444, type = 0, rot = 2, tile = Tile(x = 3405, z = 9895))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = 3405
        val z = if (player.tile.z >= 9895) 9893 else 9896
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.GATE_3445, option = "open") {
    handleDoor2(player)
}

fun handleDoor2(player: Player) {
    val closedDoor = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 2, tile = Tile(x = 3431, z = 9897))
    val door = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 1, tile = Tile(x = 3431, z = 9897))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = if (player.tile.x >= 3431) 3430 else 3433
        val z = 9897
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

fun close(p: Player, obj: GameObject) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30571))
}

fun open(p: Player, obj: GameObject) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30572))
}