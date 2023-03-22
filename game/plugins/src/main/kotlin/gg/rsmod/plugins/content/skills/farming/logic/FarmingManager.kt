package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType

class FarmingManager(private val player: Player) {

    private val patches: Map<Patch, PatchManager> = Patch.values().associateWith { PatchManager(it, player) }

    fun onFarmingTick(seedTypesToGrow: Set<SeedType>) {
        for (patch in patches.values) {
            patch.grow(seedTypesToGrow)
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

    fun everythingFullyGrown(): Boolean {
        return patches.values.all { it.fullyGrown }
    }
}
