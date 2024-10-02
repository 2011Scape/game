package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.SkillDialogueOption
import gg.rsmod.plugins.api.ext.produceItemBox
import gg.rsmod.plugins.content.skills.smithing.data.SmeltingData

object Smelting {
    /**
     * The [Bar] enum values
     */
    val bars = SmeltingData.values

    /**
     * The map of [Bar] ids to their definition
     */
    val barDefs = SmeltingData.barDefinitions

    /**
     * An array of [Bar] item ids that may be smelted at any standard furnace
     */
    val standardBarIds = bars.map { bar -> bar.product }.toIntArray()

    /**
     * An array of [Bar] ore item ids that can be used on furnaces to open the smelting interface
     */
    val standardOreIds = bars.flatMap { bars.map { ore -> ore.primaryOre } }

    /**
     * Handles the smelting of the standard bars
     *
     * @param player    The player instance
     * @param item      The item the player is trying to smelt
     * @param amount    The number of bars the player is trying to smelt
     */
    fun smeltItem(
        player: Player,
        item: Int,
        amount: Int = 28,
    ) {
        val def = barDefs[item] ?: return
        player.queue { SmeltingAction(player.world.definitions).smelt(this, def, amount) }
    }

    /**
     * Opens the proompt to smelt any standard bar
     *
     * @param player    The player instance
     */
    fun smeltStandard(player: Player) {
        player.queue {
            produceItemBox(
                *standardBarIds,
                option = SkillDialogueOption.MAKE,
                title = "How many bars would you like to smelt?<br>Choose a number, then click the bar to begin.",
                logic = ::smeltItem,
            )
        }
    }
}
