package gg.rsmod.plugins.content.magic.telegrab

import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy

/**
 * @author Alycia <https://github.com/alycii>
 */

val ANIMATION = 711
val START_GFX = Graphic(id = 142, height = 80, delay = 15)
val PROJECTILE_GFX = 143
val FLOOR_GFX = 144
val PICKUP_SOUND = 3008

on_spell_on_ground_item(fromInterface = 192, fromComponent = 44) {
    val groundItem = player.getInteractingGroundItem()

    if (!player.world.plugins.canPickupGroundItem(player, groundItem.item)) {
        return@on_spell_on_ground_item
    }

    if(!player.inventory.hasSpace) {
        player.message("You don't have enough inventory space to hold that item.")
        return@on_spell_on_ground_item
    }

    if(!groundItem.isSpawned(world)) {
        player.message("Too late!")
        return@on_spell_on_ground_item
    }

    player.queue {

        // face the ground item tile
        player.faceTile(groundItem.tile)

        // lock the player
        player.lock()

        // animate
        player.animate(ANIMATION)
        player.graphic(START_GFX)

        // spawn projectile
        val projectile = player.createProjectile(srcTile = player.tile, target = groundItem.tile, gfx = PROJECTILE_GFX, type = ProjectileType.TELEKINETIC_GRAB)
        player.world.spawn(projectile = projectile)

        // wait for the projectile length
        wait(MagicCombatStrategy.getHitDelay(groundItem.tile, player.tile) + 1)

        // spawn the pickup graphics on the item
        player.world.spawn(TileGraphic(groundItem.tile, id = FLOOR_GFX, height = 0))
        wait(1)

        // if the item has been removed from the world before the projectile arrived
        if(!groundItem.isSpawned(world)) {
            player.message("Too late!")
            player.unlock()
            return@queue
        }

        // finally remove the ground item
        player.world.remove(groundItem)
        // play the pickup sound
        player.playSound(PICKUP_SOUND)
        // add the item
        player.inventory.add(groundItem.item)
        // unlock the player
        player.unlock()
    }
}