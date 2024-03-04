import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.WolfWhistle

val wolfWhistle = WolfWhistle

on_obj_option(Objs.LADDER_28572, "Climb-up") {
    when(player.getCurrentStage(wolfWhistle)) {
        1 -> {
            player.lockingQueue {
                val npc = Npc(Npcs.GIANT_WOLPERTINGER, Tile(2921, 3444, 1), world)
                world.spawn(npc)
                player.teleportTo(2926, 3442, 1)
                npc.faceTile(Tile(2926, 3442, 1))
                this.chatPlayer("Come on then little, fluffy...")
                player.facePawn(npc)
                this.chatPlayer("Gigantic...")
                this.chatPlayer("scary-looking...")
                this.chatPlayer("razor-fanged...")
                this.chatPlayer("bunny...")
                npc.forceChat("Mrooowr?")
                this.chatPlayer("What is that thing?")
                this.chatPlayer("Maybe, if I leave quietly, it won't notice me.")
                //npc.walkTo(this, Tile(2921, 3443, 1)) Doesn't work for whatever reason. Npc remains in the same spot
                //npc.facePawn(player)
                //npc.graphic() Missing graphic
                //npc.animate() Missing animation
                this.messageBox("A chilling, unnatural fear envelops you!")
                player.forceChat("It's coming to get me!")
                player.animate(8499)
                this.chatPlayer("It's coming to get me!")
                wait(5)
                player.faceTile(Tile(2925, 3442, 1))
                wait(1)
                player.animate(8908)
                wait(2)
                player.teleportTo(2924, 3442, 0)
                world.remove(npc)
                player.advanceToNextStage(wolfWhistle)
            }
        }
        5 -> {
            // TODO: Implement 2nd cutscene
            player.lockingQueue {
                val npc = Npc(Npcs.GIANT_WOLPERTINGER, Tile(2921, 3444, 1), world)
                world.spawn(npc)
                player.teleportTo(2926, 3442, 1)
                this.chatPlayer(
                    "Mustn't look around, bunny will eat me.",
                    "Mustn't look around, bunny will eat me.",
                    "Mustn't look around, bunny will eat me."
                )
                wait(2)
                this.chatPlayer("Okay, I have the spirit wolf pouch, so it can't hurt me...")
                this.chatPlayer("...I hope.")
                wait(4)
                this.chatPlayer("Oh, good. It's too busy gnawing to notice me.")
                npc.facePawn(player)
                npc.forceChat("Raaaarw!")
                this.chatPlayer("I spoke too soon!")
                this.chatPlayer("I'll use the spirit wolf pouch. I hope this works.")
            }

            //TODO: Player spawns wolf, uses scroll on wolpertinger
        }
    }
}