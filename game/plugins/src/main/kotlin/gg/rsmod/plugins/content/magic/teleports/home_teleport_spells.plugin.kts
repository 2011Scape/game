package gg.rsmod.plugins.content.magic.teleports

import gg.rsmod.plugins.content.combat.isAttacking
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.magic.MagicSpells.on_magic_spell_button
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport

val TERMINATE_HOME_TELEPORT_NEUTRAL: QueueTask.() -> Unit = {
    player.animate(-1)
    player.graphic(-1)
}

val HOME_TELEPORT_TIMER_ENABLED = true
val HOME_TELEPORT_TIMER_DELAY = 3000
val HOME_TELEPORT_TIMER = TimerKey(persistenceKey = "home_teleport_delay", tickOffline = true)

HomeTeleport.values.forEach { teleport ->

    on_magic_spell_button(teleport.spellName) {
        if (player.hasMoveDestination()) {
            player.message("You can't use that teleport at the moment.")
            return@on_magic_spell_button
        } else if (player.isAttacking()) {
            player.message("You can't use that teleport at the moment.")
            return@on_magic_spell_button
        }

        if (HOME_TELEPORT_TIMER_ENABLED && player.timers.has(HOME_TELEPORT_TIMER) && player.privilege.id != 2) {
            val minutes = player.timers.getMinutesLeft(HOME_TELEPORT_TIMER)

            if (minutes != null) {
                player.message("You need to wait another ${minutes.appendToString("minute")} to cast this spell.")
            } else {
                player.message("You need to wait another couple of seconds to cast this spell.")
            }
            return@on_magic_spell_button
        }

        if (player.canTeleport(TeleportType.MODERN)) {
            player.queue(TaskPriority.STRONG) {
                teleport(teleport.endTile(world))
            }
        }
    }
}

val animations = arrayOf(1722, 1723, 1724, 1725, 2798, 2799, 2800, 3195, 4643, 4645, 4646, 4847, 4848, 4849, 4850, 4851, 4852)
val graphics = arrayOf(775, 800, 801, 802, 803, 804, 1703, 1704, 1705, 1706, 1707, 1708, 1709, 1710, 1711, 1712, 1713)

suspend fun QueueTask.teleport(endTile: Tile) {
    waitAndCheckCombat(2)
    terminateAction = TERMINATE_HOME_TELEPORT_NEUTRAL
    repeat(17) { cycle ->
        player.animate(animations[cycle])
        player.graphic(graphics[cycle])
        waitAndCheckCombat(1)
    }
    player.animate(-1)
    player.moveTo(endTile)
    player.timers[HOME_TELEPORT_TIMER] = HOME_TELEPORT_TIMER_DELAY
}

suspend fun QueueTask.waitAndCheckCombat(cycles: Int): Boolean {
    for (i in 0 until cycles) {
        wait(1)
        if (player.isBeingAttacked()) {
            terminate()
            return false
        }
    }
    return true
}

enum class HomeTeleport(val spellName: String, val endTile: World.() -> Tile) {
    LUMBRIDGE("Lumbridge Home Teleport", { Tile(x = 3221, z = 3218, height = 0) }),
    ;

    companion object {
        val values = enumValues<HomeTeleport>()
    }
}