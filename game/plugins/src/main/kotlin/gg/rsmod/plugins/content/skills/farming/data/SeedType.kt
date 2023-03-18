package gg.rsmod.plugins.content.skills.farming.data

enum class SeedType(val growthFrequency: Int, val amountToPlant: Int = 1) {
    Flower(growthFrequency = 1),
    Allotment(growthFrequency = 2, amountToPlant = 3),
    Hops(growthFrequency = 2, amountToPlant = 4),
    PotatoCactus(growthFrequency = 2),
    Bush(growthFrequency = 4),
    Herb(growthFrequency = 4),
    Mushroom(growthFrequency = 8),
    Tree(growthFrequency = 8),
    Cactus(growthFrequency = 16),
    Calquat(growthFrequency = 32),
    FruitTree(growthFrequency = 32),
    SpiritTree(growthFrequency = 64)
}
