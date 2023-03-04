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
            player.queue {
                Pickpocketing.pickpocket(this, npc, target)
            }
        }
    }
}

StallTarget.values().forEach { target ->
    DropTableFactory.register(target.drops, *target.fullAndEmptyObjectIds.keys.toIntArray(), type = DropTableType.STALL)
    target.fullAndEmptyObjectIds.keys.forEach { targetId ->
        val option = if (if_obj_has_option(targetId, "steal-from")) {
            "steal-from"
        } else {
            "steal from"
        }
        on_obj_option(targetId, option) {
            val obj = player.getInteractingGameObj()
            player.queue {
                Stalls.stealFromStall(this, obj, target)
            }
        }
    }
}

StallTarget.values().flatMap { it.guards }.toSet().forEach { npc ->
    on_npc_spawn(npc) {
        Stalls.stallGuards.add(this.npc)
    }
}
