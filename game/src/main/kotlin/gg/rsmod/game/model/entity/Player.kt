package gg.rsmod.game.model.entity

import com.google.common.base.MoreObjects
import gg.rsmod.game.fs.def.VarbitDef
import gg.rsmod.game.fs.def.VarpDef
import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.*
import gg.rsmod.game.model.*
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.container.key.BANK_KEY
import gg.rsmod.game.model.container.key.ContainerKey
import gg.rsmod.game.model.container.key.EQUIPMENT_KEY
import gg.rsmod.game.model.container.key.INVENTORY_KEY
import gg.rsmod.game.model.interf.DisplayMode
import gg.rsmod.game.model.interf.InterfaceSet
import gg.rsmod.game.model.interf.listener.PlayerInterfaceListener
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.queue.TaskPriority
import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.game.model.timer.*
import gg.rsmod.game.model.varp.VarpSet
import gg.rsmod.game.service.log.LoggerService
import gg.rsmod.game.sync.block.UpdateBlockType
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

/**
 * A [Pawn] that represents a player.
 *
 * @author Tom <rspsmods@gmail.com>
 */
open class Player(world: World) : Pawn(world) {

    /**
     * A persistent and unique id. This is <strong>not</strong> the index
     * of our [Player] when registered to the [World], it is a value determined
     * when the [Player] first registers their account.
     */
    lateinit var uid: PlayerUID

    /**
     * The display name that will show on the player while in-game.
     */
    var username = ""

    /**
     * @see Privilege
     */
    var privilege = Privilege.DEFAULT

    /**
     * The base region [Coordinate] is the most bottom-left (south-west) tile where
     * the last known region for this player begins.
     */
    var lastKnownRegionBase: Coordinate? = null

    /**
     * A flag that indicates whether or not the [login] method has been executed.
     * This is currently used so that we don't send player updates when the player
     * hasn't been fully initialized. We can test later to see if this is even
     * necessary.
     */
    var initiated = false

    /**
     * The index that was assigned to a [Player] when they are first registered to the
     * [World]. This is needed to remove local players from the synchronization task
     * as once that logic is reached, the local player would have an index of [-1].
     */
    var lastIndex = -1

    /**
     * A flag which indicates the player is attempting to log out. There can be
     * certain circumstances where the player should not be unregistered from
     * the world.
     *
     * For example: when the player is in combat.
     */
    @Volatile
    private var pendingLogout = false

    /**
     * A flag which indicates that our [FORCE_DISCONNECTION_TIMER] must be set
     * when [pendingLogout] logic is handled.
     */
    @Volatile
    private var setDisconnectionTimer = false

    val inventory = ItemContainer(world.definitions, INVENTORY_KEY)

    val equipment = ItemContainer(world.definitions, EQUIPMENT_KEY)

    val bank = ItemContainer(world.definitions, BANK_KEY)

    /**
     * A flag which indicates if the map should be force
     * refreshed
     */
    var forceMapRefresh = false

    /**
     * The size of the map, used as an array index
     * e.g, sizes are { 104, 120, 136, 168 }
     */
    var mapSize = 0

    /**
     * A map that contains all the [ItemContainer]s a player can have.
     */
    val containers = HashMap<ContainerKey, ItemContainer>().apply {
        put(INVENTORY_KEY, inventory)
        put(EQUIPMENT_KEY, equipment)
        put(BANK_KEY, bank)
    }

    /**
     * If the client is in resizeable mode
     */
    var resizableClient = false

    val interfaces by lazy { InterfaceSet(PlayerInterfaceListener(this, world.plugins)) }

    val varps = VarpSet(varpIds = world.definitions.getAllKeys(VarpDef::class.java))

    /**
     * Current set varcs on the player
     */
    val varcs = MutableList(world.definitions.getCount(VarbitDef::class.java)) { 0 }

    /**
     * The players skillSet
     */
    private val skillSet = SkillSet(maxSkills = world.gameContext.skillCount)

    /**
     * The options that can be executed on this player
     */
    val options = Array<String?>(10) { null }

    /**
     * Flag that indicates whether or not to refresh the shop the player currently
     * has open.
     */
    var shopDirty = false

    /**
     * Some areas have a 'large' viewport. Which means the player's client is
     * able to render more entities in a larger radius than normal.
     */
    private var largeViewport = false

    /**
     * The players in our viewport, including ourselves. This list should not
     * be used outside of our synchronization task.
     */
    internal val gpiLocalPlayers = arrayOfNulls<Player>(2048)

