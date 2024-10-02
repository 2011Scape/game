package gg.rsmod.plugins.content.mechanics.canoes

import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.model.attr.CANOE_VARBIT
import gg.rsmod.plugins.content.skills.woodcutting.AxeType

private val CANOE_STATIONS_SHAPE_CANOE = Objs.CANOE_STATION_12146
private val CANOE_STATIONS_FLOAT =
    intArrayOf(
        Objs.CANOE_STATION_12147,
        Objs.CANOE_STATION_12148,
        Objs.CANOE_STATION_12149,
        Objs.CANOE_STATION_12150,
    )
private val CANOE_STATIONS_PADDLE_CANOE =
    intArrayOf(
        Objs.CANOE_STATION_12151,
        Objs.CANOE_STATION_12152,
        Objs.CANOE_STATION_12153,
        Objs.CANOE_STATION_12154,
        Objs.CANOE_STATION_12155,
        Objs.CANOE_STATION_12156,
        Objs.CANOE_STATION_12157,
        Objs.CANOE_STATION_12158,
    )

private val STAGE_TREE_NONINTERACTABLE = 9
private val STAGE_TREE_CHOPPED = 10
private val PUSH_ANIMATION = 3301
private val FLOAT_ANIMATION = 3304

/**
 * Handles the chopping-down of the Canoe Station tree.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_obj_option(obj = Objs.CANOE_STATION, option = "chop-down") {
    // The object being interacted with
    val obj = player.getInteractingGameObj()

    // The object varbit
    val varbit = obj.getDef(world.definitions).varbit

    // Detects if player has an axe in equipment or inventory
    val axe =
        AxeType.values.reversed().firstOrNull {
            player.skills
                .getMaxLevel(Skills.WOODCUTTING) >= it.level &&
                (
                    player.equipment.contains(it.item) ||
                        player.inventory.contains(
                            it.item,
                        )
                )
        }

    // Resets obj varbit to 0 if higher and supposed to be on chop-down option.
    if (player.getVarbit(varbit) != 0) {
        player.setVarbit(varbit, 0)
    }

    // If no axe, don't allow player to progress the canoe construction.
    if (axe == null) {
        player.message("You do not have an axe which you have the woodcutting level to use.")
        return@on_obj_option
    }

    // Checks if the player has at least level 12 woodcutting.
    if (player.skills.getCurrentLevel(Skills.WOODCUTTING) < 12) {
        player.message("You need a Woodcutting level of at least 12 to chop down this tree.")
        return@on_obj_option
    }

    // Lock the player and queue the chopping down of the tree.
    player.lockingQueue {
        player.walkTo(
            it = this,
            tile = CanoeUtils.getChopLocation(player.tile),
            stepType = MovementQueue.StepType.FORCED_WALK,
        )
        repeat(player.tile.getDistance(CanoeUtils.getChopLocation(player.tile))) {
            if (player.tile == CanoeUtils.getChopLocation(player.tile)) {
                return@lockingQueue
            }
            wait(1)
        }

        CanoeUtils.updateCanoeStations(player, varbit)

        // Wait 1 tick.
        wait(1)

        // Stop the players movements.
        player.stopMovement()

        // Make the player face the canoe station.
        player.faceTile(face = CanoeUtils.getFaceLocation(player.tile))

        // Wait 1 tick.
        wait(1)

        // Make the player start the chopping animation.
        player.animate(id = axe.animation)

        // Wait 3 ticks.
        wait(3)

        // Stop chopping, set tree to non-interactable.
        player.animate(-1)
        player.setVarbit(varbit, STAGE_TREE_NONINTERACTABLE)

        // Set the object varbit for it to know that the tree has been chopped.
        wait(2)
        player.setVarbit(varbit, STAGE_TREE_CHOPPED)
    }
}

/**
 * Handles the shaping of the Canoe Station.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_obj_option(obj = CANOE_STATIONS_SHAPE_CANOE, option = "shape-canoe") {
    // The object being interacted with.
    val obj = player.getInteractingGameObj()

    // The object varbit.
    val varbit = obj.getDef(world.definitions).varbit

    // Check if varbit is actually set to tree chopped.
    if (player.getVarbit(varbit) != STAGE_TREE_CHOPPED) {
        // If not properly set, reset canoe station to default.
        player.setVarp(varbit, 0)
        return@on_obj_option
    }

    // Lock the player and open the canoe shape interface.
    player.lockingQueue {

        // Handle the player walking towards the station to shape the canoe.
        val endTile = CanoeUtils.getCraftFloatLocation(player.tile)
        player.walkTo(it = this, tile = endTile, stepType = MovementQueue.StepType.FORCED_WALK)
        repeat(player.tile.getDistance(endTile)) {
            if (player.tile == endTile) {
                return@lockingQueue
            }
            wait(1)
        }

        // Wait 1 tick.
        wait(1)

        // Stop the players movements.
        player.stopMovement()

        // Make the player face the canoe station.
        player.faceTile(face = CanoeUtils.getFaceLocation(player.tile))

        // Send the canoe shaping interface.
        player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_FULL, interfaceId = CanoeUtils.SHAPE_INTERFACE)

        // Set the CANOE_VARBIT attribute to the selected canoe varbit (Information for the Destination Interface)
        player.attr[CANOE_VARBIT] = varbit
    }
}

/**
 * Handles the pushing/floating of the canoe station.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_obj_options(objects = CANOE_STATIONS_FLOAT, options = arrayOf("float canoe", "float log", "float waka")) {
    // The object being interacted with.
    val obj = player.getInteractingGameObj()

    // The object varbit.
    val varbit = obj.getDef(world.definitions).varbit

    // The canoe selected.
    val canoe = CanoeUtils.getCanoeFromVarbit(player, varbit)

    // Lock the player and handle the canoe pushing/floating
    player.lockingQueue {
        val endTile = CanoeUtils.getCraftFloatLocation(player.tile)

        // Handle the walking of the player towards the front of the canoe station.
        player.walkTo(it = this, tile = endTile)
        repeat(player.tile.getDistance(endTile)) {
            if (player.tile == endTile) {
                return@lockingQueue
            }
            wait(1)
        }

        // Wait 1 tick.
        wait(1)

        // Make the player face the Canoe Station.
        player.faceTile(face = CanoeUtils.getFaceLocation(player.tile))

        // Wait 1 tick.
        wait(1)

        // Make the player push the boat and send the proper object animation at same time.
        player.animate(id = PUSH_ANIMATION)
        player.write(LocAnimMessage(gameObject = obj, animation = FLOAT_ANIMATION))

        // Wait 2 ticks.
        wait(2)

        // Set the canoe station varbit to the proper floating canoe.
        player.setVarbit(varbit, CanoeUtils.getCraftValue(canoe, true))
    }
}

/**
 * Handles the paddle option of the Canoe Station.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_obj_options(objects = CANOE_STATIONS_PADDLE_CANOE, options = arrayOf("paddle log", "paddle canoe")) {
    // The object being interacted with.
    val obj = player.getInteractingGameObj()

    // The object varbit.
    val varbit = obj.getDef(world.definitions).varbit

    // Set the CANOE_VARBIT attribute to the selected canoe varbit (Information for the Destination Interface)
    player.attr[CANOE_VARBIT] = varbit

    // Open the destination interface.
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_FULL, interfaceId = CanoeUtils.DESTINATION_INTERFACE)
}

on_login {
    CanoeUtils.updateCanoeStations(player)
}
