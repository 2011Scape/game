package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.fs.def.VarbitDef
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.skills.farming.data.Patch

object WeedsHandler {
    fun grow(player: Player, patch: Patch) {
        val varbit = player.world.definitions.get(ObjectDef::class.java, patch.id).varbit
        val current = player.getVarbit(varbit)
        if (current in 1..3) {
            player.setVarbit(varbit, current - 1)
            player.message("Growing")
        }
    }

    suspend fun rake(task: QueueTask, player: Player, patch: Patch) {
        val varbit = player.world.definitions.get(ObjectDef::class.java, patch.id).varbit
        while (player.getVarbit(varbit) in 0..2) {
            player.animate(2273)
            task.wait(4)
            player.setVarbit(varbit, player.getVarbit(varbit) + 1)
            player.addXp(Skills.FARMING, 4.0)
            if (player.inventory.add(Items.WEEDS).hasFailed()) {
                player.world.spawn(GroundItem(Item(Items.WEEDS), player.tile, player))
            }
        }
        player.animate(-1)
    }

    fun canRake(player: Player, patch: Patch): Boolean {
        val varbit = player.world.definitions.get(ObjectDef::class.java, patch.id).varbit
        return player.getVarbit(varbit) in 0..2
    }
}
