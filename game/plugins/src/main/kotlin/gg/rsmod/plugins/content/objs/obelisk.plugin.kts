on_obj_options(Objs.OBELISK_28716, Objs.OBELISK_28725, Objs.OBELISK_28734, options = arrayOf("infuse-pouch")) {
    openPouchInterface(player)
}

fun openPouchInterface(player: Player) {
    player.openInterface(672, InterfaceDestination.MAIN_SCREEN)
    player.runClientScript(757, (672 shl 16 or 16), 8, 10, "Infuse<col=FF9040>", "Infuse-5<col=FF9040>", "Infuse-10<col=FF9040>", "Infuse-X<col=FF9040>", "Infuse-All<col=FF9040>", "List<col=FF9040>", 1, 78)
    player.setInterfaceEvents(672, 16, IntRange(0, 79), 254)
}