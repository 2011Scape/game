package gg.rsmod.plugins.content.skills.thieving

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

PickpocketTarget.values().forEach { target ->
    DropTableFactory.register(target.drops, *target.objectIds.toIntArray(), type = DropTableType.PICKPOCKET)
    target.objectIds.forEach { targetId ->
        on_npc_option(targetId, "pickpocket") {
            val npc = player.getInteractingNpc()
            player.interruptQueues()
            player.resetInteractions()
            player.queue {
                Thieving.pickpocket(this, npc, target)
            }
        }
    }
}
