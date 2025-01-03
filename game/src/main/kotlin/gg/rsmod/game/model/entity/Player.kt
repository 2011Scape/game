package gg.rsmod.game.model.entity

import com.google.common.base.MoreObjects
import gg.rsmod.game.action.PlayerDeathAction
import gg.rsmod.game.fs.def.VarbitDef
import gg.rsmod.game.fs.def.VarpDef
import gg.rsmod.game.message.Message
import gg.rsmod.game.message.impl.*
import gg.rsmod.game.model.*
import gg.rsmod.game.model.attr.*
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.container.key.*
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
import gg.rsmod.game.sync.block.UpdateBlockType
import gg.rsmod.util.Misc
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * A [Pawn] that represents a player.
 *
 * @author Tom <rspsmods@gmail.com>
 */
abstract class Player(
    world: World,
) : Pawn(world) {
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
     * A flag that indicates whether the [login] method has been executed.
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

    val randomEventGift = ItemContainer(world.definitions, RANDOM_EVENT_GIFT_KEY)

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
    val containers =
        HashMap<ContainerKey, ItemContainer>().apply {
            put(INVENTORY_KEY, inventory)
            put(EQUIPMENT_KEY, equipment)
            put(BANK_KEY, bank)
            put(RANDOM_EVENT_GIFT_KEY, randomEventGift)
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
     * The players skills
     */
    val skills = SkillSet(maxSkills = world.gameContext.skillCount)

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

    var appearance = Appearance.DEFAULT

    var weight = 0.0

    var skullIcon = -1

    var runEnergy = 100.0

    /**
     * The current combat level. This must be set externally by a login plugin
     * that is used on whatever revision you want.
     */
    var combatLevel = 3

    var prayerPoints = 10.0

    val enabledSkillTarget = BooleanArray(25)
    val skillTargetMode = BooleanArray(25)
    val skillTargetValue = IntArray(25)

    var friends = mutableListOf<String>()

    var ignoredPlayers = mutableListOf<String>()

    var privateFilterSetting = ChatFilterType.ON
    var publicFilterSetting = ChatFilterType.ON
    var tradeFilterSetting = ChatFilterType.ON

    override val entityType: EntityType = EntityType.PLAYER

    /**
     * Checks if the player is running. We assume that the varp with id of
     * [173] is the running state varp.
     */
    override fun isRunning(): Boolean =
        varps[173].state == 1 || movementQueue.peekLastStep()?.type == MovementQueue.StepType.FORCED_RUN

    fun isResting(): Boolean = varps[173].state >= 3

    override fun getSize(): Int = 1

    fun setRenderAnimation(
        i: Int,
        delay: Int = -1,
        priority: TaskPriority = TaskPriority.STANDARD,
    ) {
        if (delay > 0) {
            queue(priority) {
                setRenderAnimation(i)
                wait(delay)
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

    /**
     * Mining accumulator used for dragon pickaxe
     */

    var miningAccumulator: Double = 0.0

    /**
     * Returns the player's current lifepoints as an integer value.
     *
     * @return The current lifepoints of the player.
     */
    override fun getCurrentLifepoints(): Int = varps.getVarbit(world, skills.LIFEPOINTS_VARBIT)

    /**
     * Returns the player's maximum lifepoints, calculated as 10 times their maximum level in the Constitution skill (skill ID 3).
     *
     * @return The maximum lifepoints of the player.
     */
    override fun getMaximumLifepoints(): Int = skills.getMaxLevel(3) * 10

    /**
     * Sets the player's current lifepoints to the specified value.
     *
     * @param level The new value for the player's lifepoints.
     */
    override fun setCurrentLifepoints(level: Int) {
        varps.setVarbit(world, skills.LIFEPOINTS_VARBIT, level)
    }

    /**
     * Returns the player's current prayer points as an integer value.
     *
     * @return The current prayer points of the player.
     */
    fun getCurrentPrayerPoints(): Int = varps.getVarbit(world, skills.PRAYER_POINTS_VARBIT)

    /**
     * Returns the player's maximum prayer points, calculated as 10 times their maximum level in the Prayer skill (skill ID 5).
     *
     * @return The maximum prayer points of the player.
     */
    fun getMaximumPrayerPoints(): Int = skills.getMaxLevel(5) * 10

    /**
     * Sets the player's current prayer points to the specified value.
     *
     * @param level The new value for the player's prayer points.
     */
    fun setCurrentPrayerPoints(level: Int) {
        varps.setVarbit(world, skills.PRAYER_POINTS_VARBIT, level)
    }

    fun decreasePrayerPoints(value: Int) {
        val currentPrayerPoints = getCurrentPrayerPoints()
        val newPrayerPoints = if (currentPrayerPoints > value) currentPrayerPoints - value else 0
        varps.setVarbit(world, skills.PRAYER_POINTS_VARBIT, newPrayerPoints)
    }

    /**
     * Alters the player's lifepoints by the specified value, with an optional cap value to limit the change.
     * The cap value and value to alter lifepoints must have the same sign (positive or negative).
     *
     * @param value The value by which the player's lifepoints will be altered.
     * @param capValue An optional cap value to limit the change in lifepoints (default is 0).
     *                 If provided, it must have the same sign as the value parameter.
     * @throws IllegalArgumentException If the cap value and value parameter have different signs.
     */
    fun alterLifepoints(
        value: Int,
        capValue: Int = 0,
    ) {
        check(capValue == 0 || capValue < 0 && value < 0 || capValue > 0 && value >= 0) {
            "Cap value and alter value must always be the same signum (+ or -)."
        }
        val altered =
            when {
                capValue > 0 -> min(getCurrentLifepoints() + value, getMaximumLifepoints() + capValue)
                capValue < 0 -> max(getCurrentLifepoints() + value, getMaximumLifepoints() + capValue)
                else -> min(getMaximumLifepoints(), getCurrentLifepoints() + value)
            }
        val newLevel = max(0, altered)
        val curLevel = getCurrentLifepoints()

        if (newLevel != curLevel) {
            setCurrentLifepoints(newLevel)
        }
    }

    fun alterPrayerPoints(
        value: Int,
        capValue: Int = 0,
    ) {
        check(capValue == 0 || capValue < 0 && value < 0 || capValue > 0 && value >= 0) {
            "Cap value and alter value must always be the same signum (+ or -)."
        }
        val altered =
            when {
                capValue > 0 -> min(getCurrentPrayerPoints() + value, getMaximumPrayerPoints() + capValue)
                capValue < 0 -> max(getCurrentPrayerPoints() + value, getMaximumPrayerPoints() + capValue)
                else -> min(getMaximumPrayerPoints(), getCurrentPrayerPoints() + value)
            }
        val newLevel = max(0, altered)
        val curLevel = getCurrentPrayerPoints()

        if (newLevel != curLevel) {
            setCurrentPrayerPoints(newLevel)
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

    /**
     * Applies a forced movement to the player by setting the `forceMovement` property of the block buffer
     * and adding an `UpdateBlockType.FORCE_MOVEMENT` block to the player.
     *
     * @param movement The forced movement to be applied to the player.
     */
    private fun forceMove(movement: ForcedMovement) {
        blockBuffer.forceMovement = movement
        addBlock(UpdateBlockType.FORCE_MOVEMENT)
    }

    /**
     * Applies a forced movement to the player, waits for the specified duration, and then unlocks the player
     * if they were locked during the forced movement. This method is a suspend function and should be called
     * within a coroutine context.
     *
     * @param task The queue task that this method is called within.
     * @param animation The animation the player should use while moving.
     * @param movement The forced movement to be applied to the player.
     * @param cycleDuration The number of game cycles to wait before unlocking the player. Defaults to
     *                      `movement.maxDuration / 30`.
     */
    suspend fun forceMove(
        task: QueueTask,
        animation: Animation,
        movement: ForcedMovement,
        cycleDuration: Int =
            movement.maxDuration /
                30,
    ) {
        movementQueue.clear()

        if (animation.id != -1) {
            animate(animation.id)
        }

        if (movement.lock != LockState.NONE) {
            lock = movement.lock
        }

        lastTile = Tile(tile)
        moveTo(movement.finalDestination)

        forceMove(movement)

        task.wait(cycleDuration)
        if (movement.lock != LockState.NONE) {
            lock = LockState.NONE
        }
    }

    suspend fun forceMove(
        task: QueueTask,
        movement: ForcedMovement,
        cycleDuration: Int = movement.maxDuration / 30,
    ) {
        movementQueue.clear()

        if (movement.lock != LockState.NONE) {
            lock = movement.lock
        }

        lastTile = Tile(tile)
        moveTo(movement.finalDestination)

        forceMove(movement)

        task.wait(cycleDuration)
        if (movement.lock != LockState.NONE) {
            lock = LockState.NONE
        }
    }

    /**
     * A flag that indicates whether the player's weight should be recalculated.
     * The weight calculation is triggered when the player's inventory or equipment changes.
     */
    var calculateWeight = false

    /**
     * A flag that indicates whether the player's bonuses should be recalculated.
     * The bonuses calculation is triggered when the player's equipment changes.
     */
    var calculateBonuses = false

    /**
     * Logic that should be executed every game cycle, before
     * [gg.rsmod.game.sync.task.PlayerSynchronizationTask].
     *
     * Note that this method may be handled in parallel, so be careful with race
     * conditions if any logic may modify other [Pawn]s.
     */
    override fun cycle() {
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
                timers.has(ACTIVE_COMBAT_TIMER) &&
                    damageMap
                        .getAll(type = EntityType.PLAYER, timeFrameMs = 10_000)
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

        updateInventory()
        updateEquipment()
        updateBank()
        updateRandomEventGift()
        updateShop()

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

        updateVarps()
        updateSkills()
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
     * Updates the player's inventory and sets interface events.
     */
    private fun updateInventory() {
        if (inventory.dirty) {
            write(UpdateInvFullMessage(containerKey = 93, items = inventory.rawItems))
            setEvents(interfaceId = 679, to = 27, setting = 0x457D8E)
            setEvents(interfaceId = 679, from = 28, to = 55, setting = 0x200000)
            inventory.dirty = false
            calculateWeight = true
        }
    }

    /**
     * Updates the player's equipment and appearance.
     */
    private fun updateEquipment() {
        if (equipment.dirty) {
            write(UpdateInvFullMessage(containerKey = 94, items = equipment.rawItems))
            equipment.dirty = false
            calculateBonuses = true
            calculateWeight = true
            addBlock(UpdateBlockType.APPEARANCE)
        }
    }

    /**
     * Updates the player's bank.
     */
    private fun updateBank() {
        if (bank.dirty) {
            write(UpdateInvFullMessage(containerKey = 95, items = bank.rawItems))
            bank.shift()
            bank.dirty = false
        }
    }

    /**
     * Updates the player's random event gift inventory.
     */
    private fun updateRandomEventGift() {
        if (randomEventGift.dirty) {
            write(UpdateInvFullMessage(containerKey = 307, items = randomEventGift.rawItems))
            randomEventGift.dirty = false
        }
    }

    /**
     * Updates the player's current shop.
     */
    private fun updateShop() {
        if (shopDirty) {
            attr[CURRENT_SHOP_ATTR]?.let { shop ->
                write(
                    UpdateInvFullMessage(
                        containerKey = 4,
                        items =
                            shop.items
                                .map { if (it != null) Item(it.item, it.currentAmount) else null }
                                .toTypedArray(),
                    ),
                )
                write(
                    UpdateInvFullMessage(
                        containerKey = 6,
                        items =
                            shop.sampleItems
                                .map { if (it != null) Item(it.item, it.currentAmount) else null }
                                .toTypedArray(),
                    ),
                )
            }
            shopDirty = false
        }
    }

    /**
     * Updates the player's varps (variables that are saved on the server and can be synced with the client).
     */
    private fun updateVarps() {
        for (i in 0 until varps.maxVarps) {
            if (varps.isDirty(i)) {
                val varp = varps[i]
                val message =
                    when {
                        varp.state in -Byte.MAX_VALUE..Byte.MAX_VALUE -> VarpSmallMessage(varp.id, varp.state)
                        else -> VarpLargeMessage(varp.id, varp.state)
                    }
                write(message)
            }
        }
        varps.clean()
    }

    /**
     * Updates the player's skills.
     */
    private fun updateSkills() {
        for (i in 0 until skills.maxSkills) {
            if (skills.isDirty(i)) {
                write(
                    UpdateStatMessage(
                        skill = i,
                        level = skills.getCurrentLevel(i),
                        xp = skills.getCurrentXp(i).toInt(),
                    ),
                )
                skills.clean(i)
            }
        }
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
            // Add local player
            gpiLocalPlayers[index] = this
            gpiLocalIndexes[gpiLocalCount++] = index

            // Add external players
            for (i in 1 until 2048) {
                if (i == index) continue
                gpiExternalIndexes[gpiExternalCount++] = i
                gpiTileHashMultipliers[i] =
                    if (i < world.players.capacity) world.players[i]?.tile?.asTileHashMultiplier ?: 0 else 0
            }

            // Send RebuildLoginMessage
            val tiles =
                IntArray(gpiTileHashMultipliers.size).apply {
                    System.arraycopy(gpiTileHashMultipliers, 0, this, 0, size)
                }
            write(RebuildLoginMessage(mapSize, if (forceMapRefresh) 1 else 0, index, tile, tiles, world.xteaKeyService))
        }

        // Update reboot timer if needed
        if (world.rebootTimer != -1) {
            write(UpdateRebootTimerMessage(world.rebootTimer))
        }

        // Initiate player
        initiated = true
        interfaces.displayMode = if (resizableClient) DisplayMode.RESIZABLE_NORMAL else DisplayMode.FIXED

        // Execute login plugins
        world.plugins.executeLogin(this)

        // Check if the player died before logging out
        if (attr[DEATH_FLAG] == true) {
            PlayerDeathAction.handleDeath(this)
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

    /**
     * Calculates the player's total weight based on the items in their inventory and equipment.
     * Sends an `UpdateRunWeightMessage` to update the client's weight display.
     * This method should be called when there's a change in the player's inventory or equipment.
     */
    private fun calculateWeight() {
        val inventoryWeight = inventory.filterNotNull().sumOf { it.getDef(world.definitions).weight }
        val equipmentWeight = equipment.filterNotNull().sumOf { it.getDef(world.definitions).weight }
        this.weight = inventoryWeight + equipmentWeight
        write(UpdateRunWeightMessage(this.weight.toInt()))
        calculateWeight = false
    }

    /**
     * Calculates the player's total equipment bonuses by iterating through their equipment.
     * The calculated bonuses are stored in the `equipmentBonuses` array.
     * This method should be called when there's a change in the player's equipment.
     */
    private fun calculateBonuses() {
        Arrays.fill(equipmentBonuses, 0)
        for (i in 0 until equipment.capacity) {
            val item = equipment[i] ?: continue
            val def = item.getDef(world.definitions)
            def.bonuses.forEachIndexed { index, bonus -> equipmentBonuses[index] += bonus }
        }
        calculateBonuses = false
    }

    /**
     * Sets event handlers for a specified range of interface components.
     *
     * @param interfaceId The ID of the interface containing the components.
     * @param component The ID of the component within the interface.
     * @param from The starting index of the child components for which the event handlers will be set.
     * @param to The ending index of the child components for which the event handlers will be set.
     * @param setting The setting value for the event handlers (default is 0).
     */
    fun setEvents(
        interfaceId: Int,
        component: Int = 0,
        from: Int = 0,
        to: Int,
        setting: Int = 0,
    ) {
        write(
            IfSetEventsMessage(
                hash = ((interfaceId shl 16) or component),
                fromChild = from,
                toChild = to,
                setting = setting,
            ),
        )
    }

    /**
     * Adds experience points to a specific skill for the player, taking into account any modifiers or bonus experience.
     *
     * @param skill The ID of the skill to add experience points to.
     * @param xp The amount of experience points to be added.
     * @param modifiers A flag indicating whether to apply modifiers and bonus experience (default is true).
     */
    private var accumulatedTime = 0.0 // Field to store the accumulated time

    fun addXp(
        skill: Int,
        xp: Double,
        modifiers: Boolean = true,
        disableBonusExperience: Boolean = false,
    ) {
        val oldXp = skills.getCurrentXp(skill)
        var modifier = interpolate(1.0, 5.0, skills.getMaxLevel(skill))

        // calculate bonus experience
        // based on players elapsed time in-game
        var bonusExperience = calculateXpMultiplier()

        if (oldXp >= SkillSet.MAX_XP) {
            return
        }

        if (!world.gameContext.bonusExperience || !modifiers || disableBonusExperience) {
            // apply a 1.0x bonus which does
            // nothing to overall gain
            bonusExperience = 1.0
        } else {
            // set the "bonus xp gained" varp
            val bonusGained = (xp * bonusExperience) - xp
            varps.setState(1878, varps[1878].state.plus(bonusGained.toInt() * 10))
        }

        if (!modifiers) {
            modifier = 1.0
        }

        val newXp = min(SkillSet.MAX_XP.toDouble(), (oldXp + (xp * modifier * bonusExperience)))

        /*
         * Amount of levels that have increased with the addition of [xp].
         */
        val increment = SkillSet.getLevelForXp(newXp) - SkillSet.getLevelForXp(oldXp)
        val oldTotal = skills.calculateTotalLevel
        /*
         * Updates the XP counter orb
         */
        varps.setState(1801, varps[1801].state + ((xp * modifier * bonusExperience) * 10).toInt())

        /*
         * Only increment the 'current' level if it's set at its capped level.
         */
        if (skills.getCurrentLevel(skill) == skills.getMaxLevel(skill)) {
            skills.setBaseXp(skill, newXp)
        } else {
            skills.setXp(skill, newXp)
        }
        attr[EXPERIENCE_UP_SKILL_ID] = skill
        world.plugins.executeSkillExperienceUp(this)
        if (increment > 0) {
            attr[LAST_TOTAL_LEVEL] = oldTotal
            attr[LEVEL_UP_SKILL_ID] = skill
            attr[LEVEL_UP_INCREMENT] = increment
            attr[LEVEL_UP_OLD_XP] = oldXp
            world.plugins.executeSkillLevelUp(this)
        }

        val xpGained = newXp - oldXp
        val timeToSubtract = (xpGained / 85000) * 90
        accumulatedTime += timeToSubtract
        if (timers.has(ANTI_CHEAT_TIMER)) {
            val currentAntiCheatTimer = timers[ANTI_CHEAT_TIMER]
            val subtractableTime = accumulatedTime.toInt()
            accumulatedTime -= subtractableTime
            val newAntiCheatTimer = max(0, currentAntiCheatTimer - subtractableTime)
            timers[ANTI_CHEAT_TIMER] = newAntiCheatTimer
            // writeConsoleMessage("New Time: $newAntiCheatTimer Old Time: $currentAntiCheatTimer Time Subtracted: $subtractableTime Accumulated Time: $accumulatedTime XP: $xpGained")
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
    fun interpolate(
        low: Double,
        high: Double,
        level: Int,
    ): Double {
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
    private fun calculateXpMultiplier(): Double {
        // A list of predefined multipliers corresponding to 30-minute intervals
        val multipliers =
            listOf(
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
                1.1,
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
     * Add slayer points to this player
     */
    fun addSlayerPoints(amount: Int) {
        attr[SLAYER_POINTS] = (attr[SLAYER_POINTS] ?: 0) + amount
    }

    /**
     * Remove slayer points from this player
     */
    fun subtractSlayerPoints(amount: Int) {
        attr[SLAYER_POINTS] = (attr[SLAYER_POINTS] ?: 0) - amount
    }

    /**
     * Get the current count of slayer points for this player
     */
    fun getSlayerPointsCount(): Int {
        return attr[SLAYER_POINTS] ?: 0
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

    fun getNpcKillCount(npcId: Int): Int {
        return attr[NPC_KILL_COUNTS]?.getOrDefault(npcId.toString(), 0) ?: 0
    }

    fun getNpcKillCounts(): MutableMap<String, Int> {
        return attr.getOrDefault(NPC_KILL_COUNTS, mutableMapOf())
    }

    fun incrementNpcKillCount(
        npcId: Int,
        count: Int = 1,
    ) {
        val npcKillCounts = attr.getOrDefault(NPC_KILL_COUNTS, mutableMapOf())
        npcKillCounts[npcId.toString()] = npcKillCounts.getOrDefault(npcId.toString(), 0) + count
        attr[NPC_KILL_COUNTS] = npcKillCounts
    }

    /**
     * Sends an updated friend list to the player.
     */
    fun updateFriendList() {
        val friendList = mutableListOf<Friend>()
        friends.forEach { friend ->
            val friendPlayer = world.getPlayerForName(friend)
            var worldId = if (friendPlayer != null) 15 else 0
            val added = attr[ADDED_FRIEND] == friend
            val playerIsRanked = privilege.id != 0

            // Check to see if this user should show offline, unless the player is a mod+
            if (friendPlayer != null && !playerIsRanked) {
                val playerOnFriendList = friendPlayer.friends.contains(Misc.formatForDisplay(username))
                val playerOnIgnoreList = friendPlayer.ignoredPlayers.contains(Misc.formatForDisplay(username))
                // If the friend has their private off, show them as offline
                if (friendPlayer.privateFilterSetting == ChatFilterType.OFF) worldId = 0
                // If the friend has their private on friends, and you're not on their list, show them as offline
                if (friendPlayer.privateFilterSetting == ChatFilterType.FRIENDS && !playerOnFriendList) worldId = 0
                // If the friend has you on their ignore list, show them as offline
                if (playerOnIgnoreList) worldId = 0
            }

            friendList.add(
                Friend(
                    added = added,
                    username = friend,
                    world = worldId,
                    friendChatRank = 0,
                ),
            )
        }

        write(UpdateFriendListMessage(friendList))
        attr[ADDED_FRIEND] = ""
    }

    /**
     * Updates the friendlists of all players that have this player on their friendlist
     */
    fun updateOthersFriendLists() {
        world.players.forEach { otherPlayer ->
            if (otherPlayer.friends.contains(Misc.formatForDisplay(username))) {
                otherPlayer.queue {
                    // Queue for next cycle, needed when updating for a friend logging off
                    wait(1)
                    otherPlayer.updateFriendList()
                }
            }
        }
    }

    /**
     * Sends an updated ignore list to the player.
     */
    fun updateIgnoreList() {
        val ignorePlayers = mutableListOf<IgnoredPlayer>()
        ignoredPlayers.forEach { ignoredPlayer ->
            ignorePlayers.add(
                IgnoredPlayer(
                    ignoredPlayer,
                ),
            )
        }

        write(UpdateIgnoreListMessage(ignorePlayers))
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
    abstract fun handleMessages()

    /**
     * Default method to write [Message]s to the attached channel that won't
     * be handled unless the [Player] is controlled by a [Client] user.
     */
    abstract fun write(vararg messages: Message)

    abstract fun write(vararg messages: Any)

    /**
     * Default method to flush the attached channel. Won't be handled unless
     * the [Player] is controlled by a [Client] user.
     */
    abstract fun channelFlush()

    /**
     * Default method to close the attached channel. Won't be handled unless
     * the [Player] is controlled by a [Client] user.
     */
    abstract fun channelClose()

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

    /**
     * Write a [MessagePrivateReceivedMessage] to the client. The client will see a message in their
     * private chat that starts with "From:". Depending on the privilege the username provided might also be
     * preceded with a PMod or JMod crown.
     */
    internal fun receivePrivateMessage(
        message: String,
        privilege: Privilege,
        username: String,
    ) {
        write(
            MessagePrivateReceivedMessage(
                username = username,
                privilege = privilege,
                messageId = world.getNextMessageCount(),
                15,
                message,
                world,
            ),
        )
    }

    /**
     * Write a [MessagePrivateSentMessage] to the client. The client will see a message in their
     * private chat that starts with "To:".
     */
    internal fun sendPrivateMessage(
        message: String,
        username: String,
    ) {
        write(
            MessagePrivateSentMessage(
                username = username,
                message = message,
                world = world,
            ),
        )
    }

    override fun toString(): String =
        MoreObjects
            .toStringHelper(this)
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

        const val PRAYER_VARBIT = 9816
    }
}
