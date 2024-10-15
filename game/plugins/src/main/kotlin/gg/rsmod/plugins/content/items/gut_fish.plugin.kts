package gg.rsmod.plugins.content.items

import kotlin.math.min

/**
 * @author Harley <https://github.com/HarleyGilpin/HarleyGilpin>
 */

/**
 * Gutting leaping trout with a knife.
 * Provides a chance of receiving roe and fish offcuts.
 * The roe and offcuts serve as bait.
 */
on_item_on_item(item1 = Items.KNIFE, item2 = Items.LEAPING_TROUT) {
    val cookingLevel = player.skills.getCurrentLevel(Skills.COOKING)
    val roeChance = min(1.0 + 65 * (cookingLevel - 1) / 98.0, 99.0) / 150.0
    val randomValue = Math.random()

    if (randomValue <= roeChance) {
        player.inventory.remove(Items.LEAPING_TROUT)

        // Add roe to the player's inventory
        player.inventory.add(Items.ROE)
        player.addXp(Skills.COOKING, 10.0)

        // 50% chance of receiving fish offcuts
        if (Math.random() <= 0.5) {
            player.inventory.add(Items.FISH_OFFCUTS)
        }

        player.filterableMessage("You successfully gut the leaping trout.")
    } else {
        player.inventory.remove(Items.LEAPING_TROUT)
        player.filterableMessage("You fail to gut the leaping trout.")
    }
}

/**
 * Gutting leaping salmon with a knife.
 * Provides a chance of receiving roe and fish offcuts.
 * The roe and offcuts serve as bait.
 */
on_item_on_item(item1 = Items.KNIFE, item2 = Items.LEAPING_SALMON) {
    val cookingLevel = player.skills.getCurrentLevel(Skills.COOKING)
    val roeChance = min(1.25 + 98.75 * (cookingLevel - 1) / 79.0, 100.0) / 100.0
    val randomValue = Math.random()

    if (randomValue <= roeChance) {
        player.inventory.remove(Items.LEAPING_SALMON)

        // Add roe to the player's inventory
        player.inventory.add(Items.ROE)
        player.addXp(Skills.COOKING, 10.0)

        // 75% chance of receiving fish offcuts
        if (Math.random() <= 0.75) {
            player.inventory.add(Items.FISH_OFFCUTS)
        }

        player.filterableMessage("You successfully gut the leaping salmon.")
    } else {
        player.inventory.remove(Items.LEAPING_SALMON)
        player.filterableMessage("You fail to gut the leaping salmon.")
    }
}

/**
 * Gutting leaping sturgeon with a knife.
 * Provides a chance of receiving caviar and fish offcuts.
 * The caviar and offcuts serve as bait.
 */
on_item_on_item(item1 = Items.KNIFE, item2 = Items.LEAPING_STURGEON) {
    val cookingLevel = player.skills.getCurrentLevel(Skills.COOKING)
    val caviarChance = min(1.25 + 98.75 * (cookingLevel - 1) / 79.0, 100.0) / 100.0
    val randomValue = Math.random()

    if (randomValue <= caviarChance) {
        player.inventory.remove(Items.LEAPING_STURGEON)

        // Add roe to the player's inventory
        player.inventory.add(Items.CAVIAR)
        player.addXp(Skills.COOKING, 15.0)

        // 5/6 chance of receiving fish offcuts
        if (Math.random() <= 5.0 / 6.0) {
            player.inventory.add(Items.FISH_OFFCUTS)
        }

        player.filterableMessage("You successfully gut the leaping sturgeon.")
    } else {
        player.inventory.remove(Items.LEAPING_STURGEON)
        player.filterableMessage("You fail to gut the leaping sturgeon.")
    }
}
