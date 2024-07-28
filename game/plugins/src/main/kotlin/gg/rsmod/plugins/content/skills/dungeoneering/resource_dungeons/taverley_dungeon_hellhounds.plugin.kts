package gg.rsmod.plugins.content.skills.dungeoneering.resource_dungeons

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

//Exit
on_obj_option(obj = Objs.MYSTERIOUS_DOOR_52870, option = "Exit") {
    player.teleport(Tile(2854, 9841, 0), TeleportType.DUNGEONEERING_GATESTONE)
}