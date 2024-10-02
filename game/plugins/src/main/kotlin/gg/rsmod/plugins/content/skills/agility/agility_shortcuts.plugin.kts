package gg.rsmod.plugins.content.skills.agility

/**
 * @author Harley <github.com/HarleyGilpin>
 * Created: 1/20/24
 * Modified: 2/10/24
 *
 * Plugin Description:
 * This plugin features agility shortcuts that enable players to quickly overcome obstacles using their agility skill,
 * thereby facilitating swifter travel throughout the game world.
 */

/**
 * Shortcut Objects
 */
private val STILE_OBJECTS =
    intArrayOf(
        Objs.STILE,
        Objs.STILE_7527,
        Objs.STILE_29460,
        Objs.STILE_45205,
        Objs.STILE_34776,
        Objs.STILE_48208,
    )

/**
 * Shortcut Animations
 */

private val climbStile = 1560

/**
 * Location: Edgeville Dungeon Monkeybars
 * Agility Requirement: 15
 */

/**
 * TODO: Remove Monkey bar swing delay once a forceMove function run movement speed is implemented.
 * Once running speed has been added to the function set the start client duration for movements 2-5 to 0.
 * Then set the flag to enable running in the forceMove. Crossing the monkey bars should take no longer than 6 cycles.
 */

on_obj_option(obj = Objs.MONKEY_BARS_29375, option = 1) {
    if (player.skills.getCurrentLevel(Skills.AGILITY) < 15) {
        player.message("You need an agility level of 15 to use this obstacle.")
        return@on_obj_option
    }

    val obj = player.getInteractingGameObj()
    val isNorth = player.tile.z > obj.tile.z
    val offsetZ = if (isNorth) -1 else 1
    val monkeyBarsStartTile = Tile(obj.tile.x + 1, obj.tile.z)
    val monkeyBarsEndTile = Tile(obj.tile.x + 1, obj.tile.z + 5 * offsetZ)

    player.lockingQueue {
        if (player.tile != monkeyBarsStartTile) {
            val distance = player.tile.getDistance(monkeyBarsStartTile)
            player.walkTo(monkeyBarsStartTile)
            wait(distance + 2)
        } else {
            wait(2)
        }

        player.faceTile(Tile(obj.tile.x + 1, obj.tile.z + offsetZ))
        wait(1)

        // Loop for forced movement
        for (i in 1..5) {
            player.animate(if (i == 1) 742 else 744)
            player.queue {
                val move =
                    ForcedMovement.of(
                        player.tile,
                        Tile(obj.tile.x + 1, obj.tile.z + i * offsetZ),
                        clientDuration1 = if (i == 1) 25 else 25,
                        clientDuration2 = 60,
                        directionAngle = if (isNorth) Direction.SOUTH.ordinal else Direction.NORTH.ordinal,
                        lockState = LockState.NONE,
                    )
                player.forceMove(this, Animation(id = 745), move)
            }
            wait(1)
        }
        player.animate(744)
        waitTile(monkeyBarsEndTile)
        player.animate(743)
        player.addXp(Skills.AGILITY, 20.0)
    }
}

/**
 * Location: Stile at Fred the Farmer's sheep field and the stile at Falador cabbage patch.
 * Agility Requirement: None
 * Experience: 3
 * Notes: These shortcuts are available to free players, but, obviously, yield them no Agility experience.
 */

