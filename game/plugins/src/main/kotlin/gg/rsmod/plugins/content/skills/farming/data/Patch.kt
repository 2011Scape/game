package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.content.skills.farming.logic.WeedsHandler

enum class Patch(val id: Int) {
    CatherbyHerb(Objs.HERB_PATCH_8151);

    fun canRake(player: Player): Boolean {
        return WeedsHandler.canRake(player, this)
    }
}
