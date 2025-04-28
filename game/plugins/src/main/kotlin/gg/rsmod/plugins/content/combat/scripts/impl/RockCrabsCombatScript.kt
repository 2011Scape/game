package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.removeCombatTarget

object RockCrabsCombatScript : CombatScript() {
    override val ids = intArrayOf(Npcs.ROCKS, Npcs.ROCKS_1268)

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val id = if (it.npc.id == Npcs.ROCKS) Npcs.ROCK_CRAB else Npcs.ROCK_CRAB_1267
        val target = it.npc.getCombatTarget()
        val world = it.npc.world
        if (target is Player) {
            var crab = Npc(id, it.npc.tile, world)
            crab.walkRadius = 5
            crab.static = false
            it.npc.stopMovement()
            it.npc.removeCombatTarget()
            it.npc.resetInteractions()
            it.npc.resetFacePawn()
            world.remove(it.npc)
            world.spawn(crab)
            it.wait(1)
            crab.facePawn(target)
            crab.attack(target)
        }
    }
}
