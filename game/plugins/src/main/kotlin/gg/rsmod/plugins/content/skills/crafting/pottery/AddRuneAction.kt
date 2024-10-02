package gg.rsmod.plugins.content.skills.crafting.pottery

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

object AddRuneAction {
    suspend fun attachRune(
        task: QueueTask,
        item: Int,
        amount: Int = 1,
    ) {
        val player = task.player
        val inventory = player.inventory
        val urn = PotteryData.findPotteryItemByItem(item - 3)!!
        val rune = PotteryData.findPotteryDataByItem(item - 3)!!.runeRequired!!
        val maxAmount =
            min(
                amount,
                if (inventory.getItemCount(urn.fired) <
                    inventory.getItemCount(rune)
                ) {
                    inventory.getItemCount(urn.fired)
                } else {
                    inventory.getItemCount(rune)
                },
            )

        if (!canAttach(task, urn)) {
            return
        }
        player.animate(id = urn.addRuneAnimation)
        repeat(maxAmount) {
            if (!inventory.remove(urn.fired, assureFullRemoval = true).hasSucceeded() ||
                !inventory.remove(rune, assureFullRemoval = true).hasSucceeded()
            ) {
                return
            }
            player.addXp(Skills.CRAFTING, xp = 1.0)
            inventory.add(urn.fired + 2, assureFullInsertion = true)
        }
        player.filterableMessage("You carefully attach the runes to your urns.")
    }

    private suspend fun canAttach(
        task: QueueTask,
        data: PotteryItem,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val rune = PotteryData.findPotteryDataByItem(data.unfired)!!.runeRequired!!
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.fired + 2)
                .name
                .replace(" (r)", "")

        if (!inventory.contains(rune) || !inventory.contains(data.fired)) {
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>attach the rune to ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.fired + 2,
            )
            return false
        }

        return true
    }
}
