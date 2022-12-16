package gg.rsmod.plugins.content.inter.attack

import gg.rsmod.game.model.attr.NEW_ACCOUNT_ATTR

/**
 * First log-in logic (when accounts have just been made).
 */
on_login {
    if (player.attr.getOrDefault(NEW_ACCOUNT_ATTR, false)) {
        AttackTab.setEnergy(player, 100)
    }
}

/**
 * Attack style buttons
 */
on_button(interfaceId = 884, component = 11) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 0)
}

on_button(interfaceId = 884, component = 12) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 1)
}

on_button(interfaceId = 884, component = 13) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 2)
}

on_button(interfaceId = 884, component = 14) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 3)
}

/**
 * Toggle auto-retaliate button.
 */
on_button(interfaceId = 884, component = 15) {
    player.toggleVarp(AttackTab.DISABLE_AUTO_RETALIATE_VARP)
}

/**
 * Toggle special attack.
 */
on_button(interfaceId = 884, component = 4) {
    player.toggleVarp(AttackTab.SPECIAL_ATTACK_VARP)
}

/**
 * Disable special attack when switching weapons.
 */
on_equip_to_slot(EquipmentType.WEAPON.id) {
    player.setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}

/**
 * Disable special attack on log-out.
 */
on_logout {
    player.setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}