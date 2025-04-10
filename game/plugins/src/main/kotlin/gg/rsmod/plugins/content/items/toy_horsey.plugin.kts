package gg.rsmod.plugins.content.items

import kotlin.random.Random


/**
 * @author Tank <https://github.com/reeeccoo>
 */

enum class HorseyData(
    val horsey: Int,
    val anim: Int,
) {
    BROWN_HORSEY(Items.TOY_HORSEY, Anims.BROWN_HORSEY),
    WHITE_HORSEY(Items.TOY_HORSEY_2522, Anims.WHITE_HORSEY),
    BLACK_HORSEY(Items.TOY_HORSEY_2524, Anims.BLACK_HORSEY),
    GREY_HORSEY(Items.TOY_HORSEY_2526, Anims.GREY_HORSEY),
    ;

    companion object {
        val values = enumValues<HorseyData>()
        val horseDefs = values.associateBy { it.horsey }
    }
}
val chatMessages =
    arrayOf(
        "Come on Dobbin, we can win the race!",
        "Just say neigh to gambling!",
        "Hi-ho Silver, and away!",
        "Neaahhhyyy! Giddy-up horsey!",
    )
val horseData = HorseyData.values
val horseTime = horseData.map { it.horsey }.toTypedArray()
val someDefs = HorseyData.horseDefs

fun randomMessage(): Int {
    return Random.nextInt(chatMessages.size)
}

horseTime.forEach {
        horsey ->
    on_item_option(horsey, option = "play-with") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            val rand: Int = randomMessage()
            player.forceChat(chatMessages[rand])
            player.animate(someDefs[horsey]!!.anim)
        }
    }
}
