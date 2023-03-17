package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.HarvestManager

Patch.values().forEach { patch ->
    val transformIds = world.definitions.get(ObjectDef::class.java, patch.id).transforms?.toSet() ?: return@forEach
    val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

    initializeRaking(patch, transforms)
}

fun initializeRaking(patch: Patch, transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                player.queue {
                    HarvestManager.rakeWeeds(this, player, patch)
                }
            }

            on_item_on_obj(it.id, item = Items.RAKE) {
                player.queue {
                    HarvestManager.rakeWeeds(this, player, patch)
                }
            }
        }
    }
}
