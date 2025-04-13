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
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(1576, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(1577, 0),
    ),
    TAB(
        teleportDelay = 4,
        animation = Anims.TAB_TELEPORT_START,
        graphic = Graphic(1680, 0),
        endAnimation = Anims.TAB_TELEPORT_END),
    LUNAR(
        teleportDelay = 8,
        animation = Anims.LUNAR_TELEPORT,
        graphic = Graphic(531, 0)),
    ANCIENT(
        teleportDelay = 8,
        animation = Anims.ANCIENT_TELEPORT,
        graphic = (Graphic(1681, 0))),
    LYRE(
        teleportDelay = 11,
        animation = Anims.LYRE_TELEPORT,
        graphic = Graphic(1682, 0)),
    SKULL_SCEPTRE(
        teleportDelay = 6,
        animation = Anims.SKULL_SCEPTRE_TELEPORT,
        graphic = Graphic(1683, 0)),
    WILDERNESS_OBELISK(
        teleportDelay = 5,
        animation = Anims.WILDERNESS_OBELISK_TELEPORT,
        graphic = Graphic(1690, 0)),
    JEWELRY(
        teleportDelay = 5,
        animation = Anims.JEWELLERY_TELEPORT,
        graphic = Graphic(1684, 0),
        wildLvlRestriction = 30),
    SPIRIT_TREE(
        teleportDelay = 6,
        animation = Anims.SPIRIT_TREE_TELEPORT_START,
        graphic = Graphic(129, 0),
        endAnimation = Anims.SPIRIT_TREE_TELEPORT_END,
        endGraphic = Graphic(1229, 0),
    ),
    DRAKAN_MEDALLION(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(1864, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(1864, 0),
    ),
    ECTOFUNTUS_GREEN(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(1678, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(1679, 0),
    ),
    ECTOPHIAL(
        teleportDelay = 6,
        animation = Anims.ECTOPHIAL_TELEPORT,
        graphic = Graphic(1688, 0)),
    JUJU_SPIRIT(
        teleportDelay = 4,
        animation = Anims.JUJU_SPIRIT_TELEPORT),
    CABBAGE(
        teleportDelay = 8,
        animation = Anims.CABBAGE_TELEPORT_START,
        graphic = Graphic(1731, 0),
        endAnimation = Anims.CABBAGE_TELEPORT_END,
        endGraphic = Graphic(1732, 0),
    ),
    BROOMSTICK(
        teleportDelay = 4,
        animation = Anims.BROOMSTICK_TELEPORT_START,
        graphic = Graphic(1867, 0),
        endAnimation = Anims.BROOMSTICK_TELEPORT_END),
    BONESACK(
        teleportDelay = 6,
        animation = Anims.BONESACK_TELEPORT_START,
        graphic = Graphic(2133, 0),
        endAnimation = Anims.BONESACK_TELEPORT_END,
        endGraphic = Graphic(2134, 0),
    ),
    FARM_PATCH(
        teleportDelay = 5,
        animation = Anims.FARM_PATCH_TELEPORT_START,
        graphic = Graphic(761, 0),
        endAnimation = Anims.FARM_PATCH_TELEPORT_END,
        endGraphic = Graphic(762, 0),
    ),
    MONASTERY(
        teleportDelay = 8,
        animation = Anims.MONASTERY_TELEPORT_START,
        graphic = Graphic(2172, 0),
        endAnimation = Anims.MONASTERY_TELEPORT_END,
        endGraphic = Graphic(2173, 0),
    ),
    DUNGEONEERING_GATESTONE(
        teleportDelay = 6,
        animation = Anims.DUNG_GATESTONE_TELEPORT_START,
        graphic = Graphic(2516, 0),
        endAnimation = Anims.DUNG_GATESTONE_TELEPORT_END,
        endGraphic = Graphic(2517, 0),
    ),
    RING_OF_KINSHIP(
        teleportDelay = 11,
        animation = Anims.KINSHIP_TELEPORT_START,
        graphic = Graphic(2602, 0),
        endAnimation = Anims.KINSHIP_TELEPORT_END,
        endGraphic = Graphic(2603, 0),
    ),
    SCROLL(
        teleportDelay = 4,
        animation = Anims.SCROLL_TELEPORT,
        graphic = Graphic(94, 0)),
    OBELISK(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(1690, 0),
        endAnimation = Anims.MODERN_TELEPORT_END),
    FAIRY(
        teleportDelay = 6,
        animation = Anims.FAIRY_TELEPORT_START,
        graphic = Graphic(2670, 0),
        endAnimation = Anims.FAIRY_TELEPORT_END),
}
