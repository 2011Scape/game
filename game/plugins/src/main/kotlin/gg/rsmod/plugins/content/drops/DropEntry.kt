package gg.rsmod.plugins.content.drops

import gg.rsmod.game.model.item.Item

/**
 * Represents a possible drop entry.
 */
sealed class DropEntry {
    /**
     * A drop that will contain nothing.
     */
    object NothingDrop : DropEntry()

    /**
     * A drop that will contain an item.
     */
    class ItemDrop(
        val item: Item,
    ) : DropEntry()

    /**
     * A drop that will recurse into a table.
     */
    class TableDrop(
        val table: DropTable,
    ) : DropEntry()

    /**
     * A drop that contains multiple items.
     */
    class MultiDrop(
        vararg val items: Item,
    ) : DropEntry() {
        fun getDrop(): List<Item> = items.toList()
    }

    /**
     * A drop that will contain an item. The quantity is within a range
     */
    class ItemRangeDrop(
        val item: Item,
        val quantityRange: IntRange,
    ) : DropEntry() {
        fun getDrop() = Item(item, quantityRange.random())
    }
}
