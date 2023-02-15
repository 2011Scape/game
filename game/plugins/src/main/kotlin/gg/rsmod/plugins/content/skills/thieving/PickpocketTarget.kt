package gg.rsmod.plugins.content.skills.thieving

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
    ),
    FemaleHamMember(
        objectIds = listOf(Npcs.HAM_MEMBER_1715),
        level = 15,
        minChance = 135,
        maxChance = 240,
        xp = 18.5,
        drops = DropTableFactory.build {
            main {
                total(1100)
                obj(Items.BRONZE_ARROW, quantityRange = 1..13, slots = 33)
                obj(Items.BRONZE_HATCHET, slots = 33)
                obj(Items.BRONZE_DAGGER, slots = 33)
                obj(Items.BRONZE_PICKAXE, slots = 33)
                obj(Items.IRON_HATCHET, slots = 33)
                obj(Items.IRON_DAGGER, slots = 33)
                obj(Items.IRON_PICKAXE, slots = 33)
                obj(Items.LEATHER_BODY, slots = 33)
                obj(Items.STEEL_ARROW, quantityRange = 1..13, slots = 22)
                obj(Items.STEEL_HATCHET, slots = 22)
                obj(Items.STEEL_DAGGER, slots = 22)
                obj(Items.STEEL_PICKAXE, slots = 22)
                obj(Items.HAM_BOOTS, slots = 11)
                obj(Items.HAM_CLOAK, slots = 11)
                obj(Items.HAM_GLOVES, slots = 11)
                obj(Items.HAM_HOOD, slots = 11)
                obj(Items.HAM_LOGO, slots = 11)
                obj(Items.HAM_ROBE, slots = 11)
                obj(Items.HAM_SHIRT, slots = 11)
                obj(Items.COINS_995, quantityRange = 1..21, slots = 165)
                obj(Items.BUTTONS, slots = 44)
                obj(Items.DAMAGED_ARMOUR, slots = 44)
                obj(Items.RUSTY_SWORD, slots = 44)
                obj(Items.FEATHER, quantityRange = 1..7, slots = 33)
                obj(Items.LOGS, slots = 33)
                obj(Items.THREAD, quantityRange = 1..10, slots = 33)
                obj(Items.COWHIDE, slots = 33)
                obj(Items.KNIFE, slots = 22)
                obj(Items.NEEDLE, slots = 22)
                obj(Items.RAW_ANCHOVIES, slots = 22)
                obj(Items.RAW_CHICKEN, slots = 22)
                obj(Items.TINDERBOX, slots = 22)
                obj(Items.UNCUT_OPAL, slots = 22)
                obj(Items.CLUE_SCROLL_EASY, slots = 22)
                obj(Items.COAL, slots = 22)
                obj(Items.IRON_ORE, slots = 22)
                obj(Items.UNCUT_JADE, slots = 22)
                obj(Items.GRIMY_GUAM, slots = 12)
                obj(Items.GRIMY_MARRENTILL, slots = 6)
                obj(Items.GRIMY_TARROMIN, slots = 4)
            }
        },
        damage = 10..30,
        stunnedTicks = 7
    ),
    MaleHamMember(
        objectIds = listOf(Npcs.HAM_MEMBER),
        level = 20,
        minChance = 117,
        maxChance = 240,
        xp = 22.5,
        drops = DropTableFactory.build {
            main {
                total(1100)
                obj(Items.BRONZE_ARROW, quantityRange = 1..13, slots = 33)
                obj(Items.BRONZE_HATCHET, slots = 33)
                obj(Items.BRONZE_DAGGER, slots = 33)
                obj(Items.BRONZE_PICKAXE, slots = 33)
                obj(Items.IRON_HATCHET, slots = 33)
                obj(Items.IRON_DAGGER, slots = 33)
                obj(Items.IRON_PICKAXE, slots = 33)
                obj(Items.LEATHER_BODY, slots = 33)
                obj(Items.STEEL_ARROW, quantityRange = 1..13, slots = 22)
                obj(Items.STEEL_HATCHET, slots = 22)
                obj(Items.STEEL_DAGGER, slots = 22)
                obj(Items.STEEL_PICKAXE, slots = 22)
                obj(Items.HAM_BOOTS, slots = 11)
                obj(Items.HAM_CLOAK, slots = 11)
                obj(Items.HAM_GLOVES, slots = 11)
                obj(Items.HAM_HOOD, slots = 11)
                obj(Items.HAM_LOGO, slots = 11)
                obj(Items.HAM_ROBE, slots = 11)
                obj(Items.HAM_SHIRT, slots = 11)
                obj(Items.COINS_995, quantityRange = 1..21, slots = 165)
                obj(Items.BUTTONS, slots = 44)
                obj(Items.DAMAGED_ARMOUR, slots = 44)
                obj(Items.RUSTY_SWORD, slots = 44)
                obj(Items.FEATHER, quantityRange = 1..7, slots = 33)
                obj(Items.LOGS, slots = 33)
                obj(Items.THREAD, quantityRange = 1..10, slots = 33)
                obj(Items.COWHIDE, slots = 33)
                obj(Items.KNIFE, slots = 22)
                obj(Items.NEEDLE, slots = 22)
                obj(Items.RAW_ANCHOVIES, slots = 22)
                obj(Items.RAW_CHICKEN, slots = 22)
                obj(Items.TINDERBOX, slots = 22)
                obj(Items.UNCUT_OPAL, slots = 22)
                obj(Items.CLUE_SCROLL_EASY, slots = 22)
                obj(Items.COAL, slots = 22)
                obj(Items.IRON_ORE, slots = 22)
                obj(Items.UNCUT_JADE, slots = 22)
                obj(Items.GRIMY_GUAM, slots = 12)
                obj(Items.GRIMY_MARRENTILL, slots = 6)
                obj(Items.GRIMY_TARROMIN, slots = 4)
            }
        },
        damage = 10..30,
        stunnedTicks = 7
    ),
    Warrior(
        objectIds = listOf(Npcs.WARRIOR, Npcs.WARRIOR_WOMAN, Npcs.ALKHARID_WARRIOR),
        level = 25,
        minChance = 100,
        maxChance = 240,
        xp = 26.0,
        drops = DropTableFactory.build {
            guaranteed {
                obj(Items.COINS_995, quantity = 18)
            }
        },
        damage = 20..20,
        stunnedTicks = 8
    ),
    Rogue(
        objectIds = listOf(Npcs.ROGUE, Npcs.ROGUE_GUARD, Npcs.ROGUE_GUARD_2268, Npcs.ROGUE_GUARD_2269, Npcs.ROGUE_8122),
        level = 32,
        minChance = 74,
        maxChance = 240,
        xp = 36.5,
        drops = DropTableFactory.build {
            main {
                total(128)
                obj(Items.COINS_995, quantityRange = 25..40, slots = 108)
                obj(Items.IRON_DAGGER_P, slots = 1)
                obj(Items.AIR_RUNE, quantity = 8, slots = 8)
                obj(Items.JUG_OF_WINE, slots = 6)
                obj(Items.LOCKPICK, slots = 5)
            }
        },
        damage = 20..20,
        stunnedTicks = 8
    ),;

    fun rollDamage() = damage.random()
    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
    fun hasInventorySpace(player: Player): Boolean {
        return DropTableFactory.hasInventorySpaceForAnyDrop(player, objectIds.first(), DropTableType.PICKPOCKET) ?: false
    }
}
