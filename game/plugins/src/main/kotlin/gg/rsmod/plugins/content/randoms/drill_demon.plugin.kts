package gg.rsmod.plugins.content.randoms

import gg.rsmod.game.model.attr.*
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import kotlin.random.Random

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val sergeantDamien = Npcs.SERGEANT_DAMIEN

on_npc_option(sergeantDamien, option = "talk-to") {
    player.queue {
        if (player.tile.regionId == 12619) {
            atTheDrillDemonTrainingArea(this)
        } else {
            if (player.attr[DRILL_DEMON_ACTIVE] == false || !player.attr.has(DRILL_DEMON_ACTIVE)) {
                NotAllowedZone(this)
            } else {
                sergeantDamienDialogue(this)
            }
        }
    }
}

suspend fun sergeantDamienDialogue(it: QueueTask) {
    it.chatNpc("Private ${it.player.username}, atten-SHUN!",
                        "You've been recommended for my corps.",
                        "Do you think you can be the best?")
    val option1 = it.options("Sir, yes sir!", "No thanks, I'm not interested.")
    when (option1) {
        1 -> sirYesSir(it)
        2 -> notInterested(it)
    }
}

suspend fun sirYesSir(it: QueueTask) {
    var lastKnownPosition: Tile = it.player.tile
    var teleportToDrillDemon = Tile(3163, 4821)
    it.chatPlayer("Sir, yes sir!")
    it.player.attr[LAST_KNOWN_POSITION] = lastKnownPosition
    it.player.moveTo(teleportToDrillDemon)
    it.wait(1)
    it.player.graphic(86)
    val npc = it.player.getInteractingNpc()
    npc.queue {
        world.remove(npc)
        npc.graphic(86)
    }
}

suspend fun notInterested(it: QueueTask) {
    it.chatPlayer("No thanks, I'm not interested.")
}

suspend fun atTheDrillDemonTrainingArea(it: QueueTask) {
    it.chatNpc("Move yourself, Private! Follow my orders and you",
                        "may, just may, leave here in a fit state for my corps!")
    it.player.attr[CORRECT_EXERCISE] = Random.nextInt(1, 5)
    val exercise: Int? = it.player.attr[CORRECT_EXERCISE]
    if (exercise != null) {
        afterPerformingCorrectExerciseOrStartingEvent(it, exercise)
    }
}

suspend fun useExerciseMatBeforeInstruction(it: QueueTask) {
    it.chatNpc("I haven't given you the order yet, worm!", npc = sergeantDamien)
    // Continue with the dialogue below.
}

suspend fun afterPerformingCorrectExerciseOrStartingEvent(it: QueueTask, exerciseType: Int) {
    val dialogue = when (exerciseType) {
        1 -> ("I want to see you on that mat doing star jumps, private!")
        2 -> ("Drop and give me push ups on that mat, private!")
        3 -> ("Get yourself over there and jog on that mat, private!")
        4 -> ("Get on that mat and give me sit ups, private!")
        else -> throw IllegalArgumentException("Invalid exercise: $exerciseType")
    }
    it.chatNpc(dialogue, npc = sergeantDamien)
    val option1 = it.options("Okay.", "I want to leave.")
    if (it.player.attr[EXERCISE_SCORE]!! > 4) {
        it.chatNpc("Well I'll be, you actually did it, Private.",
            "Now take this and get out of my sight.", npc = sergeantDamien)
        it.player.inventory.add(Item(Items.RANDOM_EVENT_GIFT))
        it.player.addLoyalty(world.random(1..30))
        it.player.attr[DRILL_DEMON_ACTIVE] = false
        it.player.attr[EXERCISE_SCORE] = 0
        val lastKnownPosition: Tile? = it.player.attr[LAST_KNOWN_POSITION]
        if (lastKnownPosition != null) {
            it.player.moveTo(lastKnownPosition)
        } else {
            // Handle the case where the saved position is null, e.g., notify the player.
            it.player.message("No saved position found.")
        }
    }
    when (option1) {
        1 -> it.chatPlayer("Okay.")
        2 -> {
            it.chatPlayer("I want to leave.")
            it.chatNpc("Pathetic. Get out of here then.", npc = sergeantDamien)
            val lastKnownPosition: Tile? = it.player.attr[LAST_KNOWN_POSITION]
            if (lastKnownPosition != null) {
                it.player.moveTo(lastKnownPosition)
            } else {
                // Handle the case where the saved position is null, e.g., notify the player.
                it.player.message("No saved position found.")
            }
        }
    }
}

