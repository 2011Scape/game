package gg.rsmod.plugins.content.skills.farming.data

enum class CompostBinState(
    val varbits: IntRange,
) {
    Empty(0..0),
    FillingCompost(1..15),
    EmptyingCompost(16..30),
    ClosedReadyCompost(31..31),
    ClosedReadySupercompost(32..32),
    FillingSuperCompost(33..47),
    EmptyingSuperCompost(48..62),
    ClosedFermentingCompost(64..68),
    ClosedFermentingSupercompost(74..78),
    ;

    companion object {
        fun forVarbit(varbit: Int): CompostBinState {
            return values().single { varbit in it.varbits }
        }
    }
}
