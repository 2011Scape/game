package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.api.cfg.Npcs

val crewMembers =
    arrayOf(
        Npcs.TRADER_STAN,
        4651,
        4652,
        4653,
        4654,
        4655,
        4656,
    )

enum class CharterType(
    val varpValue: Int,
    val npcIds: Array<Int>,
    val delay: Int,
) {
    FADE_TO_BLACK(varpValue = 0, npcIds = crewMembers, delay = 4),
    PORT_SARIM_TO_ENTRANA(varpValue = 1, npcIds = arrayOf(Npcs.MONK_OF_ENTRANA), delay = 11),
    ENTRANA_TO_PORT_SARIM(varpValue = 2, npcIds = arrayOf(Npcs.MONK_OF_ENTRANA_2729), delay = 12),
    PORT_SARIM_TO_CRANDOR(varpValue = 3, npcIds = arrayOf(Npcs.CAPTAIN_NED), delay = 12),
    PORT_SARIM_TO_KARAMJA(varpValue = 5, npcIds = arrayOf(Npcs.CAPTAIN_TOBIAS), delay = 7),
    KARAMJA_TO_PORT_SARIM(varpValue = 6, npcIds = arrayOf(Npcs.CUSTOMS_OFFICER), delay = 7),
    PORT_SARIM_TO_PEST_CONTROLL(varpValue = 14, npcIds = emptyArray(), delay = 8),
    PEST_CONTROLL_TO_PORT_SARIM(varpValue = 15, npcIds = emptyArray(), delay = 8),
    FELDIP_HILLS_TO_CHOMPY_QUEST_LOC(varpValue = 16, npcIds = emptyArray(), delay = 11),
    CHOMPY_QUEST_LOC_TO_FELDIP_HILLS(varpValue = 17, npcIds = emptyArray(), delay = 11),
}
