package gg.rsmod.plugins.content.drops

/**
 * Represents a drop table.
 * @param name      The name of this table.
 * @param entries   The drop entries.
 */
data class DropTable(val name: String?, val entries: Array<DropEntry>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DropTable

        if (name != other.name) return false
        if (!entries.contentEquals(other.entries)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + entries.contentHashCode()
        return result
    }
}
