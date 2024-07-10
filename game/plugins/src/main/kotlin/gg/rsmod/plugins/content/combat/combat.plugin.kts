package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.attr.AGGRESSOR
import gg.rsmod.game.model.attr.COMBAT_TARGET_FOCUS_ATTR
import gg.rsmod.game.model.attr.FACING_PAWN_ATTR
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER
import gg.rsmod.game.model.timer.FROZEN_TIMER
import gg.rsmod.game.model.timer.STUN_TIMER
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks
import gg.rsmod.plugins.content.combat.strategy.CombatStrategy
import gg.rsmod.plugins.content.combat.strategy.MeleeCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.inter.attack.AttackTab
import kotlin.math.abs
import kotlin.math.max

set_combat_logic {
    if (pawn.getCombatTarget() != null) {
        pawn.queue {
            while (true) {
                if (!cycle(this)) {
                    break
                }
                wait(1)
            }
        }
    }
}

on_player_option("Attack") {
    val target = pawn.attr[INTERACTING_PLAYER_ATTR]?.get() ?: return@on_player_option
    player.attack(target)
}

on_timer(ACTIVE_COMBAT_TIMER) {
    val pawn = pawn
    if (pawn.attr.has(AGGRESSOR)) {
        pawn.attr.remove(AGGRESSOR)
    }
}

/*suspend fun cycle(it: QueueTask): Boolean {
    // Retrieve pawn and target from the task
    val pawn = it.pawn
    val target = pawn.attr[COMBAT_TARGET_FOCUS_ATTR]?.get() ?: return false

    // Retrieve combat strategy and attack range for the pawn
    val strategy = CombatConfigs.getCombatStrategy(pawn)
    val attackRange = strategy.getAttackRange(pawn)

    // Check if the strategy is projectile-based
    val isProjectile = strategy !is MeleeCombatStrategy

    // Check if the pawn can attack
    if (!pawn.lock.canAttack()) {
        Combat.reset(pawn)
        return false
    }

    // Ensure the pawn is facing the target
    if (pawn.attr[FACING_PAWN_ATTR] != target) {
        pawn.facePawn(target)
    }

    // Check if the pawn can engage the target
    if (!Combat.canEngage(pawn, target)) {
        Combat.reset(pawn)
        pawn.resetFacePawn()
        return false
    }

    // If the pawn is an NPC and has a spell to cast, set the casting spell attribute
    if (pawn is Npc && pawn.combatDef.spell > -1) {
        val spell = CombatSpell.values.firstOrNull { it.uniqueId == pawn.combatDef.spell }
        spell?.let { pawn.attr[Combat.CASTING_SPELL] = it }
    }

    // If the pawn is a player, set the priority PID varp and auto cast spell
    if (pawn is Player) {
        pawn.setVarp(Combat.PRIORITY_PID_VARP, target.index)
        if (!pawn.attr.has(Combat.CASTING_SPELL) && pawn.getVarp(Combat.SELECTED_AUTOCAST_VARP) != 0) {
            val spell = CombatSpell.values.firstOrNull { it.autoCastId == pawn.getVarp(Combat.SELECTED_AUTOCAST_VARP) }
            spell?.let { pawn.attr[Combat.CASTING_SPELL] = it }
        }
    }

    // Calculate distance to target
    val distanceToTarget = distanceBetween(pawn, target)

    if (isProjectile){ // When using Magic / Ranged
        if (pawn.hasLineOfSightTo(target, true, 12)){
            pawn.stopMovement() // Stop movement if within attack range and has line of sight
            if (Combat.isAttackDelayReady(pawn) && Combat.canAttack(pawn, target, strategy)) {
                handleAttack(pawn, target, strategy)
            }
        } else {
            val pathFound = pawn.moveToAttackRange(it ,target, attackRange, isProjectile)
            if (!pathFound) handlePathNotFound(pawn, target)
        }
    } else { // When using Melee
        val pathFound = pawn.moveToAttackRange(it ,target, attackRange, isProjectile)
        if (!pathFound) handlePathNotFound(pawn, target)
        if (distanceToTarget <= attackRange) {
            pawn.stopMovement()
            if (Combat.isAttackDelayReady(pawn) && Combat.canAttack(pawn, target, strategy)) {
                handleAttack(pawn, target, strategy)
            }
        }
    }
    if (distanceToTarget <= attackRange){
        pawn.stopMovement() // Stop movement if within attack range and has line of sight
        if (Combat.isAttackDelayReady(pawn) && Combat.canAttack(pawn, target, strategy)) {
            handleAttack(pawn, target, strategy)
        }
    } else {
        // If the target is not already within attack range, walk to the target's tile
        val pathFound = pawn.moveToAttackRange(it ,target, attackRange, isProjectile)
        if (!pathFound) handlePathNotFound(pawn, target)
        // PawnPathAction.walkTo(it, pawn, target, interactionRange = attackRange, lineOfSight = false)
    }

    return true
}*/

