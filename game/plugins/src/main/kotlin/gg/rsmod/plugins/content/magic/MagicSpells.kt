package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.Plugin
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getSpellbook
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.message
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

/**
 * @author Tom <rspsmods@gmail.com>
 */
object MagicSpells {

    const val INF_RUNES_VARBIT = 4145

    private val STAFF_ITEMS = arrayOf(
            Items.IBANS_STAFF, Items.SLAYERS_STAFF,
            Items.SARADOMIN_STAFF, Items.GUTHIX_STAFF, Items.ZAMORAK_STAFF
    )

    private val metadata = Int2ObjectOpenHashMap<SpellMetadata>()

    fun getMetadata(spellId: Int): SpellMetadata? = metadata[spellId]

    fun getCombatSpells(): Map<Int, SpellMetadata> = metadata.filter { it.value.spellType == SpellType.COMBAT_SPELL_TYPE }

    fun canCast(p: Player, lvl: Int, items: List<Item>): Boolean {
        if (p.getSkills().getCurrentLevel(Skills.MAGIC) < lvl) {
            p.message("Your Magic level is not high enough for this spell.")
            return false
        }
        if (p.getVarbit(INF_RUNES_VARBIT) == 0) {
            for (item in items) {
                if (p.inventory.getItemCount(item.id) < item.amount && p.equipment.getItemCount(item.id) < item.amount) {
                    p.message("You do not have enough ${item.getDef(p.world.definitions).name.lowercase()}s to cast this spell.")
                    return false
                }
            }
        }
        return true
    }

    fun removeRunes(p: Player, items: List<Item>) {
        if (p.getVarbit(INF_RUNES_VARBIT) == 0) {
            for (item in items) {
                /*
                 * Do not remove staff item requirements.
                 */
                if (item.id in STAFF_ITEMS) {
                    continue
                }
                p.inventory.remove(item)
            }
        }
    }

    fun isLoaded(): Boolean = metadata.isNotEmpty()

    fun loadSpellRequirements() {
        for(spell in SpellbookData.values()) {
            val spell = SpellMetadata(
                interfaceId = spell.interfaceId,
                component = spell.component,
                sprite = spell.sprite,
                spellType = spell.spellType,
                name = spell.spellName,
                lvl = spell.level,
                runes = spell.runes
            )
            metadata[spell.sprite] = spell
        }
    }

    fun KotlinPlugin.on_magic_spell_button(name: String, plugin: Plugin.(SpellMetadata) -> Unit) {
        if (!isLoaded()) {
            loadSpellRequirements()
        }

        val spell = metadata.values.first { it.name == name }

        on_button(spell.interfaceId, spell.component) {
            plugin(this, spell)
        }
    }
}