package gg.rsmod.plugins.content.combat.scripts

import gg.rsmod.plugins.content.combat.scripts.impl.DragonCombatScript

/**
 * Sets the [on_npc_combat] for Regular Dragons
 */
on_npc_combat(*DragonCombatScript.ids) {
    npc.queue {
        DragonCombatScript.handleSpecialCombat(this)
    }
}