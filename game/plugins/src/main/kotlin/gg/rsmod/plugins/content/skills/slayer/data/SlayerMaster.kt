package gg.rsmod.plugins.content.skills.slayer.data

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.*
import gg.rsmod.plugins.content.quests.Quest

/**
 * @author Alycia <https://github.com/alycii>
 */
class SlayerData(
    private val assignmentsByMaster: Map<SlayerMaster, List<Assignment>>,
) {
    fun getAssignmentsForMaster(master: SlayerMaster): List<Assignment> {
        return assignmentsByMaster[master] ?: emptyList()
    }
}

data class Assignment(
    val assignment: SlayerAssignment,
    val amount: IntRange = 0..0,
    val requirement: List<Requirement> = emptyList(),
    val questRequirement: QuestRequirement? = null,
    val weight: Double = 1.0,
)

enum class SlayerMaster(
    val id: Int,
    val identifier: String,
    val defaultAmount: IntRange,
    val requiredCombatLevel: Int,
    val reqSlayerLevel: Int,
    private val points: Int,
    private val points10: Int,
    private val points50: Int,
) {
    TURAEL(Npcs.TURAEL, "Turael", 15..50, 3, 1, 1, 3, 10),
    MAZCHNA(Npcs.MAZCHNA, "Mazchna", 40..70, 20, 1, 2, 5, 15),
    VANNAKA(Npcs.VANNAKA, "Vannaka", 60..120, 40, 1, 4, 20, 60),
    CHAELDAR(Npcs.CHAELDAR, "Chaeldar", 70..120, 70, 1, 10, 50, 150),
    SUMONA(Npcs.SUMONA, "Sumona", 120..180, 85, 35, 12, 60, 180),
    DURADEL(Npcs.DURADEL_8466, "Duradel", 130..200, 100, 50, 15, 75, 225),
    KURADAL(Npcs.KURADAL_9085, "Kuradal", 150..250, 110, 75, 18, 90, 270),
    ;

    companion object {
        private val SLAYER_MASTERS = values().associateBy(SlayerMaster::id)

        fun getMaster(id: Int): SlayerMaster? {
            return SLAYER_MASTERS[id]
        }
    }

    fun getPoints(): Int {
        return points
    }

    fun getPoints10(): Int {
        return points10
    }

    fun getPoints50(): Int {
        return points50
    }
}

