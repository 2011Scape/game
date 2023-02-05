package gg.rsmod.plugins.content.items.food

import gg.rsmod.plugins.api.cfg.Items

enum class Food(
    val item: Int, val heal: Int = 0, val overheal: Boolean = false,
    val replacement: Int = -1, val tickDelay: Int = 3,
    val comboFood: Boolean = false,
    val message: String = ""
) {

    /**
     * Sea food.
     */
    CRAYFISH(item = Items.CRAYFISH, heal = 20),
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
    KARAMBWAN(item = Items.COOKED_KARAMBWAN, heal = 180, tickDelay = 2, comboFood = true),
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
    BREAD(item = Items.BREAD, heal = 50),
    CAKE(item = Items.CAKE, replacement = Items._23_CAKE, heal = 30),
    CAKE_23(item = Items._23_CAKE, replacement = Items.SLICE_OF_CAKE, heal = 30),
    SLICE_OF_CAKE(item = Items.SLICE_OF_CAKE, heal = 30),

    /**
     * Vegetables
     */
    CABBAGE(item = Items.CABBAGE, heal = 10, message = "You eat the cabbage. Yuck!"),
    ONION(item = Items.ONION, heal = 10, message = "It's always sad see a grown man/woman cry.")


    ;

    companion object {
        val values = enumValues<Food>()
    }
}