    /**
     * The indices of any possible local player in the world.
     */
    internal val gpiLocalIndexes = IntArray(2048)

    /**
     * The current local player count.
     */
    internal var gpiLocalCount = 0

    /**
     * The indices of players outside of our viewport in the world.
     */
    internal val gpiExternalIndexes = IntArray(2048)

    /**
     * The amount of players outside of our viewport.
     */
    internal var gpiExternalCount = 0

    /**
     * The inactivity flags for players.
     */
    internal val gpiInactivityFlags = IntArray(2048)

    /**
     * GPI tile hash multipliers.
     *
     * The player synchronization task will send [Tile.x] and [Tile.z] as 13-bit
     * values, which is 2^13 (8192). To send a player position higher than said
     * value in either direction, we must also send a multiplier.
     */
    internal val gpiTileHashMultipliers = IntArray(2048)

    /**
     * The npcs in our viewport. This list should not be used outside of our
     * synchronization task.
     */
    internal val localNpcs = ObjectArrayList<Npc>()

    val oneMinuteInGameCycles = 60 / 0.6

    var appearance = Appearance.DEFAULT

    var weight = 0.0

    var skullIcon = -1

    var runEnergy = 100.0

    /**
     * The current combat level. This must be set externally by a login plugin
     * that is used on whatever revision you want.
     */
    var combatLevel = 3

    var lifepoints = 100

    var hpRestoreMultiplier: Int = 10

    var boostedXp: Boolean = false

    val enabledSkillTarget = BooleanArray(25)
    val skillTargetMode = BooleanArray(25)
    val skillTargetValue = IntArray(25)

    /**
     * The last cycle that this client has received the MAP_BUILD_COMPLETE
     * message. This value is set to [World.currentCycle].
     *
     * @see [gg.rsmod.game.message.handler.MapBuildCompleteHandler]
     */
    var lastMapBuildTime = 0

    fun getSkills(): SkillSet = skillSet

    override val entityType: EntityType = EntityType.PLAYER

    /**
     * Checks if the player is running. We assume that the varp with id of
     * [173] is the running state varp.
     */
    override fun isRunning(): Boolean =
        varps[173].state == 1 || movementQueue.peekLastStep()?.type == MovementQueue.StepType.FORCED_RUN

    fun isResting(): Boolean = varps[173].state >= 3

    override fun getSize(): Int = 1

    fun getLastCombatLevel(): Int {
        val lastCombat = attr[LAST_COMBAT_LEVEL]
        if (lastCombat == null) {
            setLastCombatLevel(combatLevel)
            return getLastCombatLevel()
        }
        return lastCombat
    }

    fun setLastCombatLevel(level: Int) {
        attr[LAST_COMBAT_LEVEL] = level
    }

    fun getLastTotalLevel(): Int {
        val lastTotal = attr[LAST_TOTAL_LEVEL]
        if (lastTotal == null) {
            setLastTotalLevel(getSkills().calculateTotalLevel)
            return getLastTotalLevel()
        }
        return lastTotal
    }

    fun setLastTotalLevel(level: Int) {
        attr[LAST_TOTAL_LEVEL] = level
    }

    fun setRenderAnimation(i: Int, queueTime: Int = -1, priority: TaskPriority = TaskPriority.STANDARD) {
        if (queueTime > 0) {
            queue(priority) {
                setRenderAnimation(i)
                wait(queueTime)
                resetRenderAnimation()
            }
            return
        }
        appearance.renderAnim = i
        addBlock(UpdateBlockType.APPEARANCE)
    }

    fun resetRenderAnimation() {
        setRenderAnimation(-1)
    }

    fun updateAppearence() {
        addBlock(UpdateBlockType.APPEARANCE)
    }

    override fun getCurrentHp(): Int = lifepoints

    override fun getMaxHp(): Int = getSkills().getMaxLevel(3) * 10

    override fun setCurrentHp(level: Int) {
        lifepoints = level
        sendTemporaryVarbit(7198, lifepoints)
    }

    fun alterLifepoints(value: Int, capValue: Int = 0) {
        check(capValue == 0 || capValue < 0 && value < 0 || capValue > 0 && value >= 0) {
            "Cap value and alter value must always be the same signum (+ or -)."
        }
        val altered = when {
            capValue > 0 -> min(getCurrentHp() + value, getMaxHp() + capValue)
            capValue < 0 -> max(getCurrentHp() + value, getMaxHp() + capValue)
            else -> min(getMaxHp(), getCurrentHp() + value)
        }
        val newLevel = max(0, altered)
        val curLevel = getCurrentHp()

        if (newLevel != curLevel) {
            setCurrentHp(newLevel)
        }
    }

