package gg.rsmod.plugins.content.skills.slayer

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.ENCHANTED_GEM, option = "activate") {
    player.contactSlayerMaster()
}

on_item_option(item = Items.ENCHANTED_GEM, option = "kills-left") {
    player.getSlayerKillsRemaining()
}
