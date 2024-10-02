package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.COMPLETED_MAGE_ARENA
import gg.rsmod.game.model.attr.prayedAtStatue
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

val humanForm = Npcs.KOLODION_907
val ogreForm = Npcs.KOLODION_908
val trollForm = Npcs.KOLODION_11301
val darkBeastForm = Npcs.KOLODION_11302
val demonForm = Npcs.KOLODION_911

on_npc_spawn(npc = humanForm) {
    npc.forceChat("You must prove yourself... now!")
    npc.graphic(267, 100)
}

on_npc_spawn(npc = ogreForm) {
    npc.forceChat("This is only the beginning; you can't beat me!")
    npc.graphic(267, 100)
}

on_npc_spawn(npc = trollForm) {
    npc.forceChat("Foolish mortal; I am unstoppable.")
    npc.graphic(267, 100)
}

on_npc_spawn(npc = darkBeastForm) {
    npc.forceChat("Now you feel it... The dark energy.")
    npc.graphic(267, 100)
}

on_npc_spawn(npc = demonForm) {
    npc.forceChat("Aaaaaaaarrgghhhh! The power!")
    npc.graphic(267, 100)
}

val ids = intArrayOf(Npcs.KOLODION_907)

val table = DropTableFactory
val wizard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(wizard, *ids)

on_npc_death(humanForm) {
    world.spawn(Npc(ogreForm, npc.tile, world))
    npc.graphic(308, 100)
}

on_npc_death(ogreForm) {
    world.spawn(Npc(trollForm, npc.tile, world))
}

on_npc_death(trollForm) {
    world.spawn(Npc(darkBeastForm, npc.tile, world))
}

on_npc_death(darkBeastForm) {
    world.spawn(Npc(demonForm, npc.tile, world))
}

on_npc_death(demonForm) {
    val p = npc.damageMap.getMostDamage()!! as Player
    val playerSpawnTile = Tile(x = 2541, z = 4715)
    p.teleport(playerSpawnTile, TeleportType.MODERN)
    p.attr[COMPLETED_MAGE_ARENA] = true
}

set_combat_def(humanForm) {
    configs {
        attackSpeed = 7
        spell = 503
        respawnDelay = 10
    }
    stats {
        hitpoints = 30
        attack = 0
        strength = 0
        defence = 0
        magic = 60
        ranged = 0
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 99999
        defenceSlash = 9999
        defenceCrush = 9999
        defenceMagic = 0
        defenceRanged = 9999
    }
    anims {
        attack = 811
        death = 2888
        block = 404
    }
    aggro {
        radius = 10
    }
}

set_combat_def(ogreForm) {
    configs {
        attackSpeed = 7
        respawnDelay = 0
    }
    stats {
        hitpoints = 650
        attack = 85
        strength = 98
        defence = 105
        magic = 80
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 99999
        defenceSlash = 9999
        defenceCrush = 9999
        defenceMagic = 0
        defenceRanged = 9999
    }
    anims {
        attack = 8577
        death = 8576
        block = 8578
    }
    aggro {
        radius = 10
    }
}

set_combat_def(trollForm) {
    configs {
        attackSpeed = 7
        respawnDelay = 0
    }
    stats {
        hitpoints = 650
        attack = 85
        strength = 98
        defence = 105
        magic = 80
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 99999
        defenceSlash = 9999
        defenceCrush = 9999
        defenceMagic = 0
        defenceRanged = 9999
    }
    anims {
        attack = 13790
        death = 13785
        block = 13787
    }
    aggro {
        radius = 10
    }
}

set_combat_def(darkBeastForm) {
    configs {
        attackSpeed = 7
        respawnDelay = 0
    }
    stats {
        hitpoints = 780
        attack = 85
        strength = 98
        defence = 105
        magic = 80
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 99999
        defenceSlash = 9999
        defenceCrush = 9999
        defenceMagic = 0
        defenceRanged = 9999
    }
    anims {
        attack = 2731
        death = 2733
        block = 2732
    }
    aggro {
        radius = 10
    }
}

set_combat_def(demonForm) {
    configs {
        attackSpeed = 4
        respawnDelay = 0
    }
    stats {
        hitpoints = 1070
        attack = 85
        strength = 98
        defence = 105
        magic = 80
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 99999
        defenceSlash = 9999
        defenceCrush = 9999
        defenceMagic = 0
        defenceRanged = 9999
    }
    anims {
        attack = 64
        death = 67
        block = 65
    }
    aggro {
        radius = 10
    }
}

