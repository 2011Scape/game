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
enum class SecurityQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanations: List<String>,
) {
    QUESTION_1(
        "What do I do if a moderator asks me for my account details?",
        listOf(
            "Tell them whatever they want to know.",
            "Politely tell them no.",
            "Politely tell them no and then use the 'Report Abuse' button.",
        ),
        3,
        listOf(
            "Never give your account details to anyone! This includes things like recovery answers, contact details and passwords. Never use personal details for recoveries or bank PINs!",
            "Okay, don't tell them the details. But reporting the incident to 2011Scape staff would help. Use the Report Abuse button. Never use personal details for recoveries or bank PINs!",
            "Report any attempt to gain your account details as it is a very serious breach of 2011Scape rules. Never use personal details for recoveries or bank PINs!",
        ),
    ),
    QUESTION_2(
        "My friend uses this great add-on program he got from a website, should I?",
        listOf(
            "No, it might steal my password.",
            "I'll gave it a try and see if I like it.",
            "Sure, he's used it a lot, so can I.",
        ),
        1,
        listOf(
            "The only safe add-on for is the Window client available from our Website.",
            "Some programs steal your password without you even knowing, this only requires running the program once, even if you don't use it.",
            "The program may steal your password and is against the rules to use.",
        ),
    ),
    QUESTION_3(
        "Who is it ok to share my account with?",
        listOf(
            "My friends.",
            "Relatives.",
            "Nobody.",
        ),
        3,
        listOf(
            "Your account may only be used by you.",
            "Your account may only be used by you.",
            "You, and only you may use your account.",
        ),
    ),
    QUESTION_4(
        "Why do I need to type in recovery questions?",
        listOf(
            "To help me recover my password if I forget it or it is stolen.",
            "To let 2011Scape know more about its players.",
            "To see if I can type in random letters on my keyboard.",
        ),
        1,
        listOf(
            "Your recovery questions will help staff protect and return your account if it is stolen. Never use personal details for recoveries or bank PINs!",
            "2011Scape values players opinions, but we use polls and forums to see what you think. The recoveries are not there to gain personal information about anybody but to protect your account. Never use personal details",
            "Typing random letters into your recoveries won't help you or the staff - you'll never remember them anyway! Never use personal details for recoveries or bank PINs!",
        ),
    ),
    QUESTION_5(
        "Who is it ok to share my account with?",
        listOf(
            "My friends",
            "Relatives",
            "Nobody",
        ),
        3,
        listOf(
            "Your account may only be used by you.",
            "Your account may only be used by you.",
            "You, and only you may use your account.",
        ),
    ),
    QUESTION_6(
        "Who can I give my password to?",
        listOf(
            "My friends",
            "My brother",
            "Nobody",
        ),
        3,
        listOf(
            "Your password should be kept secret from everyone. You should *never* give it out under any circumstances.",
            "Your password should be kept secret from everyone. You should *never* give it out under any circumstances.",
            "Your password should be kept secret from everyone. You should *never* give it out under any circumstances.",
        ),
    ),
    QUESTION_7(
        "How will 2011Scape contact me if I have been chosen to be a moderator?",
        listOf(
            "Email.",
            "Website popup.",
            "Game Inbox on the Website.",
        ),
        3,
        listOf(
            "2011Scape never uses email to contact you, this is a scam and a fake, do not reply to it and delete it straight away. 2011Scape will only contact you through your Game Inbox available on our website.",
            "2011Scape would never use such an insecure method to pick you. We will contact you through your Game Inbox available on our website.",
            "It's always best to protect your personal information and report suspicious activity.",
        ),
    ),
    QUESTION_8(
        "How often should you change your recovery questions?",
        listOf(
            "Never",
            "Every couple of months",
            "Every day",
        ),
        2,
        listOf(
            "Never changing your recovery questions will lead to an insecure account due to keyloggers or friends knowing enough about you to guess them. Don't use personal details for your recoveries.",
            "it is the ideal option to change your questions but make sure you can remember the answers! Don't use personal details for your recoveries.",
            "Normally recovery questions will take 14 days to become active, so there's no point in changing them everyday! Don't use personal details for your recoveries.",
        ),
    ),
    QUESTION_9(
        "A website says I can become a player moderator by giving them my password what do I do?",
        listOf(
            "Nothing.",
            "Give them my password.",
            "Don't tell them anything and inform 2011Scape through the game website.",
        ),
        3,
        listOf(
            "This is one solution, however someone will fall for this scam sooner or later. Tell us about it through the website. Remember that  moderators are hand picked by 2011Scape.",
            "This will almost certainly lead to your account being hijacked. No website can make you a moderator as they are hand picked by 2011Scape.",
            "By informing us we can have the site taken down so other people will not have their accounts hijacked by this scam.",
        ),
    ),
    QUESTION_10(
        "Will 2011Scape block me from saying my PIN in game?",
        listOf(
            "Yes.",
            "No.",
        ),
        2,
        listOf(
            "2011Scape does NOT block your PIN so don't type it!",
            "2011Scape will not block your PIN so don't type it! Never use personal details for recoveries or bank PINs!",
        ),
    ),
    QUESTION_11(
        "Can I leave my account logged in while I'm out of the room?",
        listOf(
            "Yes.",
            "No.",
            "If I'm going to be quick.",
        ),
        2,
        listOf(
            "You should logout in case you are attacked or receive a random event. Leaving your character logged in can also allow someone to steal your items or entire account!",
            "This is the safest, both in terms of security and and keeping your items! Leaving you character logged in can also allow someone to steal your items or entire account!",
            "You should logout in case you are attacked or receive a random event. Leaving your character logged in can also allow someone to steal your items or entire account!",
        ),
    ),
    QUESTION_12(
        "Where should I enter my 2011Scape Password?",
        listOf(
            "On all 2011Scape fan sites.",
            "Only on 2011Scape website.",
            "On all websites I visit.",
        ),
        2,
        listOf(
            "Always use a unique password purely for your account.",
            "Always make sure you are entering your password only on the 2011Scape Website as other sites may try to steal it.",
            "This is very insecure and will may lead to your account being stolen.",
        ),
    ),
    QUESTION_13(
        "What is an example of a good bank PIN?",
        listOf(
            "Your real life bank PIN.",
            "Your birthday.",
            "The birthday of a famous person or event.",
        ),
        3,
        listOf(
            "This is a bad idea as if someone happens to find out your bank PIN on 2011Scape, they then have access to your bank account.",
            "Not a good idea because you know how many presents you get for your birthday. So you can imagine how many people know this date. Never use personal details for recoveries or bank PINs!",
            "Well done! Unless you tell someone, they are unlikely to guess who or what you have chosen, and you can always look it up, Never use personal details for recoveries or bank PINs!",
        ),
    ),
    QUESTION_14(
        "What do I do if I think I have a keylogger or virus?",
        listOf(
            "Virus scan my computer then change my password and recoveries.",
            "Change my password and recoveries then virus scan my computer.",
            "Nothing, it will go away on its own.",
        ),
        1,
        listOf(
            "Removing the keylogger must be the priority otherwise anything you type can be given away. Remember to change your password and recovery questions afterwards.",
            "If you change your password and recoveries while you still have the keylogger, they will still be insecure. Remove the keylogger first. Never use personal details for recoveries or bank PINs!",
            "This could mean your account may be accessed by someone else. Remove the keylogger then change your password and recoveries. Never use personal details for recoveries or bank PINs!",
        ),
    ),
    QUESTION_15(
        "Recovery answers should be...",
        listOf(
            "Memorable.",
            "Easy to guess.",
            "Random gibberish.",
        ),
        1,
        listOf(
            "A good recovery answer that not many people will know, you won't forget, will stay the same and that you wouldn't accidentally give away. Remember: Don't use personal details for your recoveries.",
            "This is a bad idea as anyone who knows you could guess them. Remember: Don't use personal details for your recoveries.",
            "This is a bad idea because it is very difficult to remember and you won't be able to recover your account! Remember: Don't use personal details for your recoveries.",
        ),
    ),
    QUESTION_16(
        "What do you do if someone tells you that you have won the 2011Scape Lottery and asks for your password or recoveries?",
        listOf(
            "Give them the information they asked for",
            "Don't tell them anything and ignore them.",
            "Ignore them and report them.",
        ),
        3,
        listOf(
            "here is no 2011Scape Lottery! Never give your account details to anyone. Press the 'Report Abuse' button and fill in the offending player's name and the correct category. Don't tell them anything and click the 'Report Abuse' button.",
            "Quite good. But we should try to stop scammers. So please report them using the 'Report Abuse' button.",
            "Press the 'Report Abuse' button and fill in the offending player's name and the correct category.",
        ),
    ),
    QUESTION_17(
        "What should I do if I think someone knows my recoveries?",
        listOf(
            "Tell them never to use them.",
            "Use the Account Management section on the 2011Scape website.",
            "Recover a Lost Password' section on the 2011Scape website.",
        ),
        3,
        listOf(
            "This does nothing to help the security of your account. You should reset your questions through the 'Lost password' link on our website.",
            "If you use the Account Management section to change your recovery questions, it will take 14 days to come into effect, someone may have access to your account this time.",
            "If you provide all the correct information this will reset your questions within 24 hours and make your account secure again.",
        ),
    ),
    QUESTION_18(
        "What do you do if someone asks you for your password or recoveries to make you a player moderator?",
        listOf(
            "Don't give them any information and send an 'Abuse report'.",
            "Don't tell them anything and ignore them.",
            "Give them the information they asked for.",
        ),
        1,
        listOf(
            "Press the 'Report Abuse' button and fill in the offending player's name and the correct category.",
            "But we should try to stop scammers. So please report them using the 'Report Abuse' button.",
            "2011Scape never ask for your account information especially to become a player moderator. Press the 'Report Abuse' button and fill in the offending player's name and the correct category.",
        ),
    ),
    QUESTION_19(
        "To pass you must answer me this: Where can i find cheats for 2011Scape?",
        listOf(
            "On the 2011Scape website",
            "By searching the internet",
            "Nowhere.",
        ),
        3,
        listOf(
            "There are NO cheats coded into the game and any sites claiming to have cheats are fakes and may lead to your account being stolen if you give them your password.",
            "There are NO cheats coded into the game and any sites claiming to have cheats are fakes and may lead to your account being stolen if you give them your password.",
            "There are NO cheats coded into the game and any sites claiming to have cheats are fakes and may lead to your account being stolen if you give them your password.",
        ),
    ),
}

