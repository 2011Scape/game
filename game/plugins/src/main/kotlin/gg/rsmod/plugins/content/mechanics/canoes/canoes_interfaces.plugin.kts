package gg.rsmod.plugins.content.mechanics.canoes

import gg.rsmod.game.model.attr.CANOE_VARBIT
import gg.rsmod.plugins.content.skills.woodcutting.AxeType
import kotlin.math.abs

// IntArray containing all possible boat selection buttons.
val boatChildIds = intArrayOf(30, 31, 32, 33)
// IntArray containing all possible boat traveling location buttons.
val locationChildIds = intArrayOf(47, 48, 3, 6, 49)

/**
 * Set information to check on opening of [CanoeUtils.SHAPE_INTERFACE]
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_interface_open(interfaceId = CanoeUtils.SHAPE_INTERFACE) {
    CanoeUtils.checkCanoe(player, Canoe.DUGOUT)
    CanoeUtils.checkCanoe(player, Canoe.STABLE_DUGOUT)
    CanoeUtils.checkCanoe(player, Canoe.WAKA)
}

/**
 * Bind all boat selection buttons on [CanoeUtils.SHAPE_INTERFACE] interface to an action.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
boatChildIds.forEach { button ->
    on_button(interfaceId = CanoeUtils.SHAPE_INTERFACE, component = button) {
        player.closeInterface(CanoeUtils.SHAPE_INTERFACE)
        val canoe = Canoe.definitions[player.getInteractingButton()]!!
        val varbit = player.attr[CANOE_VARBIT]!!

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

        if (axe == null) {
            player.message("You do not have an axe which you have the Woodcutting level to use.")
            return@on_button
        }

        player.lockingQueue {
            repeat(100) {
                player.animate(CanoeUtils.getShapeAnimation(axe))
                val rand = world.random(if (canoe == Canoe.WAKA) 8 else 6)
                if (rand == 1) {
                    player.addXp(Skills.WOODCUTTING, canoe.experience)
                    player.setVarbit(varbit, CanoeUtils.getCraftValue(canoe, false))
                    player.animate(-1)
                    return@lockingQueue
                }
                wait(2)
            }
        }
    }
}

/**
 * Set information to process on opening of [CanoeUtils.DESTINATION_INTERFACE]
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_interface_open(interfaceId = CanoeUtils.DESTINATION_INTERFACE) {
    val varbit = player.attr[CANOE_VARBIT]!!
    val canoe = CanoeUtils.getCanoeFromVarbit(player, varbit)
    val stationIndex = CanoeUtils.getStationIndex(player.tile)
    val maxDistance = canoe.maxDistance

    val locationChilds = intArrayOf(47, 48, 3, 6)
    val boatChilds = intArrayOf(39, 40, 1, 4)
    val hiddenChilds = intArrayOf(10, 11, 12, 20)

    player.setComponentHidden(
        interfaceId = CanoeUtils.DESTINATION_INTERFACE,
        component = boatChilds[stationIndex],
        hidden = false,
    )
    player.setComponentHidden(
        interfaceId = CanoeUtils.DESTINATION_INTERFACE,
        component = locationChilds[stationIndex],
        hidden = true,
    )
    player.setComponentHidden(
        interfaceId = CanoeUtils.DESTINATION_INTERFACE,
        component = CanoeUtils.getYouAreHereComponent(player.tile),
        hidden = false,
    )
    if (canoe != Canoe.WAKA) {
        player.setComponentHidden(interfaceId = CanoeUtils.DESTINATION_INTERFACE, component = 49, hidden = true)
        player.setComponentHidden(interfaceId = CanoeUtils.DESTINATION_INTERFACE, component = 46, hidden = false)
        player.setComponentHidden(interfaceId = CanoeUtils.DESTINATION_INTERFACE, component = 45, hidden = true)
        for (i in 0..3) {
            if (i == stationIndex) continue
            if (abs(i - stationIndex) > maxDistance) {
                player.setComponentHidden(
                    interfaceId = CanoeUtils.DESTINATION_INTERFACE,
                    component = boatChilds[i],
                    hidden = true,
                )
                player.setComponentHidden(
                    interfaceId = CanoeUtils.DESTINATION_INTERFACE,
                    component = locationChilds[i],
                    hidden = true,
                )
                player.setComponentHidden(
                    interfaceId = CanoeUtils.DESTINATION_INTERFACE,
                    component = hiddenChilds[i],
                    hidden = true,
                )
            }
        }
    }
}

/**
 * Bind all location buttons on [CanoeUtils.DESTINATION_INTERFACE] to an action
 * @author Kevin Senez <ksenez94@gmail.com>
 */
locationChildIds.forEach { button ->
    on_button(interfaceId = CanoeUtils.DESTINATION_INTERFACE, component = button) {
        val dest = CanoeUtils.getDestinationFromButtonId(button)
        val destIndex = CanoeUtils.getStationIndex(dest)
        val arrivalMessage = CanoeUtils.getNameByIndex(destIndex)
        val stationIndex = CanoeUtils.getStationIndex(player.tile)
        val interfaceAnimationId = CanoeUtils.getTravelAnimation(stationId = stationIndex, destId = destIndex)

        /**
         * TODO HERE; Add check for familiar, and stop user if they have one following them.
         */

        player.lockingQueue(priority = TaskPriority.STRONG) {
            // Close the destination map interface.
            player.closeInterface(interfaceId = CanoeUtils.DESTINATION_INTERFACE)

            player.lockingQueue {

                // Fade screen to black.
                player.openFullscreenInterface(interfaceId = 120)
                wait(2)
                player.closeFullscreenInterface()

                // Send boat traveling interface and appropriate animation on interface for the correct destination.
                player.openFullscreenInterface(interfaceId = 758)
                player.setComponentAnim(interfaceId = 758, 3, interfaceAnimationId)

                // Wait travel duration.
                wait(getTravelDuration(interfaceAnimationId) + 1)

                // Move player to desired destination
                player.moveTo(dest)
                player.closeFullscreenInterface()

                // Fade out from black.
                player.openFullscreenInterface(interfaceId = 170)
                wait(2)
                player.closeFullscreenInterface()

                // Wait 2 ticks after arrival to set to have arrived.
                wait(2)
                player.message("You arrive at $arrivalMessage.")
                player.message("Your canoe sinks from the long journey.")

                // If destination is Wilderness, send extra message.
                if (destIndex == 4) {
                    player.message("There are no trees nearby to make a new canoe. Guess you're walking.")
                }

                // Reset tree to unused.
                CanoeUtils.updateCanoeStations(player)
            }
        }
    }
}

/**
 * Gives out the travel duration depending on the [interfaceAnimation] supplied.
 *
 * @param interfaceAnimation The animation for the boat on the interface.
 *
 * @return The travelling duration depending on the animation duration or default to 15.
 */
fun getTravelDuration(interfaceAnimation: Int): Int {
    return if (interfaceAnimation != 0) {
        world.definitions
            .get(
                AnimDef::class.java,
                interfaceAnimation,
            ).cycleLength
    } else {
        15
    }
}
