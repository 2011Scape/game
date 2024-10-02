package gg.rsmod.plugins.content.skills.crafting.glassblowing

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

object GlassblowingAction {
    suspend fun craft(
        task: QueueTask,
        data: GlassData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory

        val maxCount = min(amount, inventory.getItemCount(Items.MOLTEN_GLASS))

        repeat(maxCount) {
            if (!canCraft(task, data)) {
                player.animate(-1)
                return
            }
            player.animate(884)
            if (!inventory.remove(item = Items.MOLTEN_GLASS, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            inventory.add(data.id, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.experience)
            player.filterableMessage(
                "You make ${Misc.formatWithIndefiniteArticle(
                    player.world.definitions
                        .get(ItemDef::class.java, data.id)
                        .name
                        .lowercase(),
                )}.",
            )
            task.wait(3)
        }
    }

    private suspend fun canCraft(
        task: QueueTask,
        data: GlassData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.id)
                .name
                .lowercase()

        if (!inventory.contains(Items.GLASSBLOWING_PIPE)) {
            player.message("You need a glassblowing pipe to make ${Misc.formatWithIndefiniteArticle(resultName)}.")
            return false
        }

        if (!inventory.contains(Items.MOLTEN_GLASS)) {
            player.message("You need some molten glass to make ${Misc.formatWithIndefiniteArticle(resultName)}.")
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequired) {
            task.itemMessageBox(
                "You need a Crafting level of ${data.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.id,
            )
            return false
        }
        return true
    }
}
