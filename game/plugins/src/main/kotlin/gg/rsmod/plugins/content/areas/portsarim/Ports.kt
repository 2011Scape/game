package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.game.model.Tile
import gg.rsmod.plugins.content.quests.Quest

/**
 * @author raynna
 * @project 2011scape
 */

val PORT_SARIM_TILE = Tile(3038, 3192)
val BRIMHAVEN_TILE = Tile(2760, 3238)
val PORT_KHAZARD_TILE = Tile(2674, 3144)
val PORT_TYRAS_TILE = Tile(2142, 3122)
val PORT_PHASMATYS_TILE = Tile(3702, 3503)
val KARAMJA_TILE = Tile(2954, 3158)
val MOS_LE_HARMLESS_TILE = Tile(3671, 2931)
val CATHERYBY_TILE = Tile(2792, 3414)
val OO_GLOG_TILE = Tile(2623, 2857)
val SHIPYARD_TILE = Tile(3001, 3032)

enum class Ports(
    val portName: String,
    val component: Int = -1,
    val tile: Tile,
    val charter: CharterType,
    val questReq: Quest? = null,
    vararg val destination: Destinations,
) {
    ENTRANA_MONKS(
        portName = "Entrana",
        tile = Tile(2834, 3335),
        charter = CharterType.PORT_SARIM_TO_ENTRANA,
    ),
    MONKS_BOAT(
        portName = "Port Sarim",
        tile = Tile(2834, 3335),
        charter = CharterType.ENTRANA_TO_PORT_SARIM,
    ),
    MUSA_POINT(
        portName = "Karamja",
        tile = Tile(2956, 3146),
        charter = CharterType.PORT_SARIM_TO_KARAMJA,
    ),
    CAPTAIN_TOBIAS_BOAT(
        portName = "Port Sarim",
        tile = Tile(3029, 3217),
        charter = CharterType.KARAMJA_TO_PORT_SARIM,
    ),
    CRANDOR(
        portName = "Crandor",
        tile = Tile(2853, 3238),
        charter = CharterType.PORT_SARIM_TO_CRANDOR,
    ),
    PORT_SARIM(
        portName = "Port Sarim",
        component = 30,
        tile = PORT_SARIM_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.BRIMHAVEN_TO_PORT_SARIM,
                Destinations.CATHERBY_TO_PORT_SARIM,
                Destinations.MOS_LE_HARMLESS_TO_PORT_SARIM,
                Destinations.PORT_KHAZARD_TO_PORT_SARIM,
                Destinations.PORT_PHASMATYS_TO_PORT_SARIM,
                Destinations.SHIPYARD_TO_PORT_SARIM,
                Destinations.PORT_TYRAS_TO_PORT_SARIM,
                Destinations.OO_GLOG_TO_PORT_SARIM,
            ),
    ),
    PORT_KHAZARD(
        portName = "Port Khazard",
        component = 29,
        tile = PORT_KHAZARD_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.BRIMHAVEN_TO_PORT_KHAZARD,
                Destinations.CATHERBY_TO_PORT_KHAZARD,
                Destinations.KARAMJA_TO_PORT_KHAZARD,
                Destinations.MOS_LE_HARMLESS_TO_PORT_KHAZARD,
                Destinations.PORT_PHASMATYS_TO_PORT_KHAZARD,
                Destinations.PORT_SARIM_TO_PORT_KHAZARD,
                Destinations.SHIPYARD_TO_PORT_KHAZARD,
                Destinations.PORT_TYRAS_TO_PORT_KHAZARD,
                Destinations.OO_GLOG_TO_PORT_KHAZARD,
            ),
    ),
    MOS_LE_HARMLESS(
        portName = "Mos'le Harmless",
        component = 31,
        tile = MOS_LE_HARMLESS_TILE,
        charter = CharterType.FADE_TO_BLACK,
        questReq = null, // TODO CavinFever.slot add quest slot, usage: questReq = CabinFeber,
        Destinations.BRIMHAVEN_TO_MOS_LE_HARMLESS,
        Destinations.CATHERBY_TO_MOS_LE_HARMLESS,
        Destinations.KARAMJA_TO_MOS_LE_HARMLESS,
        Destinations.PORT_KHAZARD_TO_MOS_LE_HARMLESS,
        Destinations.PORT_SARIM_TO_MOS_LE_HARMLESS,
        Destinations.SHIPYARD_TO_MOS_LE_HARMLESS,
        Destinations.PORT_TYRAS_TO_MOS_LE_HARMLESS,
        Destinations.OO_GLOG_TO_MOS_LE_HARMLESS,
    ),
    KARAMJA(
        portName = "Karamja",
        component = 27,
        tile = KARAMJA_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.BRIMHAVEN_TO_KARAMJA,
                Destinations.CATHERBY_TO_KARAMJA,
                Destinations.MOS_LE_HARMLESS_TO_KARAMJA,
                Destinations.PORT_KHAZARD_TO_KARAMJA,
                Destinations.PORT_PHASMATYS_TO_KARAMJA,
                Destinations.SHIPYARD_TO_KARAMJA,
                Destinations.PORT_TYRAS_TO_KARAMJA,
                Destinations.OO_GLOG_TO_KARAMJA,
            ),
    ),
    BRIMHAVEN(
        portName = "Brimhaven",
        component = 28,
        tile = BRIMHAVEN_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.CATHERBY_TO_BRIMHAVEN,
                Destinations.KARAMJA_TO_BRIMHAVEN,
                Destinations.MOS_LE_HARMLESS_TO_BRIMHAVEN,
                Destinations.PORT_KHAZARD_TO_BRIMHAVEN,
                Destinations.PORT_PHASMATYS_TO_BRIMHAVEN,
                Destinations.PORT_SARIM_TO_BRIMHAVEN,
                Destinations.SHIPYARD_TO_BRIMHAVEN,
                Destinations.PORT_TYRAS_TO_BRIMHAVEN,
                Destinations.OO_GLOG_TO_BRIMHAVEN,
            ),
    ),
    CATHERBY(
        portName = "Catherby",
        component = 25,
        tile = CATHERYBY_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.BRIMHAVEN_TO_CATHERBY,
                Destinations.KARAMJA_TO_CATHERBY,
                Destinations.MOS_LE_HARMLESS_TO_CATHERBY,
                Destinations.PORT_KHAZARD_TO_CATHERBY,
                Destinations.PORT_SARIM_TO_CATHERBY,
                Destinations.SHIPYARD_TO_CATHERBY,
                Destinations.PORT_TYRAS_TO_CATHERBY,
                Destinations.PORT_PHASMATYS_TO_CATHERBY,
                Destinations.OO_GLOG_TO_CATHERBY,
            ),
    ),
    OO_GLOG(
        portName = "Oo'glog",
        component = 33,
        tile = OO_GLOG_TILE,
        charter = CharterType.FADE_TO_BLACK,
        questReq = null, // TODO AsAFirstResort.slot add quest slot
        Destinations.BRIMHAVEN_TO_OO_GLOG,
        Destinations.CATHERBY_TO_OO_GLOG,
        Destinations.KARAMJA_TO_OO_GLOG,
        Destinations.MOS_LE_HARMLESS_TO_OO_GLOG,
        Destinations.PORT_KHAZARD_TO_OO_GLOG,
        Destinations.PORT_PHASMATYS_TO_OO_GLOG,
        Destinations.PORT_SARIM_TO_OO_GLOG,
        Destinations.SHIPYARD_TO_OO_GLOG,
        Destinations.PORT_TYRAS_TO_OO_GLOG,
    ),
    PORT_PHASMATYS(
        portName = "Port Phasmaty's",
        component = 24,
        tile = PORT_PHASMATYS_TILE,
        charter = CharterType.FADE_TO_BLACK,
        destination =
            arrayOf(
                Destinations.BRIMHAVEN_TO_PORT_PHASMATYS,
                Destinations.CATHERBY_TO_PORT_PHASMATYS,
                Destinations.KARAMJA_TO_PORT_PHASMATYS,
                Destinations.PORT_KHAZARD_TO_PORT_PHASMATYS,
                Destinations.PORT_SARIM_TO_PORT_PHASMATYS,
                Destinations.PORT_TYRAS_TO_PORT_PHASMATYS,
                Destinations.OO_GLOG_TO_PORT_PHASMATYS,
            ),
    ),
    PORT_TYRAS(
        portName = "Port Tyras",
        component = 23,
        tile = PORT_TYRAS_TILE,
        charter = CharterType.FADE_TO_BLACK,
        questReq = null, // TODO Regicide.slot add quest slot
        Destinations.BRIMHAVEN_TO_PORT_TYRAS,
        Destinations.CATHERBY_TO_PORT_TYRAS,
        Destinations.KARAMJA_TO_PORT_TYRAS,
        Destinations.MOS_LE_HARMLESS_TO_PORT_TYRAS,
        Destinations.PORT_KHAZARD_TO_PORT_TYRAS,
        Destinations.PORT_PHASMATYS_TO_PORT_TYRAS,
        Destinations.PORT_SARIM_TO_PORT_TYRAS,
        Destinations.SHIPYARD_TO_PORT_TYRAS,
        Destinations.OO_GLOG_TO_PORT_TYRAS,
    ),
    SHIPYARD(
        portName = "Karamja Shipyard",
        component = 26,
        tile = SHIPYARD_TILE,
        charter = CharterType.FADE_TO_BLACK,
        questReq = null, // TODO TheGrandTree.slot add quest slot
        Destinations.BRIMHAVEN_TO_SHIPYARD,
        Destinations.CATHERBY_TO_SHIPYARD,
        Destinations.KARAMJA_TO_SHIPYARD,
        Destinations.MOS_LE_HARMLESS_TO_SHIPYARD,
        Destinations.PORT_KHAZARD_TO_SHIPYARD,
        Destinations.PORT_SARIM_TO_SHIPYARD,
        Destinations.PORT_TYRAS_TO_SHIPYARD,
        Destinations.OO_GLOG_TO_SHIPYARD,
    ),
}
