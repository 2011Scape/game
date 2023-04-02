package gg.rsmod.plugins.content.mechanics.equipment

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.mechanics.trading.getTradeSession
import gg.rsmod.plugins.content.mechanics.trading.removeTradeSession

val EQUIP_ITEM_SOUND = 2238

val EQUIPMENT_BONUS_INTERFACE_ID = 667
val INVENTORY_INTERFACE_ID = 670
val KEPT_ON_DEATH_INTERFACE = 17

val questItems = arrayOf(Items.QUEST_POINT_CAPE, Items.QUEST_POINT_HOOD)
questItems.forEach {
    can_equip_item(item = it) {
        if (!player.completedAllQuests()) {
            player.message("You need to complete all of the available Quests before you can wear this.")
            false
        } else {
            true
        }
    }
}

on_button(interfaceId = 387, component = 39) {
    when (player.getInteractingOpcode()) {
        61 -> openEquipmentBonuses(player, false)
    }
}

on_interface_close(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID) {
        player.closeInterface(interfaceId = INVENTORY_INTERFACE_ID)
        player.openInterface(dest = InterfaceDestination.INVENTORY_TAB)
        player.inventory.dirty = true
}

private val names = arrayOf(
    "Stab",
    "Slash",
    "Crush",
    "Magic",
    "Ranged",
    "Summoning",
    "Absorb Melee",
    "Absorb Magic",
    "Absorb Ranged",
    "Strength",
    "Ranged Strength",
    "Prayer",
    "Magic Damage"
)


fun openEquipmentBonuses(player: Player, bank: Boolean) {
    player.queue {
        player.openInterface(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID, dest = InterfaceDestination.MAIN_SCREEN)
        player.setVarbit(4894, if (bank) 1 else 0)
        player.setVarbit(8448, 1)
        player.runClientScript(787, 1)//unknown
        player.openInterface(INVENTORY_INTERFACE_ID, dest = InterfaceDestination.TAB_AREA)
        player.setInterfaceEvents(interfaceId = INVENTORY_INTERFACE_ID, component = 0, from = 0, to = 27, 1266)
        player.runClientScript(150, INVENTORY_INTERFACE_ID shl 16, 93, 0, 1, 2)

        player.setInterfaceEvents(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID, component = 7, from = 0, to = 13, 1030)
        player.runClientScript(150, EQUIPMENT_BONUS_INTERFACE_ID shl 16, 94, 0, 8, 9)
        refreshBonuses(player)
        var setting = 0
        for (i in 0 until 3) {
            setting += (2 shl i)
        }
//        player.message("Setting: $setting", type = ChatMessageType.GAME_MESSAGE)
    }
}

fun refreshBonuses(player: Player) {
    player.setVarc(779, player.getWeaponRenderAnimation())
    for (i in 0..17) {
        var bonusName: String = StringBuilder(names[if (i <= 4) i else i - 5]).append(": ").toString()
        val bonus: Int = player.equipmentBonuses[i]
        bonusName = StringBuilder(bonusName).append(if (bonus >= 0) "+" else "").append(bonus).toString()
        if (i == 17 || i in 11..13)//component 42-44 absorb bonuses
            bonusName = StringBuilder(bonusName).append("%").toString()
        player.setComponentText(EQUIPMENT_BONUS_INTERFACE_ID, 31 + i, bonusName)//31 to 48 is bonuses
    }
}

on_button(interfaceId = 387, component = 45) {
    when (player.getInteractingOpcode()) {
        61 -> player.openInterface(interfaceId = KEPT_ON_DEATH_INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
    }
}

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
                        refreshBonuses(player)
                }
            }
            25 -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                world.sendExamine(player, item.id, ExamineEntityType.ITEM)
            }
            else -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                val menuOpt = when(opt) {
                    64 -> 1
                    4 -> 2
                    52 -> 3
                    81 -> 4
                    91 -> 5
                    else -> 0
                }
                if (!world.plugins.executeEquipmentOption(player, item.id, menuOpt) && world.devContext.debugItemActions) {
                    val action = item.getDef(world.definitions).equipmentMenu[menuOpt]
                    player.message("Unhandled equipment action: [item=${item.id}, option=$menuOpt, action=$action]", ChatMessageType.CONSOLE)
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
            refreshBonuses(player)
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