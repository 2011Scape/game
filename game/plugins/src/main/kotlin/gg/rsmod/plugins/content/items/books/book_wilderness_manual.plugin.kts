package gg.rsmod.plugins.content.items.books

import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER

val wildernessManual =
    Book(
        title = "Wilderness Manual",
        pages =
            listOf(
                BookPage(
                    leftLines =
                        listOf(
                            "In the north-east of the",
                            "mainland is a region where",
                            "players may fight other",
                            "players. This land is known",
                            "as the Wilderness.",
                            "",
                            "At the sourthern edge of the",
                            "Wilderness, you may only",
                            "fight players whose combat",
                            "level is close to your own. As",
                            "you go further north, you",
                            "become able to fight players",
                            "with a wider range of combat",
                            "levels. This means that you",
                            "might fight yourself attacked",
                        ),
                    rightLines =
                        listOf(
                            "by players who are",
                            "considerably stronger than",
                            "you, so watch out!",
                            "",
                            "If you get killed, you will",
                            "drop everything except your",
                            "three most valuable items.",
                            "However, if you attack",
                            "another player who hasn't",
                            "recently attacked you, you",
                            "may be marked with a ${red("skull")}",
                            "for 20 minutes; you will drop",
                            "ALL your items if you die",
                            "while you are ${red("skulled")}.",
                            "Anything you drop on death",
                        ),
                ),
                BookPage(
                    leftLines =
                        listOf(
                            "will normally be awarded to",
                            "the player who did the most",
                            "damage against you; your",
                            "gravestone will not appear in",
                            "the Wilderness at all.",
                            "",
                            "The Protect Item prayer",
                            "allows you to keep one extra",
                            "item on death, so you will",
                            "keep one item if you use the",
                            "prayer while you are skulled.",
                            "",
                            "If you drop items on the",
                            "floor in the Wilderness, other",
                            "players may be able to pick",
                        ),
                    rightLines =
                        listOf(
                            "them up immediately, so don't",
                            "drop anything you want to",
                            "keep.",
                            "",
                            "In addition some teleport",
                            "spells will not work deep in",
                            "the wilderness. To escape",
                            "you will need to run south",
                            "or fight your way out!",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                        ),
                )
            ),
    )

on_item_option(Items.WILDERNESS_MANUAL, "Read") {
    player.queue {
        if (!player.timers.has(ACTIVE_COMBAT_TIMER) && player.interfaces.currentModal == -1) {
            player.openBook(wildernessManual)
        }
    }
}

fun red(text: String) = "<col=8A0808>$text</col>"
