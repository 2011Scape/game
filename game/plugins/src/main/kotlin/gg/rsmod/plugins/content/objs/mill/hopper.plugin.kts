package gg.rsmod.plugins.content.objs.mill

/**
 * @author Alycia <https://github.com/alycii>
 */

val FULL_BIN_VARP = 695
val HOPPER_VARP = 694

on_item_on_obj(obj = Objs.HOPPER_36881, item = Items.GRAIN) {
    player.animate(3572)
    player.inventory.remove(Items.GRAIN)
    player.setVarp(HOPPER_VARP, player.getVarp(HOPPER_VARP) + 1)
    player.message("You put the grain in the hopper.")
}
