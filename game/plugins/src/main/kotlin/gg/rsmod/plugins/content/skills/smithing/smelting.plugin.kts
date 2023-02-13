package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.plugins.content.skills.smithing.data.SmeltingData

/**
 * The [Bar] enum values
 */
val bars = SmeltingData.values

/**
 * The map of [Bar] ids to their definition
 */
val barDefs = SmeltingData.barDefinitions

/**
 * An array of [Bar] item ids that may be smelted at any standard furnace
 */
val standardBarIds = bars.map { bar -> bar.id }.toIntArray()

/**
 * The smelting action instance
 */
val smelting = SmeltingAction(world.definitions)

/**
 * The set of 'standard' furnaces
 */
val standardFurnaces = setOf(
    Objs.FURNACE_45310
)

/**
 * Handles the smelting of the standard bars
 *
 * @param player    The player instance
 * @param item      The item the player is trying to smelt
 * @param amount    The number of bars the player is trying to smelt
 */
fun smeltItem(player: Player, item: Int, amount: Int = 28) {
    val def = barDefs[item] ?: return
    player.queue { smelting.smelt(this, def, amount) }
}

/**
 * Handle smelting at any 'standard' furnace
 */
standardFurnaces.forEach { furnace ->

    on_obj_option(obj = furnace, option = "smelt") { smeltStandard(player) }

    standardBarIds.forEach { on_item_on_obj(obj = furnace, item = it) { smeltStandard(player) } }

}

/**
 * Opens the proompt to smelt any standard bar
 *
 * @param player    The player instance
 */
fun smeltStandard(player: Player) {
    player.queue {
        produceItemBox(
            *standardBarIds,
            option = SkillDialogueOption.MAKE,
            title = "How many bars would you like to smelt?<br>Choose a number, then click the bar to begin.",
            logic = ::smeltItem
        )
    }
}