package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.pluralSuffix

/**
 * The possible tools that can be used to plant a seed
 */
enum class PlantingTool(
    val id: Int,
    val animation: Int,
    val plantingSound: Int,
    val messageWhenMissing: String,
    val replacementId: Int? = null,
) {
    SeedDibber(
        id = Items.SEED_DIBBER,
        animation = 2291,
        plantingSound = 2432,
        messageWhenMissing = "You need a seed dibber to plant seeds.",
    ),
    Spade(
        id = Items.SPADE,
        animation = 830,
        plantingSound = 1470,
        messageWhenMissing = "You need a spade to plant a sapling.",
        replacementId = Items.PLANT_POT,
    ),
    ;

    fun plantedMessage(
        seed: Seed,
        patch: Patch,
    ) = when (this) {
        SeedDibber -> "You plant ${seed.amountToPlant()} ${seed.seedName.pluralSuffix(
            seed.amountToPlant(),
        )} in the ${patch.patchName}."
        Spade -> "You plant the ${seed.seedName} in the tree patch."
    }
}
