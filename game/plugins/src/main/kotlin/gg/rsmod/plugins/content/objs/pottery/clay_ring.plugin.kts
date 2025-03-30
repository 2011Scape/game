package gg.rsmod.plugins.content.objs.pottery

import gg.rsmod.plugins.content.skills.crafting.pottery.PotteryData

on_item_on_item(Items.SOFT_CLAY, Items.RING_MOULD) {
    handleMakingUnfiredRing(player)
}

fun handleMakingUnfiredRing(player: Player) {
    player.queue {
        val maxCount = player.inventory.count { item -> item?.id == Items.SOFT_CLAY }
        produceItemBox(Items.CLAY_RING_UNFIRED, names = arrayOf("Clay ring (unfired)"), maxItems = maxCount, logic =
            ::pickRings)
    }
}

fun pickRings(player: Player, item: Int, amount: Int) {
    player.queue {
        formRings(this, amount)
    }
}

suspend fun formRings(task: QueueTask, amount: Int) {
    var firstCycle = true
    repeat(amount) {
        if (!task.player.inventory
                .remove(
                    Items.SOFT_CLAY,
                    amount = 1,
                    assureFullRemoval = true,
                ).hasSucceeded()
        ) {
            return
        }
        task.player.inventory.add(item = Items.CLAY_RING_UNFIRED, assureFullInsertion = true)
        task.player.addXp(Skills.CRAFTING, PotteryData.CLAY_RING.products[0].formingExperience)
        task.player.filterableMessage("You press the clay into the mould and retrieve a clay ring.")
        if (firstCycle) {
            task.wait(2)
            firstCycle = false
        }
        else {
            task.wait(1)
        }
    }
}
