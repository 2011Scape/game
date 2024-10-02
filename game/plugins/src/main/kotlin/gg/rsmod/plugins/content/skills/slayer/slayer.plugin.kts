package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.game.model.attr.KILLER_ATTR

/**
 * @author Alycia <https://github.com/alycii>
 */

set_slayer_logic {
    // Cast the pawn to an Npc and check if it has the KILLER_ATTR attribute
    val npc = pawn as Npc
    if (pawn.attr.has(KILLER_ATTR)) {
        // Get the most damaging entity from the npc's damageMap and cast it to a Player
        val player = npc.damageMap.getMostDamage() as? Player
        // If the entity is a Player and has the same slayer assignment as the NPC, decrese the amount
        if (player != null && player.getSlayerAssignment() != null) {
            if (player.getSlayerAssignment() == npc.combatDef.slayerAssignment) {
                player.handleDecrease(npc)
            }
        }
    }
}
