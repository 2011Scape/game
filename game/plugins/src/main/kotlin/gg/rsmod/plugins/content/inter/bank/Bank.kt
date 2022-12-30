package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.World
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.ext.*

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Bank {

    const val TAB_COUNT = 9

    const val BANK_INTERFACE_ID = 762
    const val BANK_MAINTAB_COMPONENT = 93
    const val INV_INTERFACE_ID = 763
    const val INV_INTERFACE_CHILD = 0

    const val REARRANGE_MODE_VARP = 304
    const val WITHDRAW_AS_VARB = 115
    const val LAST_X_INPUT = 1249

    fun withdraw(p: Player, id: Int, amt: Int, slot: Int) {
        var withdrawn = 0

        val from = p.bank
        val to = p.inventory

        val amount = Math.min(from.getItemCount(id), amt)
        val note = p.getVarp(WITHDRAW_AS_VARB) == 1

        for (i in slot until from.capacity) {
            val item = from[i] ?: continue
            if (item.id != id) {
                continue
            }

            if (withdrawn >= amount) {
                break
            }

            val left = amount - withdrawn

            val copy = Item(item.id, Math.min(left, item.amount))
            if (copy.amount >= item.amount) {
                copy.copyAttr(item)
            }

            val transfer = from.transfer(to, item = copy, fromSlot = i, note = note, unnote = !note)
            withdrawn += transfer?.completed ?: 0
        }

        if (withdrawn == 0) {
            p.message("You don't have enough inventory space.")
        } else if (withdrawn != amount) {
            p.message("You don't have enough inventory space to withdraw that many.")
        }
    }

    fun deposit(p: Player, id: Int, amt: Int) {
        val from = p.inventory
        val to = p.bank

        val amount = Math.min(from.getItemCount(id), amt)

        var deposited = 0
        for (i in 0 until from.capacity) {
            val item = from[i] ?: continue
            if (item.id != id) {
                continue
            }

            if (deposited >= amount) {
                break
            }

            val left = amount - deposited

            val copy = Item(item.id, Math.min(left, item.amount))
            if (copy.amount >= item.amount) {
                copy.copyAttr(item)
            }

            val placeholderSlot = to.removePlaceholder(p.world, copy)
            val transfer = from.transfer(to, item = copy, fromSlot = i, toSlot = placeholderSlot, note = false, unnote = true)

            if (transfer != null) {
                deposited += transfer.completed
            }
        }

        if (deposited == 0) {
            p.message("Bank full.")
        }
    }

    fun open(p: Player) {
        p.openInterface(BANK_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
        p.openInterface(INV_INTERFACE_ID, InterfaceDestination.TAB_AREA)
        p.inventory.dirty = true
        p.bank.dirty = true
        p.setInterfaceEvents(interfaceId = BANK_INTERFACE_ID, component = 93, from = 0, to = 516, setting = 0x2804FE)
        p.setInterfaceEvents(interfaceId = INV_INTERFACE_ID, component = 0, from = 0, to = 27, setting = 0x25047E)
        p.setVarp(WITHDRAW_AS_VARB, 0)
        p.sendTempVarbit(4893, 1) // currently viewed tab
        p.sendTempVarbit(190, 1) // resets search
    }

    fun ItemContainer.removePlaceholder(world: World, item: Item): Int {
        val def = item.toUnnoted(world.definitions).getDef(world.definitions)
        val slot = if (def.placeholderLink > 0) indexOfFirst { it?.id == def.placeholderLink && it.amount == 0 } else -1
        if (slot != -1) {
            this[slot] = null
        }
        return slot
    }

    fun ItemContainer.insert(from: Int, to: Int) {
        val fromItem = this[from]!! // Shouldn't be null

        this[from] = null

        if (from < to) {
            for (i in from until to) {
                this[i] = this[i + 1]
            }
        } else {
            for (i in from downTo to + 1) {
                this[i] = this[i - 1]
            }
        }
        this[to] = fromItem
    }
}