package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getInteractingItemId
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player

class ForinthryBracelet(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    companion object {
        val FORINTHRY_CHARGES = AttributeKey<Int>(persistenceKey = "forinthry_charges")
    }

    val CHARGES_CHECK = TimerKey()
    val forinthryDungeonRegionId = listOf(12445, 12446)
    val bracelets = listOf(
        Items.FORINTHRY_BRACE_1, Items.FORINTHRY_BRACE_2, Items.FORINTHRY_BRACE_3,
        Items.FORINTHRY_BRACE_4, Items.FORINTHRY_BRACE_5)

    init {
        bracelets.forEach {
            on_item_option(it, "Repel") {
                takeOneCharge(player)
            }

            on_equipment_option(it, "Repel") {
                takeOneCharge(player, true)
            }

            on_equipment_option(it, "Check") {
                player.message(minutesLeftMessage(player))
            }
        }

        on_login {
            player.timers[CHARGES_CHECK] = 1
        }

        on_timer(CHARGES_CHECK) {
            if (forinthryDungeonRegionId.contains(player.tile.regionId)) {
                if (player.attr.has(FORINTHRY_CHARGES)) {
                    val charges = player.attr[FORINTHRY_CHARGES]!!
                    player.attr[FORINTHRY_CHARGES] = charges - 1
                    when (charges) {
                        // when 40 seconds worth of charges have been taken
                        (6_000 - 67) -> player.message("<col=ff0000>Your immunity to damage from revenants will expire in 20 seconds.")
                        // when 50 seconds worth of charges have been taken
                        (6_000 - 84) -> player.message("<col=ff0000>Your immunity to damage from revenants will expire in 10 seconds.")
                        // when 50 seconds worth of charges have been taken
                        (6_000 - 100) -> player.message("<col=ff0000>Your immunity to damage from revenants has expired.")
                        // when 20 seconds worth of charges are left
                        33 -> player.message("<col=ff0000>Revenants will become aggressive to you in 20 seconds.")
                        // when 10 seconds worth of charges are left
                        16 -> player.message("<col=ff0000>Revenants will become aggressive to you in 10 seconds.")
                        // when charges run out
                        0 -> {
                            player.message("<col=ff0000>Revenants are once again aggressive towards you.")
                            player.attr.remove(FORINTHRY_CHARGES)
                        }
                    }
                }
            }
            player.timers[CHARGES_CHECK] = 1
        }
    }

    fun takeOneCharge(player: Player, takeFromEquipment: Boolean = false) {
        if (player.attr.has(FORINTHRY_CHARGES) && player.attr[FORINTHRY_CHARGES]!! > 0) {
            player.message(minutesLeftMessage(player))
            return
        }
        val curId = player.getInteractingItemId()
        val nextId = oneLessCharge(curId)
        if (takeFromEquipment) {
            player.equipment.remove(curId)
        }
        else {
            player.inventory.remove(curId)
        }

        // if there is a bracelet with charges remaining
        if (nextId != -1) {
            if (takeFromEquipment) {
                player.equipment.add(nextId, beginSlot = EquipmentType.GLOVES.id)
            }
            else {
                player.inventory.add(nextId)
            }
            player.message(chargesMessage(nextId))
        }
        else {
            player.message("The item degrades.")
        }
        player.message("For one minute, revenants cannot damage you.")
        player.message("For one hour, revenants will be unaggressive to you.")
        player.message("The timers will only decrease while you're in Forinthry Dungeon")
        player.attr[FORINTHRY_CHARGES] = 6_000
    }

    fun minutesLeftMessage(player: Player): String {
        if (!player.attr.has(FORINTHRY_CHARGES)) {
            return "Your immunity to aggression from revenants is not active."
        }
        val minLeft = (player.attr[FORINTHRY_CHARGES]!! / 100).toInt()
        return "You have $minLeft minutes of aggression immunity remaining."
    }

    fun chargesMessage(id: Int): String {
        val charges = when (id) {
            Items.FORINTHRY_BRACE_5 -> "five charges"
            Items.FORINTHRY_BRACE_4 -> "four charges"
            Items.FORINTHRY_BRACE_3 -> "three charges"
            Items.FORINTHRY_BRACE_2 -> "two charges"
            Items.FORINTHRY_BRACE_1 -> "one charge"
            else -> "no charges"
        }
        return "Your forinthry bracelet has $charges left."
    }

    fun oneLessCharge(id: Int): Int {
        return when (id) {
            in Items.FORINTHRY_BRACE_2 downTo Items.FORINTHRY_BRACE_5 -> id + 2
            else -> -1
        }
    }
}
