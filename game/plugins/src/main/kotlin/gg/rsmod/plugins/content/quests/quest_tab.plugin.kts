package gg.rsmod.plugins.content.quests

import gg.rsmod.plugins.content.quests.Quest.Companion.getQuest
import gg.rsmod.plugins.content.quests.Quest.Companion.quests


on_login {
    player.setVarp(Varps.MAX_QUEST_POINTS, quests.sumOf { it.pointReward })
    player.setEvents(interfaceId = 190, component = 18, to = 399, setting = 30)
    /**
     * Set undeveloped quest stages to complete, then remove them here to implement them.
     */
    // slot 147
    player.setVarp(Varps.UNSTABLE_FOUNDATIONS_PROGRESS, 1000)
    // slot 159
    player.setVarp(Varps.BLACK_KNIGHTS_FORTRESS_PROGRESS, 4)
    // slot 1 - Cooks Assistant
    // Done.
    // slot 2
    player.setVarbit(Varbits.DEMON_SLAYER_PROGRESS, 3)
    // slot 3 - Doric's Quest
    // Done.
    // slot 4
    player.setVarp(Varps.DRAGON_SLAYER_PROGRESS, 10)
    // slot 5
    player.setVarp(Varps.ERNEST_THE_CHICKEN_PROGRESS, 3)
    // slot 6
    player.setVarbit(Varbits.GOBLIN_DIPLOMACY_PROGRESS, 6)
    // slot 7 - Imp Catcher
    // Done.
    // slot 8 - The Knight's Sword
    // Done.
    // slot 9 - Pirate's Treasure
    // Done.
    // slot 10
    player.setVarp(Varps.PRINCE_ALI_RESCUE_PROGRESS, 110)
    // slot 11
    // Done - player.setVarp(Varps.VARP_107, 5)
    // slot 13 - Rune Mysteries
    // Done.
    // slot 15
    player.setVarp(Varps.SHIELD_OF_ARRAV_PROGRESS_1, 7)
    player.setVarp(Varps.SHIELD_OF_ARRAV_PROGRESS_2, 4)
    // slot 16
    // Vampyre Slayer - player.setVarbit(Varbits.VARBIT_6914, 3)
    // Done.
    // slot 18
    player.setVarbit(Varbits.ANIMAL_MAGNETISM_PROGRESS, 240)
    // slot 19
    player.setVarbit(Varbits.BETWEEN_A_ROCK_PROGRESS, 110)
    // slot 20
    player.setVarp(Varps.BIG_CHOMPY_BIRD_HUNTING_PROGRESS, 65)
    // slot 21
    player.setVarp(Varps.BIOHAZARD_PROGRESS, 16)
    // slot 22
    player.setVarp(Varps.CABIN_FEVER_PROGRESS, 140)
    // slot 23
    player.setVarp(Varps.CLOCK_TOWER_PROGRESS, 8)
    // slot 24
    player.setVarbit(Varbits.CONTACT_PROGRESS, 130)
    // slot 25
    player.setVarbit(Varbits.ZOGRE_FLESH_EATERS_PROGRESS, 14)
    // slot 26
    player.setVarp(Varps.CREATURE_OF_FENKENSTRAIN_PROGRESS, 7)
    // slot 27
    player.setVarbit(Varbits.DARKNESS_OF_HALLOWVALE_PROGRESS, 320)
    // slot 28
    player.setVarbit(Varbits.DEATH_TO_THE_DORGESHUUN_PROGRESS, 13)
    // slot 29
    player.setVarp(Varps.DEATH_PLATEAU_PROGRESS, 80)
    // slot 30
    player.setVarbit(Varbits.DESERT_TREASURE_PROGRESS, 15)
    // slot 31
    player.setVarbit(Varbits.DEVIOUS_MINDS_PROGRESS, 80)
    // slot 32
    player.setVarp(Varps.THE_DIG_SITE_PROGRESS, 9)
    // slot 33 - Druidic Ritual
    // Done.
    // slot 34
    player.setVarp(Varps.DWARF_CANNON_PROGRESS, 11)
    // slot 35
    player.setVarp(Varps.EADGARS_RUSE_PROGRESS, 110)
    // slot 36
    player.setVarbit(Varbits.EAGLES_PEAK_PROGRESS, 40)
    // slot 37
    player.setVarp(Varps.ELEMENTAL_WORKSHOP_I_PROGRESS, 1.shl(20))
    // if (isBitFlagged(VARP[299], 20)) ...
    // if (!isBitFlagged(VARP[299], 1)) ...
    // slot 38
    player.setVarbit(Varbits.ELEMENTAL_WORKSHOP_II_PROGRESS, 11)
    // slot 39
    player.setVarbit(Varbits.ENAKHRAS_LAMENT_PROGRESS, 70)
    // slot 40
    player.setVarbit(Varbits.ELIGHTENED_JOURNEY_PROGRESS, 200)
    // slot 41
    player.setVarbit(Varbits.THE_EYES_OF_GLOUPHRIE_PROGRESS, 60)
    // slot 42
    player.setVarbit(Varbits.FAIRY_TALE_I_PROGRESS, 90)
    // slot 43
    player.setVarbit(Varbits.FAIRY_TALE_II_PROGRESS, 90)
    // slot 44
    player.setVarp(Varps.FAMILY_CREST_PROGRESS, 11)
    // slot 45
    player.setVarbit(Varbits.THE_FUED_PROGRESS, 28)
    // slot 46
    player.setVarp(Varps.FIGHT_ARENA_PROGRESS, 14)
    // slot 47
    player.setVarp(Varps.FISHING_CONTEST_PROGRESS, 5)
    // slot 48
    player.setVarbit(Varbits.FORGETTABLE_TALE_PROGRESS, 140)
    // slot 49
    player.setVarp(Varps.THE_FREMENNIK_TRIALS_PROGRESS, 10)
    // slot 50
    player.setVarp(Varps.WATERFALL_QUEST_PROGRESS, 10)
    // slot 51
    player.setVarbit(Varbits.GARDEN_OF_TRANQUILITY_PROGRESS, 60)
    // slot 52
    player.setVarp(Varps.GERTRUDES_CAT_PROGRESS, 6)
    // slot 53
    player.setVarbit(Varbits.GHOSTS_AHOY_PROGRESS, 8)
    // slot 54
    player.setVarbit(Varbits.THE_GIANT_DWARF_PROGRESS, 50)
    // slot 55
    player.setVarbit(Varbits.THE_GOLEM_PROGRESS, 10)
    // slot 56
    player.setVarp(Varps.THE_GRAND_TREE_PROGRESS, 160)
    // slot 57
    player.setVarbit(Varbits.THE_HAND_IN_THE_SAND_PROGRESS, 160)
    // slot 58
    player.setVarp(Varps.HAUNTED_MINE_PROGRESS, 11)
    // slot 59
    player.setVarp(Varps.HAZEEL_CULT_PROGRESS, 9)
    // slot 60
    player.setVarp(Varps.HEROES_QUEST_PROGRESS, 15)
    // slot 61
    player.setVarp(Varps.HOLY_GRAIL_PROGRESS, 10)
    // slot 62
    player.setVarbit(Varbits.HORROR_FROM_THE_DEEP_PROGRESS, 10)
    // slot 63
    player.setVarbit(Varbits.ICTHLARINS_LITTLE_HELPER_PROGRESS, 26)
    // slot 64
    player.setVarbit(Varbits.IN_AID_OF_THE_MYREQUE_PROGRESS, 430)
    // slot 65
    player.setVarp(Varps.IN_SEARCH_OF_THE_MYREQUE_PROGRESS, 105)
    // slot 66
    player.setVarp(Varps.JUNGLE_POTION_PROGRESS, 12)
    // slot 67
    player.setVarp(Varps.LEGENDS_QUEST_PROGRESS, 75)
    // slot 68 - Lost City
    // Done.
    // slot 69
    player.setVarbit(Varbits.THE_LOST_TRIBE_PROGRESS, 11)
    // slot 70
    player.setVarbit(Varbits.LUNAR_DIPLOMACY_PROGRESS, 190)
    // slot 71
    player.setVarbit(Varbits.MAKING_HISTORY_PROGRESS, 4)
    // slot 72
    player.setVarp(Varps.MERLINS_CRYSTAL_PROGRESS, 7)
    // slot 73
    player.setVarp(Varps.MONKEY_MADNESS_PROGRESS, 9)
    // slot 74
    player.setVarp(Varps.MONKS_FRIEND_PROGRESS, 80)
    // slot 75
    player.setVarbit(Varbits.MOUNTAIN_DAUGHTER_PROGRESS, 70)
    // slot 76
    player.setVarp(Varps.MOURNINGS_END_PART_I_PROGRESS, 8)
    // slot 77
    player.setVarbit(Varbits.MOURNINGS_END_PART_II_PROGRESS, 60)
    // slot 78
    player.setVarp(Varps.MURDER_MYSTERY_PROGRESS, 2)
    // slot 79
    player.setVarbit(Varbits.MY_ARMS_BIG_ADVENTURE_PROGRESS, 320)
    // slot 80
    player.setVarp(Varps.NATURE_SPIRIT_PROGRESS, 110)
    // slot 81
    player.setVarp(Varps.OBSERVATORY_QUEST_PROGRESS, 7)
    // slot 82
    player.setVarp(Varps.ONE_SMALL_FAVOUR_PROGRESS, 280)
    // slot 83
    player.setVarp(Varps.PLAGUE_CITY_PROGRESS, 29)
    // slot 84 - Priest in Peril
    // Done.
    // slot 85
    player.setVarbit(Varbits.RAG_AND_BONE_MAN_PROGRESS, 4)
    // slot 86
    player.setVarbit(Varbits.RATCATCHERS_PROGRESS, 127)
    // slot 87
    player.setVarbit(Varbits.RECIPE_FOR_DISASTER_PROGRESS, 5)
    // slot 88
    player.setVarbit(Varbits.RECRUITMENT_DRIVE_PROGRESS, 2)
    // slot 89
    player.setVarp(Varps.REGICIDE_PROGRESS, 15)
    // slot 90
    player.setVarp(Varps.ROVING_ELVES_PROGRESS, 6)
    // slot 91
    player.setVarbit(Varbits.ROYAL_TROUBLE_PROGRESS, 30)
    // slot 92
    player.setVarp(Varps.RUM_DEAL_PROGRESS, 19)
    // slot 93
    player.setVarp(Varps.SCORPION_CATCHER_PROGRESS, 6)
    // slot 94
    player.setVarp(Varps.SEA_SLUG_PROGRESS, 12)
    // slot 95
    player.setVarbit(Varbits.THE_SLUG_MENACE_PROGRESS, 14)
    // slot 96
    player.setVarp(Varps.SHADES_OF_MORTON_PROGRESS, 85)
    // slot 97
    player.setVarbit(Varbits.SHADOW_OF_THE_STORM_PROGRESS, 125)
    // slot 98
    player.setVarp(Varps.SHEEP_HERDER_PROGRESS, 3)
    // slot 99
    player.setVarp(Varps.SHILO_VILLAGE_PROGRESS, 15)
    // slot 100
    player.setVarbit(Varbits.A_SOULS_BANE_PROGRESS, 13)
    // slot 101
    player.setVarbit(Varbits.SPIRITS_OF_THE_ELID_PROGRESS, 60)
    // slot 102
    player.setVarbit(Varbits.SWAN_SONG_PROGRESS, 200)
    // slot 103
    player.setVarp(Varps.TAI_BWO_WANNAI_TRIO_PROGRESS, 6)
    // slot 104
    player.setVarbit(Varbits.A_TAIL_OF_TWO_CATS_PROGRESS, 70)
    // slot 105
    player.setVarbit(Varbits.TEARS_OF_GUTHIXS_PROGRESS, 2)
    // slot 106
    player.setVarp(Varps.TEMPLE_OF_IKOV_PROGRESS, 80)
    // slot 107
    player.setVarp(Varps.THRONE_OF_MISCELLANIA_PROGRESS, 100)
    // slot 108
    player.setVarp(Varps.THE_TOURIST_TRAP_PROGRESS, 30)
    // slot 109
    player.setVarp(Varps.WITCHS_HOUSE_PROGRESS, 7)
    // slot 110
    player.setVarp(Varps.TREE_GNOME_VILLAGE_PROGRESS, 9)
    // slot 111
    player.setVarp(Varps.TRIBAL_TOTEM_PROGRESS, 5)
    // slot 112
    player.setVarp(Varps.TROLL_ROMANCE_PROGRESS, 45)
    // slot 113
    player.setVarp(Varps.TROLL_STRONGHOLD_PROGRESS, 50)
    // slot 114
    player.setVarp(Varps.UNDERGROUND_PASS_PROGRESS, 10)
    // slot 115
    player.setVarbit(Varbits.WANTED_PROGRESS, 11)
    // slot 116
    player.setVarp(Varps.WATCHTOWER_PROGRESS, 13)
    // slot 117
    player.setVarbit(Varbits.COLD_WAR_PROGRESS, 135)
    // slot 118
    player.setVarbit(Varbits.THE_FREMENNIK_ISLES_PROGRESS, 340)
    // slot 119
    player.setVarbit(Varbits.TOWER_OF_LIFE_PROGRESS, 18)
    // slot 120
    player.setVarp(Varps.THE_GREAT_BRAIN_ROBBERY_PROGRESS, 130)
    // slot 121
    player.setVarbit(Varbits.WHAT_LIES_BELOW_PROGRESS, 150)
    // slot 122
    player.setVarbit(Varbits.OLAFS_QUEST_PROGRESS, 80)
    // slot 123
    player.setVarbit(Varbits.ANOTHER_SLICE_OF_HAM_PROGRESS, 11)
    // slot 124
    player.setVarbit(Varbits.DREAM_MENTOR_PROGRESS, 28)
    // slot 125
    player.setVarbit(Varbits.GRIM_TALES_PROGRESS, 60)
    // slot 126
    player.setVarbit(Varbits.KINGS_RANSOM_PROGRESS, 90)
    // slot 127
    player.setVarbit(Varbits.THE_PATH_OF_GLOUPHRIE_PROGRESS, 200)
    // slot 128
    player.setVarbit(Varbits.BACK_TO_MY_ROOTS_PROGRESS, 65)
    // slot 129
    player.setVarbit(Varbits.LAND_OF_THE_GOBLINS_PROGRESS, 18)
    // slot 130
    player.setVarbit(Varbits.DEALING_WITH_SCABARAS_PROGRESS, 700)
    // slot 131
    player.setVarbit(Varbits.WOLF_WHISTLE_PROGRESS, 110)
    // slot 132
    player.setVarbit(Varbits.AS_A_FIRST_RESORT_PROGRESS, 200)
    // slot 133
    player.setVarbit(Varbits.CATAPULT_CONSTRUCTION_PROGRESS, 60)
    // slot 134
    player.setVarbit(Varbits.KENNITHS_CONCERNS_PROGRESS, 100)
    // slot 135
    player.setVarbit(Varbits.LEGACY_OF_SEERGAZE_PROGRESS, 500)
    // slot 136
    player.setVarbit(Varbits.PERILS_OF_ICE_MOUNTAIN_PROGRESS, 150)
    // slot 137
    player.setVarbit(Varbits.TOKTZ_KET_DILL_PROGRESS, 63)
    // slot 138
    player.setVarbit(Varbits.SMOKING_KILLS_PROGRESS, 250)
    // slot 139
    player.setVarbit(Varbits.ROCKING_OUT_PROGRESS, 100)
    // slot 140
    player.setVarbit(Varbits.SPIRIT_OF_SUMMER_PROGRESS, 80)
    // slot 141
    player.setVarbit(Varbits.MEETING_HISTORY_PROGRESS, 20)
    // slot 142
    player.setVarbit(Varbits.ALL_FIRED_UP_PROGRESS, 90)
    // slot 143
    player.setVarbit(Varbits.SUMMERS_END_PROGRESS, 35)
    // slot 144
    player.setVarbit(Varbits.DEFENDER_OF_VARROCK_PROGRESS, 250)
    // slot 145
    player.setVarbit(Varbits.WHILE_GUTHIX_SLEEPS_PROGRESS, 910)
    // slot 146
    player.setVarbit(Varbits.IN_PYRE_NEED_PROGRESS, 30)
    // slot 148
    player.setVarbit(Varbits.MYTHS_OF_THE_WHITE_LANDS_PROGRESS, 60)
    // slot 149
    player.setVarbit(Varbits.GLORIOUS_MEMORIES_PROGRESS, 45)
    // slot 150
    player.setVarbit(Varbits.THE_TALE_OF_THE_MUSPAH_PROGRESS, 250)
    // slot 155
    player.setVarbit(Varbits.MISSING_MY_MUMMY_PROGRESS, 60)
    // slot 156
    player.setVarbit(Varbits.THE_CURSE_OF_ARRAV_PROGRESS, 240)
    // slot 157
    player.setVarbit(Varbits.THE_TEMPLE_AT_SENNTISTEN_PROGRESS, 90)
    // slot 151
    player.setVarbit(Varbits.HUNT_FOR_RED_RAKTUBER_PROGRESS, 140)
    // slot 152
    player.setVarbit(Varbits.THE_CHOSEN_COMMANDER_PROGRESS, 110)
    // slot 153
    player.setVarbit(Varbits.SWEPT_AWAY_PROGRESS, 50)
    // slot 154
    player.setVarbit(Varbits.FUN_N_SEEK_PROGRESS, 5)
    // slot 158
    player.setVarbit(Varbits.FAIRY_TALE_III_PROGRESS, 180)
    // slot 160
    player.setVarbit(Varbits.FORGIVENESS_OF_A_CHAOS_DWARF_PROGRESS, 90)
    // slot 161
    player.setVarbit(Varbits.WITHIN_THE_LIGHT_PROGRESS, 46)
    // slot 162
    player.setVarbit(Varbits.NOMADS_REQUIEM_PROGRESS, 12)
    // slot 163
    player.setVarbit(Varbits.BLOOD_RUNS_DEEP_PROGRESS, 147)
    // slot 165
    player.setVarbit(Varbits.RUNE_MECHANICS_PROGRESS, 35)
    // slot 167
    player.setVarbit(Varbits.BUYERS_AND_CELLARS_PROGRESS, 30)
    // slot 168
    player.setVarbit(Varbits.LOVE_STORY_PROGRESS, 150)
    // slot 170
    player.setVarbit(Varbits.THE_BLOOD_PACT_PROGRESS, 60)
    // slot 171
    player.setVarbit(Varbits.QUIET_BEFORE_THE_STORM_PROGRESS, 90)
    // slot 172
    player.setVarbit(Varbits.ELEMENTAL_WORKSHOP_III_PROGRESS, 5)
    // slot 173
    player.setVarbit(Varbits.A_VOID_DANCE_PROGRESS, 63)
    // slot 14
    player.setVarbit(Varbits.KING_OF_THE_DWARVES_PROGRESS, 90)
    // slot 174
    player.setVarbit(Varbits.THE_VOID_STARES_BACK_PROGRESS, 40)
    // slot 17
    player.setVarbit(Varbits.GUNNARS_GROUND_PROGRESS, 100)
    // slot 176
    player.setVarbit(Varbits.RITUAL_OF_THE_MAHJARRAT_PROGRESS, 400)
    // slot 179
    player.setVarbit(Varbits.DO_NO_EVIL_PROGRESS, 315)
    // slot 12
    player.setVarbit(Varbits.THE_PRISONER_OF_GLOUPHRIE_PROGRESS, 120)
    // slot 180
    player.setVarbit(Varbits.ELEMENTAL_WORKSHOP_IV_PROGRESS, 9)
    // slot 181
    player.setVarbit(Varbits.A_CLOCKWORK_SYRINGE_PROGRESS, 210)
    // slot 182
    player.setVarbit(Varbits.DEADLIEST_CATCH_PROGRESS, 50)
    // slot 183
    player.setVarbit(Varbits.SALT_IN_THE_WOUND_PROGRESS, 250)
    // slot 184
    player.setVarbit(Varbits.THE_BRANCHES_OF_DARKMEYER_PROGRESS, 170)
}

on_button(interfaceId = 190, 18) {
    val quest = getQuest(player.getInteractingSlot()) ?: return@on_button
    if (!player.startedQuest(quest)) {
        player.buildQuestOverview(quest)
        return@on_button
    }
    player.buildQuestStages(quest)
}

on_button(interfaceId = 178, component = 57) {
    player.setComponentHidden(interfaceId = 178, component = 56, hidden = true)
    player.setComponentHidden(interfaceId = 178, component = 58, hidden = false)
}

on_button(interfaceId = 178, component = 30) {
    player.setComponentHidden(interfaceId = 178, component = 64, hidden = true)
    player.setComponentHidden(interfaceId = 178, component = 65, hidden = false)
}
