package gg.rsmod.plugins.content.magic.alchemy

import gg.rsmod.plugins.content.magic.MagicSpells
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */

val ALCH_TIMER = TimerKey()

// Low alchemy
on_spell_on_item(fromInterface = 192, fromComponent = 38) {
    player.queue(TaskPriority.STRONG) {
        val data = MagicSpells.getMetadata(spellId = SpellbookData.LOW_ALCHEMY.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performAlchemy(player, highAlchemy = false)) {
                MagicSpells.removeRunes(player, data.runes, data.sprite)
            }
        }
    }
}

// High alchemy
on_spell_on_item(fromInterface = 192, fromComponent = 59) {
    player.queue(TaskPriority.STRONG) {
        val data = MagicSpells.getMetadata(spellId = SpellbookData.HIGH_ALCHEMY.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performAlchemy(player, highAlchemy = true)) {
                MagicSpells.removeRunes(player, data.runes, data.sprite)
            }
        }
    }
}

/**
 * The duration of the alchemy timer, in ticks.
 */
private val ALCH_TIMER_DURATION = 5

/**
 * Performs a high or low alchemy on the item the player is interacting with, and adds coins and experience
 * to the player's inventory if successful.
 *
 * @param player The player performing the alchemy.
 * @param highAlchemy Whether to perform a high alchemy. If false, a low alchemy will be performed.
 * @return true if the alchemy was successful, false otherwise.
 */
fun performAlchemy(
    player: Player,
    highAlchemy: Boolean,
): Boolean {
    if (player.timers.has(ALCH_TIMER) && player.timers[ALCH_TIMER] > 0) {
        return false
    }

    val item = player.getInteractingItem()

    val unnoted = item.toUnnoted(world.definitions)

    if (unnoted.id == Items.COINS_995) {
        player.message("You can't cast this spell on an object made of gold.")
        return false
    }
    if (!world.definitions.get(ItemDef::class.java, unnoted.id).tradeable) {
        player.message("You can't cast this spell on an object like that.")
        return false
    }
    if (player.inventory.requiresFreeSlotToAdd(Items.COINS_995) && player.inventory.freeSlotCount < 1) {
        player.message("Not enough space in your inventory!")
        return false
    }

    val hasStaff = player.hasWeaponType(WeaponType.STAFF)
    val (animation, graphic) =
        when {
            hasStaff -> 9633 to 112
            else -> 713 to 113
        }
    val coinAmount = calculateCoinAmount(unnoted, highAlchemy)
    val experience = calculateExperience(highAlchemy)

    player.timers[ALCH_TIMER] = ALCH_TIMER_DURATION
    player.focusTab(Tabs.SPELLBOOK)
    player.animate(animation)
    player.graphic(graphic)
    if (player.inventory.remove(Item(item.id, 1)).hasSucceeded()) {
        player.inventory.add(Item(Items.COINS_995, coinAmount))
        player.addXp(Skills.MAGIC, experience)
    }
    return true
}

/**
 * Calculates the amount of coins to add to the player's inventory for a high or low alchemy on the given item.
 *
 * @param item The item to calculate the coin amount for.
 * @param highAlchemy Whether to calculate the coin amount for a high alchemy. If false, the coin amount for a
 * low alchemy will be calculated.
 * @return The amount of coins to add to the player's inventory.
 */
fun calculateCoinAmount(
    item: Item,
    highAlchemy: Boolean,
): Int {
    val coinModifier = if (highAlchemy) 0.6 else 0.4
    return (world.definitions.get(ItemDef::class.java, item.id).cost * coinModifier).toInt()
}

/**
 * Calculates the amount of experience to add to the player's Magic skill for a high or low alchemy.
 *
 * @param highAlchemy Whether to calculate the experience for a high alchemy. If false, the experience for a
 * low alchemy will be calculated.
 * @return The amount of experience to add to the player's Magic skill.
 */
fun calculateExperience(highAlchemy: Boolean): Double {
    return if (highAlchemy) 65.0 else 31.0
}
