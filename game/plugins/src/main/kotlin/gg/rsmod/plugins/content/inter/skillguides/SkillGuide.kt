package gg.rsmod.plugins.content.inter.skillguides

enum class SkillGuide(val child: Int, val bit: Int) {
    ATTACK(child = 200, bit = 1),
    STRENGTH(child = 11, bit = 2),
    DEFENCE(child = 28, bit = 5),
    RANGED(child = 52, bit = 3),
    PRAYER(child = 76, bit = 7),
    MAGIC(child = 93, bit = 4),
    RUNECRAFTING(child = 110, bit = 12),
    CONSTRUCTION(child = 134, bit = 22),
    HITPOINTS(child = 193, bit = 6),
    AGILITY(child = 19, bit = 8),
    HERBLORE(child = 36, bit = 9),
    THIEVING(child = 60, bit = 10),
    CRAFTING(child = 84, bit = 11),
    FLETCHING(child = 101, bit = 19),
    SLAYER(child = 118, bit = 20),
    HUNTER(child = 142, bit = 23),
    MINING(child = 186, bit = 13),
    SMITHING(child = 179, bit = 14),
    FISHING(child = 44, bit = 15),
    COOKING(child = 68, bit = 16),
    FIREMAKING(child = 172, bit = 17),
    WOODCUTTING(child = 165, bit = 18),
    FARMING(child = 126, bit = 21),
    SUMMONING(child = 150, bit = 24),
    DUNGEONEERING(child = 158, bit = 25);

    companion object {
        val values = enumValues<SkillGuide>()
    }
}