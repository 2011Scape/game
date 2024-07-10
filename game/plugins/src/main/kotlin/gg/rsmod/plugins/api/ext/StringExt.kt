package gg.rsmod.plugins.api.ext

private const val vowels = "aeiou"

fun String.pluralPrefix(amount: Int): String {
    return if (amount > 1) "are $this" else "is $this"
}

fun String.pluralSuffix(amount: Int): String {
    if (endsWith('s', ignoreCase = true)) {
        return this
    }
    return if (amount != 1) this + "s" else this
}

fun String.withPluralSuffix(
    string: String,
    count: Int,
): String {
    return if (count == 1) {
        string
    } else {
        when (string.last()) {
            'y' -> "${string.dropLast(1)}ies"
            's', 'x', 'z', 'h' -> "${string}es"
            else -> string
        }
    }
}

/**
 * Prefixes the string with either "a" or "an" depending on whether
 * the string starts with a vowel
 */
fun String.prefixAn(): String {
    return if (vowels.indexOf(Character.toLowerCase(this[0])) != -1) "an $this" else "a $this"
}

fun String.formatNumber(): String {
    val regex = "\\d+".toRegex()
    return regex.replace(this) { matchResult ->
        val number = matchResult.value.toLong()
        String.format("%,d", number)
    }
}

fun String.splitForDialogue(): Array<String> {
    val maxLength = 50
    if (this.length <= maxLength) {
        return arrayOf(this)
    }

    val words = this.split(" ")
    val finalResult = mutableListOf<String>()
    var currentLine = ""

    for (word in words) {
        if (currentLine.isBlank()) {
            currentLine = word
        } else if (currentLine.length + word.length <= maxLength) {
            currentLine += " $word"
        } else {
            val trimmedLine = currentLine.trim()
            if (trimmedLine.isNotBlank()) {
                finalResult.add(trimmedLine)
            }
            currentLine = word
        }
    }

    val lastTrimmedLine = currentLine.trim()
    if (lastTrimmedLine.isNotBlank()) {
        finalResult.add(lastTrimmedLine)
    }

    return finalResult.toTypedArray()
}
