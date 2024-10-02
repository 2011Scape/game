/**
 * WILDERNESS NPC & ITEM SPAWNS
 * @author Harley Gilpin <https://github.com/HarleyGilpin>
 */

spawn_npc(npc = Npcs.FISHING_SPOT_FROGSPAWN, x = 3050, z = 3704, direction = Direction.SOUTH, static = true)

spawn_npc(npc = Npcs.GUARD_BANDIT, x = 3038, z = 3656, walkRadius = 5, direction = Direction.WEST)
spawn_npc(npc = Npcs.GUARD_BANDIT, x = 3037, z = 3669, walkRadius = 5, direction = Direction.WEST)
spawn_npc(npc = Npcs.BLACK_HEATHER, x = 3043, z = 3693, walkRadius = 3, direction = Direction.WEST)
spawn_npc(npc = Npcs.FAT_TONY, x = 3037, z = 3707, walkRadius = 3, direction = Direction.WEST)
spawn_npc(npc = Npcs.GIANT_RAT, x = 3025, z = 3695, walkRadius = 5, direction = Direction.WEST)
spawn_npc(npc = Npcs.GIANT_RAT, x = 3030, z = 3703, walkRadius = 5, direction = Direction.WEST)
spawn_item(item = Items.CHEESE, amount = 1, x = 3039, z = 3707, respawnCycles = 90)
spawn_item(item = Items.TOMATO, amount = 1, x = 3039, z = 3706, respawnCycles = 90)
