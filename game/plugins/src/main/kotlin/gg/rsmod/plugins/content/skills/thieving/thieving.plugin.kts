package gg.rsmod.plugins.content.skills.thieving

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.skills.thieving.pickpocketing.PickpocketTarget
import gg.rsmod.plugins.content.skills.thieving.pickpocketing.Pickpocketing
import gg.rsmod.plugins.content.skills.thieving.stalls.StallTarget
import gg.rsmod.plugins.content.skills.thieving.stalls.Stalls

PickpocketTarget.values().forEach { target ->
    DropTableFactory.register(target.drops, *target.objectIds.toIntArray(), type = DropTableType.PICKPOCKET)
    target.objectIds.forEach { targetId ->
        on_npc_option(targetId, "pickpocket") {
            val npc = player.getInteractingNpc()
            player.interruptQueues()
            player.resetInteractions()
            player.queue {
                Pickpocketing.pickpocket(this, npc, target)
            }
        }
    }
}

StallTarget.values().forEach { target ->
    DropTableFactory.register(target.drops, *target.fullAndEmptyObjectIds.keys.toIntArray(), type = DropTableType.STALL)
    target.fullAndEmptyObjectIds.keys.forEach { targetId ->
        on_obj_option(targetId, "steal-from") {
            val obj = player.getInteractingGameObj()
            player.interruptQueues()
            player.resetInteractions()
            player.queue {
                Stalls.stealFromStall(this, obj, target)
            }
        }
    }
}
