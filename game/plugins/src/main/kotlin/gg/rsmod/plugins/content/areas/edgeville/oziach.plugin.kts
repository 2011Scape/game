package gg.rsmod.plugins.content.areas.edgeville

/**
 * @author Alycia <https://github.com/alycii>
 */

on_npc_option(npc = Npcs.OZIACH, option = "talk-to") {
    player.queue {
        chatNpc("Aye, 'tis a fair day my friend.")
        when(options("I'm not your friend.", "Yes, it's a very nice day.")) {
            FIRST_OPTION -> {
                chatPlayer("I'm not your friend.")
                chatNpc("I'm surprised if you're anyone's friend with those kind", "of manners.")
            }
            SECOND_OPTION -> {
                chatPlayer("Yes, it's a very nice day.")
                chatNpc("Aye, may the gods walk by yer side. Now leave me", "alone.")
            }
        }
    }
}

on_npc_option(npc = Npcs.OZIACH, option = "trade") {
    player.queue {
        chatNpc("I ain't got nothing to sell ye, adventurer. Leave me be!")
    }
}