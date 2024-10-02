package gg.rsmod.plugins.content.skills.farming.data.npcs

import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.content.skills.farming.data.Patch

enum class Farmer(
    val id: Int,
    val protectionOptions: List<ProtectionOption>,
) {
    Dantaera(
        Npcs.DANTAERA,
        listOf(
            ProtectionOption("pay (north)", "The northern allotment.", Patch.CatherbyAllotmentNorth),
            ProtectionOption("pay (south)", "The southern allotment.", Patch.CatherbyAllotmentSouth),
        ),
    ),
    Lyra(
        Npcs.LYRA,
        listOf(
            ProtectionOption("pay (north-west)", "The north-western allotment.", Patch.MorytaniaAllotmentNorth),
            ProtectionOption("pay (south-east)", "The south-eastern allotment.", Patch.MorytaniaAllotmentSouth),
        ),
    ),
    Elstan(
        Npcs.ELSTAN,
        listOf(
            ProtectionOption("pay (north-west)", "The north-western allotment.", Patch.FaladorAllotmentNorth),
            ProtectionOption("pay (south-east)", "The south-eastern allotment.", Patch.FaladorAllotmentSouth),
        ),
    ),
    Kragen(
        Npcs.KRAGEN,
        listOf(
            ProtectionOption("pay (north)", "The northern allotment.", Patch.ArdougneAllotmentNorth),
            ProtectionOption("pay (south)", "The southern allotment.", Patch.ArdougneAllotmentSouth),
        ),
    ),
    Selena(Npcs.SELENA, listOf(ProtectionOption("pay", "", Patch.YanilleHops))),
    Francis(Npcs.FRANCIS, listOf(ProtectionOption("pay", "", Patch.EntranaHops))),
    Vasquen(Npcs.VASQUEN, listOf(ProtectionOption("pay", "", Patch.LumbridgeHops))),
    Rhonen(Npcs.RHONEN, listOf(ProtectionOption("pay", "", Patch.SeersHops))),
    Dreven(Npcs.DREVEN, listOf(ProtectionOption("pay", "", Patch.VarrockBush))),
    Taria(Npcs.TARIA, listOf(ProtectionOption("pay", "", Patch.RimmingtonBush))),
    Torrell(Npcs.TORRELL, listOf(ProtectionOption("pay", "", Patch.ArdougneBush))),
    Rhazien(Npcs.RHAZIEN, listOf(ProtectionOption("pay", "", Patch.EtceteriaBush))),
    Bolongo(Npcs.BOLONGO, listOf(ProtectionOption("pay", "", Patch.GnomeStrongholdFruitTree))),
    Gileth(Npcs.GILETH, listOf(ProtectionOption("pay", "", Patch.GnomeVillageFruitTree))),
    Garth(Npcs.GARTH, listOf(ProtectionOption("pay", "", Patch.KaramjaFruitTree))),
    Ellena(Npcs.ELLENA, listOf(ProtectionOption("pay", "", Patch.CatherbyFruitTree))),
    Imiago(Npcs.IMIAGO, listOf(ProtectionOption("pay", "", Patch.Calquat))),
    Fayeth(Npcs.FAYETH, listOf(ProtectionOption("pay", "", Patch.LumbridgeTree))),
    Treznor(Npcs.TREZNOR, listOf(ProtectionOption("pay", "", Patch.VarrockTree))),
    Heskel(Npcs.HESKEL, listOf(ProtectionOption("pay", "", Patch.FaladorTree))),
    Alain(Npcs.ALAIN, listOf(ProtectionOption("pay", "", Patch.TaverleyTree))),
    PrissyScilla(Npcs.PRISSY_SCILLA, listOf(ProtectionOption("pay", "", Patch.GnomeTree))),
}

data class ProtectionOption(
    val option: String,
    val description: String,
    val patch: Patch,
)
