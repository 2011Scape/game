package gg.rsmod.plugins.content.objs.pottery

import gg.rsmod.plugins.content.skills.crafting.pottery.FirePotteryAction
import gg.rsmod.plugins.content.skills.crafting.pottery.PotteryData

val POTTERY_OVEN =
    listOf(
        Objs.POTTERY_OVEN,
        Objs.POTTERY_OVEN_11601,
        Objs.POTTERY_OVEN_3198,
        Objs.POTTERY_OVEN_4308,
        Objs.POTTERY_OVEN_34802,
        Objs.POTTERY_OVEN_5087,
        Objs.POTTERY_OVEN_61334,
    )

val potteryData = PotteryData.values
val unfiredPottery = potteryData.flatMap { it.products.toList() }

POTTERY_OVEN.forEach {
    on_obj_option(obj = it, option = "fire") {
        handlePotteryOven(player)
    }

    unfiredPottery.filterNot { data -> data.fired == -1 }.forEach { unfiredUrn ->
        val urn = unfiredUrn.unfired
        on_item_on_obj(obj = it, item = urn) {
            handlePotteryOven(player, urn)
        }
    }
}

fun handlePotteryOven(
    player: Player,
    itemUsed: Int? = null,
) {
    val inventory = player.inventory
    val unfiredList = mutableListOf<Int>()
    if (itemUsed == null) {
        val def = player.world.definitions
        inventory.rawItems
            .filter {
                it?.getName(def)?.lowercase()?.contains("(unf)") == true ||
                    it?.getName(def)?.contains("unfired", ignoreCase = true) == true
            }.forEach {
                if (!unfiredList.contains(it?.id)) {
                    unfiredList.add(it!!.id)
                }
            }
        if (unfiredList.isEmpty()) {
            player.message("You don't have any unfired items in your inventory.")
            return
        }
        player.queue {
            produceItemBox(*unfiredList.toIntArray(), logic = ::firePottery)
        }
    } else {
        player.queue {
            produceItemBox(itemUsed, maxItems = inventory.getItemCount(itemUsed), logic = ::firePottery)
        }
    }
}

fun firePottery(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = PotteryData.findPotteryItemByItem(item)!!
    player.queue { FirePotteryAction.firePottery(this, def, amount) }
}
