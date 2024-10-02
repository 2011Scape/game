package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items

enum class Sapling(
    val seedId: Int,
    val seedlingId: Int,
    val wateredSeedlingId: Int,
    val saplingId: Int,
) {
    AppleTree(Items.APPLE_TREE_SEED, Items.APPLE_SEEDLING, Items.APPLE_SEEDLING_5488, Items.APPLE_SAPLING),
    BananaTree(Items.BANANA_TREE_SEED, Items.BANANA_SEEDLING, Items.BANANA_SEEDLING_5489, Items.BANANA_SAPLING),
    OrangeTree(Items.ORANGE_TREE_SEED, Items.ORANGE_SEEDLING, Items.ORANGE_SEEDLING_5490, Items.ORANGE_SAPLING),
    CurryTree(Items.CURRY_TREE_SEED, Items.CURRY_SEEDLING, Items.CURRY_SEEDLING_5491, Items.CURRY_SAPLING),
    PineappleTree(
        Items.PINEAPPLE_SEED,
        Items.PINEAPPLE_SEEDLING,
        Items.PINEAPPLE_SEEDLING_5492,
        Items.PINEAPPLE_SAPLING,
    ),
    PapayaTree(Items.PAPAYA_TREE_SEED, Items.PAPAYA_SEEDLING, Items.PAPAYA_SEEDLING_5493, Items.PAPAYA_SAPLING),
    PalmTree(Items.PALM_TREE_SEED, Items.PALM_SEEDLING, Items.PALM_SEEDLING_5494, Items.PALM_SAPLING),
    Oak(Items.ACORN, Items.OAK_SEEDLING, Items.OAK_SEEDLING_5364, Items.OAK_SAPLING),
    Willow(Items.WILLOW_SEED, Items.WILLOW_SEEDLING, Items.WILLOW_SEEDLING_5365, Items.WILLOW_SAPLING),
    Maple(Items.MAPLE_SEED, Items.MAPLE_SEEDLING, Items.MAPLE_SEEDLING_5366, Items.MAPLE_SAPLING),
    Yew(Items.YEW_SEED, Items.YEW_SEEDLING, Items.YEW_SEEDLING_5367, Items.YEW_SAPLING),
    Magic(Items.MAGIC_SEED, Items.MAGIC_SEEDLING, Items.MAGIC_SEEDLING_5368, Items.MAGIC_SAPLING),
    Calquat(Items.CALQUAT_TREE_SEED, Items.CALQUAT_SEEDLING, Items.CALQUAT_SEEDLING_5495, Items.CALQUAT_SAPLING),
}
