package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.skills.farming.data.Patch

/**
 * Provides some helper functions to manipulate the patch-related varbit
 */
abstract class PatchVarbitUpdater(protected val patch: Patch, player: Player) : VarbitUpdater(patch.varbit, player)
