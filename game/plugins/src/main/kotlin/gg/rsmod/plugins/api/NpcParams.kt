package gg.rsmod.plugins.api

import gg.rsmod.game.model.combat.NpcCombatDef
import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.api.ext.enumSetOf
import kotlin.math.max

/**
 * @author Tom <rspsmods@gmail.com>
 */
object NpcSkills {
    const val ATTACK = 0
    const val STRENGTH = 1
    const val DEFENCE = 2
    const val MAGIC = 3
    const val RANGED = 4
}

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class NpcSpecies {
    DEMON,
    SHADE,
    KALPHITE,
    SCARAB,
    DRAGON,
    BASIC_DRAGON,
    BRUTAL_DRAGON,
    METAL_DRAGON,
    FIERY,
    UNDEAD,
}

/**
 * @author Tom <rspsmods@gmail.com>
 */
class NpcCombatBuilder {
    private var maxHealth = -1

    private var attackSpeed = -1

    private var spell = -1

    private var stats = Array(5) { -1 }

    private var defaultAttackAnim = -1

    private var defaultBlockAnim = -1

    private val deathAnimList = mutableListOf<Int>()

    private var respawnDelay = -1

    private var deathDelay = -1

    private var aggroRadius = -1

    private var aggroTargetDelay = -1

    private var aggroTimer = -1

    private var poisonDamage = -1

    private var xpMultiplier = -1.0

    private var poisonImmunity = false

    private var slayerReq = -1

    private var slayerXp = -1.0

    private var slayerAssignment: SlayerAssignment? = null

    private val bonuses = Array(BONUS_COUNT) { 0 }

    private val speciesSet = enumSetOf<NpcSpecies>()

    private var attackStyle = StyleType.STAB

    private var deathBlowLifepoints = -1

    fun build(): NpcCombatDef {
        check(maxHealth != -1) { "Max health must be set." }
        check(attackSpeed != -1) { "Attack speed must be set." }
        check(deathAnimList.isNotEmpty()) { "A death animation must be set." }
        check(respawnDelay != -1) { "Respawn delay must be set." }

        stats.forEachIndexed { index, level ->
            stats[index] = max(1, level)
        }
        poisonDamage = max(0, poisonDamage)
        slayerReq = max(1, slayerReq)
        slayerXp = max(0.0, slayerXp)
        if (xpMultiplier < 0.0) {
            xpMultiplier = 1.0
        }

        if (aggroTimer == -1) {
            aggroTimer = DEFAULT_AGGRO_TIMER
        }

        return NpcCombatDef(
            maxHealth,
            stats.toList(),
            attackSpeed,
            defaultAttackAnim,
            defaultBlockAnim,
            deathAnimList,
            respawnDelay,
            deathDelay,
            aggroRadius,
            aggroTargetDelay,
            aggroTimer,
            poisonDamage,
            poisonImmunity,
            slayerReq,
            slayerXp,
            bonuses.toList(),
            speciesSet,
            spell,
            xpMultiplier,
            slayerAssignment,
            attackStyle,
            deathBlowLifepoints,
        )
    }

    fun setHitpoints(health: Int): NpcCombatBuilder {
        check(maxHealth == -1) { "Max health already set." }
        maxHealth = health
        return this
    }

    /**
     * @param speed the attack speed, in cycles.
     */
    fun setAttackSpeed(speed: Int): NpcCombatBuilder {
        check(attackSpeed == -1) { "Attack speed already set." }
        attackSpeed = speed
        return this
    }

    fun setSpell(spellId: Int): NpcCombatBuilder {
        check(spell == -1) { "Spell already set." }
        spell = spellId
        return this
    }

    fun setDeathBlowLifepoints(lifepoints: Int): NpcCombatBuilder {
        check(deathBlowLifepoints == -1) { "Death blow lifepoitns already set." }
        deathBlowLifepoints = lifepoints
        return this
    }

    fun setAttackStyle(styleType: StyleType): NpcCombatBuilder {
        check(attackStyle == StyleType.STAB) { "Attack style already set." }
        attackStyle = styleType
        return this
    }

    fun setAttackLevel(level: Int): NpcCombatBuilder {
        check(stats[NpcSkills.ATTACK] == -1) { "Attack level already set." }
        stats[NpcSkills.ATTACK] = level
        return this
    }

    fun setStrengthLevel(level: Int): NpcCombatBuilder {
        check(stats[NpcSkills.STRENGTH] == -1) { "Strength level already set." }
        stats[NpcSkills.STRENGTH] = level
        return this
    }

    fun setDefenceLevel(level: Int): NpcCombatBuilder {
        check(stats[NpcSkills.DEFENCE] == -1) { "Defence level already set." }
        stats[NpcSkills.DEFENCE] = level
        return this
    }

    fun setMagicLevel(level: Int): NpcCombatBuilder {
        check(stats[NpcSkills.MAGIC] == -1) { "Magic level already set." }
        stats[NpcSkills.MAGIC] = level
        return this
    }

    fun setRangedLevel(level: Int): NpcCombatBuilder {
        check(stats[NpcSkills.RANGED] == -1) { "Ranged level already set." }
        stats[NpcSkills.RANGED] = level
        return this
    }

