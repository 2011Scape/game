package gg.rsmod.plugins.content.areas.barbarianvillage

import gg.rsmod.game.model.attr.STRONGHOLD_OF_SECURITY
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

/**
 * Stronghold of Security
 * @author Harley<github.com/HarleyGilpin>
 */

/**
 * Security Questions
 */
enum class SecurityQuestion(val question: String, val options: List<String>, val correctAnswer: Int) {
    QUESTION_1(
        "A website claims that they can make me a player moderator if I give them my password. What should I do?",
        listOf(
            "Immediately provide them with your password.",
            "Share your password and ask them to also boost your stats.",
            "Give them your password and ask if they need your bank PIN too.",
            "Don't tell them anything and inform 2011Scape staff."
        ),
        4
    ),
    QUESTION_2(
        "Can I leave my account logged in while I'm out of the room?",
        listOf(
            "No.",
            "Yes, and leave a note with your password for anyone passing by.",
            "Of course, it gives others a chance to experience your account.",
            "Always! I need more loyalty points."
        ),
        1
    ),
    QUESTION_3(
        "My friend uses this great add-on program he got from a website, should I?",
        listOf(
            "Download it immediately, especially if the site looks suspicious.",
            "No, it might steal my password.",
            "Use your friend's computer to play RuneScape to test it out first.",
            "Download it and share it with everyone you know."
        ),
        2
    ),
    QUESTION_4(
        "My friend asks me for my password so that he can do a difficult quest for me.",
        listOf(
            "Don't give him my password.",
            "Share your password and also give him your credit card information as a sign of trust.",
            "Tell him your password and ask him to also organize your bank.",
            "Write your password down on paper and mail it to him for extra security."
        ),
        1
    ),
    QUESTION_5(
        "Recovery answers should be...",
        listOf(
            "The same as your password for consistency.",
            "Common knowledge like your favorite game, RuneScape.",
            "Memorable.",
            "Shared with friends so they can help you recover your account."
        ),
        3
    ),
    QUESTION_6(
        "Recovery answers should be...",
        listOf(
            "The same as your password for consistency.",
            "Common knowledge like your favorite game, RuneScape.",
            "Memorable.",
            "Shared with friends so they can help you recover your account."
        ),
        3
    ),
    QUESTION_7(
        "What are your recovery questions used for?",
        listOf(
            "For sharing with friends at parties.",
            "To use as your public bio on social media.",
            "To use as a fun trivia game.",
            "To recover my account if i don't remember my password."
        ),
        4
    ),
    QUESTION_8(
        "What is a good example of a Bank PIN?",
        listOf(
            "0000",
            "The birthday of a famous person or event.",
            "1234",
            "The current year."
        ),
        2
    ),
    QUESTION_9(
        "What do you do if someone asks you for your password or recoveries to make you a member for free?",
        listOf(
            "Don't tell them anything and click the Report Abuse button.",
            "Give them the information they asked for.",
            "Don't tell them anything and ignore them.",
            "Send it to them if they are moderator."
        ),
        1
    ),
    QUESTION_10(
        "What do you do if someone asks you for your password or recoveries to make you a player moderator?",
        listOf(
            "Share it during a live stream.",
            "Make it your new in-game name.",
            "Don't tell them anything and click the Report Abuse button.",
            "Organize a party and announce it there."
        ),
        3
    ),
    QUESTION_11(
        "What do you do if someone tells you that you have won the RuneScape lottery and asks you for your password and recoveries to award your prize?",
        listOf(
            "Don't tell them anything and click the Report Abuse button.",
            "Post it in public chat.",
            "Email it to them.",
            "Share it with them immediately."
        ),
        1
    ),
    QUESTION_12(
        "What do I do if I think I have a keylogger or a virus?",
        listOf(
            "Download more suspicious software to counter it.",
            "Share the suspected file with all your friends.",
            "Ignore it, they usually go away on their own.",
            "Virus scan my computer then change my password and recoveries."
        ),
        4
    ),
    QUESTION_13(
        "What do I do if I think I have a keylogger or a virus?",
        listOf(
            "Download more suspicious software to counter it.",
            "Share the suspected file with all your friends.",
            "Ignore it, they usually go away on their own.",
            "Virus scan my computer then change my password and recoveries."
        ),
        4
    ),
    QUESTION_14(
        "What do I do if a moderator asks me for my account details?",
        listOf(
            "Provide the details, since moderators are trusted individuals.",
            "Politely tell them no then use the report abuse button.",
            "Make a new password first so your real password is secure.",
            "Email them directly."
        ),
        2
    ),
    QUESTION_15(
        "What should I do if I think someone knows my recoveries?",
        listOf(
            "Use the recover a lost password section.",
            "Do nothing; they probably won't use them anyway.",
            "Share the recoveries with more people to make them less unique.",
            "Use the same recovery questions for all your accounts."
        ),
        1
    ),
    QUESTION_16(
        "Where can I find cheats for RuneScape?",
        listOf(
            "In the official RuneScape guidebook.",
            "By messaging the top players on the high scores.",
            "On unofficial RuneScape forums that promise quick leveling.",
            "Nowhere."
        ),
        4
    ),
    QUESTION_17(
        "Where should I enter my password for RuneScape?",
        listOf(
            "On any pop-up that asks for it.",
            "Only on the RuneScape website.",
            "On third-party websites promising free in-game items.",
            "In response to email requests claiming to be from Jagex."
        ),
        2
    ),
    QUESTION_18(
        "Will 2011Scape block me for saying my PIN in-game?",
        listOf(
            "No, saying your PIN in-game is a method of two-factor authentication.",
            "Only if you say it backwards, otherwise it's fine.",
            "No, they encourage sharing it for community trust building.",
            "No."
        ),
        4
    ),
    QUESTION_19(
        "Who can I give my password to?",
        listOf(
            "My friends",
            "Members of my clan",
            "Nobody.",
            "My brother or sister"
        ),
        3
    ),
    QUESTION_20(
        "Why do I need to type in recovery questions?",
        listOf(
            "To provide hints for players who want to guess your password.",
            "To personalize your character's backstory in the game.",
            "As a secondary password when accessing high-risk areas in the game.",
            "To help me recover my password if I forget it or if it is stolen."
        ),
        4
    )
    ;
}

