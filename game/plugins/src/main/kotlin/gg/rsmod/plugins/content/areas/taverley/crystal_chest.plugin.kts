package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

val TOOTH = Items.TOOTH_HALF_OF_A_KEY
val LOOP = Items.LOOP_HALF_OF_A_KEY
val CRYSTAL_KEY = Items.CRYSTAL_KEY
val CRYSTAL_CHEST = Objs.CLOSED_CHEST_172

on_item_on_item(item1 = TOOTH, item2 = LOOP) {
    player.inventory.remove(item = TOOTH)
    player.inventory.remove(item = LOOP)
    player.inventory.add(item = CRYSTAL_KEY)
    player.message("You join the two halves of the key together.")
}

on_item_on_obj(obj = CRYSTAL_CHEST, item = CRYSTAL_KEY) {
    val obj = player.getInteractingGameObj()
    player.inventory.remove(CRYSTAL_KEY, 1)
    player.faceTile(obj.tile)
    player.lockingQueue(TaskPriority.STRONG) {
        val closedChest = DynamicObject(obj)
        val openChest =
            DynamicObject(id = 173, type = obj.type, rot = obj.rot, tile = Tile(x = obj.tile.x, z = obj.tile.z))
        world.remove(closedChest)
        world.spawn(openChest)

        player.animate(536)
        player.message("You unlock the chest with your key.")

        wait(2)

        world.remove(openChest)
        world.spawn(closedChest)
        val drop = DropTableFactory.createDropInventory(player, CRYSTAL_KEY, DropTableType.CHEST)
        if (drop != null) player.message("You find some treasure in the chest!")
    }
}

on_obj_option(obj = CRYSTAL_CHEST, option = "open") {
    player.message("This chest is securely locked shut.")
}
val table = DropTableFactory
val mainTable =
    table.build {
        val toothDrop = world.percentChance(50.0)
        val male = player.appearance.gender.isMale()
        guaranteed {
            obj(Items.UNCUT_DRAGONSTONE)
        }
        main {
            total(128)
            objs(
                Item(id = Items.SPINACH_ROLL),
                Item(id = Items.COINS_995, amount = 2000),
                slots = 34,
            )
            objs(
                Item(id = Items.AIR_RUNE, amount = 50),
                Item(id = Items.WATER_RUNE, amount = 50),
                Item(id = Items.EARTH_RUNE, amount = 50),
                Item(id = Items.FIRE_RUNE, amount = 50),
                Item(id = Items.BODY_RUNE, amount = 50),
                Item(id = Items.MIND_RUNE, amount = 50),
                Item(id = Items.CHAOS_RUNE, amount = 10),
                Item(id = Items.DEATH_RUNE, amount = 10),
                Item(id = Items.COSMIC_RUNE, amount = 10),
                Item(id = Items.NATURE_RUNE, amount = 10),
                Item(id = Items.LAW_RUNE, amount = 10),
                slots = 12,
            )
            objs(
                Item(id = Items.RUBY, amount = 2),
                Item(id = Items.DIAMOND, amount = 2),
                slots = 12,
            )
            objs(
                Item(id = Items.RUNE_BAR, amount = 3),
                slots = 12,
            )
            objs(
                Item(id = if (toothDrop) Items.TOOTH_HALF_OF_A_KEY else Items.LOOP_HALF_OF_A_KEY),
                Item(id = Items.COINS_995, amount = 750),
                slots = 10,
            )
            objs(
                Item(id = Items.IRON_ORE_NOTED, amount = 150),
                slots = 10,
            )
            objs(
                Item(id = Items.COAL_NOTED, amount = 100),
                slots = 10,
            )
            objs(
                Item(id = Items.RAW_SWORDFISH, amount = 5),
                Item(id = Items.COINS_995, amount = 1000),
                slots = 8,
            )
            objs(
                Item(id = Items.ADAMANT_SQ_SHIELD),
                slots = 2,
            )
            obj(if (male) Items.RUNE_PLATELEGS else Items.RUNE_PLATESKIRT, slots = 1)
            nothing(slots = 17)
        }
    }

table.register(mainTable, CRYSTAL_KEY, type = DropTableType.CHEST)
