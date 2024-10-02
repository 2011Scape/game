package gg.rsmod.plugins.content.skills.crafting.weaponry

val battlestaffs = BattlestaffData.values
val staffDefinitions = BattlestaffData.battlestaffDefinitions
val orbIds = battlestaffs.map { staff -> staff.orbId }.toIntArray()

orbIds.forEach {
    val item = it

    on_item_on_item(item1 = Items.BATTLESTAFF, item2 = item) {
        val def = staffDefinitions[item] ?: return@on_item_on_item
        if (player.inventory.getItemCount(item) == 1) {
            attachOrb(player, def.resultItem, amount = 1)
            return@on_item_on_item
        }
        player.queue {
            produceItemBox(def.resultItem, option = SkillDialogueOption.MAKE, logic = ::attachOrb)
        }
    }
}

fun attachOrb(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = battlestaffs.associateBy { it.resultItem }[item] ?: return
    player.queue(TaskPriority.WEAK) { BattlestaffAction.attach(this, def, amount) }
}
