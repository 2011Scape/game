package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*

object Fishing {

    private const val waitTime = 2

    suspend fun fish(task: QueueTask, fishingSpot: Npc, tool: FishingTool) {
        val player = task.player

        if (!canFish(player, tool, fishingSpot)) {
            return
        }

        while (true) {
            player.animate(tool.animation)
            task.wait(waitTime)

            if (!canFish(player, tool, fishingSpot)) {
                player.animate(-1)
                break
            }

            val level = player.getSkills().getCurrentLevel(Skills.FISHING)
            for (fish in tool.relevantFish(level)) {
                if (fish.roll(level)) {
                    handleFishCaught(player, tool, fish)
                    break
                }
            }
            task.wait(waitTime)
        }
    }

    private fun canFish(player: Player, tool: FishingTool, fishingSpot: Npc): Boolean {
        // TODO: are these the correct messages?

        if (!fishingSpot.tile.isWithinRadius(player.tile, 1)) {
            return false
        }

        if (!hasItem(player, tool.id)) {
            player.message("You need a ${tool.identifier} to fish here.")
            return false
        }

        if (!hasItem(player, tool.baitId)) {
            player.message("You don't have bait to fish here.")
            return false
        }

        if (player.getSkills().getCurrentLevel(Skills.FISHING) < tool.level) {
            player.message("You need a fishing level of ${tool.level} to fish here.")
            return false
        }

        if (player.inventory.isFull) {
            player.message("You don't have enough inventory space.")
            return false
        }

        return true
    }

    private fun hasItem(player: Player, itemId: Int?) = itemId == null || player.inventory.contains(itemId)

    private fun handleFishCaught(player: Player, tool: FishingTool, fish: Fish) {
        player.filterableMessage("You catch some fish.")
        // player.playSound() TODO: figure out the correct sound for caught fish

        tool.baitId?.let { player.inventory.remove(it) }
        player.inventory.add(fish.id)

        player.addXp(Skills.FISHING, fish.xp)
    }
}