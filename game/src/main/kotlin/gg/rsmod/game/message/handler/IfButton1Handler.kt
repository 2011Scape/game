package gg.rsmod.game.message.handler

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.IfButtonMessage
import gg.rsmod.game.message.impl.SynthSoundMessage
import gg.rsmod.game.model.ExamineEntityType
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Entity
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTaskSet
import gg.rsmod.game.model.queue.impl.PawnQueueTaskSet
import gg.rsmod.game.service.log.LoggerService
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class IfButton1Handler : MessageHandler<IfButtonMessage> {
    internal val queues: QueueTaskSet = PawnQueueTaskSet()

    /**
     * The opcodes for item actions on if3 inventory interfaces
     */
    val FIRST_OPTION = 61
    val SECOND_OPTION = 64
    val THIRD_OPTION = 4
    val FOURTH_OPTION = 18
    val FIFTH_OPTION = 10
    val EIGHT_OPTION = 25

    override fun handle(
        client: Client,
        world: World,
        message: IfButtonMessage,
    ) {
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
            message.item,
        )

        if (world.devContext.debugButtons) {
            client.writeConsoleMessage(
                "Button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}, opcode=${message.opcode}]",
            )
        }

        client.attr[INTERACTING_BUTTON_ID] = component
        client.attr[INTERACTING_OPT_ATTR] = option
        client.attr[INTERACTING_ITEM_ID] = message.item
        client.attr[INTERACTING_SLOT_ATTR] = message.slot
        client.attr[INTERACTING_OPCODE_ATTR] = message.opcode

        if (interfaceId == 679) {
            when (message.opcode) {
                FIRST_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 1)
                }

                SECOND_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 2)
                }

                THIRD_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 3)
                }

                FOURTH_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 4)
                }

                FIFTH_OPTION -> {
                    val def = world.definitions.get(ItemDef::class.java, message.item)
                    if (def.inventoryMenu.getOrNull(4)?.lowercase() == "destroy") {
                        handleItemAction(client, world, message.item, message.slot, 10)
                    } else {
                        handleDropItem(client, world, interfaceId, component, message.item, message.slot)
                    }
                }

                EIGHT_OPTION -> {
                    handleItemAction(client, world, message.item, message.slot, 8)
                }
            }
        }

        if (!world.plugins.executeButton(client, interfaceId, component)) {
            return
        }
    }

    private fun handleItemAction(
        client: Client,
        world: World,
        itemId: Int,
        slot: Int,
        option: Int,
    ) {
        if (!client.lock.canItemInteract()) {
            return
        }

        if (itemId > -1) {
            val item = client.inventory[slot] ?: return
            if (item.id != itemId) {
                return
            }

            client.attr[INTERACTING_ITEM] = WeakReference(item)
            client.attr[INTERACTING_ITEM_ID] = item.id
            client.attr[INTERACTING_ITEM_SLOT] = slot

            if (option == 2) {
                val result = EquipAction.equip(client, item, slot)
                if (result == EquipAction.Result.UNHANDLED && world.devContext.debugItemActions) {
                    client.writeMessage("Unhandled equip action: [item=${item.id}, slot=$slot]")
                }
                return
            }

            if (option == 8) {
                world.sendExamine(client, item.id, ExamineEntityType.ITEM)
                return
            }

            if (option == 10) {
                val result = world.plugins.executeItem(client, item.id, option)
                if (!result && world.devContext.debugItemActions) {
                    client.writeMessage("Unhandled destroy action: [item=${item.id}, slot=$slot]")
                }
                return
            }

            client.fullInterruption(movement = false, interactions = true, animations = false, queue = true)

            val handled = world.plugins.executeItem(client, item.id, option)

            if (!handled) {
                client.writeFilterableMessage(Entity.NOTHING_INTERESTING_HAPPENS)
                if (world.devContext.debugItemActions) {
                    client.writeMessage("Unhandled item action: [item=${item.id}, slot=$slot, option=$option]")
                    return
                }
            }
        }
    }

    private fun handleDropItem(
        client: Client,
        world: World,
        interfaceId: Int,
        component: Int,
        itemId: Int,
        slot: Int,
    ) {
        if (!client.lock.canDropItems()) {
            return
        }

        if (itemId > -1) {
            val item = client.inventory[slot] ?: return

            if (item.id != itemId) {
                return
            }

            log(
                client,
                "Drop item: item=[%d, %d], slot=%d, interfaceId=%d, component=%d",
                item.id,
                item.amount,
                slot,
                interfaceId,
                component,
            )

            client.attr[INTERACTING_ITEM] = WeakReference(item)
            client.attr[INTERACTING_ITEM_ID] = item.id
            client.attr[INTERACTING_ITEM_SLOT] = slot

            client.fullInterruption(interactions = true, queue = true)

            if (world.plugins.canDropItem(client, item.id)) {
                val remove = client.inventory.remove(item, assureFullRemoval = false, beginSlot = slot)
                if (remove.completed > 0) {
                    val floor = GroundItem(item.id, remove.completed, client.tile, client)
                    remove.firstOrNull()?.let { removed ->
                        floor.copyAttr(removed.item.attr)
                    }
                    client.write(SynthSoundMessage(2739, 1, 0))
                    world.spawn(floor)
                    world
                        .getService(LoggerService::class.java, searchSubclasses = true)
                        ?.logItemDrop(client, Item(item.id, remove.completed), slot)
                }
            }
        }
    }
}
