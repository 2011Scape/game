package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.HarvestManager

Patch.values().forEach { patch ->
    val transformIds = world.definitions.get(ObjectDef::class.java, patch.id).transforms?.toSet() ?: return@forEach
    val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }
    transforms.forEach {
        if(if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                if (patch.canRake(player)) {
                    player.queue {
                        HarvestManager.rakeWeeds(this, player, patch)
                    }
                }
            }
        }
    }
}