    override fun addBlock(block: UpdateBlockType) {
        if (world.playerUpdateBlocks.updateBlocks[block] != null) {
            val bits = world.playerUpdateBlocks.updateBlocks[block]!!
            blockBuffer.addBit(bits.bit)
        }
    }

    override fun hasBlock(block: UpdateBlockType): Boolean {
        val bits = world.playerUpdateBlocks.updateBlocks[block]!!
        return blockBuffer.hasBit(bits.bit)
    }

    fun forceMove(movement: ForcedMovement) {
        blockBuffer.forceMovement = movement
        addBlock(UpdateBlockType.FORCE_MOVEMENT)
    }

    suspend fun forceMove(task: QueueTask, movement: ForcedMovement, cycleDuration: Int = movement.maxDuration / 30) {
        movementQueue.clear()
        if (movement.lock != LockState.NONE)
            lock = movement.lock

        lastTile = Tile(tile)
        moveTo(movement.finalDestination)

        forceMove(movement)

        task.wait(cycleDuration)
        if (movement.lock != LockState.NONE)
            lock = LockState.NONE
    }

    suspend fun forceMove(
        task: QueueTask,
        movement: ForcedMovement,
        cycleDuration: Int = movement.maxDuration / 30,
        addLock: Boolean,
    ) {
        movementQueue.clear()
        if (addLock)
            lock = LockState.DELAY_ACTIONS

        lastTile = Tile(tile)
        moveTo(movement.finalDestination)

        forceMove(movement)

        task.wait(cycleDuration)
        if (addLock)
            lock = LockState.NONE
    }

    /**
     * Logic that should be executed every game cycle, before
     * [gg.rsmod.game.sync.task.PlayerSynchronizationTask].
     *
     * Note that this method may be handled in parallel, so be careful with race
     * conditions if any logic may modify other [Pawn]s.
     */
    override fun cycle() {
        var calculateWeight = false
        var calculateBonuses = false

        if (pendingLogout) {
            /*
             * If a channel is suddenly inactive (disconnected), we don't to
             * immediately unregister the player. However, we do want to
             * unregister the player abruptly if a certain amount of time
             * passes since their channel disconnected.
             */
            if (setDisconnectionTimer) {
                timers[FORCE_DISCONNECTION_TIMER] = 250 // 2 mins 30 secs
                setDisconnectionTimer = false
            }

            /*
             * A player should only be unregistered from the world when they
             * do not have [ACTIVE_COMBAT_TIMER] or its cycles are <= 0, or if
             * their channel has been inactive for a while.
             *
             * We do allow players to disconnect even if they are in combat, but
             * only if the most recent damage dealt to them are by npcs.
             */
            val stopLogout =
                timers.has(ACTIVE_COMBAT_TIMER) && damageMap.getAll(type = EntityType.PLAYER, timeFrameMs = 10_000)
                    .isNotEmpty()
            val forceLogout = timers.exists(FORCE_DISCONNECTION_TIMER) && !timers.has(FORCE_DISCONNECTION_TIMER)

            if (!stopLogout || forceLogout) {
                // TODO: re-enable this after locks are properly checked
                // if (lock.canLogout()) {
                handleLogout()
                return
                // }
            }
        }

        if (inventory.dirty) {
            write(UpdateInvFullMessage(containerKey = 93, items = inventory.rawItems))
            setInterfaceEvents(679, 0, 0, 27, 0x457D8E)
            setInterfaceEvents(679, 0, 28, 55, 0x200000)
            inventory.dirty = false
            calculateWeight = true
        }

        if (equipment.dirty) {
            write(UpdateInvFullMessage(containerKey = 94, items = equipment.rawItems))
            equipment.dirty = false
            calculateWeight = true
            calculateBonuses = true
            addBlock(UpdateBlockType.APPEARANCE)
        }

        if (bank.dirty) {
            write(UpdateInvFullMessage(containerKey = 95, items = bank.rawItems))
            bank.shift()
            bank.dirty = false
        }

        if (shopDirty) {
            attr[CURRENT_SHOP_ATTR]?.let { shop ->
                write(
                    UpdateInvFullMessage(
                        containerKey = 4,
                        items = shop.items.map { if (it != null) Item(it.item, it.currentAmount) else null }
                            .toTypedArray()
                    )
                )
                write(
                    UpdateInvFullMessage(
                        containerKey = 6,
                        items = shop.sampleItems.map { if (it != null) Item(it.item, it.currentAmount) else null }
                            .toTypedArray()
                    )
                )
            }
            shopDirty = false
        }

        if (calculateWeight) {
            calculateWeight()
        }

        if (calculateBonuses) {
            calculateBonuses()
        }

        if (timers.isNotEmpty) {
            timerCycle()
        }

        hitsCycle()

        for (i in 0 until varps.maxVarps) {
            if (varps.isDirty(i)) {
                val varp = varps[i]
                val message = when {
                    varp.state in -Byte.MAX_VALUE..Byte.MAX_VALUE -> VarpSmallMessage(varp.id, varp.state)
                    else -> VarpLargeMessage(varp.id, varp.state)
                }
                write(message)
            }
        }
        varps.clean()

        for (i in 0 until getSkills().maxSkills) {
            if (getSkills().isDirty(i)) {
                write(
                    UpdateStatMessage(
                        skill = i,
                        level = getSkills().getCurrentLevel(i),
                        xp = getSkills().getCurrentXp(i).toInt()
                    )
                )
                if (i == 5) {
                    sendTemporaryVarbit(9816, getSkills().getCurrentLevel(i) * 10)
                }
                getSkills().clean(i)
            }
        }
        restoreStats(timers, STAT_RESTORE, oneMinuteInGameCycles.toInt())
    }

