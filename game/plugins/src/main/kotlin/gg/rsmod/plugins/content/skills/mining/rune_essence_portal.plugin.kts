package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.game.model.attr.ESSENCE_MINE_INTERACTED_WITH
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.PORTAL_7352, option = "enter") {

    val obj = player.getInteractingGameObj() ?: return@on_obj_option
    player.queue {
        val projectile = player.createProjectile(obj.tile, player.tile, 109, ProjectileType.MAGIC)
        player.world.spawn(projectile)
        player.filterableMessage("You step through the portal...")
        wait(MagicCombatStrategy.getHitDelay(obj.tile, player.tile) + 1)
        player.moveTo(getLocation(player))
        wait(1)
        player.playSound(Sfx.CURSE_HIT)
        player.graphic(110)
    }
}

fun getLocation(player: Player): Tile {
    return when (player.attr[ESSENCE_MINE_INTERACTED_WITH]) {
        Npcs.AUBURY -> Tile(3253, 3401, 0)
        Npcs.SEDRIDOR -> Tile(3105, 9571, 0)
        else -> Tile(3253, 3401, 0)
    }
}
