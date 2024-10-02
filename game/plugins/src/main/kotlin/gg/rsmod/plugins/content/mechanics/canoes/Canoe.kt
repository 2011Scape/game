package gg.rsmod.plugins.content.mechanics.canoes

enum class Canoe(
    val levelRequired: Int,
    val experience: Double,
    val childId: Int,
    val silhouetteChildId: Int,
    val textChildId: Int,
) {
    LOG(levelRequired = 12, experience = 30.0, childId = 30, silhouetteChildId = 0, textChildId = 0),
    DUGOUT(levelRequired = 27, experience = 60.0, childId = 31, silhouetteChildId = 9, textChildId = 3),
    STABLE_DUGOUT(levelRequired = 42, experience = 90.0, childId = 32, silhouetteChildId = 10, textChildId = 2),
    WAKA(levelRequired = 57, experience = 150.0, childId = 33, silhouetteChildId = 8, textChildId = 5),
    ;

    val maxDistance = ordinal + 1

    companion object {
        val values = enumValues<Canoe>()
        val definitions = values.associateBy { it.childId }
    }
}
