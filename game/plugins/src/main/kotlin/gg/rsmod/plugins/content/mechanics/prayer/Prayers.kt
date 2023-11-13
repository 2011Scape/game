package gg.rsmod.plugins.content.mechanics.prayer

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.attr.PROTECT_ITEM_ATTR
import gg.rsmod.game.model.bits.INFINITE_VARS_STORAGE
import gg.rsmod.game.model.bits.InfiniteVarsType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.game.plugin.Plugin
import gg.rsmod.game.sync.block.UpdateBlockType
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.PrayerIcon
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*
import kotlin.math.floor

object Prayers {

    private val PRAYER_DRAIN_COUNTER = AttributeKey<Int>()

    val PRAYER_DRAIN = TimerKey()
    private val DISABLE_OVERHEADS = TimerKey()

    private const val DEACTIVATE_PRAYER_SOUND = Sfx.CANCEL_PRAYER

    private const val PRAYER_POINTS_VARP = 2382

    private const val ACTIVE_PRAYERS_VARP = 1395
    private const val SELECTED_QUICK_PRAYERS_VARC = 181

    private const val QUICK_PRAYERS_ACTIVE_VARC = 182
    private const val KING_RANSOMS_QUEST_VARBIT = 3909 // Used for chivalry/piety prayer.
    const val RIGOUR_UNLOCK_VARBIT = 5451
    const val AUGURY_UNLOCK_VARBIT = 5452

    fun disableOverheads(p: Player, cycles: Int) {
        p.timers[DISABLE_OVERHEADS] = cycles
    }

    fun deactivateAll(p: Player) {
        p.setVarp(ACTIVE_PRAYERS_VARP, 0)
        p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
        p.attr.remove(PROTECT_ITEM_ATTR)

        if (p.prayerIcon != -1) {
            p.prayerIcon = -1
            p.addBlock(UpdateBlockType.APPEARANCE)
        }
    }

