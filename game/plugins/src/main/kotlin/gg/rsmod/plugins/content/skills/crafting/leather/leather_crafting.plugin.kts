package gg.rsmod.plugins.content.skills.crafting.leather

val leather = LeatherData.values
val leatherDefinitions = LeatherData.leatherDefinitions
val rawIds = leather.map { it.raw }.toIntArray()

rawIds.forEach {
    val item = it
    on_item_on_item(item1 = Items.NEEDLE, item2 = item) {
        val products = leatherDefinitions[item]!!.products.map { result -> result.resultItem }.toIntArray()
        player.queue {
            produceItemBox(
                *products,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make,<br>then click on the item to begin.",
                logic = ::craftLeatherItem,
            )
        }
    }
}

fun craftLeatherItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    player.queue(TaskPriority.WEAK) { LeatherAction.craft(this, item, amount) }
}