    fun setLevel(
        index: Int,
        level: Int,
    ): NpcCombatBuilder {
        check(stats[index] == -1) { "Level [$index] already set." }
        stats[index] = level
        return this
    }

    fun setLevels(
        attack: Int,
        strength: Int,
        defence: Int,
        magic: Int,
        ranged: Int,
    ): NpcCombatBuilder {
        setAttackLevel(attack)
        setDefenceLevel(defence)
        setStrengthLevel(strength)
        setMagicLevel(magic)
        setRangedLevel(ranged)
        return this
    }

    fun setDefaultAttackAnimation(animation: Int): NpcCombatBuilder {
        check(defaultAttackAnim == -1) { "Default attack animation already set." }
        defaultAttackAnim = animation
        return this
    }

    fun setDefaultBlockAnimation(animation: Int): NpcCombatBuilder {
        check(defaultBlockAnim == -1) { "Default block animation already set." }
        defaultBlockAnim = animation
        return this
    }

    fun setCombatAnimations(
        attackAnimation: Int,
        blockAnimation: Int,
    ): NpcCombatBuilder {
        setDefaultAttackAnimation(attackAnimation)
        setDefaultBlockAnimation(blockAnimation)
        return this
    }

    fun setDeathAnimation(vararg anims: Int): NpcCombatBuilder {
        check(anims.isNotEmpty()) { "Must specify at least one animation." }
        check(deathAnimList.isEmpty()) { "Death animation(s) already set." }
        anims.forEach { deathAnimList.add(it) }
        return this
    }

    fun setRespawnDelay(cycles: Int): NpcCombatBuilder {
        check(respawnDelay == -1) { "Respawn delay already set." }
        respawnDelay = cycles
        return this
    }

    fun setDeathDelay(cycles: Int): NpcCombatBuilder {
        check(deathDelay == -1) { "Death delay already set." }
        deathDelay = cycles
        return this
    }

    fun setAggroRadius(radius: Int): NpcCombatBuilder {
        check(aggroRadius == -1) { "Aggro radius already set." }
        aggroRadius = radius
        return this
    }

    fun setFindAggroTargetDelay(delay: Int): NpcCombatBuilder {
        check(aggroTargetDelay == -1) { "Aggro target delay already set." }
        aggroTargetDelay = delay
        return this
    }

    fun setAggroTimer(timer: Int): NpcCombatBuilder {
        check(aggroTimer == -1) { "Aggro timer already set." }
        aggroTimer = timer
        return this
    }

    fun setPoisonDamage(damage: Int): NpcCombatBuilder {
        check(poisonDamage == -1) { "Poison damage already set." }
        poisonDamage = damage
        return this
    }

    fun setXpMultiplier(multiplier: Double): NpcCombatBuilder {
        check(xpMultiplier == -1.0) { "Xp multiplier already set." }
        xpMultiplier = multiplier
        return this
    }

    fun setPoisonImmunity(): NpcCombatBuilder {
        check(!poisonImmunity) { "Poison immunity already set." }
        poisonImmunity = true
        return this
    }

    fun setSlayerRequirement(levelReq: Int): NpcCombatBuilder {
        check(slayerReq == -1) { "Slayer requirement already set." }
        slayerReq = levelReq
        return this
    }

    fun setSlayerXp(xp: Double): NpcCombatBuilder {
        check(slayerXp == -1.0) { "Slayer xp already set." }
        slayerXp = xp
        return this
    }

    fun setSlayerParams(
        levelReq: Int,
        xp: Double,
        assignment: SlayerAssignment,
    ): NpcCombatBuilder {
        setSlayerRequirement(levelReq)
        setSlayerXp(xp)
        slayerAssignment = assignment
        return this
    }

    fun setBonus(
        index: Int,
        value: Int,
    ): NpcCombatBuilder {
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setAttackStabBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.ATTACK_STAB.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setAttackSlashBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.ATTACK_SLASH.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setAttackCrushBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.ATTACK_CRUSH.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setAttackMagicBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.ATTACK_MAGIC.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setAttackRangedBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.ATTACK_RANGED.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setDefenceStabBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.DEFENCE_STAB.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setDefenceSlashBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.DEFENCE_SLASH.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setDefenceCrushBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.DEFENCE_CRUSH.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setDefenceMagicBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.DEFENCE_MAGIC.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun setDefenceRangedBonus(value: Int): NpcCombatBuilder {
        val index = BonusSlot.DEFENCE_RANGED.id
        check(bonuses[index] == 0) { "Bonus [$index] already set." }
        bonuses[index] = value
        return this
    }

    fun addSpecies(species: NpcSpecies): NpcCombatBuilder {
        speciesSet.add(species)
        return this
    }

    fun setSpecies(vararg species: NpcSpecies): NpcCombatBuilder {
        check(speciesSet.isEmpty()) { "Species already set." }
        speciesSet.addAll(species)
        return this
    }

    companion object {
        private const val BONUS_COUNT = 14
        private const val DEFAULT_AGGRO_TIMER = 1000 // 10 minutes
    }
}
