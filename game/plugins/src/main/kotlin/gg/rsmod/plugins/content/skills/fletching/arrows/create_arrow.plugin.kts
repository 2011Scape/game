package gg.rsmod.plugins.content.skills.fletching.arrows

val arrowDefinitions = ArrowData.arrowDefinitions
val arrowAction = ArrowAction()

arrowDefinitions.values.forEach { arrow ->
    on_item_on_item(item1 = arrow.tips, item2 = Items.HEADLESS_ARROW) {
        val arrowTipCount = player.inventory.getItemCount(arrow.tips)
        val headlessArrowCount = player.inventory.getItemCount(Items.HEADLESS_ARROW)
        val setCount = minOf(arrowTipCount / arrow.amount, headlessArrowCount / arrow.amount)
        player.queue {
            produceItemBox(
                arrow.product,
                maxItems = (1 + setCount).coerceAtMost(10),
                option = SkillDialogueOption.MAKE_SETS,
                title = "Choose how many sets of 15 arrows you<br>wish to make, then click on the item to begin.",
                extraNames = arrayOf("(Set of 15)"),
                logic = ::start,
            )
        }
    }
}

fun start(
    player: Player,
    arrow: Int,
    amount: Int,
) {
    val def = arrowDefinitions[arrow] ?: return
    player.queue(TaskPriority.WEAK) {
        arrowAction.createArrow(this, def, amount)
    }
}
