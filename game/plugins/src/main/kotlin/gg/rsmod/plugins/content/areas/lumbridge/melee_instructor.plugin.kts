package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.MELEE_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Greetings adventurer, I am the Melee combat tutor. Is", "there anything I can do for you?")
        mainChat(this)
    }
}
suspend fun buySkillcape(it: QueueTask) {
    it.chatPlayer("May I buy a Skillcape of Defence, please?")
    it.chatNpc("You wish to join the elite defenders of this world? ", "I'm afraid such things do not come cheaply - " ,"in fact they cost 99000 coins, to be precise!")
    when (it.options("99000 coins? That's much too expensive.", "I think I have the money right here, actually.")){
        1 -> {
            it.chatPlayer("99000 coins? That's much too expensive.")
            it.chatNpc("Not at all; there are many other adventurers who ", "would love the opportunity to purchase such a ", "prestigious item! You can find me here if you change ", "your mind.")
        }
        2 -> {
            it.chatPlayer("I think I have the money right here, actually.")
            if(it.player.inventory.freeSlotCount < 2) {
                it.chatNpc("You don't have enough free space in your inventory ", "for me to sell you a Skillcape of defence.")
                it.chatNpc("Come back to me when you've cleared up some space.")
            }
            if(it.player.hasItem(Items.DEFENCE_HOOD) || it.player.hasItem(Items.DEFENCE_CAPE)) {
                it.chatNpc("You already have a Skillcape of defence.")
            }
            else{
                if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000){
                    it.player.inventory.remove(item = Item(Items.COINS_995, amount = 99000), assureFullRemoval = true)
                    it.doubleItemMessageBox("Harlan gives you a Defence skillcape and hood.", item1 = Items.DEFENCE_CAPE, item2 = Items.DEFENCE_HOOD)
                    //TODO check for previous skillcape earned, give trimmed cape if earned, regular cape if not.
                    it.player.inventory.add(Items.DEFENCE_CAPE)
                    it.player.inventory.add(Items.DEFENCE_HOOD)
                    it.chatNpc("Excellent! Wear that cape with pride my friend.")
                }
                else{
                    it.chatPlayer("But, unfortunately, I was mistaken.")
                    it.chatNpc("Well, come back and see me when you do.")
                }
            }
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("Tell me about melee combat.", "Tell me about different weapon types I can use.", "Tell me about skillcapes.", "I'd like a training sword and shield.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("Tell me about melee combat.")
            it.chatNpc("Well adventurer, the first thing you will need is a", "sword and shield appropriate for your level.")
            it.player.runClientScript(115, 5)
            it.chatNpc("Make sure to equip your sword and shield. Click on", "them in your inventory, they will disappear from your", "inventory and move to your worn items. You can see", "your worn items in the worn items tab here.")
            it.player.runClientScript(115, 0)
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
        2 -> {
            it.chatPlayer("Tell me about different weapon types I can use.")
            it.chatNpc("Well let me see now...There are stabbing type weapons ", "such as daggers, then you have swords which are ", "slashing, maces that have great crushing abilities, ", "battle axes which are powerful.")
            it.chatNpc("It depends a lot on how you want to fight. Experiment ", "and find out what is best for you. Never be scared to ", "try out a new weapon; you never know, you might like it!")
            it.chatNpc("While I tried all of them for a while, ", "I settled on this rather good sword.")
            it.chatNpc("You might also find that different weapon types ", "are more accurate against different monsters.")
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        3 -> {
            it.chatPlayer("Tell me about skillcapes.")
            if (it.player.getSkills().getCurrentLevel(Skills.DEFENCE) >= 99){
                it.chatNpc("Ah, I can see you're already a master in the fine ", "art of Defence. Perhaps you have come to me to ", "purchase a Skillcape of Defence, and thus join the ", "elite few who have mastered this exacting skill?")
                when (it.options("May I buy a Skillcape of Defence, please?", "Can I ask about something else?")){
                    1 -> {
                        it.terminateAction
                        it.player.queue { buySkillcape(this) }
                    }
                    2 -> {
                        it.chatPlayer("Can I ask about something else?")
                        it.terminateAction
                        it.player.queue { mainChat(this) }
                    }
                }
            }else{
                it.chatNpc("Of course. Skillcapes are a symbol of achievement. ", "Only people who have mastered a skill and reached ", "level 99 can get their hands on them ", "and gain the benefits they carry.")
                it.chatNpc("Is there anything else you would like to know?")
                it.terminateAction
                it.player.queue { mainChat(this) }
            }
        }
        4 -> {
            it.chatPlayer("I'd like a training sword and shield.")
            if(it.player.hasItem(Items.TRAINING_SWORD) && it.player.hasItem(Items.TRAINING_SHIELD)) {
                it.chatNpc("You already have a training sword and shield. Save", "some for the other adventurers.")
            } else {
                if(it.player.inventory.freeSlotCount >= 2) {
                    it.doubleItemMessageBox("Harlan gives you a Training sword and shield.", item1 = Items.TRAINING_SWORD, item2 = Items.TRAINING_SHIELD)
                    it.player.inventory.add(Items.TRAINING_SWORD)
                    it.player.inventory.add(Items.TRAINING_SHIELD)
                    it.chatNpc("There you go, use it well.")
                } else{
                    it.chatNpc("You don't have enough space for me to give you a", "training sword, nor a shield.")
                }
            }

        }
        5 -> {
            it.chatPlayer("Goodbye.")
        }

    }
}