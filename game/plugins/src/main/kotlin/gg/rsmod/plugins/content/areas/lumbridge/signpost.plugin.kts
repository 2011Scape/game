package gg.rsmod.plugins.content.objs

private val PLAQUES = setOf(Objs.SIGNPOST, Objs.SIGNPOST_10090, Objs.SIGNPOST_13873, Objs.SIGNPOST_15522, Objs.SIGNPOST_18493, Objs.SIGNPOST_19153, Objs.SIGNPOST_10090, Objs.SIGNPOST_2367, Objs.SIGNPOST_2368, Objs.SIGNPOST_2369, Objs.SIGNPOST_2370, Objs.SIGNPOST_2371, Objs.SIGNPOST_24263, Objs.SIGNPOST_25397, Objs.SIGNPOST_31296, Objs.SIGNPOST_31297, Objs.SIGNPOST_31298, Objs.SIGNPOST_31299, Objs.SIGNPOST_31300, Objs.SIGNPOST_31301, Objs.SIGNPOST_4132, Objs.SIGNPOST_4134, Objs.SIGNPOST_4135)

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

