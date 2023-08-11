package gg.rsmod.util

object TextWrapping {

    private const val SPACE = ' '
    private const val DEFAULT_LENGTH = 55

    @JvmStatic
    fun wrap(str: String?): Array<String>? {
        return wrap(str, DEFAULT_LENGTH)
    }

    @JvmStatic
    fun wrap(str: String?, wrapLength: Int): Array<String>? {
        var wrapLengthVar = wrapLength

        str ?: return null

        if (wrapLengthVar < 1) {
            wrapLengthVar = 1
        }

        val inputLineLength = str.length
        var offset = 0
        val wrappedLines = mutableListOf<String>()

        while (offset < inputLineLength) {
            val spaceToWrapAt = findSpaceToWrapAt(str, offset, wrapLengthVar)

            if (spaceToWrapAt >= offset) {
                wrappedLines.add(str.substring(offset, spaceToWrapAt))
                offset = spaceToWrapAt + 1
            } else {
                wrappedLines.add(str.substring(offset))
                offset = inputLineLength
            }
        }

        return wrappedLines.toTypedArray()
    }

    private fun findSpaceToWrapAt(str: String, offset: Int, wrapLength: Int): Int {
        val spaceToWrapAt = str.indexOf(SPACE, offset + wrapLength)
        if (spaceToWrapAt == -1) {
            return str.length
        }
        return spaceToWrapAt
    }
}