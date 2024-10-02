package gg.rsmod.game.model.combat

/**
 * Represents the combat definition for an npc.
 *
 * @author Tom <rspsmods@gmail.com>
 */
data class NpcCombatDef(
    val lifepoints: Int,
    val stats: List<Int>,
    val attackSpeed: Int,
    val attackAnimation: Int,
    val blockAnimation: Int,
    val deathAnimation: List<Int>,
    val respawnDelay: Int,
    val deathDelay: Int,
    val aggressiveRadius: Int,
    val aggroTargetDelay: Int,
    val aggressiveTimer: Int,
    val poisonDamage: Int,
    val poisonImmunity: Boolean,
    val slayerReq: Int,
    val slayerXp: Double,
    val bonuses: List<Int>,
    val species: Set<Any>,
    val spell: Int,
    val xpMultiplier: Double,
    val slayerAssignment: SlayerAssignment?,
    var attackStyleType: StyleType,
    var deathBlowLifepoints: Int,
) {
    companion object {
        private const val DEFAULT_LIFEPOINTS = 100
        private const val DEFAULT_ATTACK_SPEED = 4
        private const val DEFAULT_RESPAWN_DELAY = 25
        private const val DEFAULT_DEATH_DELAY = 0
        private const val DEFAULT_ATTACK_ANIMATION = -1
        private const val DEFAULT_BLOCK_ANIMATION = -1
        private const val DEFAULT_DEATH_ANIMATION = -1

        val DEFAULT =
            NpcCombatDef(
                lifepoints = DEFAULT_LIFEPOINTS,
                stats = listOf(1, 1, 1, 1, 1),
                attackSpeed = DEFAULT_ATTACK_SPEED,
                attackAnimation = DEFAULT_ATTACK_ANIMATION,
                blockAnimation = DEFAULT_BLOCK_ANIMATION,
                deathAnimation = listOf(DEFAULT_DEATH_ANIMATION),
                respawnDelay = DEFAULT_RESPAWN_DELAY,
                deathDelay = DEFAULT_DEATH_DELAY,
                aggressiveRadius = 0,
                aggroTargetDelay = 0,
                aggressiveTimer = 0,
                poisonDamage = 0,
                poisonImmunity = false,
                slayerReq = 1,
                slayerXp = 0.0,
                bonuses = emptyList(),
                species = emptySet(),
                spell = -1,
                xpMultiplier = 1.0,
                slayerAssignment = null,
                attackStyleType = StyleType.STAB,
                deathBlowLifepoints = -1,
            )
    }
}
