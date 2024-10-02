package gg.rsmod.plugins.content.magic.superheat

import gg.rsmod.plugins.content.magic.MagicSpells
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Harley <https://github.com/harleygilpin>
 */

val SUPERHEAT_TIMER = TimerKey()
val SUPERHEAT_ANIMATION = 725
val SUPERHEAT_GFX = 148

// superheat
on_spell_on_item(fromInterface = 192, fromComponent = 50) {
    player.queue(TaskPriority.STRONG) {
        val data = MagicSpells.getMetadata(spellId = SpellbookData.SUPERHEAT_ITEM.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performSuperheat(player)) {
                MagicSpells.removeRunes(player, data.runes, data.sprite)
            }
        }
    }
}

/**
 * The duration of the superheat timer, in ticks.
 */
private val SUPERHEAT_TIMER_DURATION = 2

fun calculateSmithingExperience(
    barId: Int,
    player: Player,
): Double {
    return when (barId) {
        Items.BRONZE_BAR -> 12.5
        Items.IRON_BAR -> if (player.inventory.getItemCount(Items.COAL) >= 2) 17.5 else 12.5
        Items.SILVER_BAR -> 13.7
        Items.GOLD_BAR -> 22.5
        Items.MITHRIL_BAR -> 30.0
        Items.ADAMANT_BAR -> 37.5
        Items.RUNE_BAR -> 50.0
        else -> 0.0
    }
}

/**
 * Performs Superheat on the item the player is interacting with, smelt ore without a furnace.
 *
 * @param player The player performing superheat item spell.
 * @return true if the superheat was successful, false otherwise.
 */

fun performSuperheat(player: Player): Boolean {
    if (player.timers.has(SUPERHEAT_TIMER) && player.timers[SUPERHEAT_TIMER] > 0) {
        return false
    }

    data class SuperheatRequirements(
        val barId: Int?,
        val requiredCoal: Int,
        val requiredOtherOre: Int?,
        val level: Int?,
    )

    val item = player.getInteractingItem()
    val itemDef = world.definitions.get(ItemDef::class.java, item.id)

    if (itemDef.noted) {
        player.message("You cannot cast Superheat on noted items.")
        return false
    }

    val unnoted = item.toUnnoted(world.definitions)

    val requirements =
        when (unnoted.id) {
            Items.COPPER_ORE -> SuperheatRequirements(Items.BRONZE_BAR, 0, Items.TIN_ORE, 1)
            Items.TIN_ORE -> SuperheatRequirements(Items.BRONZE_BAR, 0, Items.COPPER_ORE, 1)
            Items.IRON_ORE -> {
                if (player.inventory.getItemCount(Items.COAL) >= 2) {
                    SuperheatRequirements(Items.STEEL_BAR, 2, null, 15)
                } else {
                    SuperheatRequirements(Items.IRON_BAR, 0, null, 15)
                }
            }
            Items.GOLD_ORE -> SuperheatRequirements(Items.GOLD_BAR, 0, null, 40)
            Items.SILVER_ORE -> SuperheatRequirements(Items.SILVER_BAR, 0, null, 20)
            Items.MITHRIL_ORE -> SuperheatRequirements(Items.MITHRIL_BAR, 4, null, 50)
            Items.ADAMANTITE_ORE -> SuperheatRequirements(Items.ADAMANT_BAR, 6, null, 70)
            Items.RUNITE_ORE -> SuperheatRequirements(Items.RUNE_BAR, 8, null, 85)
            else -> SuperheatRequirements(null, 0, null, 1)
        }

    if (requirements.barId == null) {
        player.message("You need to cast superheat item on ore.")
        player.playSound(Sfx.SUPERHEAT_FAIL)
        return false
    }

    if (unnoted.id == 2892) {
        player.message("Even this spell is not hot enough to heat this item")
        player.playSound(Sfx.SUPERHEAT_FAIL)
        return false
    }

    if (requirements.requiredOtherOre != null && player.inventory.getItemCount(requirements.requiredOtherOre) < 1) {
        player.message(
            "You need ${world.definitions.get(
                ItemDef::class.java,
                requirements.requiredOtherOre,
            ).name.lowercase()} to superheat this item!",
        )
        return false
    }

    if (requirements.requiredCoal > 0 && player.inventory.getItemCount(Items.COAL) < requirements.requiredCoal) {
        player.message("You need ${requirements.requiredCoal} coal to superheat this item!")
        return false
    }

    if (requirements.level != null && player.skills.getCurrentLevel(Skills.SMITHING) < requirements.level) {
        player.message("You need a Smithing level of ${requirements.level} to superheat this item!")
        return false
    }

    val magicExperience = 53.0

    player.timers[SUPERHEAT_TIMER] = SUPERHEAT_TIMER_DURATION
    player.focusTab(Tabs.SPELLBOOK)
    player.animate(SUPERHEAT_ANIMATION)
    player.graphic(SUPERHEAT_GFX, 100)
    if (player.inventory.remove(Item(item.id, 1)).hasSucceeded()) {
        if (requirements.requiredCoal > 0) {
            player.inventory.remove(Item(Items.COAL, requirements.requiredCoal))
        }
        if (requirements.requiredOtherOre != null) {
            player.inventory.remove(Item(requirements.requiredOtherOre, 1))
        }
        player.inventory.add(Item(requirements.barId, 1))
        player.addXp(Skills.MAGIC, magicExperience)

        val smithingExperience = calculateSmithingExperience(requirements.barId, player)
        player.addXp(Skills.SMITHING, smithingExperience)
    }
    return true
}
