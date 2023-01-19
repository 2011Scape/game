package gg.rsmod.plugins.content.skills.prayer.burying


val boneData = BoneData.values
val definitions = BoneData.boneDefinitions
val bones = boneData.map { it.bone }.toTypedArray()

bones.forEach { bone ->
    on_item_option(bone, option = "bury") {
        player.queue {
            player.filterableMessage("You dig a hole in the ground...")
            player.animate(827)
            player.lock()
            wait(2)
            if (player.inventory.remove(item = bone, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
                player.filterableMessage("You bury the bones.")
                player.addXp(Skills.PRAYER, definitions[bone]!!.experience)
                player.unlock()
            }
        }
    }
}