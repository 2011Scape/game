package gg.rsmod.plugins.content.skills.fletching.feathering

import gg.rsmod.game.model.attr.INTERACTING_ITEM_ID
import gg.rsmod.game.model.attr.OTHER_ITEM_ID_ATTR
import kotlin.math.ceil
import kotlin.math.min

val featheringDefinitions = FeatheringData.featheringDefinitions
val feathers = FeatheringData.possibleFeathers

val featherAction = FeatherAction(world.definitions)


featheringDefinitions.values.forEach { feathered ->
    feathers.forEach { feather ->
        on_item_on_item(item1 = feathered.raw, item2 = feather) {
            if(feathered.product == Items.HEADLESS_ARROW || feathered.product == Items.FLIGHTED_OGRE_ARROW) {
                featherShaft(player, feathered.product)
            } else {
                feather(player, feathered.product)
            }
        }
    }
}


fun featherShaft(player: Player, feathered: Int){
    val feather = feathers.firstOrNull { it == player.attr[INTERACTING_ITEM_ID] || it == player.attr[OTHER_ITEM_ID_ATTR] } ?: -1
    val featheredDef = featheringDefinitions[feathered] ?: return

    when (ceil(min(player.inventory.getItemCount(featheredDef.raw), (player.inventory.getItemCount(feather) / featheredDef.feathersRequired)) / featheredDef.amount.toDouble()).toInt()) {
        0 -> return
        1 -> feather(player, featheredDef.product)
        else -> player.queue {
            produceItemBox(feathered, option = SkillDialogueOption.MAKE_CUSTOM, maxItems = 10, title = "Choose how many sets of 15 headless arrows you<br>wish to make, then click on the item to begin.", extraNames = arrayOf("(Set of 15)"), logic = ::feather)
        }
    }
}


fun feather(player: Player, feathered: Int, amount: Int = 1) {
    val feather = feathers.firstOrNull { it == player.attr[INTERACTING_ITEM_ID] || it == player.attr[OTHER_ITEM_ID_ATTR] } ?: -1
    val featheredDef = featheringDefinitions[feathered] ?: return
    player.queue{ featherAction.feather(this, featheredDef, feather, amount) }
}