    /**
     * Logic that should be executed every game cycle, after
     * [gg.rsmod.game.sync.task.PlayerSynchronizationTask].
     *
     * Note that this method may be handled in parallel, so be careful with race
     * conditions if any logic may modify other [Pawn]s.
     */
    fun postCycle() {
        /*
         * Flush the channel at the end.
         */
        channelFlush()
    }

    /**
     * Registers this player to the [world].
     */
    fun register(): Boolean = world.register(this)

    /**
     * Handles any logic that should be executed upon log in.
     */
    fun login() {
        if (entityType.isHumanControlled) {
            gpiLocalPlayers[index] = this
            gpiLocalIndexes[gpiLocalCount++] = index

            for (i in 1 until 2048) {
                if (i == index) {
                    continue
                }
                gpiExternalIndexes[gpiExternalCount++] = i
                gpiTileHashMultipliers[i] =
                    if (i < world.players.capacity) world.players[i]?.tile?.asTileHashMultiplier ?: 0 else 0
            }

            val tiles = IntArray(gpiTileHashMultipliers.size)
            System.arraycopy(gpiTileHashMultipliers, 0, tiles, 0, tiles.size)

            write(RebuildLoginMessage(mapSize, if (forceMapRefresh) 1 else 0, index, tile, tiles, world.xteaKeyService))
            world.getService(LoggerService::class.java, searchSubclasses = true)?.logLogin(this)
        }
        if (world.rebootTimer != -1) {
            write(UpdateRebootTimerMessage(world.rebootTimer))
        }

        initiated = true
        interfaces.displayMode = if (resizableClient) DisplayMode.RESIZABLE_NORMAL else DisplayMode.FIXED
        world.plugins.executeLogin(this)
        timers[STAT_RESTORE] = oneMinuteInGameCycles.toInt()
    }

    /**
     * Compares the player's actual stats vs their temporary stats once per minute upon login to restore stats
     */
    fun restoreStats(statRestoreTimerMap: TimerMap, statRestoreTimerKey: TimerKey, oneMinuteInGameCycles: Int) {
        statRestoreTimerMap[statRestoreTimerKey] = statRestoreTimerMap[statRestoreTimerKey] - 1
        if (statRestoreTimerMap[statRestoreTimerKey] == 0) {
            // Generates two arrays, one of temp boosted/drained levels and one of real "max levels" based on skill experience
            val tempLevels = Array(SkillSet.DEFAULT_SKILL_COUNT) { getSkills().getCurrentLevel(it) }
            val actualLevels = Array(SkillSet.DEFAULT_SKILL_COUNT) { getSkills().getMaxLevel(it) }
            actualLevels.forEachIndexed { index, actualLevel ->
                val boost = (sign((actualLevel - tempLevels[index]).toDouble()) * 1).toInt()
                val cap = (125 * (sign((actualLevel - tempLevels[index]).toDouble()))).toInt()
                if ((index == 3) && ((getCurrentHp() / 10) < actualLevel)) { // Using Skills.HITPOINTS without introducing dependency
                    alterLifepoints(hpRestoreMultiplier, 0)
                }
                if (index != 5) { // Using Skills.PRAYER without introducing dependency
                    getSkills().alterCurrentLevel(index, boost, cap)
                }
            }
            // reset the value of the timer to 1 minute in game cycles
            statRestoreTimerMap[statRestoreTimerKey] = oneMinuteInGameCycles
        }
    }

