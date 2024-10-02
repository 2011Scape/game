package gg.rsmod.plugins.content.mechanics.shops

import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.LOYALTY_POINTS
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.shop.Shop
import gg.rsmod.game.model.shop.ShopCurrency
import gg.rsmod.game.model.shop.ShopItem
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.format
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.setComponentText
import kotlin.math.floor
import kotlin.math.min

/**
 * @author Alycia <https://github.com/alycii>
 */
open class PointCurrency(
    val singularCurrency: String,
    private val pluralCurrency: String,
) : ShopCurrency {
    override fun onSellValueMessage(
        p: Player,
        shopItem: ShopItem,
        freeItem: Boolean,
    ) {
        val unnoted = Item(shopItem.item).toUnnoted(p.world.definitions)
        val value = shopItem.sellPrice ?: getSellPrice(p.world, unnoted.id)
        val name = unnoted.getName(p.world.definitions)
        val currency = if (value != 1) pluralCurrency else singularCurrency
        p.message("$name: currently costs ${value.format()} $currency.")
    }

    override fun onBuyValueMessage(
        p: Player,
        shop: Shop,
        item: Int,
    ) {
        p.message("You can't sell this item to this shop.")
    }

    override fun getSellPrice(
        world: World,
        item: Int,
    ): Int {
        TODO("Not yet implemented")
    }

    override fun getBuyPrice(
        stock: Int,
        world: World,
        item: Int,
    ): Int {
        TODO("Not needed for points")
    }

    override fun sellToPlayer(
        p: Player,
        shop: Shop,
        slot: Int,
        amt: Int,
    ) {
        val shopItem = shop.items[slot] ?: return

        val currencyCost = shopItem.sellPrice ?: getSellPrice(p.world, shopItem.item)
        val currencyCount = p.attr[LOYALTY_POINTS] ?: 0

        var amount = min(floor(currencyCount.toDouble() / currencyCost.toDouble()).toInt(), amt)

        if (amount == 0) {
            p.message("You don't have enough $pluralCurrency.")
            return
        }

        val moreThanStock = amount > shopItem.currentAmount

        amount = Math.min(amount, shopItem.currentAmount)

        if (amount == 0) {
            p.filterableMessage("The shop has run out of stock.")
            return
        }

        if (moreThanStock) {
            p.filterableMessage("The shop has run out of stock.")
        }

        val totalCost = currencyCost.toLong() * amount.toLong()
        if (totalCost > Int.MAX_VALUE) {
            return
        }

        if (currencyCount < totalCost) {
            p.message("You don't have enough $pluralCurrency.")
            return
        }

        p.subtractLoyalty(totalCost.toInt())

        val add = p.inventory.add(item = shopItem.item, amount = amount, assureFullInsertion = false)
        if (add.completed == 0) {
            p.message("You don't have enough inventory space.")
        }

        if (add.getLeftOver() > 0) {
            val refund = add.getLeftOver() * currencyCost
            p.addLoyalty(refund)
        }

        if (add.completed > 0 && shopItem.amount != Int.MAX_VALUE) {
            shop.items[slot]!!.currentAmount -= add.completed

            /*
             * Check if the item is temporary and should be removed from the shop.
             */
            if (shop.items[slot]?.currentAmount == 0 && shop.items[slot]?.temporary == true) {
                shop.items[slot] = null
            }

            shop.refresh(p.world)
            p.setComponentText(
                interfaceId = 620,
                component = 24,
                text = "You currently have ${p.attr[LOYALTY_POINTS]!!.format()} loyalty points.",
            )
        }
    }

    override fun giveToPlayer(
        p: Player,
        shop: Shop,
        slot: Int,
        amt: Int,
    ) {
        TODO("Not needed for points")
    }

    override fun buyFromPlayer(
        p: Player,
        shop: Shop,
        slot: Int,
        amt: Int,
    ) {
        TODO("Not needed for points")
    }

    override val currencyItem = -1
}
