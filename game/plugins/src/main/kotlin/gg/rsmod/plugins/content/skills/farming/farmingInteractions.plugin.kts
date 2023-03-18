package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.patchHandler.WeedsHandler

Patch.values().forEach { patch ->
    val transformIds = world.definitions.get(ObjectDef::class.java, patch.id).transforms?.toSet() ?: return@forEach
    val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

    initializeRaking(patch, transforms)
}

fun initializeRaking(patch: Patch, transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                if (checkAvailability(player)) {
                    player.queue {
                        WeedsHandler(patch, player).rake(this)
                    }
                }
            }

            on_item_on_obj(it.id, item = Items.RAKE) {
                if (checkAvailability(player)) {
                    player.queue {
                        WeedsHandler(patch, player).rake(this)
                    }
                }
            }
        }
    }
}

fun checkAvailability(player: Player): Boolean {
    return if (world.privileges.isEligible(player.privilege, Privilege.ADMIN_POWER)) {
        true
    } else {
        player.message("Coming soon...")
        false
    }
}
