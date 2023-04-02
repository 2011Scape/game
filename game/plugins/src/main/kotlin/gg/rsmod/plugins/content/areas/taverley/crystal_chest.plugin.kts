package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.CrystalChestTable

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
        val openChest = DynamicObject(id = 173, type = obj.type, rot = obj.rot, tile = Tile(x = obj.tile.x, z = obj.tile.z))
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

on_obj_option(obj = CRYSTAL_CHEST, option="open") {
    player.message("This chest is securely locked shut.")
}
val table = DropTableFactory
val mainTable = table.build {
    guaranteed {
        obj(Items.UNCUT_DRAGONSTONE)
    }
    main{
        total(128)
        table(CrystalChestTable.spinash_roll, slots = 34)
        table(CrystalChestTable.runes, slots = 12)
        table(CrystalChestTable.gems, slots = 12)
        table(CrystalChestTable.bars, slots = 12)
        table(CrystalChestTable.key_pieces, slots = 10)
        table(CrystalChestTable.iron_ore, slots = 10)
        table(CrystalChestTable.coal, slots = 10)
        table(CrystalChestTable.swordfish, slots = 8)
        table(CrystalChestTable.adamant_shield, slots = 2)
        nothing(slots = 17)
        val male = player.appearance.gender.isMale()
        table(if (male) CrystalChestTable.rune_platelegs else CrystalChestTable.rune_plateskirt, slots = 1)
    }
}

table.register(mainTable, CRYSTAL_KEY, type = DropTableType.CHEST)