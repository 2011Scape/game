package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.combat.XpMode
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.strategy.CombatStrategy
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.MeleeCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy

/**
 * @author Tom <rspsmods@gmail.com>
 */
object CombatConfigs {
    private const val PLAYER_DEFAULT_ATTACK_SPEED = 4

    private const val MIN_ATTACK_SPEED = 1

    private val DEFENDERS =
        intArrayOf(
            Items.BRONZE_DEFENDER,
            Items.IRON_DEFENDER,
            Items.STEEL_DEFENDER,
            Items.MITHRIL_DEFENDER,
            Items.BLACK_DEFENDER,
            Items.ADAMANT_DEFENDER,
            Items.RUNE_DEFENDER,
            Items.DRAGON_DEFENDER,
        )

    val BOOKS =
        intArrayOf(
            Items.HOLY_BOOK,
            Items.BOOK_OF_BALANCE,
            Items.UNHOLY_BOOK,
            Items.BOOK_OF_LAW,
            Items.BOOK_OF_WAR,
        )

    private val BOXING_GLOVES =
        intArrayOf(
            Items.BOXING_GLOVES,
            Items.BOXING_GLOVES_7673,
        )

    private val GODSWORDS =
        intArrayOf(
            Items.ARMADYL_GODSWORD,
            Items.BANDOS_GODSWORD,
            Items.SARADOMIN_GODSWORD,
            Items.ZAMORAK_GODSWORD,
        )

    private val DRAGON_DAGGERS =
        intArrayOf(
            Items.DRAGON_DAGGER,
            Items.DRAGON_DAGGER_P,
            Items.DRAGON_DAGGER_P_5680,
            Items.DRAGON_DAGGER_P_5698,
            Items.C_DRAGON_DAGGER_DEG,
            Items.CORRUPT_DRAGON_DAGGER,
        )

    fun getCombatStrategy(pawn: Pawn): CombatStrategy =
        when (getCombatClass(pawn)) {
            CombatClass.MELEE -> MeleeCombatStrategy
            CombatClass.MAGIC -> MagicCombatStrategy
            CombatClass.RANGED -> RangedCombatStrategy
            else -> throw IllegalStateException("Invalid combat class: ${getCombatClass(pawn)} for $pawn")
        }

    fun getCombatClass(pawn: Pawn): CombatClass {
        if (pawn is Npc) {
            return if (pawn.combatDef.spell > -1) CombatClass.MAGIC else pawn.combatClass
        }

        if (pawn is Player) {
            return when {
                pawn.attr.has(Combat.CASTING_SPELL) -> CombatClass.MAGIC
                pawn.hasWeaponType(
                    WeaponType.BOW,
                    WeaponType.SLING,
                    WeaponType.CHINCHOMPA,
                    WeaponType.CROSSBOW,
                    WeaponType.THROWN,
                ) -> CombatClass.RANGED
                else -> CombatClass.MELEE
            }
        }

        throw IllegalArgumentException("Invalid combat class for $pawn")
    }

    fun getAttackDelay(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.attackSpeed
        }

        if (pawn is Player) {
            val default = PLAYER_DEFAULT_ATTACK_SPEED
            val weapon = pawn.getEquipment(EquipmentType.WEAPON) ?: return default
            var speed = weapon.getDef(pawn.world.definitions).attackSpeed
            if (getCombatClass(pawn) == CombatClass.RANGED && getAttackStyle(pawn) == WeaponStyle.RAPID) {
                speed -= 1
            }
            if (getCombatClass(pawn) == CombatClass.MAGIC) {
                speed = 5
            }
            return Math.max(MIN_ATTACK_SPEED, speed)
        }