fun climbOverStile(
    player: Player,
    stile: GameObject,
) {
    val (startTile, targetTile, directionAngle) =
        when (stile.rot) {
            0 -> {
                val isNorth = player.tile.z >= stile.tile.z
                val startTile =
                    if (isNorth) {
                        Tile(stile.tile.x, stile.tile.z + 2)
                    } else {
                        Tile(
                            stile.tile.x,
                            stile.tile.z - 1,
                        )
                    }
                val targetTile =
                    if (isNorth) {
                        Tile(stile.tile.x, stile.tile.z - 1)
                    } else {
                        Tile(
                            stile.tile.x,
                            stile.tile.z + 2,
                        )
                    }
                val direction = if (isNorth) Direction.SOUTH.ordinal else Direction.NORTH.ordinal
                Triple(startTile, targetTile, direction)
            }

            1 -> {
                val isEast = player.tile.x >= stile.tile.x
                val startTile =
                    if (isEast) {
                        Tile(
                            stile.tile.x + 2,
                            stile.tile.z,
                        )
                    } else {
                        Tile(stile.tile.x - 1, stile.tile.z)
                    }
                val targetTile =
                    if (isEast) {
                        Tile(
                            stile.tile.x - 1,
                            stile.tile.z,
                        )
                    } else {
                        Tile(stile.tile.x + 2, stile.tile.z)
                    }
                val direction = if (isEast) Direction.WEST.ordinal else Direction.EAST.ordinal
                Triple(startTile, targetTile, direction)
            }

            2 -> {
                val isSouth = player.tile.z <= stile.tile.z
                val startTile =
                    if (isSouth) {
                        Tile(stile.tile.x, stile.tile.z - 1)
                    } else {
                        Tile(
                            stile.tile.x,
                            stile.tile.z + 2,
                        )
                    }
                val targetTile =
                    if (isSouth) {
                        Tile(stile.tile.x, stile.tile.z + 2)
                    } else {
                        Tile(
                            stile.tile.x,
                            stile.tile.z - 1,
                        )
                    }
                val direction = if (isSouth) Direction.NORTH.ordinal else Direction.SOUTH.ordinal
                Triple(startTile, targetTile, direction)
            }

            3 -> {
                val isWest = player.tile.x <= stile.tile.x
                val startTile =
                    if (isWest) {
                        Tile(
                            stile.tile.x - 1,
                            stile.tile.z,
                        )
                    } else {
                        Tile(stile.tile.x + 2, stile.tile.z)
                    }
                val targetTile =
                    if (isWest) {
                        Tile(
                            stile.tile.x + 2,
                            stile.tile.z,
                        )
                    } else {
                        Tile(stile.tile.x - 1, stile.tile.z)
                    }
                val direction = if (isWest) Direction.EAST.ordinal else Direction.WEST.ordinal
                Triple(startTile, targetTile, direction)
            }

            else -> Triple(player.tile, player.tile, Direction.NORTH.ordinal)
        }
    player.climbOverStileMovement(startTile, targetTile, directionAngle)
}

fun Player.climbOverStileMovement(
    startTile: Tile,
    targetTile: Tile,
    directionAngle: Int,
) {
    queue {
        if (tile != startTile) {
            walkTo(startTile)
            val distance = tile.getDistance(startTile)
            wait(distance + 2)
            faceTile(targetTile)
        }

        val move =
            ForcedMovement.of(
                startTile,
                targetTile,
                clientDuration1 = 0,
                clientDuration2 = 90,
                directionAngle = directionAngle,
                lockState = LockState.NONE,
            )
        wait(2)
        playSound(Sfx.CLIMB_WALL)
        animate(climbStile)
        forceMove(this, move)
    }
}

STILE_OBJECTS.forEach { stile ->
    on_obj_option(obj = stile, option = "Climb-over") {
        val obj = player.getInteractingGameObj()
        player.lockingQueue(lockState = LockState.FULL) {
            climbOverStile(player, obj)
            wait(2)
            player.filterableMessage("You climb-over the stile.")
            player.addXp(Skills.AGILITY, 3.0)
        }
    }
}

/**
 * Location: Falador Agility Shortcut.
 * Agility Requirement: 5
 * Experience: 0.5
 * Notes: Right next to the Falador west bank. Provides easy access to the areas south of Taverley.
 */

/**
 * Location: (Grapple) Over the River Lum to Al Kharid
 * Agility Requirement: 8
 * Experience: N/A
 * Notes: Players must have 19 Strength and 37 Ranged.
 */

/**
 * Location: (Grapple) Scale Falador Wall
 * Agility Requirement: 11
 * Experience: N/A
 * Notes: Players must have level 37 Strength and 19 Ranged.
 */

