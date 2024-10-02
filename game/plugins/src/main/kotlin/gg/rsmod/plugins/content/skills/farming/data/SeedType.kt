package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.farming.data.blocks.Growth
import gg.rsmod.plugins.content.skills.farming.data.blocks.Harvest
import gg.rsmod.plugins.content.skills.farming.data.blocks.Plant

/**
 * Data on all the seed types (e.g., herbs, flowers, trees)
 */
enum class SeedType(
    val plant: Plant,
    val growth: Growth,
    val harvest: Harvest,
) {
    Flower(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.SeedDibber),
        Growth(
            growthFrequency = 1,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Potion,
            canBeWatered = true,
        ),
        Harvest(
            harvestingTool = Items.SPADE,
            fixedLives = true,
            harvestAnimation = 2292,
            livesReplenish = false,
            liveReplenishFrequency = null,
        ),
    ),
    Allotment(
        Plant(amountToPlant = 3, plantingTool = PlantingTool.SeedDibber),
        Growth(
            growthFrequency = 2,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Potion,
            canBeWatered = true,
        ),
        Harvest(
            harvestingTool = Items.SPADE,
            fixedLives = false,
            harvestAnimation = 830,
            livesReplenish = false,
            liveReplenishFrequency = null,
        ),
    ),
    Herb(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.SeedDibber),
        Growth(
            growthFrequency = 4,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Potion,
            canBeWatered = false,
        ),
        Harvest(
            harvestingTool = null,
            fixedLives = false,
            harvestAnimation = 2282,
            livesReplenish = false,
            liveReplenishFrequency = null,
        ),
    ),
    Hops(
        Plant(amountToPlant = 4, plantingTool = PlantingTool.SeedDibber),
        Growth(
            growthFrequency = 2,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Potion,
            canBeWatered = true,
        ),
        Harvest(
            harvestingTool = Items.SPADE,
            fixedLives = false,
            harvestAnimation = 830,
            livesReplenish = false,
            liveReplenishFrequency = null,
        ),
    ),
    Bush(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.SeedDibber),
        Growth(
            growthFrequency = 4,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Potion,
            canBeWatered = false,
        ),
        Harvest(
            harvestingTool = null,
            fixedLives = true,
            harvestAnimation = 2281,
            livesReplenish = true,
            liveReplenishFrequency = 4,
        ),
    ),
    FruitTree(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.Spade),
        Growth(
            growthFrequency = 32,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Secateurs,
            canBeWatered = false,
        ),
        Harvest(
            harvestingTool = null,
            fixedLives = true,
            harvestAnimation = 2280,
            livesReplenish = true,
            liveReplenishFrequency = 8,
        ),
    ),
    Tree(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.Spade),
        Growth(
            growthFrequency = 8,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Secateurs,
            canBeWatered = false,
        ),
        Harvest(
            harvestingTool = null,
            fixedLives = true,
            harvestAnimation = -1,
            livesReplenish = false,
            liveReplenishFrequency = null,
        ),
    ),
    Calquat(
        Plant(amountToPlant = 1, plantingTool = PlantingTool.Spade),
        Growth(
            growthFrequency = 32,
            canDiseaseOnFirstStage = false,
            cureType = CureType.Secateurs,
            canBeWatered = false,
        ),
        Harvest(
            harvestingTool = null,
            fixedLives = true,
            harvestAnimation = 2280,
            livesReplenish = true,
            liveReplenishFrequency = 8,
        ),
    ),
}
