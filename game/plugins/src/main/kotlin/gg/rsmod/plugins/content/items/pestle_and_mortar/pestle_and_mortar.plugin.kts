package gg.rsmod.plugins.content.items.pestle_and_mortar

val data = PestleAndMortarData.values
val definitions = PestleAndMortarData.definitions
val sources = data.map { data -> data.source }.toIntArray()

// Iterate over each source item in the 'sources' array
sources.forEach { source ->
    // Check if the player uses the source item on a pestle and mortar
    on_item_on_item(Items.PESTLE_AND_MORTAR, source) {
        // If the player only has one of the source item, grind it immediately
        if (player.inventory.getItemCount(source) == 1) {
            grindItem(player, definitions[source]!!.result, 1)
            return@on_item_on_item
        }
        // Otherwise, produce a dialogue box asking the player how many of the source item they want to grind
        player.queue {
            // Retrieve the 'PestleAndMortarDefinition' for the given source item from the 'definition' map
            // and use it to produce the item box
            produceItemBox(definitions[source]!!.result, option = SkillDialogueOption.MAKE, logic = ::grindItem)
        }
    }
}

// Function to grind an item with a pestle and mortar
fun grindItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    // Find the 'PestleAndMortarDefinition' that produces the given item
    // by searching through the 'data' array of all possible definitions
    val def = data.firstOrNull { it.result == item } ?: return
    // Add a 'GrindAction' task to the player's task queue to grind the item
    player.queue(TaskPriority.WEAK) { GrindAction.grind(this, def, amount) }
}
