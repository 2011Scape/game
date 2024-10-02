package gg.rsmod.plugins.content.objs

/**
 * @author Alycia <https://github.com/alycii>
 */

// Define an array of objects representing cows
val cows = arrayOf(Objs.DAIRY_COW, Objs.DAIRY_COW_12111)

// Iterate over each cow object in the 'cows' array
cows.forEach {
    // Set up an object option for milking the cow
    on_obj_option(obj = it, option = "Milk") {
        // Check if the player has an empty bucket in their inventory
        if (!player.inventory.contains(Items.BUCKET)) {
            player.message("You'll need an empty bucket to collect the milk.")
            return@on_obj_option
        }
        // Add a task to the player's queue to milk the cow
        player.queue {
            // Animate the player milking the cow
            player.animate(2305)
            wait(world.getAnimationDelay(2305))
            // Remove an empty bucket from the player's inventory and add a bucket of milk
            player.inventory.remove(Items.BUCKET)
            player.inventory.add(Items.BUCKET_OF_MILK)
            // Send a message to the player indicating that they have milked the cow
            player.filterableMessage("You milk the cow.")
        }
    }
}
