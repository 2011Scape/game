package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items

enum class SeedTypeOld(
        val growthFrequency: Int,
        val amountToPlant: Int = 1,
        val plantingTool: PlantingTool = PlantingTool.SeedDibber,
        val harvestingTool: Int? = null,
        val canBeCured: Boolean = true,
        val fixedLives: Boolean = true,
        val harvestOption: String = "pick",
        val harvestAnimation: Int,
        val canDiseaseOnFirstStage: Boolean = false,
        val wateredOffset: Int? = null,
        val diseasedOffset: Int,
        val diedOffset: Int,
) {
    Flower(growthFrequency = 1, harvestAnimation = 2292, harvestingTool = Items.SPADE, wateredOffset = 64, diseasedOffset = 128, diedOffset = 192),
    Allotment(growthFrequency = 2, amountToPlant = 3, fixedLives = false, harvestAnimation = 830, harvestingTool = Items.SPADE, wateredOffset = 64, diseasedOffset = 128, diedOffset = 192),
//    Hops(growthFrequency = 2, amountToPlant = 4, fixedLives = false, harvestAnimation = 830, harvestingTool = Items.SPADE),
//    PotatoCactus(growthFrequency = 2, harvestAnimation = 2281),
//    Bush(growthFrequency = 4, canBeCured = false, harvestAnimation = 2281),
    Herb(growthFrequency = 4, fixedLives = false, harvestAnimation = 2282, diseasedOffset = 123, diedOffset = 192),
//    Mushroom(growthFrequency = 8, harvestAnimation = 2282, harvestingTool = Items.SPADE),
//    Tree(growthFrequency = 8, plantingTool = PlantingTool.Spade, canBeCured = false, harvestAnimation = 2275),
//    Cactus(growthFrequency = 16, harvestAnimation = 2281),
//    Calquat(growthFrequency = 32, plantingTool = PlantingTool.Spade, canBeCured = false, harvestAnimation = 2280),
//    FruitTree(growthFrequency = 32, plantingTool = PlantingTool.Spade, canBeCured = false, harvestAnimation = 2280),
//    SpiritTree(growthFrequency = 64, plantingTool = PlantingTool.Spade, canBeCured = false, harvestAnimation = -1),
    ;

    val canBeWatered = wateredOffset != null
}