    /**
     * Requests for this player to log out. However, the player may not be able
     * to log out immediately under certain circumstances.
     */
    fun requestLogout() {
        pendingLogout = true
        setDisconnectionTimer = true
    }

    /**
     * Handles the logic that must be executed once a player has successfully
     * logged out. This means all the prerequisites have been met for the player
     * to log out of the [world].
     *
     * The [Client] implementation overrides this method and will handle saving
     * data for the player and call this super method at the end.
     */
    open fun handleLogout() {
        interruptQueues()
        unlock()
        world.instanceAllocator.logout(this)
        world.plugins.executeLogout(this)
        world.unregister(this)
    }

    fun calculateWeight() {
        val inventoryWeight = inventory.filterNotNull().sumOf { it.getDef(world.definitions).weight }
        val equipmentWeight = equipment.filterNotNull().sumOf { it.getDef(world.definitions).weight }
        this.weight = inventoryWeight + equipmentWeight
        write(UpdateRunWeightMessage(this.weight.toInt()))
    }

    fun calculateBonuses() {
        Arrays.fill(equipmentBonuses, 0)
        for (i in 0 until equipment.capacity) {
            val item = equipment[i] ?: continue
            val def = item.getDef(world.definitions)
            def.bonuses.forEachIndexed { index, bonus -> equipmentBonuses[index] += bonus }
        }
    }

    fun setInterfaceEvents(interfaceId: Int, component: Int, from: Int, to: Int, setting: Int = 0) {
        write(
            IfSetEventsMessage(
                hash = ((interfaceId shl 16) or component),
                fromChild = from,
                toChild = to,
                setting = setting
            )
        )
    }

    fun sendTemporaryVarbit(id: Int, value: Int) {
        val message =
            if (id in -Byte.MAX_VALUE..Byte.MAX_VALUE) VarbitSmallMessage(id, value) else VarbitLargeMessage(id, value)
        write(message)
    }

    fun addXp(skill: Int, xp: Double) {
        val oldXp = getSkills().getCurrentXp(skill)
        val modifier = interpolate(1.0, 5.0, getSkills().getMaxLevel(skill))

        // calculate bonus experience
        // based on players elapsed time in-game
        var bonusExperience = calculateXpMultiplier()

        if (oldXp >= SkillSet.MAX_XP) {
            return
        }

        if (!world.gameContext.bonusExperience) {

            // apply a 1.0x bonus which does
            // nothing to overall gain
            bonusExperience = 1.0
        } else {

            // set the "bonus xp gained" varp
            val bonusGained = (xp * bonusExperience) - xp
            varps.setState(1878, varps[1878].state.plus(bonusGained.toInt() * 10))
        }

        val newXp = min(SkillSet.MAX_XP.toDouble(), (oldXp + (xp * modifier * bonusExperience)))

        /*
         * Amount of levels that have increased with the addition of [xp].
         */
        val increment = SkillSet.getLevelForXp(newXp) - SkillSet.getLevelForXp(oldXp)
        val oldTotal = getSkills().calculateTotalLevel
        /*
         * Updates the XP counter orb
         */
        varps.setState(1801, varps[1801].state + ((xp * modifier * bonusExperience) * 10).toInt())

        /*
         * Only increment the 'current' level if it's set at its capped level.
         */
        if (getSkills().getCurrentLevel(skill) == getSkills().getMaxLevel(skill)) {
            getSkills().setBaseXp(skill, newXp)
        } else {
            getSkills().setXp(skill, newXp)
        }
        attr[EXPERIENCE_UP_SKILL_ID] = skill
        world.plugins.executeSkillExperienceUp(this)
        if (increment > 0) {
            setLastTotalLevel(oldTotal)
            attr[LEVEL_UP_SKILL_ID] = skill
            attr[LEVEL_UP_INCREMENT] = increment
            attr[LEVEL_UP_OLD_XP] = oldXp
            world.plugins.executeSkillLevelUp(this)
        }
    }

