package gg.rsmod.plugins.content.items.potion

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.COMBO_FOOD_DELAY
import gg.rsmod.game.model.timer.FOOD_DELAY
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
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
    private const val DRINK_POTION_SOUND = 2393

    fun canDrink(p: Player, potion: Potion): Boolean = !p.timers.has(if (potion.potionType.comboFood) COMBO_FOOD_DELAY else FOOD_DELAY)

    fun drink(p: Player, potion: Potion) {
        val delay = if (potion.potionType.comboFood) COMBO_FOOD_DELAY else FOOD_DELAY
        val anim = if (p.hasEquipped(EquipmentType.WEAPON, Items.SLED)) DRINK_POTION_ON_SLED_ANIM else DRINK_POTION_ANIM
        val oldHp = p.getSkills().getCurrentLevel(Skills.HITPOINTS)
        val potionName = p.world.definitions.get(ItemDef::class.java, potion.item).name
        p.animate(anim)
        p.playSound(DRINK_POTION_SOUND)
        // Potion application strategy handled in PotionType
        potion.potionType.apply(p)
        p.resetFacePawn()
        p.timers[delay] = potion.potionType.tickDelay
        var message = "You drink the ${potionName.lowercase()}."
        if(potion.potionType.message.isNotEmpty()) {
            message = potion.potionType.message
        }
        when (potion) {
            else -> {
                p.filterableMessage(message)
                if (p.getSkills().getCurrentLevel(Skills.HITPOINTS) > oldHp) {
                    p.filterableMessage("It heals some health.")
                }
            }
        }
    }
}