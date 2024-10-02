/**
 * Gets the 'interface hash' of a given interface id and
 * child component. This value is commonly used in ClientScripts when referring
 * to a child component in the game. An 'interface hash' is in the format of (parent >> 16) | child
 *
 * Example: 335.getInterfaceHash(25) would return 21954585, which is the 'absolute' id of the component
 *
 * @param child     The child component
 */
fun Int.getInterfaceHash(child: Int = -1): Int {
    val value = (this shl 16)
    if (child != -1) return value or child
    return value
}

object Tabs {
    const val COMBAT_STYLES = 0
    const val TASK_LIST = 1
    const val SKILLS = 2
    const val QUESTS = 3
    const val INVENTORY = 4
    const val EQUIPMENT = 5
    const val PRAYER = 6
    const val SPELLBOOK = 7
    const val BLANK_TAB = 8
    const val FRIENDS_IGNORE = 9
    const val FRIENDS_CHAT = 10
    const val CLAN_CHAT = 11
    const val SETTINGS = 12
    const val EMOTES = 13
    const val MUSIC = 14
    const val NOTES = 15
}
