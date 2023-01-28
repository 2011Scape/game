package gg.rsmod.plugins.content.skills.fletching.whittling

import gg.rsmod.game.model.attr.INTERACTING_ITEM_ID
import gg.rsmod.game.model.attr.OTHER_ITEM_ID_ATTR

val logData = LogData.values
val definitions = LogData.logDefinitions
val logIds = logData.map { data -> data.raw }.toIntArray()

val whittleAction = WhittleAction(world.definitions)

logIds.forEach { log ->
    on_item_on_item(item1 = Items.KNIFE, item2 = log) {
        val whittleItems = definitions[log]?.values?.map { data -> data.product }?.toIntArray() ?: return@on_item_on_item
        val whittleNames = definitions[log]?.values?.map { data -> data.itemName } ?: return@on_item_on_item
        player.queue {
            produceItemBox(
                *whittleItems,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make, then<br>click on the chosen item to begin.",
                names = whittleNames.toTypedArray(),
                logic = ::cutItem
            )
        }
    }
}

fun cutItem(player: Player, item: Int, amount: Int) {

    val log = listOf(player.attr[INTERACTING_ITEM_ID], player.attr[OTHER_ITEM_ID_ATTR]).firstOrNull {
        definitions.containsKey(it)
    } ?: return

    val whittleOption = definitions[log]?.get(item) ?: return

    player.queue { whittleAction.cut(this, log, whittleOption, amount) }
}