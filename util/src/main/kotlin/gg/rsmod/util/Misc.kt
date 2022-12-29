package gg.rsmod.util

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Misc {

    val DIRECTION_DELTA_X = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    val DIRECTION_DELTA_Z = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)

    fun getNpcMoveDirection(dd: Int): Int {
        return if (dd < 0) {
            -1
        } else getNpcMoveDirection(
            DIRECTION_DELTA_X[dd],
            DIRECTION_DELTA_Z[dd]
        )
    }

    private fun getNpcMoveDirection(dx: Int, dy: Int): Int {
        if (dx == 0 && dy > 0) {
            return 0
        }
        if (dx > 0 && dy > 0) {
            return 1
        }
        if (dx > 0 && dy == 0) {
            return 2
        }
        if (dx > 0 && dy < 0) {
            return 3
        }
        if (dx == 0 && dy < 0) {
            return 4
        }
        if (dx < 0 && dy < 0) {
            return 5
        }
        if (dx < 0 && dy == 0) {
            return 6
        }
        return if (dx < 0 && dy > 0) {
            7
        } else -1
    }
    fun getPlayerWalkingDirection(dx: Int, dy: Int): Int {
        if (dx == -1 && dy == -1) {
            return 0
        }
        if (dx == 0 && dy == -1) {
            return 1
        }
        if (dx == 1 && dy == -1) {
            return 2
        }
        if (dx == -1 && dy == 0) {
            return 3
        }
        if (dx == 1 && dy == 0) {
            return 4
        }
        if (dx == -1 && dy == 1) {
            return 5
        }
        if (dx == 0 && dy == 1) {
            return 6
        }
        return if (dx == 1 && dy == 1) {
            7
        } else -1
    }

    fun getPlayerRunningDirection(dx: Int, dy: Int): Int {
        if (dx == -2 && dy == -2)
            return 0
        if (dx == -1 && dy == -2)
            return 1
        if (dx == 0 && dy == -2)
            return 2
        if (dx == 1 && dy == -2)
            return 3
        if (dx == 2 && dy == -2)
            return 4
        if (dx == -2 && dy == -1)
            return 5
        if (dx == 2 && dy == -1)
            return 6
        if (dx == -2 && dy == 0)
            return 7
        if (dx == 2 && dy == 0)
            return 8
        if (dx == -2 && dy == 1)
            return 9
        if (dx == 2 && dy == 1)
            return 10
        if (dx == -2 && dy == 2)
            return 11
        if (dx == -1 && dy == 2)
            return 12
        if (dx == 0 && dy == 2)
            return 13
        if (dx == 1 && dy == 2)
            return 14
        return if (dx == 2 && dy == 2) 15 else -1
    }

    /**
     * Formats the string as display name.
     * @param name The string to format.
     * @return The formatted name.
     */
    fun formatforDisplay(name: String): String? {
        var name = name
        name = name.replace("_".toRegex(), " ")
        name = name.toLowerCase()
        val newName = StringBuilder()
        var wasSpace = true
        for (i in 0 until name.length) {
            if (wasSpace) {
                newName.append((String() + name[i]).toUpperCase())
                wasSpace = false
            } else {
                newName.append(name[i])
            }
            if (name[i] == ' ') {
                wasSpace = true
            }
        }
        return newName.toString()
    }

    /**
     * Formats the string for sentences
     */
    fun formatSentence(str: String): String {
        val buf = str.toLowerCase().toCharArray()
        var endMarker = true
        for (i in buf.indices) {
            val c = buf[i]
            if (endMarker && c >= 'a' && c <= 'z') {
                buf[i] = Character.toUpperCase(c)
                endMarker = false
            }
            if (c == '.' || c == '!' || c == '?') {
                endMarker = true
            }
            if (c == 'i') {
                var next = 0.toChar()
                if (i + 1 < buf.size) {
                    next = buf[i + 1]
                }
                var last = 0.toChar()
                if (i - 1 > 0) {
                    last = buf[i - 1]
                }
                if (last == ' ' && (next == ' ' || next == '\'' || next.toInt() == 0)) {
                    buf[i] = Character.toUpperCase(c);
                }
            }
        }
        return String(buf, 0, buf.size)
    }

}