suspend fun cycle(it: QueueTask): Boolean {
    val pawn = it.pawn
    val target = pawn.attr[COMBAT_TARGET_FOCUS_ATTR]?.get() ?: return false

    if (!pawn.lock.canAttack()) {
        Combat.reset(pawn)
        return false
    }

    if (pawn.attr[FACING_PAWN_ATTR] != target) {
        pawn.facePawn(target)
    }

    if (!Combat.canEngage(pawn, target)) {
        Combat.reset(pawn)
        pawn.resetFacePawn()
        return false
    }

    if (pawn is Npc && pawn.combatDef.spell > -1) {
        val spell = CombatSpell.values.firstOrNull { it.uniqueId == pawn.combatDef.spell }
        if (spell != null) {
            pawn.attr[Combat.CASTING_SPELL] = spell
        }
    }

    if (pawn is Player) {
        pawn.setVarp(Combat.PRIORITY_PID_VARP, target.index)
        if (!pawn.attr.has(Combat.CASTING_SPELL) && pawn.getVarp(Combat.SELECTED_AUTOCAST_VARP) != 0) {
            val spell = CombatSpell.values.firstOrNull { it.autoCastId == pawn.getVarp(Combat.SELECTED_AUTOCAST_VARP) }
            if (spell != null) {
                pawn.attr[Combat.CASTING_SPELL] = spell
            }
        }
    }

    val strategy = CombatConfigs.getCombatStrategy(pawn)
    val attackRange = strategy.getAttackRange(pawn)
    val isProjectile = strategy !is MeleeCombatStrategy

    if (pawn.tile.getDistance(target.tile) <= attackRange) {
        pawn.stopMovement()

        if (Combat.isAttackDelayReady(pawn)) {
            if (Combat.canAttack(pawn, target, strategy)) {
                val IM_IN_MULTI = pawn.tile.isMulti(pawn.world)
                val ENEMY_IN_MULTI = target.tile.isMulti(target.world)

                val IM_UNDER_ATTACK = pawn.isBeingAttacked()
                val ENEMY_UNDER_ATTACK = target.isBeingAttacked()

                val LAST_HIT_BY = pawn.getLastHitBy()
                val ENEMY_LAST_HIT_BY = target.getLastHitBy()

                val IM_ATTACKED_BY_ENEMY = LAST_HIT_BY == target
                val ENEMY_ATTACKED_BY_YOU = ENEMY_LAST_HIT_BY == pawn

                if (IM_IN_MULTI || ENEMY_IN_MULTI || (IM_ATTACKED_BY_ENEMY && ENEMY_ATTACKED_BY_YOU)) {
                    strategy.attack(pawn, target)
                    Combat.postAttack(pawn, target)
                } else {
                    if (IM_UNDER_ATTACK && !IM_ATTACKED_BY_ENEMY) {
                        if (pawn is Player) {
                            pawn.message("I'm already under attack.")
                        }
                        return false
                    }
                    if (ENEMY_UNDER_ATTACK && !ENEMY_ATTACKED_BY_YOU) {
                        if (pawn is Player) {
                            pawn.message("That ${if (target is Player) "player" else "npc"} is already in combat.")
                        }
                        return false
                    }
                    if (pawn is Player && target is Npc) {
                        if (target.combatDef.slayerReq > pawn.skills.getMaxLevel(Skills.SLAYER)) {
                            pawn.message("You need a higher Slayer level to know how to wound this monster.")
                            return false
                        }
                    }
                    if (pawn is Player &&
                        AttackTab.isSpecialEnabled(pawn) &&
                        pawn.getEquipment(EquipmentType.WEAPON) != null
                    ) {
                        AttackTab.disableSpecial(pawn)
                        if (SpecialAttacks.execute(pawn, target, world)) {
                            Combat.postAttack(pawn, target)
                            return true
                        }
                        pawn.message("You don't have enough power left.")
                    }
                    if (pawn.hasLineOfSightTo(target, isProjectile, attackRange)) {
                        strategy.attack(pawn, target)
                        Combat.postAttack(pawn, target)
                    }
                }
            } else {
                Combat.reset(pawn)
                return false
            }
        }
    } else {
        val pathFound = pawn.moveToAttackRange(it, target, attackRange, isProjectile)
        if (!pathFound) handlePathNotFound(pawn, target)
    }
    return true
}

