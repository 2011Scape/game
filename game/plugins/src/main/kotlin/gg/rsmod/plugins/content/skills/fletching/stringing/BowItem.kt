package gg.rsmod.plugins.content.skills.fletching.stringing

import gg.rsmod.plugins.api.cfg.Items

enum class BowItem(
    val product: Int,
    val amount: Int = 1,
    val levelRequirement: Int,
    val experience: Double,
    val itemName: String = "",
) {
    SHORTBOW_U(product = Items.SHORTBOW_U, levelRequirement = 5, experience = 5.0, itemName = "Short Bow"),
    LONGBOW_U(product = Items.LONGBOW_U, levelRequirement = 10, experience = 10.0, itemName = "Long Bow"),
    SHORTBOW(product = Items.SHORTBOW, levelRequirement = 5, experience = 5.0, itemName = "Short Bow"),
    LONGBOW(product = Items.LONGBOW, levelRequirement = 10, experience = 10.0, itemName = "Long Bow"),

    OAK_SHORTBOW_U(product = Items.OAK_SHORTBOW_U, levelRequirement = 20, experience = 16.5, itemName = "Oak shortbow"),
    OAK_LONGBOW_U(product = Items.OAK_LONGBOW_U, levelRequirement = 25, experience = 25.0, itemName = "Oak longbow"),
    OAK_SHORTBOW(product = Items.OAK_SHORTBOW, levelRequirement = 20, experience = 16.5, itemName = "Oak shortbow"),
    OAK_LONGBOW(product = Items.OAK_LONGBOW, levelRequirement = 25, experience = 25.0, itemName = "Oak longbow"),

    UNSTRUNG_COMP_BOW(
        product = Items.UNSTRUNG_COMP_BOW,
        levelRequirement = 30,
        experience = 45.0,
        itemName = "Unstrung comp bow",
    ),

//    COMP_OGRE_BOW(product = Items.COMP_OGRE_BOW, levelRequirement = 30, experience = 45.0, itemName = "Unstrung comp bow"),
    WILLOW_SHORTBOW_U(
        product = Items.WILLOW_SHORTBOW_U,
        levelRequirement = 35,
        experience = 33.3,
        itemName = "Willow shortbow",
    ),
    WILLOW_LONGBOW_U(
        product = Items.WILLOW_LONGBOW_U,
        levelRequirement = 40,
        experience = 41.5,
        itemName = "Willow longbow",
    ),
    WILLOW_SHORTBOW(
        product = Items.WILLOW_SHORTBOW,
        levelRequirement = 35,
        experience = 33.3,
        itemName = "Willow shortbow",
    ),
    WILLOW_LONGBOW(
        product = Items.WILLOW_LONGBOW,
        levelRequirement = 40,
        experience = 41.5,
        itemName = "Willow longbow",
    ),

    MAPLE_SHORTBOW_U(
        product = Items.MAPLE_SHORTBOW_U,
        levelRequirement = 50,
        experience = 50.0,
        itemName = "Maple shortbow",
    ),
    MAPLE_LONGBOW_U(
        product = Items.MAPLE_LONGBOW_U,
        levelRequirement = 55,
        experience = 58.3,
        itemName = "Maple longbow",
    ),
    MAPLE_SHORTBOW(
        product = Items.MAPLE_SHORTBOW,
        levelRequirement = 50,
        experience = 50.0,
        itemName = "Maple shortbow",
    ),
    MAPLE_LONGBOW(product = Items.MAPLE_LONGBOW, levelRequirement = 55, experience = 58.3, itemName = "Maple longbow"),

    YEW_SHORTBOW_U(product = Items.YEW_SHORTBOW_U, levelRequirement = 65, experience = 67.5, itemName = "Yew shortbow"),
    YEW_LONGBOW_U(product = Items.YEW_LONGBOW_U, levelRequirement = 70, experience = 75.0, itemName = "Yew longbow"),
    YEW_SHORTBOW(product = Items.YEW_SHORTBOW, levelRequirement = 65, experience = 67.5, itemName = "Yew shortbow"),
    YEW_LONGBOW(product = Items.YEW_LONGBOW, levelRequirement = 70, experience = 75.0, itemName = "Yew longbow"),

    MAGIC_SHORTBOW_U(
        product = Items.MAGIC_SHORTBOW_U,
        levelRequirement = 80,
        experience = 83.3,
        itemName = "Magic shortbow",
    ),
    MAGIC_LONGBOW_U(
        product = Items.MAGIC_LONGBOW_U,
        levelRequirement = 85,
        experience = 91.5,
        itemName = "Magic longbow",
    ),
    MAGIC_SHORTBOW(
        product = Items.MAGIC_SHORTBOW,
        levelRequirement = 80,
        experience = 83.3,
        itemName = "Magic shortbow",
    ),
    MAGIC_LONGBOW(product = Items.MAGIC_LONGBOW, levelRequirement = 85, experience = 91.5, itemName = "Magic longbow"),
}
