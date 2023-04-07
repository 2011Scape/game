package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.core.FarmTicker
import gg.rsmod.plugins.content.skills.farming.data.CureType
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.handler.*
import mu.KLogging

/**
 * Manager class for all farming-related logic, tied to a specific player and patch
 */
class PatchManager(private val patch: Patch, player: Player) {

    val state = PatchState(patch, player)
    private val rakeHandler = RakeHandler(state, patch, player)
    private val plantingHandler = PlantingHandler(state, patch, player)
    private val growingHandler = GrowingHandler(state, player)
    private val compostHandler = CompostHandler(state, patch, player)
    private val waterHandler = WaterHandler(state, patch, player)
    private val cureHandler = CureHandler(state, player)
    private val harvestHandler = HarvestingHandler(state, patch, player)
    private val clearHandler = ClearHandler(state, player)
    private val inspectHandler = InspectHandler(state, patch, player)
    private val protectHandler = ProtectHandler(state, patch, player)
    private val healthCheckHandler = HealthCheckHandler(state, patch, player)
    private val chopHandler = ChopHandler(state, player)

    val fullyGrown get() = state.isFullyGrown

    fun grow(seedTypesForTick: FarmTicker.SeedTypesForTick) {
        rakeHandler.growWeeds()

        if (patch.seedTypes.intersect(seedTypesForTick.grow).any()) {
            growingHandler.grow()
        }

        if (patch.seedTypes.intersect(seedTypesForTick.replenishProduce).any()) {
            growingHandler.replenishProduce()
        }
    }

    fun rake() {
        rakeHandler.rake()
    }

    fun plant(seed: Seed) {
        plantingHandler.plant(seed)
    }

    fun addCompost(compost: CompostState) {
        compostHandler.addCompost(compost)
    }

    fun water(wateringCan: Int) {
        waterHandler.water(wateringCan)
    }

    fun cure(cureType: CureType) {
        cureHandler.cure(cureType)
    }

    fun harvest() {
        harvestHandler.harvest()
    }

    fun clear() {
        clearHandler.clear()
    }

    fun chopDown() {
        chopHandler.chopDown()
    }

    fun checkHealth() {
        healthCheckHandler.checkHealth()
    }

    fun inspect() {
        inspectHandler.inspect()
    }

    fun protect(): Boolean {
        return protectHandler.protect()
    }

    fun hasEnoughItemsToProtect(): Boolean {
        return protectHandler.hasEnoughToPay()
    }

    fun canProtect(): Boolean {
        return protectHandler.canProtect()
    }

    fun setLives(lives: Int) {
        state.setLives(lives)
    }

    companion object : KLogging()
}
