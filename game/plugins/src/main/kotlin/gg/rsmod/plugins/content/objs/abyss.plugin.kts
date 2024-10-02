package gg.rsmod.plugins.content.objs

import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.LostCity
import gg.rsmod.plugins.content.skills.mining.PickaxeType
import gg.rsmod.plugins.content.skills.woodcutting.AxeType

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

// Spawns a new dynamic object based on the given objectId and properties of the input obj.
fun demolish(
    objectId: Int,
    obj: GameObject,
) {
    val newObj = DynamicObject(objectId, obj.type, obj.rot, obj.tile)
    world.spawn(newObj)
}

// Determines if a player successfully performs an action based on their skill level.
fun success(
    p: Player,
    requestedSkill: Int,
): Boolean {
    return (p.skills.getCurrentLevel(requestedSkill) / 99.0) > Math.random()
}

// Handles the player clearing rocks using the mining skill.
fun clearRocks(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
): Boolean {
    val pick =
        PickaxeType.values.reversed().firstOrNull {
            p.skills
                .getMaxLevel(Skills.MINING) >= it.level &&
                (p.equipment.contains(it.item) || p.inventory.contains(it.item))
        }
    if (pick == null) {
        p.message("You need a pickaxe which you have the Mining level to use.")
        return false
    }
    // Save oldObj before removing it
    val tile = obj.tile
    val chunk = world.chunks.getOrCreate(tile)
    val oldObj =
        chunk.getEntities<GameObject>(tile, EntityType.STATIC_OBJECT, EntityType.DYNAMIC_OBJECT).firstOrNull {
            it.type ==
                obj.type
        }

    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile, 3)
                    p.message("You use your mining skill to mine the rock...")
                }
                2 -> p.animate(pick.animation)
                4 -> {
                    if (!success(p, Skills.MINING)) {
                        p.message("...but you fail to mine the rock")
                        p.unlock()
                        p.animate(-1)
                        break
                    }
                }
                in 5..7 -> {
                    demolish(7158 + (ticks - 5), obj)
                    wait(1)
                }
                9 -> {
                    p.moveTo(obj.tile.x + xOffset, obj.tile.z + zOffset, 0)
                    p.unlock()
                    p.message("...you successfully clear the rock out of the way.")
                    if (oldObj != null) {
                        world.spawn(DynamicObject(oldObj))
                    }
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

// Handles the player clearing tendrils using the woodcutting skill.
fun clearTendrils(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
): Boolean {
    val axe =
        AxeType.values.reversed().firstOrNull {
            p.skills
                .getMaxLevel(Skills.WOODCUTTING) >= it.level &&
                (
                    p.equipment.contains(it.item) ||
                        p.inventory.contains(
                            it.item,
                        )
                )
        }
    if (axe == null) {
        p.message("You need a hatchet which you have the Woodcutting level to use.")
        return false
    }
    // Save oldObj before removing it
    val tile = obj.tile
    val chunk = world.chunks.getOrCreate(tile)
    val oldObj =
        chunk.getEntities<GameObject>(tile, EntityType.STATIC_OBJECT, EntityType.DYNAMIC_OBJECT).firstOrNull {
            it.type ==
                obj.type
        }
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile, 3)
                    p.message("You use your woodcutting skill to clear the tendrils...")
                }
                2 -> p.animate(axe.animation)
                3 -> {
                    if (!success(p, Skills.WOODCUTTING)) {
                        p.message("...but you fail to clear the tendrils.")
                        p.unlock()
                        p.animate(-1)
                        break
                    }
                }
                in 4..6 -> {
                    demolish(7161 + (ticks - 4), obj)
                    wait(1)
                }
                7 -> {
                    p.moveTo(obj.tile.x + xOffset, obj.tile.z + zOffset)
                    p.unlock()
                    p.message("...you successfully clear the tendrils out of the way.")
                    if (oldObj != null) {
                        world.spawn(DynamicObject(oldObj))
                    }
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

// Handles the player distracting eyes using the thieving skill.
fun clearEyes(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
): Boolean {
    // Save oldObj before removing it
    val tile = obj.tile
    val chunk = world.chunks.getOrCreate(tile)
    val oldObj =
        chunk.getEntities<GameObject>(tile, EntityType.STATIC_OBJECT, EntityType.DYNAMIC_OBJECT).firstOrNull {
            it.type ==
                obj.type
        }
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile, 3)
                    p.message("You use your thieving skills to distract the eyes...")
                }

                2 -> p.animate(866)
                3 -> {
                    if (!success(p, Skills.THIEVING)) {
                        p.message("...but you fail to distract the eyes.")
                        p.unlock()
                        p.animate(-1)
                        break
                    }
                }
                in 4..6 -> {
                    demolish(7168 + (ticks - 4), obj)
                    wait(1)
                }
                7 -> {
                    p.moveTo(obj.tile.x + xOffset, obj.tile.z + zOffset)
                    p.unlock()
                    p.message("...you successfully distract the eyes.")
                    if (oldObj != null) {
                        world.spawn(DynamicObject(oldObj))
                    }
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

// Handles the player squeezing through gaps using the agility skill.
fun clearGap(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
    quick: Boolean,
) {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile, 3, 2)
                }
                3 -> {
                    p.animate(844)
                    if (!quick) {
                        if (!success(p, Skills.AGILITY)) {
                            p.message("You cannot seem to slip through the gap.")
                            p.unlock()
                            p.animate(-1)
                            break
                        }
                    }
                }
                4 -> {
                    p.moveTo(obj.tile.x + xOffset, obj.tile.z + zOffset)
                    p.unlock()
                    p.message("...you manage to slip through the gap..")
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
}

