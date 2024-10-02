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
import gg.rsmod.plugins.api.PrayerIcon
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*

object Prayers {
    private val PRAYER_DRAIN_COUNTER = AttributeKey<Int>()

    val PRAYER_DRAIN = TimerKey(removeOnZero = false)
    private val DISABLE_OVERHEADS = TimerKey()

    private const val DEACTIVATE_PRAYER_SOUND = Sfx.CANCEL_PRAYER
    private var QUICK_PRAYERS_SETTING = false

    private const val PRAYER_POINTS_VARP = 2382
    const val ACTIVE_PRAYERS_VARP = 1395
    private const val SELECTED_QUICK_PRAYERS_VARC = 181
    private const val QUICK_PRAYERS_ACTIVE_VARC = 182

    // Unused
    private const val KING_RANSOMS_QUEST_VARBIT = 3909 // Used for chivalry/piety prayer.
    const val RIGOUR_UNLOCK_VARBIT = 5451
    const val AUGURY_UNLOCK_VARBIT = 5452

    enum class StatMod {
        ATTACK,
        STRENGTH,
        DEFENSE,
        RANGE,
        MAGE,
    }

    fun init(player: Player) {
        // player.setvarp(if (curses) 1582 else 1395, 0)
        resetStatMods(player)
        refreshSettingQuickPrayers(player)
        unlockPrayerBookButtons(player)
    }

    private fun switchSettingQuickPrayer(player: Player) {
        // player.setvarp(if (curses) 1582 else 1395, 0)
        refreshSettingQuickPrayers(player)
        unlockPrayerBookButtons(player)
    }

    private fun refreshSettingQuickPrayers(player: Player) {
        player.setVarc(SELECTED_QUICK_PRAYERS_VARC, if (QUICK_PRAYERS_SETTING) 1 else 0)
    }

    fun unlockPrayerBookButtons(player: Player) {
        player.setEvents(
            interfaceId = 271,
            component = if (QUICK_PRAYERS_SETTING) 42 else 8,
            from = 0,
            to = 29,
            setting = 2,
        )
    }

    // Assuming the existence of a statMods array
    private val statMods: IntArray = IntArray(StatMod.values().size)

    fun decreaseStatModifier(
        player: Player,
        mod: StatMod,
        bonus: Int,
        max: Int,
    ): Boolean {
        if (statMods[mod.ordinal] > max) {
            statMods[mod.ordinal]--
            updateStatMod(player, mod)
            return true
        }
        return false
    }

    fun increaseStatModifier(
        player: Player,
        mod: StatMod,
        bonus: Int,
        max: Int,
    ): Boolean {
        if (statMods[mod.ordinal] < max) {
            statMods[mod.ordinal]++
            updateStatMod(player, mod)
            return true
        }
        return false
    }

    private fun resetStatMods(player: Player) {
        StatMod.values().forEach { mod ->
            setStatMod(player, mod, 0)
        }
    }

    private fun getStatMod(mod: StatMod): Int {
        return statMods[mod.ordinal]
    }

    private fun setStatMod(
        player: Player,
        mod: StatMod,
        bonus: Int,
    ) {
        statMods[mod.ordinal] = bonus
        updateStatMod(player, mod)
    }

    private fun updateStatMod(
        player: Player,
        mod: StatMod,
    ) {
        player.setVarbit(6857 + mod.ordinal, 30 + statMods[mod.ordinal])
    }

    private fun updateStatMods(player: Player) {
        for (m in StatMod.values()) {
            updateStatMod(player, m)
        }
    }

    fun disableOverheads(
        p: Player,
        cycles: Int,
    ) {
        p.timers[DISABLE_OVERHEADS] = cycles
    }

    fun deactivateAll(p: Player) {
        Prayer.values.forEach { prayer ->
            if (isActive(p, prayer)) {
                deactivate(p, prayer)
            }
        }
        p.setVarp(ACTIVE_PRAYERS_VARP, 0)
        p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
        resetStatMods(p)
        p.attr.remove(PROTECT_ITEM_ATTR)
        if (p.prayerIcon != -1) {
            p.prayerIcon = -1
            p.addBlock(UpdateBlockType.APPEARANCE)
        }
    }

