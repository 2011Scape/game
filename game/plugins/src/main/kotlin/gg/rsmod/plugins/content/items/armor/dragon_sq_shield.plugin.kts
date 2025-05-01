package gg.rsmod.plugins.content.items.armor

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.DoricsQuest

val anvils = arrayOf(Objs.ANVIL, Objs.ANVIL_12692, Objs.ANVIL_2783, Objs.ANVIL_24744)
anvils.forEach {
    on_any_item_on_obj(it) {
        val item = player.getInteractingItem()
        if (item.id != Items.SHIELD_LEFT_HALF && item.id != Items.SHIELD_RIGHT_HALF) {
            return@on_any_item_on_obj
        }

        if (it == Objs.ANVIL && !player.finishedQuest(DoricsQuest)) {
            player.message("You need to complete <col=0000ff>Doric's Quest</col> before you can use this anvil.")
            return@on_any_item_on_obj
        }

        if (!hasBothHalves(player)) {
            player.queue {
                messageBox("You need both halves of the shield before attempting to fix it.")
            }
            return@on_any_item_on_obj
        }

        if (!player.inventory.contains(Items.HAMMER)) {
            player.queue {
                messageBox("You need a hammer in order to fix the shield.")
            }
            return@on_any_item_on_obj
        }

        if (player.skills.getCurrentLevel(Skills.SMITHING) < 60) {
            player.message("You need 60 smithing to fix the Dragon square shield.")
            return@on_any_item_on_obj
        }

        player.queue {
            messageBox("You set to work trying to fix the ancient shield. It's seen some",
                "heavy action and needs some serious work doing to it.")
            player.animate(Anims.HAMMER_FIX_SHIELD)
            wait(4)
            player.animate(Anims.HAMMER_FIX_SHIELD)
            wait(3)
            messageBox("Even for an experienced armourer it is not an easy task, but",
                "eventually it is ready. You have restored the dragon square shield to",
                "its former glory.")

            player.inventory.remove(Items.SHIELD_LEFT_HALF)
            player.inventory.remove(Items.SHIELD_RIGHT_HALF)
            player.inventory.add(Items.DRAGON_SQ_SHIELD)
            player.addXp(Skills.SMITHING, 70.0)
        }
    }
}

fun hasBothHalves(player: Player): Boolean {
    return player.inventory.contains(Items.SHIELD_LEFT_HALF) &&
        player.inventory.contains(Items.SHIELD_RIGHT_HALF)
}
