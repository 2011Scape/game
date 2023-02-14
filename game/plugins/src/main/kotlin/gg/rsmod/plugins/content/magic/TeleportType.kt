package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.Graphic

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class TeleportType(val teleportDelay: Int, val animation: Int, val endAnimation: Int? = null, val graphic: Graphic? = null,
                        val endGraphic: Graphic? = null, val wildLvlRestriction: Int = 20) {
    MODERN(teleportDelay = 5, animation = 8939, graphic = Graphic(1576, 0), endAnimation = 8941, endGraphic = Graphic(1577, 0)),
    GLORY(teleportDelay = 4, animation = 714, graphic = Graphic(111, 92), wildLvlRestriction = 30),
    ANCIENT(teleportDelay = 5, animation = 1979, graphic = Graphic(392, 0)),
    LUNAR(teleportDelay = 4, animation = 1816, graphic = Graphic(747, 120)),
    DAEMONHEIM(teleportDelay = 11, animation = 13652, graphic = Graphic(2602, 0), endAnimation = 13654, endGraphic = Graphic(2603, 0))
}