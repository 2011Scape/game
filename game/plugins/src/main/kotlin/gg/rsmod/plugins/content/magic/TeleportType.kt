package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.Graphic

/**
 * @author Tom <rspsmods@gmail.com>
 */

enum class TeleportType(val teleportDelay: Int, val animation: Int, val endAnimation: Int? = null, val graphic: Graphic? = null,
                        val endGraphic: Graphic? = null, val wildLvlRestriction: Int = 20) {
    MODERN(teleportDelay = 5, animation = 8939, graphic = Graphic(1576, 0), endAnimation = 8941, endGraphic = Graphic(1577, 0)),
    TAB(teleportDelay = 4, animation = 9597, graphic = Graphic(1680, 0), endAnimation = 9598),
    LUNAR(teleportDelay = 8, animation = 3691, graphic = Graphic(531, 0)),
    ANCIENT(teleportDelay = 8, animation = 9599, graphic = (Graphic(1681, 0))),
    LYRE(teleportDelay = 11, animation = 9600, graphic = Graphic(1682, 0)),
    SKULL_SCEPTRE(teleportDelay = 6, animation= 9601, graphic = Graphic(1683, 0)),
    WILDERNESS_OBELISK(teleportDelay = 5, animation = 9602, graphic = Graphic(1690, 0)),
    JEWELRY(teleportDelay = 5, animation = 9603, graphic = Graphic(1684, 0), wildLvlRestriction = 30),
    SPIRIT_TREE(teleportDelay = 6, animation = 7082, graphic = Graphic(129, 0), endAnimation = 7084, endGraphic = Graphic(1229, 0)),
    DRAKAN_MEDALLION(teleportDelay = 6, animation = 8939, graphic = Graphic(1864, 0), endAnimation = 8941, endGraphic = Graphic(1864, 0)),
    ECTOFUNTUS_GREEN(teleportDelay = 6, animation = 8939, graphic = Graphic(1678, 0), endAnimation = 8941, endGraphic = Graphic(1679, 0)),
    ECTOPHIAL(teleportDelay = 6, animation = 9609, graphic = Graphic(1688,0)),
    JUJU_SPIRIT(teleportDelay = 4, animation = 9897),
    CABBAGE(teleportDelay = 8, animation = 9984, graphic = Graphic(1731, 0), endAnimation = 9986, endGraphic = Graphic(1732, 0)),
    BROOMSTICK(teleportDelay = 4, animation = 10538, graphic = Graphic(1867, 0), endAnimation = 10537),
    BONESACK(teleportDelay = 6, animation = 12055, graphic = Graphic(2133, 0), endAnimation = 12057, endGraphic = Graphic(2134, 0)),
    FARM_PATCH(teleportDelay = 5, animation = 12437, graphic = Graphic(761, 0), endAnimation = 12438, endGraphic = Graphic(762, 0)),
    MONASTERY(teleportDelay = 8, animation = 12441, graphic = Graphic(2172, 0), endAnimation = 12442, endGraphic = Graphic(2173, 0)),
    DUNGEONEERING_GATESTONE(teleportDelay = 6, animation = 13288, graphic = Graphic(2516,0), endAnimation = 13285, endGraphic = Graphic(2517, 0)),
    RING_OF_KINSHIP(teleportDelay = 11, animation = 13652, graphic = Graphic(2602, 0), endAnimation = 13654, endGraphic = Graphic(2603, 0)),
    SCROLL(teleportDelay = 4, animation = 14293, graphic = Graphic(94, 0)),
    OBELISK(teleportDelay = 6, animation = 8939, graphic = Graphic(1690, 0), endAnimation = 8941),
}