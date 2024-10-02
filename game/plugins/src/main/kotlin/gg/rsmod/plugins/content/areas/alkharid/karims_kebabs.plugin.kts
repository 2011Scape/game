package gg.rsmod.plugins.content.areas.alkharid

on_npc_option(Npcs.KARIM, option = "talk-to") {
    player.queue {
        chatNpc("Would you like to buy a nice kebab? Only one gold.", facialExpression = FacialExpression.HAPPY_TALKING)
        when (options("I think I'll give it a miss.", "Yes please.")) {
            1 -> chatPlayer("I think I'll give it a miss.")
            2 -> {
                chatPlayer("Yes please.")
                if (player.inventory.remove(Items.COINS_995, amount = 1, assureFullRemoval = true).hasSucceeded()) {
                    val addKebab = player.inventory.add(Items.KEBAB)
                    if (addKebab.hasFailed()) {
                        val kebab = GroundItem(Items.KEBAB, amount = 1, tile = player.tile, owner = player)
                        world.spawn(kebab)
                    }
                    player.filterableMessage("You buy a kebab.")
                } else {
                    chatPlayer("Oops, I forgot to bring any money with me.", facialExpression = FacialExpression.SAD_2)
                    chatNpc("Come back when you have some.")
                }
            }
        }
    }
}