/**
 * Location: Varrock south fence jump.
 * Agility Requirement: 13
 * Experience: 0
 * Notes: Provides a shortcut to and from South Varrock.
 */

/**
 * Location: Yanille Agility Shortcut.
 * Agility Requirement: 16
 * Experience: 0
 * Notes: Near the Watchtower. Gives easy access to the west parts of Yanille from the teleport point.
 */

/**
 * Location: Coal Truck log balance.
 * Agility Requirement: 20
 * Experience: 8.5
 * Notes: Near the Coal Trucks west of Seers' Village. Provides easier access between the Mining spot and the pickup location
 *        near Seers' Village.
 */

/**
 * Location: Grand Exchange underwall tunnel shortcut.
 * Agility Requirement: 21
 * Experience: 0
 * Notes: Grants easier access to Edgeville. Also means access to Grand Exchange is far easier from the nearby fairy ring
 *        and canoe. (It is known as 'Varrock agility shortcut' in the game guide.)
 */

/**
 * Location: Falador Agility Shortcut.
 * Agility Requirement: 26
 * Experience: 0
 * Notes: South-west corner of Falador. Provides easier travel between the Falador west bank and Rimmington, as well as
 *        the Crafting Guild.
 */

/**
 * Location: Draynor Manor log balance to Champions' Guild.
 * Agility Requirement: 31
 * Experience: 3 or 1 (if failed)
 * Notes: Near Draynor Manor. Allows you to cross the River Lum between the Manor and Varrock. There's a chance of taking
 *        a few points of damage when using this shortcut.
 */

/**
 * Location: (Grapple) Scale Catherby Cliff
 * Agility Requirement: 32
 * Experience: N/A
 * Notes: Players must have level 35 Strength and 35 Ranged and have completed the Fishing Contest quest to use this shortcut.
 */

/**
 * Location: Ardougne log balance shortcut.
 * Agility Requirement: 33
 * Experience: 4 or 2 (if failed)
 * Notes: Allows easy passage across the river. There's a chance of taking a few points of damage when using this shortcut.
 */

/**
 * Location: (Grapple) Escape from the Water Obelisk Island
 * Agility Requirement: 36
 * Experience: 15
 * Notes: Players must have level 22 Strength and 39 Ranged.
 */

/**
 * Location: Gnome Stronghold shortcut.
 * Agility Requirement: 37
 * Experience: 0
 * Notes: The Grand Tree and Tree Gnome Village quests needed. North-east of the Grand Tree. Allows easy access to Barbarian
 *        Outpost and the Lighthouse.
 */

/**
 * Location: Al Kharid mining pit cliff side scramble
 * Agility Requirement: 38
 * Experience: 0
 * Notes: This shortcut is at the north end of the mine, and is useful for exiting to go north instead of going all the
 *        way around from the southern end.
 */

/**
 * Location: (Grapple) Scale Yanille Wall
 * Agility Requirement: 39
 * Experience: N/A
 * Notes: Players must have level 38 Strength and 21 Ranged.
 */

/**
 * Location: Trollheim, easy cliffside scramble.
 * Agility Requirement: 41
 * Experience: 0
 * Notes: Allows easier travel up and down the lower path of the west side of the hill near the Troll Stronghold.
 */

/**
 * Location: Dwarven mine, narrow crevice.
 * Agility Requirement: 42
 * Experience: 0
 * Notes: Provides a shortcut between the two arms of the mine.
 */

/**
 * Location: Trollheim, medium cliffside scramble.
 * Agility Requirement: 43
 * Experience: 0
 * Notes: Allows easier travel up and down the higher paths of the west side of the hill near the Troll Stronghold.
 */

/**
 * Location: Trollheim, advanced cliffside scramble.
 * Agility Requirement: 44
 * Experience: 0
 * Notes: Allows easier travel up and down the east side of the hill near the Troll Stronghold.
 *        This is useful to avoid the Troll Throwers, on the north side of the hill.
 */

