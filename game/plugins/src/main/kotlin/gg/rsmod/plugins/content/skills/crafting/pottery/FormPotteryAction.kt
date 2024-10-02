package gg.rsmod.plugins.content.skills.crafting.pottery

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc
import kotlin.math.min

object FormPotteryAction {
    suspend fun formPottery(
        task: QueueTask,
        data: PotteryItem,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        val urn = PotteryData.findPotteryDataByItem(data.unfired)!!.urn
        val count = inventory.getItemCount(Items.SOFT_CLAY)
        val maxAmount = min(amount, if (urn) count / 2 else count)

        if (!canFormPottery(task, data)) {
            return
        }

        repeat(maxAmount) {
            if (!canFormPottery(task, data)) {
                player.animate(-1)
                return
            }
            player.animate(id = 883)
            if (!inventory
                    .remove(
                        Items.SOFT_CLAY,
                        amount = if (urn) 2 else 1,
                        assureFullRemoval = true,
                    ).hasSucceeded()
            ) {
                return
            }
            inventory.add(item = data.unfired, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.formingExperience)
            var resultName =
                player.world.definitions
                    .get(ItemDef::class.java, data.fired)
                    .name
                    .lowercase()
            if (urn) resultName = resultName.dropLast(6)
            player.filterableMessage("You make the clay into ${Misc.formatWithIndefiniteArticle(resultName)}.")
            task.wait(4)
        }
    }

    private suspend fun canFormPottery(
        task: QueueTask,
        data: PotteryItem,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(
                    ItemDef::class.java,
                    data.unfired,
                ).name
                .lowercase()
                .replace(" (unf)", "")
                .replace(" (unfired)", "")

        if ((PotteryData.findPotteryDataByItem(data.unfired)!!.urn && inventory.getItemCount(Items.SOFT_CLAY) < 2) ||
            !inventory.contains(Items.SOFT_CLAY)
        ) {
            player.message("You don't have enough soft clay to make this.")
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>make ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.unfired,
            )
            return false
        }
        return true
    }
}
