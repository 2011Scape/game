package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.npcs.Farmer
import gg.rsmod.plugins.content.skills.farming.npcs.FarmerDialogue

Farmer.values().forEach { farmer ->
    on_npc_option(npc = farmer.id, option = "talk-to") {
        player.queue {
            FarmerDialogue(farmer).mainChat(this)
        }
    }

    farmer.protectionOptions.forEach { protectionOption ->
        on_npc_option(npc = farmer.id, option = protectionOption.option) {
            FarmerDialogue(Farmer.Dantaera).quickProtect(player, protectionOption.patch)
        }
    }
}

Seed.values().forEach {
    val produce = it.produce.id
    val noted = world.definitions.get(ItemDef::class.java, produce).noteLinkId
    if (noted > 0) {
        on_item_on_npc(produce, Npcs.LEPRECHAUN) {
            player.queue {
                val count = player.inventory.getItemCount(produce)
                if (player.inventory.remove(produce, count).hasSucceeded()) {
                    player.inventory.add(noted, count)
                    chatNpc("The tool leprechaun exchanges your produce for bank notes.")
                }
            }
        }
    }
}
