package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.run.RunEnergy


on_npc_option(npc = Npcs.MELEE_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Greetings adventurer, I am the Melee combat tutor. Is", "there anything I can do for you?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("Tell me about melee combat.", "Tell me about different weapon types I can use.", "Tell me about skillcapes.", "I'd like a training sword and shield.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("Tell me about melee combat.")
            it.chatNpc("Well adventurer, the first thing you will need is a", "sword and shield appropriate for your level.")
            it.chatNpc("Make sure to equip your sword and shield. Click on", "them in your inventory, they will disappear from your", "inventory and move to your worn items. You can see", "your worn items in the worn items tab here.")
            it.chatNpc("When you are wielding your sword you will then be", "able to see the correct options in the combat interface.")
            it.chatNpc("There are four different melee styles. Accurate,", "aggressive, defensive, and controlled. Not all weapons will", "have all four styles though.")
            it.chatPlayer("Interesting, what does each style do?")
            it.chatNpc("Well I am glad you asked. The accurate style will give", "you experience points in your Attack skill, you will also", "find you will deal damage more frequently as a result", "of being well, more accurate.")
            it.chatNpc("Next we have the aggressive style. This style will give", "you experience in your Strength skill. When", "using this style you will notice that your attacks will hit", "a little harder.")
            it.chatNpc("Now for the defensive style, this style will give you", "experience points in your Defensive skill. When using", "this style you will notice that you get hit less often.")
            it.chatNpc("Finally, we have the controlled style. This style will give", "you the same amount of experience as the other styles", "would but shared accross all three of the combat skills.")
            it.chatNpc("If you were using the training sword for example, there", "are four different attack types. Stab, lunge, slash and", "block.")
            it.chatNpc("Each type uses one of the attack styles. Stab uses", "accurate , lunge and slash use aggressive and block uses", "defensive.")
            it.chatNpc("To find out which style an attack type uses, hover your", "mouse cursor over the style button.")
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        2 -> {}
        3 -> {}
        4 -> {
            it.chatPlayer("I'd like a training sword and shield.")
            if(it.player.hasItem(Items.TRAINING_SWORD) || it.player.hasItem(Items.TRAINING_SHIELD)) {
                it.chatNpc("You already have a training sword and shield. Save", "some for the other adventurers.")
                it.chatNpc("Is there anything else I can help you with?")
                it.terminateAction
                it.player.queue { mainChat(this) }
            } else {
                if(it.player.inventory.capacity == 27) {
                    it.itemMessageBox("Harlan gives you a Training sword", item = Items.TRAINING_SWORD)
                    it.player.inventory.add(Items.TRAINING_SWORD)
                    it.chatNpc("There you go, use it well.")
                    it.chatNpc("Is there anything else I can help you with?")
                    it.terminateAction
                    it.player.queue { mainChat(this) }
                    return
                }
                if(it.player.inventory.isFull) {
                    it.chatNpc("You don't have enough space for me to give you a", "training sword, nor a shield.")
                    it.chatNpc("Is there anything else I can help you with?")
                    it.terminateAction
                    it.player.queue { mainChat(this) }
                    return
                }
                it.doubleItemMessageBox("Harlan gives you a Training sword and shield.", item1 = Items.TRAINING_SWORD, item2 = Items.TRAINING_SHIELD)
                it.player.inventory.add(Items.TRAINING_SWORD)
                it.player.inventory.add(Items.TRAINING_SHIELD)
                it.chatNpc("There you go, use it well.")
                it.chatNpc("Is there anything else I can help you with?")
                it.terminateAction
                it.player.queue { mainChat(this) }
                return
            }
        }
        5 -> it.chatPlayer("Goodbye.")

    }
}