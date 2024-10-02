package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.skills.mining.PickaxeType
import kotlin.math.min
import kotlin.random.Random

// TODO: Add living rock patri
val ids = intArrayOf(Npcs.LIVING_ROCK_PROTECTOR, Npcs.LIVING_ROCK_STRIKER)
val living_rock_remains = Npcs.LIVING_ROCK_REMAINS
val table = DropTableFactory
val NPC_REMOVAL_TIMER =
    TimerKey(
        persistenceKey = "npc_removal",
        tickOffline = true,
        resetOnDeath = false,
        tickForward = false,
        removeOnZero = true,
    )

private val MINING_ANIMATION_TIME = 16

val living_rock_protector =
    table.build {

        main {
            total(total = 512)
            // Runes
            obj(Items.MUD_RUNE, quantity = 3, slots = 64)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 4)
            obj(Items.DEATH_RUNE, quantity = 7, slots = 4)
            // Ores
            obj(Items.COAL_NOTED, quantity = 3, slots = 64)
            obj(Items.GOLD_ORE_NOTED, quantity = 3, slots = 64)
            obj(Items.MITHRIL_ORE_NOTED, quantity = 4, slots = 64)
            obj(Items.ADAMANTITE_ORE_NOTED, quantity = 2, slots = 16)
            obj(Items.SILVER_ORE_NOTED, quantity = 5, slots = 16)
            obj(Items.CLAY_NOTED, quantity = 3, slots = 1)
            obj(Items.CLAY_NOTED, quantity = 8, slots = 1)
            // Gems
            obj(Items.UNCUT_SAPPHIRE_NOTED, quantity = 1, slots = 64)
            obj(Items.UNCUT_EMERALD_NOTED, quantity = 1, slots = 64)
            obj(Items.UNCUT_RUBY_NOTED, quantity = 1, slots = 16)
            obj(Items.UNCUT_DIAMOND_NOTED, quantity = 1, slots = 1)
            // Other
            obj(Items.ADAMANT_PICKAXE, quantity = 1, slots = 4)
            obj(Items.MITHRIL_PICKAXE, quantity = 1, slots = 4)
            obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 1)

            nothing(slots = 60)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 10)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 150)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 828)
        }
    }

table.register(living_rock_protector, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ROCK_CRAB_DEATH)
}

on_npc_option(Npcs.LIVING_ROCK_REMAINS, option = "mine") {
    player.queue {
        val rockNPC = player.getInteractingNpc()
        mineLivingRemains(this, rockNPC)
    }
}

suspend fun mineLivingRemains(
    it: QueueTask,
    npc: Npc,
) {
    val player = it.player
    if (!canMine(it, player, npc)) {
        return
    }
    var animations = 0
    val pick =
        PickaxeType.values.reversed().firstOrNull {
            player.skills
                .getMaxLevel(Skills.MINING) >= it.level &&
                (
                    player.equipment.contains(it.item) ||
                        player.inventory.contains(
                            it.item,
                        )
                )
        }!!
    player.filterableMessage("You swing your pick at the rock.")
    var ticks = 0
    while (canMine(it, player, npc)) {
        val animationWait = if (animations < 2) MINING_ANIMATION_TIME + 1 else MINING_ANIMATION_TIME
        if (ticks % animationWait == 0) {
            player.animate(pick.animation, delay = 30)
            animations++
        }
        if (ticks % pick.ticksBetweenRolls == 0 && ticks != 0) {
            val level = player.skills.getCurrentLevel(Skills.MINING)
            if (interpolate(15, 100, level) > RANDOM.nextInt(255)) {
                onSuccess(player, npc)
            }
        }
        val time =
            min(
                animationWait - ticks % animationWait,
                pick.ticksBetweenRolls - ticks % pick.ticksBetweenRolls,
            )
        it.wait(time)
        ticks += time
    }
    player.animate(-1)
}

suspend fun canMine(
    it: QueueTask,
    p: Player,
    npc: Npc,
): Boolean {
    val pick =
        PickaxeType.values.reversed().firstOrNull {
            p.skills
                .getMaxLevel(Skills.MINING) >= it.level &&
                (p.equipment.contains(it.item) || p.inventory.contains(it.item))
        }
    if (!npc.isSpawned()) {
        return false
    }
    if (pick == null) {
        it.messageBox("You need a pickaxe to mine this rock.")
        return false
    }
    if (p.skills.getMaxLevel(Skills.MINING) < 73) {
        it.messageBox("You need a Mining level of 73 to mine living minerals.")
        return false
    }
    if (p.inventory.isFull && !p.hasItem(Items.LIVING_MINERALS)) {
        it.messageBox("Your inventory is too full to hold any living minerals.")
        return false
    }
    return true
}

fun onSuccess(
    player: Player,
    npc: Npc,
) {
    val world = player.world
    val amount = Random.nextInt(5, 25)
    player.inventory.add(Items.LIVING_MINERALS, amount)
    player.addXp(Skills.MINING, 25.0)
    if (npc.isSpawned()) {
        world.remove(npc)
    }
    player.filterableMessage("You manage to mine some living minerals.")
}

on_timer(NPC_REMOVAL_TIMER) {
    if (npc.id == living_rock_remains) {
        if (npc.isSpawned()) {
            world.remove(npc)
        }
    }
}

on_npc_death(*ids) {
    table.getDrop(
        world,
        npc.damageMap.getMostDamage()!! as Player,
        npc.id,
        Tile(x = npc.tile.x + 2, z = npc.tile.z + 2, height = npc.tile.height),
    )
    world.spawn(Npc(Npcs.LIVING_ROCK_REMAINS, npc.tile, world))
}

on_npc_spawn(living_rock_remains) {
    npc.timers[NPC_REMOVAL_TIMER] = 200 // 2 minutes in game ticks (1 minute = 100 game ticks)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 200
            // TODO: Immuned to Poison
        }
        stats {
            hitpoints = 2000
            attack = 100
            strength = 100
            defence = 100
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 100
            defenceSlash = 100
            defenceCrush = 50
            defenceMagic = 50
            defenceRanged = 150
        }
        anims {
            attack = 8843
            death = 12170
            block = 12194
        }
        aggro {
            radius = 4
            aggroMinutes = Int.MAX_VALUE
        }
    }
}
