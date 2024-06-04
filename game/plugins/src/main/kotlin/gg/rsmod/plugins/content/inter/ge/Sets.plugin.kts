package gg.rsmod.plugins.content.inter.ge

import gg.rsmod.game.fs.DefinitionSet

val GE_SETS_INTERFACE = 645
val INV_INTERFACE = 644

val geClerk = arrayOf(Npcs.GRAND_EXCHANGE_CLERK, Npcs.GRAND_EXCHANGE_CLERK_2240, Npcs.GRAND_EXCHANGE_CLERK_2241, Npcs.GRAND_EXCHANGE_CLERK_2593)

geClerk.forEach {
    on_npc_option(npc = it, option = "sets", lineOfSightDistance = 2) {
        openGESets(player)
    }
}

on_interface_close(GE_SETS_INTERFACE) {
    player.closeInterface(interfaceId = INV_INTERFACE)
    player.openInterface(dest = InterfaceDestination.INVENTORY_TAB)
    player.inventory.dirty = true
}

fun openGESets(p: Player) {
    p.openInterface(GE_SETS_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    p.openInterface(INV_INTERFACE, InterfaceDestination.INVENTORY_TAB)
    p.unlockIComponentOptionSlots(GE_SETS_INTERFACE, 16, 0, 115, 0, 1)
    p.runClientScriptReversed(676)
    p.unlockIComponentOptionSlots(INV_INTERFACE, 0, 0, 27, 0, 1)
    p.runClientScript(150, INV_INTERFACE shl 16, 93, 4, 7, 0, -1, "Components", "Exchange")
}

fun setComponents(p: Player, set: Set?) {
    if (set != null) {
        val components = StringBuilder()
        set.items.forEachIndexed { index, itemId ->
            val itemName = getItemName(p.world.definitions, itemId).lowercase()
            components.append(itemName).append(if (index == set.items.size - 1) "" else ", ")
        }
        p.message("This set contains $components.")
    } else {
        p.message("No set found.")
    }
}

fun getItemName(definitions: DefinitionSet, itemId: Int): String {
    return definitions.get(ItemDef::class.java, itemId).name
}

fun unpackSet(player: Player, set: Set?) {
    if (set != null) {
        if (!player.inventory.contains(set.id)) {
            return
        }
        if (player.inventory.freeSlotCount < set.items.size) {
            player.message("You don't have enough space for that.")
            return
        }
        set.items.forEach { itemId ->
            player.inventory.add(itemId, 1)
        }
        player.inventory.remove(set.id, 1)
    }
}

fun handleButton(player: Player) {
    val opcode = player.getInteractingOpcode()
    val itemId = player.getInteractingItemId()
    val set = Set.values().find { it.id == itemId }

    when (opcode) {
        61 -> setComponents(player, set)
        64 -> unpackSet(player, set)
    }
}

on_button(interfaceId = GE_SETS_INTERFACE, component = 0) {
    handleButton(player)
}

on_button(interfaceId = GE_SETS_INTERFACE, component = 16) {
    handleButton(player)
}

on_button(interfaceId = INV_INTERFACE, component = 0) {
    handleButton(player)
}

on_button(interfaceId = INV_INTERFACE, component = 16) {
    handleButton(player)
}



enum class Set(val id: Int, vararg val items: Int) {
    BRONZE_LG(11814, 1155, 1117, 1075, 1189),
    BRONZE_SK(11816, 1155, 1117, 1087, 1189),
    IRON_LG(11818, 1153, 1115, 1067, 1191),
    IRON_SK(11820, 1153, 1115, 1081, 1191),
    STEEL_LG(11822, 1157, 1119, 1069, 1193),
    STEEL_SK(11824, 1157, 1119, 1083, 1193),
    BLACK_LG(11826, 1165, 1125, 1077, 1195),
    BLACK_SK(11828, 1165, 1125, 1089, 1195),
    MITHRIL_LG(11830, 1159, 1121, 1071, 1197),
    MITHRIL_SK(11832, 1159, 1121, 1085, 1197),
    ADAMANT_LG(11834, 1161, 1123, 1073, 1199),
    ADAMANT_SK(11836, 1161, 1123, 1091, 1199),
    PROSELYTE_LG(9666, 9672, 9674, 9676),
    PROSELYTE_SK(9670, 9672, 9674, 9678),
    RUNE_LG(11838, 1163, 1127, 1079, 1201),
    RUNE_SK(11840, 1163, 1127, 1093, 1201),
    DRAG_CHAIN_LG(11842, 1149, 2513, 4087, 1187),
    DRAG_CHAIN_SK(11844, 1149, 2513, 4585, 1187),
    DRAG_PLATE_LG(14529, 11335, 14479, 4087, 1187),
    DRAG_PLATE_SK(14531, 11335, 14479, 4585, 1187),
    BLACK_H1_LG(19520, 10306, 19167, 7332, 19169),
    BLACK_H1_SK(19530, 10306, 19167, 7332, 19171),
    BLACK_H2_LG(19522, 10308, 19188, 7338, 19190),
    BLACK_H2_SK(19532, 10308, 19188, 7338, 19192),
    BLACK_H3_LG(19524, 10310, 19209, 7344, 19211),
    BLACK_H3_SK(19534, 10310, 19209, 7344, 19213),
    BLACK_H4_LG(19526, 10312, 19230, 7350, 19232),
    BLACK_H4_SK(19536, 10312, 19230, 7350, 19234),
    BLACK_H5_LG(19528, 10314, 19251, 7356, 19253),
    BLACK_H5_SK(19538, 10314, 19251, 7356, 19255),
    BLACK_TRIM_LG(11878, 2587, 2583, 2585, 2589),
    BLACK_TRIM_SK(11880, 2587, 2583, 3472, 2589),
    BLACK_GTRIM_LG(11882, 2595, 2591, 2593, 2597),
    BLACK_GTRIM_SK(11884, 2595, 2591, 3473, 2597),
    ADAMANT_H1_LG(19540, 10296, 19173, 7334, 19175),
    ADAMANT_H1_SK(19550, 10296, 19173, 7334, 19177),
    ADAMANT_H2_LG(19542, 10298, 19194, 7340, 19196),
    ADAMANT_H2_SK(19552, 10298, 19194, 7340, 19198),
    ADAMANT_H3_LG(19544, 10300, 19215, 7346, 19217),
    ADAMANT_H3_SK(19554, 10300, 19215, 7346, 19219),
    ADAMANT_H4_LG(19546, 10302, 19236, 7352, 19238),
    ADAMANT_H4_SK(19556, 10302, 19236, 7352, 19240),
    ADAMANT_H5_LG(19548, 10304, 19257, 7358, 19259),
    ADAMANT_H5_SK(19558, 10304, 19257, 7358, 19261),
    ADAMANT_TRIM_LG(11886, 2605, 2599, 2601, 2603),
    ADAMANT_TRIM_SK(11888, 2605, 2599, 3474, 2603),
    ADAMANT_GTRIM_LG(11890, 2613, 2607, 2609, 2611),
    ADAMANT_GTRIM_SK(11892, 2613, 2607, 3475, 2611),
    RUNE_H1_LG(19560, 10286, 19179, 19182, 7336),
    RUNE_H1_SK(19570, 10286, 19179, 19185, 7336),
    RUNE_H2_LG(19562, 10288, 19200, 19203, 7342),
    RUNE_H2_SK(19572, 10288, 19200, 19206, 7342),
    RUNE_H3_LG(19564, 10290, 19221, 19224, 7348),
    RUNE_H3_SK(19574, 10290, 19221, 19227, 7348),
    RUNE_H4_LG(19566, 10292, 19242, 19245, 7354),
    RUNE_H4_SK(19576, 10292, 19242, 19248, 7354),
    RUNE_H5_LG(19568, 10294, 19263, 19266, 7360),
    RUNE_H5_SK(19578, 10294, 19263, 19269, 7360),
    RUNE_TRIM_LG(11894, 2627, 2623, 2625, 2629),
    RUNE_TRIM_SK(11896, 2627, 2623, 3477, 2629),
    RUNE_GTRIM_LG(11898, 2619, 2615, 2617, 2621),
    RUNE_GTRIM_SK(11900, 2619, 2615, 3676, 2621),
    GUTHIX_LG(11926, 2673, 2669, 2671, 2675),
    GUTHIX_SK(11932, 2673, 2669, 3480, 2675),
    SARADOMIN_LG(11928, 2665, 2661, 2663, 2667),
    SARADOMIN_SK(11934, 2665, 2661, 3479, 2667),
    ZAMORAK_LG(11930, 2657, 2653, 2655, 2659),
    ZAMORAK_SK(11936, 2657, 2653, 3478, 2659),
    ROCKSHELL(11942, 6128, 6129, 6130, 6151, 6145),
    ELITEBLACK(14527, 14494, 14492, 14490),
    THIRDAGEMELEE(11858, 10350, 10348, 10352, 10346),
    THIRDAGERANGE(11860, 10334, 10330, 10332, 10336),
    THIRDAGEMAGE(11862, 10342, 10334, 10338, 10340),
    THIRDAGEDRUIDIC(19580, 19308, 19311, 19314, 19317, 19320),
    AHRIM(11846, 4708, 4712, 4714, 4710),
    DHAROK(11848, 4716, 4720, 4722, 4718),
    GUTHAN(11850, 4724, 4728, 4730, 4726),
    KARIL(11852, 4732, 4736, 4738, 4734),
    TORAG(11854, 4745, 4749, 4751, 4747),
    VERAC(11856, 4753, 4757, 4759, 4755),
    AKRISAE(21768, 21736, 21744, 21752, 21760),
    GREEN_DHIDE(11864, 1135, 1099, 1065),
    BLUE_DHIDE(11866, 2499, 2493, 2487),
    RED_DHIDE(11868, 2501, 2495, 2489),
    BLACK_DHIDE(11870, 2503, 2497, 2491),
    ROYAL_DHIDE(24386, 24382, 24379, 24376),
    MYSTIC(11872, 4089, 4091, 4093, 4095, 4097),
    MYSTICD(11962, 4099, 4101, 4103, 4105, 4107),
    MYSTICL(11960, 4109, 4111, 4113, 4115, 4117),
    INFINITY(11874, 6918, 6916, 6924, 6920, 6922),
    SPLITBARK(11876, 3385, 3387, 3389, 3391, 3393),
    ENCHANTED(11902, 7400, 7399, 7398),
    BLUE_ROBE_T(11904, 7396, 7392, 7388),
    BLUE_ROBE_G(11906, 7394, 7390, 7386),
    LEATHER_T(11908, 7364, 7368),
    LEATHER_G(11910, 7362, 7366),
    GREEN_DH_T(11912, 7372, 7380),
    GREEN_DH_G(11914, 7370, 7378),
    BLUE_DH_T(11916, 7376, 7384),
    BLUE_DH_G(11918, 7374, 7382),
    GREEN_BLESS(11920, 10382, 10378, 10380, 10376),
    BLUE_BLESS(11922, 10390, 10386, 10388, 10384),
    RED_BLESS(11924, 10374, 10370, 10372, 10368),
    BROWN_DHIDE(19582, 19457, 19453, 19455, 19451),
    PURPLE_DHIDE(19584, 19449, 19445, 19449, 19443),
    SILVER_DHIDE(19586, 19465, 19461, 19463, 19459),
    ARMA_LG(19588, 19422, 19413, 19416, 19410),
    ARMA_SK(19590, 19422, 19413, 19419, 19410),
    BAND_LG(19592, 19437, 19428, 19431, 19440),
    BAND_SK(19594, 19437, 19428, 19434, 19440),
    ANCI_LG(19596, 19407, 19398, 19401, 19410),
    ANCI_SK(19598, 19407, 19398, 19404, 19410),
    GILDED_LG(11938, 3486, 3481, 3483, 3488),
    GILDED_SK(11940, 3486, 3481, 3485, 3488),
    SPINED(11944, 6131, 6133, 6135, 6143, 6149),
    SKELETAL(11946, 6137, 6139, 6141, 6147, 6153),
    CANNON(11967, 6, 8, 10, 12),
    DAGON_HAI(14525, 14497, 14499, 14501)
}

/**
@PluginEventHandler
object Sets {
    fun open(player: Player) {
        player.getInterfaceManager().sendInterface(645)
        player.getInterfaceManager().sendInventoryInterface(644)
        player.getPackets().setIFRightClickOps(645, 16, 0, 115, 0, 1)
        player.getPackets().sendRunScriptReverse(676)
        player.getPackets().sendInterSetItemsOptionsScript(644, 0, 93, 4, 7, "Components", "Exchange")
        player.getPackets().setIFRightClickOps(644, 0, 0, 27, 0, 1)
    }

    fun unpackSet(player: Player, set: com.rs.game.ge.Sets.Set?) {
        if (set != null) {
            if (!player.inventory.containsItem(set.getId())) return
            if (player.inventory.getFreeSlots() < set.getItems().size) {
                player.sendMessage("You don't have enough space for that.")
                return
            }
            for (itemId in set.getItems()) player.inventory.addItem(Item(itemId, 1))
            player.inventory.deleteItem(set.getId(), 1)
        }
    }

    fun packSet(player: Player, set: com.rs.game.ge.Sets.Set?) {
        if (set != null) {
            for (itemId in set.getItems()) if (!player.inventory.containsItem(itemId)) {
                player.sendMessage(("You need a " + ItemDefinitions.getDefs(itemId).name).toString() + " to exchange this set.")
                return
            }
            for (itemId in set.getItems()) player.inventory.deleteItem(Item(itemId, 1))
            player.inventory.addItem(set.getId(), 1)
        }
    }

    fun printSet(player: Player, set: com.rs.game.ge.Sets.Set?) {
        if (set != null) {
            val components = StringBuilder()
            for (i in set.getItems().indices) components.append(
                ItemDefinitions.getDefs(
                    set.getItems().get(i)
                ).name.toLowerCase()
            ).append(if (i == set.getItems().size - 1) "" else ", ")
            player.sendMessage("This set contains $components.")
        }
    }

    var processItemSets: ButtonClickHandler = ButtonClickHandler(arrayOf<Any>(644, 645)) { e ->
        if (e.getPacket() === ClientPacket.IF_OP1) com.rs.game.ge.Sets.printSet(
            e.getPlayer(),
            com.rs.game.ge.Sets.Set.Companion.forId(e.getSlotId2())
        )
        else if (e.getComponentId() === 16) com.rs.game.ge.Sets.packSet(
            e.getPlayer(),
            com.rs.game.ge.Sets.Set.Companion.forId(e.getSlotId2())
        )
        else com.rs.game.ge.Sets.unpackSet(e.getPlayer(), com.rs.game.ge.Sets.Set.Companion.forId(e.getSlotId2()))
    }

    enum class Set(val id: Int, vararg val items: Int) {
        BRONZE_LG(11814, 1155, 1117, 1075, 1189),
        BRONZE_SK(11816, 1155, 1117, 1087, 1189),
        IRON_LG(11818, 1153, 1115, 1067, 1191),
        IRON_SK(11820, 1153, 1115, 1081, 1191),
        STEEL_LG(11822, 1157, 1119, 1069, 1193),
        STEEL_SK(11824, 1157, 1119, 1083, 1193),
        BLACK_LG(11826, 1165, 1125, 1077, 1195),
        BLACK_SK(11828, 1165, 1125, 1089, 1195),
        MITHRIL_LG(11830, 1159, 1121, 1071, 1197),
        MITHRIL_SK(11832, 1159, 1121, 1085, 1197),
        ADAMANT_LG(11834, 1161, 1123, 1073, 1199),
        ADAMANT_SK(11836, 1161, 1123, 1091, 1199),
        PROSELYTE_LG(9666, 9672, 9674, 9676),
        PROSELYTE_SK(9670, 9672, 9674, 9678),
        RUNE_LG(11838, 1163, 1127, 1079, 1201),
        RUNE_SK(11840, 1163, 1127, 1093, 1201),
        DRAG_CHAIN_LG(11842, 1149, 2513, 4087, 1187),
        DRAG_CHAIN_SK(11844, 1149, 2513, 4585, 1187),
        DRAG_PLATE_LG(14529, 11335, 14479, 4087, 1187),
        DRAG_PLATE_SK(14531, 11335, 14479, 4585, 1187),
        BLACK_H1_LG(19520, 10306, 19167, 7332, 19169),
        BLACK_H1_SK(19530, 10306, 19167, 7332, 19171),
        BLACK_H2_LG(19522, 10308, 19188, 7338, 19190),
        BLACK_H2_SK(19532, 10308, 19188, 7338, 19192),
        BLACK_H3_LG(19524, 10310, 19209, 7344, 19211),
        BLACK_H3_SK(19534, 10310, 19209, 7344, 19213),
        BLACK_H4_LG(19526, 10312, 19230, 7350, 19232),
        BLACK_H4_SK(19536, 10312, 19230, 7350, 19234),
        BLACK_H5_LG(19528, 10314, 19251, 7356, 19253),
        BLACK_H5_SK(19538, 10314, 19251, 7356, 19255),
        BLACK_TRIM_LG(11878, 2587, 2583, 2585, 2589),
        BLACK_TRIM_SK(11880, 2587, 2583, 3472, 2589),
        BLACK_GTRIM_LG(11882, 2595, 2591, 2593, 2597),
        BLACK_GTRIM_SK(11884, 2595, 2591, 3473, 2597),
        ADAMANT_H1_LG(19540, 10296, 19173, 7334, 19175),
        ADAMANT_H1_SK(19550, 10296, 19173, 7334, 19177),
        ADAMANT_H2_LG(19542, 10298, 19194, 7340, 19196),
        ADAMANT_H2_SK(19552, 10298, 19194, 7340, 19198),
        ADAMANT_H3_LG(19544, 10300, 19215, 7346, 19217),
        ADAMANT_H3_SK(19554, 10300, 19215, 7346, 19219),
        ADAMANT_H4_LG(19546, 10302, 19236, 7352, 19238),
        ADAMANT_H4_SK(19556, 10302, 19236, 7352, 19240),
        ADAMANT_H5_LG(19548, 10304, 19257, 7358, 19259),
        ADAMANT_H5_SK(19558, 10304, 19257, 7358, 19261),
        ADAMANT_TRIM_LG(11886, 2605, 2599, 2601, 2603),
        ADAMANT_TRIM_SK(11888, 2605, 2599, 3474, 2603),
        ADAMANT_GTRIM_LG(11890, 2613, 2607, 2609, 2611),
        ADAMANT_GTRIM_SK(11892, 2613, 2607, 3475, 2611),
        RUNE_H1_LG(19560, 10286, 19179, 19182, 7336),
        RUNE_H1_SK(19570, 10286, 19179, 19185, 7336),
        RUNE_H2_LG(19562, 10288, 19200, 19203, 7342),
        RUNE_H2_SK(19572, 10288, 19200, 19206, 7342),
        RUNE_H3_LG(19564, 10290, 19221, 19224, 7348),
        RUNE_H3_SK(19574, 10290, 19221, 19227, 7348),
        RUNE_H4_LG(19566, 10292, 19242, 19245, 7354),
        RUNE_H4_SK(19576, 10292, 19242, 19248, 7354),
        RUNE_H5_LG(19568, 10294, 19263, 19266, 7360),
        RUNE_H5_SK(19578, 10294, 19263, 19269, 7360),
        RUNE_TRIM_LG(11894, 2627, 2623, 2625, 2629),
        RUNE_TRIM_SK(11896, 2627, 2623, 3477, 2629),
        RUNE_GTRIM_LG(11898, 2619, 2615, 2617, 2621),
        RUNE_GTRIM_SK(11900, 2619, 2615, 3676, 2621),
        GUTHIX_LG(11926, 2673, 2669, 2671, 2675),
        GUTHIX_SK(11932, 2673, 2669, 3480, 2675),
        SARADOMIN_LG(11928, 2665, 2661, 2663, 2667),
        SARADOMIN_SK(11934, 2665, 2661, 3479, 2667),
        ZAMORAK_LG(11930, 2657, 2653, 2655, 2659),
        ZAMORAK_SK(11936, 2657, 2653, 3478, 2659),
        ROCKSHELL(11942, 6128, 6129, 6130, 6151, 6145),
        ELITEBLACK(14527, 14494, 14492, 14490),
        THIRDAGEMELEE(11858, 10350, 10348, 10352, 10346),
        THIRDAGERANGE(11860, 10334, 10330, 10332, 10336),
        THIRDAGEMAGE(11862, 10342, 10334, 10338, 10340),
        THIRDAGEDRUIDIC(19580, 19308, 19311, 19314, 19317, 19320),
        AHRIM(11846, 4708, 4712, 4714, 4710),
        DHAROK(11848, 4716, 4720, 4722, 4718),
        GUTHAN(11850, 4724, 4728, 4730, 4726),
        KARIL(11852, 4732, 4736, 4738, 4734),
        TORAG(11854, 4745, 4749, 4751, 4747),
        VERAC(11856, 4753, 4757, 4759, 4755),
        AKRISAE(21768, 21736, 21744, 21752, 21760),
        GREEN_DHIDE(11864, 1135, 1099, 1065),
        BLUE_DHIDE(11866, 2499, 2493, 2487),
        RED_DHIDE(11868, 2501, 2495, 2489),
        BLACK_DHIDE(11870, 2503, 2497, 2491),
        ROYAL_DHIDE(24386, 24382, 24379, 24376),
        MYSTIC(11872, 4089, 4091, 4093, 4095, 4097),
        MYSTICD(11962, 4099, 4101, 4103, 4105, 4107),
        MYSTICL(11960, 4109, 4111, 4113, 4115, 4117),
        INFINITY(11874, 6918, 6916, 6924, 6920, 6922),
        SPLITBARK(11876, 3385, 3387, 3389, 3391, 3393),
        ENCHANTED(11902, 7400, 7399, 7398),
        BLUE_ROBE_T(11904, 7396, 7392, 7388),
        BLUE_ROBE_G(11906, 7394, 7390, 7386),
        LEATHER_T(11908, 7364, 7368),
        LEATHER_G(11910, 7362, 7366),
        GREEN_DH_T(11912, 7372, 7380),
        GREEN_DH_G(11914, 7370, 7378),
        BLUE_DH_T(11916, 7376, 7384),
        BLUE_DH_G(11918, 7374, 7382),
        GREEN_BLESS(11920, 10382, 10378, 10380, 10376),
        BLUE_BLESS(11922, 10390, 10386, 10388, 10384),
        RED_BLESS(11924, 10374, 10370, 10372, 10368),
        BROWN_DHIDE(19582, 19457, 19453, 19455, 19451),
        PURPLE_DHIDE(19584, 19449, 19445, 19449, 19443),
        SILVER_DHIDE(19586, 19465, 19461, 19463, 19459),
        ARMA_LG(19588, 19422, 19413, 19416, 19410),
        ARMA_SK(19590, 19422, 19413, 19419, 19410),
        BAND_LG(19592, 19437, 19428, 19431, 19440),
        BAND_SK(19594, 19437, 19428, 19434, 19440),
        ANCI_LG(19596, 19407, 19398, 19401, 19410),
        ANCI_SK(19598, 19407, 19398, 19404, 19410),
        GILDED_LG(11938, 3486, 3481, 3483, 3488),
        GILDED_SK(11940, 3486, 3481, 3485, 3488),
        SPINED(11944, 6131, 6133, 6135, 6143, 6149),
        SKELETAL(11946, 6137, 6139, 6141, 6147, 6153),
        CANNON(11967, 6, 8, 10, 12),
        DAGON_HAI(14525, 14497, 14499, 14501);

        companion object {
            private val SETS: Map<Int, com.rs.game.ge.Sets.Set> = HashMap<Int, com.rs.game.ge.Sets.Set>()

            fun forId(itemId: Int): com.rs.game.ge.Sets.Set {
                return com.rs.game.ge.Sets.Set.Companion.SETS.get(itemId)
            }

            init {
                for (set in com.rs.game.ge.Sets.Set.values()) com.rs.game.ge.Sets.Set.Companion.SETS.put(
                    set.getId(),
                    set
                )
            }
        }
    }
}**/