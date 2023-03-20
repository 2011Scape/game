package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.combat.AttackStyle
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatStyle
import gg.rsmod.game.model.combat.XpMode
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ChatMessageType
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

    private val DEFENDERS = intArrayOf(
        Items.BRONZE_DEFENDER,
        Items.IRON_DEFENDER,
        Items.STEEL_DEFENDER,
        Items.MITHRIL_DEFENDER,
        Items.BLACK_DEFENDER,
        Items.ADAMANT_DEFENDER,
        Items.RUNE_DEFENDER,
        Items.DRAGON_DEFENDER,
    )

    private val BOOKS = intArrayOf(
        Items.HOLY_BOOK,
        Items.BOOK_OF_BALANCE,
        Items.UNHOLY_BOOK,
        Items.BOOK_OF_LAW,
        Items.BOOK_OF_WAR,
    )

    private val BOXING_GLOVES = intArrayOf(
        Items.BOXING_GLOVES,
        Items.BOXING_GLOVES_7673,
    )

    private val GODSWORDS = intArrayOf(
        Items.ARMADYL_GODSWORD,
        Items.BANDOS_GODSWORD,
        Items.SARADOMIN_GODSWORD,
        Items.ZAMORAK_GODSWORD,
    )

    private val DRAGON_DAGGERS = intArrayOf(
        Items.DRAGON_DAGGER,
        Items.DRAGON_DAGGER_P,
        Items.DRAGON_DAGGER_P_5680,
        Items.DRAGON_DAGGER_P_5698,
        Items.C_DRAGON_DAGGER_DEG,
        Items.CORRUPT_DRAGON_DAGGER,
    )

    fun getCombatStrategy(pawn: Pawn): CombatStrategy = when (getCombatClass(pawn)) {
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
                pawn.hasWeaponType(WeaponType.BOW, WeaponType.CHINCHOMPA, WeaponType.CROSSBOW, WeaponType.THROWN) -> CombatClass.RANGED
                else -> CombatClass.MELEE
            }
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    fun getAttackDelay(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.attackSpeed
        }

        if (pawn is Player) {
            val default = PLAYER_DEFAULT_ATTACK_SPEED
            val weapon = pawn.getEquipment(EquipmentType.WEAPON) ?: return default
            var speed = weapon.getDef(pawn.world.definitions).attackSpeed
            if (getCombatClass(pawn) == CombatClass.RANGED && getAttackStyle(pawn) == AttackStyle.RAPID) {
                speed -= 1
            }
            if (getCombatClass(pawn) == CombatClass.MAGIC) {
                speed = 5
            }
            return Math.max(MIN_ATTACK_SPEED, speed)
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    private fun getOption(style: Int): WeaponStyleOption {
        if (style == WeaponStyleOption.FIRST.id)
            return WeaponStyleOption.FIRST
        if (style == WeaponStyleOption.SECOND.id)
            return WeaponStyleOption.SECOND
        if (style == WeaponStyleOption.THIRD.id)
            return WeaponStyleOption.THIRD
        return WeaponStyleOption.FOURTH
    }

    fun getAttackAnimation(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.attackAnimation
        }

        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getOption(style)

            return when {
                pawn.hasEquipped(EquipmentType.WEAPON, *GODSWORDS) -> when (option) {
                    WeaponStyleOption.THIRD -> 7048
                    WeaponStyleOption.FOURTH -> 7042
                    else -> 7041
                }
                pawn.hasWeaponType(WeaponType.AXE) -> when (option) {
                    WeaponStyleOption.FIRST, WeaponStyleOption.FOURTH -> 401
                    else -> 395
                }
                pawn.hasWeaponType(WeaponType.LONG_SWORD) -> when (option) {
                    WeaponStyleOption.THIRD -> 386
                    else -> 390
                }
                pawn.hasWeaponType(WeaponType.TWO_HANDED) -> when (option) {
                    WeaponStyleOption.THIRD -> 406
                    else -> 407
                }
                pawn.hasWeaponType(WeaponType.PICKAXE) -> when (option) {
                    WeaponStyleOption.THIRD -> 401
                    WeaponStyleOption.FOURTH -> 400
                    else -> 395
                }
                pawn.hasEquipped(EquipmentType.WEAPON, *DRAGON_DAGGERS) -> when (option) {
                    WeaponStyleOption.THIRD -> 395
                    else -> 396
                }
                pawn.hasWeaponType(WeaponType.DAGGER) -> when (option) {
                    WeaponStyleOption.THIRD -> 390
                    else -> 400
                }
                pawn.hasWeaponType(WeaponType.MACE) -> when (option) {
                    WeaponStyleOption.THIRD -> 400
                    else -> 401
                }
                pawn.hasWeaponType(WeaponType.WHIP) -> when (option) {
                    WeaponStyleOption.SECOND -> 11969
                    else -> 11968
                }
                pawn.hasWeaponType(WeaponType.SPEAR) -> when (option) {
                    WeaponStyleOption.SECOND -> 429
                    WeaponStyleOption.THIRD -> 414
                    else -> 428
                }
                pawn.hasWeaponType(WeaponType.HALBERD) -> when (option) {
                    WeaponStyleOption.SECOND -> 440
                    else -> 400
                }
                pawn.hasWeaponType(WeaponType.HAMMER) || pawn.hasWeaponType(WeaponType.HAMMER_EXTRA) -> 401
                pawn.hasWeaponType(WeaponType.BOW) -> 426
                pawn.hasWeaponType(WeaponType.CROSSBOW) -> 4230
                pawn.hasWeaponType(WeaponType.STAFF) || pawn.hasWeaponType(WeaponType.SCEPTRE) -> 419
                pawn.hasWeaponType(WeaponType.CHINCHOMPA) -> 7618
                pawn.hasWeaponType(WeaponType.THROWN) || pawn.hasWeaponType(WeaponType.THROWN_EXTRA) -> if (pawn.hasEquipped(EquipmentType.WEAPON, Items.TOKTZXILUL)) 7558 else 929
                pawn.hasWeaponType(WeaponType.CLAWS) -> 393
                else -> if (style == 1) 423 else 422
            }
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    fun getBlockAnimation(pawn: Pawn): Int {
        if (pawn is Npc) {
            return pawn.combatDef.blockAnimation
        }

        if (pawn is Player) {
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
                pawn.hasWeaponType(WeaponType.SPEAR, WeaponType.HALBERD) -> 430
                pawn.hasWeaponType(WeaponType.WHIP) -> 11974
                else -> 424
            }
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    fun getAttackStyle(pawn: Pawn): AttackStyle {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).attackStyle
        }

        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getOption(style)

            return when {
                //accurate, rapid, long_range
                pawn.hasWeaponType(WeaponType.BOW, WeaponType.CROSSBOW, WeaponType.THROWN, WeaponType.CHINCHOMPA) -> when (option) {
                    WeaponStyleOption.FIRST -> AttackStyle.ACCURATE
                    WeaponStyleOption.SECOND -> AttackStyle.RAPID
                    else -> AttackStyle.LONG_RANGE
                }
                //accurate, aggressive, defensive
                pawn.hasWeaponType(WeaponType.NONE,WeaponType.HAMMER,WeaponType.STAFF) -> when (option) {
                    WeaponStyleOption.FIRST  -> AttackStyle.ACCURATE
                    WeaponStyleOption.SECOND -> AttackStyle.AGGRESSIVE
                    else -> AttackStyle.DEFENSIVE
                }
                //accurate, aggressive, aggressive, defensive
                pawn.hasWeaponType(WeaponType.AXE, WeaponType.PICKAXE) -> when (option) {
                    WeaponStyleOption.FIRST -> AttackStyle.ACCURATE
                    WeaponStyleOption.SECOND, WeaponStyleOption.THIRD -> AttackStyle.AGGRESSIVE
                    else -> AttackStyle.DEFENSIVE
                }
                //accurate, aggressive, controlled, defensive
                pawn.hasWeaponType(WeaponType.LONG_SWORD, WeaponType.CLAWS) -> when (option) {
                    WeaponStyleOption.FIRST -> AttackStyle.ACCURATE
                    WeaponStyleOption.SECOND -> AttackStyle.AGGRESSIVE
                    WeaponStyleOption.THIRD -> AttackStyle.CONTROLLED
                    else -> AttackStyle.DEFENSIVE
                }
                //accurate, controlled, defensive
                pawn.hasWeaponType(WeaponType.WHIP) -> when (option) {
                    WeaponStyleOption.FIRST -> AttackStyle.ACCURATE
                    WeaponStyleOption.SECOND -> AttackStyle.CONTROLLED
                    else -> AttackStyle.DEFENSIVE
                }
                //controlled, aggressive, defensive
                pawn.hasWeaponType(WeaponType.HALBERD) -> when (option) {
                    WeaponStyleOption.FIRST -> AttackStyle.CONTROLLED
                    WeaponStyleOption.SECOND -> AttackStyle.AGGRESSIVE
                    else -> AttackStyle.CONTROLLED
                }
                //controlled, controlled, controlled, defensive
                pawn.hasWeaponType(WeaponType.SPEAR) -> when (option) {
                    WeaponStyleOption.FOURTH -> AttackStyle.DEFENSIVE
                    else -> AttackStyle.CONTROLLED
                }


                else -> AttackStyle.NONE
            }
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    fun getCombatStyle(pawn: Pawn): CombatStyle {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).combatStyle
        }

        if (pawn is Player) {
            val style = pawn.getAttackStyle()
            val option = getOption(style)

            return when {
                //only MAGIC
                pawn.attr.has(Combat.CASTING_SPELL) -> CombatStyle.MAGIC
                //only RANGE
                pawn.hasWeaponType(WeaponType.BOW, WeaponType.CROSSBOW, WeaponType.THROWN, WeaponType.CHINCHOMPA, WeaponType.THROWN_EXTRA) -> CombatStyle.RANGED
                //only CRUSH
                pawn.hasWeaponType(WeaponType.NONE, WeaponType.HAMMER,WeaponType.HAMMER_EXTRA, WeaponType.STAFF, WeaponType.SCEPTRE) -> CombatStyle.CRUSH
                //only slash
                pawn.hasWeaponType(WeaponType.WHIP) -> CombatStyle.SLASH
                //CRUSH, CRUSH, slash, CRUSH
                pawn.hasWeaponType(WeaponType.AXE) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.CRUSH
                    else -> CombatStyle.SLASH
                }
                //SLASH, SLASH, stab, SLASH
                pawn.hasWeaponType(WeaponType.CLAWS, WeaponType.LONG_SWORD) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.STAB
                    else -> CombatStyle.SLASH
                }
                //slash, ranged, magic
                pawn.hasWeaponType(WeaponType.SALAMANDER) -> when (option) {
                    WeaponStyleOption.FIRST -> CombatStyle.SLASH
                    WeaponStyleOption.SECOND -> CombatStyle.RANGED
                    else -> CombatStyle.MAGIC
                }
                //SLASH, SLASH, crush, SLASH
                pawn.hasWeaponType(WeaponType.TWO_HANDED) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.CRUSH
                    else -> CombatStyle.SLASH
                }
                //STAB, STAB, crush, STAB
                pawn.hasWeaponType(WeaponType.PICKAXE) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.CRUSH
                    else -> CombatStyle.STAB
                }
                //STAB, slash, STAB
                pawn.hasWeaponType(WeaponType.HALBERD) -> when (option) {
                    WeaponStyleOption.SECOND -> CombatStyle.SLASH
                    else -> CombatStyle.STAB
                }
                //SLASH, stab, crush, SLASH
                pawn.hasWeaponType(WeaponType.SCYTHE) -> when (option) {
                    WeaponStyleOption.SECOND -> CombatStyle.STAB
                    WeaponStyleOption.THIRD -> CombatStyle.CRUSH
                    else -> CombatStyle.SLASH
                }
                //STAB, slash, crush, STAB
                pawn.hasWeaponType(WeaponType.SPEAR) -> when (option) {
                    WeaponStyleOption.SECOND -> CombatStyle.SLASH
                    WeaponStyleOption.THIRD -> CombatStyle.CRUSH
                    else -> CombatStyle.STAB
                }
                //CRUSH, CRUSH, stab, CRUSH
                pawn.hasWeaponType(WeaponType.MACE) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.STAB
                    else -> CombatStyle.CRUSH
                }
                //STAB, STAB, slash, STAB
                pawn.hasWeaponType(WeaponType.DAGGER) -> when (option) {
                    WeaponStyleOption.THIRD -> CombatStyle.SLASH
                    else -> CombatStyle.STAB
                }
                else -> CombatStyle.NONE
            }
        }

        throw IllegalArgumentException("Invalid pawn type.")
    }

    fun getXpMode(player: Player): XpMode {
        val style = player.getAttackStyle()
        val option = getOption(style)

        return when {
            //attack strength defence
            player.hasWeaponType(WeaponType.NONE, WeaponType.STAFF, WeaponType.SCEPTRE, WeaponType.HAMMER, WeaponType.HAMMER_EXTRA) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.ATTACK
                    WeaponStyleOption.SECOND -> XpMode.STRENGTH
                    else -> XpMode.DEFENCE
                }
            }

            //attack, STRENGTH, STRENGTH, defence
            player.hasWeaponType(
                WeaponType.AXE,
                WeaponType.TWO_HANDED,
                WeaponType.PICKAXE,
                WeaponType.DAGGER,
                WeaponType.HAMMER_EXTRA,
                WeaponType.SCYTHE
            ) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.ATTACK
                    WeaponStyleOption.FOURTH -> XpMode.DEFENCE
                    else -> XpMode.STRENGTH
                }
            }
            //attack, strength, shared, defence
            player.hasWeaponType(WeaponType.LONG_SWORD, WeaponType.MACE, WeaponType.CLAWS) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.ATTACK
                    WeaponStyleOption.SECOND -> XpMode.STRENGTH
                    WeaponStyleOption.THIRD -> XpMode.SHARED
                    else -> XpMode.DEFENCE
                }
            }
            //attack, shared, defence
            player.hasWeaponType(WeaponType.WHIP) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.ATTACK
                    WeaponStyleOption.FIRST -> XpMode.SHARED
                    else -> XpMode.DEFENCE
                }
            }
            //SHARED, SHARED, SHARED, defence
            player.hasWeaponType(WeaponType.SPEAR) -> {
                when (option) {
                    WeaponStyleOption.FOURTH -> XpMode.DEFENCE
                    else -> XpMode.SHARED
                }
            }
            //shared, strength, defence
            player.hasWeaponType(WeaponType.HALBERD) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.SHARED
                    WeaponStyleOption.SECOND -> XpMode.STRENGTH
                    else -> XpMode.DEFENCE
                }
            }
            //ranged, ranged, shared
            player.hasWeaponType(WeaponType.BOW, WeaponType.CROSSBOW, WeaponType.THROWN, WeaponType.CHINCHOMPA, WeaponType.THROWN_EXTRA) -> {
                when (option) {
                    WeaponStyleOption.THIRD -> XpMode.SHARED
                    else -> XpMode.RANGED
                }
            }
            //strength, range, magic
            player.hasWeaponType(WeaponType.SALAMANDER) -> {
                when (option) {
                    WeaponStyleOption.FIRST -> XpMode.STRENGTH
                    WeaponStyleOption.SECOND -> XpMode.RANGED
                    else -> XpMode.MAGIC
                }
            }

            else -> XpMode.ATTACK
        }
    }
}