package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.farmingManager
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.constants.Constants.farmingManagerAttr
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.FarmingManager

object PlayerManager {

    /**
     * Called when a player farming tick occurs. Grows all seeds, weeds and produce, and
     * sets the relevant timer and attribute
     */
    fun onFarmingTick(player: Player) {
        grow(player, FarmTicker.seedTypesForTick)

        player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
        player.attr[LAST_WORLD_FARMING_TICK] = player.world.attr[Constants.worldFarmTick]!!
    }

    /**
     * Initializes the player farming logic upon login. Replays farming ticks that occurred while logged out and
     * starts the farming tick timer
     */
    fun onLogin(player: Player) {
        initializeAttributes(player)

        val lastWorldFarmTick = player.attr[LAST_WORLD_FARMING_TICK]
        val farmingManager = FarmingManager(player)
        player.attr[farmingManagerAttr] = farmingManager

        if (lastWorldFarmTick != null) {
            val totalGameTicksToHandle = gameTicksSinceLastPlayerFarmTick(player)
            val ticksLeftOnNextTimer = Constants.playerFarmingTickLength - (totalGameTicksToHandle % Constants.playerFarmingTickLength)

            // If there are less game ticks until the next world farming tick than game ticks until the
            // next player farming tick, that means the current world farming tick was activated for the
            // player while logged out
            val includeCurrentFarmTick = FarmTicker.gameTicksUntilNextFarmTick(player.world) < ticksLeftOnNextTimer

            // Replay all player farming ticks that occurred while logged out
            for (seedTypesForTick in FarmTicker.pastSeedTypes(player.world, lastWorldFarmTick + 1, includeCurrentFarmTick)) {
                grow(player, seedTypesForTick)
                if (farmingManager.everythingFullyGrown()) {
                    // If everything is fully grown, there's no need to replay any more player farming ticks
                    break
                }
            }
            player.timers[Constants.playerFarmingTimer] = ticksLeftOnNextTimer.toInt()

            // Set the player attribute that stores the last world farm tick the player handled
            // If the current farming tick was not included in the replay, this is the world farming tick minus 1
            player.attr[LAST_WORLD_FARMING_TICK] = player.world.attr[Constants.worldFarmTick]!!.let {
                if (includeCurrentFarmTick) it else it - 1
            }
        } else {
            // lastWorldFarmTick is null, which should only happen for brand-new accounts and
            // accounts that didn't log in since farming was introduced
            player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
            player.attr[LAST_WORLD_FARMING_TICK] = player.world.attr[Constants.worldFarmTick]!! - 1
        }
    }

    /**
     * Grows all seeds, weeds and produce
     */
    private fun grow(player: Player, seedTypesForTick: FarmTicker.SeedTypesForTick) {
        if(player.attr.has(farmingManagerAttr)) {
            player.farmingManager().onFarmingTick(seedTypesForTick)
        }
    }

    /**
     * Calculates the amount of game ticks that occurred since the last player farm tick
     */
    private fun gameTicksSinceLastPlayerFarmTick(player: Player): Int {
        val timer = if (player.timers.has(Constants.playerFarmingTimer)) {
            player.timers[Constants.playerFarmingTimer]
        } else {
            return 0
        }
        val lastLogout: Long = player.attr[LAST_LOGOUT_DATE]?.toLong() ?: return 0

        val ticksSpentOffline = (System.currentTimeMillis() - lastLogout) / player.world.gameContext.cycleTime
        val ticksUsedOnTimer = Constants.playerFarmingTickLength - timer

        // The amount of ticks since the last player tick is the time spent offline, plus the
        // time that was used already on the player farming tick timer. That timer is paused upon logout
        return (ticksSpentOffline + ticksUsedOnTimer).toInt()
    }

    /**
     * Initializes the information that cannot be stored in varbits
     */
    private fun initializeAttributes(player: Player) {
        val compostStates = player.attr[COMPOST_ON_PATCHES]
        if (compostStates == null) {
            player.attr[COMPOST_ON_PATCHES] = mutableMapOf()
        }
        val protectedPatches = player.attr[PROTECTED_PATCHES]
        if (protectedPatches == null) {
            player.attr[PROTECTED_PATCHES] = mutableListOf()
        }
        val patchLives = player.attr[PATCH_LIVES_LEFT]
        if (patchLives == null) {
            player.attr[PATCH_LIVES_LEFT] = mutableMapOf()
        }

        for (patch in Patch.values()) {
            player.attr[COMPOST_ON_PATCHES]!!.getOrPut(patch.persistenceId) { CompostState.None.persistenceId }
            player.attr[PATCH_LIVES_LEFT]!!.getOrPut(patch.persistenceId) { "0" }
        }
    }
}
