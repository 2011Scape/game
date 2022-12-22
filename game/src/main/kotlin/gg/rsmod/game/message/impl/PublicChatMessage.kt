package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.World

/**
 * @author Alycia
 */
class PublicChatMessage(val world: World, val text: String, val index: Int, val icon: Int, val effects: Int) : Message