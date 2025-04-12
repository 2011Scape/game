package gg.rsmod.plugins.content.items.holidayitems

val YO_YO = intArrayOf(Items.YOYO, Items.YOYO_10733)

YO_YO.forEach {
    on_item_option(item = it, option = "play") {
        player.playSound(Sfx.YOYO1)
        player.animate(Anims.YOYO_PLAY)
    }

    on_item_option(item = it, option = "loop") {
        player.playSound(Sfx.YOYO2)
        player.animate(Anims.YOYO_LOOP)
    }

    on_item_option(item = it, option = "walk") {
        player.playSound(Sfx.YOYO3)
        player.animate(Anims.YOYO_WALK)
    }

    on_item_option(item = it, option = "crazy") {
        player.playSound(Sfx.YOYO4)
        player.animate(Anims.YOYO_CRAZY)
    }
}
