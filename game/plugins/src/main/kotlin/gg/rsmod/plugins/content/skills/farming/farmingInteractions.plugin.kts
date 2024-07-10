package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.plugins.content.skills.farming.data.*
import gg.rsmod.plugins.content.skills.farming.logic.handler.WaterHandler

val transformIds =
    Patch
        .values()
        .flatMap {
            world.definitions
                .get(ObjectDef::class.java, it.id)
                .transforms
                ?.toSet()
                ?: setOf()
        }.toSet()
val transforms = transformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }

initializeRaking(transforms)
initializeHarvesting(transforms)
initializeClearing(transforms)
initializeChopping(transforms)
initializeHealthChecking(transforms)
initializeInspecting(transforms)
initializeCompostBins()
initializeSeedlings()

initializeItemOnPatch(transforms)
initializeItemOnLeprechaun()

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
    val options = Seed.values().mapNotNull { it.harvest.harvestOption }.toSet()
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

fun initializeChopping(transforms: List<ObjectDef>) {
    transforms.forEach {
        listOf("chop down", "chop-down").forEach { option ->
            if (if_obj_has_option(it.id, option)) {
                on_obj_option(it.id, option) {
                    if (checkAvailability(player)) {
                        findPatch(player)?.let { player.farmingManager().chopDown(it, player.getInteractingGameObj()) }
                    }
                }
            }
        }
    }
}

fun initializeHealthChecking(transforms: List<ObjectDef>) {
    transforms.forEach {
        if (if_obj_has_option(it.id, "check-health")) {
            on_obj_option(it.id, "check-health") {
                if (checkAvailability(player)) {
                    findPatch(player)?.let(player.farmingManager()::checkHealth)
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

fun initializeItemOnLeprechaun() {
    val allowedItems = Seed.values().map { it.produce.id }
    listOf(
        Npcs.TOOL_LEPRECHAUN,
        Npcs.TOOL_LEPRECHAUN_3121,
        Npcs.TOOL_LEPRECHAUN_4965,
        Npcs.TOOL_LEPRECHAUN_7559,
        Npcs.TOOL_LEPRECHAUN_7557,
        Npcs.TOOL_LEPRECHAUN_7558,
        Npcs.TOOL_LEPRECHAUN_7560,
        Npcs.TOOL_LEPRECHAUN_7561,
        Npcs.TOOL_LEPRECHAUN_7562,
        Npcs.TOOL_LEPRECHAUN_7563,
        Npcs.TOOL_LEPRECHAUN_7564,
        Npcs.TOOL_LEPRECHAUN_7565,
        Npcs.TOOL_LEPRECHAUN_7567,
        Npcs.TOOL_LEPRECHAUN_7569,
    ).forEach {
            npc ->
        on_any_item_on_npc(npc) {
            val item = player.getInteractingItem()
            val definition = player.world.definitions.get(ItemDef::class.java, item.id)
            if (definition.noted) {
                player.queue {
                    chatNpc("That IS a banknote!")
                }
            } else if (definition.noteLinkId == 0) {
                player.queue {
                    chatNpc(*"Nay, there's no such thing as a banknote for that.".splitForDialogue())
                }
            } else if (item.id in allowedItems) {
                val amount = player.inventory.getItemCount(item.id)
                player.queue {
                    if (player.inventory.remove(item.id, amount = amount, assureFullRemoval = true).hasSucceeded()) {
                        player.inventory.add(definition.noteLinkId, amount = amount)
                        itemMessageBox("You receive bank notes in exchange for your produce.", item.id)
                    }
                }
            } else {
                player.queue {
                    chatNpc(*"Nay, I've got no banknotes to exchange for that item.".splitForDialogue())
                }
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

fun initializeSeedlings() {
    Sapling.values().forEach { saplingType ->
        on_item_on_item(saplingType.seedId, Items.PLANT_POT_5354) {
            if (!player.inventory.contains(Items.GARDENING_TROWEL)) {
                player.message("You need a gardening trowel to do that.")
            } else {
                player.inventory.remove(saplingType.seedId)
                player.inventory.remove(Items.PLANT_POT_5354)
                player.inventory.add(saplingType.seedlingId)
            }
        }

        WaterHandler.wateringCans.filter { it != Items.WATERING_CAN }.forEach { can ->
            on_item_on_item(saplingType.seedlingId, can) {
                player.inventory.remove(saplingType.seedlingId)
                player.inventory.remove(can)
                player.inventory.add(WaterHandler.wateringCans[WaterHandler.wateringCans.indexOf(can) - 1])
                player.inventory.add(saplingType.wateredSeedlingId)
            }
        }
    }
}

fun initializeCompostBins() {
    val transformIds =
        CompostBin
            .values()
            .flatMap {
                world.definitions
                    .get(ObjectDef::class.java, it.id)
                    .transforms
                    ?.toSet()
                    ?: setOf()
            }.toSet()
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

// Note from Ally* I'll leave it to Eocene to remove this completely
fun checkAvailability(player: Player): Boolean {
    return true
}

fun findPatch(player: Player) = Patch.byPatchId(player.getInteractingGameObj().id)

fun findCompostBin(player: Player) = CompostBin.byCompostBinId(player.getInteractingGameObj().id)
