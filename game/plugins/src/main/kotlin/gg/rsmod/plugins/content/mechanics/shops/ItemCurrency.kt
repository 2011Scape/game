package gg.rsmod.plugins.content.mechanics.shops

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.LAST_VIEWED_SHOP_ITEM_FREE
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.shop.PurchasePolicy
import gg.rsmod.game.model.shop.Shop
import gg.rsmod.game.model.shop.ShopCurrency
import gg.rsmod.game.model.shop.ShopItem
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.util.Misc
import kotlin.math.max
import kotlin.math.min

/**
 * @author Tom <rspsmods@gmail.com>
 */
open class ItemCurrency(private val currencyItem: Int, private val singularCurrency: String, private val pluralCurrency: String) : ShopCurrency {

    private data class AcceptItemState(val acceptable: Boolean, val errorMessage: String)

    private fun canAcceptItem(shop: Shop, world: World, item: Int): AcceptItemState {
        if (item == Items.COINS_995) {
            return AcceptItemState(acceptable = false, errorMessage = "You can't sell this item to a shop.")
        }
        when {
            shop.purchasePolicy == PurchasePolicy.BUY_TRADEABLES -> {
                if (!Item(item).getDef(world.definitions).tradeable) {
                    return AcceptItemState(acceptable = false, errorMessage = "You can't sell this item.")
                }
            }
            shop.purchasePolicy == PurchasePolicy.BUY_STOCK -> {
                if (shop.items.none { it?.item == item }) {
                    return AcceptItemState(acceptable = false, errorMessage = "You can't sell this item to this shop.")
                }
            }
            shop.purchasePolicy == PurchasePolicy.BUY_ALL -> return AcceptItemState(acceptable = true, errorMessage = "")
            shop.purchasePolicy == PurchasePolicy.BUY_NONE -> return AcceptItemState(acceptable = false, errorMessage = "You can't sell any items to this shop.")
            else -> throw RuntimeException("Unhandled purchase policy. [shop=${shop.name}, policy=${shop.purchasePolicy}]")
        }
        return AcceptItemState(acceptable = true, errorMessage = "")
    }

    override fun onSellValueMessage(p: Player, shopItem: ShopItem, freeItem: Boolean) {
        val unnoted = Item(shopItem.item).toUnnoted(p.world.definitions)
        val value = shopItem.sellPrice ?: getSellPrice(p.world, unnoted.id)
        val name = unnoted.getName(p.world.definitions)
        val currency = if (value != 1) pluralCurrency else singularCurrency
        val def = unnoted.getDef(p.world.definitions)
        p.attr[LAST_VIEWED_SHOP_ITEM_FREE] = freeItem
        if(freeItem) {
            p.message("$name: is free, go ahead and take one!")
        } else {
            p.message("$name: currently costs ${value.format()} $currency")
        }
        p.openInterface(interfaceId = 449, dest = InterfaceDestination.TAB_AREA)

        // Sets some component text colors
        p.setVarc(1241, 0xeb981f)

        /**
         * 741 - the item
         * 742 - unknown
         * 743 - the type of currency
         * 744 - if the item should be free (-1), paid for (value), or if the stock is empty (1)
         * 745 - unknown
         * 746 - ranged information (fires arrows up to... etc)
         *
         */
        for(i in 740..746) {
            when(i) {
                741 -> p.setVarc(i, shopItem.item)
                743 -> p.setVarc(i, currencyItem)
                744 -> {
                    if(freeItem) {
                        p.setVarc(i, -1)
                    } else {
                        p.setVarc(i, value)
                    }
                }
                746 -> p.setVarc(i, -1)
                else -> p.setVarc(i, 1)
            }
        }

        p.setVarcString(25, "<col=eb981f>${def.examine!!}<br>")
        p.setVarcString(35, getAttackBonuses(p.world, Item(shopItem.item)).toString())
        p.setVarcString(52, getDefenceBonuses(p.world, Item(shopItem.item)).toString())

        val color = "<br><col=eb981f>"
        val builder = StringBuilder()
        builder.append("<br>")
        builder.append("${color}Stab")
        builder.append("${color}Slash")
        builder.append("${color}Crush")
        builder.append("${color}Magic")
        builder.append("${color}Ranged")
        builder.append("${color}Summoning")
        p.setVarcString(36, builder.toString())
        builder.clear()

        val levelRequirements = def.skillReqs
        if (levelRequirements != null) {
            p.setVarcString(34, "${color}Worn ${ItemDef.getSlotText(Item(shopItem.item).getDef(p.world.definitions).equipSlot)}, requiring:")
            for (entry in levelRequirements.entries) {
                val skill = entry.key.toInt()
                val level = entry.value
                val skillName = EquipAction.SKILL_NAMES[skill]
                if (p.getSkills().getMaxLevel(skill) < level) {
                    p.setVarcString(26, "<col=D55B5B>Level $level ${Misc.formatforDisplay(skillName)}")
                } else {
                    p.setVarcString(26, "<col=5BD564>Level $level ${Misc.formatforDisplay(skillName)}")
                }
            }
        } else {
            p.setVarcString(26, "<br>")
            p.setVarcString(34, "<br>")
        }

    }

