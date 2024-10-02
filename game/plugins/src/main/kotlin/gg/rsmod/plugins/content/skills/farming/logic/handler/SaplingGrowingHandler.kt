package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.data.Sapling

/**
 * Logic related to growing saplings
 */
class SaplingGrowingHandler(
    private val player: Player,
) {
    fun growSaplings() {
        for (type in Sapling.values()) {
            while (player.inventory.contains(type.wateredSeedlingId)) {
                val slot = player.inventory.getItemIndex(type.wateredSeedlingId, false)
                if (!player.inventory.remove(type.wateredSeedlingId, beginSlot = slot).hasSucceeded()) {
                    return
                }

                player.inventory.add(type.saplingId, beginSlot = slot)
            }

            if (player.bank.contains(type.wateredSeedlingId)) {
                val amount = player.bank.getItemCount(type.wateredSeedlingId)
                if (!player.bank.remove(type.wateredSeedlingId, amount = amount).hasSucceeded()) {
                    return
                }

                player.bank.add(type.saplingId, amount = amount)
            }
        }
    }
}
