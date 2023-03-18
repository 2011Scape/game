package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items

enum class SeedType(val growthFrequency: Int, val amountToPlant: Int = 1, val plantingTool: PlantingTool = PlantingTool.SeedDibber) {
    Flower(growthFrequency = 1),
    Allotment(growthFrequency = 2, amountToPlant = 3),
    Hops(growthFrequency = 2, amountToPlant = 4),
    PotatoCactus(growthFrequency = 2),
    Bush(growthFrequency = 4),
    Herb(growthFrequency = 4),
    Mushroom(growthFrequency = 8),
    Tree(growthFrequency = 8, plantingTool = PlantingTool.Spade),
    Cactus(growthFrequency = 16),
    Calquat(growthFrequency = 32, plantingTool = PlantingTool.Spade),
    FruitTree(growthFrequency = 32, plantingTool = PlantingTool.Spade),
    SpiritTree(growthFrequency = 64, plantingTool = PlantingTool.Spade)
}