        throw IllegalArgumentException("Invalid attack delay for $pawn")
    }

    private fun getCombatStyle(style: Int): CombatStyle {
        if (style == CombatStyle.FIRST.id) {
            return CombatStyle.FIRST
        }
        if (style == CombatStyle.SECOND.id) {
            return CombatStyle.SECOND
        }
        if (style == CombatStyle.THIRD.id) {
            return CombatStyle.THIRD
        }
        return CombatStyle.FOURTH
    }

    private fun getWeaponType(player: Player): WeaponType? {
        return WeaponType.values().find { it.id == player.getWeaponType() }
    }

    fun getAttackAnimation(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.attackAnimation
        }

        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getCombatStyle(style)
            var weaponId = pawn.getEquipment(EquipmentType.WEAPON)?.id
            val itemAnimation = CombatAnimation.getItemAnimation(weaponId ?: -1, pawn.getWeaponType())
            if (itemAnimation != null) {
                val style = CombatAnimation.getCorrectStyle(itemAnimation, option)
                if (style != null) {
                    return style.attackAnimation.id
                }
            }
            return when {
                pawn.hasEquipped(EquipmentType.WEAPON, *GODSWORDS) ->
                    when (option) {
                        CombatStyle.THIRD -> 7048
                        CombatStyle.FOURTH -> 7042
                        else -> 7041
                    }
                pawn.hasWeaponType(WeaponType.AXE) ->
                    when (option) {
                        CombatStyle.FIRST, CombatStyle.FOURTH -> 401
                        else -> 395
                    }
                pawn.hasWeaponType(WeaponType.LONG_SWORD) ->
                    when (option) {
                        CombatStyle.THIRD -> 386
                        else -> 390
                    }
                pawn.hasWeaponType(WeaponType.TWO_HANDED) ->
                    when (option) {
                        CombatStyle.THIRD -> 406
                        else -> 407
                    }
                pawn.hasWeaponType(WeaponType.PICKAXE) ->
                    when (option) {
                        CombatStyle.THIRD -> 401
                        CombatStyle.FOURTH -> 400
                        else -> 395
                    }
                pawn.hasEquipped(EquipmentType.WEAPON, *DRAGON_DAGGERS) ->
                    when (option) {
                        CombatStyle.THIRD -> 395
                        else -> 396
                    }
                pawn.hasWeaponType(WeaponType.DAGGER) ->
                    when (option) {
                        CombatStyle.THIRD -> 390
                        else -> 400
                    }
                pawn.hasWeaponType(WeaponType.MACE) ->
                    when (option) {
                        CombatStyle.THIRD -> 400
                        else -> 401
                    }
                pawn.hasWeaponType(WeaponType.WHIP) ->
                    when (option) {
                        CombatStyle.SECOND -> 11969
                        else -> 11968
                    }
                pawn.hasWeaponType(WeaponType.SPEAR) ->
                    when (option) {
                        CombatStyle.SECOND -> 429
                        CombatStyle.THIRD -> 414
                        else -> 428
                    }
                pawn.hasWeaponType(WeaponType.HALBERD) ->
                    when (option) {
                        CombatStyle.SECOND -> 440
                        else -> 400
                    }
                pawn.hasWeaponType(WeaponType.SCYTHE) -> 440
                pawn.hasWeaponType(WeaponType.HAMMER) || pawn.hasWeaponType(WeaponType.HAMMER_EXTRA) -> 401
                pawn.hasWeaponType(WeaponType.BOW) -> 426
                pawn.hasWeaponType(WeaponType.CROSSBOW) -> 4230
                pawn.hasWeaponType(WeaponType.STAFF) || pawn.hasWeaponType(WeaponType.SCEPTRE) -> 419
                pawn.hasWeaponType(WeaponType.CHINCHOMPA) -> 2779
                pawn.hasWeaponType(
                    WeaponType.THROWN,
                ) ||
                    pawn.hasWeaponType(
                        WeaponType.THROWN_EXTRA,
                    ) -> if (pawn.hasEquipped(EquipmentType.WEAPON, Items.TOKTZXILUL)) 2614 else 929
                pawn.hasWeaponType(WeaponType.CLAWS) -> 393
                pawn.hasWeaponType(WeaponType.SLING) -> 789
                else -> if (style == 1) 423 else 422
            }
        }

        throw IllegalArgumentException("Invalid attack animation for $pawn")
    }

    fun getBlockAnimation(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.blockAnimation
        }

        if (pawn is Player) {
            val shield = pawn.getEquipment(EquipmentType.SHIELD)?.id
            val shieldAnimation =
                shield?.let {
                    CombatAnimation.getItemAnimation(
                        it,
                        equipType = EquipmentType.SHIELD.id,
                    )
                }
            if (shieldAnimation != null) {
                return shieldAnimation.blockAnimation.id
            }
            val weapon = pawn.getEquipment(EquipmentType.WEAPON)?.id ?: -1
            val weaponAnimation = weapon.let { CombatAnimation.getItemAnimation(it, weaponType = pawn.getWeaponType()) }
            if (weaponAnimation != null) {
                return weaponAnimation.blockAnimation.id
            }
            return when {
                pawn.hasEquipped(EquipmentType.SHIELD, *BOOKS) -> 420
                pawn.hasEquipped(EquipmentType.WEAPON, Items.SLED_4084) -> 1466
                pawn.hasEquipped(EquipmentType.WEAPON, Items.BASKET_OF_EGGS) -> 1834
                pawn.hasEquipped(EquipmentType.SHIELD, *DEFENDERS) -> 4177
                pawn.getEquipment(EquipmentType.SHIELD) != null -> 1156 // If wearing any shield, this animation is used

                pawn.hasEquipped(EquipmentType.WEAPON, *BOXING_GLOVES) -> 3679
                pawn.hasEquipped(EquipmentType.WEAPON, *GODSWORDS) -> 7056
                pawn.hasEquipped(EquipmentType.WEAPON, Items.ZAMORAKIAN_SPEAR) -> 1709

                pawn.hasWeaponType(WeaponType.DAGGER) -> 378
                pawn.hasWeaponType(WeaponType.LONG_SWORD) -> 388
                pawn.hasWeaponType(WeaponType.PICKAXE, WeaponType.CLAWS) -> 397
                pawn.hasWeaponType(WeaponType.MACE) -> 403
                pawn.hasWeaponType(WeaponType.TWO_HANDED) -> 7050
                pawn.hasWeaponType(WeaponType.STAFF) || pawn.hasWeaponType(WeaponType.SCEPTRE) -> 420
                pawn.hasWeaponType(WeaponType.BOW) -> 424
                pawn.hasWeaponType(WeaponType.SPEAR, WeaponType.HALBERD, WeaponType.SCYTHE) -> 430
                pawn.hasWeaponType(WeaponType.WHIP) || pawn.hasWeaponType(WeaponType.SLING) -> 11974
                else -> 424
            }
        }
        throw IllegalArgumentException("Invalid block animation for $pawn")
    }

    fun getAttackStyle(pawn: Pawn): WeaponStyle {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).weaponStyle
        }
        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getCombatStyle(style)
            val data = getWeaponType(pawn)?.let { WeaponCombatData.getAttackStyleType(it, option) }
            if (data != null) {
                return data.weaponStyle
            }
        }
        throw IllegalArgumentException("Invalid attack style for $pawn")
    }

    fun getCombatStyle(pawn: Pawn): StyleType {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).combatDef.attackStyleType
        }
        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getCombatStyle(style)
            val data = getWeaponType(pawn)?.let { WeaponCombatData.getAttackStyleType(it, option) }
            if (data != null) {
                return data.styleType
            }
        }
        throw IllegalArgumentException("Invalid combat style for $pawn")
    }

    fun getXpMode(player: Player): XpMode {
        val style = player.getAttackStyle()
        val option = getCombatStyle(style)
        val data = getWeaponType(player)?.let { WeaponCombatData.getAttackStyleType(it, option) }
        if (data != null) {
            return data.xpMode
        }
        throw IllegalArgumentException("Invalid xp mode for $player")
    }
}
