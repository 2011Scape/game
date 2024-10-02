package gg.rsmod.plugins.content.skills.firemaking

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.MovementQueue
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.timer.LAST_LOG_LIT
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*

object FiremakingAction {
    suspend fun burnLog(
        task: QueueTask,
        data: FiremakingData,
        ground: GroundItem? = null,
    ) {
        val player = task.player

        val groundBurning = ground != null
        val logItem = ground ?: GroundItem(data.raw, 1, player.tile, player)

        if (!canBurn(player, data, groundBurning, logItem)) {
            player.animate(-1)
            return
        }

        val quickLight = player.timers.has(LAST_LOG_LIT) && player.timers[LAST_LOG_LIT] > 0

        if (!groundBurning) {
            val slot =
                if (player.getInteractingItem().id ==
                    Items.TINDERBOX_590
                ) {
                    player.getInteractingOtherItemSlot()
                } else {
                    player.getInteractingItemSlot()
                }
            if (player.inventory.remove(data.raw, beginSlot = slot, assureFullRemoval = true).hasSucceeded()) {
                player.world.spawn(logItem)
            }
        }

        if (!quickLight) {
            player.filterableMessage("You attempt to light the logs.")
        }

        while (canBurn(player, data, groundBurning, logItem)) {
            var success =
                interpolate(
                    low = 65,
                    high = 513,
                    level = player.skills.getCurrentLevel(Skills.FIREMAKING),
                ) > RANDOM.nextInt(255)

            if (quickLight) {
                success = true
            } else {
                player.animate(733)
            }

            task.wait(2)

            if (success) {
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

                player.playSound(Sfx.FIRE_LIT)
                player.filterableMessage("The fire catches and the logs begin to burn.")
                player.animate(-1)
                player.addXp(Skills.FIREMAKING, data.experience)

                val targetTile = player.findWesternTile()
                if (targetTile != player.tile) {
                    player.walkTo(targetTile, MovementQueue.StepType.FORCED_WALK, true)

                    world.queue {
                        wait(2)
                        player.faceTile(fire.tile)
                        player.timers[LAST_LOG_LIT] = 4
                    }
                }
                break
            }
            task.wait(2)
        }
        player.animate(-1)
    }

    val bankAreas =
        listOf(
            // edgeville
            Pair(3091, 3098) to Pair(3488, 3499),
            // varrock west bank
            Pair(3179, 3194) to Pair(3432, 3446),
            // varrock east bank
            Pair(3250, 3257) to Pair(3416, 3423),
            // alkharid bank
            Pair(3265, 3272) to Pair(3161, 3173),
            // draynor bank
            Pair(3088, 3097) to Pair(3240, 3246),
            // falador  east bank
            Pair(3009, 3018) to Pair(3355, 3358),
            Pair(3009, 3921) to Pair(3353, 3356),
            // falador west bank
            Pair(2943, 2949) to Pair(3368, 3369),
            Pair(2943, 2947) to Pair(3370, 3373),
            // catherby bank
            Pair(2806, 2812) to Pair(3438, 3445),
            // seers village bank
            Pair(2724, 2727) to Pair(3487, 3489),
            Pair(2721, 2730) to Pair(3490, 3493),
            // ardougne north bank
            Pair(2612, 2621) to Pair(3332, 3335),
            // ardougne south bank
            Pair(2649, 2658) to Pair(3280, 3287),
            // yanille bank
            Pair(2609, 2616) to Pair(3088, 3097),
            // grand exchange south-west bank
            Pair(3146, 3151) to Pair(3476, 3481),
            // grand exchange south-east bank
            Pair(3178, 3183) to Pair(3476, 3481),
            // grand exchange north-east bank
            Pair(3178, 3183) to Pair(3502, 3507),
            // grand exchange north-west bank
            Pair(3146, 3151) to Pair(3502, 3507),
        )

    fun isWithinBankArea(player: Player): Boolean {
        for (bankArea in bankAreas) {
            val (x1, x2) = bankArea.first
            val (z1, z2) = bankArea.second
            if (player.tile.x in x1..x2 && player.tile.z in z1..z2) {
                return true
            }
        }
        return false
    }

    private fun canBurn(
        player: Player,
        data: FiremakingData,
        groundBurning: Boolean,
        logItem: GroundItem,
    ): Boolean {
        if (groundBurning && !player.world.isSpawned(logItem)) {
            return false
        }
        if (!player.inventory.contains(Items.TINDERBOX_590)) {
            player.message("You need a tinderbox to light a fire.")
            return false
        }
        if (player.skills.getCurrentLevel(Skills.FIREMAKING) < data.levelRequired) {
            player.message(
                "You need a Firemaking level of ${data.levelRequired} to burn ${
                    player.world.definitions.get(
                        ItemDef::class.java,
                        data.raw,
                    ).name.lowercase()
                }.",
            )
            return false
        }
        if (player.world.getObject(
                player.tile,
                type = ObjectType.INTERACTABLE,
            ) != null ||
            isWithinBankArea(player) ||
            player.world.getObject(player.tile, type = ObjectType.DIAGONAL_INTERACTABLE) != null
        ) {
            player.message("You can't light a fire here.")
            return false
        }
        return true
    }
}
