package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedGrowth
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedHarvest
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedPlant

/**
 * Data on all the seeds
 */
enum class Seed(
        val seedId: Int,
        val produce: Item,
        val seedType: SeedType,
        val plant: SeedPlant,
        val growth: SeedGrowth,
        val harvest: SeedHarvest,
) {
    /**
     * Herbs
     */
    Guam(
            seedId = Items.GUAM_SEED, produce = Item(Items.GRIMY_GUAM), seedType = SeedType.Herb,
            SeedPlant(level = 9, plantXp = 11.0, plantedVarbit = 4, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 127, diedVarbit = 169),
            SeedHarvest(12.5, 24, 80)
    ),
    Marrentill(
            seedId = Items.MARRENTILL_SEED, produce = Item(Items.GRIMY_MARRENTILL), seedType = SeedType.Herb,
            SeedPlant(level = 14, plantXp = 13.5, plantedVarbit = 11, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 130, diedVarbit = 169),
            SeedHarvest(15.0, 28, 80)
    ),
    Tarromin(
            seedId = Items.TARROMIN_SEED, produce = Item(Items.GRIMY_TARROMIN), seedType = SeedType.Herb,
            SeedPlant(level = 19, plantXp = 16.0, plantedVarbit = 18, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 133, diedVarbit = 169),
            SeedHarvest(18.0, 31, 80)
    ),
    Harralander(
            seedId = Items.HARRALANDER_SEED, produce = Item(Items.GRIMY_HARRALANDER), seedType = SeedType.Herb,
            SeedPlant(level = 26, plantXp = 21.5, plantedVarbit = 25, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 136, diedVarbit = 169),
            SeedHarvest(24.0, 36, 80)
    ),
    Ranarr(
            seedId = Items.RANARR_SEED, produce = Item(Items.GRIMY_RANARR), seedType = SeedType.Herb,
            SeedPlant(level = 32, plantXp = 27.0, plantedVarbit = 32, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 139, diedVarbit = 169),
            SeedHarvest(30.5, 39, 80)
    ),
    SpiritWeed(
            seedId = Items.SPIRIT_WEED_SEED, produce = Item(Items.GRIMY_SPIRIT_WEED), seedType = SeedType.Herb,
            SeedPlant(level = 36, plantXp = 32.0, plantedVarbit = 204, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 210, diedVarbit = 169),
            SeedHarvest(36.0, 43, 80)
    ),
    Toadflax(
            seedId = Items.TOADFLAX_SEED, produce = Item(Items.GRIMY_TOADFLAX), seedType = SeedType.Herb,
            SeedPlant(level = 38, plantXp = 34.0, plantedVarbit = 39, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 142, diedVarbit = 169),
            SeedHarvest(38.5, 43, 80)
    ),
    Irit(
            seedId = Items.IRIT_SEED, produce = Item(Items.GRIMY_IRIT), seedType = SeedType.Herb,
            SeedPlant(level = 44, plantXp = 43.0, plantedVarbit = 46, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 145, diedVarbit = 169),
            SeedHarvest(48.5, 46, 80)
    ),
    Avantoe(
            seedId = Items.AVANTOE_SEED, produce = Item(Items.GRIMY_AVANTOE), seedType = SeedType.Herb,
            SeedPlant(level = 50, plantXp = 54.5, plantedVarbit = 53, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 148, diedVarbit = 169),
            SeedHarvest(61.5, 50, 80)
    ),
    Kwuarm(
            seedId = Items.KWUARM_SEED, produce = Item(Items.GRIMY_KWUARM), seedType = SeedType.Herb,
            SeedPlant(level = 56, plantXp = 69.0, plantedVarbit = 68, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 151, diedVarbit = 169),
            SeedHarvest(78.0, 54, 80)
    ),
    Snapdragon(
            seedId = Items.SNAPDRAGON_SEED, produce = Item(Items.GRIMY_SNAPDRAGON), seedType = SeedType.Herb,
            SeedPlant(level = 62, plantXp = 87.5, plantedVarbit = 75, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 154, diedVarbit = 169),
            SeedHarvest(98.5, 57, 80)
    ),
    Cadantine(
            seedId = Items.CADANTINE_SEED, produce = Item(Items.GRIMY_CADANTINE), seedType = SeedType.Herb,
            SeedPlant(level = 67, plantXp = 106.5, plantedVarbit = 82, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 157, diedVarbit = 169),
            SeedHarvest(120.0, 60, 80)
    ),
    Lantadyme(
            seedId = Items.LANTADYME_SEED, produce = Item(Items.GRIMY_LANTADYME), seedType = SeedType.Herb,
            SeedPlant(level = 73, plantXp = 135.5, plantedVarbit = 89, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 160, diedVarbit = 169),
            SeedHarvest(151.5, 64, 80)
    ),
    DwarfWeed(
            seedId = Items.DWARF_WEED_SEED, produce = Item(Items.GRIMY_DWARF_WEED), seedType = SeedType.Herb,
            SeedPlant(level = 79, plantXp = 170.5, plantedVarbit = 96, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 163, diedVarbit = 169),
            SeedHarvest(192.0, 67, 80)
    ),
    Torstol(
            seedId = Items.TORSTOL_SEED, produce = Item(Items.GRIMY_TORSTOL), seedType = SeedType.Herb,
            SeedPlant(level = 85, plantXp = 199.5, plantedVarbit = 103, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null, waterVarbit = null, diseaseVarbit = 166, diedVarbit = 169),
            SeedHarvest(224.5, 71, 80)
    ),

    /**
     * Flowers
     */
    Marigold(
            seedId = Items.MARIGOLD_SEED, produce = Item(Items.MARIGOLDS), seedType = SeedType.Flower,
            SeedPlant(level = 2, plantXp = 8.5, plantedVarbit = 8, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = null, waterVarbit = 72, diseaseVarbit = 136, diedVarbit = 200),
            SeedHarvest(47.0, -1, -1)
    ),
    Rosemary(
            seedId = Items.ROSEMARY_SEED, produce = Item(Items.ROSEMARY), seedType = SeedType.Flower,
            SeedPlant(level = 11, plantXp = 12.0, plantedVarbit = 13, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 25, protectionPayment = null, waterVarbit = 77, diseaseVarbit = 141, diedVarbit = 205),
            SeedHarvest(66.5, -1, -1)
    ),
    Nasturtium(
            seedId = Items.NASTURTIUM_SEED, produce = Item(Items.NASTURTIUMS), seedType = SeedType.Flower,
            SeedPlant(level = 24, plantXp = 19.5, plantedVarbit = 18, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 20, protectionPayment = null, waterVarbit = 82, diseaseVarbit = 146, diedVarbit = 210),
            SeedHarvest(111.0, -1, -1)
    ),
    WoadSeed(
            seedId = Items.WOAD_SEED, produce = Item(Items.WOAD_LEAF, amount = 3), seedType = SeedType.Flower,
            SeedPlant(level = 25, plantXp = 20.5, plantedVarbit = 23, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 15, protectionPayment = null, waterVarbit = 87, diseaseVarbit = 151, diedVarbit = 215),
            SeedHarvest(115.5, -1, -1)
    ),
    Limpwurt(
            seedId = Items.LIMPWURT_SEED, produce = Item(Items.LIMPWURT_ROOT), seedType = SeedType.Flower,
            SeedPlant(level = 26, plantXp = 21.5, plantedVarbit = 28, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 10, protectionPayment = null, waterVarbit = 92, diseaseVarbit = 156, diedVarbit = 220),
            SeedHarvest(120.0, -1, -1)
    ),
    Scarecrow(
            seedId = Items.SCARECROW, produce = Item(Items.SCARECROW), seedType = SeedType.Flower,
            SeedPlant(level = 1, plantXp = 0.0, plantedVarbit = 33, baseLives = 1),
            SeedGrowth(growthStages = 0, canDisease = false, diseaseSlots = -1, protectionPayment = null, waterVarbit = 33, diseaseVarbit = 33, diedVarbit = 33),
            SeedHarvest(0.0, -1, -1)
    ),
//    WhiteLily(
//            seedId = Items.WHITE_LILY_SEED, produce = Item(Items.WHITE_LILY), seedType = SeedType.Flower,
//            SeedPlant(level = 58, plantXp = 42.0, plantedVarbit = 37, baseLives = 1),
//            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 5, protectionPayment = null, waterVarbit = null, diseaseVarbit = 136, diedVarbit = 200),
//            SeedHarvest(250.0, -1, -1)
//    ), // TODO: can't find the varbits for diseased/died/watered

    /**
     * Allotment
     */
    Potato(
            seedId = Items.POTATO_SEED, produce = Item(Items.POTATO), seedType = SeedType.Allotment,
            SeedPlant(level = 1, plantXp = 8.0, plantedVarbit = 6, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.COMPOST, amount = 2), waterVarbit = 70, diseaseVarbit = 134, diedVarbit = 198),
            SeedHarvest(9.0, 130, 200)
    ),
    Onion(
            seedId = Items.ONION_SEED, produce = Item(Items.ONION), seedType = SeedType.Allotment,
            SeedPlant(level = 5, plantXp = 9.5, plantedVarbit = 13, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.POTATOES_10), waterVarbit = 78, diseaseVarbit = 141, diedVarbit = 205),
            SeedHarvest(10.5, 130, 200)
    ),
    Cabbage(
            seedId = Items.CABBAGE_SEED, produce = Item(Items.CABBAGE), seedType = SeedType.Allotment,
            SeedPlant(level = 7, plantXp = 10.0, plantedVarbit = 20, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.ONIONS_10), waterVarbit = 84, diseaseVarbit = 148, diedVarbit = 212),
            SeedHarvest(11.5, 130, 200)
    ),
    Tomato(
            seedId = Items.TOMATO_SEED, produce = Item(Items.TOMATO), seedType = SeedType.Allotment,
            SeedPlant(level = 12, plantXp = 12.5, plantedVarbit = 27, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.CABBAGES_10, amount = 2), waterVarbit = 91, diseaseVarbit = 155, diedVarbit = 219),
            SeedHarvest(14.0, 130, 200)
    ),
    Sweetcorn(
            seedId = Items.SWEETCORN_SEED, produce = Item(Items.SWEETCORN), seedType = SeedType.Allotment,
            SeedPlant(level = 20, plantXp = 17.0, plantedVarbit = 34, baseLives = 3),
            SeedGrowth(growthStages = 6, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.APPLES_5), waterVarbit = 98, diseaseVarbit = 162, diedVarbit = 226),
            SeedHarvest(19.0, 130, 200)
    ),
    Strawberry(
            seedId = Items.STRAWBERRY_SEED, produce = Item(Items.STRAWBERRY), seedType = SeedType.Allotment,
            SeedPlant(level = 31, plantXp = 26.0, plantedVarbit = 43, baseLives = 3),
            SeedGrowth(growthStages = 6, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.CURRY_LEAF, amount = 10), waterVarbit = 107, diseaseVarbit = 171, diedVarbit = 235),
            SeedHarvest(29.0, 130, 200)
    ),
    Watermelon(
            seedId = Items.WATERMELON_SEED, produce = Item(Items.WATERMELON), seedType = SeedType.Allotment,
            SeedPlant(level = 47, plantXp = 48.5, plantedVarbit = 52, baseLives = 3),
            SeedGrowth(growthStages = 8, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.JANGERBERRIES, amount = 5), waterVarbit = 116, diseaseVarbit = 180, diedVarbit = 244),
            SeedHarvest(54.5, 130, 200)
    ),
    ;

    private val plantedVarbits = findVarbitRange(plant.plantedVarbit, false)
    private val diseasedVarbits = findVarbitRange(growth.diseaseVarbit, true)
    private val diedVarbits = findVarbitRange(growth.diedVarbit, true)
    private val wateredVarbits = growth.waterVarbit?.let { findVarbitRange(it, false) } ?: 0..-1

    lateinit var seedName: String
        private set

    fun isPlanted(patch: Patch, varbit: Int): Boolean {
        if (seedType !in patch.seedTypes) {
            return false
        }

        return isHealthy(varbit) || isDiseased(varbit) || isDead(varbit) || isWatered(varbit)
    }

    fun isHealthy(varbit: Int) = varbit in plantedVarbits
    fun isDiseased(varbit: Int) = varbit in diseasedVarbits
    fun isDead(varbit: Int) = varbit in diedVarbits
    fun isWatered(varbit: Int) = varbit in wateredVarbits
    fun growthStage(varbit: Int): Int {
        return when {
            isHealthy(varbit) -> varbit - plant.plantedVarbit
            isDiseased(varbit) -> varbit - plant.plantedVarbit - seedType.growth.diseasedOffset
            isDead(varbit) -> varbit - plant.plantedVarbit - seedType.growth.diedOffset
            isWatered(varbit) -> varbit - plant.plantedVarbit - seedType.growth.wateredOffset!!
            else -> throw IllegalStateException()
        }
    }

    private fun findVarbitRange(start: Int, useFirstStageAdjustment: Boolean): IntRange {
        val firstStageAdjustment = if (useFirstStageAdjustment && !seedType.growth.canDiseaseOnFirstStage) 1 else 0
        return (start + firstStageAdjustment)..(start + growth.growthStages)
    }

    companion object {
        /**
         * Initializes the names for all seeds. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            Seed.values().forEach {
                it.seedName = world.definitions.get(ItemDef::class.java, it.seedId).name.lowercase()
            }
        }
    }
}
