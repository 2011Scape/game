package gg.rsmod.plugins.content.items.holidayitems

val YO_YO = intArrayOf(Items.YOYO, Items.YOYO_10733)

YO_YO.forEach {
    on_item_option(item = it, option = "play") {
        player.playSound(Sfx.YOYO1)
        player.animate(1457)
    }

    on_item_option(item = it, option = "loop") {
        player.playSound(Sfx.YOYO2)
        player.animate(1458)
    }

    on_item_option(item = it, option = "walk") {
        player.playSound(Sfx.YOYO3)
        player.animate(1459)
    }

    on_item_option(item = it, option = "crazy") {
        player.playSound(Sfx.YOYO4)
        player.animate(1460)
    }
}
