package gg.rsmod.plugins.content.quests

import gg.rsmod.plugins.content.quests.Quest.Companion.getQuest
import gg.rsmod.plugins.content.quests.Quest.Companion.quests


on_login {
    player.setVarp(MAX_QUEST_POINT_VARP, quests.sumOf { it.pointReward })
    player.setEvents(interfaceId = 190, component = 18, to = 399, setting = 30)
    /**
     * Set undeveloped quest stages to complete, then remove them here to implement them.
     */
    // slot 147
    player.setVarp(281, 1000)
    // slot 159
    player.setVarp(130, 4)
    // slot 1 - Cooks Assistant
    // Done.
    // slot 2
    player.setVarbit(2561, 3)
    // slot 3 - Doric's Quest
    // Done.
    // slot 4
    player.setVarp(176, 10)
    // slot 5
    player.setVarp(32, 3)
    // slot 6
    player.setVarbit(2378, 6)
    // slot 7 - Imp Catcher
    // Done.
    // slot 8 - The Knight's Sword
    // Done.
    // slot 9
    player.setVarp(71, 4)
    // slot 10
    player.setVarp(273, 110)
    // slot 11
    // Done - player.setVarp(107, 5)
    // slot 13 - Rune Mysteries
    // Done.
    // slot 15
    player.setVarp(145, 7)
    player.setVarp(146, 4)
    // slot 16
    // Vampyre Slayer - player.setVarbit(6914, 3)
    // Done.
    // slot 18
    player.setVarbit(3185, 240)
    // slot 19
    player.setVarbit(299, 110)
    // slot 20
    player.setVarp(293, 65)
    // slot 21
    player.setVarp(68, 16)
    // slot 22
    player.setVarp(655, 140)
    // slot 23
    player.setVarp(10, 8)
    // slot 24
    player.setVarbit(3274, 130)
    // slot 25
    player.setVarbit(487, 14)
    // slot 26
    player.setVarp(399, 7)
    // slot 27
    player.setVarbit(2573, 320)
    // slot 28
    player.setVarbit(2258, 13)
    // slot 29
    player.setVarp(314, 80)
    // slot 30
    player.setVarbit(358, 15)
    // slot 31
    player.setVarbit(1465, 80)
    // slot 32
    player.setVarp(131, 9)
    // slot 33 - Druidic Ritual
    // Done.
    // slot 34
    player.setVarp(0, 11)
    // slot 35
    player.setVarp(335, 110)
    // slot 36
    player.setVarbit(2780, 40)
    // slot 37
    player.setVarp(299, 1.shl(20))
    // if (isBitFlagged(VARP[299], 20)) ...
    // if (!isBitFlagged(VARP[299], 1)) ...
    // slot 38
    player.setVarbit(2639, 11)
    // slot 39
    player.setVarbit(1560, 70)
    // slot 40
    player.setVarbit(2866, 200)
    // slot 41
    player.setVarbit(2497, 60)
    // slot 42
    player.setVarbit(1803, 90)
    // slot 43
    player.setVarbit(2326, 90)
    // slot 44
    player.setVarp(148, 11)
    // slot 45
    player.setVarbit(334, 28)
    // slot 46
    player.setVarp(17, 14)
    // slot 47
    player.setVarp(11, 5)
    // slot 48
    player.setVarbit(822, 140)
    // slot 49
    player.setVarp(347, 10)
    // slot 50
    player.setVarp(65, 10)
    // slot 51
    player.setVarbit(961, 60)
    // slot 52
    player.setVarp(180, 6)
    // slot 53
    player.setVarbit(217, 8)
    // slot 54
    player.setVarbit(571, 50)
    // slot 55
    player.setVarbit(346, 10)
    // slot 56
    player.setVarp(150, 160)
    // slot 57
    player.setVarbit(1527, 160)
    // slot 58
    player.setVarp(382, 11)
    // slot 59
    player.setVarp(223, 9)
    // slot 60
    player.setVarp(188, 15)
    // slot 61
    player.setVarp(5, 10)
    // slot 62
    player.setVarbit(34, 10)
    // slot 63
    player.setVarbit(418, 26)
    // slot 64
    player.setVarbit(1990, 430)
    // slot 65
    player.setVarp(387, 105)
    // slot 66
    player.setVarp(175, 12)
    // slot 67
    player.setVarp(139, 75)
    // slot 68 - Lost City
    // Done.
    // slot 69
    player.setVarbit(532, 11)
    // slot 70
    player.setVarbit(2448, 190)
    // slot 71
    player.setVarbit(1383, 4)
    // slot 72
    player.setVarp(14, 7)
    // slot 73
    player.setVarp(365, 9)
    // slot 74
    player.setVarp(30, 80)
    // slot 75
    player.setVarbit(260, 70)
    // slot 76
    player.setVarp(517, 8)
    // slot 77
    player.setVarbit(1103, 60)
    // slot 78
    player.setVarp(192, 2)
    // slot 79
    player.setVarbit(2790, 320)
    // slot 80
    player.setVarp(307, 110)
    // slot 81
    player.setVarp(112, 7)
    // slot 82
    player.setVarp(416, 280)
    // slot 83
    player.setVarp(165, 29)
    // slot 84 - Priest in Peril
    // Done.
    // slot 85
    player.setVarbit(6178, 4)
    // slot 86
    player.setVarbit(1404, 127)
    // slot 87
    player.setVarbit(1850, 5)
    // slot 88
    player.setVarbit(657, 2)
    // slot 89
    player.setVarp(328, 15)
    // slot 90
    player.setVarp(402, 6)
    // slot 91
    player.setVarbit(2140, 30)
    // slot 92
    player.setVarp(600, 19)
    // slot 93
    player.setVarp(76, 6)
    // slot 94
    player.setVarp(159, 12)
    // slot 95
    player.setVarbit(2610, 14)
    // slot 96
    player.setVarp(339, 85)
    // slot 97
    player.setVarbit(1372, 125)
    // slot 98
    player.setVarp(60, 3)
    // slot 99
    player.setVarp(116, 15)
    // slot 100
    player.setVarbit(2011, 13)
    // slot 101
    player.setVarbit(1444, 60)
    // slot 102
    player.setVarbit(2098, 200)
    // slot 103
    player.setVarp(320, 6)
    // slot 104
    player.setVarbit(1028, 70)
    // slot 105
    player.setVarbit(451, 2)
    // slot 106
    player.setVarp(26, 80)
    // slot 107
    player.setVarp(359, 100)
    // slot 108
    player.setVarp(197, 30)
    // slot 109
    player.setVarp(226, 7)
    // slot 110
    player.setVarp(111, 9)
    // slot 111
    player.setVarp(200, 5)
    // slot 112
    player.setVarp(385, 45)
    // slot 113
    player.setVarp(317, 50)
    // slot 114
    player.setVarp(161, 10)
    // slot 115
    player.setVarbit(1051, 11)
    // slot 116
    player.setVarp(212, 13)
    // slot 117
    player.setVarbit(3293, 135)
    // slot 118
    player.setVarbit(3311, 340)
    // slot 119
    player.setVarbit(3337, 18)
    // slot 120
    player.setVarp(980, 130)
    // slot 121
    player.setVarbit(3523, 150)
    // slot 122
    player.setVarbit(3534, 80)
    // slot 123
    player.setVarbit(3550, 11)
    // slot 124
    player.setVarbit(3618, 28)
    // slot 125
    player.setVarbit(2783, 60)
    // slot 126
    player.setVarbit(3888, 90)
    // slot 127
    player.setVarbit(3954, 200)
    // slot 128
    player.setVarbit(4055, 65)
    // slot 129
    player.setVarbit(4105, 18)
    // slot 130
    player.setVarbit(4230, 700)
    // slot 131
    player.setVarbit(4302, 110)
    // slot 132
    player.setVarbit(4321, 200)
    // slot 133
    player.setVarbit(4396, 60)
    // slot 134
    player.setVarbit(4505, 100)
    // slot 135
    player.setVarbit(4569, 500)
    // slot 136
    player.setVarbit(4684, 150)
    // slot 137
    player.setVarbit(4700, 63)
    // slot 138
    player.setVarbit(4764, 250)
    // slot 139
    player.setVarbit(4797, 100)
    // slot 140
    player.setVarbit(5032, 80)
    // slot 141
    player.setVarbit(5075, 20)
    // slot 142
    player.setVarbit(5133, 90)
    // slot 143
    player.setVarbit(5331, 35)
    // slot 144
    player.setVarbit(5387, 250)
    // slot 145
    player.setVarbit(5491, 910)
    // slot 146
    player.setVarbit(5761, 30)
    // slot 148
    player.setVarbit(5733, 60)
    // slot 149
    player.setVarbit(6001, 45)
    // slot 150
    player.setVarbit(6048, 250)
    // slot 155
    player.setVarbit(6112, 60)
    // slot 156
    player.setVarbit(6289, 240)
    // slot 157
    player.setVarbit(6775, 90)
    // slot 151
    player.setVarbit(6180, 140)
    // slot 152
    player.setVarbit(1938, 110)
    // slot 153
    player.setVarbit(5448, 50)
    // slot 154
    player.setVarbit(6307, 5)
    // slot 158
    player.setVarbit(7826, 180)
    // slot 160
    player.setVarbit(6471, 90)
    // slot 161
    player.setVarbit(6553, 46)
    // slot 162
    player.setVarbit(6962, 12)
    // slot 163
    player.setVarbit(6883, 147)
    // slot 165
    player.setVarbit(7050, 35)
    // slot 167
    player.setVarbit(7793, 30)
    // slot 168
    player.setVarbit(8045, 150)
    // slot 170
    player.setVarbit(7238, 60)
    // slot 171
    player.setVarbit(7958, 90)
    // slot 172
    player.setVarbit(7871, 5)
    // slot 173
    player.setVarbit(7451, 63)
    // slot 14
    player.setVarbit(8704, 90)
    // slot 174
    player.setVarbit(8248, 40)
    // slot 17
    player.setVarbit(5730, 100)
    // slot 176
    player.setVarbit(9829, 400)
    // slot 179
    player.setVarbit(8605, 315)
    // slot 12
    player.setVarbit(5332, 120)
    // slot 180
    player.setVarbit(998, 9)
    // slot 181
    player.setVarbit(8961, 210)
    // slot 182
    player.setVarbit(9369, 50)
    // slot 183
    player.setVarbit(9491, 250)
    // slot 184
    player.setVarbit(9757, 170)
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
