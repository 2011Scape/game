package gg.rsmod.plugins.content.mechanics.shops

import gg.rsmod.game.model.attr.CURRENT_SHOP_ATTR
import gg.rsmod.game.model.attr.LAST_VIEWED_SHOP_ITEM_FREE
import gg.rsmod.game.model.attr.LAST_VIEWED_SHOP_ITEM_SLOT

val SHOP_INTERFACE_ID = 620
val INV_INTERFACE_ID = 621
val INFO_INTERFACE_ID = 449

val BUY_OPTS = arrayOf(1, 5, 10, 50, 500)
val SELL_OPTS = arrayOf(1, 5, 10, 50)


on_interface_open(interfaceId = SHOP_INTERFACE_ID) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        shop.viewers.add(player.uid)
    }
}

on_interface_open(interfaceId = INV_INTERFACE_ID) {
    player.setInterfaceEvents(interfaceId = 621, component = 0, from = 0, to = 27, setting = 1266)
    player.runClientScript(150, 621 shl 16, 93, 4, 7, 0, -1, "Value", "", "", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "")
}

on_interface_close(interfaceId = SHOP_INTERFACE_ID) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        shop.viewers.remove(player.uid)
        player.closeInterface(interfaceId = INFO_INTERFACE_ID)
        player.closeInterface(interfaceId = INV_INTERFACE_ID)
        player.openInterface(dest = InterfaceDestination.INVENTORY_TAB)
        player.inventory.dirty = true
    }
}

on_button(interfaceId = SHOP_INTERFACE_ID, component = 25) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        val opcode = player.getInteractingOpcode()
        var slot = player.getInteractingSlot()
        if(slot != 0) {
            slot = player.getInteractingSlot() / 6
        }
        val shopItem = shop.items[slot] ?: return@on_button

        when (opcode) {
            61 -> {
                shop.currency.onSellValueMessage(player, shopItem, false)
                player.attr[LAST_VIEWED_SHOP_ITEM_SLOT] = slot
            }
            25 -> world.sendExamine(player, shopItem.item, ExamineEntityType.ITEM)
            else -> {
                val amount = when (opcode) {
                    64 -> BUY_OPTS[0]
                    4 -> BUY_OPTS[1]
                    52 -> BUY_OPTS[2]
                    81 -> BUY_OPTS[3]
                    91 -> BUY_OPTS[4]
                    else -> return@on_button
                }
                shop.currency.sellToPlayer(player, shop, slot, amount)
            }
        }
    }
}

on_button(interfaceId = INFO_INTERFACE_ID, component = 21) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        val opcode = player.getInteractingOpcode()
        var slot = player.attr[LAST_VIEWED_SHOP_ITEM_SLOT]

        val amount = when (opcode) {
            61 -> BUY_OPTS[0]
            64 -> BUY_OPTS[1]
            4 -> BUY_OPTS[2]
            52 -> BUY_OPTS[3]
            else -> return@on_button
        }
        if(player.attr[LAST_VIEWED_SHOP_ITEM_FREE] == true) {
            shop.currency.giveToPlayer(player, shop, slot!!, amount)
        } else {
            shop.currency.sellToPlayer(player, shop, slot!!, amount)
        }
        player.closeInterface(interfaceId = INFO_INTERFACE_ID)
        player.openInterface(INV_INTERFACE_ID, dest = InterfaceDestination.TAB_AREA)
    }
}

on_button(interfaceId = INFO_INTERFACE_ID, component = 1) {
    player.closeInterface(interfaceId = INFO_INTERFACE_ID)
    player.openInterface(INV_INTERFACE_ID, dest = InterfaceDestination.TAB_AREA)
}

on_button(interfaceId = SHOP_INTERFACE_ID, component = 26) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        val opcode = player.getInteractingOpcode()
        var slot = player.getInteractingSlot()
        if(slot != 0) {
            slot = player.getInteractingSlot() / 4
        }
        val shopItem = shop.sampleItems[slot] ?: return@on_button

        when (opcode) {
            61 -> {
                shop.currency.onSellValueMessage(player, shopItem, true)
                player.attr[LAST_VIEWED_SHOP_ITEM_SLOT] = slot
            }
            25 -> world.sendExamine(player, shopItem.item, ExamineEntityType.ITEM)
            else -> {
                val amount = when (opcode) {
                    64 -> BUY_OPTS[0]
                    4 -> BUY_OPTS[1]
                    52 -> BUY_OPTS[2]
                    81 -> BUY_OPTS[3]
                    91 -> BUY_OPTS[4]
                    else -> return@on_button
                }
                shop.currency.giveToPlayer(player, shop, slot, amount)
            }
        }
    }
}

on_button(interfaceId = INV_INTERFACE_ID, component = 0) {
    player.attr[CURRENT_SHOP_ATTR]?.let { shop ->
        val opcode = player.getInteractingOpcode()
        val slot = player.getInteractingSlot()
        val item = player.inventory[slot] ?: return@on_button

        when (opcode) {
            61 -> shop.currency.onBuyValueMessage(player, shop, item.id)
            25 -> world.sendExamine(player, item.id, ExamineEntityType.ITEM)
            else -> {
                val amount = when (opcode) {
                    52 -> SELL_OPTS[0]
                    81 -> SELL_OPTS[1]
                    91 -> SELL_OPTS[2]
                    18 -> SELL_OPTS[3]
                    else -> return@on_button
                }
                shop.currency.buyFromPlayer(player, shop, slot, amount)
            }
        }
    }
}