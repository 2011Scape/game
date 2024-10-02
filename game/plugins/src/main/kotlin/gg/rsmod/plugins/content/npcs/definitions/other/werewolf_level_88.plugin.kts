package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids =
    intArrayOf(
        Npcs.WEREWOLF_6006,
        Npcs.WEREWOLF_6007,
        Npcs.WEREWOLF_6008,
        Npcs.WEREWOLF_6009,
        Npcs.WEREWOLF_6010,
        Npcs.WEREWOLF_6011,
        Npcs.WEREWOLF_6012,
        Npcs.WEREWOLF_6013,
        Npcs.WEREWOLF_6014,
        Npcs.WEREWOLF_6015,
        Npcs.WEREWOLF_6016,
        Npcs.WEREWOLF_6017,
        Npcs.WEREWOLF_6018,
        Npcs.WEREWOLF_6019,
        Npcs.WEREWOLF_6020,
        Npcs.WEREWOLF_6021,
        Npcs.WEREWOLF_6022,
        Npcs.WEREWOLF_6023,
        Npcs.WEREWOLF_6024,
        Npcs.WEREWOLF_6025,
    )
val table = DropTableFactory
val werewolf =
    table.build {

        guaranteed {
            obj(Items.WOLF_BONES)
        }
        main {
            total(128)
            // FOOD
            obj(Items.RAW_CHICKEN, quantity = 5, slots = 16)
            obj(Items.RAW_BEEF, quantity = 5, slots = 16)
            obj(Items.RAW_BEAR_MEAT, quantity = 5, slots = 16)
            obj(Items.JUG_OF_WINE, quantity = 1, slots = 16)
            // WEAPONS AND ARMOUR
            obj(Items.STEEL_SCIMITAR, quantity = 1, slots = 16)
            obj(Items.STEEL_HATCHET, quantity = 1, slots = 16)
            obj(Items.STEEL_ARROW_P, quantity = 5, slots = 4)
            obj(Items.STEEL_ARROW, quantity = 50, slots = 4)
            obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 4)
            obj(Items.MITHRIL_SCIMITAR, quantity = 1, slots = 4)
            obj(Items.MITHRIL_SQ_SHIELD, quantity = 1, slots = 4)
            obj(Items.RUNE_MED_HELM, quantity = 1, slots = 4)
            obj(Items.RUNE_JAVELIN, quantity = 1, slots = 1)
            // OTHER
            table(Herbs.minorHerbTable, slots = 3)
            table(Gems.gemTable, slots = 3)
            nothing(1)
        }
    }

table.register(werewolf, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HALF_WEREWOLF_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
    // Respawn citizen after werewolf is killed.
    val npcCoordinates =
        mapOf(
            6026 to Tile(3489, 3490, 0), // BORIS
            6027 to Tile(3490, 3474, 0), // IMRE
            6028 to Tile(3497, 3497, 0), // YURI
            6029 to Tile(3474, 3474, 0), // JOSEPH
            6030 to Tile(3496, 3476, 0), // NIKOLAI
            6031 to Tile(3511, 3482, 0), // EDUARD
            6032 to Tile(3502, 3487, 0), // LEV
            6033 to Tile(3483, 3478, 0), // GEORGY
            6034 to Tile(3501, 3493, 0), // SVETLANA
            6035 to Tile(3485, 3489, 0), // IRINA
            6036 to Tile(3499, 3474, 1), // ALEXIS
            6037 to Tile(3497, 3475, 0), // MILLA
            6038 to Tile(3490, 3472, 1), // GALINA
            6039 to Tile(3483, 3495, 0), // SOFIYA
            6040 to Tile(3498, 3472, 1), // KSENIA
            6041 to Tile(3479, 3492, 0), // YADVIGA
            6042 to Tile(3479, 3498, 0), // NIKITA
            6043 to Tile(3490, 3473, 0), // VERA
            6044 to Tile(3505, 3491, 0), // ZOJA
            6045 to Tile(3479, 3498, 1), // LILIYA
        )
    val newNpcId = npc.id + 20
    val npcTile = npcCoordinates[newNpcId] ?: Tile(npc.tile.x, npc.tile.z, npc.tile.height) // Default value if NPC ID not found in the map
    val citizen = Npc(newNpcId, npcTile, world)
    citizen.walkRadius = 5
    world.queue {
        wait(25)
        world.spawn(citizen)
    }
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 0
        }
        stats {
            hitpoints = 1000
            attack = 70
            strength = 70
            defence = 70
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceMagic = 60
        }
        anims {
            attack = 6536
            death = 6537
            block = 6538
        }
        aggro {
            radius = 5
            alwaysAggro()
        }
    }
}
