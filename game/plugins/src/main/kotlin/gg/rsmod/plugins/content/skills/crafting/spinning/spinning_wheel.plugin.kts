package gg.rsmod.plugins.content.skills.crafting.spinning


val spinningData = SpinningData.values
val definitions = SpinningData.spinningDefinitions

val wheels =
    listOf(
        Objs.SPINNING_WHEEL,
        Objs.SPINNING_WHEEL_4309,
        Objs.SPINNING_WHEEL_5707,
        Objs.SPINNING_WHEEL_8748,
        Objs.SPINNING_WHEEL_20365,
        Objs.SPINNING_WHEEL_21304,
        Objs.SPINNING_WHEEL_25824,
        Objs.SPINNING_WHEEL_36970,
        Objs.SPINNING_WHEEL_37476,
        Objs.SPINNING_WHEEL_49934,
        Objs.SPINNING_WHEEL_49935,
        Objs.SPINNING_WHEEL_49936,
        Objs.SPINNING_WHEEL_53749,
        Objs.SPINNING_WHEEL_55566,
    )

wheels.forEach { wheel ->
    on_obj_option(wheel, option = "Spin") {
        val products = spinningData.map { it.product }.toTypedArray()
        val rawNames = spinningData.map { "(${it.rawName})" }.toTypedArray()
        player.queue {
            produceItemBox(
                *products.toIntArray(),
                option = SkillDialogueOption.MAKE,
                title = "Choose how many you wish to cut,<br>then click on the item to begin.",
                extraNames = rawNames,
                logic = ::spinItem,
            )
        }
    }
}

fun spinItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = definitions[item] ?: return
    player.queue(TaskPriority.WEAK) { SpinningAction.spin(this, def, amount) }
}
