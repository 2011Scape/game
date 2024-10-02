package gg.rsmod.plugins.content.mechanics.canoes

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setComponentHidden
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.skills.woodcutting.AxeType

object CanoeUtils {
    const val SHAPE_INTERFACE = 52
    const val DESTINATION_INTERFACE = 53

    private val FROM_LUMBRIDGE = mapOf(4 to 9887, 3 to 9888, 2 to 9889, 1 to 9890)
    private val FROM_CHAMPIONS = mapOf(4 to 9891, 3 to 9892, 2 to 9893, 0 to 9906)
    private val FROM_BARBARIAN = mapOf(4 to 9894, 3 to 9895, 1 to 9905, 0 to 9904)
    private val FROM_EDGEVILLE = mapOf(4 to 9896, 2 to 9903, 1 to 9902, 0 to 9901)
    private val FROM_WILDERNESS = mapOf(3 to 9900, 2 to 9899, 1 to 9898, 0 to 9897)

    fun checkCanoe(
        player: Player,
        canoe: Canoe,
    ) {
        if (player.skills.getCurrentLevel(Skills.WOODCUTTING) < canoe.levelRequired) return
        player.setComponentHidden(SHAPE_INTERFACE, component = canoe.silhouetteChildId, hidden = true)
        player.setComponentHidden(SHAPE_INTERFACE, component = canoe.textChildId, hidden = false)
    }

    fun getCanoeFromVarbit(
        player: Player,
        varbit: Int,
    ): Canoe {
        var bit = player.getVarbit(varbit)
        if (bit > 10) bit -= 10
        return Canoe.values()[bit - 1]
    }

    fun getCraftValue(
        canoe: Canoe,
        floating: Boolean,
    ): Int =
        when (canoe) {
            Canoe.LOG -> if (floating) 11 else 1
            Canoe.DUGOUT -> if (floating) 12 else 2
            Canoe.STABLE_DUGOUT -> if (floating) 13 else 3
            Canoe.WAKA -> if (floating) 14 else 4
        }

    fun getStationIndex(tile: Tile): Int {
        return when (tile.regionId) {
            12850 -> 0 // Lumbridge
            12852 -> 1 // Champions Guild
            12341 -> 2 // Barbarian Village
            12342 -> 3 // Edgeville
            12603 -> 4 // Wilderness
            else -> 0
        }
    }

    fun getTravelAnimation(
        stationId: Int,
        destId: Int,
    ): Int {
        return when (stationId) {
            0 -> FROM_LUMBRIDGE.getOrDefault(destId, 0)
            1 -> FROM_CHAMPIONS.getOrDefault(destId, 0)
            2 -> FROM_BARBARIAN.getOrDefault(destId, 0)
            3 -> FROM_EDGEVILLE.getOrDefault(destId, 0)
            4 -> FROM_WILDERNESS.getOrDefault(destId, 0)
            else -> 0
        }
    }

    fun getShapeAnimation(axe: AxeType): Int {
        return when (axe) {
            AxeType.BRONZE -> 6744
            AxeType.IRON -> 6743
            AxeType.STEEL -> 6742
            AxeType.BLACK -> 6741
            AxeType.MITHRIL -> 6740
            AxeType.ADAMANT -> 6739
            AxeType.RUNE -> 6738
            AxeType.DRAGON -> 6745
            else -> axe.animation
        }
    }

    fun getDestinationFromButtonId(buttonId: Int): Tile {
        return when (buttonId) {
            47 -> Tile(x = 3235, z = 3242, height = 0) // Lumbridge
            48 -> Tile(x = 3202, z = 3345, height = 0) // Champs Guild
            3 -> Tile(x = 3109, z = 3415, height = 0) // Barbarian Village
            6 -> Tile(x = 3132, z = 3510, height = 0) // Edgeville
            49 -> Tile(x = 3139, z = 3796, height = 0) // Wilderness
            else -> Tile(x = 3235, z = 3242, height = 0)
        }
    }

    fun getNameByIndex(index: Int): String {
        return when (index) {
            0 -> "Lumbridge"
            1 -> "the Champion's Guild."
            2 -> "Barbarian Village"
            3 -> "Edgeville"
            4 -> "the Wilderness Pond"
            else -> "Report this in the discords bug-report section."
        }
    }

    fun getFaceLocation(tile: Tile): Tile {
        return when (getStationIndex(tile)) {
            1 -> tile.transform(0, -1, 0)
            0 -> tile.transform(1, 0, 0)
            2, 3 -> tile.transform(-1, 0, 0)
            else -> tile
        }
    }

    fun getChopLocation(tile: Tile): Tile {
        return when (getStationIndex(tile)) {
            0 -> Tile(3232, 3254, 0)
            1 -> Tile(3204, 3343, 0)
            2 -> Tile(3112, 3409, 0)
            3 -> Tile(3132, 3508, 0)
            else -> Tile(0, 0)
        }
    }

    fun getCraftFloatLocation(tile: Tile): Tile {
        return when (getStationIndex(tile)) {
            0 -> Tile(3232, 3252, 0)
            1 -> Tile(3202, 3343, 0)
            2 -> Tile(3112, 3411, 0)
            3 -> Tile(3132, 3510, 0)
            else -> Tile(0, 0)
        }
    }

    fun getYouAreHereComponent(tile: Tile): Int {
        return when (getStationIndex(tile)) {
            0 -> 25
            1 -> 24
            2 -> 23
            3 -> 19
            else -> -1
        }
    }

    fun updateCanoeStations(
        player: Player,
        varbit: Int = 0,
    ) {
        val CANOE_STATIONS = intArrayOf(1842, 1841, 1840, 1839)
        CANOE_STATIONS.forEach {
            if (it == varbit) return@forEach
            if (player.getVarbit(it) != 0) {
                player.setVarbit(it, 0)
            }
        }
    }
}
