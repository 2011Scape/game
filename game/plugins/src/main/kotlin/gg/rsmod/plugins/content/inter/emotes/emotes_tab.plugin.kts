package gg.rsmod.plugins.content.inter.emotes

for(emote in Emote.values) {
    on_button(interfaceId = EmotesTab.COMPONENT_ID, component = emote.component) {
        EmotesTab.performEmote(player, emote)
    }
}