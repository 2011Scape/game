package gg.rsmod.plugins.content.skills.fletching.feathering

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

class FeatherAction(private val definitions: DefinitionSet) {

    private val itemNames = FeatheringData.featheringDefinitions.keys.associateWith { definitions.get(ItemDef::class.java, it).name.lowercase() }

    suspend fun feather(task: QueueTask, data: FeatheringData, feather: Int, amount: Int) {
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
            val removeUnfeathered = inventory.remove(item = data.raw, amount = amountToFeather, assureFullRemoval = true)
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

    private suspend fun canFeather(task: QueueTask, feathered: FeatheringData, feather: Int) : Boolean {
        val player = task.player
        val inventory = player.inventory
        if (!inventory.contains(feathered.raw) || inventory.getItemCount(feather) < feathered.amount) {
            return false
        }
        if (player.getSkills().getCurrentLevel(Skills.FLETCHING) < feathered.levelRequirement) {
            task.doubleItemMessageBox("You need a ${Skills.getSkillName(player.world, Skills.FLETCHING)} level of ${feathered.levelRequirement} to fletch ${itemNames[feathered.product]}.", item1 = feathered.raw, amount1 = 100, item2 = Items.FEATHER)
            return false
        }
        return true
    }
}