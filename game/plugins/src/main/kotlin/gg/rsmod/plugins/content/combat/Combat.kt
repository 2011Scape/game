package gg.rsmod.plugins.content.combat

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.attr.COMBAT_TARGET_FOCUS_ATTR
import gg.rsmod.game.model.attr.LAST_HIT_ATTR
import gg.rsmod.game.model.attr.LAST_HIT_BY_ATTR
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.NpcCombatDef
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER
import gg.rsmod.game.model.timer.ATTACK_DELAY
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.NpcSkills
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.strategy.CombatStrategy
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.MeleeCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.inter.attack.AttackTab
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Combat {
    val CASTING_SPELL = AttributeKey<CombatSpell>()
    val DAMAGE_DEAL_MULTIPLIER = AttributeKey<Double>()
    val DAMAGE_TAKE_MULTIPLIER = AttributeKey<Double>()
    val BOLT_ENCHANTMENT_EFFECT = AttributeKey<Boolean>()

    const val PRIORITY_PID_VARP = 1075

    const val SELECTED_AUTOCAST_VARP = 108
    const val DEFENSIVE_CAST_VARP = 439

    fun reset(pawn: Pawn) {
        pawn.attr.remove(COMBAT_TARGET_FOCUS_ATTR)
    }

    fun canAttack(
        pawn: Pawn,
        target: Pawn,
        combatClass: CombatClass,
    ): Boolean = canEngage(pawn, target) && getStrategy(combatClass).canAttack(pawn, target)

    fun canAttack(
        pawn: Pawn,
        target: Pawn,
        strategy: CombatStrategy,
    ): Boolean = canEngage(pawn, target) && strategy.canAttack(pawn, target)

    fun isAttackDelayReady(pawn: Pawn): Boolean = !pawn.timers.has(ATTACK_DELAY)

    fun postAttack(
        pawn: Pawn,
        target: Pawn,
    ) {
        pawn.timers[ATTACK_DELAY] = CombatConfigs.getAttackDelay(pawn)
        target.timers[ACTIVE_COMBAT_TIMER] = 17 // 10,2 seconds
        pawn.attr[BOLT_ENCHANTMENT_EFFECT] = false

        pawn.attr[LAST_HIT_ATTR] = WeakReference(target)
        target.attr[LAST_HIT_BY_ATTR] = WeakReference(pawn)

        if (target is Player && target.interfaces.getModal() != -1) {
            target.closeInterface(target.interfaces.getModal())
            target.interfaces.setModal(-1)
        }

        if (target is Player && target.interfaces.isVisible(740)) {
            if (target.getVarp(AttackTab.DISABLE_AUTO_RETALIATE_VARP) == 0) {
                target.interruptQueues()
                target.closeComponent(parent = 752, child = 13)
                target.attack(pawn)
            }
        }

        // TODO: Find proper poison chances
        if (pawn is Npc) {
            if (pawn.combatDef.poisonDamage > 0 && pawn.world.random(10) < 4) {
                target.poison(pawn.combatDef.poisonDamage)
            }
        }
    }

    fun postDamage(
        pawn: Pawn,
        target: Pawn,
    ) {
        if (pawn.attr.has(CASTING_SPELL)) {
            pawn.attr.remove(CASTING_SPELL)
        }
        if (target.isDead()) {
            return
        }
        if (target.lock.canAttack()) {
            if (target.entityType.isNpc) {
                if (!target.attr.has(COMBAT_TARGET_FOCUS_ATTR) ||
                    target.attr[COMBAT_TARGET_FOCUS_ATTR]!!.get() != pawn
                ) {
                    target.attack(pawn)
                }
            } else if (target is Player) {
                if (target.getVarp(AttackTab.DISABLE_AUTO_RETALIATE_VARP) == 0 &&
                    target.getCombatTarget() != pawn &&
                    !target.hasMoveDestination()
                ) {
                    target.attack(pawn)
                }
            }
        }
    }

    fun getNpcXpMultiplier(npc: Npc): Double {
        val attackLvl = npc.stats.getMaxLevel(NpcSkills.ATTACK)
        val strengthLvl = npc.stats.getMaxLevel(NpcSkills.STRENGTH)
        val defenceLvl = npc.stats.getMaxLevel(NpcSkills.DEFENCE)
        val hitpoints = npc.getMaximumLifepoints()

        if (npc.name.contains("kolodion", ignoreCase = true)) {
            return 0.0
        }

        val averageLvl = Math.floor((attackLvl + strengthLvl + defenceLvl + hitpoints) / 4.0)
        val averageDefBonus =
            Math.floor(
                (
                    npc.getBonus(BonusSlot.DEFENCE_STAB) + npc.getBonus(BonusSlot.DEFENCE_SLASH) +
                        npc.getBonus(BonusSlot.DEFENCE_CRUSH)
                ) /
                    3.0,
            )
        return (
            1.0 +
                Math.floor(averageLvl * (averageDefBonus + npc.getStrengthBonus() + npc.getAttackBonus()) / 5120.0) /
                40.0
        ) *
            npc.combatDef.xpMultiplier
    }

    fun raycast(
        pawn: Pawn,
        target: Pawn,
        distance: Int,
        projectile: Boolean,
    ): Boolean {
        val world = pawn.world
        val start = pawn.tile
        val end = target.tile

        return start.isWithinRadius(end, distance) && world.collision.raycast(start, end, projectile = projectile)
    }

    suspend fun moveToAttackRange(
        it: QueueTask,
        pawn: Pawn,
        target: Pawn,
        distance: Int,
        projectile: Boolean,
    ): Boolean {
        val world = pawn.world
        val start = pawn.tile
        val end = target.tile

        val srcSize = pawn.getSize()
        val dstSize = Math.max(distance, target.getSize())

        val touching =
            if (distance >
                1
            ) {
                areOverlapping(start.x, start.z, srcSize, srcSize, end.x, end.z, dstSize, dstSize)
            } else {
                areBordering(start.x, start.z, srcSize, srcSize, end.x, end.z, dstSize, dstSize)
            }
        val withinRange = touching && world.collision.raycast(start, end, projectile = projectile)
        return withinRange || PawnPathAction.walkTo(it, pawn, target, interactionRange = distance, lineOfSight = false)
    }

    fun getProjectileLifespan(
        source: Pawn,
        target: Tile,
        type: ProjectileType,
    ): Int =
        when (type) {
            ProjectileType.MAGIC, ProjectileType.FIERY_BREATH, ProjectileType.TELEKINETIC_GRAB -> {
                val fastPath = source.world.collision.raycastTiles(source.tile, target)
                5 + (fastPath * 10)
            }
            else -> {
                val distance = source.tile.getDistance(target)
                type.calculateLife(distance)
            }
        }

    fun canEngage(
        pawn: Pawn,
        target: Pawn,
    ): Boolean {
        if (pawn.isDead() || target.isDead() || pawn.invisible || target.invisible) {
            return false
        }

        // Check if either the attacker or the target is in a multi-combat area
        val pawnInMulti = pawn.tile.isMulti(pawn.world)
        val targetInMulti = target.tile.isMulti(target.world)

        // Modify logic to handle multi-way combat
        if (pawnInMulti || targetInMulti) {
            // In multi-combat areas, multiple entities can engage the same target
            if (target.isBeingAttacked() && target.getCombatTarget() != pawn) {
                return true
            }
        } else {
            // In single combat areas, check if the target is already engaged in combat
            if (target.isAttacking() && target.getCombatTarget() != pawn) {
                if (pawn is Player) {
                    pawn.message("Someone is already fighting this.")
                }
                return false
            }
        }

        val maxDistance =
            when {
                pawn is Player && pawn.hasLargeViewport() -> Player.LARGE_VIEW_DISTANCE
                else -> Player.NORMAL_VIEW_DISTANCE
            }
        if (!pawn.tile.isWithinRadius(target.tile, maxDistance)) {
            return false
        }

        val pvp = pawn.entityType.isPlayer && target.entityType.isPlayer

        if (pawn is Player) {
            if (!pawn.isOnline) {
                return false
            }

            if (pawn.invisible && pvp) {
                pawn.message("You can't attack while invisible.")
                return false
            }
        } else if (pawn is Npc) {
            if (!pawn.isSpawned()) {
                return false
            }
        }

        if (target is Npc) {
            if (!target.isSpawned()) {
                return false
            }
            if (!target.def.isAttackable() ||
                target.combatDef.lifepoints == -1 ||
                target.combatDef == NpcCombatDef.DEFAULT
            ) {
                (pawn as? Player)?.message("You can't attack this npc.")
                (pawn as? Player)?.message(
                    "Npc ID: ${target.def.id} is missing combat definitions, please report this on Discord.",
                )
                return false
            }
        } else if (target is Player) {
            if (!target.isOnline || target.invisible) {
                return false
            }

            if (!target.lock.canBeAttacked()) {
                return false
            }

            if (pvp) {
                pawn as Player

                if (!inPvpArea(pawn)) {
                    pawn.message("You can't attack players here.")
                    return false
                }

                if (!inPvpArea(target)) {
                    return false
                }

                val combatLvlRange = getValidCombatLvlRange(pawn)
                if (target.combatLevel !in combatLvlRange) {
                    pawn.message("The level difference between you and your opponent is too great.")
                    return false
                }
            }
        }
        return true
    }

    private fun inPvpArea(player: Player): Boolean = player.tile.getWildernessLevel() > 0

    private fun getValidCombatLvlRange(player: Player): IntRange {
        val wildLvl = player.tile.getWildernessLevel()
        val minLvl = Math.max(Skills.MIN_COMBAT_LVL, player.combatLevel - wildLvl)
        val maxLvl = Math.min(Skills.MAX_COMBAT_LVL, player.combatLevel + wildLvl)
        return minLvl..maxLvl
    }

    private fun getStrategy(combatClass: CombatClass): CombatStrategy =
        when (combatClass) {
            CombatClass.MELEE -> MeleeCombatStrategy
            CombatClass.RANGED -> RangedCombatStrategy
            CombatClass.MAGIC -> MagicCombatStrategy
        }

    private fun areOverlapping(
        x1: Int,
        z1: Int,
        width1: Int,
        length1: Int,
        x2: Int,
        z2: Int,
        width2: Int,
        length2: Int,
    ): Boolean {
        val a = Box(x1, z1, width1 - 1, length1 - 1)
        val b = Box(x2, z2, width2 - 1, length2 - 1)

        if (a.x1 > b.x2 || b.x1 > a.x2) {
            return false
        }

        if (a.z1 > b.z2 || b.z1 > a.z2) {
            return false
        }

        return true
    }

    /**
     * Checks to see if two AABB are bordering, but not overlapping.
     */
    fun areBordering(
        x1: Int,
        z1: Int,
        width1: Int,
        length1: Int,
        x2: Int,
        z2: Int,
        width2: Int,
        length2: Int,
    ): Boolean {
        val a = Box(x1, z1, width1 - 1, length1 - 1)
        val b = Box(x2, z2, width2 - 1, length2 - 1)

        if (b.x1 in a.x1..a.x2 && b.z1 in a.z1..a.z2 || b.x2 in a.x1..a.x2 && b.z2 in a.z1..a.z2) {
            return false
        }

        if (b.x1 > a.x2 + 1) {
            return false
        }

        if (b.x2 < a.x1 - 1) {
            return false
        }

        if (b.z1 > a.z2 + 1) {
            return false
        }

        if (b.z2 < a.z1 - 1) {
            return false
        }
        return true
    }

    data class Box(
        val x: Int,
        val z: Int,
        val width: Int,
        val length: Int,
    ) {
        val x1: Int get() = x

        val x2: Int get() = x + width

        val z1: Int get() = z

        val z2: Int get() = z + length
    }
}