/**
 * Safe areas: indicate corridors that are safe to ask player security questions.
 */
enum class SafeArea(
    val northEast: Pair<Int, Int>,
    val southWest: Pair<Int, Int>,
) {
    // Floor 1 - Vault of War
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

    // Floor 2 - Catacomb of Famine
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

    // Floor 3 - Pit of Pestilence
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

    // Floor 4 - The Sepulchre of Death
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
    SAFE_AREA_71(Pair(2341, 5224), Pair(2340, 5222)),
    ;

    fun isInArea(
        x: Int,
        z: Int,
    ): Boolean {
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

fun completeFloor(
    player: Player,
    newFloor: Int,
) {
    val floor = player.getStrongholdOfSecurity()
    if (floor + 1 == newFloor) {
        player.setStrongholdOfSecurity(newFloor)
    }
}

fun getDoorSFX(objId: Int): Int {
    return when (objId) {
        Objs.GATE_OF_WAR -> Sfx.SOS_WARDOOR_OPEN_CLOSE
        Objs.GATE_OF_WAR_16124 -> Sfx.SOS_WARDOOR_OPEN_CLOSE
        Objs.RICKETY_DOOR -> Sfx.SOS_FAMDOOR_OPEN_CLOSE
        Objs.RICKETY_DOOR_16066 -> Sfx.SOS_FAMDOOR_OPEN_CLOSE
        Objs.OOZING_BARRIER -> Sfx.SOS_PESTDOOR_OPEN_CLOSE
        Objs.OOZING_BARRIER_16090 -> Sfx.SOS_PESTDOOR_OPEN_CLOSE
        Objs.PORTAL_OF_DEATH -> Sfx.SOS_DEATHDOOR_OPEN_CLOSE
        Objs.PORTAL_OF_DEATH_16044 -> Sfx.SOS_DEATHDOOR_OPEN_CLOSE
        else -> Sfx.SOS_WARDOOR_OPEN // Default value
    }
}

suspend fun gateSecurityQuestion(
    it: QueueTask,
    obj: GameObject,
    player: Player,
    npcId: Int,
) {
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
            facialExpression = FacialExpression.OLD_NORMAL,
            wrap = true,
        )

        val userAnswer = it.options(*selectedQuestion.options.toTypedArray())
        when (userAnswer) {
            selectedQuestion.correctAnswer -> {
                it.chatNpc(
                    "Correct! ${selectedQuestion.explanations[userAnswer - 1]}",
                    npc = npcId,
                    facialExpression = FacialExpression.OLD_NORMAL,
                    wrap = true,
                )
                it.player.animate(4282)
                it.player.playSound(Sfx.SOS_THROUGH_DOOR)
                it.wait(3)
                it.player.playSound(getDoorSFX(obj.id))
                moveThroughDoor(it, player, obj)
                it.wait(1)
                it.player.animate(4283)
            }
            else -> {
                it.chatNpc(
                    "Wrong! ${selectedQuestion.explanations[userAnswer - 1]}",
                    npc = npcId,
                    facialExpression = FacialExpression.OLD_NORMAL,
                    wrap = true,
                )
            }
        }
    } else {
        // Skip Question in Dangerous Area or Completed Floor
        it.player.animate(4282)
        it.wait(2)
        it.player.playSound(getDoorSFX(obj.id))
        moveThroughDoor(it, player, obj)
        it.wait(1)
        it.player.animate(4283)
    }
}

