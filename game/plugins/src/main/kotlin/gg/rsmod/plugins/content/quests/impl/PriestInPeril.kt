package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

object PriestInPeril : Quest(
    name = "Priest in Peril",
    startPoint = "Speak to King Roald in the Varrock Palace.",
    requirements = emptyList(),
    requiredItems = "50 rune essence or 25 pure essence (the essence must be un-noted), and a bucket",
    combat = "Temple guardian dog (level 30) You cannot use Magic.",
    rewards = "1 Quest Point, Access to Morytania, 1,406 Prayer XP, The Wolfbane Dagger",
    pointReward = 1,
    varbit = 562,
    spriteId = 4434,
    slot = 84,
    stages = 2
) {

    init {
        addQuest(this)
    }

    override fun getObjective(player: Player, stage: Int): QuestStage = when (stage) {
        1 -> QuestStage(
            objectives = listOf(
                "It's the <col=8A0808>Duke of Lumbridge's birthday</col> and I have to help",
                "his <col=8A0808>cook make him a <col=8A0808>birthday cake. To do this I need to",
                "bring the cook the following ingredients:",
                "",
                "",
                "I need to find a <col=8A0808>bucket of top-quality milk.",
                "I need to find a <col=8A0808>pot of extra fine flour.",
                "I need to find a <col=8A0808>super large egg.",
                "",
                "",
                "According to the <col=8A0808>cook, I can find the ingredients in the",
                "vicinity of <col=8A0808>Lumbridge. He has noted certain possible",
                "locations of the ingredients on my world map."
            )
        )

        2 -> QuestStage(
            objectives = listOf(
                "<str>It was the Duke of Lumbridge's birthday, but his cook had",
                "<str>forgotten to buy the ingredients he needed to make him a",
                "<str>cake. I brought the cook an egg, some flour, and some milk",
                "<str>and the cook made a delicious looking cake with them.",
                "<str>As a reward, he now lets me use his high quality range",
                "<str>which lets me burn things less whenever I wish to cook",
                "<str>there.",
                "<col=FF0000>QUEST COMPLETE!"
            )
        )

        else -> TODO("Not yet implemented")
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.inventory.remove(Items.SUPER_LARGE_EGG)
        player.inventory.remove(Items.TOPQUALITY_MILK)
        player.inventory.remove(Items.EXTRA_FINE_FLOUR)
        player.inventory.add(Items.COINS_995, 500)
        player.inventory.add(Items.SARDINE_NOTED, 20)
        player.addXp(Skills.COOKING, 300.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.CAKE,
            rewards = arrayOf(
                "1 Quest Point",
                "Access to Morytania",
                "1,406 Prayer XP",
                "The Wolfbane Dagger"
            )
        )
    }
}