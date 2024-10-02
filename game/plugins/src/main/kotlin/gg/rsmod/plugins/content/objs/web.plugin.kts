package gg.rsmod.plugins.content.objs

import gg.rsmod.game.model.attr.WEB_FATIGUE

fun Player.hasAnyWeaponType(vararg weaponTypes: WeaponType): Boolean {
    return weaponTypes.any { hasWeaponType(it) }
}

fun slashWeb(
    player: Player,
    obj: GameObject,
) {
    if (!player.attr.has(WEB_FATIGUE)) {
        player.attr[WEB_FATIGUE] = 0
    }
    val successThreshold = 1.0 - (0.3333 * player.attr[WEB_FATIGUE]!!)
    if (player.inventory.contains(Items.KNIFE) ||
        player.hasAnyWeaponType(
            WeaponType.AXE,
            WeaponType.LONG_SWORD,
            WeaponType.DAGGER,
            WeaponType.CLAWS,
            WeaponType.HALBERD,
            WeaponType.SCYTHE,
        )
    ) {
        if ((0..3).random() < successThreshold * 4) {
            val newObj = DynamicObject(Objs.SLASHED_WEB, obj.type, obj.rot, obj.tile)
            world.spawnTemporaryObject(newObj, 100, obj)
            player.animate(390)
            player.message("You slash the web apart!")
            player.attr[WEB_FATIGUE] = (player.attr[WEB_FATIGUE] ?: 0) + 1
        } else {
            // Increase fatigue with each failed attempt
            if (player.attr[WEB_FATIGUE]!! > 5) {
                player.attr[WEB_FATIGUE] = 0
            } else {
                player.attr[WEB_FATIGUE] = (player.attr[WEB_FATIGUE] ?: 0) + 1
            }
            player.animate(390)
            player.message("You fail to cut through the web.")
        }
    } else {
        player.message("You must have a knife to slash the web.")
    }
}

/** Handles the Varrock sewers spiderweb **/
on_obj_option(obj = Objs.SPIDERWEB, option = "pass") {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}

/** Handles the Varrock sewers spiderweb **/
on_item_on_obj(obj = Objs.SPIDERWEB, item = Items.KNIFE) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}

on_obj_option(obj = Objs.WEB, option = "slash") {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}

on_item_on_obj(obj = Objs.WEB, item = Items.KNIFE) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}