/**
 * Location: Cosmic Temple, medium narrow walkway.
 * Agility Requirement: 46
 * Experience: 10 or 6 (on failure)
 * Notes: Provides faster travel between the bank and the Cosmic Altar at Zanaris. If you fail, you take a small amount
 *        of damage, but you still pass through.
 */

/**
 * Location: Trollheim, hard cliffside scramble.
 * Agility Requirement: 47
 * Experience: 8
 * Notes: Allows easier travel up and down the higher paths of the west side of the hill near the Troll Stronghold.
 */

/**
 * Location: Log balance to Fremennik Province.
 * Agility Requirement: 48
 * Experience: 0
 * Notes: North of Camelot. Provides slightly faster travel to Eastern Fremennik Province. There's a chance of taking a
 *        few points of damage when using this shortcut.
 */

/**
 * Location: Pipe from Edgeville dungeon to Varrock sewers
 * Agility Requirement: 51
 * Experience: 10
 * Notes: Allows passage between the eastern part of the Edgeville Dungeon and the Moss giants in the Varrock Sewers.
 *        There's a chance of taking a few points of damage when using this shortcut.
 */
on_obj_option(obj = Objs.PIPE_29370, option = 1) {
    if (player.skills.getCurrentLevel(Skills.AGILITY) < 53) {
        player.message("You need an agility level of 53 to use this obstacle.")
        return@on_obj_option
    }
    val obj = player.getInteractingGameObj()
    val isWest = player.tile.x <= obj.tile.x
    player.lockingQueue {
        val pipeStartTile = if (isWest) Tile(obj.tile.x - 1, obj.tile.z) else Tile(obj.tile.x + 2, obj.tile.z)
        if (player.tile != pipeStartTile) {
            val distance = player.tile.getDistance(pipeStartTile)
            player.walkTo(pipeStartTile)
            wait(distance + 2)
            player.faceTile(obj.tile)
        } else {
            wait(2)
        }
        player.filterableMessage("You squeeze into the pipe...")
        player.animate(12457)
        val move =
            if (isWest) {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x + 2, obj.tile.z),
                    clientDuration1 = 0,
                    clientDuration2 = 62,
                    directionAngle = Direction.EAST.ordinal,
                    lockState = LockState.NONE,
                )
            } else {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x, obj.tile.z),
                    clientDuration1 = 0,
                    clientDuration2 = 62,
                    directionAngle = Direction.WEST.ordinal,
                    lockState = LockState.NONE,
                )
            }

        player.forceMove(this, move)
        wait(2)
        val move2 =
            if (isWest) {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x + 4, obj.tile.z),
                    clientDuration1 = 10,
                    clientDuration2 = 70,
                    directionAngle = Direction.EAST.ordinal,
                    lockState = LockState.NONE,
                )
            } else {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x - 2, obj.tile.z),
                    clientDuration1 = 10,
                    clientDuration2 = 70,
                    directionAngle = Direction.WEST.ordinal,
                    lockState = LockState.NONE,
                )
            }
        player.forceMove(this, move2)
        wait(2)
        player.animate(12458)
        val move3 =
            if (isWest) {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x + 5, obj.tile.z),
                    clientDuration1 = 20,
                    clientDuration2 = 70,
                    directionAngle = Direction.EAST.ordinal,
                    lockState = LockState.NONE,
                )
            } else {
                ForcedMovement.of(
                    player.tile,
                    Tile(obj.tile.x - 4, obj.tile.z),
                    clientDuration1 = 20,
                    clientDuration2 = 70,
                    directionAngle = Direction.WEST.ordinal,
                    lockState = LockState.NONE,
                )
            }
        player.forceMove(this, move3)
        wait(2)
        player.filterableMessage("You pulled yourself through the pipes.")
        player.addXp(Skills.AGILITY, 7.5)
    }
}

/**
 * Location: (Grapple) Karamja Volcano
 * Agility Requirement: 53
 * Experience: N/A
 * Notes: Players must have level 21 Strength and 42 Ranged. Provides easier travel between the Karamja Volcano area and
 *        lower Karamja.
 */

