package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to protecting a patch through payment
 */
class ProtectHandler(
    private val state: PatchState,
    private val patch: Patch,
    private val player: Player,
) {
    fun protect(): Boolean {
        if (canProtect()) {
            val amount =
                state.seed!!
                    .growth.protectionPayment!!
                    .amount
            val unnoted =
                state.seed!!
                    .growth.protectionPayment!!
                    .id
            val noted =
                player.world.definitions
                    .get(ItemDef::class.java, unnoted)
                    .noteLinkId
            return if (hasEnoughToPay(noted, unnoted, amount)) {
                val unnotedToRemove = player.inventory.getItemCount(unnoted).coerceAtMost(amount)
                val notedToRemove = amount - unnotedToRemove
                player.inventory.remove(unnoted, amount = unnotedToRemove)
                player.inventory.remove(noted, amount = notedToRemove)
                state.protect()
                true
            } else {
                false
            }
        } else {
            return false
        }
    }

    fun canProtect() =
        state.seed != null &&
            !state.isPlantFullyGrown &&
            !state.isDead &&
            !state.isDiseased &&
            !state.isProtectedThroughPayment &&
            state.seed!!.growth.protectionPayment != null

    private fun hasEnoughToPay(
        noted: Int,
        unnoted: Int,
        required: Int,
    ) = player.inventory.getItemCount(noted) + player.inventory.getItemCount(unnoted) >= required

    fun hasEnoughToPay(): Boolean {
        if (!canProtect()) {
            return false
        }
        return hasEnoughToPay(
            player.world.definitions
                .get(
                    ItemDef::class.java,
                    state.seed!!
                        .growth.protectionPayment!!
                        .id,
                ).noteLinkId,
            state.seed!!
                .growth.protectionPayment!!
                .id,
            state.seed!!
                .growth.protectionPayment!!
                .amount,
        )
    }
}
