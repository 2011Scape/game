package gg.rsmod.plugins.content.items.books

import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER

val barbarianTrainingNotes =
    Book(
        title = "What Otto told me.",
        pages =
            listOf(
                BookPage(
                    leftLines =
                        listOf(
                            "",
                            "I have noted down what Otto",
                            "has told me in this journal, so",
                            "that I may not forget my",
                            "tasks. His instructions are",
                            "thus faithfully recorded for",
                            "posterity.",
                            "",
                            "",
                            "",
                            "<col=0000FF>Otto's words on Firemaking</col>",
                            "",
                            "'The first point in your",
                            "progression is that of lighting",
                            "fires without a tinderbox. For",
                        ),
                    rightLines =
                        listOf(
                            "this process you will require",
                            "a strung bow. You use the",
                            "bow to quickly rotate pieces",
                            "of wood against one another.",
                            "As you rub, the wood",
                            "becomes hot, eventually",
                            "springing into flame. The",
                            "spirits will aid you, the power",
                            "they supply will guide your",
                            "hands. Go and benefit from",
                            "their guidance upon an oaken",
                            "log.'",
                            "",
                            "",
                            "",
                        ),
                ),
                BookPage(
                    leftLines =
                        listOf(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                        ),
                    rightLines =
                        listOf(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                        ),
                ),
            ),
    )

on_item_option(Items.BARBARIAN_SKILLS, option = "read") {
    player.queue {
        if (!player.timers.has(ACTIVE_COMBAT_TIMER) && player.interfaces.currentModal == -1) {
            player.openBook(barbarianTrainingNotes)
        }
    }
}
