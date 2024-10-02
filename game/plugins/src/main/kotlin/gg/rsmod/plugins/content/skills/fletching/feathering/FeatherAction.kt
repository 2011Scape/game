package gg.rsmod.plugins.content.skills.fletching.feathering

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.attr.BROAD_FLETCHING
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

class FeatherAction(
    private val definitions: DefinitionSet,
) {
    private val itemNames =
        FeatheringData.featheringDefinitions.keys.associateWith {
            definitions.get(ItemDef::class.java, it).name.lowercase()
        }

    suspend fun feather(
        task: QueueTask,
        data: FeatheringData,
        feather: Int,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory
        var completed = 0

        while (completed < amount) {
            if (data.product in listOf(Items.HEADLESS_ARROW, Items.FLIGHTED_OGRE_ARROW)) {
                task.wait(2)
            }

            if (!canFeather(task, data, feather)) {
                player.animate(-1)
                break
            }

            val unfeatheredCount = inventory.getItemCount(data.raw)
            val featherCount = inventory.getItemCount(feather)
            val amountToFeather = min(min(unfeatheredCount, (featherCount / data.feathersRequired)), data.amount)
            val feathersUsed = data.feathersRequired * amountToFeather

            val unfeatheredIndex = inventory.getItemIndex(data.raw, true)
            val removeUnfeathered =
                inventory.remove(
                    item = data.raw,
                    amount = amountToFeather,
                    assureFullRemoval = true,
                )
            if (removeUnfeathered.hasFailed()) {
                break
            }

            val removeFeather = inventory.remove(item = feather, amount = feathersUsed, assureFullRemoval = true)
            if (removeFeather.hasFailed()) {
                inventory.add(item = data.raw, amount = amountToFeather, beginSlot = unfeatheredIndex)
                break
            }

            inventory.add(data.product, amount = amountToFeather)
            player.addXp(Skills.FLETCHING, data.experience * amountToFeather)
            completed++
        }
    }

    private suspend fun canFeather(
        task: QueueTask,
        feathered: FeatheringData,
        feather: Int,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory
        if (!inventory.contains(feathered.raw) || inventory.getItemCount(feather) < feathered.amount) {
            return false
        }
        if (feathered == FeatheringData.BROAD_BOLTS && !player.attr.has(BROAD_FLETCHING)) {
            player.message("You need to unlock the ability to create broad bolts.")
            return false
        }
        if (!hasRoomForProduct(inventory, feathered, feather)) {
            task.player.message("You don't have enough inventory space to do that.")
            return false
        }
        if (player.skills.getCurrentLevel(Skills.FLETCHING) < feathered.levelRequirement) {
            task.doubleItemMessageBox(
                "You need a ${Skills.getSkillName(
                    player.world,
                    Skills.FLETCHING,
                )} level of ${feathered.levelRequirement} to fletch ${itemNames[feathered.product]}.",
                item1 = feathered.raw,
                amount1 = 100,
                item2 = Items.FEATHER,
            )
            return false
        }
        return true
    }

    private fun hasRoomForProduct(
        inventory: ItemContainer,
        feathered: FeatheringData,
        feather: Int,
    ): Boolean {
        if (inventory.contains(feathered.product)) {
            return true
        }

        if (inventory.hasSpace) {
            return true
        }

        if (inventory.getItemCount(feathered.raw) <= feathered.amount) {
            return true
        }

        if (inventory.getItemCount(feather) <= feathered.amount * feathered.feathersRequired) {
            return true
        }

        return false
    }
}
