package gg.rsmod.plugins.content.objs

import gg.rsmod.plugins.content.skills.mining.PickaxeType
import gg.rsmod.plugins.api.Skills

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

fun demolish(objectId: Int, obj: GameObject) {
    val newObj = DynamicObject(objectId, obj.type, obj.rot, obj.tile)
    world.spawnTemporaryObject(newObj, 10)
}

fun success(player: Player, requestedSkill: Int): Boolean {
    return (player.getSkills().getCurrentLevel(requestedSkill) / 99.0) > Math.random()
}

fun clearRocks(p: Player, obj: GameObject): Boolean {
    val pick = PickaxeType.values.reversed().firstOrNull {
        p.getSkills()
            .getMaxLevel(Skills.MINING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item))
    }
    if (pick == null) {
        p.message("You need a pickaxe which you have the Mining level to use.")
        return false
    }
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.faceTile(obj.tile)
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
                    p.moveTo(obj.tile.x,obj.tile.z +13, 0)
                    p.unlock()
                    p.message("...you successfully clear the rock out of the way.")
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
        player.faceTile(obj.tile)
        clearRocks(player, obj)
    }
}

