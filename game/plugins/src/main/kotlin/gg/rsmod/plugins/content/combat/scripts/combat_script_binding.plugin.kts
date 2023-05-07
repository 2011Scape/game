package gg.rsmod.plugins.content.combat.scripts

import gg.rsmod.plugins.content.combat.scripts.impl.CockatriceCombatScript
import gg.rsmod.plugins.content.combat.scripts.impl.DragonCombatScript
import gg.rsmod.plugins.content.combat.scripts.impl.HighwaymanCombatScript
import gg.rsmod.plugins.content.combat.scripts.impl.ImpCombatScript
import gg.rsmod.plugins.content.combat.scripts.impl.CanifisCitizensCombatScript
/**
 * We can use this file to bind the combat scripts for the npcs.
 * Keeps them all in one place.
 * @author Kevin Senez <ksenez94@gmail.com>
 */

/**
 * Sets the [on_npc_combat] for Regular Dragons
 */
on_npc_combat(*DragonCombatScript.ids) {
    npc.queue {
        DragonCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Imps
 */
on_npc_combat(*ImpCombatScript.ids) {
    npc.queue {
        ImpCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Highwaymen
 */
on_npc_combat(*HighwaymanCombatScript.ids) {
    npc.queue {
        HighwaymanCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Cockatrices
 */
on_npc_combat(*CockatriceCombatScript.ids) {
    npc.queue {
        CockatriceCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Canifis Citizens
 */
on_npc_combat(*CanifisCitizensCombatScript.ids) {
    npc.queue {
        CanifisCitizensCombatScript.handleSpecialCombat(this)
    }
}