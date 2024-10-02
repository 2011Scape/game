package gg.rsmod.plugins.content.skills.prayer.burying


val boneData = BoneData.values
val definitions = BoneData.boneDefinitions
val bones = boneData.map { it.bone }.toTypedArray()

bones.forEach { bone ->
    on_item_option(bone, option = "bury") {
        player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {

            // send the message, animate and play the sound
            player.filterableMessage("You dig a hole in the ground...")
            player.animate(827)
            player.playSound(2738)

            // wait 2 ticks for the animation to play
            wait(2)

            // remove the bones, and add experience
            if (player.inventory.remove(item = bone, beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
                player.filterableMessage("You bury the bones.")
                player.addXp(Skills.PRAYER, definitions[bone]!!.experience)
            }
        }
    }
}
