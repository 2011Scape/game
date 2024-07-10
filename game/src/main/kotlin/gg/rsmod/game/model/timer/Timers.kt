package gg.rsmod.game.model.timer

/**
 * A decoupled file that holds TimerKeys that require read-access from our
 * game module. Any timer keys that can be stored on the plugin classes themselves,
 * should do so. When storing them in a class, remember the TimerKey must be
 * a singleton, meaning it should only have a single state.
 *
 * @author Tom <rspsmods@gmail.com>
 */

/**
 * A timer for npcs to reset their pawn face attribute.
 */
internal val RESET_PAWN_FACING_TIMER = TimerKey()

/**
 * A timer for removing a skull icon.
 */
val SKULL_ICON_DURATION_TIMER = TimerKey()

/**
 * Timer key set when a pawn is attacked either in PvP or in PvM.
 */
val ACTIVE_COMBAT_TIMER = TimerKey()

/**
 * Timer key used to force a player disconnect, usually used so that if a
 * player's channel has been inactive (disconnected) for X amount of time,
 * we disconnect them so that they can play again.
 */
val FORCE_DISCONNECTION_TIMER = TimerKey()

/**
 * Timer key set when frozen.
 */
val FROZEN_TIMER = TimerKey()

/**
 * Timer key set when stunned.
 */
val STUN_TIMER = TimerKey()

/**
 * Timer key for poison ticks.
 */
val POISON_TIMER = TimerKey(persistenceKey = "poison", tickOffline = false, resetOnDeath = true)

/**
 * Timer key for poison immunity ticks.
 */
val POISON_IMMUNITY = TimerKey(persistenceKey = "poison_immunity", tickOffline = false, resetOnDeath = false)

/**
 * Timer key for dragonfire protection ticking down.
 */
val ANTIFIRE_TIMER = TimerKey()

/**
 * Timer key for the delay in between a pawn's attack.
 */
val ATTACK_DELAY = TimerKey()

/**
 * Timer key for delay in between drinking potions.
 */
val POTION_DELAY = TimerKey()

/**
 * Timer key for delay in between eating food.
 */
val FOOD_DELAY = TimerKey()

/**
 * Timer key for delay in between eating "combo" food.
 */
val COMBO_FOOD_DELAY = TimerKey()

/**
 * Timer key for lighting a log while firemaking
 */
val LAST_LOG_LIT = TimerKey()

/**
 * Timer key for stat restore
 */
var STAT_RESTORE = TimerKey("stat_restoration", tickOffline = false, resetOnDeath = false)

/**
 * Timer key for time spent logged in
 */
val TIME_ONLINE =
    TimerKey(persistenceKey = "time_online", tickOffline = false, resetOnDeath = false, tickForward = true)

/**
 * Timer key for anti cheat random event trigger
 */
val ANTI_CHEAT_TIMER =
    TimerKey(
        persistenceKey = "anti_cheat",
        tickOffline = false,
        resetOnDeath = false,
        tickForward = false,
        removeOnZero = true,
    )

/**
 * Timer key for bonus experience time elapsed
 */
val BONUS_EXPERIENCE_TIME_ELAPSED = TimerKey()

/**
 * Timer key for saving the individual player
 */
val SAVE_TIMER = TimerKey()

/**
 * Timer key for dailies
 */
val DAILY_TIMER = TimerKey(persistenceKey = "dailies", tickOffline = true, resetOnDeath = false, removeOnZero = false)

/**
 * The timer to tick if a player is in an area that requires a light source
 */
val DARK_ZONE_TIMER = TimerKey()

/**
 * The timer used to trigger a visual update of the prayer points in the client after login
 */
val PRAYER_INITIALIZATION_TIMER = TimerKey()

/**
 * A timer that will run if the player is slotted for logout
 */
val LOGOUT_TIMER = TimerKey()
