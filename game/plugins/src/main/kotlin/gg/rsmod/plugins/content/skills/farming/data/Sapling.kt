package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items

enum class Sapling(val seedId: Int, val seedlingId: Int, val wateredSeedlingId: Int, val saplingId: Int) {
    AppleTree(Items.APPLE_TREE_SEED, Items.APPLE_SEEDLING, Items.APPLE_SEEDLING_5488, Items.APPLE_SAPLING),
    BananaTree(Items.BANANA_TREE_SEED, Items.BANANA_SEEDLING, Items.BANANA_SEEDLING_5489, Items.BANANA_SAPLING),
    OrangeTree(Items.ORANGE_TREE_SEED, Items.ORANGE_SEEDLING, Items.ORANGE_SEEDLING_5490, Items.ORANGE_SAPLING),
    CurryTree(Items.CURRY_TREE_SEED, Items.CURRY_SEEDLING, Items.CURRY_SEEDLING_5491, Items.CURRY_SAPLING),
    PineappleTree(Items.PINEAPPLE_SEED, Items.PINEAPPLE_SEEDLING, Items.PINEAPPLE_SEEDLING_5492, Items.PINEAPPLE_SAPLING),
    PapayaTree(Items.PAPAYA_TREE_SEED, Items.PAPAYA_SEEDLING, Items.PAPAYA_SEEDLING_5493, Items.PAPAYA_SAPLING),
    PalmTree(Items.PALM_TREE_SEED, Items.PALM_SEEDLING, Items.PALM_SEEDLING_5494, Items.PALM_SAPLING),
    Oak(Items.ACORN, Items.OAK_SEEDLING, Items.OAK_SEEDLING_5364, Items.OAK_SAPLING),
    Calquat(Items.CALQUAT_TREE_SEED, Items.CALQUAT_SEEDLING, Items.CALQUAT_SEEDLING_5495, Items.CALQUAT_SAPLING),
}
