package gg.rsmod.plugins.content.objs.prayeraltar

private val ALTARS_PRAY_AT = setOf(Objs.ALTAR_27661, Objs.CHAOS_ALTAR, Objs.ALTAR_24343, Objs.ALTAR_2640, Objs.ALTAR, Objs.ALTAR_19145)

private val ALTARS_PRAY = setOf(Objs.ALTAR_36972, Objs.ALTAR_OF_GUTHIX, Objs.ALTAR_34616, Objs.ALTAR_18254, Objs.ALTAR_39842)

/**
 * @author Kevin Senez <ksenez94@gmail.com>
 */
ALTARS_PRAY_AT.forEach { altar ->
    on_obj_option(obj = altar, "pray-at") {
        player.queue {
            player.animate(645)
            player.filterableMessage("You recharge your Prayer points.")
            player.playSound(Sfx.PRAYER_RECHARGE)
            rechargePrayerPoints(player)
        }
    }
}

ALTARS_PRAY.forEach { altar ->
    on_obj_option(obj = altar, "pray") {
        player.queue {
            player.animate(645)
            player.filterableMessage("You recharge your Prayer points.")
            player.playSound(Sfx.PRAYER_RECHARGE)
            rechargePrayerPoints(player)
        }
    }
}

fun rechargePrayerPoints(player: Player) {
    player.setPrayerPoints(player.getSkills().getMaxLevel(Skills.PRAYER) * 10)
}