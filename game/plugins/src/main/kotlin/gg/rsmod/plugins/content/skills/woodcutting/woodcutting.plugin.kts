package gg.rsmod.plugins.content.skills.woodcutting

import gg.rsmod.plugins.content.skills.woodcutting.Woodcutting.Tree

private val TREES = setOf(
    Tree(TreeType.TREE, obj = Objs.TREE_1276),
    Tree(TreeType.TREE, obj = Objs.TREE_1277),
    Tree(TreeType.TREE, obj = Objs.TREE_1278),
    Tree(TreeType.TREE, obj = Objs.TREE_1280),
    Tree(TreeType.TREE, obj = Objs.TREE_1330),
    Tree(TreeType.TREE, obj = Objs.TREE_1331),
    Tree(TreeType.TREE, obj = Objs.TREE_1332),
    Tree(TreeType.TREE, obj = Objs.TREE_2410),
    Tree(TreeType.TREE, obj = Objs.TREE_2411),
    Tree(TreeType.TREE, obj = Objs.TREE_3033),
    Tree(TreeType.TREE, obj = Objs.TREE_3034),
    Tree(TreeType.TREE, obj = Objs.TREE_3036),
    Tree(TreeType.TREE, obj = Objs.TREE_3879),
    Tree(TreeType.TREE, obj = Objs.TREE_3881),
    Tree(TreeType.TREE, obj = Objs.TREE_3882),
    Tree(TreeType.TREE, obj = Objs.TREE_3883),
    Tree(TreeType.TREE, obj = Objs.TREE_5904),
    Tree(TreeType.TREE, obj = Objs.TREE_14308),
    Tree(TreeType.TREE, obj = Objs.TREE_14309),
    Tree(TreeType.TREE, obj = Objs.TREE_37477),
    Tree(TreeType.TREE, obj = Objs.TREE_37478),
    Tree(TreeType.TREE, obj = Objs.TREE_37652),
    Tree(TreeType.TREE, obj = Objs.TREE_38760),
    Tree(TreeType.TREE, obj = Objs.TREE_38782),
    Tree(TreeType.TREE, obj = Objs.TREE_38783),
    Tree(TreeType.TREE, obj = Objs.TREE_38784),
    Tree(TreeType.TREE, obj = Objs.TREE_38785),
    Tree(TreeType.TREE, obj = Objs.TREE_38786),
    Tree(TreeType.TREE, obj = Objs.TREE_38787),
    Tree(TreeType.TREE, obj = Objs.TREE_38788),
    Tree(TreeType.TREE, obj = Objs.TREE_39328),

    Tree(TreeType.TREE, obj = Objs.DEAD_TREE),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1283),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1284),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1285),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1286),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1289),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1291),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1365),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1383),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_1384),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_11866),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_32294),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_37481),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_37482),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_37483),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_41713),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_47594),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_47596),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_47598),
    Tree(TreeType.TREE, obj = Objs.DEAD_TREE_47600),

    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_3300),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_9354),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_9355),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_9366),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_9387),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_9388),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58108),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58109),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58121),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58135),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58140),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58141),
    Tree(TreeType.TREE, obj = Objs.SWAMP_TREE_58142),

    Tree(TreeType.TREE, obj = Objs.EVERGREEN),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_1316),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_1318),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_1319),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_54778),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_54787),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_57932),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_57934),
    Tree(TreeType.TREE, obj = Objs.EVERGREEN_57964),

    Tree(TreeType.OAK, obj = Objs.OAK),
    Tree(TreeType.OAK, obj = Objs.OAK_3037),
    Tree(TreeType.OAK, obj = Objs.OAK_8467),
    Tree(TreeType.OAK, obj = Objs.OAK_11999),
    Tree(TreeType.OAK, obj = Objs.OAK_37479),
    Tree(TreeType.OAK, obj = Objs.OAK_38731),
    Tree(TreeType.OAK, obj = Objs.OAK_38732),

    Tree(TreeType.WILLOW, obj = Objs.WILLOW),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_142),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_2210),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_2372),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_37480),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_38616),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_38627),
    Tree(TreeType.WILLOW, obj = Objs.WILLOW_58006),

    Tree(TreeType.TEAK, obj = Objs.TEAK),
    Tree(TreeType.TEAK, obj = Objs.TEAK_15062),
    Tree(TreeType.TEAK, obj = Objs.TEAK_46275),

    Tree(TreeType.MAPLE, obj = Objs.MAPLE_TREE_1307),
    Tree(TreeType.MAPLE, obj = Objs.MAPLE_TREE_4674),
    Tree(TreeType.MAPLE, obj = Objs.MAPLE_TREE_8444),
    Tree(TreeType.MAPLE, obj = Objs.MAPLE_TREE_46277),
    Tree(TreeType.MAPLE, obj = Objs.MAPLE_TREE_51843),

    Tree(TreeType.YEW, obj = Objs.YEW),
    Tree(TreeType.YEW, obj = Objs.YEW_12000),
    Tree(TreeType.YEW, obj = Objs.YEW_38755),

    Tree(TreeType.MAGIC, obj = Objs.MAGIC_TREE_1306),
    Tree(TreeType.MAGIC, obj = Objs.MAGIC_TREE_8409),
    Tree(TreeType.MAGIC, obj = Objs.MAGIC_TREE_37823),
)

TREES.forEach { tree ->
    on_obj_option(obj = tree.obj, option = 1) {
        val obj = player.getInteractingGameObj()
        if(player.world.definitions.get(ObjectDef::class.java, obj.id).depleted == -1) {
            player.message("Nothing interesting happens.")
            return@on_obj_option
        }
        player.interruptQueues()
        player.queue {
            Woodcutting.chopDownTree(this, obj, tree.type)
        }
    }
}