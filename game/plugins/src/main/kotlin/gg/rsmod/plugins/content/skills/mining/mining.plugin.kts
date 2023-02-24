package gg.rsmod.plugins.content.skills.mining

val rockValues = RockType.values
val rockObjects = RockType.objects
val depletedRockSet = rockObjects.map { id ->
    world.definitions.get(ObjectDef::class.java, id).depleted
}.toSet()

rockObjects.forEach { rock ->
    on_obj_option(obj = rock, option = 1) {
        val obj = player.getInteractingGameObj()
        val rockType = rockValues.find { obj.id in it.objectIds } ?: return@on_obj_option
        player.queue {
            Mining.mineRock(this, obj, rockType)
        }
    }
}
depletedRockSet.forEach { depletedRock ->
    on_obj_option(obj = depletedRock, option = 1) {

        player.interruptQueues()
        player.resetInteractions()

        player.animate(-1)
        player.playSound(2661)
        player.filterableMessage("There is currently no ore available in this rock.")

    }
}