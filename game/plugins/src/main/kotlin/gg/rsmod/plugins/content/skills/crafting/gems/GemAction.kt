package gg.rsmod.plugins.content.skills.crafting.gems

import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.RANDOM
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

object GemAction {

    suspend fun cut(task: QueueTask, gem: GemData, amount: Int) {

        val player = task.player
        val inventory = player.inventory

        if(!canCut(task, gem)) {
            player.animate(-1)
            return
        }

        val maxCount = min(amount, inventory.getItemCount(gem.uncut))

        repeat(maxCount) {
            player.animate(gem.animation)
            task.wait(2)
            inventory.remove(gem.uncut, assureFullRemoval = true)

            /*
             * Certain gems such as Opal, Jade and Topaz
             * have a certain chance to "fail" and produce
             * a crushed gem and 75% less experience
             */
            val success = gem.lowChance > 0 && interpolate(gem.lowChance, gem.highChance, player.getSkills().getCurrentLevel(Skills.CRAFTING)) < RANDOM.nextInt(255)

            if(success) {
                inventory.add(gem.cut)
                player.addXp(Skills.CRAFTING, gem.experience)
            } else {
                inventory.add(Items.CRUSHED_GEM)
                player.addXp(Skills.CRAFTING, gem.experience / 4)
            }
        }

    }

    private fun canCut(task: QueueTask, gem: GemData) : Boolean {
        val player = task.player
        val inventory = player.inventory

        if(!inventory.contains(Items.CHISEL)) {
            return false
        }

        if(!inventory.contains(gem.uncut)) {
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.CRAFTING) < gem.levelRequirement) {
            player.message("You need a crafting level of ${gem.levelRequirement} to cut this gem.")
            return false
        }

        return true
    }

}