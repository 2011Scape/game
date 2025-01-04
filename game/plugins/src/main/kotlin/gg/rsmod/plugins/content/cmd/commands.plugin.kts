package gg.rsmod.plugins.content.cmd

import de.mkammerer.argon2.Argon2Factory
import gg.rsmod.game.fs.def.NpcDef
import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.message.impl.LogoutFullMessage
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.bits.INFINITE_VARS_STORAGE
import gg.rsmod.game.model.bits.InfiniteVarsType
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.game.model.region.ChunkCoords
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER
import gg.rsmod.game.service.serializer.PlayerSerializerService
import gg.rsmod.game.sync.block.UpdateBlockType
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.inter.attack.AttackTab
import gg.rsmod.plugins.content.inter.bank.openBank
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport
import gg.rsmod.plugins.content.mechanics.multi.MultiService
import gg.rsmod.plugins.content.mechanics.music.RegionMusicService
import gg.rsmod.plugins.content.npcs.Constants
import gg.rsmod.plugins.content.skills.farming.core.FarmTicker
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.util.Misc
import java.text.DecimalFormat
import java.text.NumberFormat

on_command("male") {
    player.appearance = Appearance.DEFAULT
    player.addBlock(UpdateBlockType.APPEARANCE)
}

on_command("female") {
    player.appearance = Appearance.DEFAULT_FEMALE
    player.addBlock(UpdateBlockType.APPEARANCE)
}

on_command("farm_tick", Privilege.ADMIN_POWER) {
    player.farmingManager().onFarmingTick(
        FarmTicker.SeedTypesForTick(SeedType.values().toSet(), SeedType.values().toSet()),
    )
}

on_command("clear", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::clear 1</col>") { values ->
        val amount = values[0].toInt()
        for (i in 0 until amount) {
            val item = player.inventory[i]
            if (item != null) {
                player.inventory.remove(item, beginSlot = i)
            }
        }
    }
}

on_command("pnpc", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::pnpc 1</col>") { values ->
        val id = values[0].toInt()
        player.setTransmogId(id)
    }
}

on_command("objanim", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::objanim LENGTHWISE_WALL 1</col>",
    ) { values ->
        val id = values[1].toInt()
        val idType = values[0]
        // No need for the null check here
        val tile = Tile(player.tile.x, player.tile.z, player.tile.height)
        val ObjectSelect = player.world.getObject(tile, ObjectType.valueOf(idType))
        ObjectSelect?.let { nonNullObjectSelect ->
            player.write(LocAnimMessage(gameObject = nonNullObjectSelect, animation = id))
            player.message("${player.world.getObject(tile, ObjectType.valueOf(idType))}")
        }
    }
}

on_command("players") {
    // Count the total number of players online
    val count = world.players.count()
    // Map to store player IDs and their corresponding Player objects
    val playersMap = mutableMapOf<Int, Player>()
    // Populate playersMap with player IDs and their Player objects
    world.players.forEach { p ->
        playersMap[playersMap.size] = p
    }
    // Check if the player initiating the command is not in combat and not interacting with an interface
    if (!player.timers.has(ACTIVE_COMBAT_TIMER) && player.interfaces.currentModal == -1) {
        // Open scroll interface to display player information
        player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_FULL, interfaceId = 275)

        // Hide a specific component in the interface
        player.setComponentHidden(interfaceId = 275, component = 14, hidden = true)

        // Set the count of online players in a specific component of the interface
        player.setComponentText(interfaceId = 275, component = 2, "Players Online: $count")

        // Clear components 16 to 315 to prepare for displaying player information
        for (i in 16..315) {
            player.setComponentText(interfaceId = 275, component = i, "")
        }
        // Display player information in the interface for each player
        playersMap.forEach { (i, p) ->
            // Determine privilege icon based on player's privilege level
            val icon =
                when (p.privilege.id) {
                    1 -> "<img=0>" // pmods
                    2, 3 -> "<img=1>" // owners and moderators
                    else -> "" // Default case (no icon)
                }
            // Set player information in the interface
            player.setComponentText(interfaceId = 275, component = 17 + i, "$icon ${Misc.formatForDisplay(p.username)}")
        }
    } else {
        // If the player is in combat or interacting with an interface
        if (count == 1) {
            // Display the count of players when only one player is online
            player.message("There is currently 1 player online.")
        } else {
            // Display the count of players and show information for the first 5 players
            player.message("There are currently $count players online. Showing first 5 players.")
            playersMap.values.take(5).forEach { p ->
                // Determine privilege icon for each player and display their username
                val icon =
                    when (p.privilege.id) {
                        1 -> "<img=0>"
                        2, 3 -> "<img=1>"
                        else -> ""
                    }
                player.message(" - $icon ${Misc.formatForDisplay(p.username)}")
            }
        }
    }
}

on_command("locate") {
    // Get command arguments
    val args = player.getCommandArgs()
    // Validate command usage and arguments
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::locate username</col>",
    ) { values ->
        // Find the player object based on the provided username
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        // Get the area name where the player is located
        val areaName = getAreaName(p)
        // Determine privilege icon based on player's privilege level
        val icon =
            when (p.privilege.id) {
                1 -> "<img=0>" // Represents a specific privilege level (pmod)
                2, 3 -> "<img=1>" // Represents another privilege level (admin, owner)
                else -> "" // Default case (no icon)
            }
        player.message(
            "Player $icon<col=42C66C>${Misc.formatForDisplay(p.username)}</col> is in: <col=1A43bf>$areaName</col>.",
        )
    }
}

on_command("yell") {
    player.message(
        "To talk in the global chat, start your message in public chat with a period (.)",
        ChatMessageType.CONSOLE,
    )
}

val bossKcCommands = listOf("bosskills", "bosskc")
val slayerKcCommands = listOf("slayerkills", "slayerkc")

bossKcCommands.forEach { command ->
    on_command(command) {
        val bossKillCounts = mutableMapOf<String, Int>()
        Constants.BOSS_NPC_IDS.forEach { npcId ->
            var npcName = ""
            if (npcId == Constants.BARROWS_CHEST_ID) {
                npcName = "Barrows Chests"
            } else {
                val npcDef = player.world.definitions.get(NpcDef::class.java, npcId)
                npcName = npcDef.name
            }
            bossKillCounts[npcName] = player.getNpcKillCount(npcId)
        }
        displayKillCounts(player, bossKillCounts, "Boss Kill Counts")
    }
}

