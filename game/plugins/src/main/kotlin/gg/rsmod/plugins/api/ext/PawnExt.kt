package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Hit
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.*
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.timer.FROZEN_TIMER
import gg.rsmod.game.model.timer.STUN_TIMER
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.PrayerIcon
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.content.combat.CombatConfigs

fun Pawn.getCommandArgs(): Array<String> = attr[COMMAND_ARGS_ATTR]!!

fun Pawn.getInteractingSlot(): Int = attr[INTERACTING_SLOT_ATTR]!!

fun Pawn.getInteractingItem(): Item = attr[INTERACTING_ITEM]!!.get()!!

fun Pawn.getInteractingItemId(): Int = attr[INTERACTING_ITEM_ID]!!

fun Pawn.getInteractingGroundItem(): GroundItem = attr[INTERACTING_GROUNDITEM_ATTR]!!.get()!!

fun Pawn.getInteractingButton(): Int = attr[INTERACTING_BUTTON_ID]!!

fun Pawn.getInteractingItemSlot(): Int = attr[INTERACTING_ITEM_SLOT]!!

fun Pawn.getInteractingOtherItemId(): Int = attr[OTHER_ITEM_ID_ATTR]!!

fun Pawn.getInteractingOtherItemSlot(): Int = attr[OTHER_ITEM_SLOT_ATTR]!!

fun Pawn.getInteractingOption(): Int = attr[INTERACTING_OPT_ATTR]!!

fun Pawn.getInteractingOpcode(): Int = attr[INTERACTING_OPCODE_ATTR]!!

fun Pawn.getInteractingGameObj(): GameObject = attr[INTERACTING_OBJ_ATTR]!!.get()!!

fun Pawn.getInteractingNpc(): Npc = attr[INTERACTING_NPC_ATTR]!!.get()!!

fun Pawn.getInteractingPlayer(): Player = attr[INTERACTING_PLAYER_ATTR]!!.get()!!

fun Pawn.hasPrayerIcon(icon: PrayerIcon): Boolean = prayerIcon == icon.id

fun Pawn.getBonus(slot: BonusSlot): Int = equipmentBonuses[slot.id]

fun Pawn.getAddedFriend(): String = attr[ADDED_FRIEND]!!

fun Pawn.getDeletedFriend(): String = attr[DELETED_FRIEND]!!

fun Pawn.getAddedIgnore(): String = attr[ADDED_IGNORE]!!

fun Pawn.getDeletedIgnore(): String = attr[DELETED_IGNORE]!!

fun Pawn.hit(
    damage: Int,
    type: HitType = if (damage == 0) HitType.BLOCK else HitType.REGULAR_HIT,
    delay: Int = 0,
): Hit {
    val hit =
        Hit
            .Builder()
            .setDamageDelay(delay)
            .addHit(damage = damage, type = type.id)
            .build()

    addHit(hit)
    return hit
}

fun Pawn.hit(
    damage: Int,
    type: Int,
    delay: Int = 0,
): Hit {
    val hit =
        Hit
            .Builder()
            .setDamageDelay(delay)
            .addHit(damage = damage, type = type)
            .build()

    addHit(hit)
    return hit
}

fun Pawn.freeze(
    cycles: Int,
    onFreeze: () -> Unit,
): Boolean {
    if (timers.has(FROZEN_TIMER)) {
        return false
    }
    stopMovement()
    timers[FROZEN_TIMER] = cycles
    onFreeze()
    return true
}

fun Pawn.freeze(cycles: Int) {
    freeze(cycles) {
        if (this is Player) {
            this.message("You have been frozen.")
        }
    }
}

fun Pawn.stun(
    cycles: Int,
    onStun: () -> Unit,
): Boolean {
    if (timers.has(STUN_TIMER)) {
        return false
    }
    stopMovement()
    timers[STUN_TIMER] = cycles
    onStun()
    return true
}

fun Pawn.stun(cycles: Int) {
    stun(cycles) {
        if (this is Player) {
            val blockAnimation = CombatConfigs.getBlockAnimation(this)
            animate(blockAnimation)
            graphic(245, 124)
            playSound(Sfx.STUNNED, delay = 20)
            resetInteractions()
            interruptQueues()
            filterableMessage("You have been stunned!")
        }
    }
}
