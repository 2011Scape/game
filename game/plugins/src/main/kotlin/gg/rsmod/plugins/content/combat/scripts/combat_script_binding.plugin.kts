package gg.rsmod.plugins.content.combat.scripts

import AberrantSpectreCombatScript
import gg.rsmod.plugins.content.combat.scripts.impl.*

/**
 * We can use this file to bind the combat scripts for the npcs.
 * Keeps them all in one place.
 * @author Kevin Senez <ksenez94@gmail.com>
 */


/**
 * Sets the [on_npc_combat] for Abyssal demons
 */
on_npc_combat(*AbyssalDemonCombatScript.ids) {
    npc.queue {
        AbyssalDemonCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Aberrant Spectres
 */
on_npc_combat(*AberrantSpectreCombatScript.ids) {
    npc.queue {
        AberrantSpectreCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Regular Dragons
 */
on_npc_combat(*LeatherDragonScript.ids) {
    npc.queue {
        LeatherDragonScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Metal Dragons
 */
on_npc_combat(*MetalDragonScript.ids) {
    npc.queue {
        MetalDragonScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Mithril Dragon
 */
on_npc_combat(*MithrilDragonScript.ids) {
    npc.queue {
        MithrilDragonScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Mithril Dragon
 */
on_npc_combat(*FrostDragonScript.ids) {
    npc.queue {
        FrostDragonScript.handleSpecialCombat(this)
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
 * Sets the [on_npc_combat] for Jellys
 */
on_npc_combat(*JellysCombatScript.ids) {
    npc.queue {
        JellysCombatScript.handleSpecialCombat(this)
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
 * Sets the [on_npc_combat] for Banshees
 */
on_npc_combat(*BansheeCombatScript.ids) {
    npc.queue {
        BansheeCombatScript.handleSpecialCombat(this)
    }
}

/**
 * Sets the [on_npc_combat] for Bloodvelds
 */
on_npc_combat(*BloodveldCombatScript.ids) {
    npc.queue {
        BloodveldCombatScript.handleSpecialCombat(this)
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
/**
 * Sets the [on_npc_combat] for KBD
 */
on_npc_combat(*KingBlackDragonCombatScript.ids) {
    npc.queue {
        KingBlackDragonCombatScript.handleSpecialCombat(this)
    }
}
