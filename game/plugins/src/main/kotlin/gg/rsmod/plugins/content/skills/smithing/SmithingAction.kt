package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.messageBox
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.skills.smithing.data.BarProducts
import java.lang.Integer.min

class SmithingAction(val definitions: DefinitionSet) {

    suspend fun smith(task: QueueTask, product: BarProducts, amount: Int) {
        val player = task.player
        val maxCount = min(amount, player.inventory.getItemCount(product.barType.item) * product.smithingType.barRequirement)

        if(!canSmith(task, product, amount)) {
            player.animate(-1)
            return
        }

        repeat(maxCount) {
            if(!canSmith(task, product, amount)) {
                player.animate(-1)
                return@repeat
            }
            player.animate(898)
            task.wait(2)
            player.inventory.remove(product.barType.item, product.smithingType.barRequirement)
            player.inventory.add(product.result, product.smithingType.producedAmount)
            player.addXp(Skills.SMITHING, product.barType.experience * product.smithingType.barRequirement)
            task.wait(3)
        }
    }

    private suspend fun canSmith(task: QueueTask, product: BarProducts, amount: Int) : Boolean {
        val player = task.player

        if(!player.inventory.contains(Items.HAMMER)) {
            task.messageBox("You need a hammer to work the metal with.")
            return false
        }

        if(player.inventory.getItemCount(product.barType.item) < product.smithingType.barRequirement * amount) {
            task.messageBox("You don't have enough ${definitions.get(ItemDef::class.java, product.barType.item).name.lowercase()}s to make a ${product.smithingType.name.replace("TYPE_", "").replace("_", " ").lowercase()}.")
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.SMITHING) < product.level) {
            task.messageBox("You need a Smithing level of ${product.level} to make a ${definitions.get(ItemDef::class.java, product.result).name}.")
            return false
        }
        return true
    }


}