// TODO: Note, I only added data for monsters that we currently have definitions for.
val slayerData =
    SlayerData(
        mapOf(
            SlayerMaster.TURAEL to
                listOf(
                    Assignment(
                        assignment = SlayerAssignment.BANSHEE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 15),
                            ),
                        weight = 8.45,
                    ),
                    Assignment(assignment = SlayerAssignment.BAT, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.BIRD, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.BEAR, weight = 2.82),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_BUG,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 7),
                            ),
                        weight = 2.82,
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_SLIME,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 17),
                            ),
                        weight = 8.45,
                    ),
                    Assignment(assignment = SlayerAssignment.COW, weight = 2.82),
                    Assignment(
                        assignment = SlayerAssignment.CRAWLING_HAND,
                        weight = 8.45,
                        requirement = listOf(SkillRequirement(skill = Skills.SLAYER, level = 5)),
                    ),
                    // Assignment(assignment = SlayerAssignment.DESERT_LIZARD, weight = 8.45),
                    // Assignment(assignment = SlayerAssignment.DOG, weight = 4.23),
                    Assignment(assignment = SlayerAssignment.DWARF, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.GHOST, weight = 4.23),
                    Assignment(assignment = SlayerAssignment.GOBLIN, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.ICEFIEND, amount = 10..20, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.MINOTAUR, weight = 2.82),
                    // Assignment(assignment = SlayerAssignment.MONKEY, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.SCORPION, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.SKELETON, weight = 4.23),
                    Assignment(assignment = SlayerAssignment.SPIDER, weight = 2.82),
                    Assignment(assignment = SlayerAssignment.WOLF, weight = 4.23),
                    Assignment(assignment = SlayerAssignment.ZOMBIE, weight = 2.82),
                ),
            SlayerMaster.VANNAKA to
                listOf(
                    Assignment(
                        assignment = SlayerAssignment.COCKATRICE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 25),
                                SkillRequirement(skill = Skills.DEFENCE, level = 20),
                            ),
                        weight = 2.17,
                    ),
                    // TODO: have this require dragon slayer
                    Assignment(
                        assignment = SlayerAssignment.GREEN_DRAGON,
                        requirement =
                            listOf(
                                QuestPointRequirement(points = Quest.quests.sumOf { it.pointReward }),
                            ),
                        amount = 30..60,
                        weight = 2.17,
                    ),
                    Assignment(assignment = SlayerAssignment.HILL_GIANT, weight = 2.17),
                    Assignment(assignment = SlayerAssignment.LESSER_DEMON, weight = 2.17),
                    Assignment(assignment = SlayerAssignment.MOSS_GIANT, weight = 2.17),
                    Assignment(
                        assignment = SlayerAssignment.PYREFIEND,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 30),
                            ),
                        weight = 2.17,
                    ),
                    Assignment(assignment = SlayerAssignment.ICE_WARRIOR, weight = 2.17),
                    Assignment(assignment = SlayerAssignment.ICE_GIANT, weight = 2.17),
                    // TODO: these are filler tasks until Morytania is unlocked
                    Assignment(
                        assignment = SlayerAssignment.ROCK_SLUG,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 20),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_BUG,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 7),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_SLIME,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 17),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_CRAWLER,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 10),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CRAWLING_HAND,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 5),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.BANSHEE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 15),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.INFERNAL_MAGE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 45),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.BLOODVELD,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 50),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.ABERRANT_SPECTRE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 60),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.GARGOYLE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 75),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.NECHRYAEL,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 80),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.ABYSSAL_DEMON,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 85),
                            ),
                    ),
                    Assignment(assignment = SlayerAssignment.ZOMBIE),
                ),
            SlayerMaster.MAZCHNA to
                listOf(
                    Assignment(
                        assignment = SlayerAssignment.BANSHEE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 15),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.BAT,
                    ),
                    Assignment(
                        assignment = SlayerAssignment.BEAR,
                    ),
                    // TODO: Add Catablepon
                    Assignment(
                        assignment = SlayerAssignment.CAVE_CRAWLER,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 10),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CAVE_SLIME,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 17),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.COCKATRICE,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 25),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.CRAWLING_HAND,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 5),
                            ),
                    ),
                    // TODO: Add Cyclopes
                    // TODO: Add Desert Lizard
                    // TODO: Add Dog
                    // TODO: Add Flesh Crawler
                    Assignment(
                        assignment = SlayerAssignment.GHOUL,
                    ),
                    Assignment(
                        assignment = SlayerAssignment.GHOST,
                    ),
                    Assignment(
                        assignment = SlayerAssignment.HILL_GIANT,
                    ),
                    // TODO: Add Hobgoblin
                    Assignment(
                        assignment = SlayerAssignment.ICE_WARRIOR,
                    ),
                    // TODO: Add Kalphite
                    // TODO: Add Mogre
                    Assignment(
                        assignment = SlayerAssignment.PYREFIEND,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 30),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.ROCK_SLUG,
                        requirement =
                            listOf(
                                SkillRequirement(skill = Skills.SLAYER, level = 20),
                            ),
                    ),
                    Assignment(
                        assignment = SlayerAssignment.SKELETON,
                    ),
                    // TODO: Add Vampyre
                    // TODO: Add Wallbeast
                    Assignment(
                        assignment = SlayerAssignment.WOLF,
                    ),
                    Assignment(
                        assignment = SlayerAssignment.ZOMBIE,
                    ),
                ),
        ),
    )
