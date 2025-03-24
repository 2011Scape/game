package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.cfg.FacialExpression
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*

class KarimsKebabs(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    init {
        on_npc_option(Npcs.KARIM, option = "talk-to") {
            player.queue {
                chatNpc("Would you like to buy a nice kebab? Only one gold.", facialExpression = FacialExpression.HAPPY_TALKING)
                when (options("I think I'll give it a miss.", "Yes please.")) {
                    1 -> chatPlayer("I think I'll give it a miss.")
                    2 -> {
                        chatPlayer("Yes please.")
                        if (player.inventory.remove(Items.COINS_995, amount = 1, assureFullRemoval = true).hasSucceeded()) {
                            val addKebab = player.inventory.add(Items.KEBAB)
                            if (addKebab.hasFailed()) {
                                val kebab = GroundItem(Items.KEBAB, amount = 1, tile = player.tile, owner = player)
                                world.spawn(kebab)
                            }
                            player.filterableMessage("You buy a kebab.")
                        } else {
                            chatPlayer("Oops, I forgot to bring any money with me.", facialExpression = FacialExpression.SAD_2)
                            chatNpc("Come back when you have some.")
                        }
                    }
                }
            }
        }
    }
}
