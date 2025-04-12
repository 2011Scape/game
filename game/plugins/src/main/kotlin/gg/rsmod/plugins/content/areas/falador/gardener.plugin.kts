package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PiratesTreasure

/**
 * Whether gardener has been spawned
 */
val GARDENER_SPAWNED_ATTR = AttributeKey<Boolean>(persistenceKey = "gardener_spawned")

on_item_option(Items.SPADE, "Dig") {
    if ((player.tile.x == 2999 || player.tile.x == 3000) && player.tile.z == 3383) {
        if (player.getCurrentStage(PiratesTreasure) != 3) {
            player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
                player.animate(Anims.DIG_SPADE)
                wait(2)
            }
            player.animate(Anims.DIG_SPADE)
            return@on_item_option
        }
        val spawned = player.attr.has(GARDENER_SPAWNED_ATTR) && player.attr[GARDENER_SPAWNED_ATTR]!!
        if (spawned) {
            if (world.npcs.any { npc -> npc.id == Npcs.GARDENER_3914 && npc.owner == player && npc.isAlive()}) {
                player.message("I can't dig up anything with him attacking me!")
            }
            else {
                player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
                    player.animate(Anims.DIG_SPADE)
                    wait(3)
                    PiratesTreasure.finishQuest(player)
                }
            }
        }
        else {
            player.attr[GARDENER_SPAWNED_ATTR] = true
            val gardener = Npc(
                owner = player,
                id = Npcs.GARDENER_3914,
                tile = Tile(2996, 3383),
                world = world
            )
            player.lockingQueue {
                player.animate(Anims.DIG_SPADE)
                world.spawn(gardener)
                gardener.respawns = false
                wait(2)
                gardener.forceChat("First moles, now this! Take this, vandal!")
                wait(1)
                gardener.attack(player)
            }
        }
    }
}
