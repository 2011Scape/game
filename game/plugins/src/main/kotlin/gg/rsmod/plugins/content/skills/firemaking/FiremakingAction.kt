package gg.rsmod.plugins.content.skills.firemaking

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.MovementQueue
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.timer.LAST_LOG_LIT
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.ext.*

object FiremakingAction {
    suspend fun burnLog(task: QueueTask, data: FiremakingData) {
        val player = task.player

        if (!canBurn(player, data)) {
            player.animate(-1)
            return
        }

        if(player.inventory.remove(data.raw, assureFullRemoval = true).hasFailed()) {
            return
        }

        val logItem = GroundItem(data.raw, 1, player.tile, player)
        player.world.spawn(logItem)
        player.filterableMessage("You attempt to light the logs.")


        while(true) {

            var success = interpolate(low = 64, high = 512, level = player.getSkills().getCurrentLevel(Skills.FIREMAKING)) > RANDOM.nextInt(255)

            if(player.timers.has(LAST_LOG_LIT) && player.timers[LAST_LOG_LIT] > 0) {
                success = true
                task.wait(3)
            } else {
                player.animate(733)
                task.wait(2)
            }

            if(success) {


                val world = player.world

                val fire = DynamicObject(Objs.FIRE_2732, 10, 0, logItem.tile)
                world.queue {
                    world.remove(logItem)
                    world.spawn(fire)
                    wait((100..200).random())
                    world.remove(fire)
                    val ashes = GroundItem(Items.ASHES, 1, fire.tile, player)
                    world.spawn(ashes)
                }


                player.animate(-1)
                player.addXp(Skills.FIREMAKING, data.experience)
                val westTile = Tile(player.tile.x - 1, player.tile.z, player.tile.height)
                val eastTile = Tile(player.tile.x + 1, player.tile.z, player.tile.height)
                val targetWalkTile = when {
                    player.world.collision.isBlocked(westTile, Direction.WEST, false) -> eastTile
                    player.world.collision.isBlocked(eastTile, Direction.EAST, false) -> player.tile
                    else -> westTile
                }
                if (targetWalkTile != player.tile) {
                    player.walkTo(targetWalkTile, MovementQueue.StepType.FORCED_WALK, true)

                    world.queue {
                        wait(2)
                        player.faceTile(fire.tile)
                        player.timers[LAST_LOG_LIT] = 4
                    }
                }
                break

            }

            task.wait(4)
        }
    }

    private fun canBurn(player: Player, data: FiremakingData) : Boolean {
        if(player.world.getObject(player.tile, type = 10) != null || player.world.getObject(player.tile, type = 11) != null) {
            player.message("You can't light a fire here.")
            return false
        }
        if(player.getSkills().getCurrentLevel(Skills.FIREMAKING) < data.levelRequired) {
            player.message("You need a Firemaking level of ${data.levelRequired} to burn ${player.world.definitions.get(ItemDef::class.java, data.raw).name.lowercase()}.")
            return false
        }
        if(!player.inventory.contains(Items.TINDERBOX_590) || !player.inventory.contains(data.raw)) {
            return false
        }
        return true
    }
}