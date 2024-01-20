package gg.rsmod.plugins.content.areas.entrana

import gg.rsmod.plugins.content.areas.portsarim.CharterType
import gg.rsmod.plugins.content.areas.portsarim.Ports
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity
import gg.rsmod.plugins.content.skills.woodcutting.AxeType


val ladder = Objs.LADDER_2408

val dungeonTile = Tile(2822, 9774, 0)

on_obj_option(ladder, "climb-down") {
    player.moveTo(dungeonTile)
    if (player.getCurrentStage(LostCity) == LostCity.ENTRANA_ARRIVAL) player.advanceToNextStage(LostCity)
}

on_item_on_item(Items.KNIFE, Items.DRAMEN_BRANCH) {
    player.inventory.remove(Items.DRAMEN_BRANCH, amount = 1)
    player.inventory.add(Items.DRAMEN_STAFF)
    if (player.getCurrentStage(LostCity) == LostCity.CREATE_DRAMEN_STAFF) player.advanceToNextStage(LostCity)

}

on_obj_option(Objs.DRAMEN_TREE, "chop down") {
    when (player.getCurrentStage(LostCity)) {
        LostCity.ENTRANA_ARRIVAL -> {
            player.queue {
                world.spawn(Npc(Npcs.TREE_SPIRIT, Tile(2860, 9737, 0), world = world))
                on_npc_death(Npcs.TREE_SPIRIT) {
                    player.advanceToNextStage(LostCity) //sets stage 3
                }
            }
        }
        else -> {
            player.queue {
                if (player.skills.getMaxLevel(Skills.WOODCUTTING) >= 36) {
                    if (player.inventory.hasFreeSpace()) {
                        chopDramenTree(this)
                    } else {
                        messageBox("Your inventory is too full to hold any more logs.")
                    }
                } else {
                    messageBox("You need a Woodcutting level of 36 to chop this tree.")
                }
            }
        }
    }
}

suspend fun chopDramenTree(it: QueueTask) {
    val player = it.player
    val axe = AxeType.values.reversed().firstOrNull {
        player.skills
                .getMaxLevel(Skills.WOODCUTTING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                it.item
        ))
    }!!
    val axeAnimation = axe.animation
    player.animate(axeAnimation, idleOnly = true)
    player.filterableMessage("You swing your hatchet at the dramen tree.")
    player.animate(axeAnimation, idleOnly = true)
    it.wait(5)
    player.inventory.add(Items.DRAMEN_BRANCH, amount = 1, assureFullInsertion = true)
    player.animate(-1)
}
