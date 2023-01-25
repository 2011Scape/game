package gg.rsmod.plugins.content.skills.fletching.whittling

import gg.rsmod.plugins.api.cfg.Items

enum class LogData(val raw: Int, val products: Array<WhittleItem>) {
    LOGS(raw = Items.LOGS, products = arrayOf(
        WhittleItem.ARROW_SHAFT_15,
        WhittleItem.SHORTBOW_U,
        WhittleItem.LONGBOW_U,
        WhittleItem.WOODEN_STOCK
    )),
    OAK(raw = Items.OAK_LOGS, products = arrayOf(
        WhittleItem.OAK_SHORTBOW_U,
        WhittleItem.OAK_LONGBOW_U,
        WhittleItem.OAK_STOCK
    )),
    ACHEY(raw = Items.ACHEY_TREE_LOGS, products = arrayOf(WhittleItem.OGRE_ARROW_SHAFT, WhittleItem.UNSTRUNG_COMP_BOW)),
    WILLOW(raw = Items.WILLOW_LOGS, products = arrayOf(
        WhittleItem.WILLOW_SHORTBOW_U,
        WhittleItem.WILLOW_LONGBOW_U,
        WhittleItem.WILLOW_STOCK
    )),
    TEAK(raw = Items.TEAK_LOGS, products = arrayOf(WhittleItem.TEAK_STOCK)),
    MAPLE(raw = Items.MAPLE_LOGS, products = arrayOf(
        WhittleItem.MAPLE_SHORTBOW_U,
        WhittleItem.MAPLE_LONGBOW_U,
        WhittleItem.MAPLE_STOCK
    )),
    MAHOGANY(raw = Items.MAHOGANY_LOGS, products = arrayOf(WhittleItem.MAHOGANY_STOCK)),
    YEW(raw = Items.YEW_LOGS, products = arrayOf(
        WhittleItem.YEW_SHORTBOW_U,
        WhittleItem.YEW_LONGBOW_U,
        WhittleItem.YEW_STOCK
    )),
    MAGIC(raw = Items.MAGIC_LOGS, products = arrayOf(WhittleItem.MAGIC_SHORTBOW_U, WhittleItem.MAGIC_LONGBOW_U));

    companion object {
        val values = enumValues<LogData>()
        val logDefinitions = values().associate { log ->
            log.raw to log.products.associateBy { whittleOption -> whittleOption.product }
        }
    }
}