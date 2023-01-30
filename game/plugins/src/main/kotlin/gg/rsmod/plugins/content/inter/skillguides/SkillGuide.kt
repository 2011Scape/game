package gg.rsmod.plugins.content.inter.skillguides

enum class SkillGuide(val child: Int, val bit: Int, val id: Int) {
    ATTACK(child = 200, bit = 1, id = 0),
    STRENGTH(child = 11, bit = 2, id = 2),
    DEFENCE(child = 28, bit = 5, id = 1),
    RANGED(child = 52, bit = 3, id = 4),
    PRAYER(child = 76, bit = 7, id = 5),
    MAGIC(child = 93, bit = 4, id = 6),
    RUNECRAFTING(child = 110, bit = 12, id = 20),
    CONSTRUCTION(child = 134, bit = 22, id = 22),
    HITPOINTS(child = 193, bit = 6, id = 3),
    AGILITY(child = 19, bit = 8, id = 16),
    HERBLORE(child = 36, bit = 9, id = 15),
    THIEVING(child = 60, bit = 10, id = 17),
    CRAFTING(child = 84, bit = 11, id = 12),
    FLETCHING(child = 101, bit = 19, id = 9),
    SLAYER(child = 118, bit = 20, id = 18),
    HUNTER(child = 142, bit = 23, id = 21),
    MINING(child = 186, bit = 13, id = 14),
    SMITHING(child = 179, bit = 14, id = 13),
    FISHING(child = 44, bit = 15, id = 10),
    COOKING(child = 68, bit = 16, id = 7),
    FIREMAKING(child = 172, bit = 17, id = 11),
    WOODCUTTING(child = 165, bit = 18, id = 8),
    FARMING(child = 126, bit = 21, id = 19),
    SUMMONING(child = 150, bit = 24, id = 23),
    DUNGEONEERING(child = 158, bit = 25, id = 24);

    companion object {
        val values = enumValues<SkillGuide>()
    }
}