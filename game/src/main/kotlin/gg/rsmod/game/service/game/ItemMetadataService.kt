package gg.rsmod.game.service.game

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import gg.rsmod.game.Server
import gg.rsmod.game.fs.DefinitionSet
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.bytes.Byte2ByteOpenHashMap
import mu.KLogging
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Tom <rspsmods@gmail.com>
 */
class ItemMetadataService : Service {
    override fun init(
        server: Server,
        world: World,
        serviceProperties: ServerProperties,
    ) {
        val path = Paths.get(serviceProperties.getOrDefault("path", "./data/cfg/items.yml"))
        if (!Files.exists(path)) {
            throw FileNotFoundException("Path does not exist. $path")
        }

        Files.newBufferedReader(path).use { reader ->
            load(world.definitions, reader)
        }
    }

    override fun postLoad(
        server: Server,
        world: World,
    ) {
    }

    override fun bindNet(
        server: Server,
        world: World,
    ) {
    }

    override fun terminate(
        server: Server,
        world: World,
    ) {
    }

    private fun load(
        definitions: DefinitionSet,
        reader: BufferedReader,
    ) {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.propertyNamingStrategy = PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES

        val items = mapper.readValue(reader, Array<Metadata>::class.java).map { it.id to it }.toMap()

        val itemCount = definitions.getCount(ItemDef::class.java)

        for (i in 0 until itemCount) {
            val def = definitions.get(ItemDef::class.java, i)
            val data = items[def.id] ?: continue

            if (def.id == 995) {
                def.cost = 1
            }

            def.examine = data.examine
            if (!data.destroyMessage.isNullOrEmpty()) {
                def.destroyMessage = data.destroyMessage
            }
            def.tradeable = data.tradeable
            def.weight = data.weight

            if (data.equipment != null) {
                val equipment = data.equipment

                def.attackSpeed = equipment.attackSpeed
                def.attackAudio = equipment.attackAudio
                def.weaponType = equipment.weaponType
                def.appearanceId = equipment.equipId
                def.removeHead = equipment.removeHead
                def.removeBeard = equipment.removeBeard
                def.removeArms = equipment.removeArms
                def.equipSlot = equipment.equipSlot
                def.equipType = equipment.equipType
                if (equipment.skillReqs != null) {
                    val reqs = Byte2ByteOpenHashMap()
                    equipment.skillReqs.filter { it.skill != null }.forEach { req ->
                        reqs[req.skill!!] = req.level!!.toByte()
                    }
                    def.skillReqs = reqs
                }

                def.bonuses =
                    intArrayOf(
                        equipment.attackStab,
                        equipment.attackSlash,
                        equipment.attackCrush,
                        equipment.attackMagic,
                        equipment.attackRanged,
                        equipment.defenceStab,
                        equipment.defenceSlash,
                        equipment.defenceCrush,
                        equipment.defenceMagic,
                        equipment.defenceRanged,
                        equipment.summoning,
                        equipment.absorbMelee,
                        equipment.absorbMagic,
                        equipment.absorbRanged,
                        equipment.meleeStrength,
                        equipment.rangedStrength,
                        equipment.prayer,
                        equipment.magicDamage,
                    )
            }
        }

        logger.info { "Loaded ${items.size} item metadata set${if (items.size != 1) "s" else ""}." }
    }

    private data class Metadata(
        val id: Int = -1,
        val name: String = "",
        val destroyMessage: String? = null,
        val examine: String? = null,
        val tradeable: Boolean = false,
        val weight: Double = 0.0,
        val equipment: Equipment? = null,
    )

