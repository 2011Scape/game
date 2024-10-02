package gg.rsmod.plugins.content.inter.magic

import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.Combat.DEFENSIVE_CAST_VARP
import gg.rsmod.plugins.content.combat.Combat.SELECTED_AUTOCAST_VARP
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.magic.MagicSpells

val FILTER_COMBAT_SPELLS_VARBIT = 6459
val FILTER_TELEPORT_SPELLS_VARBIT = 6462
val FILTER_MISC_SPELLS_VARBIT = 6461
val FILTER_SKILL_SPELLS_VARBIT = 6460

CombatSpell.definitions.values.forEach { spell ->
    on_button(interfaceId = 192, component = spell.componentId) {
        if (player.getVarp(SELECTED_AUTOCAST_VARP) == spell.autoCastId) {
            player.attr.remove(Combat.CASTING_SPELL)
            player.setVarp(SELECTED_AUTOCAST_VARP, 0)
            return@on_button
        }

        val metadata = MagicSpells.getMetadata(spell.uniqueId)
        if (metadata != null && MagicSpells.canCast(player, metadata.lvl, metadata.runes)) {
            player.attr[Combat.CASTING_SPELL] = spell
            player.setVarp(SELECTED_AUTOCAST_VARP, spell.autoCastId)
        } else {
            player.setVarp(SELECTED_AUTOCAST_VARP, 0)
        }
    }
}

on_button(interfaceId = 192, 2) {
    if (player.getVarp(DEFENSIVE_CAST_VARP) > 0) {
        player.setVarp(DEFENSIVE_CAST_VARP, 0)
        return@on_button
    }
    player.setVarp(DEFENSIVE_CAST_VARP, 256)
}

on_button(interfaceId = 192, 7) {
    player.toggleVarbit(FILTER_COMBAT_SPELLS_VARBIT)
}

on_button(interfaceId = 192, 9) {
    player.toggleVarbit(FILTER_TELEPORT_SPELLS_VARBIT)
}

on_button(interfaceId = 192, 11) {
    player.toggleVarbit(FILTER_MISC_SPELLS_VARBIT)
}

on_button(interfaceId = 192, 13) {
    player.toggleVarbit(FILTER_SKILL_SPELLS_VARBIT)
}
