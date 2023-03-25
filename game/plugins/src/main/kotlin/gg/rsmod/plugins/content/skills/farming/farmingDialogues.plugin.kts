package gg.rsmod.plugins.content.skills.farming

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
