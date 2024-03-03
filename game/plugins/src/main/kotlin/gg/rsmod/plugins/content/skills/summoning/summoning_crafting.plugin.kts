import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.WolfWhistle
import gg.rsmod.plugins.content.skills.summoning.SummoningPouchData
import kotlin.math.min

on_button(672, 16) {
    val option = player.getInteractingOption()

    // Examine
//    if (option == 9) {
//        break@on_button
//    }

    val pouchId = player.getInteractingItemId()
    val pouchData = SummoningPouchData.getDataByPouchId(pouchId) ?: return@on_button

    val charm = pouchData.charm
    val shards = pouchData.shards
    val tertiary = pouchData.tertiary
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

            }
        }
    }
    else {
        player.message("You don't have the items needed to infuse that.")
    }
}

on_button(672, 19) {
    openScrollInterface(player)
}

on_button(666, 18) {
    openPouchInterface(player)
}

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