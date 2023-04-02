package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.CrystalKeys

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
    player.animate(881)
    player.inventory.remove(item = cKey)
}

on_obj_option(obj = crystalChest, option="open") {
    player.queue {
    if (player.inventory.contains(cKey)){
        if (player.inventory.freeSlotCount > 3){
            player.lock()
            player.animate(881)
            player.inventory.remove(item = cKey)
            player.message("You unlock the chest with your key.")
            wait(4)
            DropTableFactory.createDropInventory(player, cKey, DropTableType.STALL)
            player.message("You find some treasure in the chest!")
            player.unlock()
        }else {
            player.message("You need more free space in your inventory.")
        }
    }else{
        player.message("The chest is locked.")
        }
    }
}

val cKeyRewards = DropTableFactory.build {
    guaranteed {
        obj(Items.UNCUT_DRAGONSTONE)
    }
    main{
        total(128)
        table(CrystalKeys.spinachRoll, slots = 34)
        table(CrystalKeys.runeStones, slots = 12)
        table(CrystalKeys.gems, slots = 12)
        table(CrystalKeys.runiteBars, slots = 12)
        table(CrystalKeys.cKeyHalves, slots = 10)
        table(CrystalKeys.ironOres, slots = 10)
        table(CrystalKeys.coal, slots = 10)
        table(CrystalKeys.rawSwordfish, slots = 8)
        table(CrystalKeys.addySqShield, slots = 2)
        nothing(slots = 17)
        if (player.appearance.gender.isMale()) {
            table(CrystalKeys.runeLegs, slots = 1)
        }else{
            table(CrystalKeys.runeSkirt, slots = 1)
        }
    }

}

DropTableFactory.register(cKeyRewards, cKey, type = DropTableType.CHEST)