    private fun getAttackBonuses(world: World, item: Item): String? {
        val brown = "<br><col=eb981f>"
        val yellow = "<br><col=FFFF00>"
        val builder = StringBuilder()
        builder.append("${brown}Attack")
        for (i in 0..4) {
            val bonus = item.getDef(world.definitions).bonuses[i]
            if(bonus > 0) {
                builder.append("${yellow}+$bonus")
            } else {
                builder.append("${yellow}$bonus")
            }
        }
        builder.append("${yellow}---")
        builder.append("${brown}Strength")
        builder.append("${brown}Ranged Strength")
        builder.append("${brown}Prayer bonus")
        return builder.toString()
    }

    private fun getDefenceBonuses(world: World, item: Item): String? {
        val brown = "<br><col=eb981f>"
        val yellow = "<br><col=FFFF00>"
        val builder = StringBuilder()
        builder.append("${brown}Defence")
        var currentIndex = 5
        for (i in 0..8) {
            if(i == 5) {
                // TODO: summoning defence bonuses
                builder.append("${yellow}0")
                continue
            }
            val bonus = item.getDef(world.definitions).bonuses[currentIndex]
            currentIndex++
            if(bonus > 0) {
                builder.append("${yellow}+$bonus")
            } else {
                builder.append("${yellow}$bonus")
            }
        }
        return builder.toString()
    }

    override fun onBuyValueMessage(p: Player, shop: Shop, item: Int) {
        val unnoted = Item(item).toUnnoted(p.world.definitions)
        val acceptance = canAcceptItem(shop, p.world, unnoted.id)
        if (acceptance.acceptable) {
            val shopItem = shop.items.filterNotNull().firstOrNull { it.item == unnoted.id}
            val stock = shopItem?.currentAmount ?: 0
            val value = shopItem?.buyPrice ?: getBuyPrice(stock, p.world, unnoted.id)
            val name = unnoted.getName(p.world.definitions)
            val currency = if (value != 1) pluralCurrency else singularCurrency
            p.message("$name: shop will buy for ${value.format()} $currency")
        } else {
            p.message(acceptance.errorMessage)
        }
    }
    override fun getSellPrice(world: World, item: Int): Int = world.definitions.get(ItemDef::class.java, item).cost

    override fun getBuyPrice(stock: Int, world: World, item: Int): Int {
        val value = world.definitions.get(ItemDef::class.java, item).cost
        val firstItemSellPriceFactor = 0.4
        val sellPriceDecreasePerItem = 0.03
        val maxNumItemsBeforePriceDecrease = 10
        return (value * firstItemSellPriceFactor - sellPriceDecreasePerItem * value * min(stock, maxNumItemsBeforePriceDecrease)).toInt()
    }

    override fun giveToPlayer(p: Player, shop: Shop, slot: Int, amt: Int) {
        val shopItem = shop.sampleItems[slot] ?: return

        var amount = amt
        val moreThanStock = amount > shopItem.currentAmount

        amount = Math.min(amount, shopItem.currentAmount)

        if (amount == 0) {
            p.message("The shop has run out of stock.")
            return
        }

        if (moreThanStock) {
            p.message("The shop has run out of stock.")
        }

        val add = p.inventory.add(item = shopItem.item, amount = amount, assureFullInsertion = false)
        if (add.completed == 0) {
            p.message("You don't have enough inventory space.")
        }

        if (add.completed > 0 && shopItem.amount != Int.MAX_VALUE) {
            shop.sampleItems[slot]!!.currentAmount -= add.completed

            /*
             * Check if the item is temporary and should be removed from the shop.
             */
            if (shop.sampleItems[slot]?.amount == 0 && shop.sampleItems[slot]?.isTemporary == true) {
                shop.sampleItems[slot] = null
            }

            shop.refresh(p.world)
        }
    }

