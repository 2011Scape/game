package gg.rsmod.plugins.content.items.combine

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.itemMessageBox
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc

object CombinationAction {
    suspend fun combine(
        task: QueueTask,
        data: CombinationData,
    ) {
        val player = task.player
        val inventory = player.inventory

        if (!canCombine(task, data)) {
            return
        }

        data.items.forEach {
            inventory.remove(item = it, assureFullRemoval = true)
        }

        inventory.add(data.resultItem, assureFullInsertion = true)
        player.addXp(data.skill, data.experience)

        if (data.tool != CombinationTool.NONE) {
            player.filterableMessage(
                "You use your ${player.world.definitions.get(
                    ItemDef::class.java,
                    data.tool.item,
                ).name.lowercase()} to make ${Misc.formatWithIndefiniteArticle(
                    player.world.definitions
                        .get(ItemDef::class.java, data.resultItem)
                        .name
                        .lowercase(),
                )}.",
            )
        } else {
            if (data.message == null) {
                player.filterableMessage(
                    "You combine the items and make ${Misc.formatWithIndefiniteArticle(
                        player.world.definitions
                            .get(ItemDef::class.java, data.resultItem)
                            .name
                            .lowercase(),
                    )}.",
                )
            } else {
                player.filterableMessage(data.message)
            }
        }
    }

    private suspend fun canCombine(
        task: QueueTask,
        data: CombinationData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.resultItem)
                .name

        if (data.tool != CombinationTool.NONE && !inventory.contains(data.tool.item)) {
            player.message(
                "You need ${Misc.formatWithIndefiniteArticle(
                    player.world.definitions
                        .get(ItemDef::class.java, data.tool.item)
                        .name
                        .lowercase(),
                )} to combine this.",
            )
            return false
        }

        if (!inventory.hasItems(data.items)) {
            return false
        }

        if (player.skills.getCurrentLevel(data.skill) < data.levelRequired) {
            task.itemMessageBox(
                "You need a ${Skills.getSkillName(
                    player.world,
                    data.skill,
                )} level of ${data.levelRequired} to<br>craft ${Misc.formatWithIndefiniteArticle(
                    resultName,
                )}.",
                item = data.resultItem,
            )
            return false
        }
        return true
    }
}
