package gg.rsmod.plugins.content.objs.cupboard

import gg.rsmod.plugins.content.quests.impl.TheKnightsSword

val knightsSword = TheKnightsSword

on_obj_option(obj = Objs.CUPBOARD_2271, option = "open") {
    val obj = player.getInteractingGameObj()
    player.queue {
        player.animate(536)
        wait(2)
        val dynObj = DynamicObject(other = obj, id = 2272)
        world.spawn(dynObj)
    }
}

on_obj_option(obj = Objs.CUPBOARD_2272, option = "search") {
    player.queue {
        if (player.inventory.contains(Items.PORTRAIT) || player.bank.contains(Items.PORTRAIT)) {
            messageBox("There is just a load of junk in here.")
        } else {
            messageBox("You find a small portrait in here which you take.")
            if (player.inventory.add(Items.PORTRAIT, assureFullInsertion = true).hasFailed()) {
                world.spawn(GroundItem(Items.PORTRAIT, amount = 1, tile = player.tile, owner = player))
            }
        }
    }
}

on_obj_option(obj = Objs.CUPBOARD_2272, option = "shut") {
    val obj = player.getInteractingGameObj()
    player.queue {
        player.animate(536)
        wait(2)
        val dynObj = DynamicObject(other = obj, id = 2271)
        world.spawn(dynObj)
    }
}