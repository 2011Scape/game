package gg.rsmod.plugins.content.skills.woodcutting

TreeType.objects.forEach { tree ->
    on_obj_option(obj = tree, option = 1) {
        val obj = player.getInteractingGameObj()
        val treeType = TreeType.values().find { obj.id in it.objectIds } ?: return@on_obj_option
        player.queue {
            Woodcutting.chopDownTree(this, obj, treeType)
        }
    }
}
