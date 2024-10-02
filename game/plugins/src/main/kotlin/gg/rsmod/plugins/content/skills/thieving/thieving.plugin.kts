package gg.rsmod.plugins.content.skills.thieving

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.skills.thieving.pickpocketing.PickpocketTarget
import gg.rsmod.plugins.content.skills.thieving.pickpocketing.Pickpocketing
import gg.rsmod.plugins.content.skills.thieving.stalls.StallTarget
import gg.rsmod.plugins.content.skills.thieving.stalls.Stalls

PickpocketTarget.values().forEach { target ->
    DropTableFactory.register(target.drops, *target.objectIds.toIntArray(), type = DropTableType.PICKPOCKET)
    target.objectIds.forEach { targetId ->
        on_npc_option(targetId, "pickpocket") {
            val npc = player.getInteractingNpc()
            player.queue {
                Pickpocketing.pickpocket(this, npc, target)
            }
        }
    }
}

StallTarget.values().filter { it != StallTarget.WallSafe }.forEach { target ->
    DropTableFactory.register(target.drops, *target.fullAndEmptyObjectIds.keys.toIntArray(), type = DropTableType.STALL)
    target.fullAndEmptyObjectIds.keys.forEach { targetId ->
        val option =
            if (if_obj_has_option(targetId, "steal-from")) {
                "steal-from"
            } else {
                "steal from"
            }
        on_obj_option(targetId, option) {
            val obj = player.getInteractingGameObj()
            player.queue {
                Stalls.stealFromStall(this, obj, target)
            }
        }
    }
}

StallTarget.values().flatMap { it.guards }.toSet().forEach { npc ->
    on_npc_spawn(npc) {
        Stalls.stallGuards.add(this.npc)
    }
}

on_obj_option(obj = Objs.WALL_SAFE, option = "crack") {
    // Find the target wall safe in the list of stall targets
    val target = StallTarget.values().first { it == StallTarget.WallSafe }

    // Register the drop table for the wall safe object
    DropTableFactory.register(target.drops, *target.fullAndEmptyObjectIds.keys.toIntArray(), type = DropTableType.STALL)

    // Check if the player has the required thieving level to crack the safe
    if (player.skills.getCurrentLevel(Skills.THIEVING) < target.level) {
        // Notify the player that they need level 50 thieving to crack the safe
        player.message("You need to be a level 50 thief to crack this safe.")
        return@on_obj_option
    }

    // Check if the player has enough inventory space
    if (!player.inventory.hasSpace) {
        // Notify the player that they do not have enough inventory space
        player.message("Not enough inventory space.")
        return@on_obj_option
    }

    // Notify the player that they have started cracking the safe
    player.filterableMessage("You start cracking the safe.")

    // Queue the actions for cracking the safe
    player.queue {
        // Determine if the player is successful in cracking the safe based on their thieving level
        val success =
            interpolate(
                target.lowChance,
                target.highChance,
                player.skills.getCurrentLevel(Skills.THIEVING),
            ) > RANDOM.nextInt(255)

        // Play the safe-cracking animation
        player.animate(2248)
        wait(4)

        // Check if the safe-cracking attempt was unsuccessful
        if (!success) {
            // Notify the player that they have triggered a trap
            player.filterableMessage("You slip and trigger a trap!")

            // Play the trap triggering animation
            player.animate(1113)

            // Apply damage to the player
            player.hit(world.random(20..60), type = HitType.REGULAR_HIT)
            wait(1)

            // Reset the player's animation
            player.animate(-1)

            // Interrupt the player's queued actions
            player.interruptQueues()
        } else {
            // Handle the success scenario, including giving the player the loot from the safe
            Stalls.handleSuccess(player, player.getInteractingGameObj(), target)
        }
    }
}
