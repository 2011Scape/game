import gg.rsmod.plugins.content.skills.summoning.SummoningPouchData
import gg.rsmod.plugins.content.skills.summoning.SummoningScrollData
import kotlin.math.min

on_npc_option(Npcs.BOGROG, "swap") {
    openPouchTradeInInterface(player)
}

// Pouch tab
on_button(78, 24) {
    openPouchTradeInInterface(player)
}
// Scroll tab
on_button(78, 20) {
    openScrollTradeInInterface(player)
}

// Scroll trading
on_button(78, 14) {
    val option = player.getInteractingOption()
    val scrollId = player.getInteractingItemId()
    val scrollSlotNum = player.getInteractingSlot()
    val scrollData = SummoningScrollData.getDataByScrollId(scrollId)

    if (scrollData == null) {
        val greyedScrollData = SummoningScrollData.getDataByGreyedScrollIdOrSlotNum(scrollId, scrollSlotNum)
        if (greyedScrollData !=
            null
        ) {
            if (greyedScrollData.numNeededToSwap == 1) {
                player.message("You will receive ${greyedScrollData.numSwapShards} shards per scroll.")
            }
            else {
                player.message("You will receive 1 shard for every ${greyedScrollData.numNeededToSwap} scrolls.")
            }
        }
        return@on_button
    }

    val maxSwappable = getMaxSwappable(player, scrollData)

    when (option) {
        // Value
        3 -> {
            if (scrollData.numNeededToSwap == 1) {
                player.message("You will receive ${scrollData.numSwapShards} shards per scroll.")
            }
            else {
                player.message("You will receive 1 shard for every ${scrollData.numNeededToSwap} scrolls.")
            }
        }
        // Trade 1
        2 -> {
            swapForShards(player, scrollData, min(1, maxSwappable))
        }
        // Trade 5
        4 -> {
            swapForShards(player, scrollData, min(5, maxSwappable))
        }
        // Trade 10
        5 -> {
            swapForShards(player, scrollData, min(10, maxSwappable))
        }
        // Trade X
        6 -> {
            player.closeInterface(78)
            player.queue {
                val chosen = this.inputInt()
                swapForShards(player, scrollData, min(chosen, maxSwappable))
            }
        }
        // Trade All
        7 -> {
            swapForShards(player, scrollData, maxSwappable)
        }
    }
}

// Pouch trading
on_button(78, 15) {
    val option = player.getInteractingOption()
    val pouchId = player.getInteractingItemId()
    val pouchSlotNum = player.getInteractingSlot()
    val pouchData = SummoningPouchData.getDataByPouchId(pouchId)

    if (pouchData == null) {
        val greyedPouchData = SummoningPouchData.getDataByGreyedPouchIdOrSlotNum(pouchId, pouchSlotNum)
        if (greyedPouchData !=
            null
        ) {
            player.message("You will receive ${greyedPouchData.numSwapShards} shards per pouch.")
        }
        return@on_button
    }

    val maxSwappable = getMaxSwappable(player, pouchData)

    when (option) {
        // Value
        3 -> {
            player.message("You will receive ${pouchData.numSwapShards} shards per scroll.")
        }
        // Trade 1
        2 -> {
            swapForShards(player, pouchData, min(1, maxSwappable))
        }
        // Trade 5
        4 -> {
            swapForShards(player, pouchData, min(5, maxSwappable))
        }
        // Trade 10
        5 -> {
            swapForShards(player, pouchData, min(10, maxSwappable))
        }
        // Trade X
        6 -> {
            player.closeInterface(78)
            player.queue {
                val chosen = this.inputInt()
                swapForShards(player, pouchData, min(chosen, maxSwappable))
            }
        }
        // Trade All
        7 -> {
            swapForShards(player, pouchData, maxSwappable)
        }
    }
}

/**
 * Finds the maximum number of scrolls a player can trade in.
 *
 * @param player: The [Player] swapping in the scrolls
 * @param scrollData: The [SummoningScrollData] of the scroll being traded in
 *
 * @returns: The maximum number of scrolls the player can trade in
 */
fun getMaxSwappable(player: Player, scrollData: SummoningScrollData): Int {
    val scrollsHeld = player.inventory.getItemCount(scrollData.scroll)

    return scrollsHeld
}

/**
 * Finds the maximum number of pouches a player can trade in.
 *
 * @param player: The [Player] swapping in the scrolls
 * @param pouchData: The [SummoningPouchData] of the pouch being traded in
 *
 * @returns: The maximum number of pouches the player can trade in
 */
fun getMaxSwappable(player: Player, pouchData: SummoningPouchData): Int {
    val notedPouchId = Item(pouchData.pouch).toNoted(player.world.definitions).id
    val pouchesHeld = player.inventory.getItemCount(pouchData.pouch)
    val notedPouchesHeld = player.inventory.getItemCount(notedPouchId)

    return pouchesHeld + notedPouchesHeld
}

/**
 * Trades in the given scroll for shards.
 *
 * Will only trade in up to the given number of scrolls. I.e. if a player tries to trade 5 for a scroll that needs 3
 * per shard, and they have 5 in their inventory, they will only trade in 3 scrolls.
 *
 * @param player: The [Player] trading in the scrolls
 * @param scrollData: The [SummoningScrollData] of the scroll being traded in
 * @param number: The max number of scrolls being traded in
 */
fun swapForShards(player: Player, scrollData: SummoningScrollData, number: Int) {
    val actualNumber = number - (number % scrollData.numNeededToSwap)
    val stacksTraded = actualNumber / scrollData.numNeededToSwap
    player.inventory.remove(scrollData.scroll, actualNumber)
    player.inventory.add(Items.SPIRIT_SHARDS, scrollData.numSwapShards * stacksTraded)
}

/**
 * Trades in the given pouches for shards.
 *
 * @param player: The [Player] trading in the scrolls
 * @param pouchData: The [SummoningPouchData] of the pouch being traded in
 * @param number: The max number of scrolls being traded in
 */
fun swapForShards(player: Player, pouchData: SummoningPouchData, number: Int) {
    val notedPouchId = Item(pouchData.pouch).toNoted(player.world.definitions).id
    val unotedScrollCount = player.inventory.getItemCount(pouchData.pouch)
    val remainingCount = number - unotedScrollCount
    player.inventory.remove(pouchData.pouch, unotedScrollCount)
    player.inventory.remove(notedPouchId, remainingCount)

    player.inventory.add(Items.SPIRIT_SHARDS, pouchData.numSwapShards * number)
}
