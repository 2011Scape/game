package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.constants.Constants.farmingManagerAttr
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.PlantingHandler
import gg.rsmod.plugins.content.skills.farming.logic.handler.WaterHandler

val transformIds = Patch.values().flatMap { world.definitions.get(ObjectDef::class.java, it.id).transforms?.toSet() ?: setOf() }.toSet()
val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

initializeRaking(transforms)
initializePlanting(transforms)
initializeComposting(transforms)
initializeWatering(transforms)
initializeCuring(transforms)
initializeHarvesting(transforms)

fun initializeRaking(transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.attr[farmingManagerAttr]!!::rake)
                }
            }

            on_item_on_obj(it.id, item = Items.RAKE) {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.attr[farmingManagerAttr]!!::rake)
                }
            }
        }
    }
}

fun initializePlanting(transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        Seed.values().forEach { seed ->
            on_item_on_obj(transform.id, item = seed.seedId) {
                if (checkAvailability(player)) {
                    findPatch(player)?.let { player.attr[farmingManagerAttr]!!.plant(it, seed) }
                }
            }
        }
    }
}

fun initializeComposting(transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        CompostState.values().filter { it.itemId > 0 }.forEach { compost ->
            on_item_on_obj(transform.id, item = compost.itemId) {
                if (checkAvailability(player)) {
                    findPatch(player)?.let { player.attr[farmingManagerAttr]!!.addCompost(it, compost) }
                }
            }
        }
    }
}

fun initializeWatering(transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        WaterHandler.wateringCans.forEach { wateringCan ->
            on_item_on_obj(transform.id, item = wateringCan) {
                if (checkAvailability(player)) {
                    findPatch(player)?.let { player.attr[farmingManagerAttr]!!.water(it, wateringCan) }
                }
            }
        }
    }
}

fun initializeCuring(transforms: List<ObjectDef>) {
    transforms.forEach { transform ->
        on_item_on_obj(transform.id, item = Items.PLANT_CURE) {
            if (checkAvailability(player)) {
                findPatch(player)?.let(player.attr[farmingManagerAttr]!!::cure)
            }
        }
    }
}

fun initializeHarvesting(transforms: List<ObjectDef>) {
    val options = SeedType.values().map { it.harvest.harvestOption }.toSet()
    options.forEach { option ->
        transforms.forEach {
            if (if_obj_has_option(it.id, option)) {
                on_obj_option(it.id, option) {
                    if (checkAvailability(player)) {
                        findPatch(player)?.let(player.attr[farmingManagerAttr]!!::harvest)
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

fun findPatch(player: Player) = Patch.byPatchId(player.getInteractingGameObj().id)
