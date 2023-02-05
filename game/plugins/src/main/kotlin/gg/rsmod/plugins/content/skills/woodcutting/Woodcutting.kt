package gg.rsmod.plugins.content.skills.woodcutting

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.Tile
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
object Woodcutting {

    data class Tree(val type: TreeType, val obj: Int)

    val treeStumps: MutableMap<Int, Int> = HashMap()

    suspend fun chopDownTree(it: QueueTask, obj: GameObject, tree: TreeType) {
        val player = it.player

        if (!canChop(player, obj, tree)) {
            return
        }

        val logName = player.world.definitions.get(ItemDef::class.java, tree.log).name
        val axe = AxeType.values.reversed().firstOrNull { player.getSkills().getMaxLevel(Skills.WOODCUTTING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(it.item)) }!!

        val infernoAdze = axe.item == Items.INFERNO_ADZE
        player.filterableMessage("You swing your hatchet at the tree.")
        while (true) {
            player.animate(axe.animation)
            it.wait(2)

            if (!canChop(player, obj, tree)) {
                player.animate(-1)
                break
            }

            val level = player.getSkills().getCurrentLevel(Skills.WOODCUTTING)
            if(player.world.random(250) == 1) {
                val nest = DropTableFactory.getDrop(player, 10_000) ?: break

                val westTile = Tile(player.tile.x - 1, player.tile.z, player.tile.height)
                val eastTile = Tile(player.tile.x + 1, player.tile.z, player.tile.height)
                val southTile = Tile(player.tile.x, player.tile.z - 1, player.tile.height)
                val northTile = Tile(player.tile.x, player.tile.z + 1, player.tile.height)
                val targetSpawnTile = when {
                    player.world.collision.isBlocked(westTile, Direction.WEST, false) -> eastTile
                    player.world.collision.isBlocked(eastTile, Direction.EAST, false) -> southTile
                    player.world.collision.isBlocked(southTile, Direction.SOUTH, false) -> northTile
                    player.world.collision.isBlocked(northTile, Direction.NORTH, false) -> player.tile
                    else -> westTile
                }

                nest.forEach {
                    val groundItem = GroundItem(it, targetSpawnTile)

                    // Gives the birds nest 30 seconds before it despawns
                    groundItem.currentCycle = 150
                    player.world.spawn(groundItem)
                }
                player.message("<col=FF0000>A bird's nest falls out of the tree.</col>")
                player.playSound(BIRD_NEST_DROP_SYNTH)
            }
            if (interpolate((tree.lowChance * axe.ratio).toInt(), (tree.highChance * axe.ratio).toInt(), level) > RANDOM.nextInt(255)) {
                player.filterableMessage("You get some ${logName.pluralSuffix(2).lowercase()}.")
                player.inventory.add(tree.log)
                player.addXp(Skills.WOODCUTTING, tree.xp)

                if (player.world.random(tree.depleteChance) == 0) {
                    player.animate(-1)
                    player.playSound(TREE_FALLING_SYNTH)

                    if (treeStumps[obj.id] != -1) {
                        val world = player.world
                        world.queue {
                            val trunk = DynamicObject(obj, treeStumps[obj.id]!!)
                            var canopy = world.getObject(obj.tile.transform(-1, -1, 1), 10) ?: world.getObject(obj.tile.transform(0, -1, 1), 10) ?: world.getObject(obj.tile.transform(-1, 0, 1), 10) ?: world.getObject(obj.tile.transform(0, 0, 1), 10)
                            if(canopy != null) {
                                world.remove(canopy)
                            }
                            world.remove(obj)
                            world.spawn(trunk)
                            wait(tree.respawnTime.random())
                            world.remove(trunk)
                            world.spawn(DynamicObject(obj))
                            if(canopy != null) {
                                world.spawn(DynamicObject(canopy))
                            }
                        }
                    }
                    break
                }
            }
            it.wait(2)
        }
    }

    private fun canChop(p: Player, obj: GameObject, tree: TreeType): Boolean {
        if (!p.world.isSpawned(obj)) {
            return false
        }

        val axe = AxeType.values.reversed().firstOrNull { p.getSkills().getMaxLevel(Skills.WOODCUTTING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item)) }
        if (axe == null) {
            p.message("You need a hatchet to chop down this tree.")
            p.message("You do not have an axe which you have the woodcutting level to use.")
            return false
        }

        if (p.getSkills().getMaxLevel(Skills.WOODCUTTING) < tree.level) {
            p.message("You need a Woodcutting level of ${tree.level} to chop down this tree.")
            return false
        }

        if (p.inventory.isFull) {
            p.message("Your inventory is too full to hold any more logs.")
            return false
        }

        return true
    }
}