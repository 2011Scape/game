package gg.rsmod.plugins.content.inter.slayer

import gg.rsmod.game.model.attr.*
import gg.rsmod.plugins.content.skills.slayer.getSlayerAssignment
import gg.rsmod.plugins.content.skills.slayer.removeSlayerAssignment

/**
 * @author Harley <github.com/HarleyGilpin>
 */

val BUY_INTERFACE = 164
val LEARN_INTERFACE = 378
val ASSIGNMENT_INTERFACE = 161

val masters = arrayOf(Npcs.TURAEL, Npcs.VANNAKA, Npcs.MAZCHNA)

/**
 * Initializes rewards shop option for the slayer masters.
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
    p.setComponentText(LEARN_INTERFACE, component = 79, text = getSlayerPointsText(p))
    updateLearnedComponents(p)
}

fun openAssignmentInterface(p: Player) {
    p.openInterface(ASSIGNMENT_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    p.setComponentText(ASSIGNMENT_INTERFACE, component = 19, text = getSlayerPointsText(p).format())
    refreshBlockedTasks(p)
}

fun refreshPoints(
    player: Player,
    interfaceId: Int,
) {
    val slayerPoints = getSlayerPointsText(player)
    when (interfaceId) {
        BUY_INTERFACE -> player.setComponentText(BUY_INTERFACE, component = 20, text = slayerPoints)
        LEARN_INTERFACE -> player.setComponentText(LEARN_INTERFACE, component = 79, text = slayerPoints)
        ASSIGNMENT_INTERFACE -> player.setComponentText(ASSIGNMENT_INTERFACE, component = 19, text = slayerPoints)
        else -> {} // No default action
    }
}

/**
 * Get players slayer points as string value.
 */
fun getSlayerPointsText(player: Player): String = player.attr[SLAYER_POINTS]?.format() ?: "0"

// Function to initialize the blocked tasks list if it's not present
fun initBlockedTasks(player: Player) {
    if (!player.attr.has(BLOCKED_TASKS)) {
        // Initialize with an empty list
        player.attr[BLOCKED_TASKS] = mutableListOf()
    }
}

fun refreshBlockedTasks(player: Player) {
    // Existing code to handle current assignment display
    if (player.attr.has(SLAYER_ASSIGNMENT)) {
        player.setComponentText(
            ASSIGNMENT_INTERFACE,
            component = 23,
            text = "Cancel task of ${player.getSlayerAssignment()!!.identifier}",
        )
        player.setComponentText(
            ASSIGNMENT_INTERFACE,
            component = 24,
            text = "Never assign ${player.getSlayerAssignment()!!.identifier} again",
        )
    } else {
        player.setComponentText(
            ASSIGNMENT_INTERFACE,
            component = 23,
            text = "Reassign current mission",
        )
        player.setComponentText(
            ASSIGNMENT_INTERFACE,
            component = 24,
            text = "Permanently remove current",
        )
    }

    // Initialize blocked tasks list if it doesn't exist
    initBlockedTasks(player)
    val blockedTasks = player.attr[BLOCKED_TASKS] ?: mutableListOf()

    // Define the component IDs for the text fields corresponding to the blocked task slots
    val textComponentIds = arrayOf(36, 30, 31, 32, 33)

    // Loop through each possible slot and update the component text accordingly
    for (slot in textComponentIds.indices) {
        val textComponentId = textComponentIds[slot]
        val taskText =
            if (slot < blockedTasks.size) {
                // If there's a blocked task for this slot, display its identifier
                blockedTasks[slot]
            } else {
                "Empty"
            }
        player.setComponentText(ASSIGNMENT_INTERFACE, component = textComponentId, text = taskText)
    }

    // Update slayer points text
    player.setComponentText(ASSIGNMENT_INTERFACE, component = 19, text = getSlayerPointsText(player))
}

// Function to add a task to the block list
fun blockTask(
    player: Player,
    task: String,
) {
    initBlockedTasks(player)
    val blockedTasks = player.attr[BLOCKED_TASKS]!!

    if (blockedTasks.size >= 5) {
        player.message("You cannot block more than 5 tasks.")
        return
    }
    if (blockedTasks.contains(task)) {
        player.message("You have already blocked the task: $task.")
        return
    }
    if (player.getSlayerPointsCount() < 100) {
        player.message("You need 100 slayer points to block a task.")
        return
    }

    blockedTasks.add(task)
    player.removeSlayerAssignment()
    player.subtractSlayerPoints(100)
    player.message("You have blocked the task: $task.")
    refreshPoints(player, ASSIGNMENT_INTERFACE)
    refreshBlockedTasks(player)
}

