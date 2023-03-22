package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedGrowth
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedHarvest
import gg.rsmod.plugins.content.skills.farming.data.blocks.SeedPlant

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
            SeedPlant(level = 9, plantXp = 11.0, plantedVarbitValue = 4, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(12.5, 24, 80)
    ),
    Marrentill(
            seedId = Items.MARRENTILL_SEED, produce = Item(Items.GRIMY_MARRENTILL), seedType = SeedType.Herb,
            SeedPlant(level = 14, plantXp = 13.5, plantedVarbitValue = 11, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(15.0, 28, 80)
    ),
    Tarromin(
            seedId = Items.TARROMIN_SEED, produce = Item(Items.GRIMY_TARROMIN), seedType = SeedType.Herb,
            SeedPlant(level = 19, plantXp = 16.0, plantedVarbitValue = 18, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(18.0, 31, 80)
    ),
    Harralander(
            seedId = Items.HARRALANDER_SEED, produce = Item(Items.GRIMY_HARRALANDER), seedType = SeedType.Herb,
            SeedPlant(level = 26, plantXp = 21.5, plantedVarbitValue = 25, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(24.0, 36, 80)
    ),
    Ranarr(
            seedId = Items.RANARR_SEED, produce = Item(Items.GRIMY_RANARR), seedType = SeedType.Herb,
            SeedPlant(level = 32, plantXp = 27.0, plantedVarbitValue = 32, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(30.5, 39, 80)
    ),
    SpiritWeed(
            seedId = Items.SPIRIT_WEED_SEED, produce = Item(Items.GRIMY_SPIRIT_WEED), seedType = SeedType.Herb,
            SeedPlant(level = 36, plantXp = 32.0, plantedVarbitValue = 204, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(36.0, 43, 80)
    ),
    Toadflax(
            seedId = Items.TOADFLAX_SEED, produce = Item(Items.GRIMY_TOADFLAX), seedType = SeedType.Herb,
            SeedPlant(level = 38, plantXp = 34.0, plantedVarbitValue = 39, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(38.5, 43, 80)
    ),
    Irit(
            seedId = Items.IRIT_SEED, produce = Item(Items.GRIMY_IRIT), seedType = SeedType.Herb,
            SeedPlant(level = 44, plantXp = 43.0, plantedVarbitValue = 46, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(48.5, 46, 80)
    ),
    Avantoe(
            seedId = Items.AVANTOE_SEED, produce = Item(Items.GRIMY_AVANTOE), seedType = SeedType.Herb,
            SeedPlant(level = 50, plantXp = 54.5, plantedVarbitValue = 53, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(61.5, 50, 80)
    ),
    Kwuarm(
            seedId = Items.KWUARM_SEED, produce = Item(Items.GRIMY_KWUARM), seedType = SeedType.Herb,
            SeedPlant(level = 56, plantXp = 69.0, plantedVarbitValue = 68, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(78.0, 54, 80)
    ),
    Snapdragon(
            seedId = Items.SNAPDRAGON_SEED, produce = Item(Items.GRIMY_SNAPDRAGON), seedType = SeedType.Herb,
            SeedPlant(level = 62, plantXp = 87.5, plantedVarbitValue = 75, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(98.5, 57, 80)
    ),
    Cadantine(
            seedId = Items.CADANTINE_SEED, produce = Item(Items.GRIMY_CADANTINE), seedType = SeedType.Herb,
            SeedPlant(level = 67, plantXp = 106.5, plantedVarbitValue = 82, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(120.0, 60, 80)
    ),
    Lantadyme(
            seedId = Items.LANTADYME_SEED, produce = Item(Items.GRIMY_LANTADYME), seedType = SeedType.Herb,
            SeedPlant(level = 73, plantXp = 135.5, plantedVarbitValue = 89, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(151.5, 64, 80)
    ),
    DwarfWeed(
            seedId = Items.DWARF_WEED_SEED, produce = Item(Items.GRIMY_DWARF_WEED), seedType = SeedType.Herb,
            SeedPlant(level = 79, plantXp = 170.5, plantedVarbitValue = 96, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(192.0, 67, 80)
    ),
    Torstol(
            seedId = Items.TORSTOL_SEED, produce = Item(Items.GRIMY_TORSTOL), seedType = SeedType.Herb,
            SeedPlant(level = 85, plantXp = 199.5, plantedVarbitValue = 103, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 27, protectionPayment = null),
            SeedHarvest(224.5, 71, 80)
    ),

    /**
     * Flowers
     */
    Marigold(
            seedId = Items.MARIGOLD_SEED, produce = Item(Items.MARIGOLDS), seedType = SeedType.Flower,
            SeedPlant(level = 2, plantXp = 8.5, plantedVarbitValue = 8, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = null),
            SeedHarvest(47.0, -1, -1)
    ),
    Rosemary(
            seedId = Items.ROSEMARY_SEED, produce = Item(Items.ROSEMARY), seedType = SeedType.Flower,
            SeedPlant(level = 11, plantXp = 12.0, plantedVarbitValue = 13, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 25, protectionPayment = null),
            SeedHarvest(66.5, -1, -1)
    ),
    Nasturtium(
            seedId = Items.NASTURTIUM_SEED, produce = Item(Items.NASTURTIUMS), seedType = SeedType.Flower,
            SeedPlant(level = 24, plantXp = 19.5, plantedVarbitValue = 18, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 20, protectionPayment = null),
            SeedHarvest(111.0, -1, -1)
    ),
    WoadSeed(
            seedId = Items.WOAD_SEED, produce = Item(Items.WOAD_LEAF, amount = 3), seedType = SeedType.Flower,
            SeedPlant(level = 25, plantXp = 20.5, plantedVarbitValue = 23, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 15, protectionPayment = null),
            SeedHarvest(115.5, -1, -1)
    ),
    Limpwurt(
            seedId = Items.LIMPWURT_SEED, produce = Item(Items.LIMPWURT_ROOT), seedType = SeedType.Flower,
            SeedPlant(level = 26, plantXp = 21.5, plantedVarbitValue = 28, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 10, protectionPayment = null),
            SeedHarvest(120.0, -1, -1)
    ),
    Scarecrow(
            seedId = Items.SCARECROW, produce = Item(Items.SCARECROW), seedType = SeedType.Flower,
            SeedPlant(level = 1, plantXp = 0.0, plantedVarbitValue = 33, baseLives = 1),
            SeedGrowth(growthStages = 0, canDisease = false, diseaseSlots = -1, protectionPayment = null),
            SeedHarvest(0.0, -1, -1)
    ),
    WhiteLily(
            seedId = Items.WHITE_LILY_SEED, produce = Item(Items.WHITE_LILY), seedType = SeedType.Flower,
            SeedPlant(level = 58, plantXp = 42.0, plantedVarbitValue = 37, baseLives = 1),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 5, protectionPayment = null),
            SeedHarvest(250.0, -1, -1)
    ),

    /**
     * Allotment
     */
    Potato(
            seedId = Items.POTATO_SEED, produce = Item(Items.POTATO), seedType = SeedType.Allotment,
            SeedPlant(level = 1, plantXp = 8.0, plantedVarbitValue = 6, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.COMPOST, amount = 2)),
            SeedHarvest(9.0, 130, 200)
    ),
    Onion(
            seedId = Items.ONION_SEED, produce = Item(Items.ONION), seedType = SeedType.Allotment,
            SeedPlant(level = 5, plantXp = 9.5, plantedVarbitValue = 13, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.POTATOES_10)),
            SeedHarvest(10.5, 130, 200)
    ),
    Cabbage(
            seedId = Items.CABBAGE_SEED, produce = Item(Items.CABBAGE), seedType = SeedType.Allotment,
            SeedPlant(level = 7, plantXp = 10.0, plantedVarbitValue = 20, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.ONIONS_10)),
            SeedHarvest(11.5, 130, 200)
    ),
    Tomato(
            seedId = Items.TOMATO_SEED, produce = Item(Items.TOMATO), seedType = SeedType.Allotment,
            SeedPlant(level = 12, plantXp = 12.5, plantedVarbitValue = 27, baseLives = 3),
            SeedGrowth(growthStages = 4, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.CABBAGES_10, amount = 2)),
            SeedHarvest(14.0, 130, 200)
    ),
    Sweetcorn(
            seedId = Items.SWEETCORN_SEED, produce = Item(Items.SWEETCORN), seedType = SeedType.Allotment,
            SeedPlant(level = 20, plantXp = 17.0, plantedVarbitValue = 34, baseLives = 3),
            SeedGrowth(growthStages = 6, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.APPLES_5)),
            SeedHarvest(19.0, 130, 200)
    ),
    Strawberry(
            seedId = Items.STRAWBERRY_SEED, produce = Item(Items.STRAWBERRY), seedType = SeedType.Allotment,
            SeedPlant(level = 31, plantXp = 26.0, plantedVarbitValue = 43, baseLives = 3),
            SeedGrowth(growthStages = 6, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.CURRY_LEAF, amount = 10)),
            SeedHarvest(29.0, 130, 200)
    ),
    Watermelon(
            seedId = Items.WATERMELON_SEED, produce = Item(Items.WATERMELON), seedType = SeedType.Allotment,
            SeedPlant(level = 47, plantXp = 48.5, plantedVarbitValue = 52, baseLives = 3),
            SeedGrowth(growthStages = 8, canDisease = true, diseaseSlots = 30, protectionPayment = Item(Items.JANGERBERRIES, amount = 5)),
            SeedHarvest(54.5, 130, 200)
    ),
    ;

    private val plantedVarbits = seedType.plant.plantedVarbits(plant.plantedVarbitValue, growth.growthStages)
    private val diseasedVarbits = seedType.growth.diseasedVarbits(plant.plantedVarbitValue, growth.growthStages)
    private val diedVarbits = seedType.growth.diedVarbits(plant.plantedVarbitValue, growth.growthStages)
    private val wateredVarbits = seedType.growth.wateredVarbits(plant.plantedVarbitValue, growth.growthStages)

    val baseWater = if (seedType.growth.canBeWatered) plant.plantedVarbitValue + seedType.growth.wateredOffset!! else plant.plantedVarbitValue
    val baseDisease = plant.plantedVarbitValue + seedType.growth.diseasedOffset
    val baseDied = plant.plantedVarbitValue + seedType.growth.diedOffset

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
            isHealthy(varbit) -> varbit - plant.plantedVarbitValue
            isDiseased(varbit) -> varbit - plant.plantedVarbitValue - seedType.growth.diseasedOffset
            isDead(varbit) -> varbit - plant.plantedVarbitValue - seedType.growth.diedOffset
            isWatered(varbit) -> varbit - plant.plantedVarbitValue - seedType.growth.wateredOffset!!
            else -> throw IllegalStateException()
        }
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
