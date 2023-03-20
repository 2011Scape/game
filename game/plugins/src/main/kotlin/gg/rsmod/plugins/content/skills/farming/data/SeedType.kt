package gg.rsmod.plugins.content.skills.farming.data

enum class SeedType(val growthFrequency: Int, val amountToPlant: Int = 1, val plantingTool: PlantingTool = PlantingTool.SeedDibber, val harvestingTool: HarvestingTool = HarvestingTool.Spade, val canBeWatered: Boolean = false, val canBeCured: Boolean = true, val fixedLives: Boolean = true) {
    Flower(growthFrequency = 1, canBeWatered = true),
    Allotment(growthFrequency = 2, amountToPlant = 3, canBeWatered = true, fixedLives = false),
    Hops(growthFrequency = 2, amountToPlant = 4, canBeWatered = true, fixedLives = false),
    PotatoCactus(growthFrequency = 2),
    Bush(growthFrequency = 4, canBeCured = false),
    Herb(growthFrequency = 4, harvestingTool = HarvestingTool.Secateurs, fixedLives = false),
    Mushroom(growthFrequency = 8),
    Tree(growthFrequency = 8, plantingTool = PlantingTool.Spade, canBeCured = false),
    Cactus(growthFrequency = 16),
    Calquat(growthFrequency = 32, plantingTool = PlantingTool.Spade, canBeCured = false),
    FruitTree(growthFrequency = 32, plantingTool = PlantingTool.Spade, canBeCured = false),
    SpiritTree(growthFrequency = 64, plantingTool = PlantingTool.Spade, canBeCured = false)
}
