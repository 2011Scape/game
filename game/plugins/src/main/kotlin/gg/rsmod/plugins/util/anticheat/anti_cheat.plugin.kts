package gg.rsmod.plugins.util.anticheat

import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.Rare

/**
 * @author Alycia <https://github.com/alycii>
 */

val TIMER = TimerKey()
val LOGOUT_TIMER = TimerKey()

val range = 3000..12000
on_login {
    player.timers[TIMER] = world.random(range)
}

on_timer(TIMER) {
    if(player.isBeingAttacked() || player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) {
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
            if (!player.inventory.hasSpace) {
                itemMessageBox("Thank you for solving this random event, a gift has been added to your bank.", item = Items.MYSTERY_BOX)
                player.bank.add(Item(Items.MYSTERY_BOX))
            }
            else {
                itemMessageBox("Thank you for solving this random event, a gift has been added to your inventory.", item = Items.MYSTERY_BOX)
                player.inventory.add(Item(Items.MYSTERY_BOX))
            }
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

on_item_option(item = Items.MYSTERY_BOX, option = "open") {
    if(player.inventory.remove(player.getInteractingItem()).hasSucceeded()) {
        DropTableFactory.createDropInventory(player, Items.MYSTERY_BOX, DropTableType.STALL)
    }
}

val mysteryBox = DropTableFactory.build {
    main {
        total(13)
        obj(Items.CABBAGE, slots = 1)
        obj(Items.DIAMOND, slots = 1)
        obj(Items.BUCKET, slots = 1)
        obj(Items.COINS, quantity = 500, slots = 1)
        obj(Items.AL_KHARID_FLYER, slots = 1)
        obj(Items.OLD_BOOT, slots = 1)
        obj(Items.BODY_RUNE, slots = 1)
        obj(Items.ONION, slots = 1)
        obj(Items.MITHRIL_SCIMITAR, slots = 1)
        obj(Items.CASKET, slots = 1)
        obj(Items.STEEL_PLATEBODY, slots = 1)
        obj(Items.NATURE_RUNE, quantity = 20, slots = 1)
        table(Rare.rareTable, slots = 1)
    }
    table("Holiday") {
        total(100)
        nothing(97)
        obj(Items.EASTER_EGG, slots = 1)
        if(!player.hasItem(Items.BUNNY_EARS)) {
            obj(Items.BUNNY_EARS, slots = 1)
        } else {
            nothing(1)
        }
        if(!player.hasItem(Items.BASKET_OF_EGGS)) {
            obj(Items.BASKET_OF_EGGS, slots = 1)
        } else {
            nothing(1)
        }
    }
}

DropTableFactory.register(mysteryBox, Items.MYSTERY_BOX, type = DropTableType.STALL)