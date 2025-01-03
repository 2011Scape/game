package gg.rsmod.game.service.serializer.json

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.mkammerer.argon2.Argon2Factory
import gg.rsmod.game.Server
import gg.rsmod.game.model.*
import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.attr.DOUBLE_ATTRIBUTES
import gg.rsmod.game.model.attr.LONG_ATTRIBUTES
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.interf.DisplayMode
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.game.service.serializer.PlayerLoadResult
import gg.rsmod.game.service.serializer.PlayerSerializerService
import gg.rsmod.net.codec.login.LoginRequest
import gg.rsmod.util.ServerProperties
import mu.KLogging
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.math.max

/**
 * A [PlayerSerializerService] implementation that decodes and encodes player
 * data in JSON.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class JsonPlayerSerializer : PlayerSerializerService() {
    private lateinit var path: Path

    override fun initSerializer(
        server: Server,
        world: World,
        serviceProperties: ServerProperties,
    ) {
        path = Paths.get(serviceProperties.getOrDefault("path", "./data/saves/"))
        if (!Files.exists(path)) {
            Files.createDirectory(path)
            logger.info("Path does not exist: $path, creating directory...")
        }
    }

    override fun loadClientData(
        client: Client,
        request: LoginRequest,
    ): PlayerLoadResult {
        client.loginUsername = client.loginUsername.lowercase()

        if (!characterExists(client.loginUsername)) {
            configureNewPlayer(client, request)
            client.uid = PlayerUID(client.loginUsername)
            saveClientData(client)
            return PlayerLoadResult.NEW_ACCOUNT
        }
        try {
            val world = client.world
            val save = path.resolve(client.loginUsername)
            val reader = BufferedReader(FileReader(save.toFile()), 8192)
            val json = Gson()
            val data = json.fromJson(reader, JsonPlayerSaveData::class.java)
            reader.close()
            if (!request.reconnecting) {
                /*
                 * If the [request] is not a [LoginRequest.reconnecting] request, we have to
                 * verify the password is correct.
                 */

                if (!Argon2Factory.create().verify(data.passwordHash, request.password.toCharArray())) {
                    return PlayerLoadResult.INVALID_CREDENTIALS
                }
            } else {
                /*
                 * If the [request] is a [LoginRequest.reconnecting] request, we
                 * verify that the login xteas match from our previous session.
                 */
                if (!Arrays.equals(data.previousXteas, request.xteaKeys)) {
                    return PlayerLoadResult.INVALID_RECONNECTION
                }
            }
            client.loginUsername = data.username
            client.uid = PlayerUID(data.username)
            client.username = data.displayName
            client.passwordHash = data.passwordHash
            client.tile = Tile(data.x, data.z, data.height)
            client.privilege = world.privileges.get(data.privilege) ?: Privilege.DEFAULT
            client.runEnergy = data.runEnergy
            client.interfaces.displayMode =
                DisplayMode.values.firstOrNull { it.id == data.displayMode } ?: DisplayMode.FIXED
            client.appearance =
                Appearance(
                    data.appearance.looks,
                    data.appearance.colors,
                    Gender.values.firstOrNull { it.id == data.appearance.gender } ?: Gender.MALE,
                )
            data.skills.forEach { skill ->
                client.skills.setXp(skill.skill, skill.xp)
                client.skills.setCurrentLevel(skill.skill, skill.lvl)
                client.skills.setLastLevel(skill.skill, skill.lastLvl)
            }
            data.itemContainers.forEach {
                val key = world.plugins.containerKeys.firstOrNull { other -> other.name == it.name }
                if (key == null) {
                    logger.error { "Container was found in serialized data, but is not registered to our World. [key=${it.name}]" }
                    return@forEach
                }
                val container =
                    if (client.containers.containsKey(key)) {
                        client.containers[key]
                    } else {
                        client.containers[key] = ItemContainer(client.world.definitions, key)
                        client.containers[key]
                    }!!
                it.items.forEach { slot, item ->
                    container[slot] = item
                }
            }

            /**
             * The value.toInt() below loses any information that may have been stored as Double or Long.
             * Therefore, these attributes were stored as sub-attributes to their respective super-attributes.
             */
            val longAttributes = data.attributes[LONG_ATTRIBUTES.persistenceKey!!] as? Map<String, Double>
            val doubleAttributes = data.attributes[DOUBLE_ATTRIBUTES.persistenceKey!!] as? Map<String, Double>
            data.attributes
                .filter {
                    it.key != LONG_ATTRIBUTES.persistenceKey &&
                        it.key != DOUBLE_ATTRIBUTES.persistenceKey
                }.forEach { (key, value) ->
                    val attribute = AttributeKey<Any>(key)
                    client.attr[attribute] = if (value is Double) value.toInt() else value
                }
            longAttributes?.forEach { (key, value) ->
                val attribute = AttributeKey<Long>(key)
                client.attr[attribute] = value.toLong()
            }
            doubleAttributes?.forEach { (key, value) ->
                val attribute = AttributeKey<Double>(key)
                client.attr[attribute] = value
            }

            data.timers.forEach { timer ->
                var time = timer.timeLeft
                if (timer.tickOffline) {
                    val elapsed = System.currentTimeMillis() - timer.currentMs
                    val ticks = (elapsed / client.world.gameContext.cycleTime).toInt()
                    time -= ticks
                }
                val key =
                    TimerKey(
                        persistenceKey = timer.identifier,
                        tickOffline = timer.tickOffline,
                        tickForward = timer.tickForward,
                        resetOnDeath = timer.resetOnDeath,
                        removeOnZero = timer.removeOnZero,
                    )
                client.timers[key] = max(0, time)
            }
            data.varps.forEach { varp ->
                client.varps.setState(varp.id, varp.state)
            }
            client.friends = data.friends?.toMutableList() ?: mutableListOf()
            client.ignoredPlayers = data.ignoredPlayers?.toMutableList() ?: mutableListOf()
            client.publicFilterSetting =
                ChatFilterType.getSettingById(data.publicFilterSetting) ?: ChatFilterType.getSettingById(0)!!
            client.privateFilterSetting =
                ChatFilterType.getSettingById(data.privateFilterSetting) ?: ChatFilterType.getSettingById(0)!!
            client.tradeFilterSetting =
                ChatFilterType.getSettingById(data.tradeFilterSetting) ?: ChatFilterType.getSettingById(0)!!
            return PlayerLoadResult.LOAD_ACCOUNT
        } catch (e: Exception) {
            logger.error(e) { "Error when loading player: ${request.username}" }
            return PlayerLoadResult.MALFORMED
        }
    }

    override fun saveClientData(client: Client): Boolean {
        client.loginUsername = client.loginUsername.lowercase() // Convert username to lowercase
        val data =
            JsonPlayerSaveData(
                username = client.loginUsername,
                passwordHash = client.passwordHash,
                privilege = client.privilege.id,
                displayName = client.username, // this order didnt change tho hmm
                x = client.tile.x,
                z = client.tile.z,
                height = client.tile.height,
                previousXteas = client.currentXteaKeys,
                displayMode = client.interfaces.displayMode.id,
                runEnergy = client.runEnergy,
                appearance = client.getPersistentAppearance(),
                attributes = client.attr.toPersistentMap(),
                timers = client.timers.toPersistentTimers(),
                skills = client.getPersistentSkills(),
                itemContainers = client.getPersistentContainers(),
                varps = client.varps.getAll().filter { it.state != 0 },
                friends = client.friends,
                ignoredPlayers = client.ignoredPlayers,
                publicFilterSetting = client.publicFilterSetting.settingId,
                privateFilterSetting = client.privateFilterSetting.settingId,
                tradeFilterSetting = client.tradeFilterSetting.settingId,
            )
        val writer = Files.newBufferedWriter(path.resolve(client.loginUsername))
        val json = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        json.toJson(data, writer)
        writer.close()
        return true
    }

    private fun Client.getPersistentContainers(): List<PersistentContainer> {
        val persistent = mutableListOf<PersistentContainer>()

        containers.forEach { (key, container) ->
            if (!container.isEmpty) {
                persistent.add(PersistentContainer(key.name, container.toMap()))
            }
        }

        return persistent
    }

    private fun Client.getPersistentSkills(): List<PersistentSkill> {
        val skills = mutableListOf<PersistentSkill>()

        for (i in 0 until this.skills.maxSkills) {
            val xp = this.skills.getCurrentXp(i)
            val lvl = this.skills.getCurrentLevel(i)
            val lastLevel = this.skills.getLastLevel(i)

            skills.add(PersistentSkill(skill = i, xp = xp, lvl = lvl, lastLvl = lastLevel))
        }

        return skills
    }

    private fun Client.getPersistentAppearance(): PersistentAppearance =
        PersistentAppearance(appearance.gender.id, appearance.looks, appearance.colors)

    /**
     * Checks to see if the player exists in the saves folder
     *
     * @param username The username of the player
     *
     * @return If the player exists in the server's save files.
     */
    fun characterExists(username: String): Boolean {
        val save = path.resolve(username)

        return Files.exists(save)
    }

    data class PersistentAppearance(
        @JsonProperty("gender") val gender: Int,
        @JsonProperty("looks") val looks: IntArray,
        @JsonProperty("colors") val colors: IntArray,
    )

    data class PersistentContainer(
        @JsonProperty("name") val name: String,
        @JsonProperty("items") val items: Map<Int, Item>,
    )

    data class PersistentSkill(
        @JsonProperty("skill") val skill: Int,
        @JsonProperty("xp") val xp: Double,
        @JsonProperty("lvl") val lvl: Int,
        @JsonProperty("lastLvl") val lastLvl: Int,
    )

    companion object : KLogging()
}
