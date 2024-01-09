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
import kotlin.math.min
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
        val kebabEffect: Pair<Int, String>? = if (food == Food.KEBAB) {
            calculateKebabEffect(p)
        } else {
            null
        }
        
        val heal = when (food) {
            Food.ROCKTAIL -> {
                floor(p.skills.getMaxLevel(Skills.CONSTITUTION) / 10.0).toInt() + 10
            }
            Food.KEBAB -> {
                kebabEffect?.first ?: 0
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
        
        if (food == Food.KEBAB) {
            message = kebabEffect?.second ?: ""
        }

        p.filterableMessage(message)
        if (p.skills.getCurrentLevel(Skills.CONSTITUTION) > oldHp && food != Food.KEBAB) {
            p.filterableMessage("It heals some health.")
        }
    }
    
    fun calculateKebabEffect(player: Player): Pair<Int, String> {
        val randomNumber = Random.nextInt(100)
        return when {
            randomNumber < 66 -> {
                // 66% chance: Heal 10% of total Hitpoints
                val healAmount = (player.skills.getMaxLevel(Skills.CONSTITUTION) * 0.1).toInt()
                player.filterableMessage("You eat the kebab.")
                Pair(healAmount, "It heals some health.")
            }
            randomNumber < 87 -> {
                // 21% chance: Heal 10-20 Hitpoints
                val healAmount = Random.nextInt(10, 21)
                Pair(healAmount, "That was a good kebab. You feel a lot better.")
            }
            randomNumber < 96 -> {
                // 9% chance: No healing. 50% chance to lower a skill by 3.
                if (Random.nextInt(100) < 50) {
                    val affectedSkill = (0..24).filter { it != Skills.CONSTITUTION }.random()
                    player.skills.setCurrentLevel(affectedSkill, player.skills.getCurrentLevel(affectedSkill) - min(3, player.skills.getCurrentLevel(affectedSkill)))
                    Pair(0, "That tasted a bit dodgy. You feel a bit ill.")
                } else {
                    Pair(0, "That kebab didn't seem to do a lot.")
                }
            }
            else -> {
                // 4% chance: Heal 30 Hitpoints and boost Attack, Strength, and Defence by 1-3 levels.
                val boost = Random.nextInt(1, 4)
                listOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE).forEach { skill ->
                    player.skills.setCurrentLevel(skill, player.skills.getCurrentLevel(skill) + boost)
                }
                Pair(30, "Wow, that was an amazing kebab! You feel really invigorated.")
            }
        }
    }

}
