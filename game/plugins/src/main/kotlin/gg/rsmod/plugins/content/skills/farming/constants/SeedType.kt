package gg.rsmod.plugins.content.skills.farming.constants

enum class SeedType(val growthFrequency: Int) {
    Allotment(growthFrequency = 2),
    Hops(growthFrequency = 2),
    Tree(growthFrequency = 8),
    FruitTree(growthFrequency = 32),
    Bush(growthFrequency = 4),
    Flower(growthFrequency = 1),
    Herb(growthFrequency = 4),
    Mushroom(growthFrequency = 8),
    Cactus(growthFrequency = 16),
    PotatoCactus(growthFrequency = 2),
    Calquat(growthFrequency = 32),
    SpiritTree(growthFrequency = 64),
    Sapling(growthFrequency = 1),
}
