package gg.rsmod.plugins.content.skills.crafting.pottery

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

object FirePotteryAction {
    suspend fun firePottery(
        task: QueueTask,
        data: PotteryItem,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        val maxAmount = min(amount, inventory.getItemCount(data.unfired))
        var startName =
            player.world.definitions
                .get(
                    ItemDef::class.java,
                    data.unfired,
                ).name
                .lowercase()
                .replace(" (unfired)", "")
        var endName =
            player.world.definitions
                .get(ItemDef::class.java, data.fired)
                .name
                .lowercase()
        if (PotteryData.findPotteryDataByItem(data.unfired)!!.urn) {
            startName = startName.dropLast(6)
            endName = endName.dropLast(5)
        }

        repeat(maxAmount) {
            if (!canFire(task, data)) {
                player.animate(-1)
                return
            }
            if (!inventory.remove(item = data.unfired, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            player.lock()
            player.animate(id = 899)
            player.filterableMessage("You put ${Misc.formatWithIndefiniteArticle(startName)} in the oven.")
            task.wait(3)
            player.filterableMessage("You remove the $endName from the oven.")
            inventory.add(item = data.fired, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.firingExperience)
            player.unlock()
            task.wait(2)
        }
    }

    private suspend fun canFire(
        task: QueueTask,
        data: PotteryItem,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.fired)
                .name
                .lowercase()
        if (!inventory.contains(data.unfired)) {
            return false
        }
        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>make ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.fired,
            )
            return false
        }
        return true
    }
}
