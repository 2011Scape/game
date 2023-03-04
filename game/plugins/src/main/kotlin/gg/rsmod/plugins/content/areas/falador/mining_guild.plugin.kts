package gg.rsmod.plugins.content.areas.falador

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.LADDER_2113, option ="climb-down") {
    handleMiningGuild(player, climbUp = false)
}

on_obj_option(obj = Objs.LADDER_6226, option ="climb-up") {
    handleMiningGuild(player, climbUp = true)
}

fun handleMiningGuild(player: Player, climbUp: Boolean) {
    if(player.getSkills().getCurrentLevel(Skills.MINING) < 60 && !climbUp) {
        player.queue {
            chatNpc("Sorry, but you're not experienced enough to go in there.", npc = Npcs.DWARF_3295)
            messageBox("You need a Mining level of 60 to access the Mining Guild.")
        }
        return
    }
    player.queue {
        player.animate(828)
        wait(2)
        val zOffset = when(climbUp) {
            true -> -6400
            false -> 6400
        }
        player.moveTo(player.tile.x, player.tile.z + zOffset)
    }
}