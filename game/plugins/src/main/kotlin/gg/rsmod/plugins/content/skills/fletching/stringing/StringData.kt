package gg.rsmod.plugins.content.skills.fletching.stringing

import gg.rsmod.plugins.api.cfg.Items

enum class StringData(
    val bow_u: Int,
    val products: Array<BowItem>,
) {
    SHORTBOW(
        bow_u = Items.SHORTBOW_U,
        products =
            arrayOf(
                BowItem.SHORTBOW,
            ),
    ),
    LONGBOW(
        bow_u = Items.LONGBOW_U,
        products =
            arrayOf(
                BowItem.LONGBOW,
            ),
    ),
    OAK_SHORTBOW(
        bow_u = Items.OAK_SHORTBOW_U,
        products =
            arrayOf(
                BowItem.OAK_SHORTBOW,
            ),
    ),
    OAK_LONGBOW(
        bow_u = Items.OAK_LONGBOW_U,
        products =
            arrayOf(
                BowItem.OAK_LONGBOW,
            ),
    ),
    WILLOW_SHORTBOW(
        bow_u = Items.WILLOW_SHORTBOW_U,
        products =
            arrayOf(
                BowItem.WILLOW_SHORTBOW,
            ),
    ),
    WILLOW_LONGBOW(
        bow_u = Items.WILLOW_LONGBOW_U,
        products =
            arrayOf(
                BowItem.WILLOW_LONGBOW,
            ),
    ),
    MAPLE_SHORTBOW(
        bow_u = Items.MAPLE_SHORTBOW_U,
        products =
            arrayOf(
                BowItem.MAPLE_SHORTBOW,
            ),
    ),
    MAPLE_LONGBOW(
        bow_u = Items.MAPLE_LONGBOW_U,
        products =
            arrayOf(
                BowItem.MAPLE_LONGBOW,
            ),
    ),
    YEW_SHORTBOW(
        bow_u = Items.YEW_SHORTBOW_U,
        products =
            arrayOf(
                BowItem.YEW_SHORTBOW,
            ),
    ),
    YEW_LONGBOW(
        bow_u = Items.YEW_LONGBOW_U,
        products =
            arrayOf(
                BowItem.YEW_LONGBOW,
            ),
    ),
    MAGIC_SHORTBOW(
        bow_u = Items.MAGIC_SHORTBOW_U,
        products =
            arrayOf(
                BowItem.MAGIC_SHORTBOW,
            ),
    ),
    MAGIC_LONGBOW(
        bow_u = Items.MAGIC_LONGBOW_U,
        products =
            arrayOf(
                BowItem.MAGIC_LONGBOW,
            ),
    ),
    ;

    companion object {
        val values = enumValues<StringData>()
        val bowDefinitions =
            values().associate { bow_u ->
                bow_u.bow_u to bow_u.products.associateBy { stringOption -> stringOption.product }
            }
    }
}
