package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.plugins.content.skills.fishing.Fishing
import gg.rsmod.plugins.content.skills.fishing.FishingTool
import kotlin.math.floor
import kotlin.math.pow

on_command("missing_defs", Privilege.ADMIN_POWER) {
    val npcs = (0..14376)
            .filter { player.world.npcs.any { npc -> npc.id == it } }
            .map { player.world.definitions.get(NpcDef::class.java, it) }
            .filter { "attack" in it.options }
            .sortedBy { it.id }
            .joinToString("\n") { "${it.id} - ${it.name}" }
    println(npcs)
}

on_command("rake", Privilege.ADMIN_POWER) {
    val varbit = player.getVarbit(781)
    player.message("Current value: $varbit")
    player.setVarbit(781, varbit + 1)
}

on_command("fishing_check", Privilege.ADMIN_POWER) {
    val xpTable = IntArray(99).apply {
        var points = 0
        for (level in 1 until size) {
            points += floor(level + 300 * 2.0.pow(level / 7.0)).toInt()
            set(level, points / 4)
        }
    }

    fun getXpForNextLevel(level: Int) = (xpTable[level] - xpTable[level - 1]).toDouble()
    val results = mutableListOf<List<String>>()

    for (lvl in 1..98) {
        val xpToGo = getXpForNextLevel(lvl)
        val result = mutableListOf("========= Level $lvl to ${lvl + 1}")
        for (tool in FishingTool.values().filter { it.level <= lvl }) {
            var count = 0
            var ticks = 0
            var xpLeft = xpToGo
            while (xpLeft > 0) {
                ticks += 4
                for (fish in tool.relevantFish(lvl)) {
                    if (fish.roll(lvl)) {
                        count++
                        xpLeft -= fish.xp
                        break
                    }
                }
            }
            result += "${tool.name}: $ticks ticks for $count catches"
        }
        results += result
    }

    println("done")
}
