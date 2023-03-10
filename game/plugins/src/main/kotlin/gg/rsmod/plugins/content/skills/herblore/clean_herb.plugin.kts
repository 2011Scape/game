package gg.rsmod.plugins.content.skills.herblore

val grimyHerbDefinitions = HerbData.grimyHerbDefinitions

grimyHerbDefinitions.values.forEach { unf ->
    on_item_option(item = unf.grimy, option = "Clean") {
        cleanHerb(player, unf.grimy)
    }
}

fun cleanHerb(player: Player, herb: Int) {
    val def = grimyHerbDefinitions[herb] ?: return
    val slot = player.getInteractingItemSlot()
    if (player.inventory.remove(herb, beginSlot = slot, assureFullRemoval = true).hasSucceeded()) {
        player.inventory.add(def.clean, 1, beginSlot = slot, assureFullInsertion = true)
        player.addXp(Skills.HERBLORE, def.experience)
        player.filterableMessage("You clean the herb.")
    }
}