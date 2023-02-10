package gg.rsmod.plugins.content.skills.cooking


val cookingData = CookingData.values
val cookingDefinitions = CookingData.cookingDefinitions
val rawIds = cookingData.map { data -> data.raw }.toIntArray()

val cookingObjects = arrayOf(Objs.CLAY_OVEN, Objs.FIREPIT_12285, Objs.FIREPIT_WITH_HOOK_13529, Objs.SMALL_OVEN_13533, Objs.FIREPIT_WITH_POT_13531,
    Objs.LARGE_OVEN_13536, Objs.STEEL_RANGE_13539, Objs.FANCY_RANGE_13542, Objs.RANGE, Objs.FIRE_2732, Objs.COOKING_RANGE, Objs.RANGE_3039,
    Objs.FIRE_3769, Objs.FIRE_4265, Objs.FIRE_4266, Objs.FIRE_5249, Objs.FIRE_5499, Objs.FIRE_5631, Objs.WRECKAGE, Objs.FIRE_5981,
    Objs.RANGE_9682, Objs.FIRE_10433, Objs.FIRE_11404, Objs.FIRE_11405, Objs.FIRE_11406, Objs.FIRE_12796, Objs.FIRE_13337,
    Objs.FIRE_13881, Objs.FIRE_14169, Objs.RANGE_14919, Objs.FIRE_15156, Objs.FIRE_20000, Objs.FIRE_20001, Objs.FIRE_21620,
    Objs.RANGE_22713, Objs.RANGE_22714, Objs.FIRE_23046, Objs.RANGE_24283, Objs.RANGE_24284, Objs.FIRE_25155, Objs.FIRE_25156,
    Objs.FIRE_25465, Objs.RANGE_25730, Objs.FIRE_27297, Objs.FIRE_29139, Objs.FIRE_32099, Objs.RANGE_33500, Objs.RANGE_34495,
    Objs.RANGE_34546, Objs.RANGE_36973, Objs.FIRE_37597, Objs.RANGE_37629, Objs.FIRE_37726, Objs.COOKING_RANGE_4172,
    Objs.COOKING_RANGE_5275, Objs.COOKING_RANGE_8750, Objs.COOKING_RANGE_16893, Objs.COOKING_RANGE_34565, Objs.STOVE, Objs.STOVE_9086,
    Objs.STOVE_9087, Objs.STOVE_12269, Objs.STOVE_SPACE, Objs.GOBLIN_STOVE, Objs.GOBLIN_STOVE_25441, Objs.FIREPLACE, Objs.FIREPLACE_2725,
    Objs.FIREPLACE_2726, Objs.FIREPLACE_4650, Objs.FIREPLACE_5165, Objs.FIREPLACE_6093, Objs.FIREPLACE_6094, Objs.FIREPLACE_6095,
    Objs.FIREPLACE_6096, Objs.FIREPLACE_8712, Objs.FIREPLACE_9439, Objs.FIREPLACE_9440, Objs.FIREPLACE_9441, Objs.FIREPLACE_10824,
    Objs.FIREPLACE_17640, Objs.FIREPLACE_17641, Objs.FIREPLACE_17642, Objs.FIREPLACE_17643, Objs.FIREPLACE_18039, Objs.FIREPLACE_24285,
    Objs.FIREPLACE_24329, Objs.FIREPLACE_27251, Objs.FIREPLACE_33498, Objs.FIREPLACE_35449, Objs.FIREPLACE_36815, Objs.FIREPLACE_36816
)

rawIds.forEach {
    val item = it
    on_item_on_obj(cookingObjects, item = item) {
        player.interruptQueues()
        player.resetInteractions()
        player.queue {
            produceItemBox(item, option = SkillDialogueOption.COOK, title = "Choose how many you wish to cook,<br>then click on the item to begin.", logic = ::cookItem)
        }
    }
}

fun cookItem(player: Player, item: Int, amount: Int) {
    val def = cookingDefinitions[item] ?: return
    player.queue { CookingAction.cook(this, def, amount) }
}