    private data class Equipment(
        @JsonProperty("equip_slot") val equipSlot: Int = -1,
        @JsonProperty("equip_type") val equipType: Int = -1,
        @JsonProperty("appearance_id") val equipId: Int = -1,
        @JsonProperty("remove_head") val removeHead: Boolean = false,
        @JsonProperty("remove_beard") val removeBeard: Boolean = false,
        @JsonProperty("remove_arms") val removeArms: Boolean = false,
        @JsonProperty("weapon_type") val weaponType: Int = -1,
        @JsonProperty("attack_speed") val attackSpeed: Int = -1,
        @JsonProperty("attack_stab") val attackStab: Int = 0,
        @JsonProperty("attack_slash") val attackSlash: Int = 0,
        @JsonProperty("attack_crush") val attackCrush: Int = 0,
        @JsonProperty("attack_magic") val attackMagic: Int = 0,
        @JsonProperty("attack_ranged") val attackRanged: Int = 0,
        @JsonProperty("defence_stab") val defenceStab: Int = 0,
        @JsonProperty("defence_slash") val defenceSlash: Int = 0,
        @JsonProperty("defence_crush") val defenceCrush: Int = 0,
        @JsonProperty("defence_magic") val defenceMagic: Int = 0,
        @JsonProperty("defence_ranged") val defenceRanged: Int = 0,
        @JsonProperty("summoning") val summoning: Int = 0,
        @JsonProperty("absorb_melee") val absorbMelee: Int = 0,
        @JsonProperty("absorb_magic") val absorbMagic: Int = 0,
        @JsonProperty("absorb_ranged") val absorbRanged: Int = 0,
        @JsonProperty("melee_strength") val meleeStrength: Int = 0,
        @JsonProperty("ranged_strength") val rangedStrength: Int = 0,
        @JsonProperty("magic_damage") val magicDamage: Int = 0,
        @JsonProperty("prayer") val prayer: Int = 0,
        @JsonProperty("attack_audio") val attackAudio: Int = 0,
        @JsonProperty("skill_reqs") val skillReqs: Array<SkillRequirement>? = null,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Equipment

            if (equipSlot != other.equipSlot) return false
            if (weaponType != other.weaponType) return false
            if (attackSpeed != other.attackSpeed) return false
            if (attackStab != other.attackStab) return false
            if (attackSlash != other.attackSlash) return false
            if (attackCrush != other.attackCrush) return false
            if (attackMagic != other.attackMagic) return false
            if (attackRanged != other.attackRanged) return false
            if (defenceStab != other.defenceStab) return false
            if (defenceSlash != other.defenceSlash) return false
            if (defenceCrush != other.defenceCrush) return false
            if (defenceMagic != other.defenceMagic) return false
            if (defenceRanged != other.defenceRanged) return false
            if (summoning != other.summoning) return false
            if (meleeStrength != other.meleeStrength) return false
            if (rangedStrength != other.rangedStrength) return false
            if (magicDamage != other.magicDamage) return false
            if (prayer != other.prayer) return false
            if (skillReqs != null) {
                if (other.skillReqs == null) return false
                if (!skillReqs.contentEquals(other.skillReqs)) return false
            } else if (other.skillReqs != null) {
                return false
            }

            return true
        }

        override fun hashCode(): Int {
            var result = equipSlot.hashCode()
            result = 31 * result + weaponType
            result = 31 * result + attackSpeed
            result = 31 * result + attackStab
            result = 31 * result + attackSlash
            result = 31 * result + attackCrush
            result = 31 * result + attackMagic
            result = 31 * result + attackRanged
            result = 31 * result + defenceStab
            result = 31 * result + defenceSlash
            result = 31 * result + defenceCrush
            result = 31 * result + defenceMagic
            result = 31 * result + defenceRanged
            result = 31 * result + summoning
            result = 31 * result + meleeStrength
            result = 31 * result + rangedStrength
            result = 31 * result + magicDamage
            result = 31 * result + prayer
            result = 31 * result + (skillReqs?.contentHashCode() ?: 0)
            return result
        }
    }

    private data class SkillRequirement(
        @JsonProperty("skill") val skill: Byte?,
        @JsonProperty("level") val level: Int?,
    )

    companion object : KLogging()
}