slayerKcCommands.forEach { command ->
    on_command(command) {
        val slayerKillCounts = mutableMapOf<String, Int>()
        Constants.SLAYER_NPC_IDS.forEach { npcId ->
            val npcDef = player.world.definitions.get(NpcDef::class.java, npcId)
            val npcName = npcDef.name
            // Slayer NPCs can have multiple IDs, so let's combine the counts
            slayerKillCounts[npcName] = slayerKillCounts.getOrDefault(npcName, 0) + player.getNpcKillCount(npcId)
        }
        displayKillCounts(player, slayerKillCounts, "Slayer Kill Counts")
    }
}

val checkNpcKillCommands = listOf("kc", "kills", "checknpckill", "checknpckills")

checkNpcKillCommands.forEach { command ->
    on_command(command) {
        val arguments = player.getCommandArgs()
        if (arguments.isNotEmpty()) {
            val npcInput = arguments.joinToString(" ").toLowerCase()

            try {
                // Try to parse as an ID
                val npcId = npcInput.toInt()
                val killCount = player.getNpcKillCount(npcId)
                val npcDef = player.world.definitions.get(NpcDef::class.java, npcId)
                val npcName = npcDef.name
                player.message("Kill count for $npcName: ${NumberFormat.getNumberInstance().format(killCount as Int)}")
            } catch (e: NumberFormatException) {
                // If not an ID, treat as a name
                val allNpcDefs = player.world.definitions.getAll(NpcDef::class.java) as Map<Int, NpcDef>
                val matchingDefs = allNpcDefs.filter { (_, def) -> def.name.toLowerCase().startsWith(npcInput) }

                if (matchingDefs.isEmpty()) {
                    player.message("No NPCs found with the name: $npcInput")
                } else {
                    val results =
                        matchingDefs.mapNotNull { (id, def) ->
                            val killCount = player.getNpcKillCount(id)
                            if (killCount >
                                0
                            ) {
                                "Kill count for ${def.name} (ID: $id): ${NumberFormat.getNumberInstance().format(
                                    killCount as Int,
                                )}"
                            } else {
                                null
                            }
                        }

                    if (results.isEmpty()) {
                        player.message("Kill count for $npcInput: 0")
                    } else {
                        results.forEach(player::message)
                    }
                }
            }
        } else {
            player.message("Please provide an NPC ID or name.")
        }
    }
}

on_command("addloyalty", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::addloyalty alycia 1</col>",
    ) { values ->
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        val amount = if (values.size > 1) Math.min(Int.MAX_VALUE.toLong(), values[1].parseAmount()).toInt() else 1
        p.addLoyalty(amount)
        p.message(
            "You have been granted ${Misc.formatWithIndefiniteArticle(
                "loyalty point",
                amount,
            )}, as a thank you for your contributions.",
        )
    }
}

on_command("addslayerpoints", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::addslayerpoints alycia 1</col>",
    ) { values ->
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        val amount = if (values.size > 1) Math.min(Int.MAX_VALUE.toLong(), values[1].parseAmount()).toInt() else 1
        p.addSlayerPoints(amount)
        p.message("You have been given ${Misc.formatWithIndefiniteArticle("slayer point", amount)}.")
    }
}

on_command("damage", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::damage alycia 99</col>",
    ) { values ->
        val targetPlayer = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        val takeDamage = values[1].toIntOrNull() ?: return@tryWithUsage
        player.dealHit(
            target = targetPlayer,
            minHit = takeDamage.toDouble(),
            maxHit = takeDamage.toDouble() + 0.01,
            landHit = true,
            delay = 1,
            hitType = HitType.REGULAR_HIT,
        )
    }
}

on_command("bonusxp", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::bonusxp list or add|remove playerName</col>",
    ) { values ->
        val action = values[0].lowercase()

        when (action) {
            "add" -> {
                val targetName =
                    values.getOrNull(1)?.lowercase()
                        ?: return@tryWithUsage
                world.playersWithBonusXP.add(targetName)
                player.message("$targetName has been granted bonus XP access.")
            }
            "remove" -> {
                val targetName =
                    values.getOrNull(1)?.lowercase()
                        ?: return@tryWithUsage
                world.playersWithBonusXP.remove(targetName)
                player.message("$targetName's bonus XP access has been revoked.")
            }
            "list" -> {
                if (world.playersWithBonusXP.isEmpty()) {
                    player.message("No players have been granted bonus XP access.")
                } else {
                    val playersList = world.playersWithBonusXP.joinToString(", ")
                    player.message("Players with bonus XP access: $playersList")
                }
            }
            else -> return@tryWithUsage
        }
    }
}

on_command("teleto", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::teleto alycia</col>",
    ) { values ->
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        player.teleport(p.tile, TeleportType.RING_OF_KINSHIP)
    }
}

on_command("teletome", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::teleto alycia</col>",
    ) { values ->
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        p.teleport(player.tile, TeleportType.RING_OF_KINSHIP)
    }
}

on_command("reboot", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::reboot 500</col>") { values ->
        val cycles = values[0].toInt()
        world.rebootTimer = cycles
        world.sendRebootTimer()
    }
}

on_command("kick", Privilege.MOD_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::kick alycia</col>") { values ->
        val p = world.getPlayerForName(values[0].replace("_", " ")) ?: return@tryWithUsage
        p.requestLogout()
        p.write(LogoutFullMessage())
        p.channelClose()
    }
}

