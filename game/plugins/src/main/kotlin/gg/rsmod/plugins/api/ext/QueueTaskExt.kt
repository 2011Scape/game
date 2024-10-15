package gg.rsmod.plugins.api.ext

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.fs.def.NpcDef
import gg.rsmod.game.message.impl.KeyTypedMessage
import gg.rsmod.game.message.impl.ResumePauseButtonMessage
import gg.rsmod.game.model.Appearance
import gg.rsmod.game.model.attr.INTERACTING_NPC_ATTR
import gg.rsmod.game.model.attr.LAST_KNOWN_ITEMBOX_ITEM
import gg.rsmod.game.model.attr.LAST_KNOWN_SPACE_ACTION
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.FacialExpression
import gg.rsmod.plugins.api.cfg.SkillDialogueOption
import gg.rsmod.util.Misc
import gg.rsmod.util.TextWrapping
import java.util.*

/**
 * The id for the appearance interface.
 */
const val APPEARANCE_INTERFACE_ID = 269

/**
 * The default action that will occur when interrupting or finishing a dialog.
 */
private val closeDialog: QueueTask.() -> Unit = {
    if (player.interfaces.isOccupied(752, 12)) {
        player.closeComponent(parent = 752, child = 12)
    }
    if (player.interfaces.isOccupied(752, 13)) {
        player.closeComponent(parent = 752, child = 13)
    }

    player.interfaces.optionsOpen = false
}

/**
 * Invoked when input dialog queues are interrupted.
 */
private val closeInput: QueueTask.() -> Unit = {
    player.closeInputDialog()
}

/**
 * Invoked when the appearance input is interrupted.
 */
private val closeAppearance: QueueTask.() -> Unit = {
    player.closeInterface(APPEARANCE_INTERFACE_ID)
}

/**
 * Gets the [QueueTask.ctx] as a [Pawn].
 *
 * If [QueueTask.ctx] is not a [Pawn], a cast exception will be thrown.
 */
inline val QueueTask.pawn: Pawn get() = ctx as Pawn

/**
 * Gets the [QueueTask.ctx] as a [Player].
 *
 * If [QueueTask.ctx] is not a [Pawn], a cast exception will be thrown.
 */
inline val QueueTask.player: Player get() = ctx as Player

/**
 * Gets the [QueueTask.ctx] as an [Npc].
 *
 * If [QueueTask.ctx] is not a [Pawn], a cast exception will be thrown.
 */
inline val QueueTask.npc: Npc get() = ctx as Npc

/**
 * Prompts the player with options.
 *
 * @return
 * @return
 * The id of the option chosen. The id can range from [1] inclusive to [options] inclusive.
 */
suspend fun QueueTask.options(
    vararg options: String,
    title: String = "Select an Option",
): Int {
    val optionsFiltered = options.filterNot { it.isEmpty() || it == "" }
    val interfaceId = 224 + (2 * optionsFiltered.size)

    player.openInterface(interfaceId = interfaceId, parent = 752, child = 13)
    for (i in optionsFiltered.indices) {
        player.setComponentText(interfaceId = interfaceId, component = i + 2, text = optionsFiltered[i])
    }
    player.setComponentText(interfaceId = interfaceId, component = 1, text = title)
    player.interfaces.optionsOpen = true

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)

    // check if a key was pressed
    // note that options max out at 5, so
    // keys after 5 (such as 6)
    // will just reset the option back to 1, 2.. etc
    val keyMsg = requestReturnValue as? KeyTypedMessage
    if (keyMsg != null) {
        return if (keyMsg.keycode in 16..20) {
            keyMsg.keycode - 15
        } else {
            keyMsg.keycode - 20
        }
    }

    return (requestReturnValue as? ResumePauseButtonMessage)?.let { it.button - 1 } ?: -1
}

/**
 * Prompts the player with an input dialog where they can only enter an integer.
 *
 * @return
 * The integer input.
 */
suspend fun QueueTask.inputInt(description: String = "Enter amount"): Int {
    player.runClientScript(108, description)

    terminateAction = closeInput
    waitReturnValue()
    terminateAction!!(this)

    return requestReturnValue as? Int ?: -1
}

/**
 * Prompts the player with an input dialog where they can enter a string.
 *
 * @param description the title, or description, of the dialog box.
 *
 * @return the string input.
 */
