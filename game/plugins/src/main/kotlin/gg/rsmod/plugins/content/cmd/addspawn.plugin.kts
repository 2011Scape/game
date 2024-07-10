package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.reflect.Modifier
import java.nio.file.Files
import java.nio.file.Paths

on_command("add_item", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::add_item [id] [amount]</col>",
    ) { values ->
        val itemId = values[0].toInt()
        val amount = if (values.size > 1) values[1].toInt() else 1
        addItem(player, itemId, amount)
        world.spawn(GroundItem(itemId, amount, Tile(x = player.tile.x, z = player.tile.z, player.tile.height)))
    }
}

on_command("add_npc", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::add_spawn 0</col>") { values ->
        val id = values[0].toInt()
        addNpc(player, id)
        world.spawn(Npc(id = id, tile = player.tile, world = world))
    }
}

/**
 * Grabs the [Npcs] file val name for the corresponding [id]
 * @param id The npcs id.
 * @return if [id] is found in [Npcs] as a value, return the value name. If not, null
 */
fun getName(id: Int): String? {
    val constVals =
        Npcs::class.java.declaredFields.filter {
            it.isAccessible = true
            it.modifiers and Modifier.STATIC != 0 && it.type == Int::class.java
        }

    constVals.forEach { constVal ->
        val value = constVal.get(null) as Int
        if (value == id) {
            return constVal.name
        }
    }
    return null
}

fun tryWithUsage(
    player: Player,
    args: Array<String>,
    failMessage: String,
    tryUnit: Function1<Array<String>, Unit>,
) {
    try {
        tryUnit.invoke(args)
    } catch (e: Exception) {
        player.message(failMessage, type = ChatMessageType.CONSOLE)
        e.printStackTrace()
    }
}

fun addItem(
    player: Player,
    itemId: Int,
    amount: Int,
) {
    val string =
        "spawn_item(item = $itemId, amount = $amount, x = ${player.tile.x}, z = ${player.tile.z}, height = ${player.tile.height})"
    try {
        val file =
            File(
                "./game/plugins/src/main/kotlin/gg/rsmod/plugins/content/areas/spawns/spawns_${player.tile.regionId}.plugin.kts",
            )
        if (!file.exists()) {
            createSpawnFile(file)
        }
        val writer = BufferedWriter(FileWriter(file, true))

        val lines = Files.readAllLines(Paths.get(file.path))
        if (lines.last().isNotEmpty() || lines.last().isNotBlank()) {
            writer.newLine()
        }

        writer.write(string)
        writer.newLine()
        writer.close()
        player.message(
            "Added Item Spawn for ID=<col=FFFF00>$itemId</col> Amount=<col=FFFF00>$amount</col> [Region=<col=FFFF00>${player.tile.regionId}</col> - Coords=<col=FFFF00>${player.tile.x}, ${player.tile.z}, ${player.tile.height}</col>]",
            type = ChatMessageType.CONSOLE,
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Adds the spawn to the proper region file corresponding to the players location.
 */
fun addNpc(
    player: Player,
    id: Int,
) {
    if (getName(id) == null) {
        player.message(
            "ERROR: val not found corresponding with given id; reverting to using supplied id instead.",
            type = ChatMessageType.CONSOLE,
        )
    }
    val string =
        "spawn_npc(npc = ${if (getName(
                id,
            ) == null
        ) {
            id
        } else {
            "Npcs.${getName(
                id,
            )}"
        }}, x = ${player.tile.x}, z = ${player.tile.z}, walkRadius = 5, direction = Direction.${player.faceDirection})"
    try {
        val file =
            File(
                "./game/plugins/src/main/kotlin/gg/rsmod/plugins/content/areas/spawns/spawns_${player.tile.regionId}.plugin.kts",
            )
        if (!file.exists()) {
            createSpawnFile(file)
        }
        val writer = BufferedWriter(FileWriter(file, true))

        val lines = Files.readAllLines(Paths.get(file.path))
        if (lines.last().isNotEmpty() || lines.last().isNotBlank()) {
            writer.newLine()
        }

        writer.write(string)
        writer.newLine()
        writer.close()
        player.message(
            "Added Spawn for NPC=<col=FFFF00>$id</col> [Region=<col=FFFF00>${player.tile.regionId}</col> - Coords=<col=FFFF00>${player.tile.x}, ${player.tile.z}, ${player.tile.height}</col>]",
            type = ChatMessageType.CONSOLE,
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun createSpawnFile(file: File) {
    val write = file.printWriter()
    write.println("package gg.rsmod.plugins.content.areas.spawns")
    write.println("")
    write.close()
}
