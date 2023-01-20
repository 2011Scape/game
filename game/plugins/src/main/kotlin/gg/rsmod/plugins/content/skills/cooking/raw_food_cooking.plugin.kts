package gg.rsmod.plugins.content.skills.cooking


val cookingData = CookingData.values
val cookingDefinitions = CookingData.cookingDefinitions
val rawIds = cookingData.map { data -> data.raw }.toIntArray()

rawIds.forEach {
    val item = it
    on_item_on_obj(obj = Objs.COOKING_RANGE, item = item) {
        player.queue {
            produceItemBox(item, option = SkillDialogueOption.COOK, title = "Choose how many you wish to cook,<br>then click on the item to begin.", logic = ::cookItem)
        }
    }
}

fun cookItem(player: Player, item: Int, amount: Int) {
    val def = cookingDefinitions[item] ?: return
    player.queue { CookingAction.cook(this, def, amount) }
}