package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.item.Item

/**
 * @author Tom <rspsmods@gmail.com>
 */
class UpdateInvFullMessage(val items: Array<Item?>, val containerKey: Int, val invother: Boolean) : Message {
    constructor(containerKey: Int, items: Array<Item?>) : this(items, containerKey, false)
}