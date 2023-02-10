package gg.rsmod.plugins.content.items.potion

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.heal
import kotlin.math.floor

enum class PotionType (
    val alteredSkills: IntArray = intArrayOf(-1),
    val alterStrategy: Array<String> = emptyArray(),
    val overheal: Boolean = false,
    val comboFood: Boolean = true,
    val message: String = "",
    val tickDelay: Int = 3,
){
    STRENGTH(alteredSkills = intArrayOf(Skills.STRENGTH), alterStrategy= arrayOf("r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SUPER_STRENGTH(alteredSkills = intArrayOf(Skills.STRENGTH), alterStrategy= arrayOf("s")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    ATTACK(alteredSkills = intArrayOf(Skills.ATTACK), alterStrategy= arrayOf("r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SUPER_ATTACK(alteredSkills = intArrayOf(Skills.ATTACK), alterStrategy= arrayOf("s")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    DEFENCE(alteredSkills = intArrayOf(Skills.DEFENCE), alterStrategy= arrayOf("r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SUPER_DEFENCE(alteredSkills = intArrayOf(Skills.DEFENCE), alterStrategy= arrayOf("s")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    MAGIC(alteredSkills = intArrayOf(Skills.MAGIC), alterStrategy= arrayOf("r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    RANGING(alteredSkills = intArrayOf(Skills.RANGED), alterStrategy= arrayOf("r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    FISHING(alteredSkills = intArrayOf(Skills.FISHING), alterStrategy= arrayOf("r_skill")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    AGILITY(alteredSkills = intArrayOf(Skills.AGILITY), alterStrategy= arrayOf("r_skill")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    HUNTER(alteredSkills = intArrayOf(Skills.HUNTER), alterStrategy= arrayOf("r_skill")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    CRAFTING(alteredSkills = intArrayOf(Skills.CRAFTING), alterStrategy= arrayOf("r_skill")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    FLETCHING(alteredSkills = intArrayOf(Skills.FLETCHING), alterStrategy= arrayOf("r_skill")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    COMBAT(alteredSkills = intArrayOf(Skills.ATTACK, Skills.STRENGTH), alterStrategy= arrayOf("r", "r")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    RESTORE(alteredSkills = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC),
        alterStrategy= arrayOf("restore", "restore","restore","restore","restore")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SUPER_RESTORE(alteredSkills = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC,
        Skills.PRAYER, Skills.COOKING, Skills.WOODCUTTING, Skills.FLETCHING, Skills.FISHING, Skills.FIREMAKING,
        Skills.CRAFTING, Skills.SMITHING, Skills.MINING, Skills.HERBLORE,Skills.AGILITY,Skills.THIEVING, Skills.SLAYER,
        Skills.FARMING, Skills.RUNECRAFTING,Skills.HUNTER,Skills.CONSTRUCTION, Skills.SUMMONING,Skills.DUNGEONEERING),
        alterStrategy= arrayOf("s_restore", "s_restore","s_restore","s_restore","s_restore","s_restore","s_restore",
            "s_restore","s_restore","s_restore","s_restore","s_restore","s_restore","s_restore","s_restore","s_restore",
            "s_restore","s_restore","s_restore","s_restore","s_restore","s_restore","s_restore","s_restore",)){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    PRAYER(alteredSkills = intArrayOf(Skills.PRAYER), alterStrategy= arrayOf("prayer")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SUPER_PRAYER(alteredSkills = intArrayOf(Skills.PRAYER), alterStrategy= arrayOf("s_prayer")){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    },
    SARADOMIN_BREW(alteredSkills= intArrayOf(Skills.HITPOINTS, Skills.DEFENCE, Skills.ATTACK, Skills.STRENGTH,
        Skills.RANGED, Skills.MAGIC), alterStrategy=arrayOf("brewHealth", "brewDef",
        "brewDrain","brewDrain","brewDrain","brewDrain"), overheal = true){
        override fun apply(p: Player) {
            applyBoost(p,alteredSkills,alterStrategy)
        }
    };


    abstract fun apply(p: Player)
    fun applyBoost(p: Player, alteredSkills: IntArray,alterStrategy: Array<String>){
        alteredSkills.forEachIndexed { index, i ->
            val cap = boostCap(p.getSkills().getMaxLevel(i),alterStrategy[index])
            val boost = boostQuantity(p.getSkills().getMaxLevel(i).toDouble(),
                alterStrategy[index])
            if(i == Skills.HITPOINTS) {
                p.heal(boost*10, cap*10)
            } else {
                p.getSkills().alterCurrentLevel(i, boost, cap)
            }
        }
    }
    private fun boostQuantity(currentLevel: Double, boostStrategy: String ) : Int {
        var boost = 0
        when (boostStrategy){
            "r" -> boost = floor(currentLevel/10).toInt() + 3
            "s" -> boost = floor(15*(currentLevel/100)).toInt() + 5
            "restore" -> boost = floor((currentLevel*3)/10).toInt() + 10
            "s_restore" -> boost = floor(currentLevel/4).toInt() + 8
            "prayer" -> boost = floor(currentLevel/4).toInt() + 7
            "s_prayer" -> boost = floor((currentLevel/100)*35).toInt() + 7
            "r_skill" -> boost = 3
            "brewHealth" -> boost = floor(15*(currentLevel/100)).toInt() + 2
            "brewDef" -> boost = floor(currentLevel/5).toInt() + 2
            "brewDrain" -> boost = if((currentLevel-(floor(currentLevel/10).toInt() - 2)) <1) {currentLevel.toInt() - 1
            } else { -(floor(currentLevel/10).toInt() - 2) }
        }
        return boost
    }
    private fun boostCap(currentLevel: Int, boostStrategy: String) : Int {
        var cap = 0
        when (boostStrategy){
            "r" -> cap = boostQuantity(currentLevel.toDouble(), boostStrategy)
            "s" -> cap =  boostQuantity(currentLevel.toDouble(), boostStrategy)
            "brewHealth" -> cap =  boostQuantity(currentLevel.toDouble(), boostStrategy)
            "brewDef" -> cap =  boostQuantity(currentLevel.toDouble(), boostStrategy)
            "brewDrain" -> cap = if(currentLevel == 1) { 0 } else { -124 }
        }
        return cap
    }
}
