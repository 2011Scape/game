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
            Growth(growthFrequency = 1, canDiseaseOnFirstStage = false, wateredOffset = 64, diseasedOffset = 128, diedOffset = 192, cureType = CureType.Potion),
            Harvest(harvestingTool = Items.SPADE, fixedLives = true, harvestOption = "pick", harvestAnimation = 2292)
    ),
    Allotment(
            Plant(amountToPlant = 3, plantingTool = PlantingTool.SeedDibber),
            Growth(growthFrequency = 2, canDiseaseOnFirstStage = false, wateredOffset = 64, diseasedOffset = 128, diedOffset = 192, cureType = CureType.Potion),
            Harvest(harvestingTool = Items.SPADE, fixedLives = false, harvestOption = "harvest", harvestAnimation = 830)
    ),
    Herb(
            Plant(amountToPlant = 1, plantingTool = PlantingTool.SeedDibber),
            Growth(growthFrequency = 4, canDiseaseOnFirstStage = false, wateredOffset = null, diseasedOffset = 123, diedOffset = 165, cureType = CureType.Potion),
            Harvest(harvestingTool = null, fixedLives = false, harvestOption = "pick", harvestAnimation = 2282)
    )
}
