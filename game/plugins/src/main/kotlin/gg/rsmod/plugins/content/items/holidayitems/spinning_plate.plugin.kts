package gg.rsmod.plugins.content.items.holidayitems


on_item_option(Items.SPINNING_PLATE, option = "spin") {
    if (getPercentage(10)) {
        player.queue {
            player.playSound(Sfx.PLATE_BREAK)
            player.inventory.remove(Items.SPINNING_PLATE)
            player.animate(1906)
            wait(2)
            world.spawn(GroundItem(item = Items.SPINNING_PLATE, amount = 1, tile = Tile(player.tile), owner = player))
            player.animate(860)
        }
    } else {
        player.playSound(Sfx.PLATE_SPIN)
        player.animate(1902)
    }
}

fun getPercentage(chance: Int): Boolean {
    return (Math.random() * 100) < chance
}
