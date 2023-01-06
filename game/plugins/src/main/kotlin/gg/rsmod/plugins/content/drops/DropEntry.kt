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
    class ItemDrop(val item: Item): DropEntry()

    /**
     * A drop that will recurse into a table.
     */
    class TableDrop(val table: DropTable): DropEntry()
}