    /**
     * Interpolates a value based on the given level within a range defined by low and high values.
     *
     * @param low The lower bound of the interpolation range.
     * @param high The upper bound of the interpolation range.
     * @param level The current level to interpolate the value for, starting from 1.
     * @return The interpolated value for the given level.
     */
    fun interpolate(low: Double, high: Double, level: Int): Double {
        // Calculate the total range between the low and high values
        val range = high - low

        // Determine the increment for each level within the range
        val increment = range / 97

        // Calculate and return the interpolated value based on the level and increment
        return low + increment * (level - 1)
    }

    /**
     * Calculates the XP multiplier based on the current value of varp[7233].
     *
     * This function uses a predefined list of multipliers and selects the appropriate
     * multiplier based on the current value of varp[7233], which represents the game time.
     * The game time is divided into 30-minute intervals, and each interval has a corresponding
     * multiplier in the list.
     *
     * @return The XP multiplier for the current game time.
     */
    fun calculateXpMultiplier(): Double {
        // A list of predefined multipliers corresponding to 30-minute intervals
        val multipliers = listOf(
            2.7,
            2.55,
            2.4,
            2.25,
            2.1,
            2.0,
            1.9,
            1.8,
            1.7,
            1.6,
            1.5,
            1.45,
            1.4,
            1.35,
            1.3,
            1.25,
            1.2,
            1.175,
            1.15,
            1.125,
            1.1
        )

        // Calculate the index in the list based on the value of varps[7233] (game time)
        val index = minOf((varps.getVarbit(world, 7233) - 1) / 30, multipliers.lastIndex)

        // Return the appropriate multiplier from the list based on the calculated index
        return multipliers[index]
    }

    /**
     * Add loyalty points to this player
     */
    fun addLoyalty(amount: Int) {
        attr[LOYALTY_POINTS] = (attr[LOYALTY_POINTS] ?: 0) + amount
    }

    /**
     * Remove loyalty points from this player
     */
    fun subtractLoyalty(amount: Int) {
        attr[LOYALTY_POINTS] = (attr[LOYALTY_POINTS] ?: 0) - amount
    }

    /**
     * @see largeViewport
     */
    fun setLargeViewport(largeViewport: Boolean) {
        this.largeViewport = largeViewport
    }

    /**
     * @see largeViewport
     */
    fun hasLargeViewport(): Boolean = largeViewport

    /**
     * Invoked when the player should close their current interface modal.
     */
    internal fun closeInterfaceModal() {
        world.plugins.executeModalClose(this)
    }

    /**
     * Checks if the player is registered to a [PawnList] as they should be
     * solely responsible for write access on the index. Being registered
     * to the list should essentially mean the player is registered to the
     * [world].
     *
     * @return
     * true if the player is registered to a [PawnList].
     */
    val isOnline: Boolean get() = index > 0

    /**
     * Default method to handle any incoming [Message]s that won't be
     * handled unless the [Player] is controlled by a [Client] user.
     */
    open fun handleMessages() {
    }

    /**
     * Default method to write [Message]s to the attached channel that won't
     * be handled unless the [Player] is controlled by a [Client] user.
     */
    open fun write(vararg messages: Message) {
    }

    open fun write(vararg messages: Any) {
    }

    /**
     * Default method to flush the attached channel. Won't be handled unless
     * the [Player] is controlled by a [Client] user.
     */
    open fun channelFlush() {
    }

    /**
     * Default method to close the attached channel. Won't be handled unless
     * the [Player] is controlled by a [Client] user.
     */
    open fun channelClose() {
    }

    /**
     * Write a [MessageGameMessage] to the client.
     */
    internal fun writeMessage(message: String) {
        write(MessageGameMessage(type = 0, message = message, username = null))
    }

    /**
     * Write a [MessageGameMessage] to the client.
     */
    internal fun writeFilterableMessage(message: String) {
        write(MessageGameMessage(type = 109, message = message, username = null))
    }

    /**
     * Write a [MessageGameMessage] to the client.
     */
    internal fun writeConsoleMessage(message: String) {
        write(MessageGameMessage(type = 99, message = message, username = null))
    }

    override fun toString(): String = MoreObjects.toStringHelper(this)
        .add("name", username)
        .add("pid", index)
        .toString()

    companion object {
        /**
         * How many tiles a player can 'see' at a time, normally.
         */
        const val NORMAL_VIEW_DISTANCE = 15

        /**
         * How many tiles a player can 'see' at a time when in a 'large' viewport.
         */
        const val LARGE_VIEW_DISTANCE = 127

        /**
         * How many tiles in each direction a player can see at a given time.
         * This should be as far as players can see entities such as ground items
         * and objects.
         */
        const val TILE_VIEW_DISTANCE = 32
    }
}
