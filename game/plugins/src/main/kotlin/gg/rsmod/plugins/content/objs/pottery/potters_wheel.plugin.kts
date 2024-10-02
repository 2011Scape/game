package gg.rsmod.plugins.content.objs.pottery

import gg.rsmod.plugins.content.skills.crafting.pottery.FormPotteryAction
import gg.rsmod.plugins.content.skills.crafting.pottery.PotteryData

val POTTERS_WHEELS =
    listOf(
        Objs.POTTERS_WHEEL,
        Objs.POTTERS_WHEEL_20375,
        Objs.POTTERS_WHEEL_34801,
        Objs.POTTERS_WHEEL_3200,
        Objs.POTTERS_WHEEL_4310,
        Objs.POTTERS_WHEEL_5092,
        Objs.POTTERS_WHEEL_61335,
        Objs.POTTERS_WHEEL_5093,
    )

val potteryData = PotteryData.values()

POTTERS_WHEELS.forEach {
    on_obj_option(obj = it, option = "form") {
        handlePottersWheel(player)
    }

    on_item_on_obj(obj = it, item = Items.SOFT_CLAY) {
        handlePottersWheel(player)
    }
}

fun handlePottersWheel(player: Player) {
    val maxAmount = player.inventory.getItemCount(Items.SOFT_CLAY)
    val pottery = potteryData.filter { it == PotteryData.NOT_URNS }.flatMap { it.products.map { data -> data } }
    val products = pottery.map { it.unfired }.toIntArray()
    val names =
        pottery
            .map { data ->
                if (data.fired == -1) {
                    "Urns"
                } else {
                    player.world.definitions
                        .get(ItemDef::class.java, data.fired)
                        .name
                }
            }.toTypedArray()

    player.queue {
        produceItemBox(*products, names = names, maxItems = maxAmount, logic = ::formItem)
    }
}

fun formItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = PotteryData.findPotteryDataByItem(item)!!
    val data = PotteryData.findPotteryItemByItem(item)!!
    if (def == PotteryData.NOT_URNS && data.fired == -1) {
        val displayItems =
            listOf(
                Items.COOKING_URN_UNF,
                Items.FISHING_URN_UNF,
                Items.MINING_URN_UNF,
                Items.SMELTING_URN_UNF,
                Items.WOODCUTTING_URN_UNF,
                Items.ACCURSED_URN_UNF,
            )
        val categories =
            listOf("Cooking urns", "Fishing urns", "Mining urns", "Smelting urns", "Woodcutting urns", "Prayer urns")
        val maxAmount = player.inventory.getItemCount(Items.SOFT_CLAY) / 2
        player.queue {
            produceItemBox(
                *displayItems.toIntArray(),
                title = "Which skill would you like to make urns for?",
                maxItems = maxAmount,
                names = categories.toTypedArray(),
                logic = ::pickUrnLevel,
            )
        }
    } else {
        player.queue { FormPotteryAction.formPottery(this, PotteryData.findPotteryItemByItem(item)!!, amount) }
    }
}

fun pickUrnLevel(
    player: Player,
    item: Int,
    amount: Int,
) {
    PotteryData.findPotteryDataByItem(item)!!.urn
    val urnData = PotteryData.findPotteryDataByItem(item)!!.products
    val products = urnData.map { product -> product.unfired }
    val names =
        urnData.map { data ->
            player.world.definitions
                .get(ItemDef::class.java, data.unfired)
                .name
                .dropLast(6)
        }
    val maxAmount = player.inventory.getItemCount(Items.SOFT_CLAY) / 2
    player.queue {
        produceItemBox(
            *products.toIntArray(),
            title = "Which urn would you like to make?",
            maxItems = maxAmount,
            names = names.toTypedArray(),
            logic = ::formUrn,
        )
    }
}

fun formUrn(
    player: Player,
    item: Int,
    amount: Int,
) {
    player.queue { FormPotteryAction.formPottery(this, PotteryData.findPotteryItemByItem(item)!!, amount) }
}
