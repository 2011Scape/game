package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.combat.XpMode
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.ext.CombatStyle

data class AttackStyleData(
    /**
     * Combat Style, CombatStyle.FIRST
     */
    val combatStyle: CombatStyle,
    /**
     * Weapon Style, WeaponStyle.AGGRESSIVE
     */
    val weaponStyle: WeaponStyle,
    /**
     * Style Type, StyleType.STAB.
     */
    val styleType: StyleType,
    /**
     *Experience Mode, XpMode.STRENGTH_XP
     */
    val xpMode: XpMode,
)

enum class WeaponCombatData(
    val type: Array<out WeaponType>,
    vararg val style: AttackStyleData,
) {
    UNARMED(
        arrayOf(WeaponType.NONE, WeaponType.SCEPTRE, WeaponType.STAFF, WeaponType.HAMMER, WeaponType.HAMMER_EXTRA),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.CRUSH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.CRUSH, XpMode.DEFENCE_XP),
    ),

    BOW(
        arrayOf(WeaponType.BOW, WeaponType.CROSSBOW, WeaponType.THROWN, WeaponType.THROWN_EXTRA, WeaponType.CHINCHOMPA),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.RANGED, XpMode.RANGED_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.RAPID, StyleType.RANGED, XpMode.RANGED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.LONG_RANGE, StyleType.RANGED, XpMode.SHARED_XP),
    ),

    AXE(
        arrayOf(WeaponType.AXE),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    PICKAXE(
        arrayOf(WeaponType.PICKAXE),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.STAB, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.STAB, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.STAB, XpMode.DEFENCE_XP),
    ),

    LONGSWORD(
        arrayOf(WeaponType.LONG_SWORD),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.CONTROLLED, StyleType.STAB, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    TWO_HANDED_SWORD(
        arrayOf(WeaponType.TWO_HANDED),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    DAGGER(
        arrayOf(WeaponType.DAGGER),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.STAB, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.STAB, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.STAB, XpMode.DEFENCE_XP),
    ),

    MACE(
        arrayOf(WeaponType.MACE),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.CRUSH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.CONTROLLED, StyleType.STAB, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.CRUSH, XpMode.DEFENCE_XP),
    ),

    SPEAR(
        arrayOf(WeaponType.SPEAR),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.CONTROLLED, StyleType.STAB, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.CONTROLLED, StyleType.SLASH, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.CONTROLLED, StyleType.CRUSH, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.STAB, XpMode.DEFENCE_XP),
    ),

    HALBERD(
        arrayOf(WeaponType.HALBERD),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.CONTROLLED, StyleType.STAB, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.STAB, XpMode.DEFENCE_XP),
    ),

    CLAWS(
        arrayOf(WeaponType.CLAWS),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.CONTROLLED, StyleType.STAB, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    SALAMANDER(
        arrayOf(WeaponType.SALAMANDER),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.AGGRESSIVE, StyleType.SLASH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.ACCURATE, StyleType.RANGED, XpMode.RANGED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.MAGIC, XpMode.MAGIC_XP),
    ),

    WHIP(
        arrayOf(WeaponType.WHIP),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.CONTROLLED, StyleType.SLASH, XpMode.SHARED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    SCYTHE(
        arrayOf(WeaponType.SCYTHE),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.STAB, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.FOURTH, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    FLAIL(
        arrayOf(WeaponType.FLAIL),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.SLASH, XpMode.ATTACK_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.AGGRESSIVE, StyleType.CRUSH, XpMode.STRENGTH_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.DEFENSIVE, StyleType.SLASH, XpMode.DEFENCE_XP),
    ),

    SLING(
        arrayOf(WeaponType.SLING),
        AttackStyleData(CombatStyle.FIRST, WeaponStyle.ACCURATE, StyleType.RANGED, XpMode.RANGED_XP),
        AttackStyleData(CombatStyle.SECOND, WeaponStyle.RAPID, StyleType.RANGED, XpMode.RANGED_XP),
        AttackStyleData(CombatStyle.THIRD, WeaponStyle.LONG_RANGE, StyleType.RANGED, XpMode.SHARED_XP),
    ),
    ;

    companion object {
        fun getAttackStyleType(
            weaponType: WeaponType,
            combatStyle: CombatStyle,
        ): AttackStyleData? {
            val matchingWeaponCombatData = WeaponCombatData.values().find { it.type.contains(weaponType) }
            return matchingWeaponCombatData?.style?.find { it.combatStyle == combatStyle }
        }
    }
}