/**
 * Safe areas: indicate corridors that are safe to ask player security questions.
 */
enum class SafeArea(val northEast: Pair<Int, Int>, val southWest: Pair<Int, Int>) {
    //Floor 1 - Vault of War
    SAFE_AREA_1(Pair(1859, 5237), Pair(1858, 5236)),
    SAFE_AREA_2(Pair(1867, 5227), Pair(1865, 5226)),
    SAFE_AREA_3(Pair(1869, 5218), Pair(1867, 5217)),
    SAFE_AREA_4(Pair(1861, 5211), Pair(1860, 5210)),
    SAFE_AREA_5(Pair(1878, 5240), Pair(1876, 5239)),
    SAFE_AREA_6(Pair(1886, 5244), Pair(1884, 5243)),
    SAFE_AREA_7(Pair(1907, 5243), Pair(1904, 5242)),
    SAFE_AREA_8(Pair(1905, 5232), Pair(1904, 5231)),
    SAFE_AREA_9(Pair(1888, 5236), Pair(1886, 5235)),
    SAFE_AREA_10(Pair(1879, 5225), Pair(1878, 5224)),
    SAFE_AREA_11(Pair(1875, 5207), Pair(1874, 5205)),
    SAFE_AREA_12(Pair(1877, 5194), Pair(1876, 5192)),
    SAFE_AREA_13(Pair(1890, 5210), Pair(1889, 5208)),
    SAFE_AREA_14(Pair(1896, 5213), Pair(1894, 5212)),
    SAFE_AREA_15(Pair(1906, 5204), Pair(1904, 5203)),
    SAFE_AREA_16(Pair(1912, 5209), Pair(1911, 5207)),
    SAFE_AREA_17(Pair(1861, 5197), Pair(1860, 5196)),
    SAFE_AREA_18(Pair(1881, 5189), Pair(1879, 5188)),
    //Floor 2 - Catacomb of Famine
    SAFE_AREA_19(Pair(2039, 5245), Pair(2037, 5244)),
    SAFE_AREA_20(Pair(2045, 5239), Pair(2044, 5237)),
    SAFE_AREA_21(Pair(2042, 5223), Pair(2040, 5222)),
    SAFE_AREA_22(Pair(2032, 5227), Pair(2031, 5225)),
    SAFE_AREA_23(Pair(2027, 5241), Pair(2026, 5239)),
    SAFE_AREA_24(Pair(2020, 5242), Pair(2019, 5240)),
    SAFE_AREA_25(Pair(2014, 5242), Pair(2013, 5240)),
    SAFE_AREA_26(Pair(2006, 5237), Pair(2005, 5235)),
    SAFE_AREA_27(Pair(1999, 5216), Pair(1997, 5215)),
    SAFE_AREA_28(Pair(2008, 5216), Pair(2006, 5215)),
    SAFE_AREA_29(Pair(1995, 5196), Pair(1994, 5194)),
    SAFE_AREA_30(Pair(2005, 5194), Pair(2004, 5192)),
    SAFE_AREA_31(Pair(2021, 5202), Pair(2020, 5200)),
    SAFE_AREA_32(Pair(2032, 5198), Pair(2031, 5196)),
    SAFE_AREA_33(Pair(2036, 5186), Pair(2034, 5185)),
    SAFE_AREA_34(Pair(2046, 5197), Pair(2045, 5195)),
    SAFE_AREA_35(Pair(2037, 5203), Pair(2036, 5201)),
    SAFE_AREA_36(Pair(2034, 5210), Pair(2033, 5208)),
    SAFE_AREA_37(Pair(2018, 5228), Pair(2016, 5227)),
    //Floor 3 - Pit of Pestilence
    SAFE_AREA_38(Pair(2133, 5259), Pair(2132, 5257)),
    SAFE_AREA_39(Pair(2140, 5263), Pair(2138, 5262)),
    SAFE_AREA_40(Pair(2156, 5264), Pair(2154, 5263)),
    SAFE_AREA_41(Pair(2167, 5261), Pair(2166, 5260)),
    SAFE_AREA_42(Pair(2170, 5272), Pair(2168, 5271)),
    SAFE_AREA_43(Pair(2164, 5277), Pair(2163, 5275)),
    SAFE_AREA_44(Pair(2163, 5289), Pair(2162, 5287)),
    SAFE_AREA_45(Pair(2156, 5288), Pair(2155, 5286)),
    SAFE_AREA_46(Pair(2168, 5296), Pair(2167, 5294)),
    SAFE_AREA_47(Pair(2156, 5288), Pair(2155, 5286)),
    SAFE_AREA_48(Pair(2152, 5292), Pair(2149, 5291)),
    SAFE_AREA_49(Pair(2149, 5301), Pair(2148, 5299)),
    SAFE_AREA_50(Pair(2140, 5295), Pair(2138, 5294)),
    SAFE_AREA_51(Pair(2131, 5295), Pair(2130, 5293)),
    SAFE_AREA_52(Pair(2126, 5288), Pair(2124, 5287)),
    //Floor 4 - The Sepulchre of Death
    SAFE_AREA_54(Pair(2310, 5225), Pair(2308, 5224)),
    SAFE_AREA_55(Pair(2320, 5215), Pair(2319, 5213)),
    SAFE_AREA_56(Pair(2335, 5227), Pair(2333, 5226)),
    SAFE_AREA_57(Pair(2324, 5242), Pair(2323, 5240)),
    SAFE_AREA_58(Pair(2335, 5238), Pair(2333, 5237)),
    SAFE_AREA_59(Pair(2355, 5246), Pair(2353, 5245)),
    SAFE_AREA_60(Pair(2360, 5234), Pair(2359, 5232)),
    SAFE_AREA_61(Pair(2356, 5221), Pair(2355, 5219)),
    SAFE_AREA_62(Pair(2366, 5221), Pair(2365, 5219)),
    SAFE_AREA_63(Pair(2362, 5206), Pair(2361, 5204)),
    SAFE_AREA_64(Pair(2362, 5194), Pair(2360, 5193)),
    SAFE_AREA_65(Pair(2361, 5189), Pair(2359, 5188)),
    SAFE_AREA_66(Pair(2346, 5188), Pair(2344, 5187)),
    SAFE_AREA_67(Pair(2335, 5194), Pair(2333, 5193)),
    SAFE_AREA_68(Pair(2324, 5190), Pair(2323, 5188)),
    SAFE_AREA_69(Pair(2317, 5187), Pair(2315, 5186)),
    SAFE_AREA_70(Pair(2311, 5205), Pair(2309, 5204)),
    SAFE_AREA_71(Pair(2341, 5224), Pair(2340, 5222));

fun isInArea(x: Int, z: Int): Boolean {
    return x in southWest.first..northEast.first && z in southWest.second..northEast.second
    }
}

