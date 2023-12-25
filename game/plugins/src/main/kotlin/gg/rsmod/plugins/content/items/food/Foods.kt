package gg.rsmod.plugins.content.items.food

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.ATTACK_DELAY
import gg.rsmod.game.model.timer.COMBO_FOOD_DELAY
import gg.rsmod.game.model.timer.FOOD_DELAY
import gg.rsmod.game.model.timer.POTION_DELAY
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.heal
import gg.rsmod.plugins.api.ext.playSound
import kotlin.math.floor
import kotlin.random.Random

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Foods {

    private const val EAT_FOOD_ANIM = 829
    private const val EAT_FOOD_ON_SLED_ANIM = 1469

    fun canEat(p: Player, food: Food): Boolean = !p.timers.has(if (food.comboFood) COMBO_FOOD_DELAY else FOOD_DELAY)

    fun eat(p: Player, food: Food) {
        val delay = if (food.comboFood) COMBO_FOOD_DELAY else FOOD_DELAY
        val anim = if (p.hasEquipped(EquipmentType.WEAPON, Items.SLED)) EAT_FOOD_ON_SLED_ANIM else EAT_FOOD_ANIM

        val heal = when (food) {
            Food.ROCKTAIL -> {
                floor(p.skills.getMaxLevel(Skills.CONSTITUTION) / 10.0).toInt() + 10
            }
            else -> food.heal
        }

        val oldHp = p.skills.getCurrentLevel(Skills.CONSTITUTION)
        val foodName = p.world.definitions.get(ItemDef::class.java, food.item).name

        p.animate(anim)
        p.playSound(Sfx.EAT)
        if (heal > 0) {
            p.heal(heal, if (food.overheal) heal else 0)
        }


        p.timers[delay] = food.tickDelay
        p.timers[ATTACK_DELAY] = food.tickDelay

        if (food == Food.KARAMBWAN) {
            // Eating Karambwans also blocks drinking potions.
            p.timers[POTION_DELAY] = 3
        }

        if (food == Food.CAVEFISH) {
            val combatSkills = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC)
            val selectedSkill = combatSkills[Random.nextInt(combatSkills.size)]

            // Apply a boost of 2 points to the randomly selected combat skill
            val currentLevel = p.skills.getCurrentLevel(selectedSkill)
            val maxLevel = p.skills.getMaxLevel(selectedSkill)
            val newLevel = minOf(currentLevel + 2, maxLevel + 2) // Ensure that the boost doesn't exceed the maximum allowed level

            p.skills.setCurrentLevel(selectedSkill, newLevel)
        }

        var message = "You eat the ${foodName.lowercase()}."

        if(food.message.isNotEmpty()) {
            message = food.message
        }

        p.filterableMessage(message)
        if (p.skills.getCurrentLevel(Skills.CONSTITUTION) > oldHp) {
            p.filterableMessage("It heals some health.")
        }
    }

}
