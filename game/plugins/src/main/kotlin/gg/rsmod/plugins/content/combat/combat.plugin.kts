package gg.rsmod.plugins.content.combat

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.model.attr.AGGRESSOR
import gg.rsmod.game.model.attr.COMBAT_TARGET_FOCUS_ATTR
import gg.rsmod.game.model.attr.FACING_PAWN_ATTR
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER
import gg.rsmod.game.model.timer.FROZEN_TIMER
import gg.rsmod.game.model.timer.STUN_TIMER
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks
import gg.rsmod.plugins.content.combat.strategy.MeleeCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.inter.attack.AttackTab

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

    if (pawn is Npc) {
        if (pawn.combatDef.spell > -1) {
            val spell = CombatSpell.values.firstOrNull { it.uniqueId == pawn.combatDef.spell }
            if (spell != null) {
                pawn.attr[Combat.CASTING_SPELL] = spell
            }
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

    var pathFound = PawnPathAction.walkTo(it, pawn, target, interactionRange = attackRange, lineOfSight = false)

    if (strategy == MeleeCombatStrategy && pawn.tile.getDistance(target.tile) <= pawn.getSize()) {
        pathFound = true
    }

    if (!pathFound) {
        pawn.stopMovement()
        if (pawn.entityType.isNpc) {
            /**
             * Npcs will keep trying to find a path to engage in combat
             * UNLESS, the target isn't engaging in combat with the npc
             */
            if (target.getCombatTarget() != pawn) {
                if (pawn.tile.isMulti(pawn.world) || target.tile.getDistance(pawn.tile) <= 6) {
                    return true
                }
                pawn.stopMovement()
                pawn.resetFacePawn()
                Combat.reset(pawn)
                return false
            }
        }
        if (pawn is Player) {
            when {
                pawn.timers.has(FROZEN_TIMER) -> pawn.message(Entity.MAGIC_STOPS_YOU_FROM_MOVING)
                pawn.timers.has(STUN_TIMER) -> pawn.message(Entity.YOURE_STUNNED)
                else -> pawn.message(Entity.YOU_CANT_REACH_THAT)
            }
            pawn.clearMapFlag()
        }
        pawn.resetFacePawn()
        Combat.reset(pawn)
        return false
    }

    pawn.stopMovement()

    if (Combat.isAttackDelayReady(pawn)) {
        if (Combat.canAttack(pawn, target, strategy)) {
            val IM_IN_MULTI = pawn.tile.isMulti(pawn.world)
            val ENEMY_IN_MULTI = target.tile.isMulti(target.world)

            val IM_UNDER_ATTACK = pawn.isBeingAttacked()
            // val IM_ATTACKING = pawn.isAttacking()

            val ENEMY_UNDER_ATTACK = target.isBeingAttacked()
            // val ENEMY_IS_ATTACKING = target.isAttacking()

            val LAST_HIT_BY = pawn.getLastHitBy()
            val ENEMY_LAST_HIT_BY = target.getLastHitBy()

            val IM_ATTACKED_BY_ENEMY = LAST_HIT_BY == target
            val ENEMY_ATTACKED_BY_YOU = ENEMY_LAST_HIT_BY == pawn

            // Determine the attack logic based on multi-combat and combat states
            if (IM_IN_MULTI || ENEMY_IN_MULTI || (IM_ATTACKED_BY_ENEMY && ENEMY_ATTACKED_BY_YOU)) {
                // The NPC can attack in multi-combat areas or if there's mutual aggression
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

                strategy.attack(pawn, target)
                Combat.postAttack(pawn, target)
            }
        } else {
            Combat.reset(pawn)
            return false
        }
    }
    return true
}
