package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val shamusTile = Tile(3139, 3211)

on_obj_option(Objs.TREE_2409, "chop") {
    when (player.getCurrentStage(LostCity)) {
        LostCity.FINDING_SHAMUS -> {
            player.queue {
                world.spawn(TileGraphic(shamusTile, id = 74, height = 0))
                player.advanceToNextStage(LostCity)
                world.spawn(Npc(id = Npcs.SHAMUS, tile = shamusTile, world = world, owner = player))
                chatNpc(
                    "Hey! Yer big elephant!",
                    "Don't go chopping down me house, now!",
                    npc = Npcs.SHAMUS,
                )
            }
        }
        else -> {
            player.queue {
                chatNpc(
                    "Hey! Yer big elephant!",
                    "Don't go chopping down me house, now!",
                    npc = Npcs.SHAMUS,
                )
            }
        }
    }
}
