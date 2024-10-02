package gg.rsmod.plugins.content.magic.teleports

import gg.rsmod.game.model.Area
import gg.rsmod.plugins.content.magic.TeleportType

enum class TeleportSpell(val spellName: String, val type: TeleportType, val endArea: Area,
                         val xp: Double, val spriteId: Int? = null) {
    /**
     * Standard.
     */
    VARROCK("Varrock Teleport", TeleportType.MODERN, Area(3210, 3423, 3216, 3425), 35.0),
    MOBILISING_ARMIES("Mobilising Armies Teleport", TeleportType.MODERN, Area(2410, 2847, 2416, 2851), 19.0),
    LUMBRIDGE("Lumbridge Teleport", TeleportType.MODERN, Area(3221, 3218, 3222, 3219), 41.0),
    FALADOR("Falador Teleport", TeleportType.MODERN, Area(2961, 3376, 2969, 3385), 47.0),
    CAMELOT("Camelot Teleport", TeleportType.MODERN, Area(2756, 3476, 2758, 3480), 55.5),
    ARDOUGNE("Ardougne Teleport", TeleportType.MODERN, Area(2659, 3300, 2665, 3310), 61.0),
    WATCHTOWER("Watchtower Teleport", TeleportType.MODERN, Area(2551, 3113, 2553, 3116), 68.0),
    TROLLHEIM("Trollheim Teleport", TeleportType.MODERN, Area(2888, 3675, 2890, 3678), 68.0),
    APE_ATOLL("Teleport to Ape Atoll", TeleportType.MODERN, Area(2760, 2781, 2763, 2784), 74.0),

    ;

    companion object {
        val values = enumValues<TeleportSpell>()
    }
}