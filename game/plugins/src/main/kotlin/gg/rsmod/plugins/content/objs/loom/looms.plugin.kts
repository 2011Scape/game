package gg.rsmod.plugins.content.objs.loom

import gg.rsmod.plugins.content.skills.crafting.weaving.WeavingAction
import gg.rsmod.plugins.content.skills.crafting.weaving.WeavingData

val LOOMS = listOf(Objs.LOOM, Objs.LOOM_8717)
val weavingData = WeavingData.values

LOOMS.forEach {
    on_obj_option(it, option = "weave") {
        val products = weavingData.map { product -> product.resultItem }.toIntArray()
        val productNames =
            weavingData
                .map { data ->
                    if (data == WeavingData.MILESTONE_CAPES) {
                        "Milestone capes"
                    } else {
                        player.world.definitions
                            .get(ItemDef::class.java, data.resultItem)
                            .name
                    }
                }.toTypedArray()
        val resourceNames = weavingData.map { resources -> "(${resources.rawName})" }.toTypedArray()
        player.queue {
            produceItemBox(
                *products,
                option = SkillDialogueOption.MAKE,
                names = productNames,
                extraNames = resourceNames,
                logic = ::weaveItem,
            )
        }
    }
}

fun weaveItem(
    player: Player,
    item: Int,
    amount: Int,
) {
    val def = WeavingData.weavingDefinitions[item] ?: return
    if (def == WeavingData.MILESTONE_CAPES) {
        val capes =
            intArrayOf(
                Items.MILESTONE_CAPE_10,
                Items.MILESTONE_CAPE_20,
                Items.MILESTONE_CAPE_30,
                Items.MILESTONE_CAPE_40,
                Items.MILESTONE_CAPE_50,
                Items.MILESTONE_CAPE_60,
                Items.MILESTONE_CAPE_70,
                Items.MILESTONE_CAPE_80,
                Items.MILESTONE_CAPE_90,
            )
        val resources =
            capes
                .map {
                    val name =
                        player.world.definitions
                            .get(ItemDef::class.java, it)
                            .name
                    val amountNeeded = WeavingAction.getBallsAmount(name)
                    "($amountNeeded ball${if (amountNeeded > 1) "s" else ""} of wool)"
                }.toTypedArray()
        player.queue {
            produceItemBox(*capes, option = SkillDialogueOption.MAKE, extraNames = resources, logic = ::weaveCape)
        }
    } else {
        player.queue { WeavingAction.weave(this, def, amount) }
    }
}

fun weaveCape(
    player: Player,
    item: Int,
    amount: Int,
) {
    player.queue { WeavingAction.weaveMilestoneCape(this, item, amount) }
}
