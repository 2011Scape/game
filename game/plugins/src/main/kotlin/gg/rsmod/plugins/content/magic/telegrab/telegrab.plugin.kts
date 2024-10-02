package gg.rsmod.plugins.content.magic.telegrab

import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.magic.MagicSpells
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */

val ANIMATION = 711
val START_GFX = Graphic(id = 142, height = 80, delay = 15)
val PROJECTILE_GFX = 143
val FLOOR_GFX = 144

on_spell_on_ground_item(fromInterface = 192, fromComponent = 44) {
    val groundItem = player.getInteractingGroundItem()
    val spellData = MagicSpells.getMetadata(SpellbookData.TELEKINETIC_GRAB.uniqueId) ?: return@on_spell_on_ground_item

    if (!player.world.plugins.canPickupGroundItem(player, groundItem.item)) {
        return@on_spell_on_ground_item
    }

    if (player.inventory.requiresFreeSlotToAdd(groundItem.item) && !player.inventory.hasSpace) {
        player.message("You don't have enough inventory space to hold that item.")
        return@on_spell_on_ground_item
    }

    if (!groundItem.isSpawned(world)) {
        player.message("Too late!")
        return@on_spell_on_ground_item
    }

    // face the ground item tile
    player.faceTile(groundItem.tile)

    if (MagicSpells.canCast(player, spellData.lvl, spellData.runes)) {
        player.lockingQueue {

            MagicSpells.removeRunes(player, spellData.runes, spellData.sprite)
            // animate
            player.animate(ANIMATION)
            player.graphic(START_GFX)

            // spawn projectile
            val projectile =
                player.createProjectile(
                    srcTile = player.tile,
                    target = groundItem.tile,
                    gfx = PROJECTILE_GFX,
                    type = ProjectileType.TELEKINETIC_GRAB,
                )
            player.world.spawn(projectile = projectile)

            // wait for the projectile length
            wait(MagicCombatStrategy.getHitDelay(groundItem.tile, player.tile) + 1)

            // spawn the pickup graphics on the item
            player.world.spawn(TileGraphic(groundItem.tile, id = FLOOR_GFX, height = 0))
            wait(1)

            player.addXp(Skills.MAGIC, 43.0)
            // if the item has been removed from the world before the projectile arrived
            if (!groundItem.isSpawned(world)) {
                player.message("Too late!")
                player.unlock()
                return@lockingQueue
            }

            // play the pickup sound
            player.playSound(Sfx.TELEGRAB_HIT)

            // finally remove the ground item
            player.world.remove(groundItem)
            // add the item
            player.inventory.add(groundItem.item, groundItem.amount)
        }
    }
}
