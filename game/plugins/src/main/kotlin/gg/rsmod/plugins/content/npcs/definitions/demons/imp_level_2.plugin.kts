package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import kotlin.random.Random
import kotlin.random.nextInt

val ids = intArrayOf(Npcs.IMP, Npcs.IMP_709)

// Constants
val TELEPORT_TIMER = TimerKey()
val DELAY = 50..100
val GRAPHIC_ID = 74
val MAX_TILE_ATTEMPTS = 3
val TILE_RANGE = -10..10

// Event handler for when a global NPC spawns.
on_global_npc_spawn {
    // Check if the spawned NPC is an IMP
    if (npc.id == Npcs.IMP || npc.id == Npcs.IMP_709) {
        // If it is, set a teleport timer with a random delay for this NPC.
        npc.timers[TELEPORT_TIMER] = world.random(DELAY)
    }
}

// Event handler for when the teleport timer triggers.
on_timer(TELEPORT_TIMER) {
    // If the NPC is not alive, exit the function.
    if (!npc.isAlive()) return@on_timer

    // Retrieve the region ID of the NPC's current tile.
    val npcRegion = npc.tile.regionId

    // Try to find a valid tile within the same region for teleportation.
    val randomTile = findValidTile(npc, npcRegion)

    // If a valid tile is found and the NPC is not locked, proceed with teleportation.
    if (randomTile != null && npc.lock.canMove()) {
        npc.queue {
            // Spawn a graphic effect at the NPC's current position.
            world.spawn(TileGraphic(tile = npc.tile, id = GRAPHIC_ID, height = 0))

            // Teleport the NPC to the new valid tile.
            npc.teleportNpc(randomTile)

            // Wait for one game tick.
            wait(1)

            // Spawn a graphic effect at the NPC's new position post-teleportation.
            world.spawn(TileGraphic(tile = npc.tile, id = GRAPHIC_ID, height = 0))
        }
    } else {
        // If teleportation failed, handle the failure (logging or other actions).
    }

    // Reset the teleport timer with a new random delay.
    npc.timers[TELEPORT_TIMER] = DELAY.random()
}

// Function to find a valid tile for NPC teleportation, ensuring it's within the same region and not clipped.
fun findValidTile(
    npc: Npc,
    npcRegion: Int,
): Tile? {
    // Attempt to find a valid tile up to the maximum number of defined attempts.
    repeat(MAX_TILE_ATTEMPTS) {
        // Generate a random tile within the defined range.
        val randomTile =
            npc.tile.transform(
                x = Random.nextInt(TILE_RANGE),
                z = Random.nextInt(TILE_RANGE),
            )
        // Check if the tile is valid (not clipped and within the same region).
        if (!world.collision.isClipped(randomTile) && randomTile.regionId == npcRegion) {
            return randomTile // Return the valid tile.
        }
    }
    return null // Return null if no valid tile was found within the attempts.
}

// Function to handle cases when teleportation fails.
fun handleTeleportFailure(
    npc: Npc,
    tile: Tile?,
) {
}

val table = DropTableFactory
val imp =
    table.build {
        guaranteed {
            obj(Items.IMPIOUS_ASHES)
        }

        main {
            total(1024)
            obj(Items.BLACK_BEAD, slots = 40)
            obj(Items.RED_BEAD, slots = 40)
            obj(Items.WHITE_BEAD, slots = 40)
            obj(Items.YELLOW_BEAD, slots = 40)

            obj(Items.BRONZE_BOLTS, slots = 64)
            obj(Items.WIZARD_HAT, quantity = 1, slots = 64)

            obj(Items.EGG, slots = 47)
            obj(Items.RAW_CHICKEN, slots = 47)
            obj(Items.BURNT_BREAD, slots = 39)
            obj(Items.BURNT_MEAT, slots = 39)
            obj(Items.CABBAGE, slots = 16)
            obj(Items.BREAD_DOUGH, slots = 16)
            obj(Items.BREAD, slots = 8)
            obj(Items.COOKED_MEAT, slots = 8)

            obj(Items.HAMMER, slots = 47)
            obj(Items.TINDERBOX_590, slots = 64)
            obj(Items.SHEARS, slots = 39)
            obj(Items.BUCKET, slots = 32)
            obj(Items.BUCKET_OF_WATER, slots = 16)
            obj(Items.JUG, slots = 16)
            obj(Items.JUG_OF_WATER, slots = 16)
            obj(Items.EMPTY_POT, slots = 16)
            obj(Items.POT_OF_FLOUR, slots = 16)

            obj(Items.BALL_OF_WOOL, quantity = 1, slots = 47)
            obj(Items.MIND_TALISMAN, quantity = 1, slots = 55)
            obj(Items.ASHES, quantity = 1, slots = 48)
            obj(Items.CLAY, quantity = 1, slots = 32)
            obj(Items.CADAVA_BERRIES, quantity = 1, slots = 32)
            obj(Items.GRAIN, quantity = 1, slots = 24)
            obj(Items.CHEFS_HAT, quantity = 1, slots = 16)
        }
    }

table.register(imp, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.IMP_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 80
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -42
            attackCrush = -37
            defenceStab = -42
            defenceSlash = -42
            defenceCrush = -42
            defenceMagic = -42
            defenceRanged = -42
        }
        anims {
            attack = 169
            death = 172
            block = 170
        }
    }
}
