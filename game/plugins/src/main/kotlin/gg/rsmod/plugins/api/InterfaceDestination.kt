package gg.rsmod.plugins.api

import gg.rsmod.game.model.interf.DisplayMode

enum class InterfaceDestination(
    val interfaceId: Int,
    val fixedChildId: Int,
    val resizeChildId: Int,
    val clickThrough: Boolean = true
) {

    CHATBOX_TABS(interfaceId = 751, fixedChildId = 68, resizeChildId = 19),
    CHAT_BOX_PANE(interfaceId = 752, fixedChildId = 192, resizeChildId = 73),

    ATTACK_TAB(interfaceId = 884, fixedChildId = 204, resizeChildId = 90),
    ACHIEVEMENTS_TAB(interfaceId = 1056, fixedChildId = 205, resizeChildId = 91),
    SKILLS_TAB(interfaceId = 320, fixedChildId = 206, resizeChildId = 92),
    QUEST_TAB(interfaceId = 190, fixedChildId = 207, resizeChildId = 93),
    INVENTORY_TAB(interfaceId = 679, fixedChildId = 208, resizeChildId = 94),
    EQUIPMENT_TAB(interfaceId = 387, fixedChildId = 209, resizeChildId = 95),
    PRAYER_TAB(interfaceId = 271, fixedChildId = 210, resizeChildId = 96),
    MAGIC_TAB(interfaceId = 192, fixedChildId = 211, resizeChildId = 97),
    //  TODO: Summoning tab
    FRIENDS_TAB(interfaceId = 550, fixedChildId = 213, resizeChildId = 99),
    FRIEND_CHAT_TAB(interfaceId = 1109, fixedChildId = 214, resizeChildId = 100),
    CLAN_CHAT_TAB(interfaceId = 1110, fixedChildId = 215, resizeChildId = 101),
    SETTINGS_TAB(interfaceId = 261, fixedChildId = 216, resizeChildId = 102),
    EMOTES_TAB(interfaceId = 464, fixedChildId = 217, resizeChildId = 103),
    MUSIC_TAB(interfaceId = 187, fixedChildId = 218, resizeChildId = 104),
    NOTES_TAB(interfaceId = 34, fixedChildId = 219, resizeChildId = 105),
    LOGOUT_TAB(interfaceId = 182, fixedChildId = 222, resizeChildId = 108),

    HP_ORB(interfaceId = 748, fixedChildId = 183, resizeChildId = 177),
    PRAYER_ORB(interfaceId = 749, fixedChildId = 185, resizeChildId = 178),
    ENERGY_ORB(interfaceId = 750, fixedChildId = 186, resizeChildId = 179),
    SUMMONING_ORB(interfaceId = 747, fixedChildId = 188, resizeChildId = 180),
    SPLIT_PM(interfaceId = 754, fixedChildId = 188, resizeChildId = 17),

    MAIN_SCREEN(interfaceId = -1, fixedChildId = 9, resizeChildId = 12,
        clickThrough = false),

    // Note: this is used for interfaces such as the skill menu where it has a
    // background that should fill the entire game screen.
    MAIN_SCREEN_FULL(interfaceId = -1, fixedChildId = 9, resizeChildId = 11,
        clickThrough = false),

    TAB_AREA(interfaceId = -1, fixedChildId = 199, resizeChildId = 87),



    ;

    fun isSwitchable(): Boolean = false

    companion object {
        val values = enumValues<InterfaceDestination>()
    }
}

fun getDisplayComponentId(displayMode: DisplayMode) = when (displayMode) {
    DisplayMode.FIXED -> 548
    DisplayMode.RESIZABLE_NORMAL -> 746
    else -> throw RuntimeException("Unhandled display mode.")
}

fun getChildId(pane: InterfaceDestination, displayMode: DisplayMode): Int = when (displayMode) {
    DisplayMode.FIXED -> pane.fixedChildId
    DisplayMode.RESIZABLE_NORMAL -> pane.resizeChildId
    else -> throw RuntimeException("Unhandled display mode.")
}