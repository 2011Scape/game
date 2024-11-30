package gg.rsmod.plugins.content.objs

private val PLAQUES =
    setOf(
        Objs.SIGNPOST,
        Objs.SIGNPOST_10090,
        Objs.SIGNPOST_13873,
        Objs.SIGNPOST_15522,
        Objs.SIGNPOST_10090,
        Objs.SIGNPOST_2367,
        Objs.SIGNPOST_2368,
        Objs.SIGNPOST_2369,
        Objs.SIGNPOST_2370,
        Objs.SIGNPOST_2371,
        Objs.SIGNPOST_25397,
        Objs.SIGNPOST_31297,
        Objs.SIGNPOST_31298,
        Objs.SIGNPOST_31299,
        Objs.SIGNPOST_31300,
        Objs.SIGNPOST_31301,
        Objs.SIGNPOST_4132,
        Objs.SIGNPOST_4134,
        Objs.SIGNPOST_4135,
    )

PLAQUES.forEach { plaque ->
    on_obj_option(obj = plaque, option = "read") {
        player.openInterface(interfaceId = 943, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentText(interfaceId = 943, component = 1, text = "Report to Duke Horacio for a quest")
        player.setComponentText(interfaceId = 943, component = 2, text = "involving a mysterious talisman.")
        player.setComponentText(interfaceId = 943, component = 3, text = "Father Aereck is looking for anyone")
        player.setComponentText(interfaceId = 943, component = 4, text = "to help with a ghost problem!")
        player.setComponentText(interfaceId = 943, component = 5, text = "The Cook is preparing a huge meal,")
        player.setComponentText(interfaceId = 943, component = 6, text = "he might need some assistance...")
        player.setComponentText(interfaceId = 943, component = 7, text = "Last Updated")
        player.setComponentText(interfaceId = 943, component = 8, text = "2 - 20 - 23")
    }
}

on_obj_option(Objs.SIGNPOST_2754, "read") {
    // Western Lumbridge sign
    openSignPost(
        player,
        "Head north towards Fred's farm and the Windmill.",
        "Cross the bridge and head east to Al Kharid or north to Varrock.",
        "South to the swamps of Lumbridge.",
        "West to the Lumbridge Castle and Draynor Village. Beware the goblins!",
    )
}

on_obj_option(Objs.SIGNPOST_18493, "read") {
    // Eastern Lumbridge sign
    if (player.getInteractingGameObj().tile == Tile(3261, 3230)) {
        openSignPost(
            player,
            "North to farms and Varrock.",
            "East to Al Kharid - toll gate; bring some money.",
            "The River Lum lies to the south.",
            "West to Lumbridge.",
        )
    }

    // Draynor crossroads sign
    if (player.getInteractingGameObj().tile == Tile(3107, 3296)) {
        openSignPost(
            player,
            "North to Draynor Manor.",
            "East to Lumbridge",
            "South to Draynor Village and the Wizards' Tower.",
            "West to Post Sarim, Falador and Rimmington.",
        )
    }
}

on_obj_option(Objs.SIGNPOST_19153, "read") {
    // South Falador sign
    openSignPost(
        player,
        "North to the glorious White Knights city of Falador.",
        "Follow the path east to Port Sarim and Draynor Village.",
        "Follow the path south to Rimmington.",
        "Follow the path west to the Crafting Guild.",
    )
}

on_obj_option(Objs.SIGNPOST_24263, "read") {
    // South-east of Varrock sign
    if (player.getInteractingGameObj().tile == Tile(3283, 3333)) {
        openSignPost(
            player,
            "North to Varrock mine and Varrock east gate.",
            "Follow the path east to the Dig Site (members only).",
            "South to large Mining area and Al Kharid.",
            "West to Champions' Guild and Varrock south gate.",
        )
    }

    // South-west of Varrock sign
    if (player.getInteractingGameObj().tile == Tile(3268, 3332)) {
        openSignPost(
            player,
            "Sheep lay this way.",
            "East to Al Kharid mine and follow the path north to Varrock east gate.",
            "South through farms to Al Kharid and Lumbridge.",
            "West to Champions' Guild and Varrock south gate.",
        )
    }
}

on_obj_option(Objs.SIGNPOST_31296, "read") {
    // Sign outside Shantay Pass
    openSignPost(
        player,
        "North to Al Kharid.",
        "East and across the river to the Ruins of Uzer.",
        "South to the Desert Mining Camp and Pollnivneach.",
        "West to the Kalphite Lair.",
    )
}

fun openSignPost(
    player: Player,
    north: String,
    east: String,
    south: String,
    west: String,
) {
    val signPostInterfaceId = 135
    player.openInterface(signPostInterfaceId, dest = InterfaceDestination.MAIN_SCREEN_FULL)
    player.setComponentText(signPostInterfaceId, component = 3, north)
    player.setComponentText(signPostInterfaceId, component = 8, east)
    player.setComponentText(signPostInterfaceId, component = 9, south)
    player.setComponentText(signPostInterfaceId, component = 12, west)
}
