package gg.rsmod.plugins.content.mechanics.prayer

import gg.rsmod.plugins.content.mechanics.prayer.Prayers.toggleQuickPrayers

on_player_death {
    Prayers.deactivateAll(player)
}

/**
 * Deactivate all prayers on log out.
 */
on_logout {
    Prayers.deactivateAll(player)
}

/**
 * Restores prayer on death.
 */
on_player_death {
    Prayers.rechargePrayerPoints(player)
}

/**
 * Prayer drain.
 */
on_login {
    player.timers[Prayers.PRAYER_DRAIN] = 2
    Prayers.init(player)
}

on_timer(Prayers.PRAYER_DRAIN) {
    if (player.getVarp(Prayers.ACTIVE_PRAYERS_VARP) == 0) {
        player.timers[Prayers.PRAYER_DRAIN] = 2
        return@on_timer
    }
    Prayers.drainPrayer(player)
}

/**
 * Toggle quick-prayers.
 */

on_button(interfaceId = 749, component = 1) {
    val option = player.getInteractingOption()
    toggleQuickPrayers(player, option)
}

/**
 * Activate prayers.
 */
on_button(interfaceId = 271, component = 8) {
    player.queue(TaskPriority.STRONG) {
        val buttonSlot = player.getInteractingSlot()
        val prayer = Prayer.values().firstOrNull { it.slot == buttonSlot }

        if (prayer != null) {
            Prayers.toggle(this, prayer)
        }
    }
}

/**
 * Select quick-prayer.
 */
on_button(interfaceId = 271, component = 42) {
    val slot = player.getInteractingSlot()
    val prayer = Prayer.values.firstOrNull { prayer -> prayer.slot == slot } ?: return@on_button
    Prayers.selectQuickPrayer(this, prayer)
}

/**
 * Accept selected quick-prayer.
 */
on_button(interfaceId = 271, component = 43) {
    player.setVarc(181, 0)
    player.openInterface(InterfaceDestination.PRAYER_TAB)
}
