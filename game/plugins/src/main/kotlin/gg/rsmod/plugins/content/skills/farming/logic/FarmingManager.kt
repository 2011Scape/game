package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.CompostBinHandler
import gg.rsmod.plugins.content.skills.farming.logic.handler.WaterHandler

/**
 * Manager class for all farming-related logic, tied to a specific player
 * This is the entry point for any farming content related to a player
 */
class FarmingManager(private val player: Player) {

    private val patches: Map<Patch, PatchManager> = Patch.values().associateWith { PatchManager(it, player) }
    private val compostBins: Map<CompostBin, CompostBinHandler> = CompostBin.values().associateWith { CompostBinHandler(it, player) }

    fun getPatchManager(patch: Patch) = patches[patch]!!

    fun onFarmingTick(seedTypesToGrow: Set<SeedType>) {
        for (patch in patches.values) {
            patch.grow(seedTypesToGrow)
        }
        for (bin in compostBins.values) {
            bin.tick()
        }
    }

    fun itemUsed(patch: Patch, item: Int) {
        when (item) {
            Items.RAKE -> rake(patch)
            Items.SEED_DIBBER -> player.message("I should plant a seed, not the seed dibber.")
            Items.PLANT_CURE -> cure(patch)
            Items.SPADE -> clear(patch)
            in CompostState.itemIds -> addCompost(patch, CompostState.fromId(item))
            in WaterHandler.wateringCans -> water(patch, item)
            in Seed.seedIds -> plant(patch, Seed.fromId(item)!!)
            else -> player.message("Nothing interesting happens.")
        }
    }

    fun rake(patch: Patch) {
        patches[patch]!!.rake()
    }

    fun plant(patch: Patch, seed: Seed) {
        patches[patch]!!.plant(seed)
    }

    fun harvest(patch: Patch) {
        patches[patch]!!.harvest()
    }

    fun checkHealth(patch: Patch) {
        TODO()
    }

    fun addCompost(patch: Patch, compost: CompostState) {
        patches[patch]!!.addCompost(compost)
    }

    fun water(patch: Patch, wateringCan: Int) {
        patches[patch]!!.water(wateringCan)
    }

    fun cure(patch: Patch) {
        patches[patch]!!.cure()
    }

    fun clear(patch: Patch) {
        patches[patch]!!.clear()
    }

    fun inspect(patch: Patch) {
        patches[patch]!!.inspect()
    }

    fun addToCompostBin(compostBin: CompostBin, itemId: Int) {
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
}