on_command("rate") {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::rate 0</col> or <col=42C66C>::rate attack</col>",
    ) { values ->
        var skill: Int
        try {
            skill = values[0].toInt()
        } catch (e: NumberFormatException) {
            var name = values[0].lowercase()
            when (name) {
                "con" -> name = "construction"
                "cons" -> name = "constitution"
                "craft" -> name = "crafting"
                "hunt" -> name = "hunter"
                "slay" -> name = "slayer"
                "pray" -> name = "prayer"
                "mage" -> name = "magic"
                "fish" -> name = "fishing"
                "herb" -> name = "herblore"
                "rc" -> name = "runecrafting"
                "fm" -> name = "firemaking"
            }
            skill = Skills.getSkillForName(world, player.skills.maxSkills, name)
        }
        if (skill != -1) {
            val rate = player.interpolate(1.0, 5.0, player.skills.getMaxLevel(skill))
            player.message(
                "Your experience rate for ${Skills.getSkillName(
                    world,
                    skill,
                ).lowercase()} is ${String.format("%.2f", rate)}x",
                type = ChatMessageType.CONSOLE,
            )
        } else {
            player.message("Could not find skill with identifier: ${values[0]}", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("home", Privilege.MOD_POWER) {
    val home = world.gameContext.home
    player.moveTo(home)
}

on_command("changepass") {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::changepass newpassword</col>",
    ) { values ->
        val password = values[0]
        val client = player as Client
        client.passwordHash = Argon2Factory.create().hash(2, 65536, 1, password.toCharArray())
        player.world.getService(PlayerSerializerService::class.java, searchSubclasses = true)?.saveClientData(client)
        player.message(
            "<col=178000>You've successfully changed your password to $password",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("noclip", Privilege.ADMIN_POWER) {
    val noClip = !(player.attr[NO_CLIP_ATTR] ?: false)
    player.attr[NO_CLIP_ATTR] = noClip
    player.message(
        "No-clip: ${if (noClip) "<col=178000>enabled</col>" else "<col=42C66C>disabled</col>"}",
        type = ChatMessageType.CONSOLE,
    )
}

on_command("mypos") {
    val instancedMap = world.instanceAllocator.getMap(player.tile)
    val tile = player.tile
    if (instancedMap == null) {
        player.message(
            "Tile=[<col=42C66C>${tile.x}, ${tile.z}, ${tile.height}</col>], Region=${player.tile.regionId}, Chunk Coords=${player.tile.chunkCoords}, Chunk Hash=${player.tile.chunkCoords.hashCode()} Object=${player.world.getObject(
                tile,
                ObjectType.INTERACTABLE,
            )}",
            type = ChatMessageType.CONSOLE,
        )
    } else {
        val delta = tile - instancedMap.area.bottomLeft
        player.message(
            "Tile=[<col=42C66C>${tile.x}, ${tile.z}, ${tile.height}</col>], Relative=[${delta.x}, ${delta.z}]",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("getmultichunks", Privilege.ADMIN_POWER) {
    val multiCombatChunks = world.getMultiCombatChunks()
    print("$multiCombatChunks")
}

on_command("addchunks", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    if (args.size < 4) {
        player.message("Usage: ::addchunks <northEastX> <northEastZ> <southWestX> <southWestZ>")
        return@on_command
    }

    try {
        val northEastX = args[0].toInt()
        val northEastZ = args[1].toInt()
        val southWestX = args[2].toInt()
        val southWestZ = args[3].toInt()

        val chunkHashes = calculateChunkHashes(northEastX, northEastZ, southWestX, southWestZ, player)

        val multiService = world.getService(MultiService::class.java)

        multiService?.appendMultiCombatChunks(chunkHashes)
        player.message("Chunks added successfully.")
    } catch (e: NumberFormatException) {
        player.message("Invalid coordinates. Please ensure all coordinates are integers.")
    }
}

on_command("addregions", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    if (args.isEmpty()) {
        player.message("Usage: ::addregions <regionId1> <regionId2> <regionId3> ...")
        return@on_command
    }

    try {
        val regionIds = args.map { it.toInt() }
        val multiService = world.getService(MultiService::class.java)

        multiService?.appendMultiCombatRegions(regionIds)
        player.message("Regions added successfully.")
    } catch (e: NumberFormatException) {
        player.message("Invalid region ID. Please ensure all region IDs are integers.")
    }
}

fun calculateChunkHashes(
    northEastX: Int,
    northEastZ: Int,
    southWestX: Int,
    southWestZ: Int,
    player: Player,
): MutableList<Int> {
    val chunkHashes = mutableListOf<Int>()

    for (x in southWestX..northEastX step 8) {
        for (z in southWestZ..northEastZ step 8) {
            // Create a Tile object for the current x, z coordinates
            val tile = Tile(x, z)

            // Get the ChunkCoords from the Tile
            val chunkCoord = ChunkCoords.fromTile(tile)
            val hash = chunkCoord.hashCode()

            chunkHashes.add(hash)
            player.message("Adding chunk at [ChunkX: ${chunkCoord.x}, ChunkZ: ${chunkCoord.z}] with hash: $hash")
        }
    }

    return chunkHashes
}

on_command("change", Privilege.ADMIN_POWER) {
    openCharacterCustomizing(player)
}

on_command("close_inter", Privilege.ADMIN_POWER) {
    player.closeFullscreenInterface()
}

on_command("tele", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    var x: Int
    var z: Int
    var height: Int
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::tele 3200 3200</col>",
    ) { values ->
        if (values.size == 1 && values[0].contains(",")) {
            val split = values[0].split(",".toRegex())
            x = split[1].toInt() shl 6 or split[3].toInt()
            z = split[2].toInt() shl 6 or split[4].toInt()
            height = split[0].toInt()
        } else {
            x = values[0].toInt()
            z = values[1].toInt()
            height = if (values.size > 2) values[2].toInt() else 0
        }
        player.moveTo(x, z, height)
    }
}

on_command("telechunk", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    var chunkX: Int
    var chunkZ: Int
    var height: Int
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::telechunk 200 200</col>",
    ) { values ->
        if (values.size < 2) {
            throw IllegalArgumentException("You must provide both X and Z chunk coordinates.")
        }
        chunkX = values[0].toInt()
        chunkZ = values[1].toInt()
        height = if (values.size > 2) values[2].toInt() else 0

        val worldX = chunkX * 8
        val worldZ = chunkZ * 8

        player.moveTo(worldX, worldZ, height)
    }
}

on_command("teler", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::teler 12850</col>") { values ->
        val region = values[0].toInt()
        val tile = Tile.fromRegion(region)
        player.moveTo(tile)
    }
}

on_command("anim", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::anim 1</col>") { values ->
        val id = values[0].toInt()
        val animCycles = world.getAnimationDelay(id)
        val animDuration = ((world.getAnimationDelay(id) - 1) * 600.0) + 1
        val animFrames = world.getAnimationFrames(id)
        player.animate(id)
        player.message(
            "Animate: $id, Duration: ~ $animDuration ms or $animCycles cycles, Frames: $animFrames",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("render", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::render 1</col>") { values ->
        val id = values[0].toInt()
        player.setRenderAnimation(id, 5)
        player.message("Render Animation: $id", type = ChatMessageType.CONSOLE)
    }
}

on_command("gfx", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::gfx 1</col>") { values ->
        val id = values[0].toInt()
        val height = if (values.size >= 2) values[1].toInt() else 100
        player.graphic(id, height)
        player.message("Graphic: $id", type = ChatMessageType.CONSOLE)
    }
}

on_command("sound", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::sound 1</col>") { values ->
        val id = values[0].toInt()
        player.playSound(id)
        player.message("Sound: $id", type = ChatMessageType.CONSOLE)
    }
}

on_command("jingle", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::jingle 1</col>") { values ->
        val id = values[0].toInt()
        player.playJingle(id)
        player.message("Jingle: $id", type = ChatMessageType.CONSOLE)
    }
}

