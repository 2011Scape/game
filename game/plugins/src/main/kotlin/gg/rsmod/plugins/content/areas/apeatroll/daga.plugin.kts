package gg.rsmod.plugins.content.areas.apeatroll

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    "Daga's Scimitar Smithy",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.BRONZE_SCIMITAR, 10)
    items[1] = ShopItem(Items.IRON_SCIMITAR, 10)
    items[2] = ShopItem(Items.STEEL_SCIMITAR, 8)
    items[3] = ShopItem(Items.MITHRIL_SCIMITAR, 6)
    items[4] = ShopItem(Items.DRAGON_SCIMITAR, 0)
}

// TODO If the player has not yet completed Monkey Madness I, there will be 0 stock of dragon scimitars.

on_npc_option(npc = Npcs.DAGA, option = "talk-to") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.queue {
            chatNpc("Would you like to buy or sell some scimitars?", facialExpression = FacialExpression.OLD_NORMAL)
            when (options("Yes please.", "No, thanks", "Do you have any Dragon Scimitars in stock?")) {
                FIRST_OPTION -> {
                    chatPlayer("Yes, please.")
                    player.openShop("Daga's Scimitar Smithy")
                }

                SECOND_OPTION -> {
                    chatPlayer("No, thanks.")
                }

                THIRD_OPTION -> {
                    chatPlayer("Do you have any Dragon Scimitars in stock?")
                    chatNpc(
                        "It just so happens I recently got a fresh delivery",
                        "do you want to buy one?",
                        facialExpression = FacialExpression.OLD_NORMAL,
                    )
                    when (options("Yes", "No")) {
                        FIRST_OPTION -> {
                            chatPlayer("Yes, please.")
                            if (player.inventory.hasFreeSpace()) {
                                if (player.inventory.remove(Items.COINS_995, 100000).hasSucceeded()) {
                                    player.inventory.add(Items.DRAGON_SCIMITAR)
                                    itemMessageBox(
                                        "Player receives a dragon scimitar for 100,000 coins.",
                                        item = Items.DRAGON_SCIMITAR,
                                    )
                                } else {
                                    chatNpc(
                                        "Sorry but you don't have enough to buy one",
                                        "at the moment it costs 100000 gold coins.",
                                        facialExpression = FacialExpression.OLD_NORMAL,
                                    )
                                }
                            } else {
                                chatNpc(
                                    "Sorry but you don't have enough space in your inventory.",
                                    facialExpression = FacialExpression.OLD_NORMAL,
                                )
                            }
                        }

                        SECOND_OPTION -> {
                            chatPlayer("No")
                        }
                    }
                }
            }
        }
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}

on_npc_option(npc = Npcs.DAGA, option = "trade") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.openShop("Daga's Scimitar Smithy")
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}
