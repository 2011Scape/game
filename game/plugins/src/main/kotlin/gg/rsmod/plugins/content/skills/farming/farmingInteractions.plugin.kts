package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.SeedType

val transformIds = Patch.values().flatMap { world.definitions.get(ObjectDef::class.java, it.id).transforms?.toSet() ?: setOf() }.toSet()
val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

initializeRaking(transforms)
initializeHarvesting(transforms)
initializeClearing(transforms)
initializeInspecting(transforms)

initializeItemOnPatch(transforms)

fun initializeRaking(transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "rake")) {
            on_obj_option(it.id, "rake") {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.farmingManager()::rake)
                }
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
                        findPatch(player)?.let(player.farmingManager()::harvest)
                    }
                }
            }
        }
    }
}

fun initializeClearing(transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "clear")) {
            on_obj_option(it.id, "clear") {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.farmingManager()::clear)
                }
            }
        }
    }
}

fun initializeItemOnPatch(transforms: List<ObjectDef>) {
    transforms.forEach {
        on_any_item_on_obj(it.id) {
            if (checkAvailability(player)) {
                findPatch(player)?.let { player.farmingManager().itemUsed(it, player.getInteractingItem().id) }
            }
        }
    }
}

fun initializeInspecting(transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "inspect")) {
            on_obj_option(it.id, "inspect") {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.farmingManager()::inspect)
                }
            }
        }
    }
}

// Note from Ally: will let Eocene decide what do to with this
fun checkAvailability(player: Player): Boolean {
    return true
}

fun findPatch(player: Player) = Patch.byPatchId(player.getInteractingGameObj().id)