fun Player.setStrongholdOfSecurity(floor: Int) {
    attr[STRONGHOLD_OF_SECURITY] = floor
}

fun Player.getStrongholdOfSecurity(): Int {
    val lastFloor = attr[STRONGHOLD_OF_SECURITY]
    if (lastFloor == null) {
        setStrongholdOfSecurity(0)
        return getStrongholdOfSecurity()
    }
    return lastFloor
}

fun completeFloor(player: Player, newFloor: Int) {
    val floor = player.getStrongholdOfSecurity()
    if (floor + 1 == newFloor)
        player.setStrongholdOfSecurity(newFloor)
}

suspend fun gateSecurityQuestion(it: QueueTask, obj: GameObject, player: Player, npcId: Int) {
    val floorsCompleted = player.getStrongholdOfSecurity()
    val isInSafeArea = SafeArea.values().any { it.isInArea(player.tile.x, player.tile.z) }
    val completedVault = player.tile.regionId == 7505 && floorsCompleted > 0
    val completedCatacomb = player.tile.regionId == 8017 && floorsCompleted > 1
    val completedPit = player.tile.regionId == 8530 && floorsCompleted > 2
    val completedSepulchre = player.tile.regionId == 9297 && floorsCompleted > 3

    if (isInSafeArea && !completedVault && !completedCatacomb && !completedPit && !completedSepulchre) {
        val selectedQuestion = SecurityQuestion.values().random()
        it.chatNpc(
            "To pass you must answer me this: ${selectedQuestion.question}",
            npc = npcId,
            facialExpression = FacialExpression.SILENT,
            wrap = true
        )

        val userAnswer = it.options(*selectedQuestion.options.toTypedArray())
        when (userAnswer) {
            selectedQuestion.correctAnswer -> {
                it.player.lock()
                it.player.animate(4282)
                it.player.message("You answered the question correct.")
                it.player.playSound(Sfx.DISARM_TRAP)
                it.wait(3)

                val newX = when {
                    player.tile.x < obj.tile.x -> player.tile.x + 2
                    player.tile.x > obj.tile.x -> player.tile.x - 2
                    else -> player.tile.x
                }

                val newZ = when {
                    player.tile.z < obj.tile.z -> player.tile.z + 2
                    player.tile.z > obj.tile.z -> player.tile.z - 2
                    else -> player.tile.z
                }

                it.player.teleportTo(
                    x = newX,
                    z = newZ,
                    height = player.tile.height
                )
                it.wait(1)
                it.player.animate(4283)
                it.player.unlock()
            }
            else -> {
                it.player.playSound(Sfx.DISARM_TRAP_FAILURE)
                it.player.message("The answer you gave was incorrect.")
            }
        }
    } else {
        it.player.lock()
        // Logic to let the player pass through the gate when not in a safe area
        it.player.animate(4282, idleOnly = true)
        it.player.playSound(Sfx.DISARM_TRAP)
        it.wait(3)

        val newX = when {
            player.tile.x < obj.tile.x -> player.tile.x + 2
            player.tile.x > obj.tile.x -> player.tile.x - 2
            else -> player.tile.x
        }

        val newZ = when {
            player.tile.z < obj.tile.z -> player.tile.z + 2
            player.tile.z > obj.tile.z -> player.tile.z - 2
            else -> player.tile.z
        }

        it.player.teleportTo(
            x = newX,
            z = newZ,
            height = player.tile.height
        )
        it.wait(1)
        it.player.animate(4283)
        it.player.unlock()
    }
}

