package gg.rsmod.plugins.content.items.food

import gg.rsmod.plugins.api.cfg.Items

enum class Food(
    val item: Int,
    val heal: Int = 0,
    val overheal: Boolean = false,
    val replacement: Int = -1,
    val tickDelay: Int = 3,
    val comboFood: Boolean = false,
    val message: String = "",
) {
    /**
     * Sea food.
     */
    ANCHOVIE(item = Items.ANCHOVIES, heal = 10),
    CRAYFISH(item = Items.CRAYFISH, heal = 20),
    STUFFED_SNAKE(
        item = Items.STUFFED_SNAKE,
        heal = 20,
        message = "You eat the stuffed snake-it's quite a meal! It tastes like chicken.",
    ),
    UGTHANKI_MEAT(item = Items.UGTHANKI_MEAT, heal = 30),
    SHRIMP(item = Items.SHRIMPS, heal = 30),
    SARDINE(item = Items.SARDINE, heal = 40),
    HERRING(item = Items.HERRING, heal = 50),
    MACKEREL(item = Items.MACKEREL, heal = 60),
    COD(item = Items.COD, heal = 70),
    TROUT(item = Items.TROUT, heal = 70),
    CAVE_EEL(item = Items.CAVE_EEL, heal = 70),
    PIKE(item = Items.PIKE, heal = 80),
    SALMON(item = Items.SALMON, heal = 90),
    TUNA(item = Items.TUNA, heal = 100),
    FURY_SHARK(item = Items.FURY_SHARK, heal = 100),
    RAINBOW_FISH(item = Items.RAINBOW_FISH, heal = 110),
    LAVA_EEL(item = Items.LAVA_EEL, heal = 110),
    LOBSTER(item = Items.LOBSTER, heal = 120),
    BASS(item = Items.BASS, heal = 130),
    SWORDFISH(item = Items.SWORDFISH, heal = 140),
    MONKFISH(item = Items.MONKFISH, heal = 160),
    KARAMBWAN(item = Items.COOKED_KARAMBWAN, heal = 180, tickDelay = 2, comboFood = true),
    SHARK(item = Items.SHARK, heal = 200),
    SEA_TURTLE(item = Items.SEA_TURTLE, heal = 210),
    MANTA_RAY(item = Items.MANTA_RAY, heal = 220),
    ROCKTAIL(item = Items.ROCKTAIL, heal = 230, overheal = true),
    CAVEFISH(item = Items.CAVEFISH, heal = 200),

    /**
     * Meat.
     */
    CRAB_MEAT(item = Items.COOKED_CRAB_MEAT, heal = 20),
    CRAB_MEAT_2(item = Items.COOKED_CRAB_MEAT_7523, heal = 20),
    CRAB_MEAT_3(item = Items.COOKED_CRAB_MEAT_7524, heal = 20),
    CRAB_MEAT_4(item = Items.COOKED_CRAB_MEAT_7525, heal = 20),
    CRAB_MEAT_5(item = Items.COOKED_CRAB_MEAT_7526, heal = 20),
    CHICKEN(item = Items.COOKED_CHICKEN, heal = 40),
    MEAT(item = Items.COOKED_MEAT, heal = 40),
    RABBIT(item = Items.COOKED_RABBIT, heal = 40),
    THIN_SNAIL_MEAT(item = Items.THIN_SNAIL_MEAT, heal = 50),
    ROAST_BIRD_MEAT(item = Items.ROAST_BIRD_MEAT, heal = 60),
    COOKED_SLIMY_EEL(item = Items.COOKED_SLIMY_EEL, heal = 60),
    ROAST_RABBIT_MEAT(item = Items.ROAST_RABBIT, heal = 70),
    ROAST_BEAST_MEAT(item = Items.ROAST_BEAST_MEAT, heal = 80),
    LEAN_SNAIL_MEAT(item = Items.LEAN_SNAIL_MEAT, heal = 80),
    FAT_SNAIL_MEAT(item = Items.FAT_SNAIL_MEAT, heal = 90),
    CHOMPY(item = Items.COOKED_CHOMPY, heal = 100),
    JUBBLY(item = Items.COOKED_JUBBLY, heal = 150),
    TOADS_LEGS(item = Items.TOADS_LEGS, heal = 30),

    /** Sandwiches */
    TRIANGLE_SANDWICH(item = Items.TRIANGLE_SANDWICH, heal = 60),
    SQUARE_SANDWICH(item = Items.SQUARE_SANDWICH, heal = 60),

    /**
     * Pastries.
     */
    SPINACH_ROLL(item = Items.SPINACH_ROLL, heal = 20),
    CAKE(item = Items.CAKE, replacement = Items._23_CAKE, heal = 40),
    CAKE_23(item = Items._23_CAKE, replacement = Items.SLICE_OF_CAKE, heal = 40),
    SLICE_OF_CAKE(item = Items.SLICE_OF_CAKE, heal = 40),
    BREAD(item = Items.BREAD, heal = 50),
    CHOCOLATE_CAKE(item = Items.CHOCOLATE_CAKE, heal = 50, replacement = Items._23_CHOCOLATE_CAKE),
    CHOCOLATE_CAKE_23(item = Items._23_CHOCOLATE_CAKE, replacement = Items.CHOCOLATE_SLICE, heal = 50),
    SLICE_OF_CHOCOLATE_CAKE(item = Items.CHOCOLATE_SLICE, heal = 50),
    MINT_CAKE(item = Items.MINT_CAKE, heal = 50),
    BAGUETTE(item = Items.BAGUETTE, heal = 60),
    COOKED_FISHCAKE(item = Items.COOKED_FISHCAKE, heal = 110),

    /** Kebabs */
    KEBAB(item = Items.KEBAB, heal = 0, message = ""),

    /** Pies */
    REDBERRY_PIE(item = Items.REDBERRY_PIE, heal = 50, replacement = Items.HALF_A_REDBERRY_PIE),
    HALF_A_REDBERRY_PIE(item = Items.HALF_A_REDBERRY_PIE, heal = 50),
    MEAT_PIE(item = Items.MEAT_PIE, heal = 60, replacement = Items.HALF_A_MEAT_PIE),
    HALF_A_MEAT_PIE(item = Items.HALF_A_MEAT_PIE, heal = 60),
    APPLE_PIE(item = Items.APPLE_PIE, heal = 70, replacement = Items.HALF_AN_APPLE_PIE),
    HALF_AN_APPLE_PIE(item = Items.HALF_AN_APPLE_PIE, heal = 70),

    /** Stews */
    STEW(item = Items.STEW, heal = 110),
    SPICY_STEW(item = Items.SPICY_STEW, heal = 110),
    CURRY(item = Items.CURRY, heal = 190),

    /** Pizzas */
    PLAIN_PIZZA(item = Items.PLAIN_PIZZA, heal = 70, replacement = Items._12_PLAIN_PIZZA),
    _12_PLAIN_PIZZA(item = Items._12_PLAIN_PIZZA, heal = 70),
    MEAT_PIZZA(item = Items.MEAT_PIZZA, heal = 80, replacement = Items._12_MEAT_PIZZA),
    _12_MEAT_PIZZA(item = Items._12_MEAT_PIZZA, heal = 80),
    ANCHOVY_PIZZA(item = Items.ANCHOVY_PIZZA, heal = 90, replacement = Items._12_ANCHOVY_PIZZA),
    _12_ANCHOVY_PIZZA(item = Items._12_ANCHOVY_PIZZA, heal = 90),
    PINEAPPLE_PIZZA(item = Items.PINEAPPLE_PIZZA, heal = 110, replacement = Items._12_PAPPLE_PIZZA),
    _12_PAPPLE_PIZZA(item = Items._12_PAPPLE_PIZZA, heal = 110),

    /**
     * Vegetables
     */
    CABBAGE(item = Items.CABBAGE, heal = 10, message = "You eat the cabbage. Yuck!"),
    ONION(item = Items.ONION, heal = 10, message = "It's always sad see a grown man/woman cry."),
    SPICY_SAUCE(item = Items.SPICY_SAUCE, heal = 20),
    BAKED_POTATO(item = Items.BAKED_POTATO, heal = 20),
    CHILLI_CON_CARNE(item = Items.CHILLI_CON_CARNE, heal = 50),
    SCRAMBLED_EGG(item = Items.SCRAMBLED_EGG, heal = 50),
    EGG_AND_TOMATO(item = Items.EGG_AND_TOMATO, heal = 80),
    FRIED_ONIONS(item = Items.FRIED_ONIONS, heal = 50),
    FRIED_MUSHROOMS(item = Items.FRIED_MUSHROOMS, heal = 50),
    EVIL_TURNIP(item = Items.EVIL_TURNIP, heal = 60, replacement = Items._23_EVIL_TURNIP),
    _23_EVIL_TURNIP(item = Items._23_EVIL_TURNIP, heal = 60, replacement = Items._13_EVIL_TURNIP),
    _13_EVIL_TURNIP(item = Items._13_EVIL_TURNIP, heal = 60),
    POTATO_WITH_BUTTER(item = Items.POTATO_WITH_BUTTER, heal = 70),
    POTATO_WITH_CHEESE(item = Items.POTATO_WITH_CHEESE, heal = 90),
    EGG_POTATO(item = Items.EGG_POTATO, heal = 110),
    MUSHROOM__ONION(item = Items.MUSHROOM__ONION, heal = 110),
    TUNA_AND_CORN(item = Items.TUNA_AND_CORN, heal = 130),
    CHILLI_POTATO(item = Items.CHILLI_POTATO, heal = 140),
    MUSHROOM_POTATO(item = Items.MUSHROOM_POTATO, heal = 200),
    TUNA_POTATO(item = Items.TUNA_POTATO, heal = 220),

    /** Dairies */
    POT_OF_CREAM(item = Items.POT_OF_CREAM, heal = 10),
    CHEESE(item = Items.CHEESE, heal = 20),

    /** Fruits */
    BANANA(item = Items.BANANA, heal = 20),
    SLICED_BANANA(item = Items.SLICED_BANANA, heal = 20),
    ORANGE(item = Items.ORANGE, heal = 20),
    ORANGE_CHUNKS(item = Items.ORANGE_CHUNKS, heal = 20),
    ORANGE_SLICES(item = Items.ORANGE_SLICES, heal = 20),
    PAPAYA_FRUIT(item = Items.PAPAYA_FRUIT, heal = 20),
    PINEAPPLE_CHUNKS(item = Items.PINEAPPLE_CHUNKS, heal = 20),
    PINEAPPLE_RING(item = Items.PINEAPPLE_RING, heal = 20),
    DWELLBERRIES(item = Items.DWELLBERRIES, heal = 20),
    TOMATO(item = Items.TOMATO, heal = 20),
    WATERMELON_SLICE(item = Items.WATERMELON_SLICE, heal = 20),
    LEMON(item = Items.LEMON, heal = 20),
    LEMON_CHUNKS(item = Items.LEMON_CHUNKS, heal = 20),
    LEMON_SLICES(item = Items.LEMON_SLICES, heal = 20),
    LIME(item = Items.LIME, heal = 20),
    LIME_CHUNKS(item = Items.LIME_CHUNKS, heal = 20),
    LIME_SLICES(item = Items.LIME_SLICES, heal = 20),
    RED_BANANA(
        item = Items.RED_BANANA,
        heal = 50,
        message = "You eat the red banana. It's tastier than your average banana.",
    ),
    SLICED_RED_BANANA(item = Items.SLICED_RED_BANANA, heal = 50, message = "You eat the sliced red banana. Yum."),
    PEACH(item = Items.PEACH, heal = 80),

    /** Gnome & Crunchies & Battas & Gnomebowls*/
    SPICY_CRUNCHIES(item = Items.SPICY_CRUNCHIES, heal = 70),
    PREMADE_SY_CRUNCH(item = Items.PREMADE_SY_CRUNCH, heal = 70),
    CHOCCHIP_CRUNCHIES(item = Items.CHOCCHIP_CRUNCHIES, heal = 70),
    PREMADE_CH_CRUNCH(item = Items.PREMADE_CH_CRUNCH, heal = 70),
    PREMADE_TD_CRUNCH(item = Items.PREMADE_TD_CRUNCH, heal = 80),
    WORM_CRUNCHIES(item = Items.WORM_CRUNCHIES, heal = 80),
    PREMADE_WM_CRUN(item = Items.PREMADE_WM_CRUN, heal = 80),
    FRUIT_BATTA(item = Items.FRUIT_BATTA, heal = 110),
    PREMADE_FRT_BATTA(item = Items.PREMADE_FRT_BATTA, heal = 110),
    TOAD_BATTA(item = Items.TOAD_BATTA, heal = 110),
    PREMADE_TD_BATTA(item = Items.PREMADE_TD_BATTA, heal = 110),
    WORM_BATTA(item = Items.WORM_BATTA, heal = 110),
    PREMADE_WM_BATTA(item = Items.PREMADE_WM_BATTA, heal = 110),
    VEGETABLE_BATTA(item = Items.VEGETABLE_BATTA, heal = 110),
    PREMADE_VEG_BATTA(item = Items.PREMADE_VEG_BATTA, heal = 110),
    CHEESETOM_BATTA(item = Items.CHEESETOM_BATTA, heal = 110),
    PREMADE_CT_BATTA(item = Items.PREMADE_CT_BATTA, heal = 110),
    TOAD_CRUNCHIES(item = Items.TOAD_CRUNCHIES, heal = 120),
    WORM_HOLE(item = Items.WORM_HOLE, heal = 120),
    PREMADE_WORM_HOLE(item = Items.PREMADE_WORM_HOLE, heal = 120),
    VEG_BALL(item = Items.VEG_BALL, heal = 120),
    PREMADE_VEG_BALL(item = Items.PREMADE_VEG_BALL, heal = 120),
    TANGLED_TOADS_LEGS(item = Items.TANGLED_TOADS_LEGS, heal = 150),
    PREMADE_TTL(item = Items.PREMADE_TTL, heal = 150),
    CHOCOLATE_BOMB(item = Items.CHOCOLATE_BOMB, heal = 150),
    PREMADE_CHOC_BOMB(item = Items.PREMADE_CHOC_BOMB, heal = 150),

    /** Miscellaneous */
    FIELD_RATION(item = Items.FIELD_RATION, heal = 10),
    CHOCOLATE_BAR(item = Items.CHOCOLATE_BAR, heal = 30),
    TCHIKI_MONKEY_NUTS(item = Items.TCHIKI_MONKEY_NUTS, heal = 50),
    TCHIKI_NUT_PASTE(item = Items.TCHIKI_NUT_PASTE, heal = 50),
    ROLL(item = Items.ROLL, heal = 60),

    /** Special Events */
    PUMPKIN(item = Items.PUMPKIN, heal = 140),
    EASTER_EGG(item = Items.EASTER_EGG, heal = 140),

    /**
     * Dungeoneering Food //number might need changing for heal as this was from rs3 wiki for dungeoneering food
     */
    HEIM_CRAB(item = Items.HEIM_CRAB, heal = 200),
    REDEYE(item = Items.REDEYE, heal = 250),
    DUSK_EEL(item = Items.DUSK_EEL, heal = 500),
    GIANT_FLATFISH(item = Items.GIANT_FLATFISH, heal = 750),
    SHORTFINNED_EEL(item = Items.SHORTFINNED_EEL, heal = 1000),
    WEB_SNIPPER(item = Items.WEB_SNIPPER, heal = 1250),
    BOULDABASS(item = Items.BOULDABASS, heal = 1500),
    SALVE_EEL(item = Items.SALVE_EEL, heal = 1750),
    BLUE_CRAB(item = Items.BLUE_CRAB, heal = 2000),
    CAVE_MORAY(item = Items.CAVE_MORAY, heal = 2250),

    /**
     * Dungeoneering Regular Potatos //number might need changing for heal as this was from rs3 wiki for dungeoneering food
     */
    BAKED_CAVE_POTATO(item = Items.BAKED_CAVE_POTATO, heal = 25),
    HEIM_CRAB_POTATO(item = Items.HEIM_CRAB_POTATO, heal = 75),
    REDEYE_POTATO(item = Items.REDEYE_POTATO, heal = 325),
    DUSK_EEL_POTATO(item = Items.DUSK_EEL_POTATO, heal = 575),
    GIANT_FLATFISH_POTATO(item = Items.GIANT_FLATFISH_POTATO, heal = 825),
    SHORTFIN_EEL_POTATO(item = Items.SHORTFIN_EEL_POTATO, heal = 1075),
    SNIPPER_POTATO(item = Items.SNIPPER_POTATO, heal = 1325),
    BOULDABASS_POTATO(item = Items.BOULDABASS_POTATO, heal = 1575),
    SALVE_EEL_POTATO(item = Items.SALVE_EEL_POTATO, heal = 1825),
    BLUE_CRAB_POTATO(item = Items.BLUE_CRAB_POTATO, heal = 2075),
    CAVE_MORAY_POTATO(item = Items.CAVE_MORAY_POTATO, heal = 2325),

    /**
     * Dungeoneering Gissel Potatos //number might need changing for heal as this was from rs3 wiki for dungeoneering food
     */
    GISSEL_POTATO(item = Items.GISSEL_POTATO, heal = 50),
    HEIM_CRAB__GISSEL_POTATO(item = Items.HEIM_CRAB__GISSEL_POTATO, heal = 150),
    REDEYE__GISSEL_POTATO(item = Items.REDEYE__GISSEL_POTATO, heal = 400),
    DUSK_EEL__GISSEL_POTATO(item = Items.DUSK_EEL__GISSEL_POTATO, heal = 650),
    FLATFISH__GISSEL_POTATO(item = Items.FLATFISH__GISSEL_POTATO, heal = 900),
    SHORTFIN__GISSEL_POTATO(item = Items.SHORTFIN__GISSEL_POTATO, heal = 1150),
    SNIPPER__GISSEL_POTATO(item = Items.SNIPPER__GISSEL_POTATO, heal = 1400),
    BOULDABASS__GISSEL_POTATO(item = Items.BOULDABASS__GISSEL_POTATO, heal = 1650),
    SALVE_EEL__GISSEL_POTATO(item = Items.SALVE_EEL__GISSEL_POTATO, heal = 1900),
    BLUE_CRAB__GISSEL_POTATO(item = Items.BLUE_CRAB__GISSEL_POTATO, heal = 2150),
    MORAY__GISSEL_POTATO(item = Items.MORAY__GISSEL_POTATO, heal = 2400),

    /**
     * Dungeoneering Edicap Potatos //number might need changing for heal as this was from rs3 wiki for dungeoneering food
     */
    EDICAP_POTATO(item = Items.EDICAP_POTATO, heal = 100),
    HEIM_CRAB__EDICAP_POTATO(item = Items.HEIM_CRAB__EDICAP_POTATO, heal = 250),
    REDEYE__EDICAP_POTATO(item = Items.REDEYE__EDICAP_POTATO, heal = 475),
    DUSK_EEL__EDICAP_POTATO(item = Items.DUSK_EEL__EDICAP_POTATO, heal = 725),
    FLATFISH__EDICAP_POTATO(item = Items.FLATFISH__EDICAP_POTATO, heal = 975),
    SHORTFIN__EDICAP_POTATO(item = Items.SHORTFIN__EDICAP_POTATO, heal = 1225),
    SNIPPER__EDICAP_POTATO(item = Items.SNIPPER__EDICAP_POTATO, heal = 1475),
    BOULDABASS__EDICAP_POTATO(item = Items.BOULDABASS__EDICAP_POTATO, heal = 1725),
    SALVE_EEL__EDICAP_POTATO(item = Items.SALVE_EEL__EDICAP_POTATO, heal = 1957),
    BLUE_CRAB__EDICAP_POTATO(item = Items.BLUE_CRAB__EDICAP_POTATO, heal = 2225),
    MORAY__EDICAP_POTATO(item = Items.MORAY__EDICAP_POTATO, heal = 2475),

    ;

    companion object {
        val values = enumValues<Food>()
    }
}
