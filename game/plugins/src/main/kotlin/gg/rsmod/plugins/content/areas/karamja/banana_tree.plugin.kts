package gg.rsmod.plugins.content.areas.karamja

val bananaTrees = listOf(Objs.BANANA_TREE, Objs.BANANA_TREE_2074, Objs.BANANA_TREE_2075, Objs.BANANA_TREE_2076, Objs
    .BANANA_TREE_2077)

var trees = mutableMapOf<Tile, GameObject>()

val treeUpdateTimer = TimerKey()

/**
   Update each tree every 100 ticks (1 minute)
 */
on_world_init {
    world.timers[treeUpdateTimer] = 100
}

/**
    Each tree that still isn't fully regrown gets one more banana added to it every 100 ticks (1 minute)
 */
on_timer(treeUpdateTimer) {
    val toRemove = mutableListOf<Tile>()
    trees.forEach {
        val oneMoreBanana = DynamicObject(
            id = getOneMore(it.value.id),
            type = it.value.type,
            rot = it.value.rot,
            tile = Tile(x = it.value.tile.x, z = it.value.tile.z),
        )
        world.spawn(oneMoreBanana)
        trees[it.key] = oneMoreBanana

        if (oneMoreBanana.id == Objs.BANANA_TREE) {
            toRemove.add(it.key)
        }
    }

    if (toRemove.isNotEmpty()) {
        toRemove.forEach {
            trees.remove(it)
        }
    }
    world.timers[treeUpdateTimer] = 100
}

bananaTrees.forEach {
    on_obj_option(obj = it, option = "Pick") {
        player.lockingQueue(lockState = LockState.FULL) {
            val interactingTree = player.getInteractingGameObj()
            val added = player.inventory.add(Items.BANANA, 1)
            if (added.hasSucceeded()) {
                var tree = trees[interactingTree.tile]
                if (tree == null) {
                    tree = interactingTree
                    trees[interactingTree.tile] = tree
                }

                val oneLessBanana = DynamicObject(
                    id = getOneLess(tree.id),
                    type = tree.type,
                    rot = tree.rot,
                    tile = Tile(x = tree.tile.x, z = tree.tile.z),
                )

                if (getOneLess(tree.id) != tree.id) {
                    world.spawn(oneLessBanana)
                    trees[interactingTree.tile] = oneLessBanana
                }
            }
            else {
                player.message("You don't have enough room in your inventory")
            }
        }
    }
}

on_obj_option(obj = Objs.BANANA_TREE_2078, option = "Search") {
    player.message("There are no bananas left on the tree.")
}

fun getOneLess(id: Int): Int {
    return when(id) {
        Objs.BANANA_TREE -> Objs.BANANA_TREE_2074
        Objs.BANANA_TREE_2074 -> Objs.BANANA_TREE_2075
        Objs.BANANA_TREE_2075 -> Objs.BANANA_TREE_2076
        Objs.BANANA_TREE_2076 -> Objs.BANANA_TREE_2077
        Objs.BANANA_TREE_2077 -> Objs.BANANA_TREE_2078
        else -> Objs.BANANA_TREE_2078
    }
}

fun getOneMore(id: Int): Int {
    return when(id) {
        Objs.BANANA_TREE_2074 -> Objs.BANANA_TREE
        Objs.BANANA_TREE_2075 -> Objs.BANANA_TREE_2074
        Objs.BANANA_TREE_2076 -> Objs.BANANA_TREE_2075
        Objs.BANANA_TREE_2077 -> Objs.BANANA_TREE_2076
        Objs.BANANA_TREE_2078 -> Objs.BANANA_TREE_2077
        else -> Objs.BANANA_TREE
    }
}
