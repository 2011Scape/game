package gg.rsmod.plugins.content.skills.crafting.weaving

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

object WeavingAction {
    suspend fun weave(
        task: QueueTask,
        data: WeavingData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory

        val maxAmount = min(amount, inventory.getItemCount(data.resourceItem.id) / data.resourceItem.amount)

        if (!canWeave(task, data)) {
            return
        }

        repeat(maxAmount) {
            if (!canWeave(task, data)) {
                player.animate(-1)
                return
            }
            player.animate(id = 2270)
            if (!inventory.remove(data.resourceItem, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            inventory.add(data.resultItem, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.experience)
            player.filterableMessage(
                "You weave the items into ${Misc.formatWithIndefiniteArticle(
                    player.world.definitions
                        .get(ItemDef::class.java, data.resultItem)
                        .name,
                )}.",
            )
            task.wait(3)
        }
    }

    suspend fun weaveMilestoneCape(
        task: QueueTask,
        item: Int,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        val name =
            player.world.definitions
                .get(ItemDef::class.java, item)
                .name

        val maxAmount = min(amount, inventory.getItemCount(Items.BALL_OF_WOOL) / getBallsAmount(name))

        if (!canWeaveCape(task, item)) {
            return
        }

        repeat(maxAmount) {
            if (!canWeaveCape(task, item)) {
                player.animate(-1)
                return
            }
            player.animate(id = 2270)
            if (!inventory
                    .remove(
                        Items.BALL_OF_WOOL,
                        amount = getBallsAmount(name),
                        assureFullRemoval = true,
                    ).hasSucceeded()
            ) {
                return
            }
            inventory.add(item = item, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, getMinimumLevelForCape(name).toDouble())
            player.filterableMessage("You weave the balls of wool into this item.")
            task.wait(3)
        }
    }

    private suspend fun canWeave(
        task: QueueTask,
        data: WeavingData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resourceName =
            player.world.definitions
                .get(ItemDef::class.java, data.resourceItem.id)
                .name
                .lowercase()
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.resultItem)
                .name
                .lowercase()

        if (inventory.getItemCount(data.resourceItem.id) < data.resourceItem.amount) {
            player.message(
                "You don't have enough ${data.rawName.drop(2)} to make ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
            )
            return false
        }
        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.resultItem,
            )
            return false
        }
        return true
    }

    private suspend fun canWeaveCape(
        task: QueueTask,
        item: Int,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, item)
                .name
        if (inventory.getItemCount(Items.BALL_OF_WOOL) < getBallsAmount(resultName)) {
            player.message(
                "You don't have enough balls of wool to make ${Misc.formatWithIndefiniteArticle(resultName)}.",
            )
            return false
        }
        if (!player.skills.areSkillsAtLeast(getMinimumLevelForCape(resultName))) {
            task.itemMessageBox(
                "You need all skills to be of at least level ${getMinimumLevelForCape(
                    resultName,
                )} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = item,
            )
            return false
        }
        return true
    }

    fun getBallsAmount(name: String): Int {
        return name
            .takeLast(4)
            .replace("(", "")
            .replace(")", "")
            .toInt() / 10
    }

    private fun getMinimumLevelForCape(name: String): Int {
        return name
            .takeLast(4)
            .replace("(", "")
            .replace(")", "")
            .toInt()
    }
}