/**
 * Handle objects in the Stronghold of Security
 */

on_obj_option(obj = Objs.DEAD_EXPLORER, option = "search") {
    if (!player.hasItem(Items.STRONGHOLD_NOTES) && player.inventory.hasSpace) {
        player.inventory.add(Items.STRONGHOLD_NOTES)
        player.animate(881)
    }
}

/**
 * Ladders
 */

//Entrance
on_obj_option(obj = Objs.ENTRANCE_16154, option = "climb-down") {
    player.handleLadder(1860, 5244, 0)
}

//Floor 1 - Vault of War
on_obj_option(obj = Objs.LADDER_16148, option = "climb-up") {
    player.handleLadder(3081, 3421, 0)
}

on_obj_option(obj = Objs.SPIKEY_CHAIN_16146, option = "climb-up") {
    player.handleLadder(1860, 5244, 0)
}

on_obj_option(obj = Objs.LADDER_16149, option = "climb-down") {
    if (player.getStrongholdOfSecurity() < 1) {
        player.queue {
            messageBox("You must collect your reward before continuing.")
        }
    } else {
        player.openInterface(interfaceId = 579, dest = InterfaceDestination.MAIN_SCREEN)
    }
}

//Floor 2 - The Catacomb of Famine
on_obj_option(obj = Objs.LADDER_16080, option = "climb-up") {
    player.handleLadder(1902, 5223, 0)
}

