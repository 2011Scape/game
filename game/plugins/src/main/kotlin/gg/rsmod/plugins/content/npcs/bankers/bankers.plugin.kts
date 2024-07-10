package gg.rsmod.plugins.content.npcs.bankers

import gg.rsmod.plugins.content.inter.bank.openBank

val bankers =
    listOf(
        Npcs.BANKER_CLASSIC_MALE_PURPLE_44, // Various Regions Male Bankers Classic Models
        Npcs.BANKER_CLASSIC_FEMALE_PURPLE_45, // Various Regions Female Bankers Classic Models
        Npcs.BANKER_CLASSIC_MALE_GREY_494, // Various Regions Male Bankers Classic Models
        Npcs.BANKER_CLASSIC_FEMALE_GREY_495, // Various Regions Female Bankers Classic Models
        Npcs.BANKER_CLASSIC_FEMALE_GREY_497, // Various Regions Female Bankers Classic Models
        Npcs.BANKER_CLASSIC_FEMALE_PURPLE_2759, // Varrock East Female Bankers Classic Models
        Npcs.BANKER_CLASSIC_MALE_GREY_6200, // Falador Bankers All Share the Same ID Classic Models
        Npcs.BANKER_LUMBRIDGE_FEMALE_4907, // Lumbridge Castle Banker, Updated Model
        Npcs.BANKER_LATEST_MALE_HANDSBEHIND_3416, // Grand Exchange Updated Models
        Npcs.BANKER_LATEST_MALE_HANDSBEHIND_3418, // Grand Exchange Updated Models
        Npcs.BANKER_LATEST_FEMALE_HANDSBEHIND_3293, // Grand Exchange Updated Models
        Npcs.BANKER_LATEST_FEMALE_BLOND_HANDSBEHIND_2718, // Grand Exchange Updated Models
        Npcs.BANKER_LATEST_FEMALE_HANDSFRONT_4458, // Female Bankers Updated Models
        Npcs.BANKER_LATEST_FEMALE_HANDSFRONT_4459, // Female Bankers Updated Models
        Npcs.BANKER_LATEST_MALE_HANDSFRONT_4456, // Male Bankers Updated Models
        Npcs.BANKER_LATEST_MALE_HANDSFRONT_4457, // Male Bankers Updated Models
        Npcs.BANKER_FAIRY_BLOND_498, // Zanaris City
        Npcs.BANKER_FAIRY_BRUNETTE_909, // Zanaris City
        Npcs.BANKER_ELF_MALE_2354, // Lletya Elf Village
        Npcs.BANKER_ELF_FEMALE_2355, // Lletya Elf Village
        Npcs.BANKER_SPECIAL_FEMALE_BLOND_2570,
        Npcs.BANKER_SPECIAL_FEMALE_BRUNETTE_2569,
        Npcs.BANKER_SPECIAL_MALE_2568,
        Npcs.BANKER_MOBILISING_ARMIES_8948, // Mobilising Armies
        Npcs.BANKER_SPECIAL_1360, // Mage Arena Banker?
        Npcs.BANKER_NARDAH_FEMALE_5260, // Nardah City Bankers
        Npcs.BANKER_NARDAH_MALE_5258, // Nardah City Bankers
        Npcs.BANKER_PIRATE_MALE_HAT_3199, // Mos Le'Harmless
        Npcs.BANKER_PIRATE_MALE_EYEPATCH_3198, // Mos Le'Harmless
        Npcs.BANKER_CAVE_GOBLIN_MALE_YOUNG_5776, // Dorgesh-Kaan
        Npcs.BANKER_CAVE_GOBLIN_MALE_OLD_5777, // Dorgesh-Kaan
        Npcs.BANKER_OGRESS_7049, // Oo'glog City
        Npcs.BANKER_OGRESS_7050, // Oo'glog City
        Npcs.BANKER_PORTABLE_7605,
        Npcs.BANKER_CLASSIC_MALE_GREY_1036,
    )

bankers.forEach {
    on_npc_option(it, option = "Bank", lineOfSightDistance = 2) {
        player.openBank()
    }
    on_npc_option(it, option = "talk-to", lineOfSightDistance = 2) {
        player.queue { chat(this) }
    }
}

/**
 * Banker Dialogue, cross referenced with Runescape 3 and Runescape Wiki dating back to 2016.
 */
suspend fun chat(it: QueueTask) {
    it.chatNpc("Good day. How may I help you?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (
        it.options(
            "I'd like to access my bank account, please.",
            "I'd like to check my PIN settings.",
            "I'd like to see my collection box.",
            "What is this place?",
        )
    ) {
        1 -> {
            it.chatPlayer(
                "I'd like to access my bank account, please.",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            it.player.openBank()
        }
        2 -> {
            // TODO : Ability to set Pins isn't implemented yet
            it.chatNpc("Sorry, it is not implemented yet.", facialExpression = FacialExpression.SAD_2)
        }
        3 -> {
            // TODO : Grand Exchange isn't implemented yet
            it.chatNpc("Sorry, it is not implemented yet.", facialExpression = FacialExpression.SAD_2)
        }
        4 -> {
            it.chatPlayer("What is this place?", facialExpression = FacialExpression.UNCERTAIN)
            it.chatNpc(
                "This is a branch of the Bank of Gielinor. We have branches",
                "in many towns.",
                facialExpression = FacialExpression.CHEERFUL,
            )

            when (
                it.options(
                    "And what do you do?",
                    "Didn't you used to be called the Bank of Varrock?",
                )
            ) {
                1 -> {
                    it.chatPlayer("And what do you do?", facialExpression = FacialExpression.DISREGARD)
                    it.chatNpc(
                        "We will look after your items and money for you. Leave",
                        "your valuables with us if you want to keep them safe.",
                        facialExpression = FacialExpression.CHEERFUL,
                    )
                }
                2 -> {
                    it.chatPlayer(
                        "Didn't you used to be called the Bank of Varrock?",
                        facialExpression = FacialExpression.DISREGARD,
                    )
                    it.chatNpc(
                        "Yes we did, but people kept on coming into our branches",
                        "outside of Varrock and telling us that our signs were",
                        "wrong. They acted as if we didn't know what town we",
                        "were in or something.",
                        facialExpression = FacialExpression.TALKING,
                    )
                }
            }
        }
    }
}
