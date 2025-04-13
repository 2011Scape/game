package gg.rsmod.plugins.content.combat.strategy.ranged

import gg.rsmod.game.model.Graphic
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.cfg.Gfx
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.ADAMANT_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.BRONZE_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.DRAGON_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.IRON_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.MITHRIL_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.OGRE_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.RUNE_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.STEEL_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Arrows.TRAINING_ARROWS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Bolts
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.ADAMANT_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.BLACK_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.BRONZE_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.DRAGON_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.IRON_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.MITHRIL_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.RUNE_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts.STEEL_DARTS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.ADAMANT_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.BRONZE_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.IRON_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.MITHRIL_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.RUNE_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Javelins.STEEL_JAVELINS
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.ADAMANT_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.BLACK_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.BRONZE_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.IRON_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.MITHRIL_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.RUNE_KNIVES
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives.STEEL_KNIVES

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class RangedProjectile(
    val gfx: Int,
    val drawback: Graphic? = null,
    val impact: Graphic? = null,
    val type: ProjectileType,
    val items: Array<Int>,
) {
    BOLTS(
        gfx = Gfx.BOLT_IN_FLIGHT,
        type = ProjectileType.BOLT,
        items =
            Bolts.BRONZE_BOLTS + Bolts.IRON_BOLTS + Bolts.STEEL_BOLTS + Bolts.MITHRIL_BOLTS +
                Bolts.ADAMANT_BOLTS + Bolts.RUNITE_BOLTS + Bolts.DRAGON_BOLTS + Bolts.BLURITE_BOLTS + Bolts.KEBBIT_BOLTS +
                Bolts.BONE_BOLTS,
    ),
    TRAINING_ARROW(
        gfx = Gfx.TRAINING_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.TRAINING_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = TRAINING_ARROWS,
    ),
    BRONZE_ARROW(
        gfx = Gfx.BRONZE_ARROW_IN_FLIGHT,
        drawback = Graphic(id = Gfx.BRONZE_ARROW_DRAWBACK, height = 96),
        type = ProjectileType.ARROW,
        items = BRONZE_ARROWS,
    ),
    IRON_ARROW(
        gfx = Gfx.IRON_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.IRON_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = IRON_ARROWS
    ),
    STEEL_ARROW(
        gfx = Gfx.STEEL_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.STEEL_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = STEEL_ARROWS
    ),
    MITHRIL_ARROW(
        gfx = Gfx.MITHRIL_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.MITHRIL_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = MITHRIL_ARROWS,
    ),
    ADAMANT_ARROW(
        gfx = Gfx.ADAMANT_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.ADAMANT_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = ADAMANT_ARROWS,
    ),
    RUNE_ARROW(
        gfx = Gfx.RUNE_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.RUNE_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = RUNE_ARROWS
    ),
    DRAGON_ARROW(
        gfx = Gfx.DRAGON_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.DRAGON_ARROW_DRAWBACK, 96),
        type = ProjectileType.ARROW,
        items = DRAGON_ARROWS,
    ),
    OGRE_ARROW(
        gfx = Gfx.OGRE_ARROW_IN_FLIGHT,
        drawback = Graphic(Gfx.OGRE_ARROW_DRAWBACK, 50),
        type = ProjectileType.ARROW, items = OGRE_ARROWS
    ),
    BRONZE_JAVELIN(
        gfx = Gfx.BRONZE_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = BRONZE_JAVELINS
    ),
    IRON_JAVELIN(
        gfx = Gfx.IRON_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = IRON_JAVELINS
    ),
    STEEL_JAVELIN(
        gfx = Gfx.STEEL_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = STEEL_JAVELINS
    ),
    MITHRIL_JAVELIN(
        gfx = Gfx.MITHRIL_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = MITHRIL_JAVELINS
    ),
    ADAMANT_JAVELIN(
        gfx = Gfx.ADAMANT_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = ADAMANT_JAVELINS
    ),
    RUNE_JAVELIN(
        gfx = Gfx.RUNE_JAVELIN_IN_FLIGHT,
        type = ProjectileType.JAVELIN,
        items = RUNE_JAVELINS
    ),
    BRONZE_KNIFE(
        gfx = Gfx.BRONZE_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.BRONZE_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = BRONZE_KNIVES,
    ),
    IRON_KNIFE(
        gfx = Gfx.IRON_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.IRON_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = IRON_KNIVES
    ),
    STEEL_KNIFE(
        gfx = Gfx.STEEL_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.STEEL_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = STEEL_KNIVES,
    ),
    BLACK_KNIFE(
        gfx = Gfx.BLACK_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.BLACK_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = BLACK_KNIVES,
    ),
    MITHRIL_KNIFE(
        gfx = Gfx.MITHRIL_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.MITHRIL_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = MITHRIL_KNIVES,
    ),
    ADAMANT_KNIFE(
        gfx = Gfx.ADAMANT_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.ADAMANT_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = ADAMANT_KNIVES,
    ),
    RUNE_KNIFE(
        gfx = Gfx.RUNE_KNIFE_IN_FLIGHT,
        drawback = Graphic(Gfx.RUNE_KNIFE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = RUNE_KNIVES
    ),
    BRONZE_DART(
        gfx = Gfx.BRONZE_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.BRONZE_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = BRONZE_DARTS,
    ),
    IRON_DART(
        gfx = Gfx.IRON_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.IRON_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = IRON_DARTS
    ),
    STEEL_DART(
        gfx = Gfx.STEEL_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.STEEL_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = STEEL_DARTS
    ),
    BLACK_DART(
        gfx = Gfx.BLACK_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.BLACK_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = BLACK_DARTS
    ),
    MITHRIL_DART(
        gfx = Gfx.MITHRIL_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.MITHRIL_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = MITHRIL_DARTS,
    ),
    ADAMANT_DART(
        gfx = Gfx.ADAMANT_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.ADAMANT_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = ADAMANT_DARTS,
    ),
    RUNE_DART(
        gfx = Gfx.RUNE_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.RUNE_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = RUNE_DARTS
    ),
    DRAGON_DART(
        gfx = Gfx.DRAGON_DART_IN_FLIGHT,
        drawback = Graphic(Gfx.DRAGON_DART_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = DRAGON_DARTS,
    ),
    BRONZE_THROWING_AXE(
        gfx = Gfx.BRONZE_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.BRONZE_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.BRONZE_THROWNAXE),
    ),
    IRON_THROWING_AXE(
        gfx = Gfx.IRON_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.IRON_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.IRON_THROWNAXE),
    ),
    STEEL_THROWING_AXE(
        gfx = Gfx.STEEL_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.STEEL_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.STEEL_THROWNAXE),
    ),
    MITHRIL_THROWING_AXE(
        gfx = Gfx.MITHRIL_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.MITHRIL_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.MITHRIL_THROWNAXE),
    ),
    ADAMANT_THROWING_AXE(
        gfx = Gfx.ADAMANT_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.ADAMANT_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.ADAMANT_THROWNAXE),
    ),
    RUNE_THROWING_AXE(
        gfx = Gfx.RUNE_THROWNAXE_IN_FLIGHT,
        drawback = Graphic(Gfx.RUNE_THROWNAXE_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.RUNE_THROWNAXE),
    ),
    TOKTZ_XIL_UL(
        gfx = Gfx.TOKTZ_XIL_UL_IN_FLIGHT,
        type = ProjectileType.THROWN,
        items = arrayOf(Items.TOKTZXILUL)
    ),
    GREY_CHINCHOMA(
        gfx = Gfx.GREY_CHINCHOMPA_IN_FLIGHT,
        impact = Graphic(Gfx.CHINCHOMPA_IMPACT, 92),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.CHINCHOMPA_10033),
    ),
    RED_CHINCHOMA(
        gfx = Gfx.RED_CHINCHOMPA_IN_FLIGHT,
        impact = Graphic(Gfx.CHINCHOMPA_IMPACT, 92),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.RED_CHINCHOMPA_10034),
    ),
    SLING(
        gfx = Gfx.SLING_IN_FLIGHT,
        drawback = Graphic(Gfx.SLING_DRAWBACK, 96),
        type = ProjectileType.THROWN,
        items = arrayOf(Items.SLING),
    ),
    ;

    fun breakOnImpact(): Boolean =
        when (this) {
            GREY_CHINCHOMA, RED_CHINCHOMA -> true
            else -> false
        }

    fun noAmmoNeeded(): Boolean =
        when (this) {
            SLING -> false
            else -> true
        }

    companion object {
        val values = enumValues<RangedProjectile>()
    }
}
