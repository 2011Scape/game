package gg.rsmod.plugins.content.items.rings

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

on_item_option(item = Items.RING_OF_KINSHIP, option = 4) {
    val tile = Tile(x = 3449, z  = 3696)
    val rx = world.random(-3..3)
    val rz = world.random(-3..3)
    tile.transform(rx, rz)
    player.queue(TaskPriority.STRONG) {
        player.teleport(tile, TeleportType.DAEMONHEIM)
    }
}