package gg.rsmod.plugins.api

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class HitType(val id: Int) {
    BLOCK(id = 8),
    REGULAR_HIT(id = 3),
    MELEE(id = 0),
    RANGE(id = 1),
    MAGIC(id = 2),
    REFLECTED(id = 4),
    CANNON(id = 13),
    ABSORB(id = 5),
    POISON(id = 6),
    DISEASE(id = 7),
    HEAL(id = 9),
    CRIT_MELEE(id = 10);
}