package gg.rsmod.plugins.content.items.potion

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.POTION_DELAY
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.playSound

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Potions {

    private const val DRINK_POTION_ANIM = 829
    private const val DRINK_POTION_ON_SLED_ANIM = 1469
    private const val DRINK_POTION_SOUND = 2401

    fun canDrink(p: Player): Boolean = !p.timers.has(POTION_DELAY)

    fun drink(p: Player, potion: Potion) {

        val anim = if (p.hasEquipped(EquipmentType.WEAPON, Items.SLED)) DRINK_POTION_ON_SLED_ANIM else DRINK_POTION_ANIM

        p.animate(anim)
        p.playSound(DRINK_POTION_SOUND)

        potion.potionType.apply(p)

        p.resetFacePawn()
        p.timers[POTION_DELAY] = potion.potionType.tickDelay

        val potionName = p.world.definitions.get(ItemDef::class.java, potion.item).name
        var message = "You drink some of your ${potionName.replace(Regex(" \\(([1234])\\)$"), "").lowercase()}."
        if (potion.potionType.message.isNotEmpty()) {
            message = potion.potionType.message
        }
        p.filterableMessage(message)
        if (potion.replacement == Items.VIAL || potion.replacement == Items.BEER_GLASS) {
            p.filterableMessage("You have finished your potion.")
        } else {
            val num = potionName.substringAfter("(").substringBefore(")").toInt() - 1
            val dosesLeftMessage = "You have $num doses of potion left."
            p.filterableMessage(dosesLeftMessage)
        }

    }

}