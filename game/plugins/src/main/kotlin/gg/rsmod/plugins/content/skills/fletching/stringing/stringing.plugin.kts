package gg.rsmod.plugins.content.skills.fletching.stringing

val stringData = StringData.values()
val definitions = StringData.bowDefinitions
val bowIds = stringData.map { data -> data.bow_u }.toIntArray()

val bowStringAction = BowStringAction(world.definitions)

bowIds.forEach { bow_u ->
    on_item_on_item(item1 = Items.BOW_STRING, item2 = bow_u) {
        val stringItems =
            definitions[bow_u]?.values?.map { data -> data.product }?.toIntArray() ?: return@on_item_on_item
        val stringNames = definitions[bow_u]?.values?.map { data -> data.itemName } ?: return@on_item_on_item
        player.queue {
            produceItemBox(
                *stringItems,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make, then<br>click on the chosen item to begin.",
                names = stringNames.toTypedArray(),
                logic = ::stringItem,
            )
        }
    }
}

fun stringItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    val bow_u =
        listOf(player.getInteractingItemId(), player.getInteractingOtherItemId()).firstOrNull {
            definitions.containsKey(it)
        } ?: return
    val stringOption = definitions[bow_u]?.get(item) ?: return
    player.queue(TaskPriority.WEAK) { bowStringAction.string(this, bow_u, stringOption, amount) }
}
