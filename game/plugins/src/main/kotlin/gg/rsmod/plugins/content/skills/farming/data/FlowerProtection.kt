package gg.rsmod.plugins.content.skills.farming.data

object FlowerProtection {
    val protections =
        mapOf(
            Seed.Marigold to listOf(Seed.Potato, Seed.Onion, Seed.Tomato),
            Seed.Rosemary to listOf(Seed.Cabbage),
            Seed.Nasturtium to listOf(Seed.Watermelon),
            Seed.WoadSeed to listOf(),
            Seed.Limpwurt to listOf(),
            Seed.Scarecrow to
                listOf(
                    Seed.Potato,
                    Seed.Onion,
                    Seed.Tomato,
                    Seed.Cabbage,
                    Seed.Sweetcorn,
                    Seed.Strawberry,
                    Seed.Watermelon,
                ),
        )

    val allotmentLinks =
        mapOf(
            Patch.FaladorAllotmentNorth to Patch.FaladorFlower,
            Patch.FaladorAllotmentSouth to Patch.FaladorFlower,
            Patch.CatherbyAllotmentNorth to Patch.CatherbyFlower,
            Patch.CatherbyAllotmentSouth to Patch.CatherbyFlower,
            Patch.ArdougneAllotmentNorth to Patch.ArdougneFlower,
            Patch.ArdougneAllotmentSouth to Patch.ArdougneFlower,
            Patch.MorytaniaAllotmentNorth to Patch.MorytaniaFlower,
            Patch.MorytaniaAllotmentSouth to Patch.MorytaniaFlower,
        )
}
