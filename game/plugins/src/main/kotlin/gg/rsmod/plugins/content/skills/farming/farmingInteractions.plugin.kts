package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.constants.Constants.farmingManagerAttr
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.handler.PlantingHandler

Patch.values().forEach { patch ->
    val transformIds = world.definitions.get(ObjectDef::class.java, patch.id).transforms?.toSet() ?: return@forEach
    val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

    initializeRaking(patch, transforms)
    initializePlanting(patch, transforms)
    initializeComposting(patch, transforms)
}

fun initializeRaking(patch: Patch, transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                if (checkAvailability(player)) {
                    player.attr[farmingManagerAttr]!!.rake(patch)
                }
            }

            on_item_on_obj(it.id, item = Items.RAKE) {
                if (checkAvailability(player)) {
                    player.attr[farmingManagerAttr]!!.rake(patch)
                }
            }
        }
    }
}

fun initializePlanting(patch: Patch, transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        Seed.values().forEach { seed ->
            on_item_on_obj(transform.id, item = seed.seedId) {
                if (checkAvailability(player)) {
                    player.attr[farmingManagerAttr]!!.plant(patch, seed)
                }
            }
        }
    }
}

fun initializeComposting(patch: Patch, transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        CompostState.values().filter { it.itemId > 0 }.forEach { compost ->
            on_item_on_obj(transform.id, item = compost.itemId) {
                if (checkAvailability(player)) {
                    player.attr[farmingManagerAttr]!!.addCompost(patch, compost)
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
