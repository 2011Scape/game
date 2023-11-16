package gg.rsmod.plugins.content.inter.slayer

import gg.rsmod.game.model.attr.*

val BUY_INTERFACE = 164
val LEARN_INTERFACE = 378
val ASSIGNMENT_INTERFACE = 161

val masters = arrayOf(Npcs.TURAEL, Npcs.VANNAKA, Npcs.MAZCHNA)

/**
 * Initializes rewards shop  option for the slayer masters.
 */
masters.forEach {
    on_npc_option(npc = it, option = "rewards") {
        openBuyInterface(player)
    }
}

/**
 * Slayer Points Shop Interface
 */
fun openBuyInterface(p: Player) {
    p.openInterface(BUY_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    p.setComponentText(BUY_INTERFACE, component = 20, text = getSlayerPointsText(p))
}

fun openLearnInterface(p: Player) {
    p.openInterface(LEARN_INTERFACE, InterfaceDestination.MAIN_SCREEN)
//    for (componentId in 1..20) {
//        p.setComponentText(LEARN_INTERFACE, component = componentId, text = "Test $componentId")
//    }
    p.setComponentText(BUY_INTERFACE, component = 18, text = getSlayerPointsText(p))
    updateLearnedComponents(p)
}


fun openAssignmentInterface(p: Player) {
    p.openInterface(ASSIGNMENT_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    p.setComponentText(BUY_INTERFACE, component = 19, text = getSlayerPointsText(p))
    refreshBlockedTasks(p)

}

/**
 * Get players slayer points as string value.
 */
fun getSlayerPointsText(player: Player): String =
    player.attr[SLAYER_POINTS]?.format() ?: "0"

fun refreshBlockedTasks(p: Player) {
    //TODO: Add block tasks text for assignment Interface
}

fun updateLearnedComponents(player: Player) {
    listOf(
        BROAD_FLETCHING to 90,
        AQUANTIES to 91,
        QUICK_BLOWS to 97,
        ICE_STRYKER_NO_CAPE to 98,
        CRAFT_ROS to 99,
        SLAYER_HELM_CREATION to 100
    ).forEach { (attribute, component) ->
        if (player.attr.has(attribute)) {
            player.setComponentText(LEARN_INTERFACE, component = component, text = "Learned")
        }
    }
}

/**
 * Buy IF buttons
 */

val buyButtonIds = arrayOf(16, 17, 32, 33, 34, 35, 36)

buyButtonIds.forEach {
    on_button(BUY_INTERFACE, component = it) {
        when(it) {
            16 -> openLearnInterface(player)
            17 -> openAssignmentInterface(player)
            /**
            32 -> TODO: Buy Slayer XP
            33 -> TODO: Ring of slaying
            34 -> TODO: Runes for slayer dart
            35 -> TODO: Broad bolts
            36 -> TODO: Broad arrows
            **/
        }
    }
}

/**
 * Learn IF buttons
 */

val learnButtonIds = arrayOf(14, 15, 73, 74, 75, 76, 77, 78)

learnButtonIds.forEach {
    on_button(LEARN_INTERFACE, component = it) {
        when(it) {
            14 -> openAssignmentInterface(player)
            15 -> openBuyInterface(player)
            /**
            73 -> TODO: Aquanites
            74 -> TODO: Quick blow
            75 -> TODO: Ice Strykerwyrm technique
            76 -> TODO: Broad bolts
            77 -> TODO: ROS
            78 -> TODO: Slayer Helm
             **/
        }
    }
}

/**
 * Learn IF buttons
 */

val assignmentButtonIds = arrayOf(14, 15, 73, 74, 75, 76, 77, 78)

assignmentButtonIds.forEach {
    on_button(ASSIGNMENT_INTERFACE, component = it) {
        when(it) {
            14 -> openLearnInterface(player)
            15 -> openBuyInterface(player)
            /**
            73 -> TODO: Aquanites
            74 -> TODO: Quick blow
            75 -> TODO: Ice Strykerwyrm technique
            76 -> TODO: Broad bolts
            77 -> TODO: ROS
            78 -> TODO: Slayer Helm
             **/
        }
    }
}