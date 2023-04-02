package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.CrystalKey

val toothHalf = Items.TOOTH_HALF_OF_A_KEY
val loopHalf = Items.LOOP_HALF_OF_A_KEY
val cKey = Items.CRYSTAL_KEY
val crystalChest = Objs.CLOSED_CHEST_172


on_item_on_item(item1 = toothHalf, item2 = loopHalf) {
    player.inventory.remove(item = toothHalf)
    player.inventory.remove(item = loopHalf)
    player.inventory.add(item = cKey)
    player.message("You join the two halves of the key together.")

}

on_item_on_obj(obj = crystalChest, item = cKey) {
    val obj = player.getInteractingGameObj()
    player.inventory.remove(cKey, 1)
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

        DropTableFactory.createDropInventory(player, cKey, DropTableType.CHEST)
        player.message("You find some treasure in the chest!")
    }
}

on_obj_option(obj = crystalChest, option="open") {
    player.message("This chest is securely locked shut.")
}

val cKeyRewards = DropTableFactory.build {
    guaranteed {
        obj(Items.UNCUT_DRAGONSTONE)
    }
    main{
        total(128)
        table(CrystalKey.spinachRoll, slots = 34)
        table(CrystalKey.runeStones, slots = 12)
        table(CrystalKey.gems, slots = 12)
        table(CrystalKey.runiteBars, slots = 12)
        table(CrystalKey.cKeyHalves, slots = 10)
        table(CrystalKey.ironOres, slots = 10)
        table(CrystalKey.coal, slots = 10)
        table(CrystalKey.rawSwordfish, slots = 8)
        table(CrystalKey.addySqShield, slots = 2)
        nothing(slots = 17)
        if (player.appearance.gender.isMale()) {
            table(CrystalKey.runeLegs, slots = 1)
        }else{
            table(CrystalKey.runeSkirt, slots = 1)
        }
    }

}

DropTableFactory.register(cKeyRewards, cKey, type = DropTableType.CHEST)