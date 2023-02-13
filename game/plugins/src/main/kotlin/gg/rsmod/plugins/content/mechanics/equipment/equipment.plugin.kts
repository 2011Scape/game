package gg.rsmod.plugins.content.mechanics.equipment

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.mechanics.trading.getTradeSession
import gg.rsmod.plugins.content.mechanics.trading.removeTradeSession

val EQUIP_ITEM_SOUND = 2238

fun bind_unequip(equipment: EquipmentType, child: Int) {
    on_button(interfaceId = 387, component = child) {
        val opt = player.getInteractingOpcode()
        if(player.getTradeSession() != null) {
            val partner = player.attr[INTERACTING_PLAYER_ATTR]?.get()
            partner!!.removeTradeSession()
            player.removeTradeSession()
        }
        when (opt) {
            61 -> {
                val result = EquipAction.unequip(player, equipment.id)
                if (equipment == EquipmentType.WEAPON && result == EquipAction.Result.SUCCESS) {
                        player.sendWeaponComponentInformation()
                }
            }
            25 -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                world.sendExamine(player, item.id, ExamineEntityType.ITEM)
            }
            else -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                val menuOpt = opt - 1
                if (!world.plugins.executeEquipmentOption(player, item.id, menuOpt) && world.devContext.debugItemActions) {
                    val action = item.getDef(world.definitions).equipmentMenu[menuOpt - 1]
                    player.message("Unhandled equipment action: [item=${item.id}, option=$menuOpt, action=$action]")
                }
            }
        }
    }
}

for (equipment in EquipmentType.values) {
    on_equip_to_slot(equipment.id) {
        player.playSound(EQUIP_ITEM_SOUND)
        if (equipment == EquipmentType.WEAPON) {
            player.sendWeaponComponentInformation()
        }
    }
}

bind_unequip(EquipmentType.HEAD, child = 8)
bind_unequip(EquipmentType.CAPE, child = 11)
bind_unequip(EquipmentType.AMULET, child = 14)
bind_unequip(EquipmentType.AMMO, child = 38)
bind_unequip(EquipmentType.WEAPON, child = 17)
bind_unequip(EquipmentType.CHEST, child = 20)
bind_unequip(EquipmentType.SHIELD, child = 23)
bind_unequip(EquipmentType.LEGS, child = 26)
bind_unequip(EquipmentType.GLOVES, child = 29)
bind_unequip(EquipmentType.BOOTS, child = 32)
bind_unequip(EquipmentType.RING, child = 35)