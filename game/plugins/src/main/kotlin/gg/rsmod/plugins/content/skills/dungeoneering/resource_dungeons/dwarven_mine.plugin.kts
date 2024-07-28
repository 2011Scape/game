package gg.rsmod.plugins.content.skills.dungeoneering.resource_dungeons

import gg.rsmod.plugins.content.inter.bank.openDepositBox
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

//Exit
on_obj_option(obj = Objs.MYSTERIOUS_DOOR, option = "Exit") {
    player.teleport(Tile(3034, 9772, 0), TeleportType.DUNGEONEERING_GATESTONE)
}

//Bank Deposit
    on_obj_option(obj = Objs.BANK_DEPOSIT_BOX_25937, option = "deposit") {
        player.openDepositBox()
    }