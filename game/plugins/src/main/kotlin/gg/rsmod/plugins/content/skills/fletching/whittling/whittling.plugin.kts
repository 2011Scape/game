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
        player.queue {
            produceItemBox(
                *whittleItems,
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to make,<br>then click on the item to begin.",
                logic = ::cutItem
            )
        }
    }
}

fun cutItem(player: Player, item: Int, amount: Int) {

    // Get the log that the player is using
    val log = if(definitions.containsKey(player.attr[INTERACTING_ITEM_ID])){
        player.attr[INTERACTING_ITEM_ID]
    } else if(definitions.containsKey(player.attr[OTHER_ITEM_ID_ATTR])){
        player.attr[OTHER_ITEM_ID_ATTR]
    } else {
        null
    }

    // If the log is null, return
    log ?: return

    // Get the item the player is whittling
    val whittleOption = definitions[log]?.get(item) ?: return

    player.queue { whittleAction.cut(this, log, whittleOption, amount) }
}