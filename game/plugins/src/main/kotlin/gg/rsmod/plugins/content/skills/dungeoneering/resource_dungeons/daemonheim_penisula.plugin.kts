package gg.rsmod.plugins.content.skills.dungeoneering.resource_dungeons

import gg.rsmod.plugins.content.inter.bank.openDepositBox
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

//Exit
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52862, option = "Enter") {
    player.teleport(Tile(3513, 3666, 0), TeleportType.DUNGEONEERING_GATESTONE)
}
