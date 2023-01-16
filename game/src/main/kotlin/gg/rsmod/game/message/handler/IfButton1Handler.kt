package gg.rsmod.game.message.handler

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.IfButtonMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.service.log.LoggerService
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class IfButton1Handler : MessageHandler<IfButtonMessage> {

    /**
     * The opcodes for item actions on if3 inventory interfaces
     */
    val FIRST_OPTION = 61
    val SECOND_OPTION = 64
    val THIRD_OPTION = 4
    val FOURTH_OPTION = 18
    val FIFTH_OPTION = 10

    override fun handle(client: Client, world: World, message: IfButtonMessage) {
        val interfaceId = message.hash shr 16
        val component = message.hash and 0xFFFF
        val option = message.option + 1

        if (!client.interfaces.isVisible(interfaceId)) {
            return
        }

        log(
            client,
            "Click button: component=[%d:%d], option=%d, slot=%d, item=%d",
            interfaceId,
            component,
            option,
            message.slot,
            message.item
        )

        if (world.devContext.debugButtons) {
            client.writeFilterableMessage("Button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}, opcode=${message.opcode}]")
        }

        client.attr[INTERACTING_ITEM_ID] = message.item
        client.attr[INTERACTING_SLOT_ATTR] = message.slot
        client.attr[INTERACTING_OPCODE_ATTR] = message.opcode


        if(interfaceId == 679) {
            when (message.opcode) {
                FIRST_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 3)
                }

                SECOND_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 2)
                }

                THIRD_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 4)
                }

                FOURTH_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 1)
                }

                FIFTH_OPTION -> {
                    handleDropItem(client, world, interfaceId, component, message.item, message.slot)
                }
            }
        }

        if (!world.plugins.executeButton(client, interfaceId, component)) {
            return
        }
    }

    private fun handleItemAction(client: Client, world: World, itemId: Int, slot: Int, option: Int) {
        if (!client.lock.canItemInteract()) {
            return
        }

        if(itemId > -1) {
            val item = client.inventory[slot] ?: return
            if (item.id != itemId) {
                return
            }

            client.attr[INTERACTING_ITEM] = WeakReference(item)
            client.attr[INTERACTING_ITEM_ID] = item.id
            client.attr[INTERACTING_ITEM_SLOT] = slot

            if(option == 2) {
                val result = EquipAction.equip(client, item, slot)
                if (result == EquipAction.Result.UNHANDLED && world.devContext.debugItemActions) {
                    client.writeMessage("Unhandled equip action: [item=${item.id}, slot=${slot}]")
                }
                return
            }

            if (!world.plugins.executeItem(client, item.id, 1) && world.devContext.debugItemActions) {
                client.writeMessage("Unhandled item action: [item=${item.id}, slot=${slot}, option=$option]")
                return
            }
        }
    }

    private fun handleDropItem(client: Client, world: World, interfaceId: Int, component: Int, itemId: Int, slot: Int) {
        if (!client.lock.canDropItems()) {
            return
        }

        if (itemId > -1) {
            val item = client.inventory[slot] ?: return

            if(item.id != itemId) {
                return
            }

            log(
                client,
                "Drop item: item=[%d, %d], slot=%d, interfaceId=%d, component=%d",
                item.id,
                item.amount,
                slot,
                interfaceId,
                component
            )

            client.attr[INTERACTING_ITEM] = WeakReference(item)
            client.attr[INTERACTING_ITEM_ID] = item.id
            client.attr[INTERACTING_ITEM_SLOT] = slot

            client.resetFacePawn()

            if (world.plugins.canDropItem(client, item.id)) {
                val remove = client.inventory.remove(item, assureFullRemoval = false, beginSlot = slot)
                if (remove.completed > 0) {
                    val floor = GroundItem(item.id, remove.completed, client.tile, client)
                    remove.firstOrNull()?.let { removed ->
                        floor.copyAttr(removed.item.attr)
                    }
                    world.spawn(floor)
                    world.getService(LoggerService::class.java, searchSubclasses = true)
                        ?.logItemDrop(client, Item(item.id, remove.completed), slot)
                }
            }
        }
    }

}