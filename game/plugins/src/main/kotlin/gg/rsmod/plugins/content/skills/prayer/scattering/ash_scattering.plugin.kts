package gg.rsmod.plugins.content.skills.prayer.scattering


val ashData = AshData.values
val definitions = AshData.ashDefinitions
val ashes = ashData.map { it.ash }.toTypedArray()

ashes.forEach { ash ->
    on_item_option(ash, option = "scatter") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {

            player.animate(445)
            player.graphic(definitions[ash]!!.gfx)
            wait(2)

            if (player.inventory.remove(item = ash, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
                player.filterableMessage("You scatter the ashes in the wind.")
                player.addXp(Skills.PRAYER, definitions[ash]!!.experience)
            }
        }
    }
}