fun moveThroughDoor(
    it: QueueTask,
    player: Player,
    obj: GameObject,
) {
    // Determine the new X-coordinate for the player based on the door's rotation and the player's position relative to the door.
    val newX =
        when (obj.rot) {
            0, 2 ->
                when {
                    // If the player is to the left of a north/south rotated door, move them to the right of the door.
                    player.tile.x < obj.tile.x -> obj.tile.x + 1
                    // If the player is to the right of a north/south rotated door, move them to the left of the door.
                    player.tile.x > obj.tile.x -> obj.tile.x - 1
                    // If the player is on the same tile as a north/south rotated door, move them south (increase x-coordinate).
                    else -> obj.tile.x - 1
                }
            // If the door's rotation is neither north nor south, the X-coordinate remains unchanged.
            else -> obj.tile.x
        }

    // Determine the new Z-coordinate for the player based on the door's rotation and the player's position relative to the door.
    val newZ =
        when (obj.rot) {
            1, 3 ->
                when {
                    // If the player is in front of an east/west rotated door, move them behind the door.
                    player.tile.z < obj.tile.z -> obj.tile.z + 1
                    // If the player is behind an east/west rotated door, move them in front of the door.
                    player.tile.z > obj.tile.z -> obj.tile.z - 1
                    // If the player is on the same tile as an east/west rotated door, move them west (decrease z-coordinate).
                    else -> obj.tile.z - 1
                }
            // If the door's rotation is neither east nor west, the Z-coordinate remains unchanged.
            else -> obj.tile.z
        }

    // Teleport the player to the new coordinates.
    it.player.teleportTo(
        x = newX,
        z = newZ,
        height = player.tile.height,
    )
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

// Entrance
on_obj_option(obj = Objs.ENTRANCE_16154, option = "climb-down") {
    player.handleLadder(1860, 5244, 0)
}

// Floor 1 - Vault of War
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

// Floor 2 - The Catacomb of Famine
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

// Floor 3 - The Pit of Pestilence
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

// Floor 4 - Death
on_obj_option(obj = Objs.BONEY_LADDER, option = "climb-up") {
    player.handleLadder(2147, 5284, 0)
}

on_obj_option(obj = Objs.BONE_CHAIN, option = "climb-up") {
    player.handleLadder(2147, 5284, 0)
}

// Warning Sign: "Are you sure you want to climb down?"
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
        player.faceTile(obj.tile)
        player.lockingQueue(TaskPriority.STRONG) {
            wait(2)
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.RICKETY_DOOR, Objs.RICKETY_DOOR_16066).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.RICKETTY_DOOR
        player.faceTile(obj.tile)
        player.lockingQueue(TaskPriority.STRONG) {
            wait(2)
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.OOZING_BARRIER, Objs.OOZING_BARRIER_16090).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.OOZING_BARRIER
        player.faceTile(obj.tile)
        player.lockingQueue(TaskPriority.STRONG) {
            wait(2)
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

listOf(Objs.PORTAL_OF_DEATH, Objs.PORTAL_OF_DEATH_16044).forEach { objId ->
    on_obj_option(obj = objId, option = "open", lineOfSightDistance = 1) {
        val obj = player.getInteractingGameObj()
        val npcId = Npcs.PORTAL_OF_DEATH
        player.faceTile(obj.tile)
        player.lockingQueue(TaskPriority.STRONG) {
            wait(2)
            gateSecurityQuestion(this, obj, player, npcId)
        }
    }
}

/**
 * Reward Chests
 */

// Gift of Peace
on_obj_option(obj = Objs.GIFT_OF_PEACE, option = "open") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 0) {
            messageBox("The box hinges creak and appears to be forming audible words....")
            doubleMessageBox(
                "...Congratulations adventurer, you have been deemed worthy of this",
                "reward. You have also unlocked the Flap emote!",
            )
            player.playSound(Sfx.SOS_CHOIR)
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

// Grain of Plenty
on_obj_option(obj = Objs.GRAIN_OF_PLENTY, option = "search") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 1) {
            messageBox("The wheat shifts in the sack, sighing audible words....")
            doubleMessageBox(
                "...Congratulations adventurer. You have been deemed worthy of this",
                "reward. You have also unlocked the Slap Head emote!",
            )
            player.playSound(Sfx.SOS_SACK)
            player.playSound(Sfx.SOS_CHOIR)
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

// Box of Health
on_obj_option(obj = Objs.BOX_OF_HEALTH, option = "open") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        if (player.inventory.hasSpace && floorsCompleted == 2) {
            messageBox("The box hinges creak and appear to be forming audible words....")
            doubleMessageBox(
                "...Congratulations adventurer. You have been deemed worthy of this",
                "reward. You have also unlocked the Idea emote!",
            )
            player.playSound(Sfx.SOS_CHOIR)
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

// Cradle of Life
on_obj_option(obj = Objs.CRADLE_OF_LIFE, option = "search") {
    val floorsCompleted = player.getStrongholdOfSecurity()
    player.queue {
        val inventoryHasSpace = player.inventory.hasSpace
        val hasFancyBoots = player.hasItem(Items.FANCY_BOOTS)
        val hasFightingBoots = player.hasItem(Items.FIGHTING_BOOTS)

        if (inventoryHasSpace && floorsCompleted > 2) {
            if (!hasFancyBoots && !hasFightingBoots) {
                doubleMessageBox(
                    "As your hand touches the cradle, you hear",
                    "the voices of a million dead adventurers...",
                )
                messageBox("Welcome, adventurer... you have a choice.")
                doubleItemMessageBox(
                    "You can choose between these two pairs of boots.",
                    item1 = Items.FANCY_BOOTS,
                    item2 = Items.FIGHTING_BOOTS,
                )
                val optionSelected =
                    options(
                        "I'll take the colourful ones.",
                        "I'll take the fighting ones.",
                        title = "Choose your style of boots.",
                    )
                when (optionSelected) {
                    1 -> {
                        player.inventory.add(Items.FANCY_BOOTS)
                        messageBox("Enjoy your boots.")
                    }

                    2 -> {
                        player.inventory.add(Items.FIGHTING_BOOTS)
                        messageBox("Enjoy your boots.")
                    }
                }
                player.playSound(Sfx.SOS_CHOIR)
                player.setVarp(802, 15)
                completeFloor(player, 4)
            } else {
                messageBox("You have already claimed your reward from this level.")
            }
        } else if (!inventoryHasSpace) {
            messageBox("You don't have enough free space in your inventory.")
        }
    }
}

// Skull Sceptre Teleport
on_item_option(Items.SKULL_SCEPTRE, option = "invoke") {
    player.teleport(endTile = Tile(3081, 3421, 0), TeleportType.SKULL_SCEPTRE)
}

on_equipment_option(item = Items.SKULL_SCEPTRE, option = "invoke") {
    player.teleport(endTile = Tile(3081, 3421, 0), TeleportType.SKULL_SCEPTRE)
}
