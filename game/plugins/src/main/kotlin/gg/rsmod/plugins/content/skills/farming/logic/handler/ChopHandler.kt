package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.PatchState
import gg.rsmod.plugins.content.skills.woodcutting.AxeType
import gg.rsmod.plugins.content.skills.woodcutting.TreeType
import gg.rsmod.plugins.content.skills.woodcutting.Woodcutting

/**
 * Logic related to chopping down a tree in a patch
 */
class ChopHandler(
    private val state: PatchState,
    private val player: Player,
) {
    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    fun chopDown(obj: GameObject) {
        when (state.seed?.seedType) {
            null -> Unit
            SeedType.Tree -> chopDownTree(obj)
            else -> chopDownFruitTree()
        }
    }

    private fun chopDownTree(obj: GameObject) {
        val tree = mapToTreeType() ?: return
        player.queue {
            Woodcutting.chopDownTree(this, obj, tree, state)
        }
    }

    private fun mapToTreeType() =
        when (state.seed) {
            Seed.Oak -> TreeType.OAK
            Seed.Willow -> TreeType.WILLOW
            Seed.Maple -> TreeType.MAPLE
            Seed.Yew -> TreeType.YEW
            Seed.Magic -> TreeType.MAGIC
            else -> null
        }

    private fun chopDownFruitTree() {
        val axe =
            AxeType.values.reversed().firstOrNull {
                player.skills
                    .getMaxLevel(Skills.WOODCUTTING) >= it.level &&
                    (
                        player.equipment.contains(it.item) ||
                            player.inventory.contains(
                                it.item,
                            )
                    )
            }
        if (axe == null) {
            player.message("You need a hatchet to chop this tree.")
            return
        }

        if (state.canBeChopped && state.seed!!.seedType == SeedType.FruitTree) {
            player.queue {
                player.animate(axe.animation)
                farmingTimerDelayer.delayIfNeeded(chopWaitTime)
                wait(chopWaitTime)
                state.chopDown()
            }
        }
    }

    companion object {
        private const val chopWaitTime = 6
    }
}
