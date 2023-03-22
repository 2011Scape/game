package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.plugins.api.cfg.Items

/**
 * Describes the possible compost states a patch can have
 */
enum class CompostState(val itemId: Int, val diseaseChanceFactor: Double, val persistenceId: String, val xp: Double, val lives: Int) {
    None(itemId = -1, diseaseChanceFactor = 1.0, persistenceId = "0", xp = 0.0, lives = 0),
    Compost(itemId = Items.COMPOST, diseaseChanceFactor = 0.5, persistenceId = "1", 18.0, lives = 1),
    SuperCompost(itemId = Items.SUPERCOMPOST, diseaseChanceFactor = 0.8, persistenceId = "2", 26.0, lives = 2);

    val replacement = Items.BUCKET

    companion object {
        fun fromPersistenceId(persistenceId: String) = values().first { it.persistenceId == persistenceId }
    }
}