    override fun sellToPlayer(p: Player, shop: Shop, slot: Int, amt: Int) {
        val shopItem = shop.items[slot] ?: return

        val currencyCost = shopItem.sellPrice ?: getSellPrice(p.world, shopItem.item)
        val currencyCount = p.inventory.getItemCount(currencyItem)

        var amount = Math.min(Math.floor(currencyCount.toDouble() / currencyCost.toDouble()).toInt(), amt)

        if (amount == 0) {
            p.message("You don't have enough $pluralCurrency.")
            return
        }

        val moreThanStock = amount > shopItem.currentAmount

        amount = Math.min(amount, shopItem.currentAmount)

        if (amount == 0) {
            p.message("The shop has run out of stock.")
            return
        }

        if (moreThanStock) {
            p.message("The shop has run out of stock.")
        }

        val totalCost = currencyCost.toLong() * amount.toLong()
        if (totalCost > Int.MAX_VALUE) {
            return
        }

        if (p.inventory.getItemCount(itemId = currencyItem) < totalCost) {
            p.message("You don't have enough $pluralCurrency.")
            return
        }

        val remove = p.inventory.remove(item = currencyItem, amount = totalCost.toInt(), assureFullRemoval = true)
        if (remove.hasFailed()) {
            return
        }

        val add = p.inventory.add(item = shopItem.item, amount = amount, assureFullInsertion = false)
        if (add.completed == 0) {
            p.message("You don't have enough inventory space.")
        }

        if (add.getLeftOver() > 0) {
            val refund = add.getLeftOver() * currencyCost
            p.inventory.add(item = currencyItem, amount = refund)
        }

        if (add.completed > 0 && shopItem.amount != Int.MAX_VALUE) {
            shop.items[slot]!!.currentAmount -= add.completed

            /*
             * Check if the item is temporary and should be removed from the shop.
             */
            if (shop.items[slot]?.currentAmount == 0 && shop.items[slot]?.isTemporary == true) {
                shop.items[slot] = null
            }

            shop.refresh(p.world)
        }
    }

    override fun buyFromPlayer(p: Player, shop: Shop, slot: Int, amt: Int) {
        val item = p.inventory[slot] ?: return
        val unnoted = item.toUnnoted(p.world.definitions).id
        val acceptance = canAcceptItem(shop, p.world, unnoted)

        if (!acceptance.acceptable) {
            p.message(acceptance.errorMessage)
            return
        }

        val shopSlot = shop.items.indexOfFirst { it?.item == unnoted }
        val shopItem = if (shopSlot != -1) shop.items[shopSlot] else null
        val count = shopItem?.currentAmount ?: 0

        val amount = Math.min(Math.min(p.inventory.getItemCount(item.id), amt), Int.MAX_VALUE - count)

        if (count == 0 && shop.items.none { it == null } || amount == 0) {
            p.message("The shop has run out of space.")
            return
        }

        val remove = p.inventory.remove(item = item.id, amount = amount, assureFullRemoval = false)
        if (remove.completed == 0) {
            return
        }

        val price = shopItem?.buyPrice ?: getBuyPrice(world = p.world, item = unnoted)
        val compensation = Math.min(Int.MAX_VALUE.toLong(), price.toLong() * remove.completed.toLong()).toInt()
        val add = p.inventory.add(item = currencyItem, amount = compensation, assureFullInsertion = true)
        if (add.requested > 0 && add.completed > 0 || compensation == 0) {
            if (shopSlot != -1) {
                shop.items[shopSlot]!!.currentAmount += amount
            } else {
                val freeSlot = shop.items.indexOfFirst { it == null }
                check(freeSlot != -1)
                shop.items[freeSlot] = ShopItem(unnoted, amount = 0)
                shop.items[freeSlot]!!.currentAmount = amount
            }
            shop.refresh(p.world)
        } else {
            p.inventory.add(item.id, amount = remove.completed, beginSlot = slot)
            p.message("You don't have enough inventory space.")
        }
    }
}