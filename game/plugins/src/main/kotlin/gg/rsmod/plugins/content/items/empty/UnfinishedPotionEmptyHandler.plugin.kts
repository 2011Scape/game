package gg.rsmod.plugins.content.items.empty


/**
 * @author mrsla
 */

data class UnfinishedPotion(
    val item: Int,
    val emptyItem: Int,
    val emptyMessage: String
)

val unfinishedPotionHandler = UnfinishedPotionInteractionHandler()

val unfinishedPotions = listOf(
//Dungenning potions
UnfinishedPotion(Items.WERGALI_POTION_UNF, Items.VIAL_17490, "You empty the potion.") ,
UnfinishedPotion(Items.SAGEWORT_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.VALERIAN_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.ALOE_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.WORMWOOD_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.MAGEBANE_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.FEATHERFOIL_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.WINTERS_GRIP_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.LYCOPUS_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.BUCKTHORN_POTION_UNF, Items.VIAL_17490, "You empty the potion."),
UnfinishedPotion(Items.WORMWOOD_POTION_UNF, Items.VIAL_17490, "You empty the potion."),

//juju potions
UnfinishedPotion(Items.ERZILLE_POTION_UNF, Items.JUJU_VIAL, "You empty the potion."),
UnfinishedPotion(Items.UGUNE_POTION_UNF, Items.JUJU_VIAL, "You empty the potion."),
UnfinishedPotion(Items.ARGWAY_POTION_UNF, Items.JUJU_VIAL, "You empty the potion."),
UnfinishedPotion(Items.SHENGO_POTION_UNF, Items.JUJU_VIAL, "You empty the potion."),
UnfinishedPotion(Items.SAMADEN_POTION_UNF, Items.JUJU_VIAL, "You empty the potion."),

//normal potions
UnfinishedPotion(Items.FELLSTALK_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.GUAM_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.MARRENTILL_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.TARROMIN_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.HARRALANDER_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.RANARR_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.IRIT_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.AVANTOE_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.KWUARM_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.CADANTINE_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.DWARF_WEED_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.TORSTOL_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.LANTADYME_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.TOADFLAX_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.SNAPDRAGON_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.ROGUES_PURSE_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.WEAPON_POISON_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.ANTIPOISON_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.ANTIPOISON_UNF_5951, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.GUTHIX_BALANCE_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.GUTHIX_BALANCE_UNF_7654, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.GUTHIX_BALANCE_UNF_7656, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.GUTHIX_BALANCE_UNF_7658, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.SPIRIT_WEED_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.MOUNTAIN_IRIT_POTION_UNF, Items.VIAL, "You empty the potion."),
UnfinishedPotion(Items.DIAMOND_DUST_POTION_UNF, Items.VIAL, "You empty the potion.")

// Add more unfinished potions as needed
)

unfinishedPotions.forEach { unfinishedPotionHandler.emptyUnfinishedPotion(it) }

class UnfinishedPotionInteractionHandler {
    fun emptyUnfinishedPotion(unfinishedPotion: UnfinishedPotion) {
        on_item_option(item = unfinishedPotion.item, option = "empty") {
            val itemSlot = player.getInteractingItemSlot()
            val itemHasBeenRemoved = player.inventory.remove(player.getInteractingItem(), beginSlot = player.getInteractingItemSlot()).hasSucceeded()
            if(itemHasBeenRemoved) {
                player.inventory[itemSlot] = Item(unfinishedPotion.emptyItem)
                player.filterableMessage(unfinishedPotion.emptyMessage)
            }
        }
    }
}