// Function to remove a task from the block list
fun unblockTask(
    player: Player,
    index: Int,
) {
    initBlockedTasks(player)
    val blockedTasks = player.attr[BLOCKED_TASKS]!!

    if (index in blockedTasks.indices) {
        val unblockedTask = blockedTasks.removeAt(index)
        player.message("You have unblocked the task: $unblockedTask.")
        refreshBlockedTasks(player)
    }
}

// Function to add the current task to the block list
fun blockCurrentTask(player: Player) {
    val currentAssignment = player.getSlayerAssignment()
    if (currentAssignment != null) {
        blockTask(player, currentAssignment.identifier)
        // player.removeSlayerAssignment()
    } else {
        player.message("You do not have a current slayer assignment to block.")
    }
}

fun updateLearnedComponents(player: Player) {
    listOf(
        BROAD_FLETCHING to 90,
        AQUANTIES to 91,
        QUICK_BLOWS to 97,
        ICE_STRYKER_NO_CAPE to 98,
        CRAFT_ROS to 99,
        SLAYER_HELM_CREATION to 100,
    ).forEach { (attribute, component) ->
        if (player.attr.has(attribute)) {
            player.setComponentText(LEARN_INTERFACE, component = component, text = "Learned")
        }
    }
}

/**
 * Slayer Point Shop Interface: Buy Tab
 */

val buyButtonIds = arrayOf(16, 17, 24, 26, 28, 32, 33, 34, 35, 36, 37, 39)

val NOT_ENOUGH_SPACE = "Not enough space in your inventory!"

buyButtonIds.forEach { buttonId ->
    on_button(BUY_INTERFACE, component = buttonId) {
        handleBuyInterface(player, buttonId)
    }
}

fun handleBuyInterface(
    player: Player,
    buttonId: Int,
) {
    when (buttonId) {
        16 -> openLearnInterface(player)
        17 -> openAssignmentInterface(player)
        24, 32 -> handleSlayerXpPurchase(player)
        26, 33 -> handleRingOfSlayingPurchase(player)
        28, 36 -> handleSlayerDartRunesPurchase(player)
        37, 34 -> handleBroadBoltsPurchase(player)
        39, 35 -> handleBroadArrowsPurchase(player)
    }
}

fun handleSlayerXpPurchase(player: Player) {
    if (player.getSlayerPointsCount() >= 400) {
        player.addXp(Skills.SLAYER, 10_000.00) // Slayer XP
        player.subtractSlayerPoints(400)
        refreshPoints(player, BUY_INTERFACE)
    } else {
        player.message("You need 400 points for 10,000 Slayer experience.")
    }
}

fun handleRingOfSlayingPurchase(player: Player) {
    if (player.getSlayerPointsCount() < 75) {
        player.message("You need 75 points for Ring of Slaying (8).")
        return
    }
    if (player.inventory.hasSpace && player.getSlayerPointsCount() >= 75) {
        player.inventory.add(Items.RING_OF_SLAYING_8, 1)
        player.subtractSlayerPoints(75)
        refreshPoints(player, BUY_INTERFACE)
    } else {
        player.message(NOT_ENOUGH_SPACE)
    }
}

fun handleSlayerDartRunesPurchase(player: Player) {
    if (player.getSlayerPointsCount() < 35) {
        player.message("You need 35 points for 250 slayer dart runes.")
        return
    }
    if (player.inventory.freeSlotCount < 2) {
        player.message(NOT_ENOUGH_SPACE)
        return
    }
    if (player.getSlayerPointsCount() >= 35 && player.inventory.freeSlotCount >= 2) {
        player.inventory.add(Items.MIND_RUNE, 1000)
        player.inventory.add(Items.DEATH_RUNE, 250)
        player.subtractSlayerPoints(35)
        refreshPoints(player, BUY_INTERFACE)
    }
}

fun handleBroadBoltsPurchase(player: Player) {
    if (player.getSlayerPointsCount() < 35) {
        player.message("You need 35 points for 250 broad bolts")
        return
    }
    if (player.inventory.hasSpace && player.getSlayerPointsCount() >= 35) {
        player.inventory.add(Items.BROADTIPPED_BOLTS, 250)
        player.subtractSlayerPoints(35)
        refreshPoints(player, BUY_INTERFACE)
    } else {
        player.message(NOT_ENOUGH_SPACE)
    }
}

