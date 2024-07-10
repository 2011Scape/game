package gg.rsmod.plugins.content.skills.fletching.stringing

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

class BowStringAction(
    val definitions: DefinitionSet,
) {
    suspend fun string(
        task: QueueTask,
        bow_u: Int,
        bowItem: BowItem,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        val productName =
            player.world.definitions
                .get(ItemDef::class.java, bowItem.product)
                .name
                .trim()
                .lowercase()
        val maxCount = min(amount, inventory.getItemCount(bow_u))

        repeat(maxCount) {
            if (!canString(task, bowItem)) {
                player.animate(-1)
                return
            }
            if (productName == "shortbow")
                {
                    player.animate(6678)
                }
            if (productName == "oak shortbow")
                {
                    player.animate(6679)
                }
            if (productName == "willow shortbow")
                {
                    player.animate(6680)
                }
            if (productName == "maple shortbow")
                {
                    player.animate(6681)
                }
            if (productName == "yew shortbow")
                {
                    player.animate(6682)
                }
            if (productName == "magic shortbow")
                {
                    player.animate(6683)
                }
            if (productName == "longbow")
                {
                    player.animate(6684)
                }
            if (productName == "oak longbow")
                {
                    player.animate(6685)
                }
            if (productName == "willow longbow")
                {
                    player.animate(6686)
                }
            if (productName == "maple longbow")
                {
                    player.animate(6687)
                }
            if (productName == "yew longbow")
                {
                    player.animate(6688)
                }
            if (productName == "magic longbow")
                {
                    player.animate(6689)
                }
            task.wait(2)
            if (!inventory.remove(bow_u, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            if (!inventory.remove(Items.BOW_STRING, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            inventory.add(bowItem.product, bowItem.amount)
            player.filterableMessage("You add a string to the $productName.")
            player.addXp(Skills.FLETCHING, bowItem.experience)
            task.wait(1)
        }
    }

    private suspend fun canString(
        task: QueueTask,
        data: BowItem,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        if (!inventory.contains(Items.BOW_STRING)) {
            return false
        }
        if (player.skills.getCurrentLevel(Skills.FLETCHING) < data.levelRequirement) {
            task.itemMessageBox(
                "You need a Fletching level of at least ${data.levelRequirement} to string a ${data.itemName}.",
                item = data.product,
            )
            return false
        }
        return true
    }

    private suspend fun canStringCbow(
        task: QueueTask,
        data: BowItem,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        if (!inventory.contains(Items.CROSSBOW_STRING)) {
            return false
        }
        if (player.skills.getCurrentLevel(Skills.FLETCHING) < data.levelRequirement) {
            task.itemMessageBox(
                "You need a Fletching level of at least ${data.levelRequirement} to string a ${data.itemName}.",
                item = data.product,
            )
            return false
        }
        return true
    }
}