suspend fun QueueTask.inputString(description: String = "Enter text"): String {
    player.runClientScript(110, description)

    terminateAction = closeInput
    waitReturnValue()
    terminateAction!!(this)

    return requestReturnValue as? String ?: ""
}

/**
 * Prompts the player with an input dialog where they can only enter a player
 * username.
 *
 * @param description the title, or description, of the dialog box.
 *
 * @return the player with the respective username, null if the player is not
 * online.
 */
suspend fun QueueTask.inputPlayer(description: String = "Enter name"): Player? {
    player.runClientScript(109, description)

    terminateAction = closeInput
    waitReturnValue()
    terminateAction!!(this)

    return requestReturnValue as? Player
}

/**
 * Prompts the player with a chatbox interface that allows them to search
 * for an item.
 *
 * @return
 * The selected item's id.
 */
suspend fun QueueTask.searchItemInput(message: String): Int {
    player.runClientScript(750, message, 1, -1)

    terminateAction = closeInput
    waitReturnValue()

    return requestReturnValue as? Int ?: -1
}

/**
 * Sends a normal message dialog.
 *
 * @message
 * The message to render on the dialog box.
 *
 * @lineSpacing
 * The spacing, in pixels, in between each line that will be rendered on the
 * dialog box.
 */
suspend fun QueueTask.messageBox(
    message: String,
    @Suppress("UNUSED_PARAMETER") lineSpacing: Int = 31,
) {
    player.openInterface(interfaceId = 210, parent = 752, child = 13)
    player.setComponentText(interfaceId = 210, component = 1, text = message)
    player.setInterfaceEvents(interfaceId = 210, component = 1, range = -1..-1, setting = 1)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.doubleMessageBox(vararg message: String) {
    player.openInterface(interfaceId = 211, parent = 752, child = 13)
    player.setComponentText(interfaceId = 211, component = 1, text = message[0])
    player.setComponentText(interfaceId = 211, component = 2, text = message[1])
    player.setInterfaceEvents(interfaceId = 211, component = 3, range = -1..-1, setting = 1)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.messageBox4(vararg message: String) {
    player.openInterface(interfaceId = 213, parent = 752, child = 13)
    player.setComponentText(interfaceId = 213, component = 1, text = message[0])
    player.setComponentText(interfaceId = 213, component = 2, text = message[1])
    player.setComponentText(interfaceId = 213, component = 3, text = message[2])
    player.setComponentText(interfaceId = 213, component = 4, text = message[3])
    player.setInterfaceEvents(interfaceId = 213, component = 5, range = -1..-1, setting = 1)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.messageBox5(vararg message: String) {
    player.openInterface(interfaceId = 214, parent = 752, child = 13)
    player.setComponentText(interfaceId = 214, component = 1, text = message[0])
    player.setComponentText(interfaceId = 214, component = 2, text = message[1])
    player.setComponentText(interfaceId = 214, component = 3, text = message[2])
    player.setComponentText(interfaceId = 214, component = 4, text = message[3])
    player.setComponentText(interfaceId = 214, component = 5, text = message[4])
    player.setInterfaceEvents(interfaceId = 214, component = 6, range = -1..-1, setting = 1)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

/**
 * Send a dialog with a npc's head model.
 *
 * @param message
 * The message to render on the dialog box.
 *
 * @npc
 * The npc id which represents the npc that will be drawn on the dialog box.
 * If set to -1, the npc id will be set to the player's interacting npc. If the
 * player is not interacting with an npc, a [RuntimeException] will be thrown.
 *
 * @animation
 * The animation id of the npc's head model.
 *
 * @title
 * The title of the dialog, if left as null, the npc's name will be used.
 */
suspend fun QueueTask.chatNpc(
    vararg message: String,
    npc: Int = -1,
    facialExpression: FacialExpression = FacialExpression.HAPPY_TALKING,
    title: String? = null,
    wrap: Boolean = false,
) {
    var npcId =
        if (npc != -1) {
            npc
        } else {
            player.attr[INTERACTING_NPC_ATTR]?.get()?.getTransform(player)
                ?: throw RuntimeException("Npc id must be manually set as the player is not interacting with an npc.")
        }
    val dialogTitle =
        title ?: player.world.definitions
            .get(NpcDef::class.java, npcId)
            .name

    if (player.attr.has(INTERACTING_NPC_ATTR) && player.attr[INTERACTING_NPC_ATTR]?.get()?.getTransmogId() != -1) {
        npcId = player.attr[INTERACTING_NPC_ATTR]?.get()?.getTransmogId()!!
    }

    val wrappedMessages =
        if (wrap) {
            message.flatMap { TextWrapping.wrap(it)?.toList() ?: emptyList() }
        } else {
            message.toList()
        }

    val interfaceId = 240 + wrappedMessages.size
    player.openInterface(interfaceId = interfaceId, parent = 752, child = 13)
    player.setComponentNpcHead(interfaceId = interfaceId, component = 2, npc = npcId)
    player.setComponentAnim(interfaceId = interfaceId, component = 2, anim = facialExpression.animationId)
    player.setComponentText(interfaceId = interfaceId, component = 3, text = dialogTitle)
    for (i in wrappedMessages.indices) {
        player.setComponentText(interfaceId = interfaceId, component = i + 4, text = wrappedMessages[i])
    }

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

/**
 * Send a dialog with your player's head model.
 *
 * @param message
 * The message to render on the dialog box.
 */
suspend fun QueueTask.chatPlayer(
    vararg message: String,
    facialExpression: FacialExpression = FacialExpression.HAPPY_TALKING,
    title: String? = null,
    wrap: Boolean = false,
) {
    val dialogTitle = title ?: Misc.formatForDisplay(player.username)

    val wrappedMessages =
        if (wrap) {
            message.flatMap { TextWrapping.wrap(it)?.toList() ?: emptyList() }
        } else {
            message.toList()
        }

    val interfaceId = 63 + wrappedMessages.size
    player.openInterface(interfaceId = interfaceId, parent = 752, child = 13)
    player.setComponentPlayerHead(interfaceId = interfaceId, component = 2)
    player.setComponentAnim(interfaceId = interfaceId, component = 2, anim = facialExpression.animationId)
    player.setComponentText(interfaceId = interfaceId, component = 3, text = dialogTitle)
    for (i in wrappedMessages.indices) {
        player.setComponentText(interfaceId = interfaceId, component = i + 4, text = wrappedMessages[i])
    }

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

/**
 * Sends a single item dialog.
 *
 * @param message
 * The message to render on the dialog box.
 *
 * @param item
 * The id of the item to show on the dialog.
 *
 * @param amountOrZoom
 * The amount or zoom of the item to show on the dialog.
 */
suspend fun QueueTask.itemMessageBox(
    message: String,
    item: Int,
    amountOrZoom: Int = 1,
) {
    player.setComponentItem(interfaceId = 519, component = 0, item = item, amountOrZoom = amountOrZoom)
    player.setComponentText(interfaceId = 519, component = 1, text = message)
    player.openInterface(interfaceId = 519, parent = 752, child = 12)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.doubleItemMessageBox(
    message: String,
    item1: Int,
    item2: Int,
    amount1: Int = 1,
    amount2: Int = 1,
) {
    player.setComponentItem(interfaceId = 131, component = 0, item = item1, amountOrZoom = amount1)
    player.setComponentItem(interfaceId = 131, component = 2, item = item2, amountOrZoom = amount2)
    player.setComponentText(interfaceId = 131, component = 1, text = message)
    player.openInterface(interfaceId = 131, parent = 752, child = 12)

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.destroyItem(item: Int) {
    val itemName =
        player.world.definitions
            .get(ItemDef::class.java, item)
            .name
    player.setComponentItem(interfaceId = 94, component = 9, item = item, amountOrZoom = 1)
    player.setComponentText(interfaceId = 94, component = 8, text = itemName)
    player.setComponentText(interfaceId = 94, component = 2, text = "Are you sure you want to destroy this item?")
    player.setComponentText(
        interfaceId = 94,
        component = 7,
        text = "If you destroy this item, you will have to earn it again.",
    )
    player.openInterface(interfaceId = 94, parent = 752, child = 12)

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)

    val result = (requestReturnValue as? ResumePauseButtonMessage)?.let { it.button - 1 } ?: -1

    if (result == 2) {
        player.inventory.remove(item)
    }
}

suspend fun QueueTask.selectAppearance(): Appearance? {
    player.openInterface(APPEARANCE_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)

    terminateAction = closeAppearance
    waitReturnValue()
    terminateAction?.invoke(this)

    return requestReturnValue as? Appearance
}

suspend fun QueueTask.levelUpMessageBox(
    skill: Int,
    levelIncrement: Int,
) {
    val skillName = Skills.getSkillName(player.world, skill)
    val levelFormat = if (levelIncrement == 1) Misc.formatForVowel(skillName) else "$levelIncrement"

    player.graphic(id = 199, height = 100)
    player.setComponentText(
        interfaceId = 740,
        component = 0,
        text = "Congratulations! You've just advanced $levelFormat $skillName ${"level".pluralSuffix(levelIncrement)}!",
    )
    player.setComponentText(
        interfaceId = 740,
        component = 1,
        text = "You have now reached level ${player.skills.getMaxLevel(skill)}!",
    )
    player.openInterface(parent = 752, child = 13, interfaceId = 740)
    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)
}

suspend fun QueueTask.produceItemBox(
    vararg items: Int,
    option: SkillDialogueOption = SkillDialogueOption.MAKE,
    title: String = "Choose how many you wish to make, then<br>click on the chosen item to begin.",
    maxItems: Int = player.inventory.capacity,
    names: Array<String> = emptyArray(),
    extraNames: Array<String> = emptyArray(),
    logic: Player.(Int, Int) -> Unit,
) {
    val defs = player.world.definitions
    val itemDefs = items.map { defs.get(ItemDef::class.java, it) }

    val baseChild = 14
    val itemArray = IntArray(itemDefs.size)
    val nameArray = Array(itemDefs.size) { "" }

    itemDefs.forEachIndexed { index, def ->
        itemArray[index] = def.id
        nameArray[index] = def.name
    }

    // clears the item container
    for (i in 0..9) {
        if (i >= 6) {
            player.setVarc(id = (i + 1139) - 6, value = -1)
        } else {
            player.setVarc(id = (i + 755), value = -1)
        }
    }

    player.openInterface(interfaceId = 905, parent = 752, child = 13)
    player.openInterface(interfaceId = 916, parent = 905, child = 4)
    player.setComponentText(interfaceId = 916, component = 1, text = title)
    player.setEvents(interfaceId = 916, component = 8, from = -1, to = 0, setting = 2)
    player.setVarc(754, option.id)

    player.setVarbit(MAKE_MAX_QUANTITY_VARBIT, maxItems)
    player.setVarbit(MAKE_QUANTITY_VARBIT, maxItems)

    // adds items to the container
    itemArray.forEachIndexed { index, i ->
        if (index >= 6) {
            player.setVarc(id = (index + 1139) - 6, value = i)
            player.setVarcString(
                id = (index + 280) - 6,
                text =
                    (if (names.isNotEmpty()) names[index] else nameArray[index]) +
                        (if (extraNames.isNotEmpty()) "<br>${extraNames[index]}" else ""),
            )
        } else {
            player.setVarc(id = (index + 755), value = i)
            player.setVarcString(
                id = (index + 132),
                text =
                    (if (names.isNotEmpty()) names[index] else nameArray[index]) +
                        (if (extraNames.isNotEmpty()) "<br>${extraNames[index]}" else ""),
            )
        }
    }

    if (player.attr[LAST_KNOWN_ITEMBOX_ITEM] != itemArray[0]) {
        player.attr[LAST_KNOWN_SPACE_ACTION] = 14
    }

    player.attr[LAST_KNOWN_ITEMBOX_ITEM] = itemArray[0]

    terminateAction = closeDialog
    waitReturnValue()
    terminateAction!!(this)

    val keyMsg = requestReturnValue as? KeyTypedMessage
    val msg = requestReturnValue as? ResumePauseButtonMessage

    var child = -1

    // if the queue was returned with ResumePauseButton
    if (msg != null) {
        child = msg.component
    }

    // if the queue was returned with a KeyTyped
    if (keyMsg != null) {
        // handle number keys
        if (keyMsg.keycode in 16..26) {
            child = keyMsg.keycode - 2
        }

        // handle spacebar
        if (keyMsg.keycode == 83) {
            child = player.attr[LAST_KNOWN_SPACE_ACTION]!!
        }
    }

    if (child < baseChild || child >= baseChild + items.size) {
        if (keyMsg != null) {
            player.message("The action for the key you pressed no longer, or didn't exist.")
            player.message("If using the space bar, it will only recognize your last successful action.")
        }
        return
    }

    // set new spacebar child
    player.attr[LAST_KNOWN_SPACE_ACTION] = child

    val item = items[child - baseChild]
    val qty = player.getMakeQuantity()

    logic(player, item, qty)
}
