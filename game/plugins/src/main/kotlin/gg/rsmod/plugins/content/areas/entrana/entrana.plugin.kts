package gg.rsmod.plugins.content.areas.entrana

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.attr.HAS_SPAWNED_TREE_SPIRIT
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity
import gg.rsmod.plugins.content.skills.woodcutting.AxeType

val ladder = Objs.LADDER_2408
val dungeonTile = Tile(2822, 9774, 0)

on_npc_option(Npcs.CAVE_MONK, "talk-to") {
    player.queue {
        monkDialogue(this)
    }
}

suspend fun monkDialogue(it: QueueTask) {
    it.chatNpc(
        "Be careful going in there! You are unarmed, and there",
        "is much evilness lurking down there! The evilness seems",
        "to block off our contact with our gods,",
        npc = Npcs.CAVE_MONK,
    )
    it.chatNpc(
        "so our prayers seem to have less effect down there. Oh,",
        "also, you wont be able to come back this way -  This",
        "ladder only goes one way",
        npc = Npcs.CAVE_MONK,
    )
    it.chatNpc(
        "The only exit from the caves below is a portal which is",
        "guarded by greater demons!",
        npc = Npcs.CAVE_MONK,
    )
    it.chatPlayer("Well, that is a risk I will have to take.")
}

fun dungeonEntrance(player: Player) {
    player.moveTo(dungeonTile)
}

on_obj_option(ladder, "climb-down") {
    val currentStage = player.getCurrentStage(LostCity)
    if (currentStage == LostCity.ENTRANA_DUNGEON) {
        player.queue {
            monkDialogue(this)
            dungeonEntrance(player)
        }
    }
    if (currentStage > LostCity.ENTRANA_DUNGEON) {
        dungeonEntrance(player)
    } else {
        player.message("Nothing interesting happens.")
    }
}

on_item_on_item(Items.KNIFE, Items.DRAMEN_BRANCH) {
    player.queue {
        player.lock()
        player.animate(1248)
        wait(8)
        player.inventory.remove(Items.DRAMEN_BRANCH, amount = 1)
        player.inventory.add(Items.DRAMEN_STAFF)
        messageBox("You carve the branch into a staff.")
        player.unlock()
    }
}

on_obj_option(Objs.DRAMEN_TREE, "chop down") {
    when (player.getCurrentStage(LostCity)) {
        LostCity.ENTRANA_DUNGEON -> {
            if (player.attr.has(HAS_SPAWNED_TREE_SPIRIT))
                {
                    player.message("I should finish what I'm doing first.")
                } else {
                val treeSpiritNpc =
                    Npc(id = Npcs.TREE_SPIRIT, tile = Tile(2860, 9737, 0), world = world, owner = player)
                world.spawn(treeSpiritNpc)
                player.attr.set(HAS_SPAWNED_TREE_SPIRIT, value = 1)
            }
        }
        LostCity.CUT_DRAMEN_TREE, LostCity.CREATE_DRAMEN_BRANCH, LostCity.QUEST_COMPLETE -> {
            player.queue {
                if (player.skills.getMaxLevel(Skills.WOODCUTTING) >= 36) {
                    if (player.inventory.hasFreeSpace()) {
                        chopDramenTree(this)
                    } else {
                        messageBox("Your inventory is too full to hold any more logs.")
                    }
                } else {
                    messageBox("You need a Woodcutting level of 36 to chop this tree.")
                }
            }
        }
        else -> {
            player.queue {
                messageBox("The tree seems to have an ominous aura. <br> You do not feel like chopping it down.")
            }
        }
    }
}

suspend fun chopDramenTree(it: QueueTask) {
    val player = it.player
    val axe =
        AxeType.values.reversed().firstOrNull {
            player.skills.getMaxLevel(Skills.WOODCUTTING) >= it.level &&
                (player.equipment.contains(it.item) || player.inventory.contains(it.item))
        } ?: return
    val axeAnimation = axe.animation
    player.lock()
    player.animate(axeAnimation, idleOnly = true)
    player.filterableMessage("You swing your hatchet at the dramen tree.")
    player.animate(axeAnimation, idleOnly = true)
    it.wait(5)
    if (player.getCurrentStage(LostCity) == LostCity.CUT_DRAMEN_TREE) {
        player.advanceToNextStage(LostCity)
    }
    player.inventory.add(Items.DRAMEN_BRANCH, amount = 1, assureFullInsertion = true)
    player.animate(-1)
    player.unlock()
}

on_obj_option(Objs.MAGIC_DOOR, "open") {
    val obj = player.getInteractingGameObj()
    player.lockingQueue(TaskPriority.STRONG) {
        handleDoor(player, obj)
        wait(2)
        player.message("You feel the world around you dissolve...")
        player.playSound(Sfx.FT_FAIRY_TELEPORT)
        player.teleport(Tile(3237, 3773, 0), type = TeleportType.FAIRY)
    }
}

suspend fun handleDoor(
    player: Player,
    obj: GameObject,
) {
    val openDoor = DynamicObject(id = 20988, type = 0, rot = 1, tile = Tile(x = 2874, z = 9750))
    val door = DynamicObject(id = 2407, type = 0, rot = 0, tile = Tile(x = 2874, z = 9750))
    player.lockingQueue {
        val x = 2874
        val z = 9750
        world.remove(obj)
        world.spawn(openDoor)
        player.playSound(Sfx.DOOR_OPEN)
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(openDoor)
        player.playSound(Sfx.DOOR_CLOSE)
        world.spawn(door)
    }
}