fun closest(
    pos: Tile,
    other: Tile,
    width: Int,
    length: Int,
): Tile {
    val occupiedX = pos.x + width - 1
    val occupiedZ = pos.z + length - 1
    val x =
        if (other.x <= pos.x) {
            pos.x
        } else if (other.x >= occupiedX) {
            occupiedX
        } else {
            other.x
        }
    val z =
        if (other.z <= pos.z) {
            pos.z
        } else if (other.z >= occupiedZ) {
            occupiedZ
        } else {
            other.z
        }
    return Tile(x, z)
}

fun handlePathNotFound(
    pawn: Pawn,
    target: Pawn,
) {
    pawn.stopMovement()
    if (pawn.entityType.isNpc && target.getCombatTarget() != pawn) {
        if (pawn.tile.isMulti(pawn.world) || target.tile.getDistance(pawn.tile) <= 6) {
            return
        }
        pawn.stopMovement()
        pawn.resetFacePawn()
        Combat.reset(pawn)
    }
    if (pawn is Player) {
        when {
            pawn.timers.has(FROZEN_TIMER) -> pawn.message(Entity.MAGIC_STOPS_YOU_FROM_MOVING)
            pawn.timers.has(STUN_TIMER) -> pawn.message(Entity.YOURE_STUNNED)
            else -> pawn.message(Entity.YOU_CANT_REACH_THAT)
        }
        pawn.clearMapFlag()
        pawn.resetFacePawn()
        Combat.reset(pawn)
    }
}

fun distanceTo(
    pos: Tile,
    other: Tile,
    width: Int,
    length: Int,
): Int {
    val p1 = closest(pos, other, width, length)
    val p2 = closest(other, pos, width, length)
    return max(abs(p1.x - p2.x), abs(p1.z - p2.z))
}

fun handleAttack(
    pawn: Pawn,
    target: Pawn,
    strategy: CombatStrategy,
) {
    pawn.stopMovement()
    val IM_IN_MULTI = pawn.tile.isMulti(pawn.world)
    val ENEMY_IN_MULTI = target.tile.isMulti(target.world)

    val IM_UNDER_ATTACK = pawn.isBeingAttacked()
    val ENEMY_UNDER_ATTACK = target.isBeingAttacked()

    val LAST_HIT_BY = pawn.getLastHitBy()
    val ENEMY_LAST_HIT_BY = target.getLastHitBy()

    val IM_ATTACKED_BY_ENEMY = LAST_HIT_BY == target
    val ENEMY_ATTACKED_BY_YOU = ENEMY_LAST_HIT_BY == pawn

    if (IM_IN_MULTI || ENEMY_IN_MULTI || (IM_ATTACKED_BY_ENEMY && ENEMY_ATTACKED_BY_YOU)) {
        strategy.attack(pawn, target)
        Combat.postAttack(pawn, target)
    } else {
        handleNonMultiAttack(pawn, target, IM_UNDER_ATTACK, ENEMY_UNDER_ATTACK)
    }
}

fun handleNonMultiAttack(
    pawn: Pawn,
    target: Pawn,
    imUnderAttack: Boolean,
    enemyUnderAttack: Boolean,
) {
    /*if (imUnderAttack && !pawn.isAttacking()) {
        pawn.message("I'm already under attack.")
        return
    }
    if (enemyUnderAttack && !target.isAttackedByEnemy(pawn)) {
        pawn.asPlayer()?.message("That ${if (target is Player) "player" else "npc"} is already in combat.")
        return
    }*/
    if ((pawn is Player) && (target is Npc) && (target.combatDef.slayerReq > pawn.skills.getMaxLevel(Skills.SLAYER))) {
        pawn.message("You need a higher Slayer level to know how to wound this monster.")
        return
    }
    if (pawn is Player && AttackTab.isSpecialEnabled(pawn) && pawn.getEquipment(EquipmentType.WEAPON) != null) {
        handleSpecialAttack(pawn, target)
        return
    }
    val strategy = CombatConfigs.getCombatStrategy(pawn)
    strategy.attack(pawn, target)
    Combat.postAttack(pawn, target)
}

fun handleSpecialAttack(
    pawn: Player,
    target: Pawn,
) {
    AttackTab.disableSpecial(pawn)
    if (SpecialAttacks.execute(pawn, target, pawn.world)) {
        Combat.postAttack(pawn, target)
    } else {
        pawn.message("You don't have enough power left.")
    }
}

fun distanceBetween(
    pawn: Pawn,
    target: Pawn,
): Int {
    val dx = abs(pawn.tile.x - target.tile.x)
    val dz = Math.abs(pawn.tile.z - target.tile.z)
    return dx.coerceAtLeast(dz)
}
