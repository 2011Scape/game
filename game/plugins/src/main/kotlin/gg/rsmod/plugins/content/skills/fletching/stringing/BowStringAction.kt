package gg.rsmod.plugins.content.skills.fletching.stringing

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Anims
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
                player.animate(Anims.RESET)
                return
            }
            if (productName == "shortbow")
                {
                    player.animate(Anims.FLETCH_SHORTBOW)
                }
            if (productName == "oak shortbow")
                {
                    player.animate(Anims.FLETCH_OAK_SHORTBOW)
                }
            if (productName == "willow shortbow")
                {
                    player.animate(Anims.FLETCH_WILLOW_SHORTBOW)
                }
            if (productName == "maple shortbow")
                {
                    player.animate(Anims.FLETCH_MAPLE_SHORTBOW)
                }
            if (productName == "yew shortbow")
                {
                    player.animate(Anims.FLETCH_YEW_SHORTBOW)
                }
            if (productName == "magic shortbow")
                {
                    player.animate(Anims.FLETCH_MAGIC_SHORTBOW)
                }
            if (productName == "longbow")
                {
                    player.animate(Anims.FLETCH_LONGBOW)
                }
            if (productName == "oak longbow")
                {
                    player.animate(Anims.FLETCH_OAK_LONGBOW)
                }
            if (productName == "willow longbow")
                {
                    player.animate(Anims.FLETCH_WILLOW_LONGBOW)
                }
            if (productName == "maple longbow")
                {
                    player.animate(Anims.FLETCH_MAPLE_LONGBOW)
                }
            if (productName == "yew longbow")
                {
                    player.animate(Anims.FLETCH_YEW_LONGBOW)
                }
            if (productName == "magic longbow")
                {
                    player.animate(Anims.FLETCH_MAGIC_LONGBOW)
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
