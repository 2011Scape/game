package gg.rsmod.plugins.util.anticheat

import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.combat.isPoisoned
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.Rare
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

val TIMER = TimerKey(persistenceKey = "anti_cheat", tickOffline = false, resetOnDeath = false, tickForward = false, removeOnZero = true)
val LOGOUT_TIMER = TimerKey()

val range = 3000..12000
on_login {
    if(!player.timers.exists(TIMER)) {
        player.timers[TIMER] = world.random(range)
    }
}

on_timer(TIMER) {
    if(player.isBeingAttacked() || player.isPoisoned() || player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) {
        player.timers[TIMER] = 10
        return@on_timer
    }
    player.interruptQueues()
    player.stopMovement()
    player.animate(-1)
    player.timers[LOGOUT_TIMER] = 200
    player.lockingQueue(TaskPriority.STRONG, lockState = LockState.FULL_WITH_DAMAGE_IMMUNITY) {
        val randomNumber = world.random(100)
        val amount = inputInt("Please answer with the following number: $randomNumber")
        if (amount == randomNumber) {
            val hasSpace = player.inventory.hasSpace
            val container = if(hasSpace) player.inventory else player.bank
            itemMessageBox("Thank you for solving this random event, a gift has been added to your ${if(hasSpace) "inventory" else "bank"}.", item = Items.MYSTERY_BOX)
            container.add(Item(Items.MYSTERY_BOX))
            player.addLoyalty(world.random(1..30))
            player.timers.remove(LOGOUT_TIMER)
            player.timers[TIMER] = world.random(range)
        } else {
            player.timers[TIMER] = 1
        }
    }
}

on_timer(LOGOUT_TIMER) {
    player.handleLogout()
}