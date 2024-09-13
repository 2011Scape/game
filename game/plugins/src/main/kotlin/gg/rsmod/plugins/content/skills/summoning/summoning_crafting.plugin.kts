import gg.rsmod.plugins.content.skills.summoning.SummoningPouchData
import gg.rsmod.plugins.content.skills.summoning.SummoningScrollData
import kotlin.math.min

on_button(672, 16) {
    val option = player.getInteractingOption()
    val pouchId = player.getInteractingItemId()
    val pouchSlotNum = player.getInteractingSlot()
    val pouchData = SummoningPouchData.getDataByPouchId(pouchId)

    if (pouchData == null) {
        val greyedPouchData = SummoningPouchData.getDataByGreyedPouchIdOrSlotNum(pouchId, pouchSlotNum)
        if (greyedPouchData !=
            null
        ) {
            player.message(
                SummoningPouchData.getIngredientString(greyedPouchData.pouch, player),
                ChatMessageType.GAME_MESSAGE,
            )
        }
        return@on_button
    }

    val maxCraftable = getMaxCraftable(player, pouchData)

    if (maxCraftable > 0) {
        when (option) {
            // Infuse 1
            3 -> {
                handleCrafting(player, 1, pouchData)
            }
            // Infuse 5
            2 -> {
                handleCrafting(player, min(5, maxCraftable), pouchData)
            }
            // Infuse 10
            4 -> {
                handleCrafting(player, min(10, maxCraftable), pouchData)
            }
            // Infuse X
            5 -> {
                player.closeInterface(672)
                player.queue {
                    val chosen = this.inputInt()
                    handleCrafting(player, min(chosen, maxCraftable), pouchData)
                }
            }
            // Infuse all
            6 -> {
                handleCrafting(player, maxCraftable, pouchData)
            }
            // List
            7 -> {
                player.message(SummoningPouchData.getIngredientString(pouchId, player), ChatMessageType.GAME_MESSAGE)
            }
        }
    } else {
        player.message(SummoningPouchData.getIngredientString(pouchId, player), ChatMessageType.GAME_MESSAGE)
    }
}

on_button(666, 16) {
    val option = player.getInteractingOption()
    val scrollId = player.getInteractingItemId()
    val scrollSlotNum = player.getInteractingSlot()
    val scrollData = SummoningScrollData.getDataByScrollId(scrollId)

    if (scrollData == null) {
        val greyedScrollData = SummoningScrollData.getDataByGreyedScrollIdOrSlotNum(scrollId, scrollSlotNum)
        if (greyedScrollData !=
            null
        ) {
            player.message(
                SummoningScrollData.getIngredientString(greyedScrollData.scroll, player),
                ChatMessageType.GAME_MESSAGE,
            )
        }
        return@on_button
    }

    val maxCraftable = getMaxCraftable(player, scrollData)

    if (maxCraftable > 0) {
        when (option) {
            // Infuse 1
            3 -> {
                handleCrafting(player, 1, scrollData)
            }
            // Infuse 5
            2 -> {
                handleCrafting(player, min(5, maxCraftable), scrollData)
            }
            // Infuse 10
            4 -> {
                handleCrafting(player, min(10, maxCraftable), scrollData)
            }
            // Infuse X
            5 -> {
                player.closeInterface(666)
                player.queue {
                    val chosen = this.inputInt()
                    handleCrafting(player, min(chosen, maxCraftable), scrollData)
                }
            }
            // Infuse all
            6 -> {
                handleCrafting(player, maxCraftable, scrollData)
            }
            // List
            7 -> {
                player.message(SummoningScrollData.getIngredientString(scrollId, player), ChatMessageType.GAME_MESSAGE)
            }
        }
    } else {
        player.message(SummoningScrollData.getIngredientString(scrollId, player), ChatMessageType.GAME_MESSAGE)
    }
}

on_button(672, 19) {
    openScrollInterface(player)
}

