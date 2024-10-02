package gg.rsmod.plugins.content.items.birdnests

import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * When woodcutting, a roll is done to determine the type of nest the player will receive.
 * The table contains 100 slots:
 * 3/100 egg nest (one for each colour),
 * 32/100 ring nest,
 * 65/100 seed nest.
 * When wearing a strung rabbit foot, five seed slots are removed, making the chances:
 * 3/95 for bird's egg, 32/95 for ring and 60/95 for seeds.
 *
 * Source: https://oldschool.runescape.wiki/w/Bird_nest_(ring)
 */

val BIRDS_NEST_DROP_ID = 10_000

val woodcuttingTable = DropTableFactory.build {
    main {
        if(player.equipment.contains(Items.STRUNG_RABBIT_FOOT)) {
            total(95)
            obj(Items.BIRDS_NEST_5073, slots = 60)
        } else {
            total(100)
            obj(Items.BIRDS_NEST_5073, slots = 65)
        }
        obj(Items.BIRDS_NEST, slots = 1)
        obj(Items.BIRDS_NEST_5071, slots = 1)
        obj(Items.BIRDS_NEST_5072, slots = 1)
        obj(Items.BIRDS_NEST_5074, slots = 32)
    }
}

DropTableFactory.register(woodcuttingTable, BIRDS_NEST_DROP_ID)


val seedNestTable = DropTableFactory.build {
    guaranteed {
        obj(Items.BIRDS_NEST_5075)
    }
    main {
        total(1024)
        obj(Items.ACORN, slots = 218)
        obj(Items.APPLE_TREE_SEED, slots = 174)
        obj(Items.WILLOW_SEED, slots = 147)
        obj(Items.BANANA_TREE_SEED, slots = 114)
        obj(Items.ORANGE_TREE_SEED, slots = 87)
        obj(Items.CURRY_TREE_SEED, slots = 69)
        obj(Items.MAPLE_SEED, slots = 57)
        obj(Items.PINEAPPLE_SEED, slots = 42)
        obj(Items.PAPAYA_TREE_SEED, slots = 34)
        obj(Items.YEW_SEED, slots = 27)
        obj(Items.PALM_TREE_SEED, slots = 22)
        obj(Items.CALQUAT_TREE_SEED, slots = 17)
        obj(Items.SPIRIT_SEED, slots = 11)
        obj(Items.MAGIC_SEED, slots = 5)
    }
}

DropTableFactory.register(seedNestTable, 10001)

on_item_option(item = Items.BIRDS_NEST_5073, option = "Search") {
    if(player.inventory.isFull) {
        player.queue {
            messageBox("Your inventory is too full to take anything out of the bird's nest.")
        }
        return@on_item_option
    }
    if(player.inventory.remove(item = Items.BIRDS_NEST_5073, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        DropTableFactory.createDropInventory(player, 10001)
    }
}


val ringNestTable = DropTableFactory.build {
    guaranteed {
        obj(Items.BIRDS_NEST_5075)
    }
    main {
        total(100)
        obj(Items.SAPPHIRE_RING, slots = 40)
        obj(Items.GOLD_RING, slots = 35)
        obj(Items.EMERALD_RING, slots = 15)
        obj(Items.RUBY_RING, slots = 9)
        obj(Items.DIAMOND_RING, slots = 1)
    }
}

DropTableFactory.register(ringNestTable, 10002)

on_item_option(item = Items.BIRDS_NEST_5074, option = "Search") {
    if(player.inventory.isFull) {
        player.queue {
            messageBox("Your inventory is too full to take anything out of the bird's nest.")
        }
        return@on_item_option
    }
    if(player.inventory.remove(item = Items.BIRDS_NEST_5074, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        DropTableFactory.createDropInventory(player, 10002)
    }
}

// Zamorak egg
on_item_option(item = Items.BIRDS_NEST, option = "Search") {
    if(player.inventory.isFull) {
        player.queue {
            messageBox("Your inventory is too full to take anything out of the bird's nest.")
        }
        return@on_item_option
    }
    if(player.inventory.remove(item = Items.BIRDS_NEST, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        player.inventory.add(item = Items.BIRDS_NEST_5075)
        player.inventory.add(item = Items.BIRDS_EGG)
    }
}

// Guthix egg
on_item_option(item = Items.BIRDS_NEST_5071, option = "Search") {
    if(player.inventory.isFull) {
        player.queue {
            messageBox("Your inventory is too full to take anything out of the bird's nest.")
        }
        return@on_item_option
    }
    if(player.inventory.remove(item = Items.BIRDS_NEST_5071, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        player.inventory.add(item = Items.BIRDS_NEST_5075)
        player.inventory.add(item = Items.BIRDS_EGG_5078)
    }
}

// Saradomin egg
on_item_option(item = Items.BIRDS_NEST_5072, option = "Search") {
    if(player.inventory.isFull) {
        player.queue {
            messageBox("Your inventory is too full to take anything out of the bird's nest.")
        }
        return@on_item_option
    }
    if(player.inventory.remove(item = Items.BIRDS_NEST_5072, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        player.inventory.add(item = Items.BIRDS_NEST_5075)
        player.inventory.add(item = Items.BIRDS_EGG_5077)
    }
}