package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.Animation
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.CombatStyle
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Darts
import gg.rsmod.plugins.content.combat.strategy.ranged.ammo.Knives

val FIRST = CombatStyle.FIRST
val SECOND = CombatStyle.SECOND
val THIRD = CombatStyle.THIRD
val FOURTH = CombatStyle.FOURTH

data class style(
    /**
     * Combat Style, CombatStyle.FIRST
     */
    val combatStyle: CombatStyle,
    /**
     *Attack Animation, Animation(id)
     */
    val attackAnimation: Animation,
)

enum class CombatAnimation(
    vararg val combatstyle: style,
    val blockAnimation: Animation = Animation(424),
    val weaponType: WeaponType = WeaponType.NONE,
    val equipType: EquipmentType = EquipmentType.WEAPON,
    val itemIds: List<Int> = emptyList(),
) {
    // DEFAULTS

    UNARMED(
        style(CombatStyle.FIRST, Animations.PUNCH.crush),
        style(CombatStyle.SECOND, Animations.KICK.crush),
        style(CombatStyle.THIRD, Animations.PUNCH.crush),
        blockAnimation = Animations.BLOCK.block,
        weaponType = WeaponType.NONE,
        itemIds = listOf(-1),
    ),

    SHIELD(
        blockAnimation = Animations.SHIELD.block,
        equipType = EquipmentType.SHIELD,
    ),

    WHIP(
        style(CombatStyle.FIRST, Animations.WHIP.slash),
        style(CombatStyle.SECOND, Animations.WHIP.slash),
        style(CombatStyle.THIRD, Animations.WHIP.slash),
        blockAnimation = Animations.WHIP.block,
        weaponType = WeaponType.WHIP,
    ),

    BOW(
        style(CombatStyle.FIRST, Animations.BOW.default),
        style(CombatStyle.SECOND, Animations.BOW.default),
        style(CombatStyle.THIRD, Animations.BOW.default),
        weaponType = WeaponType.BOW,
    ),
    SLING(
        style(CombatStyle.FIRST, Animations.SLING.default),
        style(CombatStyle.SECOND, Animations.SLING.default),
        style(CombatStyle.THIRD, Animations.SLING.default),
        weaponType = WeaponType.SLING,
    ),

    CROSSBOW(
        style(CombatStyle.FIRST, Animations.CROSSBOW.default),
        style(CombatStyle.SECOND, Animations.CROSSBOW.default),
        style(CombatStyle.THIRD, Animations.CROSSBOW.default),
        weaponType = WeaponType.CROSSBOW,
    ),

    STAFF(
        style(CombatStyle.FIRST, Animations.STAFF.crush),
        style(CombatStyle.SECOND, Animations.STAFF.crush),
        style(CombatStyle.THIRD, Animations.STAFF.crush),
        blockAnimation = Animations.STAFF.block,
        weaponType = WeaponType.STAFF,
    ),

    LONGSWORD(
        style(CombatStyle.FIRST, Animations.LONGSWORD.slash),
        style(CombatStyle.SECOND, Animations.LONGSWORD.slash),
        style(CombatStyle.THIRD, Animations.LONGSWORD.stab),
        style(CombatStyle.FOURTH, Animations.LONGSWORD.slash),
        blockAnimation = Animations.LONGSWORD.block,
        weaponType = WeaponType.LONG_SWORD,
        equipType = EquipmentType.WEAPON,
    ),

    DAGGER(
        style(CombatStyle.FIRST, Animations.SHORTSWORD.stab),
        style(CombatStyle.SECOND, Animations.SHORTSWORD.stab),
        style(CombatStyle.THIRD, Animations.SHORTSWORD.slash),
        style(CombatStyle.FOURTH, Animations.SHORTSWORD.stab),
        blockAnimation = Animations.SHORTSWORD.block,
        weaponType = WeaponType.DAGGER,
        equipType = EquipmentType.WEAPON,
    ),

    MACE(
        style(CombatStyle.FIRST, Animations.MACE.crush),
        style(CombatStyle.SECOND, Animations.MACE.crush),
        style(CombatStyle.THIRD, Animations.MACE.stab),
        style(CombatStyle.FOURTH, Animations.MACE.crush),
        blockAnimation = Animations.MACE.block,
        weaponType = WeaponType.MACE,
        equipType = EquipmentType.WEAPON,
    ),

    HALBERD(
        style(CombatStyle.FIRST, Animations.HALBERD.stab),
        style(CombatStyle.SECOND, Animations.HALBERD.slash),
        style(CombatStyle.THIRD, Animations.HALBERD.stab),
        blockAnimation = Animations.HALBERD.block,
        weaponType = WeaponType.HALBERD,
        equipType = EquipmentType.WEAPON,
    ),

    SPEAR(
        style(CombatStyle.FIRST, Animations.SPEAR.stab),
        style(CombatStyle.SECOND, Animations.SPEAR.slash),
        style(CombatStyle.THIRD, Animations.SPEAR.stab),
        blockAnimation = Animations.SPEAR.block,
        weaponType = WeaponType.SPEAR,
        equipType = EquipmentType.WEAPON,
    ),

    HAMMER(
        style(CombatStyle.FIRST, Animations.WARHAMMER.crush),
        style(CombatStyle.SECOND, Animations.WARHAMMER.crush),
        style(CombatStyle.THIRD, Animations.WARHAMMER.crush),
        blockAnimation = Animations.WARHAMMER.block,
        weaponType = WeaponType.HAMMER,
        equipType = EquipmentType.WEAPON,
    ),

    HAMMER_EXTRA(
        style(CombatStyle.FIRST, Animations.WARHAMMER.stab),
        style(CombatStyle.SECOND, Animations.WARHAMMER.slash),
        style(CombatStyle.THIRD, Animations.WARHAMMER.stab),
        blockAnimation = Animations.WARHAMMER.block,
        weaponType = WeaponType.HAMMER_EXTRA,
        equipType = EquipmentType.WEAPON,
    ),

    CLAWS(
        style(CombatStyle.FIRST, Animations.CLAWS.slash),
        style(CombatStyle.SECOND, Animations.CLAWS.slash),
        style(CombatStyle.THIRD, Animations.CLAWS.stab),
        style(CombatStyle.FOURTH, Animations.CLAWS.slash),
        blockAnimation = Animations.CLAWS.block,
        weaponType = WeaponType.CLAWS,
    ),

    // NON DEFAULTS, additions under here

    KNIFE(
        style(CombatStyle.FIRST, Animations.THROWING_KNIFE.default),
        style(CombatStyle.SECOND, Animations.THROWING_KNIFE.default),
        style(CombatStyle.THIRD, Animations.THROWING_KNIFE.default),
        weaponType = WeaponType.THROWN,
        itemIds = listOf(*Knives.KNIVES),
    ),

    DART(
        style(CombatStyle.FIRST, Animations.THROWING_DART.default),
        style(CombatStyle.SECOND, Animations.THROWING_DART.default),
        style(CombatStyle.THIRD, Animations.THROWING_DART.default),
        weaponType = WeaponType.THROWN,
        itemIds = listOf(*Darts.DARTS),
    ),

    DEFENDERS(
        blockAnimation = Animations.DEFENDER.block,
        itemIds =
            listOf(
                Items.BRONZE_DEFENDER,
                Items.IRON_DEFENDER,
                Items.STEEL_DEFENDER,
                Items.BLACK_DEFENDER,
                Items.MITHRIL_DEFENDER,
                Items.ADAMANT_DEFENDER,
                Items.RUNE_DEFENDER,
                Items.DRAGON_DEFENDER,
            ),
    ),

    ;

    companion object {
        fun getCorrectStyle(
            combatAnimation: CombatAnimation,
            combatStyle: CombatStyle = CombatStyle.FIRST,
        ): style? {
            return combatAnimation.combatstyle.find { it.combatStyle == combatStyle }
        }

        fun getItemAnimation(
            itemId: Int = -1,
            weaponType: Int = -1,
            equipType: Int = -1,
        ): CombatAnimation? {
            // id are prioritised over weapontype
            var matchingCombatAnimation = CombatAnimation.values().find { itemId in it.itemIds }
            // if fail to find by idMatching, check by weaponType
            if (matchingCombatAnimation == null) {
                matchingCombatAnimation = CombatAnimation.values().find { it.weaponType.id == weaponType }
            }
            // if fail to find by weaponTypeMatching, check by equiptype
            if (matchingCombatAnimation == null) {
                matchingCombatAnimation = CombatAnimation.values().find { it.equipType.id == equipType }
            }
            return matchingCombatAnimation
        }
    }
}
