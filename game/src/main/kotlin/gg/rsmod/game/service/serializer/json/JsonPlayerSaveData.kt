package gg.rsmod.game.service.serializer.json

import gg.rsmod.game.model.timer.TimerMap
import gg.rsmod.game.model.varp.Varp

/**
 * The data that will be decoded/encoded by the [JsonPlayerSerializer].
 *
 * @author Tom <rspsmods@gmail.com>
 */
data class JsonPlayerSaveData(
    val username: String,
    val displayName: String, // imma try on local
    val passwordHash: String,
    val displayMode: Int,
    val privilege: Int,
    val runEnergy: Double,
    val x: Int,
    val z: Int,
    val height: Int,
    val previousXteas: IntArray,
    val appearance: JsonPlayerSerializer.PersistentAppearance,
    val attributes: Map<String, Any>,
    val timers: List<TimerMap.PersistentTimer>,
    val skills: List<JsonPlayerSerializer.PersistentSkill>,
    val itemContainers: List<JsonPlayerSerializer.PersistentContainer>,
    val varps: List<Varp>,
    val friends: MutableList<String>?,
    val ignoredPlayers: MutableList<String>?,
    val privateFilterSetting: Int?,
    val publicFilterSetting: Int?,
    val tradeFilterSetting: Int?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonPlayerSaveData
        if (username != other.username) return false
        if (displayName != other.displayName) return false
        if (passwordHash != other.passwordHash) return false
        if (displayMode != other.displayMode) return false
        if (privilege != other.privilege) return false
        if (runEnergy != other.runEnergy) return false
        if (x != other.x) return false
        if (z != other.z) return false
        if (height != other.height) return false
        if (!previousXteas.contentEquals(other.previousXteas)) return false
        if (appearance != other.appearance) return false
        if (attributes != other.attributes) return false
        if (timers != other.timers) return false
        if (skills != other.skills) return false
        if (itemContainers != other.itemContainers) return false
        if (varps != other.varps) return false
        if (friends != other.friends) return false
        if (ignoredPlayers != other.ignoredPlayers) return false
        if (publicFilterSetting != other.publicFilterSetting) return false
        if (privateFilterSetting != other.privateFilterSetting) return false
        if (tradeFilterSetting != other.tradeFilterSetting) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + passwordHash.hashCode()
        result = 31 * result + displayMode
        result = 31 * result + privilege
        result = 31 * result + runEnergy.hashCode()
        result = 31 * result + x
        result = 31 * result + z
        result = 31 * result + height
        result = 31 * result + previousXteas.contentHashCode()
        result = 31 * result + appearance.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + timers.hashCode()
        result = 31 * result + skills.hashCode()
        result = 31 * result + itemContainers.hashCode()
        result = 31 * result + varps.hashCode()
        result = 31 * result + friends.hashCode()
        result = 31 * result + ignoredPlayers.hashCode()
        result = 31 * result + publicFilterSetting.hashCode()
        result = 31 * result + privateFilterSetting.hashCode()
        result = 31 * result + tradeFilterSetting.hashCode()
        return result
    }
}
