package gg.rsmod.plugins.content.skills.herblore.mixing

import gg.rsmod.plugins.content.skills.herblore.HerbData

val finishedPotionDefinitions = PotionData.definitions
val unfinishedPotionDefinitions = HerbData.unfinishedPotionDefinitions

val finishedAction = CreateFinishedPotionAction()
val unfinishedAction = CreateUnfinishedPotionAction()

unfinishedPotionDefinitions.values.forEach { potion ->
    on_item_on_item(item1 = potion.clean, item2 = Items.VIAL_OF_WATER) {
        player.queue(TaskPriority.STRONG) {
            produceItemBox(
                potion.unf,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make, then<br>click on the item to begin.",
                logic = ::startUnfinished,
            )
        }
    }
}

finishedPotionDefinitions.values.forEach { potion ->
    on_item_on_item(item1 = potion.primary, item2 = potion.secondary) {
        player.queue(TaskPriority.STRONG) {
            produceItemBox(
                potion.product,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make, then<br>click on the item to begin.",
                logic = ::startFinished,
            )
        }
    }
}

fun startUnfinished(
    player: Player,
    product: Int,
    amount: Int,
) {
    val unfinished = unfinishedPotionDefinitions[product] ?: return
    player.queue(TaskPriority.WEAK) {
        unfinishedAction.mix(this, unfinished, amount)
    }
}

fun startFinished(
    player: Player,
    product: Int,
    amount: Int,
) {
    val finished = finishedPotionDefinitions[product] ?: return
    player.queue(TaskPriority.WEAK) {
        finishedAction.mix(this, finished, amount)
    }
}