on_command("song", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::song 1</col>") { values ->
        val id = values[0].toInt()
        player.playSong(id)
        player.message("Song: $id", type = ChatMessageType.CONSOLE)
    }
}

on_command("infrun", Privilege.ADMIN_POWER) {
    player.toggleStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.RUN)
    player.message(
        "Infinite run: ${
            if (!player.hasStorageBit(
                    INFINITE_VARS_STORAGE,
                    InfiniteVarsType.RUN,
                )
            ) {
                "<col=42C66C>disabled</col>"
            } else {
                "<col=178000>enabled</col>"
            }
        }",
        type = ChatMessageType.CONSOLE,
    )
}

on_command("infpray", Privilege.ADMIN_POWER) {
    player.toggleStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.PRAY)
    player.message(
        "Infinite prayer: ${
            if (!player.hasStorageBit(
                    INFINITE_VARS_STORAGE,
                    InfiniteVarsType.PRAY,
                )
            ) {
                "<col=42C66C>disabled</col>"
            } else {
                "<col=178000>enabled</col>"
            }
        }",
        type = ChatMessageType.CONSOLE,
    )
}

on_command("infhp", Privilege.ADMIN_POWER) {
    player.toggleStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.HP)
    player.message(
        "Infinite hp: ${
            if (!player.hasStorageBit(
                    INFINITE_VARS_STORAGE,
                    InfiniteVarsType.HP,
                )
            ) {
                "<col=42C66C>disabled</col>"
            } else {
                "<col=178000>enabled</col>"
            }
        }",
        type = ChatMessageType.CONSOLE,
    )
}

on_command("invisible", Privilege.ADMIN_POWER) {
    player.invisible = !player.invisible
    player.message(
        "Invisible: ${if (!player.invisible) "<col=42C66C>false</col>" else "<col=178000>true</col>"}",
        type = ChatMessageType.CONSOLE,
    )
}

on_command("camangle", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()

    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::camangle pitch yaw</col>",
    ) { values ->
        val pitch = values[0].toInt()
        val yaw = values[1].toInt()

        player.forceCameraAngle(pitch, yaw)
    }
}

on_command("cammoveto", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()

    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::cammoveto rate x z height step</col>",
    ) { values ->
        val rate = values[0].toInt()
        val x = values[1].toInt()
        val z = values[2].toInt()
        val y = values[3].toInt()
        val step = values[4].toInt()

        player.moveCameraTo(rate, x, z, y, step)
    }
}

on_command("camshake", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()

    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::camshake frequency index time center amplitude</col>",
    ) { values ->
        val frequency = values[0].toInt()
        val index = values[1].toInt()
        val time = values[2].toInt()
        val center = values[3].toInt()
        val amplitude = values[4].toInt()

        player.shakeCamera(frequency, index, time, center, amplitude)
    }
}

on_command("camreset", Privilege.ADMIN_POWER) {
    player.resetCamera()
}

on_command("camresetsmooth", Privilege.ADMIN_POWER) {
    player.resetCameraSmooth()
}

on_command("camlookat", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()

    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::camlookat x z height step speed</col>",
    ) { values ->
        val x = values[0].toInt()
        val z = values[1].toInt()
        val height = values[2].toInt()
        val step = values[3].toInt()
        val speed = values[4].toInt()

        player.cameraLookAt(x, z, height, step, speed)
    }
}

on_command("npc", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::npc id walk-radius</col>",
    ) { values ->
        val id = values[0].toInt()
        val walkradius = if (values.size > 1) values[1].toInt() else 0
        val npc = Npc(id, player.tile, world)
        npc.walkRadius = walkradius
        world.spawn(npc)
    }
}

on_command("removenpc", Privilege.ADMIN_POWER) {
    val chunk = world.chunks.getOrCreate(player.tile)
    val npc =
        chunk.getEntities<Npc>(player.tile, EntityType.NPC).firstOrNull()
    if (npc != null) {
        world.remove(npc)
        player.message(
            "Removed npc id = <col=e20f00>${npc.id}</col> name = <col=e20f00>${npc.name}</col>.",
            type = ChatMessageType.GAME_MESSAGE,
        )
    } else {
        player.message("No NPC found in the tile your standing on.", type = ChatMessageType.CONSOLE)
        player.message("No NPC found in the tile your standing on.", type = ChatMessageType.GAME_MESSAGE)
    }
}

on_command("obj", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::obj id type rotation</col>",
    ) { values ->
        val id = values[0].toInt()
        val type = if (values.size > 1) values[1].toInt() else 10
        val rot = if (values.size > 2) values[2].toInt() else 0
        val obj = DynamicObject(id, type, rot, player.tile)
        world.spawn(obj)
    }
}

on_command("tempobj", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::obj id type rotation timer</col>",
    ) { values ->
        val id = values[0].toInt()
        val type = if (values.size > 1) values[1].toInt() else 10
        val rot = if (values.size > 2) values[2].toInt() else 0
        val timer = if (values.size > 3) values[3].toInt() else 0
        val obj = DynamicObject(id, type, rot, player.tile)
        world.spawnTemporaryObject(obj, timer)
    }
}

on_command("changeobj", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::changeobj objectId objectX objectZ objectRotation newRotation</col>",
    ) { values ->
        val currentObjectId = values[0].toInt()
        val currentX = values[1].toInt()
        val currentZ = values[2].toInt()
        val currentRotation = values[3].toInt()

        val newRotation = if (values.size > 4) values[4].toInt() else -1
        val newX = if (values.size > 5) values[5].toInt() else -1
        val newZ = if (values.size > 6) values[6].toInt() else -1
        val newObjectId = if (values.size > 7) values[7].toInt() else -1
        val wait = 3
        player.transformObject(
            currentObjectId,
            currentX,
            currentZ,
            currentRotation,
            newObjectId,
            newX,
            newZ,
            newRotation,
            wait,
        )
    }
}

