package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.pluralSuffix

enum class HarvestingTool(val id: Int, val animation: Int, val harvestingSound: Int, val saveChanceMultiplier: Double = 1.0) {
    Secateurs(id = Items.SECATEURS, animation = 7227, harvestingSound = 2437),
    MagicSecateurs(id = Items.MAGIC_SECATEURS, animation = 7228, harvestingSound = 2437, saveChanceMultiplier = 1.1),
    Spade(id = Items.SPADE, animation = 830, harvestingSound = 1470);
}
