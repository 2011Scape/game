package gg.rsmod.plugins.content.skills.farming.data.npcs

import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.content.skills.farming.data.Patch

enum class Farmer(val id: Int, val protectionOptions: List<ProtectionOption>) {
    Dantaera(Npcs.DANTAERA, listOf(ProtectionOption("pay (north)", "The northern allotment.", Patch.CatherbyAllotmentNorth), ProtectionOption("pay (south)", "The southern allotment.", Patch.CatherbyAllotmentSouth))),
    Lyra(Npcs.LYRA, listOf(ProtectionOption("pay (north-west)", "The north-western allotment.", Patch.MorytaniaAllotmentNorth), ProtectionOption("pay (south-east)", "The south-eastern allotment.", Patch.MorytaniaAllotmentSouth))),
    Elstan(Npcs.ELSTAN, listOf(ProtectionOption("pay (north-west)", "The north-western allotment.", Patch.FaladorAllotmentNorth), ProtectionOption("pay (south-east)", "The south-eastern allotment.", Patch.FaladorAllotmentSouth))),
    Kragen(Npcs.KRAGEN, listOf(ProtectionOption("pay (north)", "The northern allotment.", Patch.ArdougneAllotmentNorth), ProtectionOption("pay (south)", "The southern allotment.", Patch.ArdougneAllotmentSouth))),
}

data class ProtectionOption(
        val option: String,
        val description: String,
        val patch: Patch,
)
