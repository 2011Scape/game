package gg.rsmod.plugins.content.items.lamps

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.LAMP, option = "rub") {
    player.openInterface(interfaceId = 1139, dest = InterfaceDestination.MAIN_SCREEN)
    player.filterableMessage("You rub the lamp...")
}
