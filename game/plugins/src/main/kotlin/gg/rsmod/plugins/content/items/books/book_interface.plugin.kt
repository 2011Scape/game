package gg.rsmod.plugins.content.items.books

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.plugins.api.*
import gg.rsmod.plugins.api.ext.*

val CURR_BOOK = AttributeKey<Book>("currBook")

data class Book(
    val title: String,
    val pages: List<BookPage>,
) {
    private val INTERFACE = 960
    private val LEFT_COMPONENTS = intArrayOf(49, 56, 61, 62, 54, 63, 55, 51, 60, 58, 53, 50, 57, 59, 52)
    private val RIGHT_COMPONENTS = intArrayOf(33, 39, 36, 44, 37, 46, 40, 42, 34, 35, 38, 43, 47, 45, 41)

    private var player: Player? = null
    private var page = 0

    fun open(player: Player) {
        this.player = player
        player.attr.put(CURR_BOOK, this)
        player.closeInterface(INTERFACE)
        player.attr.remove(CURR_BOOK)
        player.setComponentText(INTERFACE, 69, title)
        page = 0
        update()
    }

    fun update() {
        player?.openInterface(INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
        if (page == 0) {
            player?.setComponentHidden(INTERFACE, 72, true)
            player?.setComponentText(INTERFACE, 70, "")
        } else {
            player?.setComponentHidden(INTERFACE, 72, false)
            player?.setComponentText(INTERFACE, 70, "Prev")
        }
        if (page >= pages.size - 1) {
            player?.setComponentHidden(INTERFACE, 73, true)
            player?.setComponentText(INTERFACE, 71, "")
        } else {
            player?.setComponentHidden(INTERFACE, 73, false)
            player?.setComponentText(INTERFACE, 71, "Next")
        }
        for (line in LEFT_COMPONENTS.indices) {
            player?.setComponentText(INTERFACE, LEFT_COMPONENTS[line], pages[page].getLeftLine(line))
            player?.setComponentText(INTERFACE, RIGHT_COMPONENTS[line], pages[page].getRightLine(line))
        }
    }

    fun nextPage() {
        if (page >= pages.size - 1) return
        page++
        update()
    }

    fun prevPage() {
        if (page <= 0) return
        page--
        update()
    }
}

data class BookPage(
    val leftLines: List<String>,
    val rightLines: List<String>,
) {
    fun getLeftLine(line: Int): String {
        return if (line < leftLines.size) leftLines[line] else ""
    }

    fun getRightLine(line: Int): String {
        return if (line < rightLines.size) rightLines[line] else ""
    }
}

fun Player.openBook(book: Book) {
    book.open(this)
}

class BookInterface(
    val plugin: KotlinPlugin,
) {
    fun registerBookInterface() {
        val INTERFACE = 960
        plugin.on_interface_open(INTERFACE) {
            player.attr.get<Book>(CURR_BOOK)?.update()
        }

        plugin.on_interface_close(INTERFACE) {
            player.attr.remove(CURR_BOOK)
        }

        plugin.on_button(INTERFACE, 72) {
            player.attr.get<Book>(CURR_BOOK)?.prevPage()
        }

        plugin.on_button(INTERFACE, 73) {
            player.attr.get<Book>(CURR_BOOK)?.nextPage()
        }
    }
}
