package gg.rsmod.plugins.content.mechanics.lightsource

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class LightSource(val raw: Int, val product: Int, val levelRequired: Int, val enclosed: Boolean, val interfaceId: Int) {
    CANDLE(raw = Items.CANDLE, product = Items.LIT_CANDLE, levelRequired = 1, enclosed = false, interfaceId = 98),
    BLACK_CANDLE(raw = Items.BLACK_CANDLE, product = Items.LIT_BLACK_CANDLE, levelRequired = 1, enclosed = false, interfaceId = 98);

    companion object {
        fun getActiveLightSource(player: Player): LightSource? {
            for (source in values()) {
                if (player.inventory.contains(source.product)) {
                    return source
                }
            }
            return null
        }
    }
}