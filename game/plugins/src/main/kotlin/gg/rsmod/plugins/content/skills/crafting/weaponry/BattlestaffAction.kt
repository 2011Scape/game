package gg.rsmod.plugins.content.skills.crafting.weaponry

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

object BattlestaffAction {
    suspend fun attach(
        task: QueueTask,
        staff: BattlestaffData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory

        val orbCount = inventory.getItemCount(staff.orbId)
        val staffCount = inventory.getItemCount(Items.BATTLESTAFF)
        val orbName =
            player.world.definitions
                .get(ItemDef::class.java, staff.orbId)
                .name
                .lowercase()
        val staffName =
            player.world.definitions
                .get(ItemDef::class.java, staff.resultItem)
                .name
                .lowercase()

        val maxCount = min(amount, if (orbCount >= staffCount) staffCount else orbCount)

        repeat(maxCount) {
            if (!canAttach(task, staff)) {
                return
            }

            if (!inventory.remove(item = Items.BATTLESTAFF, assureFullRemoval = true).hasSucceeded() ||
                !inventory.remove(item = staff.orbId, assureFullRemoval = true).hasSucceeded()
            ) {
                return
            }
            inventory.add(item = staff.resultItem, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, staff.experience)
            player.filterableMessage(
                "You attach ${Misc.formatWithIndefiniteArticle(
                    orbName,
                )} to the battlestaff and make ${Misc.formatWithIndefiniteArticle(
                    staffName,
                )}.",
            )
            task.wait(3)
        }
    }

    private suspend fun canAttach(
        task: QueueTask,
        staff: BattlestaffData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory

        val resultItem =
            player.world.definitions
                .get(ItemDef::class.java, staff.resultItem)
                .name
                .lowercase()

        if (!inventory.contains(Items.BATTLESTAFF)) {
            return false
        }

        if (!inventory.contains(staff.orbId)) {
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < staff.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${staff.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultItem,
                )}.",
                item = staff.resultItem,
            )
            return false
        }
        return true
    }
}