on_command("removeobj", Privilege.ADMIN_POWER) {
    val chunk = world.chunks.getOrCreate(player.tile)
    val obj =
        chunk.getEntities<GameObject>(player.tile, EntityType.STATIC_OBJECT, EntityType.DYNAMIC_OBJECT).firstOrNull()
    if (obj != null) {
        world.remove(obj)
        player.message("Removed object id = <col=e20f00>${obj.id}</col>.", type = ChatMessageType.GAME_MESSAGE)
    } else {
        player.message("No object found in the tile your standing on.", type = ChatMessageType.CONSOLE)
        player.message("No object found in the tile your standing on.", type = ChatMessageType.GAME_MESSAGE)
    }
}

on_command("master", Privilege.ADMIN_POWER) {
    for (i in 0 until player.skills.maxSkills) {
        player.skills.setBaseLevel(i, 99)
    }
    player.calculateAndSetCombatLevel()
}
on_command("drainskills", Privilege.DEV_POWER) {
    for (i in 0 until player.skills.maxSkills) {
        player.skills.setCurrentLevel(i, 1)
    }
}

on_command("restore", Privilege.ADMIN_POWER) {
    player.setCurrentLifepoints(player.getMaximumLifepoints())
    player.runEnergy = 100.0
    AttackTab.setEnergy(player, 100)
    for (i in 0 until player.skills.maxSkills) {
        player.skills.setCurrentLevel(i, player.skills.getMaxLevel(i))
    }
    player.message("You have been given restored stats.", type = ChatMessageType.GAME_MESSAGE)
    player.message("You have been given restored stats.", type = ChatMessageType.CONSOLE)
}

on_command("reset", Privilege.ADMIN_POWER) {
    for (i in 0 until player.skills.maxSkills) {
        player.skills.setBaseLevel(i, if (i == Skills.CONSTITUTION) 10 else 1)
    }
    player.calculateAndSetCombatLevel()
}

