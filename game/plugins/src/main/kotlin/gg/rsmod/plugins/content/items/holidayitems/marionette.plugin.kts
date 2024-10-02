package gg.rsmod.plugins.content.items.holidayitems

/**
 * @author Tank <https://github.com/reeeccoo>
 */

enum class MarionetteData(
    val marionette: Int,
    val gfxJump: Int,
    val gfxWalk: Int,
    val gfxBow: Int,
    val gfxDance: Int,
) {
    BLUE_MARIONETTE(Items.BLUE_MARIONETTE, 511, 512, 513, 514),
    RED_MARIONETTE(Items.RED_MARIONETTE, 507, 508, 509, 510),
    GREEN_MARIONETTE(Items.GREEN_MARIONETTE, 515, 516, 517, 518),
    ;

    companion object {
        val values = enumValues<MarionetteData>()
        val marionetteDefs = values.associateBy { it.marionette }
    }
}

val malData = MarionetteData.values
val marionetteTime = malData.map { it.marionette }.toTypedArray()
val someDefs = MarionetteData.marionetteDefs

marionetteTime.forEach { marionette ->
    on_item_option(item = marionette, option = "jump") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(3003)
            player.graphic(someDefs[marionette]!!.gfxJump)
        }
    }

    on_item_option(item = marionette, option = "walk") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(3004)
            player.graphic(someDefs[marionette]!!.gfxWalk)
        }
    }

    on_item_option(item = marionette, option = "bow") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(3005)
            player.graphic(someDefs[marionette]!!.gfxBow)
        }
    }

    on_item_option(item = marionette, option = "dance") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(3006)
            player.graphic(someDefs[marionette]!!.gfxDance)
        }
    }
}
