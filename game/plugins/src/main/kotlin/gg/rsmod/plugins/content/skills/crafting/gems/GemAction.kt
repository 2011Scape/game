package gg.rsmod.plugins.content.skills.crafting.gems

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
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
            if (!inventory.remove(gem.uncut, assureFullRemoval = true).hasSucceeded()) {
                return@repeat
            }

            /*
             * Certain gems such as Opal, Jade and Topaz
             * have a certain chance to "fail" and produce
             * a crushed gem and 75% less experience
             */
            val success = gem.lowChance > 0 && interpolate(gem.lowChance, gem.highChance, player.getSkills().getCurrentLevel(Skills.CRAFTING)) > RANDOM.nextInt(255)

            if(success) {
                inventory.add(gem.cut)
                player.addXp(Skills.CRAFTING, gem.experience)
            } else {
                inventory.add(Items.CRUSHED_GEM)
                player.addXp(Skills.CRAFTING, gem.experience / 4)
            }
        }

    }

    private suspend fun canCut(task: QueueTask, gem: GemData) : Boolean {
        val player = task.player
        val inventory = player.inventory

        if(!inventory.contains(Items.CHISEL)) {
            return false
        }

        if(!inventory.contains(gem.uncut)) {
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.CRAFTING) < gem.levelRequirement) {
            task.itemMessageBox("You need a crafting level of ${gem.levelRequirement} to cut an ${player.world.definitions.get(ItemDef::class.java, gem.uncut).name.toLowerCase()}.", item = gem.uncut)
            return false
        }

        return true
    }

}