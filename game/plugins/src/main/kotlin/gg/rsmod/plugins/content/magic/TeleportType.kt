package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.Graphic
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Gfx

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
        graphic = Graphic(Gfx.MODERN_TELEPORT_START, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(Gfx.MODERN_TELEPORT_END, 0),
    ),
    TAB(
        teleportDelay = 4,
        animation = Anims.TAB_TELEPORT_START,
        graphic = Graphic(Gfx.GFX_1680, 0),
        endAnimation = Anims.TAB_TELEPORT_END),
    LUNAR(
        teleportDelay = 8,
        animation = Anims.LUNAR_TELEPORT,
        graphic = Graphic(Gfx.GFX_531, 0)),
    ANCIENT(
        teleportDelay = 8,
        animation = Anims.ANCIENT_TELEPORT,
        graphic = (Graphic(Gfx.GFX_1681, 0))),
    LYRE(
        teleportDelay = 11,
        animation = Anims.LYRE_TELEPORT,
        graphic = Graphic(Gfx.GFX_1682, 0)),
    SKULL_SCEPTRE(
        teleportDelay = 6,
        animation = Anims.SKULL_SCEPTRE_TELEPORT,
        graphic = Graphic(Gfx.GFX_1683, 0)),
    WILDERNESS_OBELISK(
        teleportDelay = 5,
        animation = Anims.WILDERNESS_OBELISK_TELEPORT,
        graphic = Graphic(Gfx.OBELISK_TELEPORT, 0)),
    JEWELRY(
        teleportDelay = 5,
        animation = Anims.JEWELLERY_TELEPORT,
        graphic = Graphic(Gfx.GFX_1684, 0),
        wildLvlRestriction = 30),
    SPIRIT_TREE(
        teleportDelay = 6,
        animation = Anims.SPIRIT_TREE_TELEPORT_START,
        graphic = Graphic(Gfx.GFX_129, 0),
        endAnimation = Anims.SPIRIT_TREE_TELEPORT_END,
        endGraphic = Graphic(Gfx.GFX_1229, 0),
    ),
    DRAKAN_MEDALLION(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(Gfx.EMOTE_TRICK, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(Gfx.EMOTE_TRICK, 0),
    ),
    ECTOFUNTUS_GREEN(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(Gfx.ECTOFUNTUS_TELEPORT_START, 0),
        endAnimation = Anims.MODERN_TELEPORT_END,
        endGraphic = Graphic(Gfx.ECTOFUNTUS_TELEPORT_END, 0),
    ),
    ECTOPHIAL(
        teleportDelay = 6,
        animation = Anims.ECTOPHIAL_TELEPORT,
        graphic = Graphic(Gfx.ECTOPHIAL_POUR, 0)),
    JUJU_SPIRIT(
        teleportDelay = 4,
        animation = Anims.JUJU_SPIRIT_TELEPORT),
    CABBAGE(
        teleportDelay = 8,
        animation = Anims.CABBAGE_TELEPORT_START,
        graphic = Graphic(Gfx.CABBAGE_TELEPORT_START, 0),
        endAnimation = Anims.CABBAGE_TELEPORT_END,
        endGraphic = Graphic(Gfx.CABBAGE_TELEPORT_END, 0),
    ),
    BROOMSTICK(
        teleportDelay = 4,
        animation = Anims.BROOMSTICK_TELEPORT_START,
        graphic = Graphic(Gfx.BROOMSTICK_TELEPORT, 0),
        endAnimation = Anims.BROOMSTICK_TELEPORT_END),
    BONESACK(
        teleportDelay = 6,
        animation = Anims.BONESACK_TELEPORT_START,
        graphic = Graphic(Gfx.BONESACK_TELEPORT_START, 0),
        endAnimation = Anims.BONESACK_TELEPORT_END,
        endGraphic = Graphic(Gfx.BONESACK_TELEPORT_END, 0),
    ),
    FARM_PATCH(
        teleportDelay = 5,
        animation = Anims.FARM_PATCH_TELEPORT_START,
        graphic = Graphic(Gfx.FARM_PATCH_TELEPORT_START, 0),
        endAnimation = Anims.FARM_PATCH_TELEPORT_END,
        endGraphic = Graphic(Gfx.FARM_PATCH_TELEPORT_END, 0),
    ),
    MONASTERY(
        teleportDelay = 8,
        animation = Anims.MONASTERY_TELEPORT_START,
        graphic = Graphic(Gfx.MONASTERY_TELEPORT_START, 0),
        endAnimation = Anims.MONASTERY_TELEPORT_END,
        endGraphic = Graphic(Gfx.MONASTERY_TELEPORT_END, 0),
    ),
    DUNGEONEERING_GATESTONE(
        teleportDelay = 6,
        animation = Anims.DUNG_GATESTONE_TELEPORT_START,
        graphic = Graphic(Gfx.DUNG_GATESTONE_TELEPORT_START, 0),
        endAnimation = Anims.DUNG_GATESTONE_TELEPORT_END,
        endGraphic = Graphic(Gfx.DUNG_GATESTONE_TELEPORT_END, 0),
    ),
    RING_OF_KINSHIP(
        teleportDelay = 11,
        animation = Anims.KINSHIP_TELEPORT_START,
        graphic = Graphic(Gfx.KINSHIP_TELEPORT_START, 0),
        endAnimation = Anims.KINSHIP_TELEPORT_END,
        endGraphic = Graphic(Gfx.KINSHIP_TELEPORT_END, 0),
    ),
    SCROLL(
        teleportDelay = 4,
        animation = Anims.SCROLL_TELEPORT,
        graphic = Graphic(Gfx.SCROLL_TELEPORT, 0)),
    OBELISK(
        teleportDelay = 6,
        animation = Anims.MODERN_TELEPORT_START,
        graphic = Graphic(Gfx.OBELISK_TELEPORT, 0),
        endAnimation = Anims.MODERN_TELEPORT_END),
    FAIRY(
        teleportDelay = 6,
        animation = Anims.FAIRY_TELEPORT_START,
        graphic = Graphic(Gfx.FAIRY_RING_TELEPORT, 0),
        endAnimation = Anims.FAIRY_TELEPORT_END),
}
