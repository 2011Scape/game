package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.game.model.attr.learnedBarbarianRod
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*

object Fishing {
    private const val waitTime = 2

    suspend fun fish(
        task: QueueTask,
        fishingSpot: Npc,
        tool: FishingTool,
    ) {
        val player = task.player

        if (!canFish(player, tool, fishingSpot)) {
            return
        }

        player.message(introMessage(tool))
        player.playSound(Sfx.FISHING_CAST, delay = 25)
        while (canFish(player, tool, fishingSpot)) {
            player.animate(tool.animation)
            task.wait(waitTime)
            val fishingLevel = player.skills.getCurrentLevel(Skills.FISHING)
            val strengthLevel = player.skills.getCurrentLevel(Skills.STRENGTH)
            val agilityLevel = player.skills.getCurrentLevel(Skills.AGILITY)
            for (fish in tool.relevantFish(fishingLevel, strengthLevel, agilityLevel)) {
                if (fish.roll(fishingLevel)) {
                    handleFishCaught(player, tool, fish)
                    break
                }
            }
            task.wait(waitTime)
        }
        player.animate(-1)
    }

    private fun canFish(
        player: Player,
        tool: FishingTool,
        fishingSpot: Npc,
    ): Boolean {
        // TODO: are these the correct messages?

        if (!fishingSpot.tile.isWithinRadius(player.tile, 1) && fishingSpot.id != Npcs.ROCKTAIL_SHOAL) {
            return false
        }

        if (!fishingSpot.tile.isWithinRadius(player.tile, 2) && fishingSpot.id == Npcs.ROCKTAIL_SHOAL) {
            return false
        }

        if (fishingSpot.id == Npcs.BARBARIAN_FISHING_SPOT &&
            player.skills.getCurrentLevel(Skills.AGILITY) < 15 &&
            player.skills.getCurrentLevel(Skills.STRENGTH) < 15 &&
            player.attr.has(
                learnedBarbarianRod,
            )
        ) {
            player.queue {
                messageBox("You must learn barbarian fishing to catch these fish.")
            }
        }

        if (!hasItem(player, tool.id)) {
            player.queue {
                messageBox("You need a ${tool.identifier.lowercase()} to catch these fish.")
            }
            return false
        }

        if (!hasItems(player, tool.baitId)) {
            player.message("You don't have bait to fish here.")
            return false
        }

        if (player.skills.getCurrentLevel(Skills.FISHING) < tool.level) {
            player.message("You need a fishing level of ${tool.level} to fish here.")
            return false
        }

        if (player.inventory.isFull) {
            player.message("You don't have enough space in your inventory.")
            return false
        }

        return true
    }

    private fun hasItem(
        player: Player,
        itemId: Int?,
    ) = itemId == null || player.inventory.contains(itemId)

    private fun hasItems(
        player: Player,
        itemId: List<Int>?,
    ) = itemId == null || itemId.any { player.inventory.contains(it) }

    private fun handleFishCaught(
        player: Player,
        tool: FishingTool,
        fish: Fish,
    ) {
        player.filterableMessage(caughtMessage(fish))
        player.playSound(id = Sfx.FISH_SWIM)

        val firstBaitIdInInventory = tool.baitId?.firstOrNull { baitId -> player.inventory.contains(baitId) }

        firstBaitIdInInventory?.let { player.inventory.remove(it) }
        player.inventory.add(fish.id)

        player.addXp(Skills.FISHING, fish.xp)
        fish.strengthXp?.let { player.addXp(Skills.STRENGTH, it) }
        fish.agilityXp?.let { player.addXp(Skills.AGILITY, it) }
    }

    private fun introMessage(tool: FishingTool) =
        if (tool == FishingTool.SMALL_FISHING_NET) {
            "You cast out your net..."
        } else {
            "You attempt to catch a fish."
        }

    private fun caughtMessage(fish: Fish) =
        if (fish == Fish.SHRIMP || fish == Fish.ANCHOVIES) {
            "You catch some ${fish.name.lowercase().replace('_', ' ')}."
        } else {
            "You catch a ${fish.name.lowercase().replace('_', ' ')}."
        }
}
