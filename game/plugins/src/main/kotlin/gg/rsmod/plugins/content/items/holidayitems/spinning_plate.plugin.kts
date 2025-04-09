package gg.rsmod.plugins.content.items.holidayitems


on_item_option(Items.SPINNING_PLATE, option = "spin") {
    if (getPercentage(10)) {
        player.queue {
            player.playSound(Sfx.PLATE_BREAK)
            player.inventory.remove(Items.SPINNING_PLATE)
            player.animate(Anims.DROP_SPINNING_PLATE)
            wait(2)
            world.spawn(GroundItem(item = Items.SPINNING_PLATE, amount = 1, tile = Tile(player.tile), owner = player))
            player.animate(Anims.EMOTE_CRY)
        }
    } else {
        player.playSound(Sfx.PLATE_SPIN)
        player.animate(Anims.SPIN_SPINNING_PLATE)
    }
}

fun getPercentage(chance: Int): Boolean {
    return (Math.random() * 100) < chance
}
