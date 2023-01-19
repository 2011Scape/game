package gg.rsmod.plugins.content.skills.crafting.spinning


val spinningData = SpinningData.values
val definitions = SpinningData.spinningDefinitions

on_obj_option(obj = Objs.SPINNING_WHEEL_36970, option = "Spin") {
    val products = spinningData.map { it.product }.toTypedArray()
    val rawNames = spinningData.map { "(${it.rawName})" }.toTypedArray()
    player.queue {
        produceItemBox(*products.toIntArray(), option = SkillDialogueOption.MAKE, title = "Choose how many you wish to cut,<br>then click on the item to begin.", extraNames = rawNames, logic = ::spinItem)
    }
}

fun spinItem(player: Player, item: Int, amount: Int) {
    val def = definitions[item] ?: return
    player.queue { SpinningAction.spin(this, def, amount) }
}