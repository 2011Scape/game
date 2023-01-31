package gg.rsmod.plugins.content.drops

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import java.security.SecureRandom
import java.util.*
import kotlin.collections.set

/**
 * The name of a table that always drops all of its items
 */
const val GUARANTEED_TABLE_NAME = "guaranteed"

/**
 * A utility class that assists with building dynamic drop tables.
 */
object DropTableFactory {

    /**
     * The drop tables for each npc.
     */
    private val tables = HashMap<Int, DropTableBuilder.() -> Unit>()

    /**
     * The PRNG for selecting an entry.
     */
    private val prng = SecureRandom()

    /**
     * Registers a drop table.
     * @param init  The builder instance.
     */
    fun build(init: DropTableBuilder.() -> Unit): DropTableBuilder.() -> Unit {
        return init
    }

    /**
     * Registers a drop table for an npc.
     * @param table The drop table.
     * @param npcs  The list of npc ids.
     */
    fun register(table: DropTableBuilder.() -> Unit, vararg ids: Int) {
        ids.forEach { tables[it] = table }
    }

    /**
     * Gets a drop for a player killing an NPC.
     */
    fun getDrop(world: World, player: Player, npcId: Int, tile: Tile) {
        val items = mutableListOf<Item>()

        val bldr = tables[npcId] ?: return
        try {
            val table = DropTableBuilder(player, prng).apply(bldr)

            val tables = table.tables.entries.map { it.value }

            val guaranteed = tables.firstOrNull { it.name == GUARANTEED_TABLE_NAME }
            val remaining = tables.filterNot { it.name == GUARANTEED_TABLE_NAME }
            if (guaranteed != null) {
                items.addAll(guaranteed.entries.filterIsInstance<DropEntry.ItemDrop>().map { it.item })
            }

            val remainingTables = remaining.map { DropEntry.TableDrop(it) }
            items.addAll(remainingTables.mapNotNull { it.getDrop() })
            items.forEach { createDrop(world, it, tile, player) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Creates a drop but only returns the values
     */
    fun getDrop(player: Player, tableId: Int) : MutableList<Item>? {
        val items = mutableListOf<Item>()

        val bldr = tables[tableId] ?: return null
        try {
            val table = DropTableBuilder(player, prng).apply(bldr)

            val tables = table.tables.entries.map { it.value }

            val guaranteed = tables.firstOrNull { it.name == GUARANTEED_TABLE_NAME }
            val remaining = tables.filterNot { it.name == GUARANTEED_TABLE_NAME }
            if (guaranteed != null) {
                items.addAll(guaranteed.entries.filterIsInstance<DropEntry.ItemDrop>().map { it.item })
            }

            val remainingTables = remaining.map { DropEntry.TableDrop(it) }
            items.addAll(remainingTables.mapNotNull { it.getDrop() })
            return items
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Gets a drop from a table and adds to the players inventory
     */
    fun createDropInventory(player: Player, tableId: Int) : MutableList<Item>? {
        val items = mutableListOf<Item>()

        val bldr = tables[tableId] ?: return null
        try {
            val table = DropTableBuilder(player, prng).apply(bldr)

            val tables = table.tables.entries.map { it.value }

            val guaranteed = tables.firstOrNull { it.name == GUARANTEED_TABLE_NAME }
            val remaining = tables.filterNot { it.name == GUARANTEED_TABLE_NAME }
            if (guaranteed != null) {
                items.addAll(guaranteed.entries.filterIsInstance<DropEntry.ItemDrop>().map { it.item })
            }

            val remainingTables = remaining.map { DropEntry.TableDrop(it) }
            items.addAll(remainingTables.mapNotNull { it.getDrop() })
            items.forEach { player.inventory.add(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Gets a drop from a table. This operates recursively on
     * nested tables.
     */
    private fun DropEntry.TableDrop.getDrop(): Item? {
        val entries = table.entries
        val idx = prng.nextInt(entries.size)

        return when (val drop = entries[idx]) {
            is DropEntry.NothingDrop -> null
            is DropEntry.ItemDrop -> drop.item
            is DropEntry.TableDrop -> drop.getDrop()
        }
    }

    /**
     * Creates a dropped item.
     *
     * @param item   The item to drop.
     * @param player The player getting the loot (or null).
     * @param l      The location of the NPC dropping the loot.
     */
    private fun createDrop(world: World, item: Item, tile: Tile, owner: Pawn) {
        val ground = GroundItem(item.id, item.amount, tile, owner as Player)
        world.spawn(ground)
    }

}

/**
 * Marks a builder.
 */
@DslMarker
private annotation class BuilderDslMarker

/**
 * A DSL that assists in creating drop tables.
 * @param player    The player who killed the NPC.
 * @param npc       The NPC instance.
 * @param prng      The PRNG instance.
 */
@BuilderDslMarker
class DropTableBuilder(val player: Player, private val prng: SecureRandom) {
    /**
     * The tables that have been constructed.
     */
    val tables = hashMapOf<String, DropTable>()

    /**
     * Builds a table where items inside will *always* be dropped.
     */
    fun guaranteed(builder: TableBuilder.() -> Unit) = table(GUARANTEED_TABLE_NAME, builder)

    /**
     * A helper function for building a singular table.
     */
    fun main(builder: TableBuilder.() -> Unit) = table("main", builder)

    /**
     * Builds a table with a specified name.
     * @param name  The name of the table.
     */
    fun table(name: String, builder: TableBuilder.() -> Unit) {
        val bldr = TableBuilder(player, prng, name).apply(builder)
        val table = bldr.build()
        tables[name.lowercase()] = table
    }

    /**
     * Builds the drop tables.
     */
    internal fun build(): List<DropTable> {
        return tables.values.toList()
    }
}

/**
 * A DSL that assists in creating drop tables.
 * @param player    The player who killed the NPC.
 * @param npc       The NPC instance.
 * @param prng      The PRNG instance.
 * @param name      The name of the table.
 */
@BuilderDslMarker
class TableBuilder(val player: Player, val prng: SecureRandom, val name: String? = null) {
    /**
     * The total number of slots.
     */
    private var totalSlots = if (name == GUARANTEED_TABLE_NAME) 128 else 0

    /**
     * The number of occupied slots.
     */
    private var occupiedSlots = 0

    /**
     * The drop entries for this table.
     */
    private var entries = Array<DropEntry>(totalSlots) { DropEntry.NothingDrop }

    /**
     * Specifies the total number of slots for this drop table.
     * @param total The total number of slots.
     */
    fun total(total: Int) {
        totalSlots = total
        entries = Array(total * 2) { DropEntry.NothingDrop }
    }

    /**
     * Adds an item to be dropped.
     * @param name      The name of the item.
     * @param quantity  The quantity of the item to drop.
     * @param slots     The number of slots this drop should occupy in the table.
     */
    fun obj(id: Int, quantity: Int = 1, slots: Int = 1) {
        val item = Item(id, quantity)

        repeat(slots) {
            entries[occupiedSlots++] = DropEntry.ItemDrop(item)
        }
    }

    /**
     * Adds a table to be dropped.
     * @param table The table.
     * @param slots The number of slots this table occupies.
     */
    fun table(table: DropTableBuilder.() -> Unit, slots: Int = 1) {
        val tab = DropTableBuilder(player, prng)
            .apply(table)
            .build()
            .first()
        repeat(slots) {
            entries[occupiedSlots++] = DropEntry.TableDrop(tab)
        }
    }

    /**
     * Adds a chance to drop nothing.
     * @param slots The number of slots.
     */
    fun nothing(slots: Int) {
        repeat(slots) {
            entries[occupiedSlots++] = DropEntry.NothingDrop
        }
    }

    /**
     * Builds this drop table.
     */
    internal fun build(): DropTable {
        if (occupiedSlots != totalSlots && name != GUARANTEED_TABLE_NAME) {
            println("Drop table has $totalSlots total slots, but $occupiedSlots were used.")
        }

        return DropTable(name, entries.copyOfRange(0, occupiedSlots))
    }
}
