package gg.rsmod.plugins.content.skills.thieving.pickpocketing

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.content.drops.DropTableBuilder
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

enum class PickpocketTarget(
    val objectIds: List<Int>,
    val level: Int,
    private val minChance: Int,
    private val maxChance: Int,
    val xp: Double,
    val drops: DropTableBuilder.() -> Unit,
    val damage: IntRange,
    val stunnedTicks: Int,
) {
    ManWoman(
        objectIds = listOf(Npcs.MAN, Npcs.MAN_2, Npcs.MAN_3, Npcs.MAN_24, Npcs.WOMAN, Npcs.WOMAN_5),
        level = 1,
        minChance = 180,
        maxChance = 240,
        xp = 8.0,
        drops = DropTableFactory.build {
            guaranteed {
                obj(Items.COINS_995, quantity = 3)
            }
        },
        damage = 10..10,
        stunnedTicks = 8
    ),
    Farmer(
        objectIds = listOf(Npcs.FARMER),
        level = 10,
        minChance = 150,
        maxChance = 240,
        xp = 14.5,
        drops = DropTableFactory.build {
            guaranteed {
                obj(Items.COINS_995, quantity = 3)
            }

            main {
                total(256)
                obj(Items.COINS_995, quantity = 9, slots = 246)
                obj(Items.POTATO_SEED, slots = 10)
            }
        },
        damage = 10..10,
        stunnedTicks = 8
    );

    fun rollDamage() = damage.random()
    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
    fun hasInventorySpace(player: Player): Boolean {
        return DropTableFactory.hasInventorySpaceForAnyDrop(player, objectIds.first(), DropTableType.PICKPOCKET) ?: false
    }
}