    suspend fun toggle(it: QueueTask, prayer: Prayer) {
        val p = it.player

        if (p.isDead() || !p.lock.canUsePrayer()) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            return
        } else if (!checkRequirements(it, prayer)) {
            return
        } else if (prayer.group == PrayerGroup.OVERHEAD && p.timers.has(DISABLE_OVERHEADS)) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            p.message("You cannot use overhead prayers right now.")
            return
        } else if (p.skills.getCurrentLevel(Skills.PRAYER) == 0) {
            return
        }

        it.terminateAction = { p.syncVarp(ACTIVE_PRAYERS_VARP) }
        while (p.lock.delaysPrayer()) {
            it.wait(1)
        }
        val active = p.getVarbit(prayer.varbit) != 0
        if (active) {
            deactivate(p, prayer)
        } else {
            activate(p, prayer)
        }
        p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
    }

    fun activate(p: Player, prayer: Prayer) {
        if (!isActive(p, prayer)) {
            val others = Prayer.values.filter { other -> prayer != other && other.group != null &&
                    (prayer.group == other.group || prayer.overlap.contains(other.group)) }
            others.forEach { other ->
                if (p.getVarbit(other.varbit) != 0) {
                    p.setVarbit(other.varbit, 0)
                }
            }

            p.setVarbit(prayer.varbit, 1)
            if (prayer.sound != -1) {
                p.playSound(prayer.sound)
            }

            setOverhead(p)

            if (prayer == Prayer.PROTECT_ITEM) {
                p.attr[PROTECT_ITEM_ATTR] = true
            }
        }
    }

    fun deactivate(p: Player, prayer: Prayer) {
        if (isActive(p, prayer)) {
            p.setVarbit(prayer.varbit, 0)
            p.playSound(DEACTIVATE_PRAYER_SOUND)
            setOverhead(p)

            if (prayer == Prayer.PROTECT_ITEM) {
                p.attr[PROTECT_ITEM_ATTR] = false
            }
        }
    }

    fun drainPrayer(p: Player) {
        if (p.isDead() || p.getVarp(ACTIVE_PRAYERS_VARP) == 0 || p.hasStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.PRAY)) {
            p.attr.remove(PRAYER_DRAIN_COUNTER)
            return
        }

        val drain = calculateDrainRate(p)
        if (drain > 0) {
            val counter = p.attr.getOrDefault(PRAYER_DRAIN_COUNTER, 0) + drain
            val resistance = 60 + (p.getPrayerBonus() * 2)
            if (counter >= resistance) {
                val points = floor((counter / resistance).toDouble()).toInt()

                // Drain skill level by points
                p.skills.alterCurrentLevel(Skills.PRAYER, -points)

                // Drain prayer points by 10 times the points drained from skill level
                val currentPrayerPoints = p.getCurrentPrayerPoints()
                p.setCurrentPrayerPoints(0.coerceAtLeast(currentPrayerPoints - (points * 10)))

                p.attr.put(PRAYER_DRAIN_COUNTER, counter - (resistance * points))
            } else {
                p.attr.put(PRAYER_DRAIN_COUNTER, counter)
            }
        }

        // Check if prayer skill level is 0 and not just the prayer points
        if (p.skills.getCurrentLevel(Skills.PRAYER) == 0) {
            deactivateAll(p)
            p.message("You have run out of prayer points, you can recharge at an altar.")
        }
    }

    fun selectQuickPrayer(it: Plugin, prayer: Prayer) {
        val player = it.player

        if (player.isDead() || !player.lock.canUsePrayer()) {
            player.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
            return
        }

        val slot = prayer.slot
        val enabled = (player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl slot)) != 0

        it.player.queue {
            if (!enabled) {
                if (checkRequirements(this, prayer)) {
                    val others = Prayer.values.filter { other -> prayer != other && other.group != null &&
                            (prayer.group == other.group || prayer.overlap.contains(other.group)) }
                    others.forEach { other ->
                        val otherEnabled = (player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl other.slot)) != 0
                        if (otherEnabled) {
                            player.setVarc(SELECTED_QUICK_PRAYERS_VARC, player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl other.slot).inv())
                        }
                    }
                    player.setVarc(SELECTED_QUICK_PRAYERS_VARC, player.getVarc(SELECTED_QUICK_PRAYERS_VARC) or (1 shl slot))
                }
            } else {
                player.setVarc(SELECTED_QUICK_PRAYERS_VARC, player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl slot).inv())
            }
        }
    }

    fun toggleQuickPrayers(p: Player, option: Int) {
        if (p.isDead() || !p.lock.canUsePrayer()) {
            p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
            return
        }

        if (option == 3) {
            val quickPrayers = p.getVarc(SELECTED_QUICK_PRAYERS_VARC)

            when {
                quickPrayers == 0 -> {
                    p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
                    p.message("You haven't selected any quick-prayers.")
                }
                p.skills.getCurrentLevel(Skills.PRAYER) <= 0 -> {
                    p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
                    p.message("You have run out of prayer points, you can recharge at an altar.")
                }
                p.getVarp(ACTIVE_PRAYERS_VARP) == quickPrayers -> {
                    /*
                     * All active prayers are quick-prayers - so we turn them off.
                     */
                    p.setVarp(ACTIVE_PRAYERS_VARP, 0)
                    p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
                    setOverhead(p)
                }
                else -> {
                    p.setVarp(ACTIVE_PRAYERS_VARP, quickPrayers)
                    p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 1)
                    setOverhead(p)
                }
            }
        } else if (option == 2) {
            p.openInterface(interfaceId = 271, dest = InterfaceDestination.PRAYER_TAB)
            p.focusTab(Tabs.PRAYER)
            p.setInterfaceEvents(interfaceId = 271, component = 43, range = 0..29, setting = 2)
            p.setVarc(181, 1)
        }
    }

    fun isActive(p: Player, prayer: Prayer): Boolean = p.getVarbit(prayer.varbit) != 0

    fun rechargePrayerPoints(player: Player) {
        player.skills.alterCurrentLevel(Skills.PRAYER, player.skills.getMaxLevel(Skills.PRAYER))
        player.setCurrentPrayerPoints(player.skills.getMaxLevel(Skills.PRAYER) * 10)
    }

    private suspend fun checkRequirements(it: QueueTask, prayer: Prayer): Boolean {
        val p = it.player

        if (p.skills.getMaxLevel(Skills.PRAYER) < prayer.level) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox("You need a <col=000080>Prayer</col> level of ${prayer.level} to use <col=000080>${prayer.named}.")
            return false
        }

        if (prayer == Prayer.CHIVALRY && p.getVarbit(KING_RANSOMS_QUEST_VARBIT) < 8) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox("You have not unlocked this prayer.")
            return false
        }

        if (prayer == Prayer.PIETY && p.getVarbit(KING_RANSOMS_QUEST_VARBIT) < 8) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox("You have not unlocked this prayer.")
            return false
        }

        if (prayer == Prayer.RIGOUR && p.getVarbit(RIGOUR_UNLOCK_VARBIT) == 0) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox("You have not unlocked this prayer.")
            return false
        }

        if (prayer == Prayer.AUGURY && p.getVarbit(AUGURY_UNLOCK_VARBIT) == 0) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox("You have not unlocked this prayer.")
            return false
        }

        return true
    }

    private fun setOverhead(p: Player) {
        val icon = when {
            isActive(p, Prayer.PROTECT_FROM_SUMMONING) -> PrayerIcon.PROTECT_FROM_SUMMONING
            isActive(p, Prayer.PROTECT_FROM_MELEE) -> PrayerIcon.PROTECT_FROM_MELEE
            isActive(p, Prayer.PROTECT_FROM_MISSILES) -> PrayerIcon.PROTECT_FROM_MISSILES
            isActive(p, Prayer.PROTECT_FROM_MAGIC) -> PrayerIcon.PROTECT_FROM_MAGIC
            isActive(p, Prayer.RETRIBUTION) -> PrayerIcon.RETRIBUTION
            isActive(p, Prayer.SMITE) -> PrayerIcon.SMITE
            isActive(p, Prayer.REDEMPTION) -> PrayerIcon.REDEMPTION
            else -> PrayerIcon.NONE
        }

        if (p.prayerIcon != icon.id) {
            p.prayerIcon = icon.id
            p.addBlock(UpdateBlockType.APPEARANCE)
        }
    }

    private fun calculateDrainRate(p: Player): Int = Prayer.values.filter { isActive(p, it) }.sumOf { it.drainEffect }
}