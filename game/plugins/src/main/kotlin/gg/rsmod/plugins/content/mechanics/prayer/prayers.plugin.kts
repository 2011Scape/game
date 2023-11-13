package gg.rsmod.plugins.content.mechanics.prayer

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
 * Activate prayers.
 */
on_button(interfaceId = 271, component = 8) {
    player.queue {
        val buttonSlot = player.getInteractingSlot()
        val prayer = Prayer.values().firstOrNull { it.slot == buttonSlot }

        if (prayer != null) {
            Prayers.toggle(this, prayer)
        }
    }
}

/**
 * Prayer drain.
 */
on_login {
    player.timers[Prayers.PRAYER_DRAIN] = 1
}

on_timer(Prayers.PRAYER_DRAIN) {
    player.timers[Prayers.PRAYER_DRAIN] = 1
    Prayers.drainPrayer(player)
}
/**
/**
 * Toggle quick-prayers.
 */
on_button(interfaceId = 749, component = 1) {
    val option = player.getInteractingOption()
    Prayers.toggleQuickPrayers(player, option)
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
**/

/**on_interface_open(271) {
    // Check if the player is currently selecting quick prayers
    val isSelectingQuickPrayers = player.getVarc(181) == 1
    val component = if (isSelectingQuickPrayers) 42 else 8

    // Set events to the appropriate component based on whether the player is selecting quick prayers
    player.setEvents(interfaceId = 271, component = component, from = 0, to = 29, setting = 2)
}**/