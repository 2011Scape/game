package gg.rsmod.plugins.content.skills.thieving.pickpocketing

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.content.drops.DropTableBuilder
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

enum class PickpocketTarget(
    val objectIds: List<Int>,
    val level: Int,
    private val minChance: Int,
    private val maxChance: Int,
    val xp: Double,
    val drops: DropTableBuilder.() -> Unit,
    val damage: IntRange,
    val stunnedTicks: Int,
    val onCaught: List<String> = listOf("What do you think you're doing?"),
) {
    ManWoman(
        objectIds =
            listOf(
                Npcs.MAN,
                Npcs.MAN_2,
                Npcs.MAN_3,
                Npcs.WOMAN_BRUNETTE_LONGSKIRT_4,
                Npcs.WOMAN_BRUNETTE_PONYTAIL_5,
                Npcs.WOMAN_WOMAN_BLOND_PONYTAIL_6,
                Npcs.MAN_16,
                Npcs.MAN_24,
                Npcs.WOMAN_25,
                Npcs.MAN_170,
                Npcs.MAN_3223,
                Npcs.MAN_3225,
                Npcs.WOMAN_BLOND_LONGSKIRT_3226,
                Npcs.MAN_3915,
                Npcs.MAN_5923,
                Npcs.WOMAN_BLOND_5924,
                Npcs.MAN_LATEST_7873,
                Npcs.MAN_LATEST_7874,
                Npcs.MAN_LATEST_7875,
                Npcs.MAN_LATEST_7876,
                Npcs.MAN_LATEST_7877,
                Npcs.MAN_LATEST_7878,
                Npcs.MAN_LATEST_7879,
                Npcs.WOMAN_LATEST_PANTS_7880,
                Npcs.WOMAN_LATEST_LONGSKIRT_7881,
                Npcs.WOMAN_LATEST_SHORTSKIRT_7882,
                Npcs.WOMAN_LATEST_PANTS_7883,
                Npcs.WOMAN_LATEST_LONGSKIRT_7884,
                Npcs.MAN_7909,
                Npcs.MAN_7910,
                Npcs.MAN_12345,
                Npcs.MAN_12346,
                Npcs.MAN_12347,
                Npcs.CUFFS,
                Npcs.NARF,
                Npcs.JEFF,
                Npcs.RUSTY,
            ),
        level = 1,
        minChance = 180,
        maxChance = 240,
        xp = 8.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 3)
                }
            },
        damage = 10..10,
        stunnedTicks = 8,
    ),
    Farmer(
        objectIds = listOf(Npcs.FARMER, Npcs.FARMER_1757, Npcs.FARMER_1758, Npcs.FARMER_1760),
        level = 10,
        minChance = 150,
        maxChance = 240,
        xp = 14.5,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 3)
                }

                main {
                    total(256)
                    obj(Items.COINS_995, quantity = 9, slots = 246)
                    obj(Items.POTATO_SEED, slots = 10)
                }
            },
        damage = 10..10,
        stunnedTicks = 8,
    ),
    FemaleHamMember(
        objectIds = listOf(Npcs.HAM_MEMBER_1715),
        level = 15,
        minChance = 135,
        maxChance = 240,
        xp = 18.5,
        drops =
            DropTableFactory.build {
                main {
                    total(1100)
                    obj(Items.BRONZE_ARROW, quantityRange = 1..13, slots = 33)
                    obj(Items.BRONZE_HATCHET, slots = 33)
                    obj(Items.BRONZE_DAGGER, slots = 33)
                    obj(Items.BRONZE_PICKAXE, slots = 33)
                    obj(Items.IRON_HATCHET, slots = 33)
                    obj(Items.IRON_DAGGER, slots = 33)
                    obj(Items.IRON_PICKAXE, slots = 33)
                    obj(Items.LEATHER_BODY, slots = 33)
                    obj(Items.STEEL_ARROW, quantityRange = 1..13, slots = 22)
                    obj(Items.STEEL_HATCHET, slots = 22)
                    obj(Items.STEEL_DAGGER, slots = 22)
                    obj(Items.STEEL_PICKAXE, slots = 22)
                    obj(Items.HAM_BOOTS, slots = 11)
                    obj(Items.HAM_CLOAK, slots = 11)
                    obj(Items.HAM_GLOVES, slots = 11)
                    obj(Items.HAM_HOOD, slots = 11)
                    obj(Items.HAM_LOGO, slots = 11)
                    obj(Items.HAM_ROBE, slots = 11)
                    obj(Items.HAM_SHIRT, slots = 11)
                    obj(Items.COINS_995, quantityRange = 1..21, slots = 165)
                    obj(Items.BUTTONS, slots = 44)
                    obj(Items.DAMAGED_ARMOUR, slots = 44)
                    obj(Items.RUSTY_SWORD, slots = 44)
                    obj(Items.FEATHER, quantityRange = 1..7, slots = 33)
                    obj(Items.LOGS, slots = 33)
                    obj(Items.THREAD, quantityRange = 1..10, slots = 33)
                    obj(Items.COWHIDE, slots = 33)
                    obj(Items.KNIFE, slots = 22)
                    obj(Items.NEEDLE, slots = 22)
                    obj(Items.RAW_ANCHOVIES, slots = 22)
                    obj(Items.RAW_CHICKEN, slots = 22)
                    obj(Items.TINDERBOX_590, slots = 22)
                    obj(Items.UNCUT_OPAL, slots = 22)
                    obj(Items.CLUE_SCROLL_EASY, slots = 22)
                    obj(Items.COAL, slots = 22)
                    obj(Items.IRON_ORE, slots = 22)
                    obj(Items.UNCUT_JADE, slots = 22)
                    obj(Items.GRIMY_GUAM, slots = 12)
                    obj(Items.GRIMY_MARRENTILL, slots = 6)
                    obj(Items.GRIMY_TARROMIN, slots = 4)
                }
            },
        damage = 10..30,
        stunnedTicks = 7,
        onCaught =
            listOf(
                "Stop! {name} is a thief!",
                "Keep thine hands to thineself {name}.",
                "What do you think you're doing!",
                "We deal harshly with thieves around here!",
            ),
    ),
    MaleHamMember(
        objectIds = listOf(Npcs.HAM_MEMBER),
        level = 20,
        minChance = 117,
        maxChance = 240,
        xp = 22.5,
        drops =
            DropTableFactory.build {
                main {
                    total(1100)
                    obj(Items.BRONZE_ARROW, quantityRange = 1..13, slots = 33)
                    obj(Items.BRONZE_HATCHET, slots = 33)
                    obj(Items.BRONZE_DAGGER, slots = 33)
                    obj(Items.BRONZE_PICKAXE, slots = 33)
                    obj(Items.IRON_HATCHET, slots = 33)
                    obj(Items.IRON_DAGGER, slots = 33)
                    obj(Items.IRON_PICKAXE, slots = 33)
                    obj(Items.LEATHER_BODY, slots = 33)
                    obj(Items.STEEL_ARROW, quantityRange = 1..13, slots = 22)
                    obj(Items.STEEL_HATCHET, slots = 22)
                    obj(Items.STEEL_DAGGER, slots = 22)
                    obj(Items.STEEL_PICKAXE, slots = 22)
                    obj(Items.HAM_BOOTS, slots = 11)
                    obj(Items.HAM_CLOAK, slots = 11)
                    obj(Items.HAM_GLOVES, slots = 11)
                    obj(Items.HAM_HOOD, slots = 11)
                    obj(Items.HAM_LOGO, slots = 11)
                    obj(Items.HAM_ROBE, slots = 11)
                    obj(Items.HAM_SHIRT, slots = 11)
                    obj(Items.COINS_995, quantityRange = 1..21, slots = 165)
                    obj(Items.BUTTONS, slots = 44)
                    obj(Items.DAMAGED_ARMOUR, slots = 44)
                    obj(Items.RUSTY_SWORD, slots = 44)
                    obj(Items.FEATHER, quantityRange = 1..7, slots = 33)
                    obj(Items.LOGS, slots = 33)
                    obj(Items.THREAD, quantityRange = 1..10, slots = 33)
                    obj(Items.COWHIDE, slots = 33)
                    obj(Items.KNIFE, slots = 22)
                    obj(Items.NEEDLE, slots = 22)
                    obj(Items.RAW_ANCHOVIES, slots = 22)
                    obj(Items.RAW_CHICKEN, slots = 22)
                    obj(Items.TINDERBOX_590, slots = 22)
                    obj(Items.UNCUT_OPAL, slots = 22)
                    obj(Items.CLUE_SCROLL_EASY, slots = 22)
                    obj(Items.COAL, slots = 22)
                    obj(Items.IRON_ORE, slots = 22)
                    obj(Items.UNCUT_JADE, slots = 22)
                    obj(Items.GRIMY_GUAM, slots = 12)
                    obj(Items.GRIMY_MARRENTILL, slots = 6)
                    obj(Items.GRIMY_TARROMIN, slots = 4)
                }
            },
        damage = 10..30,
        stunnedTicks = 7,
        onCaught =
            listOf(
                "Stop! {name} is a thief!",
                "Keep thine hands to thineself {name}.",
                "What do you think you're doing!",
                "We deal harshly with thieves around here!",
            ),
    ),
    Warrior(
        objectIds = listOf(Npcs.WARRIOR_WOMAN, Npcs.ALKHARID_WARRIOR),
        level = 25,
        minChance = 100,
        maxChance = 240,
        xp = 26.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 18)
                }
            },
        damage = 20..20,
        stunnedTicks = 8,
    ),
    Rogue(
        objectIds = listOf(Npcs.ROGUE),
        level = 32,
        minChance = 74,
        maxChance = 240,
        xp = 36.5,
        drops =
            DropTableFactory.build {
                main {
                    total(128)
                    obj(Items.COINS_995, quantityRange = 25..40, slots = 108)
                    obj(Items.IRON_DAGGER_P, slots = 1)
                    obj(Items.AIR_RUNE, quantity = 8, slots = 8)
                    obj(Items.JUG_OF_WINE, slots = 6)
                    obj(Items.LOCKPICK, slots = 5)
                }
            },
        damage = 20..20,
        stunnedTicks = 8,
    ),
    CaveGoblin(
        objectIds =
            listOf(Npcs.CAVE_GOBLIN_5752, Npcs.CAVE_GOBLIN_5753, Npcs.CAVE_GOBLIN_5761) +
                (Npcs.CAVE_GOBLIN_5755..Npcs.CAVE_GOBLIN_5759) +
                (Npcs.CAVE_GOBLIN_5764..Npcs.CAVE_GOBLIN_5768),
        level = 36,
        minChance = 72,
        maxChance = 240,
        xp = 40.0,
        drops =
            DropTableFactory.build {
                main {
                    total(20)
                    obj(Items.COINS_995, quantityRange = 10..50, slots = 7)
                    obj(Items.BAT_SHISH, slots = 1)
                    obj(Items.COATED_FROGS_LEGS, slots = 1)
                    obj(Items.FINGERS, slots = 1)
                    obj(Items.FROGBURGER, slots = 1)
                    obj(Items.FROGSPAWN_GUMBO, slots = 1)
                    obj(Items.GREEN_GLOOP_SOUP, slots = 1)
                    obj(Items.BULLSEYE_LANTERN, slots = 1)
                    obj(Items.CAVE_GOBLIN_WIRE, slots = 1)
                    obj(Items.OIL_LANTERN, slots = 1)
                    obj(Items.SWAMP_TAR, slots = 1)
                    obj(Items.TINDERBOX_590, slots = 1)
                    obj(Items.UNLIT_TORCH, slots = 1)
                    obj(Items.IRON_ORE, quantityRange = 1..4, slots = 1)
                }
            },
        damage = 10..10,
        stunnedTicks = 8,
    ),
    MasterFarmer(
        objectIds = listOf(Npcs.MASTER_FARMER, Npcs.MASTER_FARMER_2235),
        level = 38,
        minChance = 90,
        maxChance = 240,
        xp = 43.0,
        drops =
            DropTableFactory.build {
                main {
                    total(2199)
                    obj(Items.POTATO_SEED, quantityRange = 1..4, slots = 400)
                    obj(Items.ONION_SEED, quantityRange = 1..3, slots = 300)
                    obj(Items.CABBAGE_SEED, quantityRange = 1..3, slots = 150)
                    obj(Items.TOMATO_SEED, quantityRange = 1..2, slots = 140)
                    obj(Items.SWEETCORN_SEED, quantityRange = 1..2, slots = 50)
                    obj(Items.STRAWBERRY_SEED, slots = 25)
                    obj(Items.WATERMELON_SEED, slots = 15)
                    obj(Items.BARLEY_SEED, quantityRange = 1..12, slots = 120)
                    obj(Items.HAMMERSTONE_SEED, quantityRange = 1..9, slots = 120)
                    obj(Items.ASGARNIAN_SEED, quantityRange = 1..6, slots = 90)
                    obj(Items.JUTE_SEED, quantityRange = 1..9, slots = 90)
                    obj(Items.YANILLIAN_SEED, quantityRange = 1..6, slots = 60)
                    obj(Items.KRANDORIAN_SEED, quantityRange = 1..6, slots = 30)
                    obj(Items.WILDBLOOD_SEED, quantityRange = 1..3, slots = 15)
                    obj(Items.MARIGOLD_SEED, slots = 100)
                    obj(Items.NASTURTIUM_SEED, slots = 70)
                    obj(Items.ROSEMARY_SEED, slots = 40)
                    obj(Items.WOAD_SEED, slots = 30)
                    obj(Items.LIMPWURT_SEED, slots = 25)
                    obj(Items.REDBERRY_SEED, slots = 85)
                    obj(Items.CADAVABERRY_SEED, slots = 60)
                    obj(Items.DWELLBERRY_SEED, slots = 40)
                    obj(Items.JANGERBERRY_SEED, slots = 20)
                    obj(Items.WHITEBERRY_SEED, slots = 7)
                    obj(Items.POISON_IVY_SEED, slots = 3)
                    obj(Items.GUAM_SEED, slots = 30)
                    obj(Items.MARRENTILL_SEED, slots = 25)
                    obj(Items.TARROMIN_SEED, slots = 15)
                    obj(Items.HARRALANDER_SEED, slots = 10)
                    obj(Items.RANARR_SEED, slots = 8)
                    obj(Items.TOADFLAX_SEED, slots = 5)
                    obj(Items.IRIT_SEED, slots = 3)
                    obj(Items.AVANTOE_SEED, slots = 2)
                    obj(Items.KWUARM_SEED, slots = 2)
                    obj(Items.SNAPDRAGON_SEED, slots = 1)
                    obj(Items.CADANTINE_SEED, slots = 1)
                    obj(Items.LANTADYME_SEED, slots = 1)
                    obj(Items.DWARF_WEED_SEED, slots = 1)
                    obj(Items.TORSTOL_SEED, slots = 1)
                    obj(Items.BITTERCAP_MUSHROOM_SPORE, slots = 5)
                    obj(Items.BELLADONNA_SEED, slots = 3)
                    obj(Items.CACTUS_SEED, slots = 1)
                }
            },
        damage = 30..30,
        stunnedTicks = 8,
        onCaught = listOf("Cor blimey, mate! What are ye doing in me pockets?"),
    ),
    Guard(
        objectIds =
            listOf(
                Npcs.GUARD,
                Npcs.GUARD_32,
                Npcs.GUARD_296,
                Npcs.GUARD_297,
                Npcs.GUARD_298,
                Npcs.GUARD_299,
                Npcs.GUARD_2699,
                Npcs.GUARD_2700,
                Npcs.GUARD_2701,
                Npcs.GUARD_2702,
                Npcs.GUARD_2703,
                Npcs.GUARD_3228,
                Npcs.GUARD_3229,
                Npcs.GUARD_3230,
                Npcs.GUARD_3231,
                Npcs.GUARD_3232,
                Npcs.GUARD_3233,
                Npcs.GUARD_3241,
                Npcs.GUARD_3407,
                Npcs.GUARD_3408,
                Npcs.GUARD_4307,
                Npcs.GUARD_4308,
                Npcs.GUARD_4309,
                Npcs.GUARD_4310,
                Npcs.GUARD_4311,
                Npcs.GUARD_5919,
                Npcs.GUARD_5920,
                Npcs.GUARD_8173,
            ),
        level = 40,
        minChance = 50,
        maxChance = 240,
        xp = 46.8,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 30)
                }
            },
        damage = 20..20,
        stunnedTicks = 8,
    ),
    FremennikCitizen(
        objectIds =
            listOf(
                Npcs.AGNAR,
                Npcs.BORROKAR,
                Npcs.FREIDIR,
                Npcs.INGA,
                Npcs.JENNELLA,
                Npcs.LANZIG,
                Npcs.LENSA,
                Npcs.PONTAK,
                Npcs.SASSILIK,
            ),
        level = 45,
        minChance = 65,
        maxChance = 240,
        xp = 65.0,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 40)
                }
            },
        damage = 20..20,
        stunnedTicks = 8,
    ),
    DesertBandit(
        objectIds = listOf(Npcs.BANDIT_1881, Npcs.BANDIT_1926, Npcs.BANDIT_1931, Npcs.BANDIT_6174, Npcs.BANDIT_6388),
        level = 53,
        minChance = 50,
        maxChance = 240,
        xp = 79.4,
        drops =
            DropTableFactory.build {
                main {
                    total(7)
                    obj(Items.COINS_995, quantity = 30, slots = 5)
                    obj(Items.ANTIPOISON_1)
                    obj(Items.LOCKPICK)
                }
            },
        damage = 30..30,
        stunnedTicks = 8,
    ),
    KnightOfArdougne(
        objectIds = listOf(Npcs.KNIGHT_OF_ARDOUGNE, Npcs.KNIGHT_OF_ARDOUGNE_26),
        level = 55,
        minChance = 50,
        maxChance = 240,
        xp = 84.3,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 50)
                }
            },
        damage = 30..30,
        stunnedTicks = 8,
    ),
    YanilleWatchman(
        objectIds = listOf(Npcs.WATCHMAN),
        level = 65,
        minChance = 15,
        maxChance = 160,
        xp = 137.5,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 60)
                    obj(Items.BREAD)
                }
            },
        damage = 30..30,
        stunnedTicks = 8,
    ),
    Paladin(
        objectIds = listOf(Npcs.PALADIN, Npcs.PALADIN_2256),
        level = 70,
        minChance = 50,
        maxChance = 150,
        xp = 151.8,
        drops =
            DropTableFactory.build {
                guaranteed {
                    obj(Items.COINS_995, quantity = 80)
                    obj(Items.CHAOS_RUNE, quantity = 2)
                }
            },
        damage = 30..30,
        stunnedTicks = 8,
    ),
    Gnome(
        objectIds =
            listOf(
                Npcs.GNOME,
                Npcs.GNOME_67,
                Npcs.GNOME_68,
                Npcs.GNOME_CHILD,
                Npcs.GNOME_CHILD_160,
                Npcs.GNOME_CHILD_161,
                Npcs.GNOME_WOMAN,
                Npcs.GNOME_WOMAN_169,
                Npcs.GNOME_6002,
            ),
        level = 75,
        minChance = 8,
        maxChance = 120,
        xp = 198.3,
        drops =
            DropTableFactory.build {
                main {
                    total(128)
                    obj(Items.KING_WORM, slots = 55)
                    obj(Items.COINS_995, quantity = 300, slots = 30)
                    obj(Items.SWAMP_TOAD, slots = 28)
                    obj(Items.GOLD_ORE, slots = 8)
                    obj(Items.EARTH_RUNE, slots = 5)
                    obj(Items.FIRE_ORB, slots = 2)
                }
            },
        damage = 10..10,
        stunnedTicks = 8,
    ),
    Hero(
        objectIds = listOf(Npcs.HERO),
        level = 80,
        minChance = 6,
        maxChance = 100,
        xp = 273.3,
        drops =
            DropTableFactory.build {
                main {
                    total(128)
                    obj(Items.COINS_995, quantityRange = 200..300, slots = 105)
                    obj(Items.DEATH_RUNE, quantity = 2, slots = 8)
                    obj(Items.JUG_OF_WINE, slots = 6)
                    obj(Items.BLOOD_RUNE, slots = 5)
                    obj(Items.FIRE_ORB, slots = 2)
                    obj(Items.DIAMOND, slots = 1)
                    obj(Items.GOLD_ORE, slots = 1)
                }
            },
        damage = 40..40,
        stunnedTicks = 10,
    ), ;

    fun rollDamage() = damage.random()

    fun roll(
        level: Int,
        adjustmentFactor: Double,
    ) = level.interpolate(
        (minChance * adjustmentFactor).toInt(),
        (
            maxChance *
                adjustmentFactor
        ).toInt(),
        1,
        99,
        255,
    )

    fun hasInventorySpace(player: Player): Boolean {
        return DropTableFactory.hasInventorySpaceForAnyDrop(player, objectIds.first(), DropTableType.PICKPOCKET)
            ?: false
    }
}
