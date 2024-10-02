package gg.rsmod.plugins.content.skills.crafting.glassblowing

val glass = GlassData.values
val glassDefinitions = GlassData.glassDefinitions

on_item_on_item(item1 = Items.GLASSBLOWING_PIPE, item2 = Items.MOLTEN_GLASS) {
    val products = glass.map { it.id }.toIntArray()
    val itemNames =
        glass
            .map {
                val itemName =
                    player.world.definitions
                        .get(ItemDef::class.java, it.id)
                        .name
                if (player.skills.getCurrentLevel(Skills.CRAFTING) < it.levelRequired) {
                    "<col=ff0000>$itemName</col>"
                } else {
                    itemName
                }
            }.toTypedArray()
    val levels =
        glass
            .map {
                if (player.skills.getCurrentLevel(Skills.CRAFTING) < it.levelRequired) {
                    "<col=ff0000>Level ${it.levelRequired}</col>"
                } else {
                    ""
                }
            }.toTypedArray()
    player.queue {
        produceItemBox(
            *products,
            option = SkillDialogueOption.MAKE,
            title = "Choose how many you wish to make,<br>then click on the item to begin.",
            names = itemNames,
            extraNames = levels,
            logic = ::glassBlow,
        )
    }
}

fun glassBlow(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = glassDefinitions[item] ?: return
    player.queue(TaskPriority.WEAK) { GlassblowingAction.craft(this, def, amount) }
}