on_command("setxp", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::setlvl 0 99</col> or <col=42C66C>::setlvl attack 99</col>",
    ) { values ->
        var skill: Int
        try {
            skill = values[0].toInt()
        } catch (e: NumberFormatException) {
            var name = values[0].lowercase()
            when (name) {
                "con" -> name = "construction"
                "hp" -> name = "hitpoints"
                "craft" -> name = "crafting"
                "hunt" -> name = "hunter"
                "slay" -> name = "slayer"
                "pray" -> name = "prayer"
                "mage" -> name = "magic"
                "fish" -> name = "fishing"
                "herb" -> name = "herblore"
                "rc" -> name = "runecrafting"
                "fm" -> name = "firemaking"
            }
            skill = Skills.getSkillForName(world, player.skills.maxSkills, name)
        }
        if (skill != -1) {
            val oldLevel = player.skills.getMaxLevel(skill)
            val oldTotal = player.skills.calculateTotalLevel
            val experience = values[1].toDouble()
            player.skills.setBaseXp(skill, experience)
            val increment = player.skills.getMaxLevel(skill) - oldLevel
            if (increment > 0) {
                player.attr[LAST_TOTAL_LEVEL] = oldTotal
                player.attr[LEVEL_UP_SKILL_ID] = skill
                player.attr[LEVEL_UP_INCREMENT] = increment
                world.plugins.executeSkillLevelUp(player)
            }
            player.message(
                "You have set your ${Skills.getSkillName(world, skill)} experience to: $experience!",
                type = ChatMessageType.CONSOLE,
            )
        } else {
            player.message("Could not find skill with identifier: ${values[0]}", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("setlvl", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::setlvl 0 99</col> or <col=42C66C>::setlvl attack 99</col>",
    ) { values ->
        var skill: Int
        try {
            skill = values[0].toInt()
        } catch (e: NumberFormatException) {
            var name = values[0].lowercase()
            when (name) {
                "con" -> name = "construction"
                "hp" -> name = "hitpoints"
                "craft" -> name = "crafting"
                "hunt" -> name = "hunter"
                "slay" -> name = "slayer"
                "pray" -> name = "prayer"
                "mage" -> name = "magic"
                "fish" -> name = "fishing"
                "herb" -> name = "herblore"
                "rc" -> name = "runecrafting"
                "fm" -> name = "firemaking"
            }
            skill = Skills.getSkillForName(world, player.skills.maxSkills, name)
        }
        if (skill != -1) {
            val level = values[1].toInt()
            val oldLevel = player.skills.getMaxLevel(skill)
            val oldTotal = player.skills.calculateTotalLevel
            player.skills.setBaseLevel(skill, level)
            val increment = player.skills.getMaxLevel(skill) - oldLevel
            if (increment > 0) {
                player.attr[LAST_TOTAL_LEVEL] = oldTotal
                player.attr[LEVEL_UP_SKILL_ID] = skill
                player.attr[LEVEL_UP_INCREMENT] = increment
                world.plugins.executeSkillLevelUp(player)
            }
            player.message(
                "You have set your ${Skills.getSkillName(world, skill)} level to: $level!",
                type = ChatMessageType.CONSOLE,
            )
        } else {
            player.message("Could not find skill with identifier: ${values[0]}", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("item", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::item 4151 1</col> or <col=42C66C>::item 4151</col>",
    ) { values ->
        val item = values[0].toInt()
        val amount = if (values.size > 1) Math.min(Int.MAX_VALUE.toLong(), values[1].parseAmount()).toInt() else 1
        if (item < world.definitions.getCount(ItemDef::class.java)) {
            val def = world.definitions.get(ItemDef::class.java, Item(item).toUnnoted(world.definitions).id)
            val result = player.inventory.add(item = item, amount = amount, assureFullInsertion = false)
            player.message(
                "You have spawned <col=42C66C>${DecimalFormat().format(
                    result.completed,
                )} x ${def.name}</col></col> ($item).",
                type = ChatMessageType.CONSOLE,
            )
        } else {
            player.message("Item $item does not exist in cache.", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("give", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::give item_name amount, end with #n or #noted for noted spawn, replace (3) with .3 or (g) with .g</col>",
    ) { values ->
        val noted = values[0].endsWith(".noted") || values[0].endsWith(".n")
        val item =
            values[0]
                .replace("[", "")
                .replace("]", "")
                .replace("(", "")
                .replace(")", "")
                .replace(",", "'")
                .replace("_", " ")
                .replace(".6", " (6)")
                .replace(".5", " (5)")
                .replace(".4", " (4)")
                .replace(".3", " (3)")
                .replace(".2", " (2)")
                .replace(".1", " (1)")
                .replace(".e", " (e)")
                .replace(".i", " (i)")
                .replace(".g", " (g)")
                .replace(
                    ".or",
                    " (or)",
                ).replace(
                    ".sp",
                    " (sp)",
                ).replace(
                    ".t",
                    " (t)",
                ).replace(".u", " (u)")
                .replace(".unf", " (unf)")
                .replace(".noted", "")
                .replace(".n", "")
        val amount = if (values.size > 1) Math.min(Int.MAX_VALUE.toLong(), values[1].parseAmount()).toInt() else 1
        var foundItem = false
        val showDef = true
        for (i in 0..world.definitions.getCount(ItemDef::class.java)) {
            val def = world.definitions.getNullable(ItemDef::class.java, i)
            if (def != null) {
                if (def.name.lowercase() == item.lowercase() && !foundItem) {
                    val result =
                        player.inventory.add(
                            item = if (noted) def.noteLinkId else i,
                            amount = amount,
                            assureFullInsertion = false,
                        )
                    val s = StringBuilder()
                    s.append("You have spawned ")
                    if (amount > 1) {
                        s.append("<col=42C66C>${DecimalFormat().format(result.completed)}</col> x")
                    }
                    if (noted) {
                        s.append(" noted")
                    }
                    s.append("<col=42C66C> ${def.name}</col> (Id: <col=42C66C>$i</col>).")
                    player.message(
                        s.toString(),
                        type = ChatMessageType.CONSOLE,
                    )
                    if (showDef) {
                        val str = StringBuilder()
                        str.append("appearanceId: <col=42C66C>${def.appearanceId}</col> ")
                        str.append("maleWornModel: <col=42C66C>${def.maleWornModel}</col> ")
                        str.append("maleWornModel2: <col=42C66C>${def.maleWornModel2}</col><br> ")
                        str.append("equipSlot: <col=42C66C>${def.equipSlot}</col> ")
                        str.append("equipType: <col=42C66C>${def.equipType}</col> ")
                        str.append("cost: <col=42C66C>${def.cost}</col><br>")
                        player.message(
                            str.toString(),
                            type = ChatMessageType.CONSOLE,
                        )
                        for (j in 0 until def.inventoryMenu.size) {
                            if (def.inventoryMenu[j] == null) {
                                continue
                            }
                            player.message(
                                "Inventory option <col=42C66C>$j</col>: <col=42C66C>${def.inventoryMenu[j]}</col><br>",
                                type = ChatMessageType.CONSOLE,
                            )
                        }
                        for (k in 0 until def.groundMenu.size) {
                            if (def.groundMenu[k] == null) {
                                continue
                            }
                            player.message(
                                "Ground option <col=42C66C>$k</col>: <col=42C66C>${def.groundMenu[k]}</col><br>",
                                type = ChatMessageType.CONSOLE,
                            )
                        }
                    }
                    foundItem = true
                }
            }
        }
        if (!foundItem) {
            player.message("Item <col=e20f00>$item</col> does not exist in cache.", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("get_item_look", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::get_item_look item_name</col>",
    ) { values ->
        val itemName = values[0].replace("_", " ")
        var foundItem = false
        for (i in 0 until world.definitions.getCount(ItemDef::class.java)) {
            val def = world.definitions.getNullable(ItemDef::class.java, i)
            if (def != null && def.name.equals(itemName, ignoreCase = true)) {
                // Fetch appearanceId using getEquipId method
                val appearanceId = def.appearanceId

                // Displaying only appearanceId information
                player.message(
                    "AppearanceId for <col=42C66C>${def.name}</col> (Id: <col=42C66C>$i</col>) is <col=42C66C>$appearanceId</col>",
                    type = ChatMessageType.CONSOLE,
                )

                foundItem = true
                break
            }
        }
        if (!foundItem) {
            player.message("Item <col=e20f00>$itemName</col> does not exist in cache.", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("set_item_look", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::set_item_look item_id new_appearance_id</col>",
    ) { values ->
        if (values.size < 2) {
            player.message(
                "Invalid number of arguments. Usage: ::set_item_look item_id new_appearance_id",
                type = ChatMessageType.CONSOLE,
            )
            return@tryWithUsage
        }

        val itemId = values[0].toIntOrNull()
        val newAppearanceId = values[1].toIntOrNull()

        if (itemId == null || newAppearanceId == null) {
            player.message(
                "Invalid arguments. Both item_id and new_appearance_id must be integers.",
                type = ChatMessageType.CONSOLE,
            )
            return@tryWithUsage
        }

        val itemDef = world.definitions.getNullable(ItemDef::class.java, itemId)
        if (itemDef == null) {
            player.message("Item with ID <col=e20f00>$itemId</col> does not exist.", type = ChatMessageType.CONSOLE)
        } else {
            // Update the appearanceId
            itemDef.appearanceId = newAppearanceId
            player.message(
                "Updated appearanceId of item <col=42C66C>${itemDef.name}</col> (Id: <col=42C66C>$itemId</col>) to <col=42C66C>$newAppearanceId</col>",
                type = ChatMessageType.CONSOLE,
            )
        }
    }
}

on_command("food", Privilege.ADMIN_POWER) {
    player.inventory.add(item = Items.MANTA_RAY, amount = player.inventory.freeSlotCount)
}

on_command("varp", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::varp 173 1</col>") { values ->
        val varp = values[0].toInt()
        val state = values[1].toInt()
        val oldState = player.getVarp(varp)
        player.setVarp(varp, state)
        player.message(
            "Set varp (<col=42C66C>$varp</col>) from <col=42C66C>$oldState</col> to <col=42C66C>${
                player.getVarp(
                    varp,
                )
            }</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("varc", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::varc 173 1</col>") { values ->
        val varc = values[0].toInt()
        val state = values[1].toInt()
        val oldState = player.getVarc(varc)
        player.setVarc(varc, state)
        player.message(
            "Set varc (<col=42C66C>$varc</col>) from <col=42C66C>$oldState</col> to <col=42C66C>$state</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("object_varbit", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::object_varbit 8151</col>",
    ) { values ->
        val objectId = values[0].toInt()
        val objectDef = player.world.definitions.get(ObjectDef::class.java, objectId)
        val varbit = objectDef.varbit
        val varp =
            player.world.definitions
                .get(VarbitDef::class.java, varbit)
                .varp

        player.message(
            "Varbit for object <col=42C66C>$objectId</col>: <col=42C66C>$varbit</col> in varp <col=42C66C>$varp</col>.",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("varbit", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::varbit 5451 1</col>",
    ) { values ->
        val varbit = values[0].toInt()
        val state = values[1].toInt()
        val oldState = player.getVarbit(varbit)
        player.setVarbit(varbit, state)
        player.message(
            "Set varbit (<col=42C66C>$varbit</col>) from <col=42C66C>$oldState</col> to <col=42C66C>${
                player.getVarbit(
                    varbit,
                )
            }</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("getvarbit", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::getvarbit 5451</col>",
    ) { values ->
        val varbit = values[0].toInt()
        val state = player.getVarbit(varbit)
        player.message(
            "Get varbit (<col=42C66C>$varbit</col>): <col=42C66C>$state</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("getvarbits", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::getvarbits 83</col>",
    ) { values ->
        val varp = values[0].toInt()
        val varbits = mutableListOf<VarbitDef>()
        val totalVarbits = world.definitions.getCount(VarbitDef::class.java)
        for (i in 0 until totalVarbits) {
            val varbit = world.definitions.getNullable(VarbitDef::class.java, i)
            if (varbit?.varp == varp) {
                varbits.add(varbit)
            }
        }
        player.message("Varbits for varp <col=42C66C>$varp</col>:")
        varbits.forEach { varbit ->
            player.message(
                "  ${varbit.id} [bits ${varbit.startBit}-${varbit.endBit}] [current ${player.getVarbit(varbit.id)}]",
                type = ChatMessageType.CONSOLE,
            )
        }
    }
}

on_command("getvarp", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::getvarp 83</col>",
    ) { values ->
        val varp = values[0].toInt()
        val state = player.getVarp(varp)
        player.message(
            "Get varp (<col=42C66C>$varp</col>): <col=42C66C>$state</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("interface", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::interface 214</col>",
    ) { values ->
        val component = values[0].toInt()
        player.openInterface(component, InterfaceDestination.MAIN_SCREEN)
        player.message("Opening interface <col=42C66C>$component</col>", type = ChatMessageType.CONSOLE)
    }
}
on_command("componenttext", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::interface 214</col>",
    ) { values ->
        val interfaceId = values[0].toInt()
        val componentId = values[1].toInt()
        val text = values[2]
        player.setComponentText(interfaceId, componentId, text)
        player.message(
            "Set Text <col=42C66C>$text</col> to componentId:<col=42C66C>$componentId</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("clip", Privilege.ADMIN_POWER) {
    val chunk = world.chunks.getOrCreate(player.tile)
    val matrix = chunk.getMatrix(player.tile.height)
    val lx = player.tile.x % 8
    val lz = player.tile.z % 8
    player.message("Tile flags: ${chunk.getMatrix(player.tile.height).get(lx, lz)}")
    Direction.RS_ORDER.forEach { dir ->
        val walkBlocked = matrix.isBlocked(lx, lz, dir, projectile = false)
        val projectileBlocked = matrix.isBlocked(lx, lz, dir, projectile = true)
        val walkable = if (walkBlocked) "<col=42C66C>blocked</col>" else "<col=178000>walkable</col>"
        val projectile = if (projectileBlocked) "<col=42C66C>projectiles blocked" else "<col=178000>projectiles allowed"
        player.message("$dir: $walkable - $projectile", type = ChatMessageType.CONSOLE)
    }
}

on_command("equ", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::equ 3 19709</col>") { values ->
        val id = values[1].toInt()
        val slot = values[0].toInt()
        if (id < world.definitions.getCount(ItemDef::class.java)) {
            val def = world.definitions.get(ItemDef::class.java, Item(id).toUnnoted(world.definitions).id)
            player.equipment[slot] = Item(id)
            player.message(
                "You have equipped <col=42C66C>${def.name}</col> ($id) to equipment slot <col=42C66C>($slot)</col>.",
                type = ChatMessageType.CONSOLE,
            )
        } else {
            player.message("Item $id does not exist in cache.", type = ChatMessageType.CONSOLE)
        }
    }
}

on_command("hit", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::hit 10</col>") { values ->
        val damage = values[0].toInt()
        player.hit(damage = damage, type = HitType.MAGIC)
    }
}

on_command("bank", Privilege.ADMIN_POWER) {
    player.openBank()
}

on_command("shop", Privilege.ADMIN_POWER) {
    player.openShop("Edgeville General Store")
}

on_command("varps", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::varps 10 20 1</col>") {
        values ->
        val varpStart = values[0].toInt()
        val varpEnd = values[1].toInt()
        val value = values[2].toInt()

        for (i in varpStart..varpEnd) {
            try {
                val oldVarp = player.getVarp(i)
                player.setVarp(i, value)
                player.message(
                    "Set varp (<col=42C66C>$i</col>) from <col=42C66C>$oldVarp</col> to <col=42C66C>${
                        player.getVarp(
                            i,
                        )
                    }</col>",
                    type = ChatMessageType.CONSOLE,
                )
            } catch (_: Exception) {
                player.message("Failed to set varp $i", type = ChatMessageType.CONSOLE)
            }
        }
    }
}

on_command("varbits", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=42C66C>::varbits 10 20 1</col>") {
        values ->
        val varbitStart = values[0].toInt()
        val varbitEnd = values[1].toInt()
        val value = values[2].toInt()

        for (i in varbitStart..varbitEnd) {
            try {
                val oldVarbit = player.getVarbit(i)
                player.setVarbit(i, value)
                player.message(
                    "Set varbit (<col=42C66C>$i</col>) from <col=42C66C>$oldVarbit</col> to <col=42C66C>${
                        player.getVarbit(
                            i,
                        )
                    }</col>",
                    type = ChatMessageType.CONSOLE,
                )
            } catch (_: Exception) {
                player.message("Failed to set varbit $i", type = ChatMessageType.CONSOLE)
            }
        }
    }
}

on_command("getvarc", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::getvarc 83</col>",
    ) { values ->
        val varc = values[0].toInt()
        val state = player.getVarc(varc)
        player.message(
            "Get varc (<col=42C66C>$varc</col>): <col=42C66C>$state</col>",
            type = ChatMessageType.CONSOLE,
        )
    }
}

on_command("unlocksong", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(
        player,
        args,
        "Invalid format! Example of proper command <col=42C66C>::unlocksong 83</col>",
    ) { values ->
        val trackId = values[0].toInt()
        player.unlockSong(trackId)
    }
}

on_command("resettracks", Privilege.ADMIN_POWER) {
    world.getService(RegionMusicService::class.java)?.musicTrackVarps?.forEach {
        if (it == -1) return@forEach
        player.setVarp(it, 0)
    }
}

on_command("unlockalltracks", Privilege.ADMIN_POWER) {
    world.getService(RegionMusicService::class.java)?.musicTrackVarps?.forEach {
        if (it == -1) return@forEach
        player.setVarp(it, -1)
    }
}

fun displayKillCounts(
    player: Player,
    killCounts: Map<String, Int>,
    title: String,
) {
    // Open interface
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_FULL, interfaceId = 275)
    player.setComponentText(interfaceId = 275, component = 2, title)

    // Clear components for displaying data
    for (i in 16..315) {
        player.setComponentText(interfaceId = 275, component = i, "")
    }

    // Display kill counts
    killCounts.entries.forEachIndexed { index, (npcName, count) ->
        player.setComponentText(
            interfaceId = 275,
            component = 17 + index,
            "$npcName: ${NumberFormat.getNumberInstance().format(count)}",
        )
    }
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

fun getArgumentLine(
    args: Array<String>,
    offset: Int,
    length: Int,
): String {
    val sb = StringBuilder()
    for (i in offset until length) {
        if (i != offset) {
            sb.append(" ")
        }
        sb.append(args[i])
    }
    return sb.toString()
}

// Function to retrieve the player's area name based on region ID
fun getAreaName(player: Player): String {
    // Get the region ID of the player's current tile
    return when (player.tile.regionId) {
        // Begin Region Mapping
        9270 ->
            "Eagles' Peak"
        9526, 9782, 9525, 9781, 9780 ->
            "Tree Gnome Stronghold"
        10039 ->
            "Barbarian Outpost"
        10038, 10036, 10037, 10294 ->
            "Kandarin"
        10293 ->
            "Fishing Guild"
        9779, 10035 ->
            "West Ardougne"
        10291, 10547, 10292, 10548 ->
            "East Ardougne"
        10803 ->
            "Witchaven"
        10804 ->
            "Legends' Guild"
        10549 ->
            "Ranging Guild"
        10550 ->
            "McGrubor's Wood"
        10806, 10805 ->
            "Seers' Village"
        11061, 11062, 11317 ->
            "Catherby"
        10033 ->
            "Tree Gnome Village"
        10032, 10288 ->
            "Yanille"
        10545 ->
            "Port Khazard"
        10802, 10801, 11058, 11057, 11313, 11569, 10645, 10644, 10900, 10899, 10901 ->
            "Brimhaven"
        11056, 11312, 11568, 11055, 11311, 11567, 11823, 11054, 11566, 11822, 11053, 11309, 11565, 11821 ->
            "Karamja"
        11310 ->
            "Shilo Village"
        11318 ->
            "White Wolf Mountain"
        11319, 11575 ->
            "Burthorpe"
        11574, 11573 ->
            "Taverley"
        11830 ->
            "Near Goblin Village"
        12086 ->
            "Edgeville Monastery"
        12342 ->
            "Edgeville"
        12598 ->
            "Grand Exchange"
        12854 ->
            "Near Varrock Palace"
        13110 ->
            "Lumber Yard"
        11829 ->
            "North of Falador"
        11828, 12084, 11827 ->
            "Falador"
        12083 ->
            "Falador Farm"
        11570, 11826 ->
            "Rimmington"
        12082, 12081 ->
            "Port Sarim"
        11825, 11824 ->
            "Mudskipper Point"
        12337 ->
            "Wizards' Tower"
        12085 ->
            "Near Dwarven Mine"
        12341 ->
            "Barbarian Village"
        12597 ->
            "West Varrock"
        12853 ->
            "East Varrock"
        13109 ->
            "East of Varrock"
        12596 ->
            "Near Champions' Guild"
        12852 ->
            "South of Varrock"
        13108 ->
            "South east of Varrock"
        12593, 12849, 12693, 12949 ->
            "Lumbridge Swamp"
        12850, 12851, 12595, 12950, 13206 ->
            "Lumbridge"
        12594 ->
            "Behind Lumbridge Castle"
        12338, 12339 ->
            "Draynor Village"
        12340 ->
            "Near Draynor Manor"
        13107 ->
            "Al Kharid Mine"
        13363 ->
            "Mage Training Arena"
        13362 ->
            "Duel Arena"
        13106, 13105, 13361 ->
            "Al Kharid"
        12591, 12590, 12589, 12588, 12587, 12848, 12847, 12846, 12845, 12844, 12843, 13104, 13103, 13102, 13101, 13100,
        13099, 13355, 13356, 13357, 13358, 13359, 13360, 13872, 13871, 13870,
        ->
            "Kharidian Desert"
        13366, 13622 ->
            "Paterdomus"
        13623 ->
            "Slayer Tower"
        13878 ->
            "Canifis"
        13879, 13621, 13877, 13620, 13876, 13619, 13875, 13618, 13874, 13873, 14129, 14130, 14133, 14134, 14135, 14389,
        14390, 14391,
        ->
            "Morytania"
        14131, 14231 ->
            "Barrows"
        14385, 14386, 14387, 14388, 14132, 14642, 14644 ->
            "Meiyerditch"
        14647, 14646, 14645 ->
            "Port Phasmatys"
        11417, 11673, 11416, 11672, 11671, 11928 ->
            "Taverley Dungeon"
        11929, 12185, 12184, 12183, 11927 ->
            "Dwarven Mine"
        12444, 12443, 12442, 12186, 12441, 12698, 12954, 13210 ->
            "Edgeville Dungeon"
        11831, 11832, 11833, 11834, 11835, 11836, 11837, 12087, 12089, 12090, 12091, 12092, 12093, 12343, 12344, 12345,
        12346, 12347, 12348, 12349, 12599, 12600, 12601, 12602, 12603, 12604, 12605, 12855, 12856, 12857, 12858, 12859,
        12860, 12861, 13111, 13112, 13113, 13114, 13115, 13116, 13117, 13367, 13368, 13369, 13370, 13371, 13372, 13373,
        ->
            "the Wilderness"
        // End Region Mapping
        else -> "Unknown Area, Region ID: " + player.tile.regionId
        // if region ID returns any value not in this mapping, displays this string.
    }
}
