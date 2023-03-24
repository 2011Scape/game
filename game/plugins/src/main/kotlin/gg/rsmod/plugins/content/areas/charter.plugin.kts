package gg.rsmod.plugins.content.areas

val CHARTER_SELECTION_INTERFACE = 95
val CHARTER_INTERFACE = 299
val CHARTERVARP = 75

val traders = arrayOf(
    Npcs.TRADER_STAN,
    Npcs.TRADER_CREWMEMBER_SHIPYARD,
    Npcs.TRADER_CREWMEMBER_CATHERBY,
    Npcs.TRADER_CREWMEMBER_PORT_KHAZARD,
    Npcs.TRADER_CREWMEMBER_MUSAPOINT,
    Npcs.TRADER_CREWMEMBER_PORT_PHASMATY
)

enum class Destinations(val component: Int, val tile: Tile, vararg val charter: CharterDestination) {
    ENTRANA(
        component = -1,
        tile = Tile(0, 0),
        CharterDestination.PORT_SARIM_TO_ENTRANA
    ),
    PORT_SARIM(
        component = 30,
        tile = Tile(3038, 3192),
        CharterDestination.ENTRANA_TO_PORT_SARIM,
        CharterDestination.KARAMJA_TO_PORT_SARIM
    ),
    CRANDOR(
        component = 32,
        tile = Tile(2853, 3238),
        CharterDestination.PORT_SARIM_TO_CRANDOR
    ),
    MUSA_POINT(
        component = -2,
        tile = Tile(2956, 3146),
        CharterDestination.DIRECT_TO_KARAMJA
    ),
    KARAMJA(
        component = 27,
        tile = Tile(2954, 3158),
        CharterDestination.PORT_SARIM_TO_KARAMJA
    )
}

enum class CharterDestination(
    val varpValue: Int,
    val npcIds: Array<Int>,
    val delay: Int,
) {
    PORT_SARIM_TO_ENTRANA(varpValue = 1, npcIds = emptyArray(), delay = 11),
    ENTRANA_TO_PORT_SARIM(varpValue = 2, npcIds = arrayOf(Npcs.TRADER_CREWMEMBER_SHIPYARD), delay = 12),
    PORT_SARIM_TO_CRANDOR(varpValue = 3, npcIds = arrayOf(Npcs.TRADER_STAN), delay = 12),
    DIRECT_TO_KARAMJA(varpValue = 4, npcIds = emptyArray(), delay = 0),
    PORT_SARIM_TO_KARAMJA(varpValue = 5, npcIds = arrayOf(Npcs.TRADER_STAN), delay = 7),
    KARAMJA_TO_PORT_SARIM(varpValue = 6, npcIds = arrayOf(Npcs.TRADER_CREWMEMBER_MUSAPOINT), delay = 7),
    ;
}

val destinationValues = enumValues<Destinations>()
val charterValues = enumValues<CharterDestination>()

destinationValues.forEach { destination ->
    on_button(interfaceId = CHARTER_SELECTION_INTERFACE, destination.component) {
        val matchingCharter = charterValues.find { player.getInteractingNpc().id in it.npcIds }?: return@on_button
        setSail(player, matchingCharter, destination)
    }
}

on_npc_option(Npcs.CAPTAIN_TOBIAS, "Pay-fare") {//TODO add money check and dialogue
    setSail(player, CharterDestination.DIRECT_TO_KARAMJA, Destinations.KARAMJA)
}

on_npc_option(Npcs.CUSTOMS_OFFICER, "Pay-fare") {//TODO add money check and dialogue
    setSail(player, CharterDestination.DIRECT_TO_KARAMJA, Destinations.PORT_SARIM)
}

traders.forEach { trader ->
    on_npc_option(trader, "Charter") {
        player.openInterface(CHARTER_SELECTION_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    }
}

fun setSail(player: Player, charter: CharterDestination, destination: Destinations) {
    player.closeInterface(CHARTER_SELECTION_INTERFACE)
    player.interruptQueues()
    player.queue(TaskPriority.STRONG) {
        val varpValue = charter.varpValue
        val delay = charter.delay
        val destination = destination.tile
        player.openInterface(CHARTER_INTERFACE, InterfaceDestination.MAIN_SCREEN)
        player.setVarp(CHARTERVARP, varpValue)
        player.message("$charter varpValue: $varpValue delay: $delay", type = ChatMessageType.CONSOLE)
        if (delay > 0) wait(delay)
        player.message("move to end destination", type = ChatMessageType.CONSOLE)
        player.moveTo(destination)
        player.closeInterface(CHARTER_INTERFACE)
    }
}