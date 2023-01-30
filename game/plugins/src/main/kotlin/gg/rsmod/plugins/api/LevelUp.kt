package gg.rsmod.plugins.api

object LevelUp {

    /**
     * The varbit for sending a flashing icon
     * in the skill tab. Ordered by client id
     */
    val FLASHING_ICON_VARBITS = arrayOf(
        4732, 4734, 4733, 4738, 4735, 4736, 4737, 4747, 4749, 4743,
        4746, 4748, 4742, 4745, 4744, 4740, 4739, 4741, 4751, 4752,
        4750, 4754, 4753, 4755, 7756
    )

    /**
     * The varc the client requires to determine
     * how many levels you have made since you last
     * clicked on a flashing icon. Ordered by
     * "client" skill ids (i.e, defence is 5 instead of 1)
     */
    val LEVELLED_AMOUNT_VARC = arrayOf(
        1469, 1470, 1472, 1474, 1471, 1475, 1473, 1476, 1477, 1478,
        1479, 1487, 1481, 1482, 1483, 1484, 1485, 1486, 1480, 1488,
        1489, 1490, 1491, 1492, 1493
    )

    /**
     * The "clientscript id" for the skill
     */
    val CLIENTSCRIPT_ID = intArrayOf(
        1, 5, 2, 6, 3, 7, 4, 16,
        18, 19, 15, 17, 11, 14, 13, 9,
        8, 10, 20, 21, 12, 23, 22, 24, 25
    )


}