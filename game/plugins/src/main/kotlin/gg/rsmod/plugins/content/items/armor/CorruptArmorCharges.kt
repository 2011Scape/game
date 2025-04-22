package gg.rsmod.plugins.content.items.armor

import gg.rsmod.game.Server
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.item.ItemAttribute
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.combat.isAttacking
import gg.rsmod.plugins.content.combat.isBeingAttacked

class CorruptArmorCharges(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    private val chargesCheck = TimerKey()

    companion object {
        const val SIXTY_MINUTE_CHARGE = 6_000
        const val THIRTY_MINUTE_CHARGE = 3_000
        const val FIFTEEN_MINUTE_CHARGE = 1_500
    }

    init {
        on_login {
            player.timers[chargesCheck] = 1
        }

        on_timer(chargesCheck) {
            if (player.isAttacking() || player.isBeingAttacked()) {
                player.equipment.forEach {
                    CorruptArmor.values().forEach { corrupt ->
                        if (corrupt.newId == it?.id) {
                            val degradedItem = Item(corrupt.degradedId, it.amount)
                            val def = player.world.definitions
                                .get(ItemDef::class.java, it.id)
                            player.equipment.remove(it)
                            player.equipment.add(degradedItem, beginSlot = def.equipSlot)
                            player.equipment[def.equipSlot]!!.attr[ItemAttribute.CHARGES] = corrupt.maxCharges - 1
                        }
                        else if (corrupt.degradedId == it?.id) {
                            if (it.hasAnyAttr()) {
                                val charges = it.attr[ItemAttribute.CHARGES]
                                val newCharges = charges!! - 1
                                it.attr[ItemAttribute.CHARGES] = newCharges
                                val def = player.world.definitions
                                    .get(ItemDef::class.java, it.id)
                                if (newCharges <= 0) {
                                    val itemName = def.name
                                    player.message("<col=ff0000>Your $itemName has degraded into dust.")
                                    player.equipment.remove(it)
                                }
                            }
                        }
                    }
                }
            }
            player.timers[chargesCheck] = 1
        }
    }
}

