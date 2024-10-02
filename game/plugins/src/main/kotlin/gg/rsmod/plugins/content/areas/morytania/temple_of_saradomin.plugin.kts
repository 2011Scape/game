package gg.rsmod.plugins.content.areas.morytania

import gg.rsmod.game.model.attr.*
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PriestInPeril
import gg.rsmod.plugins.content.quests.startedQuest

fun red(text: String) = "<col=8A0808>$text</col>"

fun blue(text: String) = "<col=08088A>$text</col>"

suspend fun KnockAtDoorQuestDialogue(it: QueueTask) {
    it.doubleMessageBox(
        "You knock at the door... You hear a voice from inside. <col=0000EE>Who are you</col>",
        "<col=0000EE>and what do you want?</col>",
    )
    it.chatPlayer("Ummmmm.....")
    when (
        it.options(
            "Roald sent me to check on Drezel.",
            "Hi, I just moved in next door...",
            "I hear this place is of historic interest",
            "The council sent me to check your pipes.",
        )
    ) {
        1 -> {
            it.chatPlayer("Roald sent me to check on Drezel.")
            it.messageBox5(
                "${blue("(Psst... Hey... Who's Roald? Who's Drezel?)")}${red("(Uh... isn't Drezel that")}",
                "${red("dude upstairs? Oh, wait, Roald's the King of Varrock right?)")}${blue("He is???")}",
                "${blue("Aw man... Hey, you deal with this okay?) He's just coming! Wait a")}",
                "${blue(
                    "second!",
                )}${red("Hello, my name is Drevil.")}${blue("(Drezeil!)")}${red("I mean Drezel. How can")}",
                "${red("I help?")}",
            )
            it.chatPlayer(
                "Well, as I say, the King sent me to make sure",
                "everything's okay with you.",
            )
            it.messageBox("${red("And, uh, what would you do if everything wasn't okay with me?")}")
            it.chatPlayer("I'm not sure. Ask you what help you need I suppose.")
            it.doubleMessageBox(
                "${red("Ah, good, well, I don't think...")} ${blue("(Psst... hey... the dog!)")} ${red("OH! Yes, of")}",
                "${red("course! Will you do me a favour adventurer?")}",
            )
            when (it.options("Sure.", "Nope.")) {
                1 -> {
                    it.chatPlayer("Sure. I'm a helpful person!")
                    it.messageBox4(
                        "${red("HAHAHAHA! Really? Thanks buddy! You see that mausoleum out")}",
                        "${red("there? There's a horrible big dog underneath it that i'd like you to")}",
                        "${red("kill for me! It's been really bugging me! Barking all the time and")}",
                        "${red("stuff! Please kill it for me buddy!")}",
                    )
                    it.chatPlayer("Okey-dokey, one dead dog coming up.")
                    it.player.advanceToNextStage(PriestInPeril)
                }
                2 -> {
                    it.chatPlayer("No, something about all this is very suspicious.")
                    it.doubleMessageBox(
                        "${red("Get lost, then!")}",
                        "${red("I have important things to do, as sure as my name is Dibzil.")}",
                    )
                    it.messageBox("${blue("(Drezel!)")}")
                    it.messageBox("${red("Drezel. Go away!")}")
                }
            }
        }
        2 -> {
            it.chatPlayer(
                "Hello. I just moved in next door...",
                "Can I borrow a cup of coffee?",
            )
            it.messageBox("${red("What next door? What's 'coffee'? Who ARE you?")}")
        }
        3 -> {
            it.chatPlayer(
                "I hear this place is of historic interest.",
                "Can I come in and have a wander around?",
                "Possibly look at some antiques or buy something from your",
                "Gift shop?",
            )
            it.messageBox("${red("(Psst... Hey... Is this place of historic interest?")}")
        }
        4 -> {
            it.chatPlayer("The council sent me to check your pipes")
            it.doubleMessageBox(
                "${red("They did? Ummm... (Psst... Hey... ")}",
                "${red("Are there any pipes in here, you reckon?)")}",
            )
            it.messageBox("${blue("(I don't know. Don't think so.)")}")
            it.messageBox("${red("We don't have any! Thanks. Goodbye!")}")
        }
    }
}

suspend fun KnockAtDoorAfterAgreeingToKillDog(it: QueueTask) {
    it.doubleMessageBox(
        "You knock at the door... You hear a voice from inside. <col=0000EE>Hello?</col>",
        "<col=0000EE>and what do you want?</col>",
    )
    it.chatPlayer("What am I suppose to be doing again?")
    it.messageBox("${red("Who are you?")}")
    it.messageBox4(
        "${blue("Shhh! It's the adventurer!) ")}${red("I want you to kill the")}",
        "${red("horrible dog in the basement for me. You can use")}",
        "${red("the entrance in the mausoleum out there. You don't")}",
        "${red("need to come inside to do it.")}",
    )
    it.messageBox("${red("You'll do this for good old Delzig, won't you, buddy?")}")
    it.messageBox("${blue("(Drezel!)")}")
    it.messageBox("${red("*cough* For good old Drezel. Right, buddy?")}")
}

suspend fun KnockAtDoorAfterKillingDog(it: QueueTask) {
    it.doubleMessageBox(
        "You knock at the door... You hear a voice from inside.",
        "<col=0000EE>You again? What do you want now?</col>",
    )
    it.chatPlayer("I killed that dog for you.")
    it.messageBox("${blue("Really? Hey, that's great!")}")
    it.messageBox("${red("Yeah, thanks a lot, buddy! Hahaha")}")
    it.chatPlayer("What's so funny?")
    it.messageBox("${red("Nothing, buddy. We're just so grateful to you.")}")
    it.doubleMessageBox(
        "${red("Yeah, maybe you should tell the King what a great")}",
        "${red("job you did... 'Buddy'!")}",
    )
}

suspend fun KnockAtDoorDialogue(it: QueueTask) {
    it.doubleMessageBox(
        "You knock at the door... You hear a voice from inside. <col=0000EE>Who are you</col>",
        "<col=0000EE>and what do you want?</col>",
    )
    it.chatPlayer("Ummmmm.....")
    when (
        it.options(
            "Hi, I just moved in next door...",
            "I hear this place is of historic interest",
            "The council sent me to check your pipes.",
        )
    ) {
        1 -> {
            it.chatPlayer(
                "Hello. I just moved in next door...",
                "Can I borrow a cup of coffee?",
            )
            it.messageBox("${red("What next door? What's 'coffee'? Who ARE you?")}")
        }
        2 -> {
            it.chatPlayer(
                "I hear this place is of historic interest.",
                "Can I come in and have a wander around?",
                "Possibly look at some antiques or buy something from your",
                "Gift shop?",
            )
            it.messageBox("${red("(Psst... Hey... Is this place of historic interest?")}")
        }
        3 -> {
            it.chatPlayer("The council sent me to check your pipes")
            it.doubleMessageBox(
                "${red("They did? Ummm... (Psst... Hey... ")}",
                "${red("Are there any pipes in here, you reckon?)")}",
            )
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

on_obj_option(obj = Objs.WELL_3485, option = "search") {
    player.queue {
        doubleMessageBox(
            "You look down the well and see the filthy polluted water",
            "of the River Salve moving slowly along.",
        )
    }
}

on_item_on_obj(item = Items.BUCKET, obj = Objs.WELL_3485) {
    if (player.inventory.contains(item = Items.BUCKET)) {
        player.queue {
            if (player.inventory.remove(Items.BUCKET).hasSucceeded()) {
                player.animate(id = 832)
                player.playSound(Sfx.FIRE_DOOR_PASS)
                player.inventory.add(Items.BUCKET_OF_WATER_2953)
                player.filterableMessage("You fill the bucket with murky water from the well.")
                wait(2)
            }
        }
    }
}

on_item_on_obj(item = Items.BUCKET_OF_WATER_2954, obj = Objs.MORYTANIA_COFFIN) {
    if (player.inventory.contains(item = Items.BUCKET_OF_WATER_2954)) {
        player.queue {
            if (player.inventory.remove(Items.BUCKET_OF_WATER_2954).hasSucceeded()) {
                player.animate(id = 12643)
                player.playSound(Sfx.WATERSPLASH)
                player.inventory.add(Items.BUCKET)
                player.filterableMessage("You pour the blessed water over the coffin...")
                player.advanceToNextStage(PriestInPeril)
                wait(2)
            }
        }
    }
}

on_obj_option(obj = Objs.LARGE_DOOR_30707, option = "open") {
    player.queue {
        if (player.startedQuest(PriestInPeril)) {
            when (player.getCurrentStage(PriestInPeril)) {
                1 -> messageBox("The door is securely locked from inside.")
                2 -> messageBox("The door is securely locked from inside.")
                3 -> messageBox("The door is securely locked from inside.")
                else -> openLargeDoors(player)
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
        if (player.startedQuest(PriestInPeril)) {
            when (player.getCurrentStage(PriestInPeril)) {
                1 -> messageBox("The door is securely locked from inside.")
                2 -> messageBox("The door is securely locked from inside.")
                3 -> messageBox("The door is securely locked from inside.")
                else -> openLargeDoors(player)
            }
        } else {
            messageBox("The door is securely locked from inside.")
        }
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
    val invisibleDoor = DynamicObject(id = Objs.GATE_3444, type = 1, rot = 3, tile = Tile(x = 3405, z = 9895))
    val door = DynamicObject(id = Objs.GATE_3444, type = 0, rot = 0, tile = Tile(x = 3405, z = 9894))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    world.spawn(invisibleDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = 3405
        val z = if (player.tile.z >= 9895) 9893 else 9896
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        world.remove(invisibleDoor)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

fun handleDoor2(player: Player) {
    val closedDoor = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 2, tile = Tile(x = 3431, z = 9897))
    val invisibleDoor = DynamicObject(id = Objs.GATE_3445, type = 1, rot = 2, tile = Tile(x = 3431, z = 9897))
    val door = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 3, tile = Tile(x = 3432, z = 9897))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    world.spawn(invisibleDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = if (player.tile.x >= 3432) 3430 else 3433
        val z = 9897
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        world.remove(invisibleDoor)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.GATE_3444, option = "open") {
    if (player.getCurrentStage(quest = PriestInPeril) >= 5) {
        handleDoor(player)
    } else {
        player.queue {
            messageBox("The door is securely locked from inside.")
            chatPlayer(
                "Hmmm... From the looks of things, it seems as though",
                "somebody has been trying to force this gate open.",
                "It's still securely locked however.",
            )
        }
    }
}

on_obj_option(obj = Objs.GATE_3445, option = "open") {
    if (player.getCurrentStage(quest = PriestInPeril) >= 7) {
        handleDoor2(player)
    } else {
        player.queue {
            messageBox("The door is securely locked from inside.")
            chatPlayer(
                "Hmmm... From the looks of things, it seems as though",
                "somebody has been trying to force this gate open.",
                "It's still securely locked however.",
            )
        }
    }
}

fun openLargeDoors(player: Player) {
    val northOriginalGate = DynamicObject(id = 30707, type = 0, rot = 2, tile = Tile(x = 3406, z = 3489))
    val southOriginalGate = DynamicObject(id = 30708, type = 0, rot = 2, tile = Tile(x = 3406, z = 3488))

    val northGate = DynamicObject(id = 30709, type = 0, rot = 1, tile = Tile(x = 3406, z = 3489))
    val southGate = DynamicObject(id = 30710, type = 0, rot = 3, tile = Tile(x = 3406, z = 3488))

    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        world.remove(southOriginalGate)
        world.remove(northOriginalGate)

        player.playSound(Sfx.DOOR_OPEN)
        world.spawn(northGate)
        world.spawn(southGate)
        val x = if (player.tile.x <= 3406) 3407 else 3406
        val z = if (player.tile.z >= 3489) 3489 else 3488
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(northGate)
        world.remove(southGate)
        world.spawn(northOriginalGate)
        world.spawn(southOriginalGate)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.LADDER_30489, option = "climb-up") {
    player.handleLadder(height = 2)
}

on_obj_option(obj = Objs.LADDER_30733, option = "climb-down") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.STAIRCASE_30724, option = "climb-up") {
    when (player.tile.height) {
        0 -> { // Second Floor
            player.moveTo(3415, player.tile.z, height = 1)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_30722, option = "climb-up") {
    when (player.tile.height) {
        0 -> { // Second Floor
            player.moveTo(3415, player.tile.z, height = 1)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_TOP, option = "climb-down") {
    when (player.tile.height) {
        1 -> { // Second Floor
            player.moveTo(3414, player.tile.z, height = 0)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_TOP_30725, option = "climb-down") {
    when (player.tile.height) {
        1 -> { // Second Floor
            player.moveTo(3414, player.tile.z, height = 0)
        }
    }
}

fun close(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30571))
}

fun open(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30572))
}

// Unlock Drezel's cell door with Iron key.
on_item_on_obj(item = Items.IRON_KEY, obj = Objs.CELL_DOOR_3463) {
    player.queue {
        player.message("You unlock the cell door.")
        player.advanceToNextStage(PriestInPeril)
        player.inventory.remove(item = Items.IRON_KEY)
        chatNpc("Oh, Thank you! You have found the key!", npc = 1049)
    }
}

// Grave Monuments
val graveMonuments =
    intArrayOf(
        Objs.MONUMENT,
        Objs.MONUMENT_3494,
        Objs.MONUMENT_3495,
        Objs.MONUMENT_3496,
        Objs.MONUMENT_3497,
        Objs.MONUMENT_3498,
        Objs.MONUMENT_3499,
    )

graveMonuments.forEach {
    on_obj_option(it, option = "take-from") {
        val takeDamage = (player.getCurrentLifepoints() * 0.01)
        player.animate(id = 3864)
        player.dealHit(
            target = player,
            minHit = takeDamage,
            maxHit = takeDamage + 0.01,
            landHit = true,
            delay = 1,
            hitType = HitType.REGULAR_HIT,
        )
        player.message("A powerful force prevents you from taking the key.")
    }
}

// Golden Pot
on_obj_option(obj = Objs.MONUMENT, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_POT] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.EMPTY_POT, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the vessel that keeps us safe from harm.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_POT, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the vessel that keeps us safe from harm.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT, item = Items.EMPTY_POT) {
    if (player.hasItem(Items.EMPTY_POT)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.EMPTY_POT).hasSucceeded()) {
            player.inventory.remove(item = Items.GOLDEN_POT)
            player.message("You swap the Empty pot for the Golden pot.")
            player.attr.put(SWAPPED_GOLDEN_POT, true)
        }
    }
}

// Golden Candle
on_obj_option(obj = Objs.MONUMENT_3499, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_CANDLE] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.CANDLE, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the light that shines throughout our lives.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_CANDLE, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the light that shines throughout our lives.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3499, item = Items.CANDLE) {
    if (player.hasItem(Items.CANDLE)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.CANDLE).hasSucceeded()) {
            player.inventory.add(item = Items.GOLDEN_CANDLE)
            player.message("You swap the Candle for the Golden candle.")
            player.attr.put(SWAPPED_GOLDEN_CANDLE, true)
        }
    }
}

// Golden Hammer
on_obj_option(obj = Objs.MONUMENT_3494, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_HAMMER] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.HAMMER, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the hammer that crushes evil everywhere.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_HAMMER, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the hammer that crushes evil everywhere.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3494, item = Items.HAMMER) {
    if (player.hasItem(Items.HAMMER)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.HAMMER).hasSucceeded()) {
            player.inventory.add(item = Items.GOLDEN_HAMMER)
            player.message("You swap the Hammer for the Golden hammer.")
            player.attr.put(SWAPPED_GOLDEN_HAMMER, true)
        }
    }
}

// Golden Key
on_obj_option(obj = Objs.MONUMENT_3497, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_KEY] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_KEY, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the key that unlocks the mysteries of life.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.IRON_KEY, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the key that unlocks the mysteries of life.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3497, item = Items.GOLDEN_KEY) {
    if (player.hasItem(Items.GOLDEN_KEY)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.GOLDEN_KEY).hasSucceeded()) {
            player.inventory.add(item = Items.IRON_KEY)
            player.message("You swap the Golden key for the Iron key.")
            player.attr.put(SWAPPED_GOLDEN_KEY, true)
        }
    }
}

// Golden Tinderbox
on_obj_option(obj = Objs.MONUMENT_3495, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_TINDERBOX] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.TINDERBOX_590, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the spark that lights the fire in our hearts.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_TINDERBOX, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the spark that lights the fire in our hearts.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3495, item = Items.TINDERBOX_590) {
    if (player.hasItem(Items.TINDERBOX_590)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.TINDERBOX_590).hasSucceeded()) {
            player.inventory.add(item = Items.GOLDEN_TINDERBOX)
            player.message("You swap the Tinderbox for the Golden tinderbox.")
            player.attr.put(SWAPPED_GOLDEN_TINDERBOX, true)
        }
    }
}

