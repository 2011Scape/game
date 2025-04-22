package gg.rsmod.plugins.content.areas.ardougne

/**
 *  Inside Gangplank on Captain Barnaby's ship, Ardougne
 */
on_obj_option(Objs.GANGPLANK_2086, "Cross") {
    player.queue {
        player.teleportTo(2683, 3271, 0)
        wait(1)
    }
}

/**
 *  Outside Gangplank on Captain Barnaby's ship, Ardougne
 */
on_obj_option(Objs.GANGPLANK_2085, "Cross") {
    player.queue {
        player.teleportTo(2683, 3268, 1)
        player.message("You must speak to Captain Barnaby before it will set sail.")
        wait(1)
    }
}

/**
 *  Top Ladder on Captain Barnaby's ship, Ardougne
 */
on_obj_option(Objs.SHIPS_LADDER_9745, "Climb-down") {
    player.queue {
        player.message("I don't think Captain Barnaby wants me going down there.")
        wait(1)
    }
}

/**
 *  Inside Gangplank on Captain Barnaby's ship, Brimhaven
 */
on_obj_option(Objs.GANGPLANK_2088, "Cross") {
    player.queue {
        player.teleportTo(2772, 3234, 0)
        wait(1)
    }
}

/**
 *  Outside Gangplank on Captain Barnaby's ship, Brimhaven
 */
on_obj_option(Objs.GANGPLANK_2087, "Cross") {
    player.queue {
        player.teleportTo(2775, 3234, 1)
        player.message("You must speak to the Customs officer before it will set sail.")
        wait(1)
    }
}