enum class CorruptArmor(val newId: Int, val degradedId: Int, val maxCharges: Int) {
    // These have 30 minutes of charge
    CORRUPT_DRAGON_BATTLEAXE(
        Items.CORRUPT_DRAGON_BATTLEAXE,
        Items.C_DRAGON_BATTLEAXE_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_DAGGER(
        Items.CORRUPT_DRAGON_DAGGER,
        Items.C_DRAGON_DAGGER_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_LONGSWORD(
        Items.CORRUPT_DRAGON_LONGSWORD,
        Items.C_DRAGON_LONGSWORD_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_MACE(
        Items.CORRUPT_DRAGON_MACE,
        Items.CORRUPT_DRAGON_MACE_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_SCIMITAR(
        Items.CORRUPT_DRAGON_SCIMITAR,
        Items.C_DRAGON_SCIMITAR_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_SPEAR(
        Items.CORRUPT_DRAGON_SPEAR,
        Items.CORRUPT_DRAGON_SPEAR_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_CHAINBODY(
        Items.CORRUPT_DRAGON_CHAINBODY,
        Items.CORRUPT_DRAGON_CHAINBODY_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_MED_HELM(
        Items.CORRUPT_DRAGON_MED_HELM,
        Items.CORRUPT_DRAGON_MED_HELM_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_PLATELEGS(
        Items.CORRUPT_DRAGON_PLATELEGS,
        Items.CORRUPT_DRAGON_PLATELEGS_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_PLATESKIRT(
        Items.CORRUPT_DRAGON_PLATESKIRT,
        Items.CORRUPT_DRAGON_PLATESKIRT_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    CORRUPT_DRAGON_SQ_SHIELD(
        Items.CORRUPT_DRAGON_SQ_SHIELD,
        Items.CORRUPT_DRAGON_SQ_SHIELD_DEG,
        CorruptArmorCharges.THIRTY_MINUTE_CHARGE
    ),
    // These have 15 minutes of charge
    CORRUPT_STATIUSS_WARHAMMER(
        Items.CORRUPT_STATIUSS_WARHAMMER,
        Items.C_STATIUSS_WARHAMMER_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_VESTAS_LONGSWORD(
        Items.CORRUPT_VESTAS_LONGSWORD,
        Items.C_VESTAS_LONGSWORD_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_VESTAS_SPEAR(
        Items.CORRUPT_VESTAS_SPEAR,
        Items.CORRUPT_VESTAS_SPEAR_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_ZURIELS_STAFF(
        Items.CORRUPT_ZURIELS_STAFF,
        Items.CORRUPT_ZURIELS_STAFF_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_MORRIGANS_COIF(
        Items.CORRUPT_MORRIGANS_COIF,
        Items.CORRUPT_MORRIGANS_COIF_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_MORRIGANS_LEATHER_BODY(
        Items.CORRUPT_MORRIGANS_LEATHER_BODY,
        Items.CORRUPT_MORRIGANS_LEATHER_BODY_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_MORRIGANS_LEATHER_CHAPS(
        Items.CORRUPT_MORRIGANS_LEATHER_CHAPS,
        Items.CORRUPT_MORRIGANS_LEATHER_CHAPS_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_STATIUSS_FULL_HELM(
        Items.CORRUPT_STATIUSS_FULL_HELM,
        Items.CORRUPT_STATIUS_FULL_HELM_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_STATIUSS_PLATEBODY(
        Items.CORRUPT_STATIUSS_PLATEBODY,
        Items.CORRUPT_STATIUSS_PLATEBODY_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_STATIUSS_PLATELEGS(
        Items.CORRUPT_STATIUSS_PLATELEGS,
        Items.CORRUPT_STATIUSS_PLATELEGS_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_VESTAS_CHAINBODY(
        Items.CORRUPT_VESTAS_CHAINBODY,
        Items.CORRUPT_VESTAS_CHAINBODY_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_VESTAS_PLATESKIRT(
        Items.CORRUPT_VESTAS_PLATESKIRT,
        Items.CORRUPT_VESTAS_PLATESKIRT_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_ZURIELS_HOOD(
        Items.CORRUPT_ZURIELS_HOOD,
        Items.CORRUPT_ZURIELS_HOOD_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_ZURIELS_ROBE_TOP(
        Items.CORRUPT_ZURIELS_ROBE_TOP,
        Items.CORRUPT_ZURIELS_ROBE_TOP_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),
    CORRUPT_ZURIELS_ROBE_BOTTOM(
        Items.CORRUPT_ZURIELS_ROBE_BOTTOM,
        Items.CORRUPT_ZURIELS_ROBE_BOTTOM_DEG,
        CorruptArmorCharges.FIFTEEN_MINUTE_CHARGE
    ),

    // These have 60 minute charge
    STATIUSS_WARHAMMER(
        Items.STATIUSS_WARHAMMER,
        Items.STATIUS_WARHAMMER_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    VESTAS_LONGSWORD(
        Items.VESTAS_LONGSWORD,
        Items.VESTAS_LONGSWORD_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    VESTAS_SPEAR(
        Items.VESTAS_SPEAR,
        Items.VESTAS_SPEAR_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    ZURIELS_STAFF(
        Items.ZURIELS_STAFF,
        Items.ZURIELS_STAFF_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    MORRIGANS_COIF(
        Items.MORRIGANS_COIF,
        Items.MORRIGANS_COIF_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    MORRIGANS_LEATHER_BODY(
        Items.MORRIGANS_LEATHER_BODY,
        Items.MORRIGANS_LEATHER_BODY_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    MORRIGANS_LEATHER_CHAPS(
        Items.MORRIGANS_LEATHER_CHAPS,
        Items.MORRIGANS_LEATHER_CHAPS_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    STATIUSS_FULL_HELM(
        Items.STATIUSS_FULL_HELM,
        Items.STATIUS_FULL_HELM_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    STATIUSS_PLATEBODY(
        Items.STATIUSS_PLATEBODY,
        Items.STATIUSS_PLATEBODY_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    STATIUSS_PLATELEGS(
        Items.STATIUSS_PLATELEGS,
        Items.STATIUSS_PLATELEGS_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    VESTAS_CHAINBODY(
        Items.VESTAS_CHAINBODY,
        Items.VESTAS_CHAINBODY_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    VESTAS_PLATESKIRT(
        Items.VESTAS_PLATESKIRT,
        Items.VESTAS_PLATESKIRT_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    ZURIELS_HOOD(
        Items.ZURIELS_HOOD,
        Items.ZURIELS_HOOD_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    ZURIELS_ROBE_TOP(
        Items.ZURIELS_ROBE_TOP,
        Items.ZURIELS_ROBE_TOP_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
    ZURIELS_ROBE_BOTTOM(
        Items.ZURIELS_ROBE_BOTTOM,
        Items.ZURIELS_ROBE_BOTTOM_DEG,
        CorruptArmorCharges.SIXTY_MINUTE_CHARGE
    ),
}
