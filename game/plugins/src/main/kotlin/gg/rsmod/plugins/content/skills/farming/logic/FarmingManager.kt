package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.core.FarmTicker
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.CureType
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.handler.CompostBinHandler
import gg.rsmod.plugins.content.skills.farming.logic.handler.SaplingGrowingHandler
import gg.rsmod.plugins.content.skills.farming.logic.handler.WaterHandler

/**
 * Manager class for all farming-related logic, tied to a specific player
 * This is the entry point for any farming content related to a player
 */
class FarmingManager(
    private val player: Player,
) {
    private val patches: Map<Patch, PatchManager> = Patch.values().associateWith { PatchManager(it, player) }
    private val compostBins: Map<CompostBin, CompostBinHandler> =
        CompostBin.values().associateWith {
            CompostBinHandler(it, player)
        }

    private val saplingGrowingHandler = SaplingGrowingHandler(player)

    fun getPatchManager(patch: Patch) = patches[patch]!!

    fun onFarmingTick(seedTypesForTick: FarmTicker.SeedTypesForTick) {
        for (patch in patches.values) {
            patch.grow(seedTypesForTick)
        }
        for (bin in compostBins.values) {
            bin.tick()
        }
        saplingGrowingHandler.growSaplings()
    }

    fun itemUsed(
        patch: Patch,
        item: Int,
    ) {
        when (item) {
            Items.RAKE -> rake(patch)
            Items.SEED_DIBBER -> player.message("I should plant a seed, not the seed dibber.")
            Items.PLANT_CURE -> cure(patch, CureType.Potion)
            Items.SECATEURS -> cure(patch, CureType.Secateurs)
            Items.SPADE -> clear(patch)
            Items.PLANT_POT -> fillPot()
            in CompostState.itemIds -> CompostState.fromId(item)?.let { addCompost(patch, it) }
            in WaterHandler.wateringCans -> water(patch, item)
            in Seed.seedIds -> plant(patch, Seed.fromId(item)!!)
            else -> player.message("Nothing interesting happens.")
        }
    }

    fun rake(patch: Patch) {
        patches[patch]!!.rake()
    }

    fun plant(
        patch: Patch,
        seed: Seed,
    ) {
        patches[patch]!!.plant(seed)
    }

    fun harvest(patch: Patch) {
        patches[patch]!!.harvest()
    }

    fun checkHealth(patch: Patch) {
        patches[patch]!!.checkHealth()
    }

    fun addCompost(
        patch: Patch,
        compost: CompostState,
    ) {
        patches[patch]!!.addCompost(compost)
    }

    fun water(
        patch: Patch,
        wateringCan: Int,
    ) {
        patches[patch]!!.water(wateringCan)
    }

    fun cure(
        patch: Patch,
        cureType: CureType,
    ) {
        patches[patch]!!.cure(cureType)
    }

    fun clear(patch: Patch) {
        patches[patch]!!.clear()
    }

    fun chopDown(
        patch: Patch,
        obj: GameObject,
    ) {
        patches[patch]!!.chopDown(obj)
    }

    fun inspect(patch: Patch) {
        patches[patch]!!.inspect()
    }

    fun addToCompostBin(
        compostBin: CompostBin,
        itemId: Int,
    ) {
        compostBins[compostBin]!!.addCompostable(itemId)
    }

    fun openCompostBin(compostBin: CompostBin) {
        compostBins[compostBin]!!.open()
    }

    fun closeCompostBin(compostBin: CompostBin) {
        compostBins[compostBin]!!.close()
    }

    fun emptyCompostBin(compostBin: CompostBin) {
        compostBins[compostBin]!!.empty()
    }

    fun everythingFullyGrown(): Boolean {
        return patches.values.all { it.fullyGrown }
    }

    private fun fillPot() {
        if (!player.inventory.contains(Items.GARDENING_TROWEL)) {
            player.message("You need a gardening trowel to do that.")
        } else {
            player.queue {
                while (player.inventory.contains(Items.PLANT_POT)) {
                    player.animate(2271)
                    wait(2)
                    player.inventory.remove(Items.PLANT_POT)
                    player.inventory.add(Items.PLANT_POT_5354)
                }
                player.animate(-1)
            }
        }
    }
}
