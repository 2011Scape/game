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
    BLUE_MARIONETTE(Items.BLUE_MARIONETTE, Gfx.BLUE_MARIONETTE_JUMP,
        Gfx.BLUE_MARIONETTE_WALK, Gfx.BLUE_MARIONETTE_BOW, Gfx.BLUE_MARIONETTE_DANCE),
    RED_MARIONETTE(Items.RED_MARIONETTE, Gfx.RED_MARIONETTE_JUMP,
        Gfx.RED_MARIONETTE_WALK, Gfx.RED_MARIONETTE_BOW, Gfx.RED_MARIONETTE_DANCE),
    GREEN_MARIONETTE(Items.GREEN_MARIONETTE, Gfx.GREEN_MARIONETTE_JUMP,
        Gfx.GREEN_MARIONETTE_WALK, Gfx.GREEN_MARIONETTE_BOW, Gfx.GREEN_MARIONETTE_DANCE),
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
            player.animate(Anims.MARIONETTE_JUMP)
            player.graphic(someDefs[marionette]!!.gfxJump)
        }
    }

    on_item_option(item = marionette, option = "walk") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(Anims.MARIONETTE_WALK)
            player.graphic(someDefs[marionette]!!.gfxWalk)
        }
    }

    on_item_option(item = marionette, option = "bow") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(Anims.MARIONETTE_BOW)
            player.graphic(someDefs[marionette]!!.gfxBow)
        }
    }

    on_item_option(item = marionette, option = "dance") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
            player.animate(Anims.MARIONETTE_DANCE)
            player.graphic(someDefs[marionette]!!.gfxDance)
        }
    }
}
