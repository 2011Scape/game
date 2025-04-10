package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.Graphic
import gg.rsmod.plugins.api.cfg.Anims

/**
 * @author Tom <rspsmods@gmail.com>
 */

enum class TeleportType(
    val teleportDelay: Int,
    val animation: Int,
    val endAnimation: Int? = null,
    val graphic: Graphic? = null,
    val endGraphic: Graphic? = null,
    val wildLvlRestriction: Int = 20,
) {
    MODERN(
        teleportDelay = 5,
        animation = Anims.TELEPORT_MODERN_START,
        graphic = Graphic(1576, 0),
        endAnimation = Anims.TELEPORT_MODERN_END,
        endGraphic = Graphic(1577, 0),
    ),
    TAB(
        teleportDelay = 4,
        animation = Anims.TELEPORT_TAB_START,
        graphic = Graphic(1680, 0),
        endAnimation = Anims.TELEPORT_TAB_END),
    LUNAR(
        teleportDelay = 8,
        animation = Anims.TELEPORT_LUNAR,
        graphic = Graphic(531, 0)),
    ANCIENT(
        teleportDelay = 8,
        animation = Anims.TELEPORT_ANCIENT,
        graphic = (Graphic(1681, 0))),
    LYRE(
        teleportDelay = 11,
        animation = Anims.TELEPORT_LYRE,
        graphic = Graphic(1682, 0)),
    SKULL_SCEPTRE(
        teleportDelay = 6,
        animation = Anims.TELEPORT_SKULL_SCEPTRE,
        graphic = Graphic(1683, 0)),
    WILDERNESS_OBELISK(
        teleportDelay = 5,
        animation = Anims.TELEPORT_WILDERNESS_OBELISK,
        graphic = Graphic(1690, 0)),
    JEWELRY(
        teleportDelay = 5,
        animation = Anims.TELEPORT_JEWELLERY,
        graphic = Graphic(1684, 0),
        wildLvlRestriction = 30),
    SPIRIT_TREE(
        teleportDelay = 6,
        animation = Anims.TELEPORT_SPIRIT_TREE_START,
        graphic = Graphic(129, 0),
        endAnimation = Anims.TELEPORT_SPIRIT_TREE_END,
        endGraphic = Graphic(1229, 0),
    ),
    DRAKAN_MEDALLION(
        teleportDelay = 6,
        animation = Anims.TELEPORT_MODERN_START,
        graphic = Graphic(1864, 0),
        endAnimation = Anims.TELEPORT_MODERN_END,
        endGraphic = Graphic(1864, 0),
    ),
    ECTOFUNTUS_GREEN(
        teleportDelay = 6,
        animation = Anims.TELEPORT_MODERN_START,
        graphic = Graphic(1678, 0),
        endAnimation = Anims.TELEPORT_MODERN_END,
        endGraphic = Graphic(1679, 0),
    ),
    ECTOPHIAL(
        teleportDelay = 6,
        animation = Anims.TELEPORT_ECTOPHIAL,
        graphic = Graphic(1688, 0)),
    JUJU_SPIRIT(
        teleportDelay = 4,
        animation = Anims.TELEPORT_JUJU_SPIRIT),
    CABBAGE(
        teleportDelay = 8,
        animation = Anims.TELEPORT_CABBAGE_START,
        graphic = Graphic(1731, 0),
        endAnimation = Anims.TELEPORT_CABBAGE_END,
        endGraphic = Graphic(1732, 0),
    ),
    BROOMSTICK(
        teleportDelay = 4,
        animation = Anims.TELEPORT_BROOMSTICK_START,
        graphic = Graphic(1867, 0),
        endAnimation = Anims.TELEPORT_BROOMSTICK_END),
    BONESACK(
        teleportDelay = 6,
        animation = Anims.TELEPORT_BONESACK_START,
        graphic = Graphic(2133, 0),
        endAnimation = Anims.TELEPORT_BONESACK_END,
        endGraphic = Graphic(2134, 0),
    ),
    FARM_PATCH(
        teleportDelay = 5,
        animation = Anims.TELEPORT_FARM_PATCH_START,
        graphic = Graphic(761, 0),
        endAnimation = Anims.TELEPORT_FARM_PATCH_END,
        endGraphic = Graphic(762, 0),
    ),
    MONASTERY(
        teleportDelay = 8,
        animation = Anims.TELEPORT_MONASTERY_START,
        graphic = Graphic(2172, 0),
        endAnimation = Anims.TELEPORT_MONASTERY_END,
        endGraphic = Graphic(2173, 0),
    ),
    DUNGEONEERING_GATESTONE(
        teleportDelay = 6,
        animation = Anims.TELEPORT_DUNG_GATESTONE_START,
        graphic = Graphic(2516, 0),
        endAnimation = Anims.TELEPORT_DUNG_GATESTONE_END,
        endGraphic = Graphic(2517, 0),
    ),
    RING_OF_KINSHIP(
        teleportDelay = 11,
        animation = Anims.TELEPORT_KINSHIP_START,
        graphic = Graphic(2602, 0),
        endAnimation = Anims.TELEPORT_KINSHIP_END,
        endGraphic = Graphic(2603, 0),
    ),
    SCROLL(
        teleportDelay = 4,
        animation = Anims.TELEPORT_SCROLL,
        graphic = Graphic(94, 0)),
    OBELISK(
        teleportDelay = 6,
        animation = Anims.TELEPORT_MODERN_START,
        graphic = Graphic(1690, 0),
        endAnimation = Anims.TELEPORT_MODERN_END),
    FAIRY(
        teleportDelay = 6,
        animation = Anims.TELEPORT_FAIRY_START,
        graphic = Graphic(2670, 0),
        endAnimation = Anims.TELEPORT_FAIRY_END),
}
