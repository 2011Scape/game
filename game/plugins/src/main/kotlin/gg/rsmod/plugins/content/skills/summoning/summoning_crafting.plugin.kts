import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.WolfWhistle
import gg.rsmod.plugins.content.skills.summoning.SummoningPouchData
import gg.rsmod.plugins.content.skills.summoning.SummoningScrollData
import kotlin.math.min

on_button(672, 16) {
    val option = player.getInteractingOption()
    val pouchId = player.getInteractingItemId()
    val pouchData = SummoningPouchData.getDataByPouchId(pouchId)

    if (pouchData == null) {
        val greyedPouchData = SummoningPouchData.getDataByGreyedPouchId(pouchId)
        if (greyedPouchData != null) player.message(SummoningPouchData.getIngredientString(greyedPouchData.pouch, player), ChatMessageType.GAME_MESSAGE)
        return@on_button
    }

    val charm = pouchData.charm
    val shards = pouchData.shards
    val tertiary = pouchData.tertiaries
    val xp = pouchData.creationExperience
    val name = pouchData.name.lowercase().replace("_", " ")

    val maxCraftable = getMaxCraftablePouches(player, charm, tertiary, shards)

    if (maxCraftable > 0) {
        when (option) {
            // Infuse 1
            3 -> {
                handlePouchInfusion(player, 1, charm, tertiary, shards, pouchId, xp, name)
            }
            // Infuse 5
            2 -> {
                handlePouchInfusion(player, min(5, maxCraftable), charm, tertiary, shards, pouchId, xp, name)
            }
            // Infuse 10
            4 -> {
                handlePouchInfusion(player, min(10, maxCraftable), charm, tertiary, shards, pouchId, xp, name)
            }
            // Infuse X
            5 -> {
                player.closeInterface(672)
                player.queue {
                    val chosen = this.inputInt()
                    handlePouchInfusion(player, min(chosen, maxCraftable), charm, tertiary, shards, pouchId, xp, name)
                }
            }
            // Infuse all
            6 -> {
                handlePouchInfusion(player, maxCraftable, charm, tertiary, shards, pouchId, xp, name)
            }
            // List
            7 -> {
                player.message(SummoningPouchData.getIngredientString(pouchId, player), ChatMessageType.GAME_MESSAGE)
            }
        }
    }
    else {
        player.message(SummoningPouchData.getIngredientString(pouchId, player), ChatMessageType.GAME_MESSAGE)
    }
}

on_button(666, 16) {
    val option = player.getInteractingOption()
    val scrollId = player.getInteractingItemId()
    val scrollData = SummoningScrollData.getDataByScrollId(scrollId)

    if (scrollData == null) {
        val greyedScrollData = SummoningScrollData.getDataByGreyedScrollId(scrollId)
        if (greyedScrollData != null) player.message(SummoningScrollData.getIngredientString(greyedScrollData.scroll, player), ChatMessageType.GAME_MESSAGE)
        return@on_button
    }

    val maxCraftable = getMaxCraftableScrolls(player, scrollData)

    if (maxCraftable > 0) {
        when (option) {
            // Infuse 1
            3 -> {
                handleScrollTransformation(player, 1, scrollData)
            }
            // Infuse 5
            2 -> {
                handleScrollTransformation(player, min(5, maxCraftable), scrollData)
            }
            // Infuse 10
            4 -> {
                handleScrollTransformation(player, min(10, maxCraftable), scrollData)
            }
            // Infuse X
            5 -> {
                player.closeInterface(666)
                player.queue {
                    val chosen = this.inputInt()
                    handleScrollTransformation(player, min(chosen, maxCraftable), scrollData)
                }
            }
            // Infuse all
            6 -> {
                handleScrollTransformation(player, maxCraftable, scrollData)
            }
            // List
            7 -> {
                player.message(SummoningScrollData.getIngredientString(scrollId, player), ChatMessageType.GAME_MESSAGE)
            }
        }
    }
    else {
        player.message(SummoningScrollData.getIngredientString(scrollId, player), ChatMessageType.GAME_MESSAGE)
    }
}

on_button(672, 19) {
    openScrollInterface(player)
}

on_button(666, 18) {
    openPouchInterface(player)
}

fun handleScrollTransformation(player: Player, amount: Int, scrollData: SummoningScrollData) {
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

    player.inventory.add(scrollData.scroll, amount * 10)
}

/**
 * Crafts summoning pouches and removes the materials from the player's inventory
 *
 * @param player: The player
 * @param amount: The number of pouches to be crafted
 * @param charm: The ID if the needed charm (Gold, Crimson, Green, Blue)
 * @param tertiary: The tertiaries needed to craft the pouch
 * @param shards: The number of shards needed per pouch
 * @param pouch: The ID of the pouch being crafted
 * @param xp: The amount of xp given per pouch
 * @param name: The name of the pouch being crafted
 */
fun handlePouchInfusion(player: Player, amount: Int, charm: Int, tertiary: Array<Int>, shards: Int, pouch: Int, xp: Double, name: String) {
    val POUCH_INFUSE_ANIM = 8500

    if (amount < 1) return

    if (player.interfaces.isVisible(672)) {
        player.closeInterface(672)
    }

    player.inventory.remove(charm, amount)
    player.inventory.remove(Items.SPIRIT_SHARDS, amount * shards)
    player.inventory.remove(Items.POUCH, amount)
    tertiary.forEach {ter ->
        player.inventory.remove(ter, amount)
    }

    for (i in 0..amount) {
        player.message("You infuse a $name pouch.", ChatMessageType.FILTERED)
    }

    player.addXp(Skills.SUMMONING, amount * xp)
    player.inventory.add(pouch, amount)
    player.animate(POUCH_INFUSE_ANIM)

    if (pouch == Items.SPIRIT_WOLF_POUCH && player.getCurrentStage(WolfWhistle) == 4) {
        player.message("You now need to transform a spirit wolf pouch into some Howl scrolls.")
    }
}

/**
 * Gets the maximum number of pouches the player can craft with the items they currently have in their
 * inventory.
 *
 * @param player: The player
 * @param charm: The ID if the needed charm (Gold, Crimson, Green, Blue)
 * @param tertiaries: The tertiaries needed to craft the pouch
 * @param shards: The number of shards needed per pouch
 *
 * @return: The total number of pouches the player can craft
 */
fun getMaxCraftablePouches(player: Player, charm: Int, tertiaries: Array<Int>, shards: Int): Int {
    val charmCount = player.inventory.getItemCount(charm)
    val shardCount = player.inventory.getItemCount(Items.SPIRIT_SHARDS)
    val pouchCount = player.inventory.getItemCount(Items.POUCH)
    var minTertiaries = Int.MAX_VALUE
    tertiaries.forEach {ter ->
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
fun getMaxCraftableScrolls(player: Player, scrollData: SummoningScrollData): Int {
    var total = 0
    player.inventory.forEach { item ->
        if (item != null && item.id in scrollData.pouches) {
            total += 1
        }
    }

    return total
}