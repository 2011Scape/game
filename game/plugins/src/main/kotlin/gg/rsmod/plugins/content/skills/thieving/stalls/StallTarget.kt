package gg.rsmod.plugins.content.skills.thieving.stalls

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.content.drops.DropTableBuilder
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

enum class StallTarget(
    val fullAndEmptyObjectIds: Map<Int, Int>,
    val level: Int,
    val xp: Double,
    val drops: DropTableBuilder.() -> Unit,
    val respawnTicks: Int,
    val message: String,
) {
    VegetableStall(
        fullAndEmptyObjectIds = mapOf(Objs.VEG_STALL to Objs.MARKET_STALL, Objs.VEG_STALL_4708 to Objs.MARKET_STALL),
        level = 2,
        xp = 10.0,
        drops = DropTableFactory.build {
            main {
                total(20)
                obj(Items.POTATO, slots = 6)
                obj(Items.CABBAGE, slots = 4)
                obj(Items.ONION, slots = 4)
                obj(Items.TOMATO, slots = 4)
                obj(Items.GARLIC, slots = 2)
            }
        },
        respawnTicks = 3,
        message = "You steal vegetables from the vegetable stall."
    ),
    BakerStall(
        fullAndEmptyObjectIds = mapOf(Objs.BAKERS_STALL to Objs.MARKET_STALL_34381, Objs.BAKERY_STALL to Objs.MARKET_STALL_6984),
        level = 5,
        xp = 16.0,
        drops = DropTableFactory.build {
            main {
                total(20)
                obj(Items.CAKE, slots = 13)
                obj(Items.BREAD, slots = 5)
                obj(Items.CHOCOLATE_SLICE, slots = 2)
            }
        },
        respawnTicks = 3,
        message = "You steal pastries from the vegetable baker stall."
    ),
    CraftingStall(
        fullAndEmptyObjectIds = mapOf(Objs.CRAFTING_STALL to Objs.BAMBOO_DESK, Objs.CRAFTING_STALL_6166 to Objs.MARKET_STALL_6984),
        level = 5,
        xp = 16.0,
        drops = DropTableFactory.build {
            main {
                total(3)
                obj(Items.CHISEL)
                obj(Items.NECKLACE_MOULD)
                obj(Items.RING_MOULD)
            }
        },
        respawnTicks = 12,
        message = "You steal crafting supplies from the vegetable crafting stall."
    ),
    MonkeyFoodStall(
        fullAndEmptyObjectIds = mapOf(Objs.FOOD_STALL to Objs.BAMBOO_DESK),
        level = 5,
        xp = 16.0,
        drops = DropTableFactory.build {
            main {
                total(10)
                obj(Items.BANANA, slots = 7)
                obj(Items.MONKEY_NUTS)
                obj(Items.BANANA_STEW)
                obj(Items.MONKEY_BAR)
            }
        },
        respawnTicks = 12,
        message = "You steal food from the food stall."
    );

    fun hasInventorySpace(player: Player): Boolean {
        return DropTableFactory.hasInventorySpaceForAnyDrop(player, fullAndEmptyObjectIds.toList().first().first, DropTableType.STALL)
            ?: false
    }

    fun getEmpty(id: Int) = fullAndEmptyObjectIds[id]
}
