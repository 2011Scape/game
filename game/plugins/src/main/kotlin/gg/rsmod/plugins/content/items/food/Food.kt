package gg.rsmod.plugins.content.items.food

import gg.rsmod.plugins.api.cfg.Items

enum class Food(val item: Int, val heal: Int = 0, val overheal: Boolean = false,
                val replacement: Int = -1, val tickDelay: Int = 3,
                val comboFood: Boolean = false) {

    /**
     * Sea food.
     */
    SHRIMP(item = Items.SHRIMPS, heal = 30),
    SARDINE(item = Items.SARDINE, heal = 40),
    HERRING(item = Items.HERRING, heal = 50),
    MACKEREL(item = Items.MACKEREL, heal = 60),
    TROUT(item = Items.TROUT, heal = 70),
    SALMON(item = Items.SALMON, heal = 90),
    TUNA(item = Items.TUNA, heal = 100),
    LOBSTER(item = Items.LOBSTER, heal = 120),
    BASS(item = Items.BASS, heal = 130),
    SWORDFISH(item = Items.SWORDFISH, heal = 140),
    MONKFISH(item = Items.MONKFISH, heal = 160),
    KARAMBWAN(item = Items.COOKED_KARAMBWAN, heal = 180, comboFood = true),
    SHARK(item = Items.SHARK, heal = 200),
    MANTA_RAY(item = Items.MANTA_RAY, heal = 210),
    ROCKTAIL(item = Items.ROCKTAIL, overheal = true),

    /**
     * Meat.
     */
    CHICKEN(item = Items.COOKED_CHICKEN, heal = 40),
    MEAT(item = Items.COOKED_MEAT, heal = 40),

    /**
     * Pastries.
     */
    BREAD(item = Items.BREAD, heal = 50);

    companion object {
        val values = enumValues<Food>()
    }
}