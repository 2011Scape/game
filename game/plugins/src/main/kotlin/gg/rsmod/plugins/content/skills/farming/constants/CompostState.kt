package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.plugins.api.cfg.Items

enum class CompostState(val itemId: Int, val persistenceId: Int) {
    None(itemId = -1, persistenceId = 0),
    Compost(itemId = Items.COMPOST, persistenceId = 0),
    SuperCompost(itemId = Items.SUPERCOMPOST, persistenceId = 0);

    val replacement = Items.BUCKET

    companion object {
        fun fromPersistenceId(persistenceId: Int) = values()[persistenceId]
    }
}
