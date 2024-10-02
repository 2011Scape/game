package gg.rsmod.plugins.content.items

import kotlin.random.Random


/**
 * @author Tank <https://github.com/reeeccoo>
 */

enum class HorseyData(
    val horsey: Int,
    val anim: Int,
) {
    BROWN_HORSEY(2520, 918),
    WHITE_HORSEY(2522, 919),
    BLACK_HORSEY(2524, 920),
    GREY_HORSEY(2526, 921),
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
