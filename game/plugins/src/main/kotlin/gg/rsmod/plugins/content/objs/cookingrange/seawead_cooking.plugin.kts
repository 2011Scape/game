package gg.rsmod.plugins.content.objs.cookingrange

val cookingRanges =
    setOf(
        Objs.COOKING_RANGE,
        Objs.COOKING_RANGE_16893,
        Objs.COOKING_RANGE_5275,
        Objs.COOKING_RANGE_8750,
        Objs.COOKING_RANGE_4172,
        Objs.COOKING_RANGE_34565,
        Objs.COOKING_RANGE_2859,
        Objs.STOVE,
        Objs.STOVE_12269,
        Objs.STOVE_9086,
        Objs.STOVE_9086,
        Objs.STOVE_42476,
        Objs.STOVE_43071,
        Objs.STOVE_45316,
    )

cookingRanges.forEach { range ->
    on_item_on_obj(obj = range, item = Items.SEAWEED) {
        val count = player.inventory.getItemCount(Items.SEAWEED)
        if (count <= 1) {
            handleSeaweedCooking(player, item = Items.SEAWEED, amount = 1)
        } else {
            player.queue {
                produceItemBox(
                    Items.SEAWEED,
                    title = "Choose how many you wish to cook,<br>then click on the item to begin.",
                    maxItems = count,
                    logic = ::handleSeaweedCooking,
                )
            }
        }
    }
}

fun handleSeaweedCooking(
    player: Player,
    item: Int,
    amount: Int,
) {
    val inventory = player.inventory
    player.lockingQueue {
        repeat(amount) {
            if (!inventory.contains(Items.SEAWEED)) {
                player.animate(-1)
                return@lockingQueue
            }

            if (inventory.remove(Items.SEAWEED, assureFullRemoval = true).hasSucceeded()) {
                player.animate(883)
                player.playSound(Sfx.FRY)
                inventory.add(Items.SODA_ASH, assureFullInsertion = true)
                player.filterableMessage("You burn the seaweed to soda ash.")
                player.addXp(Skills.COOKING, xp = 1.0)
                wait(3)
            }
        }
    }
}
