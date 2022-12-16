package gg.rsmod.plugins.content.inter.emotes

on_button(interfaceId = EmotesTab.COMPONENT_ID, component = 1) p@ {
    val slot = player.getInteractingSlot()
    val emote = Emote.values.firstOrNull { e -> e.slot == slot } ?: return@p
    EmotesTab.performEmote(player, emote)
}