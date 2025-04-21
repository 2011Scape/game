package gg.rsmod.plugins.content.combat.specialattack.weapons

import gg.rsmod.game.model.attr.SPEAR_WALL
import gg.rsmod.game.model.timer.SPEAR_WALL_TIMER
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks


on_timer(SPEAR_WALL_TIMER) {
    player.attr.remove(SPEAR_WALL)
}

SpecialAttacks.register(50, Items.VESTAS_SPEAR, Items.VESTAS_SPEAR_DEG) {
    player.animate(Anims.VESTAS_SPEAR_SPECIAL)
    player.graphic(Gfx.VESTAS_SPEAR_SPECIAL, 0, 0)
    player.playSound(Sfx.CLEAVE)

    val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.2)
    val accuracy = MeleeCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.2)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay, hitType = HitType
        .MELEE)
    player.attr[SPEAR_WALL] = true
    player.timers[SPEAR_WALL_TIMER] = 9

    if (player.tile.isMulti(player.world)) {
        val targets = ArrayList<Pawn>()
        val playerTargets = player.world.players.all { p ->
            p != target &&
            p.tile.getDistance(player.tile) <= 1 &&
            p.tile.isMulti(player.world)
        }
        val npcTargets = player.world.npcs.all { npc ->
            val nearestTile = npc.nearestTile(player.tile)
            npc != target &&
            nearestTile.getDistance(player.tile) <= 1 &&
            nearestTile.isMulti(player.world)
        }
        for (player in playerTargets) {
            if (targets.size < 9) {
                targets.add(player)
            }
        }
        for (npc in npcTargets) {
            if (targets.size < 9) {
                targets.add(npc)
            }
        }

        for (target in targets) {
            player.dealHit(target = target, maxHit = (maxHit * .2), landHit = landHit, delay = delay, hitType = HitType
                .MELEE)
        }
    }
}
