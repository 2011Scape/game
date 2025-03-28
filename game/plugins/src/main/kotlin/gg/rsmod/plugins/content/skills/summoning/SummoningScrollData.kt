package gg.rsmod.plugins.content.skills.summoning

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs

enum class SummoningScrollData(
    val level: Int,
    val scroll: Int,
    val familiars: Array<Int>,
    val pouches: Array<Int>,
    val creationExperience: Double,
    val useExperience: Double,
    val specialPoints: Int,
    val numSwapShards: Int,
    val numNeededToSwap: Int = 1,
    val greyedScroll: Int = 0,
    val scrollCreationSlot: Int = -1,
) {
    HOWL_SCROLL(
        1,
        Items.HOWL_SCROLL,
        arrayOf(Npcs.SPIRIT_WOLF),
        arrayOf(Items.SPIRIT_WOLF_POUCH),
        0.1,
        0.1,
        3,
        1,
        3,
        Items.HOWL_SCROLL_GREYED,
    ),
    DREADFOWL_STRIKE_SCROLL(
        4,
        Items.DREADFOWL_STRIKE_SCROLL,
        arrayOf(Npcs.DREADFOWL),
        arrayOf(Items.DREADFOWL_POUCH),
        0.1,
        0.1,
        3,
        1,
        3,
        Items.DREADFOWL_STRIKE_SCROLL_GREYED,
    ),
    EGG_SPAWN_SCROLL(
        10,
        Items.EGG_SPAWN_SCROLL,
        arrayOf(Npcs.SPIRIT_SPIDER),
        arrayOf(Items.SPIRIT_SPIDER_POUCH),
        0.2,
        0.2,
        6,
        1,
        3,
        Items.EGG_SPAWN_SCROLL_GREYED,
    ),
    SLIME_SPRAY_SCROLL(
        13,
        Items.SLIME_SPRAY_SCROLL,
        arrayOf(Npcs.THORNY_SNAIL),
        arrayOf(Items.THORNY_SNAIL_POUCH),
        0.2,
        0.2,
        3,
        1,
        2,
        Items.SLIME_SPRAY_SCROLL_GREYED,
    ),
    STONY_SHELL_SCROLL(
        16,
        Items.STONY_SHELL_SCROLL,
        arrayOf(Npcs.GRANITE_CRAB),
        arrayOf(Items.GRANITE_CRAB_POUCH),
        0.2,
        0.2,
        12,
        1,
        3,
        Items.STONY_SHELL_SCROLL_GREYED,
    ),
    PESTER_SCROLL(
        17,
        Items.PESTER_SCROLL,
        arrayOf(Npcs.SPIRIT_MOSQUITO),
        arrayOf(Items.SPIRIT_MOSQUITO_POUCH),
        0.5,
        0.5,
        3,
        1,
        18,
        Items.PESTER_SCROLL_GREYED,
    ),
    ELECTRIC_LASH_SCROLL(
        18,
        Items.ELECTRIC_LASH_SCROLL,
        arrayOf(Npcs.DESERT_WYRM),
        arrayOf(Items.DESERT_WYRM_POUCH),
        0.4,
        0.4,
        6,
        2,
        greyedScroll = Items.ELECTRIC_LASH_SCROLL_GREYED,
    ),
    VENOM_SHOT_SCROLL(
        19,
        Items.VENOM_SHOT_SCROLL,
        arrayOf(Npcs.SPIRIT_SCORPION),
        arrayOf(Items.SPIRIT_SCORPION_POUCH),
        0.9,
        1.0,
        6,
        3,
        greyedScroll = Items.VENOM_SHOT_SCROLL_GREYED,
    ),
    FIREBALL_ASSAULT_SCROLL(
        22,
        Items.FIREBALL_ASSAULT_SCROLL,
        arrayOf(Npcs.SPIRIT_TZKIH),
        arrayOf(Items.SPIRIT_TZKIH_POUCH),
        1.1,
        1.1,
        6,
        3,
        greyedScroll = Items.FIREBALL_ASSAULT_GREYED,
    ),
    CHEESE_FEAST_SCROLL(
        23,
        Items.CHEESE_FEAST_SCROLL,
        arrayOf(Npcs.ALBINO_RAT),
        arrayOf(Items.ALBINO_RAT_POUCH),
        2.3,
        2.3,
        6,
        4,
        greyedScroll = Items.CHEESE_FEAST_SCROLL_GREYED,
    ),
    SANDSTORM_SCROLL(
        25,
        Items.SANDSTORM_SCROLL,
        arrayOf(Npcs.SPIRIT_KALPHITE),
        arrayOf(Items.SPIRIT_KALPHITE_POUCH),
        2.5,
        2.5,
        6,
        2,
        greyedScroll = Items.SANDSTORM_SCROLL_GREYED,
    ),
    GENERATE_COMPOST_SCROLL(
        28,
        Items.GENERATE_COMPOST_SCROLL,
        arrayOf(Npcs.COMPOST_MOUND),
        arrayOf(Items.COMPOST_MOUND_POUCH),
        0.6,
        0.6,
        12,
        2,
        greyedScroll = Items.GENERATE_COMPOST_SCROLL_GREYED,
    ),
    EXPLODE_SCROLL(
        29,
        Items.EXPLODE_SCROLL,
        arrayOf(Npcs.GIANT_CHINCHOMPA),
        arrayOf(Items.GIANT_CHINCHOMPA_POUCH),
        2.9,
        2.9,
        3,
        4,
        greyedScroll = Items.EXPLODE_SCROLL_GREYED,
    ),
    VAMPIRE_TOUCH_SCROLL(
        31,
        Items.VAMPYRE_TOUCH_SCROLL,
        arrayOf(Npcs.VAMPYRE_BAT),
        arrayOf(Items.VAMPYRE_BAT_POUCH),
        1.5,
        1.6,
        4,
        4,
        greyedScroll = Items.VAMPIRE_TOUCH_SCROLL_GREYED,
    ),
    INSANE_FEROCITY_SCROLL(
        32,
        Items.INSANE_FEROCITY_SCROLL,
        arrayOf(Npcs.HONEY_BADGER),
        arrayOf(Items.HONEY_BADGER_POUCH),
        1.6,
        1.6,
        12,
        4,
        greyedScroll = Items.INSANE_FEROCITY_SCROLL_GREYED,
    ),
    MULTICHOP_SCROLL(
        33,
        Items.MULTICHOP_SCROLL,
        arrayOf(Npcs.BEAVER),
        arrayOf(Items.BEAVER_POUCH),
        0.7,
        0.7,
        3,
        4,
        greyedScroll = Items.MULTICHOP_SCROLL_GREYED,
    ),
    CALL_TO_ARMS_SCROLL(
        34,
        Items.CALL_TO_ARMS_SCROLL,
        arrayOf(Npcs.VOID_RAVAGER, Npcs.VOID_SHIFTER, Npcs.VOID_SPINNER, Npcs.VOID_TORCHER),
        arrayOf(Items.VOID_RAVAGER_POUCH, Items.VOID_SHIFTER_POUCH, Items.VOID_SPINNER_POUCH, Items.VOID_TORCHER_POUCH),
        0.7,
        0.7,
        3,
        4,
        greyedScroll = Items.CALL_TO_ARMS_SCROLL_GREYED,
    ),
    BRONZE_BULL_RUSH_SCROLL(
        36,
        Items.BRONZE_BULL_RUSH_SCROLL,
        arrayOf(Npcs.BRONZE_MINOTAUR),
        arrayOf(Items.BRONZE_MINOTAUR_POUCH),
        3.6,
        3.6,
        6,
        5,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 102,
    ),
    UNBURDEN_SCROLL(
        40,
        Items.UNBURDEN_SCROLL,
        arrayOf(Npcs.BULL_ANT),
        arrayOf(Items.BULL_ANT_POUCH),
        0.6,
        0.6,
        12,
        1,
        2,
        Items.UNBURDEN_SCROLL_GREYED,
    ),
    HERBCALL_SCROLL(
        41,
        Items.HERBCALL_SCROLL,
        arrayOf(Npcs.MACAW),
        arrayOf(Items.MACAW_POUCH),
        0.8,
        0.8,
        12,
        4,
        greyedScroll = Items.HERBCALL_SCROLL_GREYED,
    ),
    EVIL_FLAMES_SCROLL(
        42,
        Items.EVIL_FLAMES_SCROLL,
        arrayOf(Npcs.EVIL_TURNIP),
        arrayOf(Items.EVIL_TURNIP_POUCH),
        2.1,
        2.1,
        6,
        5,
        greyedScroll = Items.EVIL_FLAMES_SCROLL_GREYED,
    ),
    PETRIFYING_GAZE_SCROLL(
        43,
        Items.PETRIFYING_GAZE_SCROLL,
        arrayOf(
            Npcs.SPIRIT_COCKATRICE,
            Npcs.SPIRIT_GUTHATRICE,
            Npcs.SPIRIT_SARATRICE,
            Npcs.SPIRIT_ZAMATRICE,
            Npcs.SPIRIT_PENGATRICE,
            Npcs.SPIRIT_CORAXATRICE,
            Npcs.SPIRIT_VULATRICE,
        ),
        arrayOf(
            Items.SP_COCKATRICE_POUCH,
            Items.SP_GUTHATRICE_POUCH,
            Items.SP_SARATRICE_POUCH,
            Items.SP_ZAMATRICE_POUCH,
            Items.SP_PENGATRICE_POUCH,
            Items.SP_CORAXATRICE_POUCH,
            Items.SP_VULATRICE_POUCH,
        ),
        0.9,
        0.9,
        3,
        4,
        greyedScroll = Items.PETRIFYING_GAZE_SCROLL_GREYED,
    ),
    IRON_BULL_RUSH_SCROLL(
        46,
        Items.IRON_BULL_RUSH_SCROLL,
        arrayOf(Npcs.IRON_MINOTAUR),
        arrayOf(Items.IRON_MINOTAUR_POUCH),
        4.6,
        4.6,
        6,
        7,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 157,
    ),
    IMMENSE_HEAT_SCROLL(
        46,
        Items.IMMENSE_HEAT_SCROLL,
        arrayOf(Npcs.PYRELORD),
        arrayOf(Items.PYRELORD_POUCH),
        2.3,
        2.3,
        6,
        6,
        greyedScroll = Items.IMMENSE_HEAT_SCROLL_GREYED,
    ),
    THIEVING_FINGERS_SCROLL(
        47,
        Items.THIEVING_FINGERS_SCROLL,
        arrayOf(Npcs.MAGPIE),
        arrayOf(Items.MAGPIE_POUCH),
        0.9,
        0.9,
        12,
        4,
        greyedScroll = Items.THIEVING_FINGERS_SCROLL_GREYED,
    ),
    BLOOD_DRAIN_SCROLL(
        49,
        Items.BLOOD_DRAIN_SCROLL,
        arrayOf(Npcs.BLOATED_LEECH),
        arrayOf(Items.BLOATED_LEECH_POUCH),
        2.4,
        2.5,
        6,
        6,
        greyedScroll = Items.BLOOD_DRAIN_SCROLL_GREYED,
    ),
    TIRELESS_RUN_SCROLL(
        52,
        Items.TIRELESS_RUN_SCROLL,
        arrayOf(Npcs.SPIRIT_TERRORBIRD),
        arrayOf(Items.SPIRIT_TERRORBIRD_POUCH),
        0.8,
        0.8,
        8,
        1,
        2,
        Items.TIRELESS_RUN_SCROLL_GREYED,
    ),
    ABYSSAL_DRAIN_SCROLL(
        54,
        Items.ABYSSAL_DRAIN_SCROLL,
        arrayOf(Npcs.ABYSSAL_PARASITE),
        arrayOf(Items.ABYSSAL_PARASITE_POUCH),
        1.1,
        1.1,
        6,
        5,
        greyedScroll = Items.ABYSSAL_DRAIN_SCROLL_GREYED,
    ),
    DISSOLVE_SCROLL(
        55,
        Items.DISSOLVE_SCROLL,
        arrayOf(Npcs.SPIRIT_JELLY),
        arrayOf(Items.SPIRIT_JELLY_POUCH),
        5.5,
        5.5,
        6,
        8,
        greyedScroll = Items.DISSOLVE_SCROLL_GREYED,
    ),
    FISH_RAIN_SCROLL(
        56,
        Items.FISH_RAIN_SCROLL,
        arrayOf(Npcs.IBIS),
        arrayOf(Items.IBIS_POUCH),
        1.1,
        1.1,
        12,
        6,
        greyedScroll = Items.FISH_RAIN_SCROLL_GREYED,
    ),
    STEEL_BULL_RUSH_SCROLL(
        56,
        Items.STEEL_BULL_RUSH_SCROLL,
        arrayOf(Npcs.STEEL_MINOTAUR),
        arrayOf(Items.STEEL_MINOTAUR_POUCH),
        5.6,
        5.6,
        6,
        7,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 192,
    ),
    AMBUSH_SCROLL(
        57,
        Items.AMBUSH_SCROLL,
        arrayOf(Npcs.SPIRIT_KYATT),
        arrayOf(Items.SPIRIT_KYATT_POUCH),
        5.7,
        5.7,
        3,
        8,
        greyedScroll = Items.AMBUSH_SCROLL_GREYED,
    ),
    RENDING_SCROLL(
        57,
        Items.RENDING_SCROLL,
        arrayOf(Npcs.SPIRIT_LARUPIA),
        arrayOf(Items.SPIRIT_LARUPIA_POUCH),
        5.7,
        5.7,
        3,
        8,
        greyedScroll = Items.RENDING_SCROLL_GREYED,
    ),
    GOAD_SCROLL(
        57,
        Items.GOAD_SCROLL,
        arrayOf(Npcs.SPIRIT_GRAAHK),
        arrayOf(Items.SPIRIT_GRAAHK_POUCH),
        5.7,
        5.7,
        6,
        8,
        greyedScroll = Items.GOAD_SCROLL_GREYED,
    ),
    DOOMSPHERE_SCROLL(
        58,
        Items.DOOMSPHERE_SCROLL,
        arrayOf(Npcs.KARAMTHULHU_OVERLORD),
        arrayOf(Items.KARAM_OVERLORD_POUCH),
        5.8,
        5.8,
        3,
        8,
        greyedScroll = Items.DOOMSPHERE_SCROLL_GREYED,
    ),
    DUST_CLOUD_SCROLL(
        61,
        Items.DUST_CLOUD_SCROLL,
        arrayOf(Npcs.SMOKE_DEVIL),
        arrayOf(Items.SMOKE_DEVIL_POUCH),
        3.0,
        3.1,
        6,
        7,
        greyedScroll = Items.DUST_CLOUD_SCROLL_GREYED,
    ),
    ABYSSAL_STEALTH_SCROLL(
        62,
        Items.ABYSSAL_STEALTH_SCROLL,
        arrayOf(Npcs.ABYSSAL_LURKER),
        arrayOf(Items.ABYSSAL_LURKER_POUCH),
        1.9,
        1.9,
        20,
        6,
        greyedScroll = Items.ABYSSAL_STEALTH_SCROLL_GREYED,
    ),
    OPHIDIAN_INCUBATION_SCROLL(
        63,
        Items.OPH_INCUBATION_SCROLL,
        arrayOf(Npcs.SPIRIT_COBRA),
        arrayOf(Items.SPIRIT_COBRA_POUCH),
        3.1,
        3.2,
        3,
        6,
        greyedScroll = Items.OPHIDIAN_INCUBATION_SCROLL_GREYED,
    ),
    POISONOUS_BLAST_SCROLL(
        64,
        Items.POISONOUS_BLAST_SCROLL,
        arrayOf(Npcs.STRANGER_PLANT),
        arrayOf(Items.STRANGER_PLANT_POUCH),
        3.2,
        3.2,
        6,
        7,
        greyedScroll = Items.POISONOUS_BLAST_SCROLL_GREYED,
    ),
    MITHRIL_BULL_RUSH_SCROLL(
        66,
        Items.MITH_BULL_RUSH_SCROLL,
        arrayOf(Npcs.MITHRIL_MINOTAUR),
        arrayOf(Items.MITHRIL_MINOTAUR_POUCH),
        6.6,
        6.6,
        6,
        8,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 242,
    ),
    TOAD_BARK_SCROLL(
        66,
        Items.TOAD_BARK_SCROLL,
        arrayOf(Npcs.BARKER_TOAD),
        arrayOf(Items.BARKER_TOAD_POUCH),
        1.0,
        1.0,
        6,
        1,
        2,
        Items.TOAD_BARK_SCROLL_GREYED,
    ),
    TESTUDO_SCROLL(
        67,
        Items.TESTUDO_SCROLL,
        arrayOf(Npcs.WAR_TORTOISE),
        arrayOf(Items.WAR_TORTOISE_POUCH),
        0.7,
        0.7,
        20,
        1,
        18,
        Items.TESTUDO_SCROLL_GREYED,
    ),
    SWALLOW_WHOLE_SCROLL(
        68,
        Items.SWALLOW_WHOLE_SCROLL,
        arrayOf(Npcs.BUNYIP),
        arrayOf(Items.BUNYIP_POUCH),
        1.4,
        1.4,
        3,
        6,
        greyedScroll = Items.SWALLOW_WHOLE_SCROLL_GREYED,
    ),
    FRUITFALL_SCROLL(
        69,
        Items.FRUITFALL_SCROLL,
        arrayOf(Npcs.FRUIT_BAT),
        arrayOf(Items.FRUIT_BAT_POUCH),
        1.4,
        1.4,
        6,
        7,
        greyedScroll = Items.FRUITFALL_SCROLL_GREYED,
    ),
    FAMINE_SCROLL(
        70,
        Items.FAMINE_SCROLL,
        arrayOf(Npcs.RAVENOUS_LOCUST),
        arrayOf(Items.RAVENOUS_LOCUST_POUCH),
        1.5,
        1.5,
        12,
        6,
        greyedScroll = Items.FAMINE_SCROLL_GREYED,
    ),
    ARCTIC_BLAST_SCROLL(
        71,
        Items.ARCTIC_BLAST_SCROLL,
        arrayOf(Npcs.ARCTIC_BEAR),
        arrayOf(Items.ARCTIC_BEAR_POUCH),
        1.1,
        1.1,
        6,
        1,
        2,
        Items.ARCTIC_BLAST_SCROLL_GREYED,
    ),
    RISH_FROM_THE_ASHES_SCROLL(
        72,
        Items.RISE_FROM_THE_ASHES_SCROLL,
        arrayOf(Npcs.PHOENIX),
        arrayOf(Items.PHOENIX_POUCH),
        8.0,
        8.0,
        12,
        9,
        greyedScroll = Items.RISE_FROM_THE_ASHES_SCROLL_GREYED,
    ),
    VOLCANIC_STRENGTH_SCROLL(
        73,
        Items.VOLCANIC_STR_SCROLL,
        arrayOf(Npcs.OBSIDIAN_GOLEM),
        arrayOf(Items.OBSIDIAN_GOLEM_POUCH),
        7.3,
        7.3,
        12,
        10,
        greyedScroll = Items.VOLCANIC_STRENGTH_SCROLL_GREYED,
    ),
    CRUSHING_CLAW_SCROLL(
        74,
        Items.CRUSHING_CLAW_SCROLL,
        arrayOf(Npcs.GRANITE_LOBSTER),
        arrayOf(Items.GRANITE_LOBSTER_POUCH),
        3.7,
        3.7,
        6,
        9,
        greyedScroll = Items.CRUSHING_CLAW_SCROLL_GREYED,
    ),
    MANTIS_STRIKE_SCROLL(
        75,
        Items.MANTIS_STRIKE_SCROLL,
        arrayOf(Npcs.PRAYING_MANTIS),
        arrayOf(Items.PRAYING_MANTIS_POUCH),
        3.7,
        3.8,
        6,
        9,
        greyedScroll = Items.MANTIS_STRIKE_SCROLL_GREYED,
    ),
    INFERNO_SCROLL(
        76,
        Items.INFERNO_SCROLL,
        arrayOf(Npcs.FORGE_REGENT),
        arrayOf(Items.FORGE_REGENT_POUCH),
        1.5,
        1.5,
        6,
        7,
        greyedScroll = Items.INFERNO_SCROLL_GREYED,
    ),
    ADAMANT_BULL_RUSH_SCROLL(
        76,
        Items.ADDY_BULL_RUSH_SCROLL,
        arrayOf(Npcs.ADAMANT_MINOTAUR),
        arrayOf(Items.ADAMANT_MINOTAUR_POUCH),
        7.6,
        7.6,
        6,
        8,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 302,
    ),
    DEADLY_CLAW_SCROLL(
        77,
        Items.DEADLY_CLAW_SCROLL,
        arrayOf(Npcs.TALON_BEAST),
        arrayOf(Items.TALON_BEAST_POUCH),
        11.4,
        11.7,
        6,
        9,
        greyedScroll = Items.DEADLY_CLAW_SCROLL_GREYED,
    ),
    ACORN_MISSILE_SCROLL(
        78,
        Items.ACORN_MISSILE_SCROLL,
        arrayOf(Npcs.GIANT_ENT),
        arrayOf(Items.GIANT_ENT_POUCH),
        1.6,
        1.6,
        6,
        6,
        greyedScroll = Items.ACORN_MISSLE_SCROLL_GREYED,
    ),
    TITANS_CONSTITUTION_SCROLL(
        79,
        Items.TITANS_CON_SCROLL,
        arrayOf(Npcs.FIRE_TITAN, Npcs.ICE_TITAN, Npcs.MOSS_TITAN),
        arrayOf(Items.FIRE_TITAN_POUCH, Items.ICE_TITAN_POUCH, Items.MOSS_TITAN_POUCH),
        7.9,
        7.9,
        20,
        11,
        greyedScroll = Items.TITANS_CON_SCROLL_GREYED,
    ),
    REGROWTH_SCROLL(
        80,
        Items.REGROWTH_SCROLL,
        arrayOf(Npcs.HYDRA),
        arrayOf(Items.HYDRA_POUCH),
        1.6,
        1.6,
        6,
        7,
        greyedScroll = Items.REGROWTH_SCROLL_GREYED,
    ),
    SPIKE_SHOT_SCROLL(
        83,
        Items.SPIKE_SHOT_SCROLL,
        arrayOf(Npcs.SPIRIT_DAGANNOTH),
        arrayOf(Items.SPIRIT_DAGANNOTH_POUCH),
        4.1,
        4.2,
        6,
        1,
        18,
        Items.SPIKE_SHOT_SCROLL_GREYED,
    ),
    EBON_THUNDER_SCROLL(
        83,
        Items.EBON_THUNDER_SCROLL,
        arrayOf(Npcs.LAVA_TITAN),
        arrayOf(Items.LAVA_TITAN_POUCH),
        8.3,
        8.3,
        4,
        12,
        greyedScroll = Items.EBON_THUNDER_SCROLL_GREYED,
    ),
    SWAMP_PLAGUE_SCROLL(
        85,
        Items.SWAMP_PLAGUE_SCROLL,
        arrayOf(Npcs.SWAMP_TITAN),
        arrayOf(Items.SWAMP_TITAN_POUCH),
        4.1,
        4.2,
        6,
        8,
        greyedScroll = Items.SWAMP_PLAGUE_SCROLL_GREYED,
    ),
    RUNE_BULL_RUSH_SCROLL(
        86,
        Items.RUNE_BULL_RUSH_SCROLL,
        arrayOf(Npcs.RUNE_MINOTAUR),
        arrayOf(Items.RUNE_MINOTAUR_POUCH),
        8.6,
        8.6,
        6,
        6,
        greyedScroll = Items.BULL_RUSH_SCROLL_GREYED,
        scrollCreationSlot = 352,
    ),
    HEALING_AURA_SCROLL(
        88,
        Items.HEALING_AURA_SCROLL,
        arrayOf(Npcs.UNICORN_STALLION),
        arrayOf(Items.UNICORN_STALLION_POUCH),
        1.8,
        1.8,
        20,
        7,
        greyedScroll = Items.HEALING_AURA_SCROLL_GREYED,
    ),
    BOIL_SCROLL(
        89,
        Items.BOIL_SCROLL,
        arrayOf(Npcs.GEYSER_TITAN),
        arrayOf(Items.GEYSER_TITAN_POUCH),
        8.9,
        8.9,
        6,
        12,
        greyedScroll = Items.BOIL_SCROLL_GREYED,
    ),
    MAGIC_FOCUS_SCROLL(
        92,
        Items.MAGIC_FOCUS_SCROLL,
        arrayOf(Npcs.WOLPERTINGER),
        arrayOf(Items.WOLPERTINGER_POUCH),
        4.6,
        4.6,
        20,
        11,
        greyedScroll = Items.MAGIC_FOCUS_GREYED,
    ),
    ESSENCE_SHIPMENT_SCROLL(
        93,
        Items.ESSENCE_SHIPMENT_SCROLL,
        arrayOf(Npcs.ABYSSAL_TITAN),
        arrayOf(Items.ABYSSAL_TITAN_POUCH),
        1.9,
        1.9,
        6,
        6,
        greyedScroll = Items.ESSENCE_SHIPMENT_SCROLL_GREYED,
    ),
    IRON_WITHIN_SCROLL(
        95,
        Items.IRON_WITHIN_SCROLL,
        arrayOf(Npcs.IRON_TITAN),
        arrayOf(Items.IRON_TITAN_POUCH),
        4.7,
        4.8,
        12,
        11,
        greyedScroll = Items.IRON_WITHIN_SCROLL_GREYED,
    ),
    WINTER_STORAGE_SCROLL(
        96,
        Items.WINTER_STORAGE_SCROLL,
        arrayOf(Npcs.PACK_YAK),
        arrayOf(Items.PACK_YAK_POUCH),
        4.8,
        4.8,
        12,
        11,
        greyedScroll = Items.WINTER_STORAGE_SCROLL_GREYED,
    ),
    STEEL_OF_LEGENDS_SCROLL(
        99,
        Items.STEEL_OF_LEGENDS_SCROLL,
        arrayOf(Npcs.STEEL_TITAN),
        arrayOf(Items.STEEL_TITAN_POUCH),
        4.9,
        5.0,
        12,
        9,
        greyedScroll = Items.STEEL_OF_LEGENDS_SCROLL_GREYED,
    ),
    ;

    companion object {
        val values = enumValues<SummoningScrollData>()

        fun getDataByScrollId(id: Int): SummoningScrollData? {
            for (data in values) {
                if (data.scroll == id) {
                    return data
                }
            }
            return null
        }

        fun getDataByGreyedScrollIdOrSlotNum(
            id: Int,
            slotNum: Int,
        ): SummoningScrollData? {
            // Some scrolls use the same greyed scroll item in the interface
            // Need to handle these using their position in the interface instead
            val duplicatedScrolls =
                arrayOf(
                    Items.BULL_RUSH_SCROLL_GREYED,
                )

            if (id in duplicatedScrolls) return getDataByInterfaceSlot(slotNum)

            for (data in values) {
                if (data.greyedScroll == id) {
                    return data
                }
            }
            return null
        }

        fun getDataByInterfaceSlot(slotNum: Int): SummoningScrollData? {
            for (data in values) {
                if (data.scrollCreationSlot == slotNum) {
                    return data
                }
            }
            return null
        }

        fun getIngredientString(
            id: Int,
            player: Player,
        ): String {
            val scroll = SummoningScrollData.getDataByScrollId(id) ?: return "You need nothing to craft this scroll."
            val def = player.world.definitions
            val pouches = scroll.pouches
            val pouchNames = mutableListOf<String>()
            pouches.forEach { pouch ->
                pouchNames.add(Item(pouch).getName(def).lowercase())
            }

            return "You need 1 ${pouchNames.joinToString()} to create this scroll."
        }
    }
}
