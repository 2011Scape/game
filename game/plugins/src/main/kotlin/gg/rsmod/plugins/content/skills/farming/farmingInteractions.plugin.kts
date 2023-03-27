package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.SeedType

val transformIds = Patch.values().flatMap { world.definitions.get(ObjectDef::class.java, it.id).transforms?.toSet() ?: setOf() }.toSet()
val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

initializeRaking(transforms)
initializeHarvesting(transforms)
initializeClearing(transforms)
initializeInspecting(transforms)
initializeCompostBins()

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

fun initializeCompostBins() {
    val transformIds = CompostBin.values().flatMap { world.definitions.get(ObjectDef::class.java, it.id).transforms?.toSet() ?: setOf() }.toSet()
    val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

    transforms.forEach {
        on_any_item_on_obj(it.id) {
            if (checkAvailability(player)) {
                val bin = findCompostBin(player) ?: return@on_any_item_on_obj
                val item = player.getInteractingItem().id
                if (item == Items.BUCKET) {
                    player.farmingManager().emptyCompostBin(bin)
                } else {
                    player.farmingManager().addToCompostBin(bin, item)
                }
            }
        }

        if (if_obj_has_option(it.id, "empty")) {
            on_obj_option(it.id, "empty") {
                if (checkAvailability(player)) {
                    findCompostBin(player)?.let(player.farmingManager()::emptyCompostBin)
                }
            }
        }

        if (if_obj_has_option(it.id, "open")) {
            on_obj_option(it.id, "open") {
                if (checkAvailability(player)) {
                    findCompostBin(player)?.let(player.farmingManager()::openCompostBin)
                }
            }
        }

        if (if_obj_has_option(it.id, "close")) {
            on_obj_option(it.id, "close") {
                if (checkAvailability(player)) {
                    findCompostBin(player)?.let(player.farmingManager()::closeCompostBin)
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
fun findCompostBin(player: Player) = CompostBin.byCompostBinId(player.getInteractingGameObj().id)
