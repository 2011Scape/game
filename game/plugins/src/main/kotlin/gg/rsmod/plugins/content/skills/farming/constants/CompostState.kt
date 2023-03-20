package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.plugins.api.cfg.Items

enum class CompostState(val itemId: Int, val diseaseChanceFactor: Double, val persistenceId: String) {
    None(itemId = -1, diseaseChanceFactor = 1.0, persistenceId = "0"),
    Compost(itemId = Items.COMPOST, diseaseChanceFactor = 0.5, persistenceId = "1"),
    SuperCompost(itemId = Items.SUPERCOMPOST, diseaseChanceFactor = 0.8, persistenceId = "2");

    val replacement = Items.BUCKET

    companion object {
        fun fromPersistenceId(persistenceId: String) = values().first { it.persistenceId == persistenceId }
    }
}
