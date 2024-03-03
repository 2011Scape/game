import gg.rsmod.game.model.attr.TAKEN_FROM_STIKKLEBRIX
import gg.rsmod.plugins.content.quests.impl.WolfWhistle
import gg.rsmod.plugins.content.quests.startedQuest

val wolfWhistle = WolfWhistle

on_obj_option(Objs.DEAD_BODY, "Search") {
    if (player.startedQuest(wolfWhistle)) {
        when(player.attr[TAKEN_FROM_STIKKLEBRIX]) {
            false, null -> player.queue {
                this.chatPlayer("This must be Stikklebrix.", facialExpression = FacialExpression.SAD)
                this.chatPlayer(
                    "The poor fool didn't stand a chance against the wolves.",
                    facialExpression = FacialExpression.SAD
                )
                this.chatPlayer(
                    "Let's see what he has in his pack: three unfinished",
                    "strength potions, two burnt lobsters, a guam leaf with a",
                    "footprint on it...",
                    facialExpression = FacialExpression.CALM
                )
                this.chatPlayer("and some wolf bones!", facialExpression = FacialExpression.CALM)
                player.attr[TAKEN_FROM_STIKKLEBRIX] = true
                player.inventory.add(Items.WOLF_BONE, 2)
                this.chatPlayer(
                    "It looks he got enough bones for Pikkupstix and",
                    "then died. How tragic!",
                    facialExpression = FacialExpression.SAD
                )
                this.chatPlayer("Well, his sacrifice will not be in vain.", facialExpression = FacialExpression.CALM)
            }

            true -> player.queue {
                this.chatPlayer( //TODO: Find the actual 2011 dialogue for this...
                    "I've already searched Stikklebrix. I",
                    "should get back to Pikkupstix."
                )
            }
        }
    }
}