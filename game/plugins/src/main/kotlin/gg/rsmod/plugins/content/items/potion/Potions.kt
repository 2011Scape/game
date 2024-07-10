package gg.rsmod.plugins.content.items.potion

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.FOOD_DELAY
import gg.rsmod.game.model.timer.POTION_DELAY
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.getInteractingItemSlot
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.playSound

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Potions {
    private const val TICK_DELAY = 3
    private const val DRINK_POTION_ANIM = 829
    private const val DRINK_POTION_ON_SLED_ANIM = 1469

    private fun canDrink(player: Player): Boolean = !player.timers.has(POTION_DELAY)

    fun drink(
        player: Player,
        potion: Potion,
    ) {
        if (!canDrink(player)) {
            return
        }
        val slot = player.getInteractingItemSlot()
        if (player.inventory.remove(item = potion.item, beginSlot = slot).hasSucceeded()) {
            if (potion.replacement != -1) {
                player.inventory.add(item = potion.replacement, beginSlot = slot)
            }
            val anim =
                if (player.hasEquipped(
                        EquipmentType.WEAPON,
                        Items.SLED,
                    )
                ) {
                    DRINK_POTION_ON_SLED_ANIM
                } else {
                    DRINK_POTION_ANIM
                }
            player.animate(anim)
            player.playSound(Sfx.LIQUID)
            potion.potionType.apply(player)
            player.timers[POTION_DELAY] = TICK_DELAY
            player.timers[FOOD_DELAY] = TICK_DELAY
            val potionName =
                player.world.definitions
                    .get(ItemDef::class.java, potion.item)
                    .name
            var message = "You drink some of your ${potionName.replace(Regex(" \\(([1234])\\)$"), "").lowercase()}."
            if (potion.potionType.message.isNotEmpty()) {
                message = potion.potionType.message
                player.filterableMessage(message)
                return
            }
            player.filterableMessage(message)
            if (potion.replacement == Items.VIAL || potion.replacement == Items.BEER_GLASS) {
                player.filterableMessage("You have finished your potion.")
            } else {
                val num = potionName.substringAfter("(").substringBefore(")").toInt() - 1
                val dosesLeftMessage = "You have $num doses of potion left."
                player.filterableMessage(dosesLeftMessage)
            }
        }
    }
}
