package gg.rsmod.plugins.content.skills.thieving.stalls

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.content.drops.DropTableBuilder
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

enum class StallTarget(
    val fullAndEmptyObjectIds: Map<Int, Int>,
    val level: Int,
    val xp: Double,
    val drops: DropTableBuilder.() -> Unit,
    val respawnTicks: Int,
    val message: String,
    val guards: List<Int> = listOf(),
    val lowChance: Int = 0,
    val highChance: Int = 0,
) {
    VegetableStall(
        fullAndEmptyObjectIds = mapOf(Objs.VEG_STALL to Objs.MARKET_STALL, Objs.VEG_STALL_4708 to Objs.MARKET_STALL),
        level = 2,
        xp = 10.0,
        drops =
            DropTableFactory.build {
                main {
                    total(20)
                    obj(Items.POTATO, slots = 6)
                    obj(Items.CABBAGE, slots = 4)
                    obj(Items.ONION, slots = 4)
                    obj(Items.TOMATO, slots = 4)
                    obj(Items.GARLIC, slots = 2)
                }
            },
        respawnTicks = 3,
        message = "You steal vegetables from the vegetable stall.",
    ),
    BakerStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.BAKERS_STALL to Objs.MARKET_STALL_34381,
                Objs.BAKERY_STALL to Objs.MARKET_STALL_6984,
            ),
        level = 5,
        xp = 16.0,
        drops =
            DropTableFactory.build {
                main {
                    total(20)
                    obj(Items.CAKE, slots = 13)
                    obj(Items.BREAD, slots = 5)
                    obj(Items.CHOCOLATE_SLICE, slots = 2)
                }
            },
        respawnTicks = 3,
        message = "You steal pastries from the baker stall.",
    ),
    CraftingStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.CRAFTING_STALL to Objs.BAMBOO_DESK,
                Objs.CRAFTING_STALL_6166 to Objs.MARKET_STALL_6984,
                Objs.CRAFTING_STALL_51654 to Objs.CRAFTING_STALL_51655,
            ),
        level = 5,
        xp = 16.0,
        drops =
            DropTableFactory.build {
                main {
                    total(3)
                    obj(Items.CHISEL)
                    obj(Items.NECKLACE_MOULD)
                    obj(Items.RING_MOULD)
                }
            },
        respawnTicks = 12,
        message = "You steal crafting supplies from the crafting stall.",
    ),
    MonkeyFoodStall(
        fullAndEmptyObjectIds = mapOf(Objs.FOOD_STALL to Objs.BAMBOO_DESK),
        level = 5,
        xp = 16.0,
        drops =
            DropTableFactory.build {
                main {
                    total(10)
                    obj(Items.BANANA, slots = 7)
                    obj(Items.MONKEY_NUTS)
                    obj(Items.BANANA_STEW)
                    obj(Items.MONKEY_BAR)
                }
            },
        respawnTicks = 12,
        message = "You steal food from the food stall.",
    ),
    MonkeyGeneralStall(
        fullAndEmptyObjectIds = mapOf(Objs.GENERAL_STALL to Objs.BAMBOO_DESK),
        level = 5,
        xp = 16.0,
        drops =
            DropTableFactory.build {
                main {
                    total(3)
                    obj(Items.HAMMER)
                    obj(Items.EMPTY_POT)
                    obj(Items.TINDERBOX_590)
                }
            },
        respawnTicks = 12,
        message = "You steal goods from the general stall.",
    ),
    TeaStall(
        fullAndEmptyObjectIds = mapOf(Objs.TEA_STALL to Objs.MARKET_STALL),
        level = 5,
        xp = 16.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.CUP_OF_TEA)
                }
            },
        respawnTicks = 12,
        message = "You steal tea from the tea stall.",
    ),
    SilkStall(
        fullAndEmptyObjectIds = mapOf(Objs.SILK_STALL_34383 to Objs.MARKET_STALL_34381),
        level = 20,
        xp = 24.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.SILK)
                }
            },
        respawnTicks = 8,
        message = "You steal silk from the silk stall.",
    ),
    WineStall(
        fullAndEmptyObjectIds = mapOf(Objs.MARKET_STALL_14011 to Objs.WINE_STALL),
        level = 22,
        xp = 27.0,
        drops =
            DropTableFactory.build {
                main {
                    total(100)
                    obj(Items.JUG, slots = 39)
                    obj(Items.JUG_OF_WATER, slots = 20)
                    obj(Items.GRAPES, slots = 17)
                    obj(Items.JUG_OF_WINE, slots = 13)
                    obj(Items.BOTTLE_OF_WINE, slots = 11)
                }
            },
        respawnTicks = 16,
        message = "You steal wine from the wine stall.",
        guards = listOf(Npcs.MARKET_GUARD_2236),
    ),
    SeedStall(
        fullAndEmptyObjectIds = mapOf(Objs.SEED_STALL_7053 to Objs.SEED_STALL),
        level = 27,
        xp = 10.0,
        drops =
            DropTableFactory.build {
                main {
                    total(252)
                    obj(Items.POTATO_SEED, slots = 30)
                    obj(Items.MARIGOLD_SEED, slots = 30)
                    obj(Items.BARLEY_SEED, slots = 30)
                    obj(Items.HAMMERSTONE_SEED, slots = 30)
                    obj(Items.ONION_SEED, slots = 23)
                    obj(Items.ASGARNIAN_SEED, slots = 20)
                    obj(Items.CABBAGE_SEED, slots = 18)
                    obj(Items.YANILLIAN_SEED, slots = 12)
                    obj(Items.ROSEMARY_SEED, slots = 10)
                    obj(Items.NASTURTIUM_SEED, slots = 9)
                    obj(Items.TOMATO_SEED, slots = 9)
                    obj(Items.JUTE_SEED, slots = 9)
                    obj(Items.SWEETCORN_SEED, slots = 7)
                    obj(Items.KRANDORIAN_SEED, slots = 6)
                    obj(Items.STRAWBERRY_SEED, slots = 4)
                    obj(Items.WILDBLOOD_SEED, slots = 3)
                    obj(Items.WATERMELON_SEED, slots = 2)
                }
            },
        respawnTicks = 15,
        message = "You steal seeds from the seed stall.",
        guards = listOf(Npcs.MARKET_GUARD_2236),
    ),
    FurStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.FUR_STALL_4278 to Objs.MARKET_STALL_4276,
                Objs.FUR_STALL_34387 to Objs.MARKET_STALL_34381,
            ),
        level = 35,
        xp = 36.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.GREY_WOLF_FUR)
                }
            },
        respawnTicks = 16,
        message = "You steal fur from the fur stall.",
    ),
    FishStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.FISH_STALL to Objs.MARKET_STALL,
                Objs.FISH_STALL_4705 to Objs.MARKET_STALL,
                Objs.FISH_STALL_4707 to Objs.MARKET_STALL,
            ),
        level = 42,
        xp = 42.0,
        drops =
            DropTableFactory.build {
                main {
                    total(20)
                    obj(Items.RAW_SALMON, slots = 14)
                    obj(Items.RAW_TUNA, slots = 5)
                    obj(Items.RAW_LOBSTER, slots = 1)
                }
            },
        respawnTicks = 16,
        message = "You steal fish from the fish stall.",
    ),
    CrossbowStall(
        fullAndEmptyObjectIds = mapOf(Objs.CROSSBOW_STALL to Objs.MARKET_STALL_6984),
        level = 49,
        xp = 52.0,
        drops =
            DropTableFactory.build {
                main {
                    total(10)
                    obj(Items.BRONZE_BOLTS, slots = 7)
                    obj(Items.BRONZE_LIMBS, slots = 2)
                    obj(Items.WOODEN_STOCK, slots = 1)
                }
            },
        respawnTicks = 15,
        message = "You steal equipment from the crossbow stall.",
    ),
    WallSafe(
        fullAndEmptyObjectIds = mapOf(Objs.WALL_SAFE to Objs.EMPTY_WALL_SAFE),
        level = 50,
        xp = 70.0,
        drops =
            DropTableFactory.build {
                main {
                    total(128)
                    obj(Items.COINS_995, quantity = 10, slots = 25)
                    obj(Items.COINS_995, quantity = 20, slots = 56)
                    obj(Items.COINS_995, quantity = 40, slots = 21)
                    obj(Items.UNCUT_SAPPHIRE, slots = 12)
                    obj(Items.UNCUT_EMERALD, slots = 9)
                    obj(Items.UNCUT_RUBY, slots = 4)
                    obj(Items.UNCUT_DIAMOND, slots = 1)
                }
            },
        respawnTicks = 5,
        message = "You get some loot.",
        lowChance = 8,
        highChance = 160,
    ),
    SilverStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.SILVER_STALL to Objs.MARKET_STALL_6984,
                Objs.SILVER_STALL_34382 to Objs.MARKET_STALL_34381,
            ),
        level = 50,
        xp = 54.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.SILVER_ORE)
                }
            },
        respawnTicks = 50,
        message = "You steal jewellery from the silver stall.",
    ),
    SpiceStall(
        fullAndEmptyObjectIds = mapOf(Objs.SPICE_STALL_34386 to Objs.MARKET_STALL_34381),
        level = 65,
        xp = 81.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.SPICE)
                }
            },
        respawnTicks = 133,
        message = "You steal spices from the spice stall.",
    ),
    MagicStall(
        fullAndEmptyObjectIds = mapOf(Objs.MAGIC_STALL to Objs.BAMBOO_DESK),
        level = 65,
        xp = 100.0,
        drops =
            DropTableFactory.build {
                main {
                    total(3)
                    obj(Items.AIR_RUNE)
                    obj(Items.EARTH_RUNE)
                    obj(Items.FIRE_RUNE)
                }
            },
        respawnTicks = 133,
        message = "You steal equipment from the magic stall.",
    ),
    ScimitarStall(
        fullAndEmptyObjectIds = mapOf(Objs.SCIMITAR_STALL to Objs.BAMBOO_DESK),
        level = 65,
        xp = 160.0,
        drops =
            DropTableFactory.build {
                main {
                    total(3)
                    obj(Items.IRON_SCIMITAR, slots = 2)
                    obj(Items.STEEL_SCIMITAR)
                }
            },
        respawnTicks = 133,
        message = "You steal equipment from the scimitar stall.",
    ),
    GemStall(
        fullAndEmptyObjectIds =
            mapOf(
                Objs.GEM_STALL to Objs.MARKET_STALL_6984,
                Objs.GEM_STALL_34385 to Objs.MARKET_STALL_34381,
            ),
        level = 75,
        xp = 160.0,
        drops =
            DropTableFactory.build {
                main {
                    total(128)
                    obj(Items.UNCUT_SAPPHIRE, slots = 105)
                    obj(Items.UNCUT_EMERALD, slots = 17)
                    obj(Items.UNCUT_RUBY, slots = 5)
                    obj(Items.UNCUT_DIAMOND)
                }
            },
        respawnTicks = 300,
        message = "You steal gems from the gem stall.",
    ),
    ;

    fun hasInventorySpace(player: Player): Boolean {
        return DropTableFactory.hasInventorySpaceForAnyDrop(
            player,
            fullAndEmptyObjectIds.toList().first().first,
            DropTableType.STALL,
        )
            ?: false
    }

    fun getEmpty(id: Int) = fullAndEmptyObjectIds[id]
}
