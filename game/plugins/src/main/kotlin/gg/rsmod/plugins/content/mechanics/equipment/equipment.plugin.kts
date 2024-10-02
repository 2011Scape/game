package gg.rsmod.plugins.content.mechanics.equipment

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.mechanics.trading.getTradeSession
import gg.rsmod.plugins.content.mechanics.trading.removeTradeSession
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.LostCity

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

can_equip_item(item = Items.DRAGON_LONGSWORD) {
    if (!player.finishedQuest(LostCity)) {
        player.message("You need to complete <col=0000ff>Lost City</col> before you can equip the Dragon Longsword.")
        false
    } else {
        true
    }
}

can_equip_item(item = Items.DRAGON_DAGGER) {
    if (!player.finishedQuest(LostCity)) {
        player.message("You need to complete <col=0000ff>Lost City</col> before you can equip the Dragon Dagger.")
        false
    } else {
        true
    }
}

can_equip_item(item = Items.DRAGON_DAGGER_P) {
    if (!player.finishedQuest(LostCity)) {
        player.message("You need to complete <col=0000ff>Lost City</col> before you can equip the Dragon Dagger.")
        false
    } else {
        true
    }
}

can_equip_item(item = Items.DRAGON_DAGGER_P_5680) {
    if (!player.finishedQuest(LostCity)) {
        player.message("You need to complete <col=0000ff>Lost City</col> before you can equip the Dragon Dagger.")
        false
    } else {
        true
    }
}

can_equip_item(item = Items.DRAGON_DAGGER_P_5698) {
    if (!player.finishedQuest(LostCity)) {
        player.message("You need to complete <col=0000ff>Lost City</col> before you can equip the Dragon Dagger.")
        false
    } else {
        true
    }
}

on_button(interfaceId = 387, component = 45) {
    when (player.getInteractingOpcode()) {
        61 -> player.openInterface(interfaceId = KEPT_ON_DEATH_INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
    }
}

fun bind_unequip(
    equipment: EquipmentType,
    child: Int,
) {
    on_button(interfaceId = 387, component = child) {
        val opt = player.getInteractingOpcode()
        if (player.getTradeSession() != null) {
            val partner = player.attr[INTERACTING_PLAYER_ATTR]?.get()
            partner!!.removeTradeSession()
            player.removeTradeSession()
        }
        when (opt) {
            61 -> {
                val result = EquipAction.unequip(player, equipment.id)
                if (equipment == EquipmentType.WEAPON && result == EquipAction.Result.SUCCESS) {
                    player.sendWeaponComponentInformation()
                    player.refreshBonuses()
                }
            }

            25 -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                world.sendExamine(player, item.id, ExamineEntityType.ITEM)
            }

            else -> {
                val item = player.equipment[equipment.id] ?: return@on_button
                val menuOpt =
                    when (opt) {
                        64 -> 1
                        4 -> 2
                        52 -> 3
                        81 -> 4
                        91 -> 5
                        else -> 0
                    }
                if (!world.plugins.executeEquipmentOption(
                        player,
                        item.id,
                        menuOpt,
                    ) &&
                    world.devContext.debugItemActions
                ) {
                    val action = item.getDef(world.definitions).equipmentMenu[menuOpt]
                    player.message(
                        "Unhandled equipment action: [item=${item.id}, option=$menuOpt, action=$action]",
                        ChatMessageType.CONSOLE,
                    )
                }
            }
        }
    }
}

for (equipment in EquipmentType.values) {
    on_equip_to_slot(equipment.id) {
        player.playSound(Sfx.EQUIP_FUN)
        if (equipment == EquipmentType.WEAPON || equipment == EquipmentType.SHIELD) {
            player.sendWeaponComponentInformation()
            player.refreshBonuses()
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