on_button(666, 18) {
    openPouchInterface(player)
}

/**
 * Crafts summoning scrolls and removes the pouches from the player's inventory
 *
 * @param player: The player
 * @param amount: The number of pouches to be crafted
 * @param scrollData: The [SummoningScrollData] associated with the pouch the player wants to craft
 */
fun handleCrafting(
    player: Player,
    amount: Int,
    scrollData: SummoningScrollData,
) {
    val SCROLL_INFUSE_ANIM = 8500

    if (amount < 1) return

    if (player.interfaces.isVisible(666)) {
        player.closeInterface(666)
    }

    var total = 0
    player.inventory.forEach { item ->
        if (total >= amount) {
            return@forEach
        }

        if (item != null && item.id in scrollData.pouches) {
            player.inventory.remove(item.id)
            total += 1
        }
    }

    player.addXp(Skills.SUMMONING, amount * scrollData.creationExperience)
    player.inventory.add(scrollData.scroll, amount * 10)
    player.animate(SCROLL_INFUSE_ANIM)
}

/**
 * Crafts summoning pouches and removes the materials from the player's inventory
 *
 * @param player: The player
 * @param amount: The number of pouches to be crafted
 * @param pouch: The ID of the pouch being crafted
 * @param pouchData: The [SummoningPouchData] associated with the pouch the player wants to craft
 */
fun handleCrafting(
    player: Player,
    amount: Int,
    pouchData: SummoningPouchData
) {
    val charm = pouchData.charm
    val shards = pouchData.shards
    val tertiary = pouchData.tertiaries
    val xp = pouchData.creationExperience
    val name = pouchData.name.lowercase().replace("_", " ")

    val POUCH_INFUSE_ANIM = 8500

    if (amount < 1) return

    if (player.interfaces.isVisible(672)) {
        player.closeInterface(672)
    }

    player.inventory.remove(charm, amount)
    player.inventory.remove(Items.SPIRIT_SHARDS, amount * shards)
    player.inventory.remove(Items.POUCH, amount)
    tertiary.forEach { ter ->
        player.inventory.remove(ter, amount)
    }

    for (i in 0..amount) {
        player.message("You infuse a $name pouch.", ChatMessageType.FILTERED)
    }

    player.addXp(Skills.SUMMONING, amount * xp)
    player.inventory.add(pouchData.pouch, amount)
    player.animate(POUCH_INFUSE_ANIM)
}

/**
 * Gets the maximum number of pouches the player can craft with the items they currently have in their
 * inventory.
 *
 * @param player: The player
 * @param pouchData: The [SummoningPouchData] associated with the pouch the player wants to craft
 *
 * @return: The total number of pouches the player can craft
 */
fun getMaxCraftable(
    player: Player,
    pouchData: SummoningPouchData
): Int {
    val charm = pouchData.charm
    val tertiaries = pouchData.tertiaries
    val shards = pouchData.shards

    val charmCount = player.inventory.getItemCount(charm)
    val shardCount = player.inventory.getItemCount(Items.SPIRIT_SHARDS)
    val pouchCount = player.inventory.getItemCount(Items.POUCH)
    var minTertiaries = Int.MAX_VALUE
    tertiaries.forEach { ter ->
        val count = player.inventory.getItemCount(ter)
        if (count < minTertiaries) {
            minTertiaries = count
        }
    }

    return minOf(charmCount, minTertiaries, shardCount.floorDiv(shards), pouchCount)
}

/**
 * Gets the maximum number of scrolls the player can craft with the current items in their inventory
 *
 * @param player: The local player
 * @param scrollData: The scroll data for the scroll being crafted
 *
 * @return: The total number of scrolls the player can craft
 */
fun getMaxCraftable(
    player: Player,
    scrollData: SummoningScrollData,
): Int {
    var total = 0
    player.inventory.forEach { item ->
        if (item != null && item.id in scrollData.pouches) {
            total += 1
        }
    }

    return total
}
