package gg.rsmod.plugins.content.skills.crafting.gems


val gems = GemData.values
val gemDefinitions = GemData.gemDefinitions
val standardGemIds = gems.map { gem -> gem.uncut }.toIntArray()

standardGemIds.forEach {
    val item = it
    on_item_on_item(item1 = Items.CHISEL, item2 = item) {

        if(player.inventory.getItemCount(item) == 1) {
            cutItem(player, item, 1)
            return@on_item_on_item
        }

        player.queue {
            produceItemBox(item, option = SkillDialogueOption.CUT, title = "Choose how many you wish to cut,<br>then click on the item to begin.", logic = ::cutItem)
        }
    }
}

fun cutItem(player: Player, item: Int, amount: Int) {
    val def = gemDefinitions[item] ?: return
    player.queue { GemAction.cut(this, def, amount) }
}