package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.smithing.data.SmeltingData
import kotlin.math.min

/**
 * @author Triston Plummer ("Dread")
 *
 * Handles the action of smelting bars at a furnace.
 */
class SmeltingAction(private val defs: DefinitionSet) {

    /**
     * A map of bar ids to their item names
     */
    private val barNames = SmeltingData.values.associate { it.product to  defs.get(ItemDef::class.java, it.product).name.lowercase() }

    /**
     * A map of ore ids to their item names
     */
    private val oreNames =
        SmeltingData.values.map { Pair(it.primaryOre, it.secondaryOre) }.flatMap { it.toList() }.distinct().associateWith {
            defs.get(
                ItemDef::class.java, it).name.lowercase()
        }

    /**
     * Handles the smelting of a bar
     *
     * @param task      The queued action task
     * @param bar       The bar definition
     * @param amount    The amount the player is trying to smelt
     */
    suspend fun smelt(task: QueueTask, bar: SmeltingData, amount: Int) {

        if (!canSmelt(task, bar))
            return

        val player = task.player
        val inventory = player.inventory

        val primaryCount = inventory.getItemCount(bar.primaryOre)
        val secondaryCount = inventory.getItemCount(bar.secondaryOre)

        val barCount = if (bar.secondaryCount > 0) min(primaryCount, secondaryCount / bar.secondaryCount) else primaryCount
        val maxCount = min(amount, barCount)

        repeat(maxCount) {

            player.animate(SMELT_ANIM)
            player.playSound(SMELT_SOUND)
            task.wait(ANIMATION_CYCLE)
            player.lock()
            task.wait(ANIMATION_CYCLE)

            if (!canSmelt(task, bar)) {
                player.animate(-1)
                return
            }

            val removePrimary = inventory.remove(item = bar.primaryOre)
            val removeSecondary = inventory.remove(item = bar.secondaryOre, amount = bar.secondaryCount, assureFullRemoval = true)

            val removedFromInventory = removePrimary.hasSucceeded() && removeSecondary.hasSucceeded()
            val ironBarSuccess = bar.product != Items.IRON_BAR || player.world.randomBoolean()

            if (removedFromInventory && ironBarSuccess) {
                inventory.add(bar.product)
                player.addXp(Skills.SMITHING, bar.experience)
            }

            player.unlock()
            task.wait(WAIT_CYCLE)
        }
    }

    /**
     * Checks if a bar can be smelted
     *
     * @param task  The queued task
     * @param bar   The bar to smelt
     */
    private suspend fun canSmelt(task: QueueTask, bar: SmeltingData) : Boolean {
        val player = task.player
        val inventory = player.inventory

        if (!inventory.contains(bar.primaryOre) || inventory.getItemCount(bar.secondaryOre) < bar.secondaryCount) {
            val message = when(bar.secondaryCount) {
                0 -> "You don't have any ${oreNames[bar.primaryOre]} to smelt."
                else -> "You need one ${oreNames[bar.primaryOre]} and ${bar.secondaryCount.toLiteral()} ${oreNames[bar.secondaryOre]} to make ${barNames[bar.product]?.prefixAn()}."
            }

            task.messageBox(message)
            return false
        }

        if (player.getSkills().getCurrentLevel(Skills.SMITHING) < bar.levelRequired) {
            task.messageBox("You need a ${Skills.getSkillName(player.world, Skills.SMITHING)} level of at least ${bar.levelRequired} to smelt ${oreNames[bar.primaryOre]}.")
            return false
        }

        return true
    }

    companion object {

        /**
         * The animation played when smelting a bar
         */
        const val SMELT_ANIM = 899

        /**
         * The sound played when smelting a bar
         */
        const val SMELT_SOUND = 2725

        /**
         * The number of ticks between starting the animation, and locking the player to the action
         */
        const val ANIMATION_CYCLE = 2

        /**
         * The number of ticks before automatically continuing the produce the next bar
         */
        const val WAIT_CYCLE = 3
    }
}