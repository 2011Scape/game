package gg.rsmod.plugins.content.skills.fletching.whittling

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.doubleItemMessageBox
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

class WhittleAction(val definitions: DefinitionSet) {

    private val whittleNames = LogData.logDefinitions.flatMap { it.value.values }.distinct().associate { it.product to definitions.get(ItemDef::class.java, it.product).name.toLowerCase() }

    suspend fun cut(task: QueueTask, raw: Int, whittleItem: WhittleItem, amount: Int) {

        val player = task.player
        val inventory = player.inventory

        if(!canCut(task, raw, whittleItem)) {
            player.animate(-1)
            return
        }

        val maxCount = min(amount, inventory.getItemCount(raw))

        repeat(maxCount) {
            player.animate(1248)
            task.wait(2)
            inventory.remove(raw, assureFullRemoval = true)
            inventory.add(whittleItem.product, whittleItem.amount)
            player.addXp(Skills.FLETCHING, whittleItem.experience * whittleItem.amount)
        }

    }

    private suspend fun canCut(task: QueueTask, raw: Int, data: WhittleItem) : Boolean {
        val player = task.player
        val inventory = player.inventory

        if(!inventory.contains(Items.KNIFE)) {
            return false
        }

        if(!inventory.contains(raw)) {
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.FLETCHING) < data.levelRequirement) {
            task.doubleItemMessageBox("You need a Fletching level of at least ${data.levelRequirement} to make a ${whittleNames[data.product]}.", item1 = raw, item2 = data.product)
            return false
        }

        return true
    }

}