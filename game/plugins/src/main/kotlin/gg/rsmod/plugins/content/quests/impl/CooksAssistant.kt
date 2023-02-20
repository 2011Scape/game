package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.quest.Quest
import gg.rsmod.game.model.quest.QuestStage
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.buildQuestFinish
import gg.rsmod.plugins.content.quests.buildQuestOverview

/**
 * @author Alycia <https://github.com/alycii>
 */

object CooksAssistantQuest : Quest(
    name = "Cook's Assistant",
    startPoint = "Speak to the cook in the kitchen of Lumbridge Castle.",
    requirements = emptyList(),
    requiredItems = "A pot, a bucket.",
    combat = "No specific combat, but watch out for local goblins!",
    rewards = "1 Quest Point, 300 Cooking XP, 500 coins, 20 sardines, and access to the cook's range.",
    pointReward = 1,
    varbit = 29,
    spriteId = 2379,
    slot = 1,
    stages = listOf(
        QuestStage(
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
            ),
            value = 1
        ),
        QuestStage(
            objectives = listOf(
                "It was the Duke of Lumbridge's birthday, but his cook had",
                "forgotten to buy the ingredients he needed to make him a",
                "cake. I brought the cook an egg, some flour, and some milk",
                "and the cook made a delicious looking cake with them.",
                "As a reward, he now lets me use his high quality range",
                "which lets me burn things less whenever I wish to cook",
                "there.",
                "<col=FF0000>QUEST COMPLETE!"
            ),
            value = 2
        )
    ),
) {

    init {
        addQuest(this)
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.inventory.remove(Items.SUPER_LARGE_EGG)
        player.inventory.remove(Items.TOPQUALITY_MILK)
        player.inventory.remove(Items.EXTRA_FINE_FLOUR)
        player.inventory.add(Items.COINS_995, 500)
        player.inventory.add(Items.SARDINE_NOTED, 20)
        player.addXp(Skills.COOKING, 300.0)
        player.setVarp(101, pointReward)
        player.buildQuestFinish(this, item = Items.CAKE, rewards = arrayOf("1 Quest Point", "300 Cooking XP", "500 Coins", "20 Sardines", "Access to the Cook's Range"))
    }
}