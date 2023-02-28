package gg.rsmod.plugins.content.skills.woodcutting

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.drops.DropTableFactory


/**
 * @author Tom <rspsmods@gmail.com>
 */

val BIRD_NEST_DROP_SYNTH = 1997
val TREE_FALLING_SYNTH = 2734
val RESPAWN_TIMER_MAX_RANGE = 98

object Woodcutting {

    suspend fun chopDownTree(it: QueueTask, obj: GameObject, tree: TreeType) {
        val player = it.player
        if (!canChop(player, obj, tree)) {
            return
        }
        val axe = AxeType.values.reversed().firstOrNull {
            player.getSkills()
                .getMaxLevel(Skills.WOODCUTTING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                it.item
            ))
        }!!
        player.filterableMessage("You swing your hatchet at the tree.")
        while (canChop(player, obj, tree)) {
            player.animate(axe.animation)
            it.wait(2)
            val success = interpolate(
                (tree.lowChance * axe.ratio).toInt(),
                (tree.highChance * axe.ratio).toInt(),
                player.getSkills().getCurrentLevel(Skills.WOODCUTTING)
            ) > RANDOM.nextInt(255)
            if (success) {
                onSuccess(player, obj, tree, axe.item == Items.INFERNO_ADZE)
            }
            it.wait(2)
        }
        player.animate(-1)
    }

    private fun onSuccess(player: Player, obj: GameObject, tree: TreeType, infernoAdze: Boolean) {
        val logName = player.world.definitions.get(ItemDef::class.java, tree.log).name.pluralSuffix(2).lowercase()
        val triggerInfernoAdzeEffect = infernoAdze && RANDOM.nextInt(1, 3) == 1
        if (triggerInfernoAdzeEffect) {
            //TODO: Projectile - val INFERNO_ADZE_PROJECTILE_GRAPHIC = 1776
            player.addXp(Skills.WOODCUTTING, tree.xp)
            player.addXp(Skills.FIREMAKING, tree.xp)
            player.filterableMessage("You chop some $logName. The heat of the inferno adze incinerates them.")
        } else {
            player.filterableMessage("You get some $logName.")
            player.inventory.add(tree.log)
            player.addXp(Skills.WOODCUTTING, tree.xp)
        }
        if (player.world.random(256) == 1) {
            val nest = DropTableFactory.getDrop(player, 10_000) ?: return
            nest.forEach {
                val groundItem = GroundItem(it, player.findWesternTile(), player)
                groundItem.currentCycle = 150
                player.world.spawn(groundItem)
            }
            player.message("<col=FF0000>A bird's nest falls out of the tree.</col>")
            player.playSound(BIRD_NEST_DROP_SYNTH)
        }
        if (player.world.random(tree.depleteChance) == 0) {
            val treeStump = player.world.definitions.get(ObjectDef::class.java, obj.id).depleted
            if (treeStump != -1) {
                val world = player.world
                world.queue {
                    val trunk = DynamicObject(obj, treeStump)
                    val offsets = arrayOf(
                        Pair(-1, -1), Pair(0, -1), Pair(-1, 0), Pair(0, 0)
                    )
                    var canopy: GameObject? = null
                    for (offset in offsets) {
                        val tile = obj.tile.transform(offset.first, offset.second, 1)
                        val candidate = world.getObject(tile, ObjectType.INTERACTABLE)
                        if (candidate != null) {
                            canopy = candidate
                            break
                        }
                    }
                    if (canopy != null) {
                        world.remove(canopy)
                    }
                    world.remove(obj)
                    world.spawn(trunk)
                    val respawnTime =
                        if (tree == TreeType.TREE || tree == TreeType.ACHEY) world.random(tree.respawnTime..RESPAWN_TIMER_MAX_RANGE) else tree.respawnTime
                    wait(respawnTime)
                    world.remove(trunk)
                    world.spawn(DynamicObject(obj))
                    if (canopy != null) {
                        world.spawn(DynamicObject(canopy))
                    }
                }
            }
            player.animate(-1)
            player.playSound(TREE_FALLING_SYNTH)
        }
    }

    private fun canChop(player: Player, obj: GameObject, tree: TreeType): Boolean {
        if (!player.world.isSpawned(obj)) {
            return false
        }
        val axe = AxeType.values.reversed().firstOrNull {
            player.getSkills()
                .getMaxLevel(Skills.WOODCUTTING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                it.item
            ))
        }
        if (axe == null) {
            player.message("You do not have an axe which you have the woodcutting level to use.")
            return false
        }
        if (player.getSkills().getMaxLevel(Skills.WOODCUTTING) < tree.level) {
            player.message("You need a Woodcutting level of ${tree.level} to chop down this tree.")
            return false
        }
        if (player.inventory.isFull) {
            player.message("Your inventory is too full to hold any more logs.")
            return false
        }
        return true
    }

}