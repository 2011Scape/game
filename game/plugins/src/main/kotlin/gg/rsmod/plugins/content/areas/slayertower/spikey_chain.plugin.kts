package gg.rsmod.plugins.content.areas.slayertower

on_obj_option(obj = Objs.SPIKEY_CHAIN, option = "climb-up") {
    when (player.tile.height) {
        1 -> { // Second Floor
            if (player.skills.getCurrentLevel(Skills.AGILITY) > 70) {
                player.handleLadder(height = 2)
            } else {
                player.message("This spikey chain requires 71 agility to climb.")
            }
        }
        else -> // First Floor
            if (player.skills.getCurrentLevel(Skills.AGILITY) > 60) {
                player.handleLadder(height = 1)
            } else {
                player.message("This spikey chain requires 61 agility to climb.")
            }
    }
}

on_obj_option(obj = Objs.SPIKEY_CHAIN_9320, option = "climb-down") {
    when (player.tile.height) {
        2 -> { // Second Floor
            if (player.skills.getCurrentLevel(Skills.AGILITY) > 70) {
                player.handleLadder(height = 1)
            } else {
                player.message("This spikey chain requires 71 agility to climb.")
            }
        }
        else -> // First Floor
            if (player.skills.getCurrentLevel(Skills.AGILITY) > 60) {
                player.handleLadder(height = 0)
            } else {
                player.message("This spikey chain requires 61 agility to climb.")
            }
    }
}