// Handles the player burning boils using the firemaking skill.
fun burnBoil(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
): Boolean {
    if (!p.inventory.contains(Items.TINDERBOX_590)) {
        p.message("You need a tinderbox in order to burn the boil.")
        return false
    }
    // Save oldObj before removing it
    val tile = obj.tile
    val chunk = world.chunks.getOrCreate(tile)
    val oldObj =
        chunk.getEntities<GameObject>(tile, EntityType.STATIC_OBJECT, EntityType.DYNAMIC_OBJECT).firstOrNull {
            it.type ==
                obj.type
        }
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile)
                    p.message("You use your firemaking skill to burn the boil...")
                }
                2 -> p.animate(733)
                3 -> {
                    if (!success(p, Skills.THIEVING)) {
                        p.message("...but you fail to burn the boil.")
                        p.unlock()
                        p.animate(-1)
                        break
                    }
                }
                in 4..6 -> {
                    demolish(7165 + (ticks - 4), obj)
                    wait(1)
                }
                7 -> {
                    p.moveTo(obj.tile.x + xOffset, obj.tile.z + zOffset)
                    p.unlock()
                    p.message("...you successfully pop the boil.")
                    if (oldObj != null) {
                        world.spawn(DynamicObject(oldObj))
                    }
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

on_obj_option(obj = Objs.ROCK_7158, option = "mine", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3)
        when (obj.tile.x) {
            3026 -> clearRocks(player, obj, 3, 10)
            3049 -> clearRocks(player, obj, 0, 9)
        }
    }
}

on_obj_option(obj = Objs.TENDRILS, option = "chop", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3)
        when (obj.tile.x) {
            3018 -> clearTendrils(player, obj, 11, 0)
            3057 -> clearTendrils(player, obj, -9, 0)
        }
    }
}

on_obj_option(obj = Objs.EYES, option = "distract", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3)
        when (obj.tile.x) {
            3058 -> clearEyes(player, obj, -7, 0)
            3021 -> clearEyes(player, obj, 8, 0)
        }
    }
}

on_obj_option(obj = Objs.PASSAGE_7154, option = "go-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 2)
        when (obj.tile.x) {
            3038 -> clearGap(player, obj, 2, -8, true)
        }
    }
}

on_obj_option(obj = Objs.GAP_7164, option = "squeeze-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 2)
        when (obj.tile.x) {
            3028 -> clearGap(player, obj, 2, -6, true)
            3049 -> clearGap(player, obj, 1, -9, true)
        }
    }
}

on_obj_option(obj = Objs.BOIL, option = "burn-down", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 2)
        when (obj.tile.x) {
            3060 -> burnBoil(player, obj, -6, 1)
            3018 -> burnBoil(player, obj, 6, 1)
        }
    }
}

on_obj_option(obj = Objs.LAW_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3049 -> player.moveTo(2466, 4831)
        }
    }
}

on_obj_option(obj = Objs.CHAOS_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3044 -> player.moveTo(2273, 4841)
        }
    }
}

on_obj_option(obj = Objs.NATURE_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3035 -> player.moveTo(2398, 4842)
        }
    }
}

on_obj_option(obj = Objs.COSMIC_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (player.finishedQuest(LostCity))
        {
            if (obj.isSpawned(world)) {
                player.faceTile(obj.tile)
                when (obj.tile.x) {
                    3028 -> player.moveTo(2144, 4831)
                }
            }
        } else {
        player.message("You need to have completed <col=0000ff>Lost City</col> to use this rift.")
    }
}

on_obj_option(obj = Objs.BLOOD_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3027 -> player.moveTo(2468, 4889, 1)
        }
    }
}

on_obj_option(obj = Objs.FIRE_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3029 -> player.moveTo(2574, 4848)
        }
    }
}

on_obj_option(obj = Objs.EARTH_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3031 -> player.moveTo(2655, 4830)
        }
    }
}

on_obj_option(obj = Objs.BODY_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3039 -> player.moveTo(2521, 4834)
        }
    }
}

on_obj_option(obj = Objs.MIND_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3044 -> player.moveTo(2793, 4828)
        }
    }
}

on_obj_option(obj = Objs.AIR_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3047 -> player.moveTo(2841, 4829)
        }
    }
}

on_obj_option(obj = Objs.SOUL_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3050 -> player.message("A strange power blocks your entrance.")
        }
    }
}

on_obj_option(obj = Objs.WATER_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3051 -> player.moveTo(2725, 4832)
        }
    }
}

on_obj_option(obj = Objs.DEATH_RIFT, option = "exit-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        when (obj.tile.x) {
            3050 -> player.moveTo(2208, 4830)
        }
    }
}