// Golden Needle
on_obj_option(obj = Objs.MONUMENT_3498, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_NEEDLE] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.NEEDLE, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the needle that binds our lives together.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_NEEDLE, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the needle that binds our lives together.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3498, item = Items.NEEDLE) {
    if (player.hasItem(Items.NEEDLE)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.NEEDLE).hasSucceeded()) {
            player.inventory.add(item = Items.GOLDEN_NEEDLE)
            player.message("You swap the Needle for the Golden needle.")
            player.attr.put(SWAPPED_GOLDEN_NEEDLE, true)
        }
    }
}

// Golden Feather
on_obj_option(obj = Objs.MONUMENT_3496, option = "study") {
    if (player.attr[SWAPPED_GOLDEN_FEATHER] == true) {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.FEATHER, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the delicate touch that brushes us with love.")
    } else {
        player.openInterface(interfaceId = 272, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentItem(interfaceId = 272, 4, item = Items.GOLDEN_FEATHER, amountOrZoom = 1)
        player.setComponentText(interfaceId = 272, 17, "Saradomin is the delicate touch that brushes us with love.")
    }
}

on_item_on_obj(obj = Objs.MONUMENT_3496, item = Items.FEATHER) {
    if (player.hasItem(Items.FEATHER)) {
        player.animate(id = 3864)
        if (player.inventory.remove(Items.FEATHER).hasSucceeded()) {
            player.inventory.add(item = Items.GOLDEN_FEATHER)
            player.message("You swap the Feather for the Golden feather.")
            player.attr.put(SWAPPED_GOLDEN_FEATHER, true)
        }
    }
}