suspend fun afterPerformingIncorrectExercise(it: QueueTask, exerciseType: Int) {
    val dialogue = when (exerciseType) {
        1 -> Pair("Wrong exercise, worm!",
                  "Drop and give me push ups on that mat, private!")
        2 -> Pair("Wrong exercise, worm!,",
                  "I want to see you on that mat doing star jumps, private!")
        3 -> Pair("Wrong exercise, worm!",
                  "Get on that mat and give me sit ups, private!")
        4 -> Pair("Wrong exercise, worm!",
                  "Get yourself over there and jog on that mat, private!")
        else -> throw IllegalArgumentException("Invalid exercise: $exerciseType")
    }

    it.chatNpc(dialogue.first, dialogue.second, npc = sergeantDamien)
    val option1 = it.options("Okay.", "I want to leave.")
    val lastKnownPosition = it.player.attr[LAST_KNOWN_POSITION]
    when (option1) {
        1 -> it.chatPlayer("Okay.")
        2 -> {
            it.chatPlayer("I want to leave.")
            it.chatNpc("Pathetic. Get out of here then.", npc = sergeantDamien)
            if (lastKnownPosition != null) {
                it.player.moveTo(lastKnownPosition)
            }
        }
    }
}

suspend fun NotAllowedZone(it: QueueTask) {
    it.chatNpc("As you were, soldier.", npc = sergeantDamien)
}

fun getCorrectMatId(exerciseType: Int): Int {
    return when (exerciseType) {
        1 -> 10078 //Star jumps
        2 -> 10077 //Push ups
        3 -> 10079 //Running Man
        4 -> 10076 //Situps
        else -> throw IllegalArgumentException("Invalid exercise: $exerciseType")
    }
}

fun interactWithMat(p: Player, obj: GameObject, correctMatId: Int, exerciseType: Int) {
    val faceSouth = Tile(x = obj.tile.x, z = obj.tile.z - 1)
    p.lockingQueue {
        var ticks = 0
        var isCorrectExercise = false
        while (true) {
            when (ticks) {
                1 -> p.moveTo(obj.tile.x, 4820)
                2 -> p.faceTile(faceSouth)
                3 -> {
                    when (exerciseType) {
                        1 -> p.animate(2761) // Star jumps
                        2 -> p.animate(2762) // Push ups
                        3 -> p.animate(2763) // Sit ups
                        4 -> p.animate(2764) // Running man
                    }
                }
                4 -> {
                    isCorrectExercise = p.getInteractingGameObj()?.id == correctMatId
                    if (isCorrectExercise) {
                        chatNpc("You perform the exercise correctly.", npc = sergeantDamien)
                    } else {
                        chatNpc("You perform the wrong exercise.", npc = sergeantDamien)
                    }
                }
                5 -> {
                    if (isCorrectExercise) {
                        player.queue {
                            player.attr[EXERCISE_SCORE] = (player.attr[EXERCISE_SCORE] ?: 0) + 1
                            player.attr[CORRECT_EXERCISE] = Random.nextInt(1, 5)
                            val exercise: Int? = player.attr[CORRECT_EXERCISE]
                            if (exercise != null) {
                                afterPerformingCorrectExerciseOrStartingEvent(this, exercise)
                            }
                        }
                    } else {
                        player.queue {
                            player.attr[EXERCISE_SCORE] = 0
                            val exercise: Int? = player.attr[CORRECT_EXERCISE]
                            if (exercise != null) {
                                afterPerformingIncorrectExercise(this, exercise)
                            }
                        }
                    }
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
}

on_obj_option(obj = Objs.EXERCISE_MAT_10079, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.queue {
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 4) // Running man
            }
        }
    }
}

on_obj_option(obj = Objs.EXERCISE_MAT_10078, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.queue {
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 1) // Star Jumps
            }
        }
    }
}

on_obj_option(obj = Objs.EXERCISE_MAT_10077, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.queue {
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 2) // Push ups
            }
        }
    }
}


on_obj_option(obj = Objs.EXERCISE_MAT, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.queue {
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 3) // Sit ups
            }
        }
    }
}