on_obj_option(obj = Objs.ROPE_16078, option = "climb-up") {
    player.handleLadder(2042, 5245, 0)
}

on_obj_option(obj = Objs.LADDER_16081, option = "climb-down") {
    if (player.getStrongholdOfSecurity() < 2) {
        player.queue {
            messageBox("You must collect your reward before continuing.")
        }
    } else {
        player.openInterface(interfaceId = 579, dest = InterfaceDestination.MAIN_SCREEN)
    }
}

//Floor 3 - The Pit of Pestilence
on_obj_option(obj = Objs.DRIPPING_VINE, option = "climb-up") {
    player.handleLadder(2026, 5219, 0)
}

on_obj_option(obj = Objs.GOO_COVERED_VINE, option = "climb-up") {
    player.handleLadder(2122, 5251, 0)
}

on_obj_option(obj = Objs.DRIPPING_VINE_16115, option = "climb-down") {
    if (player.getStrongholdOfSecurity() < 3) {
        player.queue {
            messageBox("You must collect your reward before continuing.")
        }
    } else {
        player.openInterface(interfaceId = 579, dest = InterfaceDestination.MAIN_SCREEN)
    }
}

//Floor 4 - Death
on_obj_option(obj = Objs.BONEY_LADDER, option = "climb-up") {
    player.handleLadder(2147, 5284, 0)
}

on_obj_option(obj = Objs.BONE_CHAIN, option = "climb-up") {
    player.handleLadder(2147, 5284, 0)
}

//Warning Sign: "Are you sure you want to climb down?"
on_button(interfaceId = 579, component = 17) {
    when (player.tile.regionId) {
        7505 -> player.handleLadder(2042, 5245, 0)
        8017 -> player.handleLadder(2122, 5251, 0)
        8530 -> player.handleLadder(2358, 5215, 0)
    }
    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
    return@on_button
}

on_button(interfaceId = 579, component = 18) {
    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
    return@on_button
}

/**
 * Portals
 */

on_obj_option(obj = Objs.PORTAL_16150, option = "enter") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    if (floorsCompleted > 0) {
        player.teleport(Tile(1907, 5220, 0), TeleportType.MODERN)
    } else {
        player.filterableMessage("A magical force prevents you from entering the portal.")
    }
}

on_obj_option(obj = Objs.PORTAL_16082, option = "enter") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    if (floorsCompleted > 1) {
        player.teleport(Tile(2022, 5219, 0), TeleportType.MODERN)
    } else {
        player.filterableMessage("A magical force prevents you from entering the portal.")
    }
}

