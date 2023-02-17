package gg.rsmod.plugins.content.skills.mining

RockType.objects.forEach { rock ->
    on_obj_option(obj = rock, option = 1) {
        val obj = player.getInteractingGameObj()
        val rockType = RockType.values().find { obj.id in it.objectIds } ?: return@on_obj_option
        player.queue {
            Mining.mineRock(this, obj, rockType)
        }
    }
}