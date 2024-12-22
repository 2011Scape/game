package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.SoundSongEndMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.LAST_SONG_END
import gg.rsmod.game.model.entity.Client

class SoundSongEndHandler : MessageHandler<SoundSongEndMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: SoundSongEndMessage,
    ) {
        client.attr[LAST_SONG_END] = message.songId
        world.plugins.executeSoundSongEnd(client)
    }
}
