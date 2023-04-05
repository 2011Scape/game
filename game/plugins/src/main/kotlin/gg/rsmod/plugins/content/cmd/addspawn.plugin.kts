package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.util.Misc
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.reflect.Modifier

on_command("add_spawn", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::p 0</col>") { values ->
        val id = values[0].toInt()
        addSpawn(player, id)
        world.spawn(Npc(id = id, tile = player.tile, world = world))
    }
}

fun getName(id: Int): String? {
    val constVals = Npcs::class.java.declaredFields.filter {
        it.isAccessible = true
        it.modifiers and Modifier.STATIC != 0 && it.type == Int::class.java
    }

    constVals.forEach { constVal ->
        val value = constVal.get(null) as Int
        if (value == id)
            return constVal.name
    }
    return null
}

fun tryWithUsage(player: Player, args: Array<String>, failMessage: String, tryUnit: Function1<Array<String>, Unit>) {
    try {
        tryUnit.invoke(args)
    } catch (e: Exception) {
        player.message(failMessage, type = ChatMessageType.CONSOLE)
        e.printStackTrace()
    }
}

fun addSpawn(player: Player, id: Int) {
    val string = "spawn_npc(npc = Npcs.${getName(id)}, x = ${player.tile.x}, z = ${player.tile.z}, walkRadius = 5, direction = Direction.${player.faceDirection})"
    try {
        val file = File("./game/plugins/src/main/kotlin/gg/rsmod/plugins/content/areas/spawns/spawns_${player.tile.regionId}.plugin.kts")
        if (!file.exists())
            createSpawnFile(file)
        val writer = BufferedWriter(FileWriter(file, true))
        writer.write("$string //Added by command by user: ${Misc.formatForDisplay(player.username)}")
        writer.newLine()
        writer.close()
        player.message("Added Spawn for NPC=<col=FFFF00>$id</col> [Region=<col=FFFF00>${player.tile.regionId}</col> - Coords=<col=FFFF00>${player.tile.x}, ${player.tile.z}, ${player.tile.height}</col>]", type = ChatMessageType.CONSOLE)
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