fun handleBroadArrowsPurchase(player: Player) {
    if (player.getSlayerPointsCount() < 35) {
        player.message("You need 35 points for 250 broad arrows")
        return
    }
    if (player.inventory.hasSpace && player.getSlayerPointsCount() >= 35) {
        player.inventory.add(Items.BROAD_ARROW, 250)
        player.subtractSlayerPoints(35)
        refreshPoints(player, BUY_INTERFACE)
    } else {
        player.message(NOT_ENOUGH_SPACE)
    }
}

/**
 * Slayer Point Shop Interface: Learn Tab
 */

data class Ability(
    val id: Int,
    val cost: Int,
    val attribute: AttributeKey<Boolean>,
)

on_button(LEARN_INTERFACE, 14) {
    openAssignmentInterface(player)
}

on_button(LEARN_INTERFACE, 15) {
    openBuyInterface(player)
}

val abilities =
    mapOf(
        73 to Ability(73, 50, AQUANTIES),
        74 to Ability(74, 400, QUICK_BLOWS),
        75 to Ability(75, 2000, ICE_STRYKER_NO_CAPE),
        76 to Ability(76, 300, BROAD_FLETCHING),
        77 to Ability(77, 300, CRAFT_ROS),
        78 to Ability(78, 400, SLAYER_HELM_CREATION),
    )

val learnButtonIds = abilities.keys.toIntArray()

learnButtonIds.forEach { buttonId ->
    on_button(LEARN_INTERFACE, component = buttonId) {
        abilities[buttonId]?.let { ability ->
            purchaseAbility(player, ability.cost, ability.attribute)
        }
    }
}

/**
 * Handles the purchasing of an ability, given its cost and attribute key.
 */

fun purchaseAbility(
    player: Player,
    abilityCost: Int,
    abilityAttribute: AttributeKey<Boolean>,
) {
    if (player.hasAbility(abilityAttribute)) {
        player.message("You have already learned this ability.")
        return
    }

    if (!player.canAffordAbility(abilityCost)) {
        player.message("You need $abilityCost points to learn this ability.")
        return
    }

    player.learnAbility(abilityCost, abilityAttribute)
}

fun Player.hasAbility(abilityAttribute: AttributeKey<Boolean>) = attr.has(abilityAttribute)

fun Player.canAffordAbility(abilityCost: Int) = getSlayerPointsCount() >= abilityCost

fun Player.learnAbility(
    abilityCost: Int,
    abilityAttribute: AttributeKey<Boolean>,
) {
    subtractSlayerPoints(abilityCost)
    attr[abilityAttribute] = true
    refreshPoints(this, LEARN_INTERFACE)
    message("You have successfully learned the new ability!")
    updateLearnedComponents(this)
}

/**
 * Slayer Point Shop Interface: Assignment Tab
 */

val assignmentButtonIds = arrayOf(14, 15, 23, 26)

assignmentButtonIds.forEach {
    on_button(ASSIGNMENT_INTERFACE, component = it) {
        when (it) {
            14 -> openLearnInterface(player)
            15 -> openBuyInterface(player)
            23, 26 -> cancelSlayerTask(player)
        }
    }
}

// handlers for blocking tasks
val blockButtons = arrayOf(24, 27)
blockButtons.forEach { buttonId ->
    on_button(ASSIGNMENT_INTERFACE, component = buttonId) {
        blockCurrentTask(player)
    }
}

// handlers for unblocking tasks
val unblockButtons = arrayOf(37, 38, 39, 40, 41)
unblockButtons.forEachIndexed { index, buttonId ->
    on_button(ASSIGNMENT_INTERFACE, component = buttonId) {
        unblockTask(player, index)
    }
}

fun cancelSlayerTask(player: Player) {
    if (player.getSlayerPointsCount() < 30) {
        player.message("You need 30 points to cancel your current mission.")
        return
    }
    if (player.getSlayerPointsCount() >= 30 && player.attr.has(SLAYER_ASSIGNMENT)) {
        player.removeSlayerAssignment()
        player.subtractSlayerPoints(30)
        refreshPoints(player, ASSIGNMENT_INTERFACE)
        refreshBlockedTasks(player)
    } else {
        player.message("You must have a slayer task assigned to cancel it.")
    }
}