on_obj_option(obj = Objs.PORTAL_16116, option = "enter") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    if (floorsCompleted > 2) {
        player.teleport(Tile(2146, 5289, 0), TeleportType.MODERN)
    } else {
        player.filterableMessage("A magical force prevents you from entering the portal.")
    }
}

on_obj_option(obj = Objs.PORTAL_16050, option = "enter") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    if (floorsCompleted > 3) {
        player.teleport(Tile(2341, 5218, 0), TeleportType.MODERN)
    } else {
        player.filterableMessage("A magical force prevents you from entering the portal.")
    }
}

/**
 * Security Gates
 */

listOf(Objs.GATE_OF_WAR, Objs.GATE_OF_WAR_16124).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.GATE_OF_WAR
        player.queue {
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.RICKETY_DOOR, Objs.RICKETY_DOOR_16066).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.RICKETTY_DOOR
        player.queue {
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.OOZING_BARRIER, Objs.OOZING_BARRIER_16090).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.OOZING_BARRIER
        player.queue {
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.PORTAL_OF_DEATH, Objs.PORTAL_OF_DEATH_16044).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.PORTAL_OF_DEATH
        player.queue {
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

/**
 * Reward Chests
 */

//Gift of Peace
on_obj_option(obj = Objs.GIFT_OF_PEACE, option = "open") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 0) {
            messageBox("The box hinges creak and appears to be forming audible words....")
            doubleMessageBox(
                "...Congratulations adventurer, you have been deemed worthy of this",
                "reward. You have also unlocked the Flap emote!"
            )
            player.inventory.add(Items.COINS_995, 2000)
            player.setVarp(802, 1)
            completeFloor(player, 1)
        } else if (!player.inventory.hasSpace) {
            messageBox("You don't have enough free space in your inventory.")
        } else {
            messageBox("You have already claimed your reward from this level.")
        }
    }
}

//Grain of Plenty
on_obj_option(obj = Objs.GRAIN_OF_PLENTY, option = "search") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 1) {
            messageBox("The wheat shifts in the sack, sighing audible words....")
            doubleMessageBox(
                "...Congratulations adventurer. You have been deemed worthy of this",
                "reward. You have also unlocked the Slap Head emote!"
            )
            player.inventory.add(Items.COINS_995, 3000)
            player.setVarp(802, 3)
            completeFloor(player, 2)
        } else if (!player.inventory.hasSpace) {
            messageBox("You don't have enough free space in your inventory.")
        } else {
            messageBox("You have already claimed your reward from this level.")
        }
    }
}

//Box of Health
on_obj_option(obj = Objs.BOX_OF_HEALTH, option = "open") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 2) {
            messageBox("The box hinges creak and appear to be forming audible words....")
            doubleMessageBox(
                "...Congratulations adventurer. You have been deemed worthy of this",
                "reward. You have also unlocked the Idea emote!"
            )
            player.inventory.add(Items.COINS_995, 5000)
            player.setVarp(802, 7)
            completeFloor(player, 3)
        } else if (!player.inventory.hasSpace) {
            messageBox("You don't have enough free space in your inventory.")
        } else {
            messageBox("You have already claimed your reward from this level.")
        }
    }
}

//Cradle of Life
on_obj_option(obj = Objs.CRADLE_OF_LIFE, option = "search") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted > 2) {
            doubleMessageBox("As your hand touches the cradle, you hear",
                "the voices of a million dead adventurers...")
            messageBox("Welcome, adventurer... you have a choice.")
            when(options("I'll take the colourful ones.", "I'll take the fighting ones.", "I'll take both!", title = "Choose your style of boots.")) {
                1 -> {
                    player.inventory.add(Items.FANCY_BOOTS)
                    messageBox("Enjoy your boots.")
                }
                2 -> {
                    player.inventory.add(Items.FIGHTING_BOOTS)
                    messageBox("Enjoy your boots.")
                }
                3 -> {
                    player.inventory.add(Items.FANCY_BOOTS)
                    player.inventory.add(Items.FIGHTING_BOOTS)
                    messageBox("Enjoy your boots.")
                }
            }
            player.setVarp(802, 15)
            completeFloor(player, 4)
        } else if (!player.inventory.hasSpace) {
            messageBox("You don't have enough free space in your inventory.")
        }
    }
}