fun stepIntoPool(
    p: Player,
    obj: GameObject,
): Boolean {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> p.moveTo(2542, 4718, 0)
                2 -> p.faceTile(obj.tile, 3, 3)
                3 -> wait(1)
                4 -> p.moveTo(2542, 4720, 0)
                5 -> p.animate(7269)
                6 -> {
                    p.graphic(68)
                    p.resetRenderAnimation()
                }
                7 -> p.moveTo(2509, 4689)
                8 -> {
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

fun stepIntoPool2(
    p: Player,
    obj: GameObject,
): Boolean {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> p.moveTo(2507, 4687, 0)
                2 -> p.faceTile(obj.tile, 3, 3)
                3 -> wait(1)
                4 -> p.moveTo(2509, 4687, 0)
                5 -> p.animate(7269)
                6 -> {
                    p.graphic(68)
                    p.resetRenderAnimation()
                }
                7 -> p.moveTo(2542, 4718)
                8 -> {
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

fun prayToSaradomin(
    p: Player,
    obj: GameObject,
): Boolean {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> p.moveTo(2500, 4719, 0)
                2 -> p.faceTile(obj.tile)
                3 -> {
                    messageBox("You kneel and begin to chant to Saradomin.")
                    p.animate(645)
                }
                4 -> wait(1)
                5 -> {
                    val floorSpawnTile = Tile(x = 2500, z = 4720)
                    val SARADOMIN_CAPE = GroundItem(Items.SARADOMIN_CAPE, 1, Tile(x = 2500, z = 4720, 0))
                    player.world.spawn(SARADOMIN_CAPE)
                    player.world.spawn(TileGraphic(floorSpawnTile, id = 189, height = 0))
                }
                6 -> {
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

fun prayToGuthix(
    p: Player,
    obj: GameObject,
): Boolean {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> p.moveTo(2507, 4722, 0)
                2 -> p.faceTile(obj.tile)
                3 -> {
                    messageBox("You kneel and begin to chant to Guthix.")
                    p.animate(645)
                }
                4 -> wait(1)
                5 -> {
                    val floorSpawnTile = Tile(x = 2507, z = 4723)
                    val GUTHIX_CAPE = GroundItem(Items.GUTHIX_CAPE, 1, Tile(x = 2507, z = 4723, 0))
                    player.world.spawn(GUTHIX_CAPE)
                    player.world.spawn(TileGraphic(floorSpawnTile, id = 189, height = 0))
                }
                6 -> {
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

fun prayToZamorak(
    p: Player,
    obj: GameObject,
): Boolean {
    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> p.moveTo(2516, 4719, 0)
                2 -> p.faceTile(obj.tile)
                3 -> {
                    messageBox("You kneel and begin to chant to Zamorak.")
                    p.animate(645)
                }
                4 -> wait(1)
                5 -> {
                    val floorSpawnTile = Tile(x = 2516, z = 4720)
                    val ZAMORAK_CAPE = GroundItem(Items.ZAMORAK_CAPE, 1, Tile(x = 2516, z = 4720, 0))
                    player.world.spawn(ZAMORAK_CAPE)
                    player.world.spawn(TileGraphic(floorSpawnTile, id = 189, height = 0))
                }
                6 -> {
                    p.unlock()
                    break
                }
            }
            ticks++
            wait(1)
        }
    }
    return true
}

on_obj_option(obj = Objs.STATUE_OF_GUTHIX, option = "pray at", lineOfSightDistance = 2) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 3)
        player.queue {
            if (!player.attr.has(COMPLETED_MAGE_ARENA)) {
                messageBox("Guthix is disappointed in your resolve...")
                return@queue
            }
            if (player.hasItem(Items.GUTHIX_CAPE)) {
                messageBox("You have already received a blessing from Guthix.")
                return@queue
            }
            if (player.hasItem(Items.ZAMORAK_CAPE) || player.hasItem(Items.SARADOMIN_CAPE)) {
                messageBox("You have already received a blessing from another God.")
                return@queue
            }
            prayToGuthix(player, obj)
            player.attr[prayedAtStatue] = true
        }
    }
}

on_obj_option(obj = Objs.STATUE_OF_ZAMORAK_2874, option = "pray at", lineOfSightDistance = 2) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 3)
        player.queue {
            if (!player.attr.has(COMPLETED_MAGE_ARENA)) {
                messageBox("Zamorak is disappointed in your resolve...")
                return@queue
            }
            if (player.hasItem(Items.ZAMORAK_CAPE)) {
                messageBox("You have already received a blessing from Zamorak.")
                return@queue
            }
            if (player.hasItem(Items.SARADOMIN_CAPE) || player.hasItem(Items.GUTHIX_CAPE)) {
                messageBox("You have already received a blessing from another God.")
                return@queue
            }
            prayToZamorak(player, obj)
            player.attr[prayedAtStatue] = true
        }
    }
}

on_obj_option(obj = Objs.STATUE_OF_SARADOMIN_2873, option = "pray at", lineOfSightDistance = 2) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 3)
        player.queue {
            if (!player.attr.has(COMPLETED_MAGE_ARENA)) {
                messageBox("Saradomin is disappointed in your resolve...")
                return@queue
            }
            if (player.hasItem(Items.SARADOMIN_CAPE)) {
                messageBox("You have already received a blessing from Guthix.")
                return@queue
            }
            if (player.hasItem(Items.ZAMORAK_CAPE) || player.hasItem(Items.GUTHIX_CAPE)) {
                messageBox("You have already received a blessing from another God.")
                return@queue
            }
            prayToSaradomin(player, obj)
            player.attr[prayedAtStatue] = true
        }
    }
}

on_obj_option(obj = Objs.SPARKLING_POOL, option = "step-into", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 3)
        player.queue {
            if (!player.attr.has(COMPLETED_MAGE_ARENA)) {
                messageBox("A magical force stops you from stepping into the pool.")
                return@queue
            }
            stepIntoPool(player, obj)
        }
    }
}

on_obj_option(obj = Objs.SPARKLING_POOL_2879, option = "step-into", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile, 3, 3)
        player.queue {
            if (!player.attr.has(COMPLETED_MAGE_ARENA)) {
                messageBox("A magical force stops you from stepping into the pool.")
                return@queue
            }
            stepIntoPool2(player, obj)
        }
    }
}