/**
 * Location: Port Phasmatys, Ectopool shortcut.
 * Agility Requirement: 58
 * Experience: 0
 * Notes: Provides faster travel down to the pool of slime.
 */

/**
 * Location: Elven Overpass (Arandar), easy cliffside scramble.
 * Agility Requirement: 59
 * Experience: 0
 * Notes: Helps when using the elven shortcut to Tirannwn.
 */

/**
 * Location: Slayer tower, medium spiked chain climb.
 * Agility Requirement: 61
 * Experience: 0
 * Notes: Shortcut between Crawling Hand area and Aberrant spectre area on the 2nd floor. Nose peg or Slayer helmet
 *        required due to Aberrant spectres. There's a chance of taking a few points of damage when using this shortcut.
 */

/**
 * Location: Fremennik Slayer Dungeon, narrow crevice.
 * Agility Requirement: 62
 * Experience: 0
 * Notes: Shortcut between Basilisk area and Turoth area.
 */

/**
 * Location: Trollheim Wilderness Route.
 * Agility Requirement: 64
 * Experience: 0
 * Notes: Shortcut from Trollheim to level 20 Wilderness. One way.
 */

/**
 * Location: Temple on the Salve to Morytania shortcut.
 * Agility Requirement: 65
 * Experience: 0
 * Notes: Slightly faster way to get from east Varrock to Canifis, bypassing the underground route at the Temple.
 */

/**
 * Location: Cosmic Temple, advanced narrow walkway.
 * Agility Requirement: 66
 * Experience: 10
 * Notes: Much faster travel between the bank and the Cosmic Altar at Zanaris. If you fail, you take a small amount of
 *        damage, but you still pass through.
 */

/**
 * Location: Elven Overpass (Arandar, medium cliffside scramble.
 * Agility Requirement: 68
 * Experience: 0
 * Notes: Helps when using the elven shortcut to Tirannwn.
 */

/**
 * Location: Taverley Dungeon pipe squeeze to blue dragon lair.
 * Agility Requirement: 70
 * Experience: 10
 * Notes: Quick passage between the entrance and the Blue dragon area. Bypasses need for the Dusty key. There's a chance
 *        of taking a few points of damage when using this shortcut.
 */

/**
 * Location: (Grapple) Cross cave, south of Dorgesh-Kaan.
 * Agility Requirement: 70
 * Experience: N/A
 * Notes: No additional notes
 */

/**
 * Location: Slayer Tower, advanced spiked chain climb.
 * Agility Requirement: 71
 * Experience: 0
 * Notes: Shortcut between Infernal Mages area on the 2nd floor and Nechryael area on the 3rd floor. There's a chance of
 *        taking a few points of damage when using this shortcut.
 */

/**
 * Location: Shilo Village, stepping stones over the river.
 * Agility Requirement: 74
 * Experience: 0
 * Notes: Shortcut allows you to cross the river in Shilo Village, making it useful for fly-fishing. Requires completion
 *        of Medium Karamja Diary.
 */

/**
 * Location: Taverley Dungeon spiked blades jump.
 * Agility Requirement: 80
 * Experience: 12.5
 * Notes: Shortcut between the entrance and the Poison spider area. Bypasses need for the Dusty key.
 */

/**
 * Location: Fremennik Slayer Dungeon chasm jump.
 * Agility Requirement: 81
 * Experience: 5 x 2
 * Notes: No additional notes
 */

/**
 * Location: Elven Overpass (Arandar), advanced cliffside scramble.
 * Agility Requirement: 85
 * Experience: 0
 * Notes: Helps when using the elven shortcut to Tirannwn.
 */

/**
 * Location: Kuradal's Dungeon wall climb.
 * Agility Requirement: 86
 * Experience: 0
 * Notes: No additional notes
 */

/**
 * Location: Kuradal's Dungeon wall run.
 * Agility Requirement: 90
 * Experience: 0
 * Notes: No additional notes
 */
