package gg.rsmod.plugins.content.objs

private val PLAQUES = setOf(Objs.CLAN_CUP_PLAQUE, Objs.CLAN_CUP_PLAQUE_54018, Objs.CLAN_CUP_PLAQUE_54019, Objs.CLAN_CUP_PLAQUE_54020, Objs.CLAN_CUP_PLAQUE_55301)

PLAQUES.forEach { plaque ->

    on_obj_option(obj = plaque, option = "read") {
        player.openInterface(interfaceId = 205, dest = InterfaceDestination.MAIN_SCREEN)
        player.setComponentText(interfaceId = 205, component = 65, text = "Current Winners")
        player.setComponentText(interfaceId = 205, component = 64, text = "The victorious winners of the 2011 Jagex Clan Cup<br><br>Combat - RuneScape Dinasty<br>Skilling - Divination<br>Combined - Family Unity Network")
    }

}

on_button(interfaceId = 205, component = 61) {
    player.setComponentText(interfaceId = 205, component = 65, text = "Current Winners")
    player.setComponentText(interfaceId = 205, component = 64, text = "The victorious winners of the 2011 Jagex Clan Cup<br><br>Combat - RuneScape Dinasty<br>Skilling - Divination<br>Combined - Family Unity Network")
}

on_button(interfaceId = 205, component = 57) {
    player.setComponentText(interfaceId = 205, component = 65, text = "Combat Winners")
    player.setComponentText(interfaceId = 205, component = 64, text = "2011 - RuneScape Dinasty<br>2010 - RuneScape Dinasty<br>2009 - The Titans")
}

on_button(interfaceId = 205, component = 53) {
    player.setComponentText(interfaceId = 205, component = 65, text = "Skilling Winners")
    player.setComponentText(interfaceId = 205, component = 64, text = "2011 - Divination<br>2010 - Divination<br>2009 - Divination")
}

on_button(interfaceId = 205, component = 49) {
    player.setComponentText(interfaceId = 205, component = 65, text = "Combined Winners")
    player.setComponentText(interfaceId = 205, component = 64, text = "2011 - Family Unity Network<br>2010 - BasedIn2Minutes<br>2009 - Wicked Fury")
}