    suspend fun toggle(
        it: QueueTask,
        prayer: Prayer,
    ) {
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
        } else if (p.getVarp(PRAYER_POINTS_VARP) == 0) {
            return
        }
        it.terminateAction = { p.syncVarp(ACTIVE_PRAYERS_VARP) }
        while (p.lock.delaysPrayer()) {
            it.wait(1)
        }
        p.syncVarp(ACTIVE_PRAYERS_VARP)
        val active = p.getVarbit(prayer.varbit) != 0
        if (active) {
            deactivate(p, prayer)
        } else {
            activate(p, prayer)
        }
        p.setVarc(QUICK_PRAYERS_ACTIVE_VARC, 0)
    }

    fun activate(
        p: Player,
        prayer: Prayer,
    ) {
        if (!isActive(p, prayer)) {
            val others =
                Prayer.values.filter { other -> // TODO PROBABLY REDO for readability
                    val matchingGroup = prayer.group == other.group && prayer.group != PrayerGroup.RESTORATION
                    val matchingPrayer = prayer == other
                    val groupNull = other.group == null
                    !matchingPrayer &&
                        !groupNull &&
                        (matchingGroup || prayer.overlap.contains(other.group))
                }

            others.forEach { other ->
                if (isActive(p, other)) {
                    deactivate(p, other)
                }
            }

            p.setVarbit(
                prayer.varbit,
                1,
            )
            if (prayer.sound != -1) {
                p.playSound(prayer.sound)
            }

            setOverhead(p)
            if (prayer == Prayer.PROTECT_ITEM) {
                p.attr[PROTECT_ITEM_ATTR] = true
            }
        }
    }

    fun deactivate(
        p: Player,
        prayer: Prayer,
    ) {
        if (isActive(p, prayer)) {
            p.setVarbit(prayer.varbit, 0)
            p.playSound(DEACTIVATE_PRAYER_SOUND)
            setOverhead(p)

            if (prayer == Prayer.PROTECT_ITEM) {
                p.attr[PROTECT_ITEM_ATTR] = false
            }
        }
    }

    private fun getDrainResistance(p: Player): Int {
        return 60 + (p.getPrayerBonus() * 2)
    }

    fun drainPrayer(p: Player) {
        if (p.isDead() ||
            p.getVarp(ACTIVE_PRAYERS_VARP) == 0 ||
            p.hasStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.PRAY)
        ) {
            p.attr.remove(PRAYER_DRAIN_COUNTER)
            return
        }
        // new correct calculation of prayer drain
        val drainResistance = getDrainResistance(p)
        var prayerDrainCounter = p.attr.getOrDefault(PRAYER_DRAIN_COUNTER, 0) + calculateDrainRate(p)
        while (prayerDrainCounter >= drainResistance) {
            p.decreasePrayerPoints(1)
            prayerDrainCounter -= drainResistance
        }
        p.attr.put(PRAYER_DRAIN_COUNTER, prayerDrainCounter)

        if (p.getVarp(PRAYER_POINTS_VARP) == 0) {
            deactivateAll(p)
            p.message("You have run out of prayer points, you can recharge at an altar.")
        }
    }

    fun selectQuickPrayer(
        it: Plugin,
        prayer: Prayer,
    ) {
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
                    val others =
                        Prayer.values.filter { other ->
                            prayer != other &&
                                other.group != null &&
                                (prayer.group == other.group || prayer.overlap.contains(other.group))
                        }
                    others.forEach { other ->
                        val otherEnabled = (player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl other.slot)) != 0
                        if (otherEnabled) {
                            player.setVarc(
                                SELECTED_QUICK_PRAYERS_VARC,
                                player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl other.slot).inv(),
                            )
                        }
                    }
                    QUICK_PRAYERS_SETTING = true
                    player.setVarc(
                        SELECTED_QUICK_PRAYERS_VARC,
                        player.getVarc(SELECTED_QUICK_PRAYERS_VARC) or (1 shl slot),
                    )
                }
            } else {
                QUICK_PRAYERS_SETTING = false
                player.setVarc(
                    SELECTED_QUICK_PRAYERS_VARC,
                    player.getVarc(SELECTED_QUICK_PRAYERS_VARC) and (1 shl slot).inv(),
                )
            }
        }
    }

    fun toggleQuickPrayers(
        p: Player,
        option: Int,
    ) {
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
            switchSettingQuickPrayer(p)
            p.focusTab(Tabs.PRAYER)
        }
    }

    fun isActive(
        p: Player,
        prayer: Prayer,
    ): Boolean = p.getVarbit(prayer.varbit) != 0

    fun rechargePrayerPoints(player: Player) {
        player.skills.alterCurrentLevel(Skills.PRAYER, player.skills.getMaxLevel(Skills.PRAYER))
        player.setCurrentPrayerPoints(player.skills.getMaxLevel(Skills.PRAYER) * 10)
    }

    private suspend fun checkRequirements(
        it: QueueTask,
        prayer: Prayer,
    ): Boolean {
        val p = it.player

        if (p.skills.getMaxLevel(Skills.PRAYER) < prayer.level) {
            p.syncVarp(ACTIVE_PRAYERS_VARP)
            it.messageBox(
                "You need a <col=000080>Prayer</col> level of ${prayer.level} to use <col=000080>${prayer.named}.",
            )
            return false
        }

        // TODO: Add requirement back after adding King's Ransom quest.
        /**
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
         **/

        return true
    }

    private fun setOverhead(p: Player) {
        val icon =
            when {
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
